package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchPhraseDto(val id: String, val iOSIcon: String, val androidIcon: String, val text: String) {

    fun toSearchPhrase(): SearchPhrase {
        return SearchPhrase(id, iOSIcon, androidIcon, text)
    }
}
