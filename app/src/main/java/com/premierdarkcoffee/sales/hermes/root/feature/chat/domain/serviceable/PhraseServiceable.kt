package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable
//
//  DataServiceable.kt
//  Maia
//
//  Created by José Ruiz on 25/9/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.SearchPhrase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.SearchPhraseDto
import kotlinx.coroutines.flow.Flow

interface PhraseServiceable {

    fun getPhrases(url: String): Flow<Result<List<SearchPhraseDto>>>
}
