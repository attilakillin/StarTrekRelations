package hu.attilakillin.startrekrelations.ui.screen.characters

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.attilakillin.startrekrelations.R
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.model.PagedList
import hu.attilakillin.startrekrelations.model.empty
import hu.attilakillin.startrekrelations.ui.screen.PaddedText
import java.util.concurrent.CancellationException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    charactersViewModel: CharactersViewModel = hiltViewModel(),
    onDetailsClick: (String) -> Unit
) {
    /* Character list - the main state displayed on the screen. */
    val favorites = charactersViewModel.favorites.observeAsState(listOf())
    val characters = charactersViewModel.characters.observeAsState(PagedList.empty())

    /* Focus manager - to remove editor focus when clicking search button. */
    val focusManager = LocalFocusManager.current

    /* Search input field state. */
    val searchQuery = remember { mutableStateOf("") }

    /* Immediately search characters when view is launched. */
    LaunchedEffect(key1 = Unit) {
        charactersViewModel.searchAll("")
    }

    /* Main view structure. */
    Column {
        /* Application header. */
        CenterAlignedTopAppBar( title = { Text("List of characters") } )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            /* Search bar. */
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp) ) {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    trailingIcon = {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "Clear text",
                            modifier = Modifier.clickable {
                                searchQuery.value = ""
                                focusManager.clearFocus()
                                charactersViewModel.searchAll(searchQuery.value)
                            }
                        )
                    },
                    modifier = Modifier
                        .weight(1.0f)
                        .height(56.dp)
                )

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        charactersViewModel.searchAll(searchQuery.value)
                    },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .padding(start = 8.dp)
                ) {
                    Text("Search")
                }
            }

            /* Scrollable list with favorites and online results. */
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                item(key = "LABEL-FAVORITES") {
                    Text(text = "Favorites")
                }

                if (favorites.value.isEmpty()) {
                    item(key = "EMPTY-NO-FAV") { PaddedText(text = "No favorites yet...") }
                } else {
                    items(items = favorites.value, key = { "FAV-${it.uid}" }) {
                        CharacterCard(
                            character = it,
                            charactersViewModel = charactersViewModel,
                            onCardClick = {
                                onDetailsClick(it.uid)
                            }
                        )
                    }
                }

                item(key = "LABEL-RESULTS") {
                    Text(text = "Results")
                }

                if (characters.value.content.isEmpty()) {
                    item(key = "EMPTY-NO-RES") { PaddedText(text = "No results...") }
                } else {
                    items(items = characters.value.content, key = { it.uid }) {
                        CharacterCard(
                            character = it,
                            charactersViewModel = charactersViewModel,
                            onCardClick = {
                                onDetailsClick(it.uid)
                            }
                        )
                    }

                    item {
                        LaunchedEffect(key1 = true) {
                            charactersViewModel.searchMoreCharacters()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCard(
    character: Character,
    onCardClick: () -> Unit,
    charactersViewModel: CharactersViewModel
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(4.dp),
        onClick = onCardClick,
        modifier = Modifier
            .height(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 20.dp, top = 8.dp, bottom = 8.dp, end = 5.dp)
        ) {
            Text(
                text = character.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1.0f)
            )

            if (character.isFavorite) {
                TextButton(
                    onClick = { charactersViewModel.removeFromFavorites(character.uid) }
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Remove from favorites",
                        modifier = Modifier
                            .height(32.dp)
                            .width(32.dp)
                    )
                }
            } else {
                TextButton(
                    onClick = {
                        charactersViewModel
                            .addToFavorites(character.uid)
                            .invokeOnCompletion {
                                if (it is CancellationException) {
                                    Toast.makeText(
                                        context,
                                        "Unable to add to favorites! Check your internet connection!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_empty),
                        contentDescription = "Add to favorites",
                        modifier = Modifier
                            .height(32.dp)
                            .width(32.dp)
                    )
                }
            }
        }
    }
}
