package com.example.newsapp.posts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.core.presentation.ShimmerPlaceholder
import com.example.newsapp.posts.domain.models.PostSimple
import com.example.newsapp.posts.presentation.HomeAction.GoToDetail
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeScreenRoot(
    goToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is GoToDetail -> goToDetail(action.id)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.status) {
            HomeStatus.ERROR -> {
                HomeScreenError(onAction)
            }
            else -> { //HomeStatus.LOADING, HomeStatus.SUCCESS
                HomeScreenLoadingAndSuccess(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun HomeScreenLoadingAndSuccess(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    if (state.status == HomeStatus.SUCCESS) {
        SearBar(
            query = state.searching,
            onQueryChange = { onAction(HomeAction.UpdateSearchingValue(it)) },
            onSearch = { onAction(HomeAction.SearchingValue) },
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        itemsIndexed(
            items = state.filteredPosts,
            key = { _, item -> item.id }
        ) { index, post ->
            PostItem(
                post = post,
                isLoading = state.status == HomeStatus.LOADING,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
                    .clickable {
                        if (state.status == HomeStatus.SUCCESS) {
                            onAction(GoToDetail(post.id))
                        }
                    }
            )
            if (index < state.filteredPosts.size - 1) {
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun SearBar(
    query: String?,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = query ?: "",
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        placeholder = {
            Text(text = stringResource(id = R.string.news_search))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (!query.isNullOrBlank()) {
                IconButton(onClick = {
                    onQueryChange("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search"
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                // Optionally, hide the keyboard here
            }
        )
    )
}

@Composable
private fun HomeScreenError(onAction: (HomeAction) -> Unit) {
    Text(
        text = stringResource(id = R.string.news_error),
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.onSurface,
        style = TextStyle(
            fontWeight = FontWeight.Bold
        )
    )
    Spacer(modifier = Modifier.height(5.dp))
    Button(
        onClick = {
            onAction(HomeAction.RetryInitialPosts)
        }
    ) {
        Text(
            text = stringResource(id = R.string.news_retry),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun PostItem(
    post: PostSimple,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(5.dp)
    ShimmerPlaceholder(
        shape = shape,
        modifier = modifier,
        isLoading = isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = shape,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = shape
                )
                .padding(5.dp)
        ) {
            Text(
                text = post.title,
                maxLines = 2,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = post.content,
                maxLines = 5,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = TextStyle(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreviewLoading() {
    NewsAppTheme {
        HomeScreen(
            state = HomeState(
                filteredPosts = (1..10).map {
                    PostSimple(
                        id = it,
                        title = "This is the title $it",
                        content = "This is the content $it"
                    )
                }
            ),
            onAction = {}
        )
    }
}

@Composable
@Preview
fun HomeScreenPreviewError() {
    NewsAppTheme {
        HomeScreen(
            state = HomeState(
                filteredPosts = (1..10).map {
                    PostSimple(
                        id = it,
                        title = "This is the title $it",
                        content = "This is the content $it"
                    )
                },
                status = HomeStatus.ERROR
            ),
            onAction = {}
        )
    }
}