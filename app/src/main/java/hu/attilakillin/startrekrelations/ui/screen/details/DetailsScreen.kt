package hu.attilakillin.startrekrelations.ui.screen.details

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.attilakillin.startrekrelations.model.Character
import hu.attilakillin.startrekrelations.ui.screen.PaddedText
import java.util.concurrent.CancellationException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    characterUid: String,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    onNavigateBackClick: () -> Unit,
    onNavigateRootClick: () -> Unit,
    onRelationClick: (String) -> Unit
) {
    val context = LocalContext.current
    val details = detailsViewModel.details.observeAsState(Character("", "Loading..."))

    /* Immediately load details when view is launched. */
    LaunchedEffect(key1 = Unit) {
        detailsViewModel
            .loadDetails(characterUid)
            .invokeOnCompletion {
                if (it is CancellationException) {
                    Toast.makeText(
                        context,
                        "Unable to load details! Check your internet connection!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /* Main view structure. */
    Column {
        /* Application header. */
        CenterAlignedTopAppBar(
            title = { Text(details.value.name) },
            navigationIcon = {
                IconButton(onClick = onNavigateBackClick) {
                    Icon(Icons.Filled.ArrowBack, "Navigate back")
                }
            },
            actions = {
                IconButton(onClick = onNavigateRootClick) {
                    Icon(Icons.Filled.Home, "Return home")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            /* Details */
            Column(
                modifier = Modifier.border(BorderStroke(2.dp, MaterialTheme.colorScheme.outline))
            ) {
                DetailPair(
                    key = "Name",
                    value = details.value.name,
                    modifier = Modifier.fillMaxWidth()
                )


                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DetailPair(
                        key = "Species",
                        value = details.value.species,
                        modifier = Modifier.weight(1.0f)
                    )

                    DetailPair(
                        key = "Gender",
                        value = details.value.gender,
                        modifier = Modifier.weight(1.0f)
                    )
                }

                DetailPair(
                    key = "Year of birth",
                    value = details.value.yearOfBirth?.toString(),
                    modifier = Modifier.fillMaxWidth()
                )

                DetailPair(
                    key = "Year of death",
                    value = details.value.yearOfDeath?.toString(),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DetailPair(
                        key = "Height",
                        value = details.value.height?.toString(),
                        modifier = Modifier.weight(1.0f)
                    )

                    DetailPair(
                        key = "Weight",
                        value = details.value.weight?.toString(),
                        modifier = Modifier.weight(1.0f)
                    )
                }
            }

            /* Relations */

            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight().padding(top = 16.dp)
            ) {
                if (details.value.relations.isEmpty()) {
                    item {
                        PaddedText(text = "No relations...")
                    }
                } else {
                    items(details.value.relations) { rel ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = rel.qualifier + " of:",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .weight(1.0f)
                            )

                            Card(
                                shape = RoundedCornerShape(4.dp),
                                onClick = {
                                    onRelationClick(rel.target.uid)
                                },
                                modifier = Modifier
                                    .weight(2.0f)
                                    .height(60.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(horizontal = 20.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = rel.target.name,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailPair(
    key: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(60.dp)
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline))
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = "$key:",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = value ?: "N/A"
        )
    }
}
