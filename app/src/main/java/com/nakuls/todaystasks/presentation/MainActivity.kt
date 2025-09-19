package com.nakuls.todaystasks.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nakuls.todaystasks.presentation.Navigation.NavGraph
import com.nakuls.todaystasks.presentation.task.PhoneTasksViewModel
import com.nakuls.todaystasks.presentation.theme.TodaysTasksTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: PhoneTasksViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodaysTasksTheme {
                NavGraph()
            }
        }
        Log.d("Weather-Debug", "ViewModel: ${viewModel.hashCode()}")
    }
}

