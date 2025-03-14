package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.data

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.PhraseServiceable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.SearchPhrase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.SearchPhraseDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhrasesUseCase @Inject constructor(private val phraseServiceable: PhraseServiceable) {

    operator fun invoke(url: String): Flow<Result<List<SearchPhraseDto>>> {
        return phraseServiceable.getPhrases(url)
    }
}
