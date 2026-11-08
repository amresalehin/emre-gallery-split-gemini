package com.amresalehin.emregallery.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import androidx.compose.ui.graphics.vector.ImageVector
import com.amresalehin.emregallery.face.BuffaloLFaceEngine
import com.amresalehin.emregallery.face.FaceClusterStore
import com.amresalehin.emregallery.ocr.TesseractOcr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SearchEngine(internal val context: Context) {

    internal val faceStore = FaceClusterStore(context.applicationContext)
    internal val locationCache = PhotoMapLocationCache(context.applicationContext)

    suspend fun getDiscoveryContent(
        allMedia: List<MediaItem>,
        customAlbums: List<CustomAlbum>,
        customTagsMap: Map<String, Set<String>>,
        customPeopleMap: Map<String, Set<String>>,
        favourites: Set<String>
    ): DiscoveryData = getDiscoveryContentImpl(allMedia, customAlbums, customTagsMap, customPeopleMap, favourites)

    suspend fun getLiveSuggestions(
        query: String,
        media: List<MediaItem>,
        recentSearches: List<String> = emptyList(),
        customAlbums: List<CustomAlbum> = emptyList(),
        customTagsMap: Map<String, Set<String>> = emptyMap(),
        customPeopleMap: Map<String, Set<String>> = emptyMap()
    ): List<SearchSuggestion> = getLiveSuggestionsImpl(query, media, recentSearches, customAlbums, customTagsMap, customPeopleMap)

    suspend fun searchMedia(
        query: String,
        media: List<MediaItem>,
        filter: SearchFilterState,
        sortMode: SearchSortMode,
        customAlbums: List<CustomAlbum> = emptyList(),
        customTagsMap: Map<String, Set<String>> = emptyMap(),
        customPeopleMap: Map<String, Set<String>> = emptyMap(),
        favourites: Set<String> = emptySet()
    ): SearchResultData = searchMediaImpl(query, media, filter, sortMode, customAlbums, customTagsMap, customPeopleMap, favourites)

    companion object {
        const val MAX_SUGGESTIONS_PER_CATEGORY = 6
        const val MAX_RECENT_SEARCHES = 10
    }
}
