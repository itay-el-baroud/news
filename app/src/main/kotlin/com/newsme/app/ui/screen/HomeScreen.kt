package com.newsme.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newsme.app.data.model.News
import com.newsme.app.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onNewsClick: (Int) -> Unit,
    onAddNewsClick: () -> Unit
) {
    val newsList by viewModel.newsList.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    LaunchedEffect(Unit) {
        viewModel.fetchNews()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Me") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = rememberVectorPainter(
                                image = androidx.compose.material.icons.Icons.Default.Search
                            ),
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            painter = rememberVectorPainter(
                                image = androidx.compose.material.icons.Icons.Default.Notifications
                            ),
                            contentDescription = "Notifications"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewsClick) {
                Icon(
                    painter = rememberVectorPainter(
                        image = androidx.compose.material.icons.Icons.Default.Add
                    ),
                    contentDescription = "Add News"
                )
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(newsList) { news ->
                    NewsCard(news = news, onClick = { onNewsClick(news.id) })
                }
            }
        }
    }
}

@Composable
fun NewsCard(news: News, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.authorName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun rememberVectorPainter(image: androidx.compose.material.icons.Icons.Default) =
    androidx.compose.material.icons.rememberVectorPainter(image = image)
