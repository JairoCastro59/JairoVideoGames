package com.jdcastro.jairovideogames.ui.dashboard.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jdcastro.jairovideogames.ui.components.AlertDialogDelete
import com.jdcastro.jairovideogames.ui.components.SearchField
import com.jdcastro.jairovideogames.ui.components.SpinnerComponent
import com.jdcastro.jairovideogames.ui.state.VideogameStateUI

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navigateToDetail: (Int) -> Unit
) {
    var videogameList by remember { mutableStateOf(arrayListOf<VideogameObj>()) }

    viewModel.videogameStateUI.collectAsStateWithLifecycle().value.let { value ->
        when(value) {
            is VideogameStateUI.Loading -> { ProgressViewLoading() }
            is VideogameStateUI.Error -> { Log.e("ERROR", value.msg) }
            is VideogameStateUI.Success -> { videogameList = value.videogames }
        }
    }

    Box (
        Modifier.fillMaxSize()
            .background(Color.LightGray)
    ) {
        VideogameList(viewModel, videogameList, navigateToDetail)
    }
}

@Composable
fun ProgressViewLoading() {
    ConstraintLayout (
        modifier = Modifier
        .background(Color.DarkGray)
        .fillMaxSize()
    ) {
        val (progressIndicator) = createRefs()

        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .constrainAs(progressIndicator, {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }),
            color = Color.White,
            trackColor = Color.Gray,
        )
    }
}

@Composable
fun VideogameList(
    viewModel: DashboardViewModel,
    videogamesList: ArrayList<VideogameObj>,
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
                videogamesList.let { list ->
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

    ConstraintLayout (
        modifier = Modifier
            .width(200.dp)
            .wrapContentHeight()
            .padding(12.dp)
            .border(2.dp, Color.Black)
            .background(Color.White)
            .wrapContentSize()
            .combinedClickable(
                onClick = { item?.id?.let { navigateToDetail(it) } },
                onLongClick = { shouldShowDialog.value = true }
            )
    ) {
        val (vgImg, vgTitle) = createRefs()
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(CircleShape)
                .padding(4.dp).constrainAs(vgImg, {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }),
            model = item?.thumbnail,
            contentDescription = item?.title,
            contentScale = ContentScale.Crop
        )
        TitleRow(item?.title,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .constrainAs(vgTitle, {
                    top.linkTo(vgImg.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })
        )
    }
}

@Composable
fun TitleRow(title: String?, modifier: Modifier) {
    Row (modifier = modifier) {
        title?.let {
            Text(
                text = it,
                Modifier.align(Alignment.CenterVertically),
                color = Color.White,
                fontWeight = FontWeight.SemiBold)
        }
    }
}

