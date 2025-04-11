package com.example.githubpoc.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.githubpoc.R
import com.example.githubpoc.model.RepositoriesResponse
import com.example.githubpoc.model.RepositoryItem
import com.example.githubpoc.ui.ComposeToolbar
import com.example.githubpoc.ui.DropdownMenuButton
import com.example.githubpoc.viewmodel.LanguageType
import com.example.githubpoc.viewmodel.RepositoriesViewModel
import com.example.githubpoc.viewmodel.SearchType

@Composable
fun RepositoryListScreen(
    navController: NavController,
    viewModel: RepositoriesViewModel
) {
    val repositoriesState = viewModel.repositoriesState.collectAsState().value
    val selectedSearchType = remember { mutableStateOf(SearchType.TOP_10_POPULAR.value) }
    val languageType = remember { mutableStateOf(LanguageType.KOTLIN.value) }

    Scaffold(
        topBar = {
            ComposeToolbar("Repository List", true)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                DropdownMenuButton(viewModel.searchType) { selected ->
                    selectedSearchType.value = selected
                }
                if (selectedSearchType.value == SearchType.LANGUAGE.value) {
                    DropdownMenuButton(viewModel.languageType) { selected ->
                        languageType.value = selected
                    }
                }
                Button(
                    onClick = {
                        viewModel.fetchRepositories(selectedSearchType.value, languageType.value)
                    }
                ) {
                    Text("Search")
                }
            }
            when {
                !repositoriesState.errorMessage.isNullOrEmpty() -> Text(
                    repositoriesState.errorMessage,
                    modifier = Modifier.padding(16.dp)
                )

                repositoriesState.data != null -> LazyListView(repositoriesState.data) { repo ->
                    navController.navigate("repository_screen")
                    viewModel.onItemClicked(repo)
                }

                else -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

    LaunchedEffect(Unit) { viewModel.init() }
}

@Composable
fun LazyListView(
    repositoriesResponse: RepositoriesResponse,
    itemClickListener: (repo: RepositoryItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        repositoriesResponse.items?.let { items ->
            itemsIndexed(items) { index, item ->
                ListItem(item, itemClickListener)

                if (index < items.size - 1) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
fun ListItem(repositoryItem: RepositoryItem, itemClickListener: (repo: RepositoryItem) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .clickable {
                itemClickListener.invoke(repositoryItem)
            }
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.weight(3f),
                text = repositoryItem.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = "star"
                )
                Text(
                    text = repositoryItem.starCount.toString(),
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = repositoryItem.description ?: "",
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}
