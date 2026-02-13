package fr.sdv.alan.movieapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*DataStore utilisé ici pour stocker les ID des films favoris tout en gardant
le projet léger et simple sans Room
N.B: Problèmes de compatibilité entre Android Gradle Plugin, le built-in de Kotlin et KSP
qui générait des erreurs liées aux sources Kotlin internes
 */
private val Context.dataStore by preferencesDataStore(name = "favorites_store")

class FavoritesStore(private val context: Context) {

    private val keyFavorites = stringSetPreferencesKey("favorite_ids")

    //Flow pour accéder aux ID des films favoris
    val favoriteIds: Flow<Set<String>> =
        context.dataStore.data.map { prefs -> prefs[keyFavorites].orEmpty() }

    //toggle pour ajouter ou retirer un film des favoris
    suspend fun toggleFavorite(movieId: Int) {
        val id = movieId.toString()
        context.dataStore.edit { prefs ->
            val current = prefs[keyFavorites].orEmpty().toMutableSet()
            if (current.contains(id)) current.remove(id) else current.add(id)
            prefs[keyFavorites] = current
        }
    }

    val favoriteIdsInt: Flow<List<Int>> =
        favoriteIds.map { set ->
            set.mapNotNull { it.toIntOrNull() }.sorted()
        }
}
