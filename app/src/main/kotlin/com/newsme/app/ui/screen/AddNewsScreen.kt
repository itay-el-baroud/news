package com.newsme.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newsme.app.ui.viewmodel.NewsViewModel

@Composable
fun AddNewsScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onNewsAdded: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Publish News",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("News Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("News Details") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 16.dp),
            maxLines = 5
        )

        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Add Link (Optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isPrivate,
                onCheckedChange = { isPrivate = it }
            )
            Text("Private")
        }

        Button(
            onClick = { viewModel.addNews(title, content, link, isPrivate) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = title.isNotEmpty() && content.isNotEmpty() && !isLoading
        ) {
            if (isLoading) CircularProgressIndicator() else Text("Publish")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.newsAddedEvent.collect {
            if (it) onNewsAdded()
        }
    }
}
