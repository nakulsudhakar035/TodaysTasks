package com.nakuls.wearos_app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.nakuls.wearos_app.presentation.navigation.WearAppNavHost

@Composable
fun WearApp() {
    WearAppNavHost()
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun WearAppPreview() {
    WearApp()
}