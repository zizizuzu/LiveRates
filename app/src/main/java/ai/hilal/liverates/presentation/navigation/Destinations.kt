package ai.hilal.liverates.presentation.navigation

sealed class Destinations(val rote: String) {
    data object Home : Destinations("home")
    data object Settings : Destinations("settings")
    data object AddAsset : Destinations("add_asset")
}