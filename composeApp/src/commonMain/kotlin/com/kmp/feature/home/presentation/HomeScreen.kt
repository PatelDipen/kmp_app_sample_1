package com.kmp.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmp.feature.auth.domain.model.UserProfile

@Composable
fun HomeScreen(user: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)
        Text("user_name: ${user.userName}", style = MaterialTheme.typography.bodyLarge)
        Text("email: ${user.email}", style = MaterialTheme.typography.bodyLarge)
    }
}

