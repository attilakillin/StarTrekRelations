package hu.attilakillin.startrekrelations.ui.screen.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.attilakillin.startrekrelations.network.CharacterBase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    charactersViewModel: CharactersViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val characters by charactersViewModel.characters.collectAsState(listOf())

    val focusManager = LocalFocusManager.current

    Column {
        CenterAlignedTopAppBar(
            title = { Text("Karakterek") }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1.0f)
                        .height(56.dp)
                )

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        charactersViewModel.searchCharacters(searchQuery)
                    },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Search")
                }
            }

            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(characters) { char ->
                    CharacterCard(character = char)
                }
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: CharacterBase
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
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
