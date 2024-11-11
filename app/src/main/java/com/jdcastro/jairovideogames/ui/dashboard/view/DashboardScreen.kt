package com.jdcastro.jairovideogames.ui.dashboard.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import com.jdcastro.jairovideogames.ui.dashboard.viewModel.DashboardViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.constraintlayout.compose.ConstraintLayout
import com.jdcastro.jairovideogames.ui.components.AlertDialogDelete
import com.jdcastro.jairovideogames.ui.components.SearchField
import com.jdcastro.jairovideogames.ui.components.SpinnerComponent

@Composable
fun DashboardScreen(viewModel: DashboardViewModel, navigateToDetail: (Int) ->Unit) {
    val videogamesList = viewModel.getVideogames.observeAsState()
    Box (
        Modifier.fillMaxSize()
            .background(Color.LightGray)
    ) {
        viewModel.getVideogameLocal()
        VideogameList(viewModel, videogamesList, navigateToDetail)
    }
}

@Composable
fun VideogameList(
    viewModel: DashboardViewModel,
    videogamesList: State<ArrayList<VideogameObj>?>,
    navigateToDetail: (Int) -> Unit
) {
    val searchQuery by viewModel.queryText.collectAsState()

    ConstraintLayout {
        val (categoriaLb, searchField, grid, categories) = createRefs()

        Text(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 5.dp)
                .constrainAs(categoriaLb, {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }),
            text = "Categorias: ",
            color = Color.White,
            fontWeight = FontWeight.SemiBold)

        SpinnerComponent(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 5.dp,)
                .constrainAs(categories, {
                    top.linkTo(parent.top)
                    start.linkTo(categoriaLb.end)
            }),
            viewModel
        )
        SearchField(
            searchQuery = searchQuery,
            onQueryChanged = viewModel::onQueryTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp)
                .constrainAs(
                    searchField,
                    constrainBlock = {
                        top.linkTo(categories.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
        )

        LazyVerticalGrid(
            modifier = Modifier.constrainAs(
                grid,
                constrainBlock = {
                    top.linkTo(searchField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ),
            columns = GridCells.Fixed(2),
            content = {
                videogamesList.value?.let { list ->
                    itemsIndexed(list) { _, item ->
                        Spacer(modifier = Modifier.height(2.dp))
                        RowItem(viewModel, item, navigateToDetail)
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowItem(viewModel: DashboardViewModel, item: VideogameObj?, navigateToDetail: (Int) -> Unit) {
    val shouldShowDialog = remember { mutableStateOf(false) }
    if (shouldShowDialog.value) {
        AlertDialogDelete(shouldShowDialog = shouldShowDialog, viewModel = viewModel, item = item)
    }
    Column (
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .padding(12.dp)
            .border(2.dp, Color.Black)
            .background(Color.White)
            .wrapContentSize()
            .combinedClickable(
                onClick = { item?.id?.let { navigateToDetail(it) } },
                onLongClick = { shouldShowDialog.value = true }
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(CircleShape)
                .padding(4.dp),
            model = item?.thumbnail,
            contentDescription = item?.title,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(2.dp))
        TitleRow(item?.title)
    }
}

@Composable
fun TitleRow(title: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.DarkGray)
    ) {
        title?.let {
            Text(
                text = it,
                Modifier.align(Alignment.CenterVertically),
                color = Color.White,
                fontWeight = FontWeight.SemiBold)
        }
    }
}

