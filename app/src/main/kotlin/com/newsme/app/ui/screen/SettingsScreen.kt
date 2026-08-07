package com.newsme.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Arabic") }
    var muteNotifications by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )

        SettingItem(
            title = "Dark Mode",
            subtitle = if (darkMode) "On" else "Off"
        )
        Switch(
            checked = darkMode,
            onCheckedChange = { darkMode = it },
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        SettingItem(
            title = "Language",
            subtitle = selectedLanguage
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Arabic")
                Text("English", style = MaterialTheme.typography.bodySmall)
            }
        }

        Divider()

        SettingItem(
            title = "Mute Notifications",
            subtitle = if (muteNotifications) "On" else "Off"
        )
        Switch(
            checked = muteNotifications,
            onCheckedChange = { muteNotifications = it },
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        SettingItem(title = "Account Settings", subtitle = "")

        Divider()

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Logout")
        }
    }
}

@Composable
fun SettingItem(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        if (subtitle.isNotEmpty()) {
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}
