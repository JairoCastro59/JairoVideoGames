package com.jdcastro.jairovideogames.ui.videogameDetail.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import com.jdcastro.jairovideogames.ui.dashboard.viewModel.DashboardViewModel

@Composable
fun VideogameDetailScreen(viewModel: DashboardViewModel, id: Int) {

    val videogameSelected = viewModel.getVideogameDetail.observeAsState()
    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 5.dp)
    ) {
        viewModel.getVideogameDetail(id)
        DetailCard(videogameSelected.value)
    }
}

@Composable
fun DetailCard(item: VideogameObj?) {
    val context = LocalContext.current

    ConstraintLayout (Modifier.fillMaxHeight()) {
        val (image, title, developer, releaseDate, platform, descripcion, categoria, urlBtn, perfilBtn) = createRefs()

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(4.dp)
                .constrainAs(image, {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }),
            model = item?.thumbnail,
            contentDescription = item?.title,
            contentScale = ContentScale.Crop
        )

        TitleRow(
            item?.title,
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.DarkGray)
                .constrainAs(title, {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        )
        item?.let {
            SubtitleRow("Desarrollado por: ${it.developer}", Modifier
                .padding(top = 4.dp, start = 16.dp)
                .constrainAs(developer, {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                }), Color.Red)

            SubtitleRow("Lanzamiento: ${it.release_date}", Modifier
                .padding(top = 4.dp, start = 16.dp)
                .constrainAs(releaseDate, {
                    top.linkTo(developer.bottom)
                    start.linkTo(parent.start)
                }), Color.Black)

            SubtitleRow("Plataforma: ${it.platform}", Modifier
                .padding(top = 4.dp, start = 16.dp)
                .constrainAs(platform, {
                    top.linkTo(releaseDate.bottom)
                    start.linkTo(parent.start)
                }), Color.Black)

            SubtitleRow("Cetegoria: ${it.genre}", Modifier
                .padding(top = 4.dp, start = 16.dp)
                .constrainAs(categoria, {
                    top.linkTo(platform.bottom)
                    start.linkTo(parent.start)
                }), Color.Black)

            DescriptionRow(it.short_description, Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .constrainAs(descripcion, {
                    centerHorizontallyTo(parent)
                    top.linkTo(categoria.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(urlBtn.top)
                }), Color.Black)

            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 32.dp, end = 8.dp)
                    .constrainAs(urlBtn, {
                    start.linkTo(parent.start)
                    end.linkTo(perfilBtn.start)
                    bottom.linkTo(parent.bottom)

                    }),
                onClick = {
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(item.game_url)
                )
                context.startActivity(urlIntent)
            }) {
                Text(
                    text = "Web del Videojuego",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }

            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 32.dp, start = 8.dp)
                    .constrainAs(perfilBtn, {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(urlBtn.end)
                    end.linkTo(parent.end)
                }),
                onClick = {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(item.freetogame_profile_url)
                    )
                    context.startActivity(urlIntent)
                }) {
                Text(
                    text = "Perfil del Videojuego",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }

}

@Composable
fun TitleRow(title: String?, modifier: Modifier) {
    Row(modifier = modifier) {
        title?.let {
            Text(
                text = it,
                Modifier.align(Alignment.CenterVertically),
                color = Color.White,
                fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun SubtitleRow(text: String, modifier: Modifier, color: Color) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold)

}

@Composable
fun DescriptionRow(text: String, modifier: Modifier, color: Color) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = 11.sp,
        textAlign = TextAlign.Justify,
        fontWeight = FontWeight.Normal)
}


