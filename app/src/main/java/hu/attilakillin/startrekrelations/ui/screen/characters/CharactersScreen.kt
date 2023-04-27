package hu.attilakillin.startrekrelations.ui.screen.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.attilakillin.startrekrelations.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    charactersViewModel: CharactersViewModel = viewModel()
) {
    /* Character list - the main state displayed on the screen. */
    val favorites = charactersViewModel.favorites.observeAsState()
    val characters = charactersViewModel.characters.observeAsState()

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
                item {
                    Text(text = "Favorites")
                }

                if (favorites.value.isNullOrEmpty()) {
                    item { PaddedText(text = "No favorites yet...") }
                } else {
                    items(favorites.value ?: listOf()) {
                        CharacterCard(character = it)
                    }
                }

                item {
                    Text(text = "Results")
                }

                if (characters.value?.content.isNullOrEmpty()) {
                    item { PaddedText(text = "No results...") }
                } else {
                    items(characters.value?.content ?: listOf()) {
                        CharacterCard(character = it)
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

@Composable
fun CharacterCard(
    character: Character
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .height(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = character.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1.0f)
            )

            Text("F")
        }
    }
}

@Composable
fun PaddedText(
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Text(text = text, fontStyle = FontStyle.Italic)
    }
}
