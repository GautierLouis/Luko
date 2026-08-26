package xyz.luko.dictionary.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.dictionary.details.CharacterDetailsSheet
import xyz.luko.dictionary.home.DictionaryScreen
import xyz.luko.ui.navigation.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
fun EntryProviderScope<NavKey>.dictionaryRoutes() {
    entry<AppRoute.Dictionary.Home> { DictionaryScreen() }
    entry<AppRoute.Dictionary.Detail>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
        CharacterDetailsSheet(it)
    }
}




