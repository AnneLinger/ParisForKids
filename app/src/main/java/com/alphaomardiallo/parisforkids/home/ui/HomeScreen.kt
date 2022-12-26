package com.alphaomardiallo.parisforkids.home.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.alphaomardiallo.parisforkids.R
import com.alphaomardiallo.parisforkids.common.ui.theme.ParisForKidsTheme
import com.alphaomardiallo.parisforkids.home.domain.UiEventCard
import com.alphaomardiallo.parisforkids.home.presenter.HomeViewModel
import com.alphaomardiallo.parisforkids.main.presenter.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import okhttp3.internal.wait

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val allEvents: List<UiEventCard> = remember {homeViewModel.allEventsAsUIEventCard.value}
    Log.e(TAG, "HomeScreen: ${allEvents.size}", )

    ParisForKidsTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .wrapContentSize(Alignment.Center)
        ) {
            HorizontalListOfEvents(list = allEvents)
        }
    }
}

@Composable
fun HorizontalListOfEvents(list: List<UiEventCard>){
    Box(modifier = Modifier.fillMaxWidth()){
        LazyRow(){
            items(list) { event ->
                EventCard(modifier = Modifier, event = event)
            }
        }
    }
}

@Composable
fun EventCard(modifier: Modifier, event: UiEventCard) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.95f)
            .height(190.dp)
            .clickable { Log.e(TAG, "EventCard: clicked ${event.title} id $event.id", null) },
        shape = MaterialTheme.shapes.medium,
        backgroundColor = Color.Black,
        contentColor = Color.White,
        elevation = 8.dp
    ) {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = 0f,
            endY = 450f
        )

        AsyncImage(
            model = event.coverImage,
            contentDescription = event.coverCredit,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(brush = gradient)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        event.tags?.map {
                            EventTagChip(tag = it!!, modifier = modifier)
                            Spacer(modifier.size(dimensionResource(id = R.dimen.margin_small)))
                        }
                    }
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Icon heart")
                }
                Spacer(modifier.size(dimensionResource(id = R.dimen.margin_large)))
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(text = event.title!!, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier.size(dimensionResource(id = R.dimen.margin_medium)))
                    Text(
                        text = event.leadText!!,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier.size(dimensionResource(id = R.dimen.margin_small)))
                    Text(text = event.audience!!, fontSize = 14.sp)
                    Spacer(modifier.size(dimensionResource(id = R.dimen.margin_small)))
                    Text(
                        text = "${event.dateDescription}  -  ${event.zipcode}  -  ${event.priceType}",
                        fontSize = 10.sp
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventTagChip(tag: String, modifier: Modifier) {
    Chip(onClick = { /*TODO*/ }, modifier = modifier.padding(2.dp)) {
        Text(text = tag, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EventCard(modifier = Modifier, event = getMockEvent())
}

fun getMockEvent() = UiEventCard(
    id = "4557ce721764a51c69a99f1e783c8a686ef64995",
    priceType = "gratuit",
    coverImage = "https://cdn.paris.fr/qfapv4/2022/11/25/huge-06753e72114d4c369b5c980262aa0462.jpg",
    zipcode = "Paris 15",
    leadText = "Venez célébrer les fêtes de fin d'année avec les bibliothécaires lors d'une heure du conte spéciale Noël!",
    title = "Racontages de Noël",
    audience = "Public enfants adolescents. A partir de 3 ans.",
    dateDescription = "Le samedi 17 décembre 2022 de 15h30 à 17h00",
    tags = listOf("Literature", "Bibliotheque"),
    coverCredit = "Pixabay", addressName = null, prm = false, blind = false, deaf = false
)