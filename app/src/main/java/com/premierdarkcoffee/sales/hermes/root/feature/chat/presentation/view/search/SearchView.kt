package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.R.drawable.arrow_back
import com.premierdarkcoffee.sales.hermes.R.drawable.delete_outline
import com.premierdarkcoffee.sales.hermes.R.drawable.map
import com.premierdarkcoffee.sales.hermes.R.string.ask_anything
import com.premierdarkcoffee.sales.hermes.R.string.delete_messages
import com.premierdarkcoffee.sales.hermes.R.string.more_options
import com.premierdarkcoffee.sales.hermes.R.string.search_within_distance
import com.premierdarkcoffee.sales.hermes.R.string.searching_within_distance
import com.premierdarkcoffee.sales.hermes.R.string.send_button
import com.premierdarkcoffee.sales.hermes.R.string.waiting_for_response
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat.ChatMessage
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.titleStyle
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component.formatDayDate
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.groupChatMessagesByDay
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component.ServerMessageView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component.TypingIndicatorView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component.UserMessageView
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper
import java.util.Date
import java.util.UUID
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    stores: Set<Store>,
    user: User,
    chatMessages: List<ChatMessage>,
    isTyping: Boolean,
    sendMessage: (String, GeoPoint, Int) -> Unit,
    onProductCardClicked: (product: String) -> Unit,
    onNavigateToStoreMarkerClicked: (String) -> Unit,
    onDeleteChatMessagesIconButtonClicked: () -> Unit,
    popBackStack: () -> Unit,
    onEditUserButtonClicked: () -> Unit
) {

    var inputText by remember { mutableStateOf("") }
    val context = LocalContext.current
    var distance by remember { mutableIntStateOf(SharedPreferencesHelper.getDistance(context)) }
    var showDistanceAdviceDialog by remember { mutableStateOf(false) }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    var showMenu by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    var doNotShowAgainChecked by remember { mutableStateOf(false) }
    val doNotShowAgainDistanceAlert: Boolean = SharedPreferencesHelper.getDoNotShowAgainDistanceAlert(context)

    val groupedMessages: List<Pair<Date, List<ChatMessage>>> = groupChatMessagesByDay(chatMessages).toSortedMap().map { (key, value) ->
        key to value.sortedBy { it.date }
    }

    LaunchedEffect(chatMessages.size, isTyping) {
        listState.animateScrollToItem(chatMessages.size)
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = if (isTyping) searching_within_distance else search_within_distance, distance
                ), modifier = Modifier.clickable {
                    if (doNotShowAgainDistanceAlert) {
                        openBottomSheet = true
                    } else {
                        showDistanceAdviceDialog = true
                    }
                }, style = titleStyle
            )
        }, navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = ImageVector.vectorResource(id = arrow_back), contentDescription = null)
            }
        }, actions = {
            if (doNotShowAgainDistanceAlert) {
                IconButton(onClick = { openBottomSheet = true }) {
                    Icon(ImageVector.vectorResource(map), contentDescription = null)
                }
            } else {
                IconButton(onClick = { showDistanceAdviceDialog = true }) {
                    Icon(ImageVector.vectorResource(map), contentDescription = null)
                }
            }

            IconButton(onClick = { showMenu = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = stringResource(id = more_options))
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(text = { Text(stringResource(id = delete_messages)) }, onClick = {
                    showMenu = false
                    onDeleteChatMessagesIconButtonClicked()
                }, leadingIcon = {
                    Icon(imageVector = ImageVector.vectorResource(delete_outline), contentDescription = null)
                })
            }
        })
    }) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isFocused = false
                    focusManager.clearFocus()
                })
            }) {

            LazyColumn(
                state = listState, modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(4.dp), verticalArrangement = Arrangement.Bottom
            ) {
                // Empty state with search phrases
                item {
                    if (chatMessages.isEmpty()) {
                        SearchPhrasesList { searchPhrase ->
                            val geoPoint = GeoPoint(
                                type = "Point", coordinates = listOf(
                                    SharedPreferencesHelper.getLongitude(context), SharedPreferencesHelper.getLatitude(context)
                                )
                            )
                            sendMessage(searchPhrase.text, geoPoint, distance)
                        }
                    }
                }

                // Spacer
                item { Spacer(modifier = Modifier.height(12.dp)) }

                // Grouped messages by day
                groupedMessages.forEach { (day, messages) ->
                    // Day header
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.time.formatDayDate(context),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                                    .padding(horizontal = 12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Messages for the day
                    items(messages) { message ->
                        if (message.isUser) {
                            UserMessageView(message)
                        } else {
                            val productsAvailable = !message.products.isNullOrEmpty()
                            val optionalProductsAvailable = !message.optionalProducts.isNullOrEmpty()
                            val secondMessageAvailable = !message.secondMessage.isNullOrEmpty()

                            when {
                                productsAvailable && optionalProductsAvailable && secondMessageAvailable -> {
                                    // Case 1: Both products and optionalProducts are available
                                    ServerMessageView(
                                        stores = stores,
                                        user = user,
                                        message = message.secondMessage!!,
                                        products = message.optionalProducts!!,
                                        date = message.date,
                                        onProductCardClicked = onProductCardClicked,
                                        onNavigateToStoreMarkerClicked = onNavigateToStoreMarkerClicked
                                    )
                                    ServerMessageView(
                                        stores = stores,
                                        user = user,
                                        message = message.firstMessage,
                                        products = message.products!!,
                                        date = message.date,
                                        onProductCardClicked = onProductCardClicked,
                                        onNavigateToStoreMarkerClicked = onNavigateToStoreMarkerClicked
                                    )
                                }

                                productsAvailable -> {
                                    // Case 2: Only products are available
                                    ServerMessageView(
                                        stores = stores,
                                        user = user,
                                        message = message.firstMessage,
                                        products = message.products!!,
                                        date = message.date,
                                        onProductCardClicked = onProductCardClicked,
                                        onNavigateToStoreMarkerClicked = onNavigateToStoreMarkerClicked
                                    )
                                }

                                optionalProductsAvailable && secondMessageAvailable -> {
                                    // Case 3: Only optionalProducts are available
                                    ServerMessageView(
                                        stores = stores,
                                        user = user,
                                        message = message.secondMessage!!,
                                        products = message.optionalProducts!!,
                                        date = message.date,
                                        onProductCardClicked = onProductCardClicked,
                                        onNavigateToStoreMarkerClicked = onNavigateToStoreMarkerClicked
                                    )
                                }

                                else -> {
                                    // Case 4: Neither products nor optionalProducts are available
                                    ServerMessageView(
                                        stores = stores,
                                        user = user,
                                        message = message.firstMessage,
                                        products = emptyList(),
                                        date = message.date,
                                        onProductCardClicked = onProductCardClicked,
                                        onNavigateToStoreMarkerClicked = onNavigateToStoreMarkerClicked
                                    )
                                }
                            }
                        }
                    }
                }

                // Typing indicator
                if (isTyping) {
                    item { TypingIndicatorView() }
                }
            }


            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .onFocusChanged { isFocused = it.isFocused },
                    placeholder = {
                        Text(
                            text = if (isTyping) stringResource(id = waiting_for_response) else stringResource(id = ask_anything), color = Color.Gray
                        )
                    },
                    singleLine = false,
                    maxLines = 5,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

                AnimatedVisibility(
                    visible = inputText.isNotEmpty(),
                    enter = expandIn(expandFrom = Alignment.Center) + fadeIn(),
                    exit = shrinkOut(shrinkTowards = Alignment.Center) + fadeOut()
                ) {
                    IconButton(onClick = {
                        val geoPoint = GeoPoint(
                            type = "Point", coordinates = listOf(
                                SharedPreferencesHelper.getLongitude(context), SharedPreferencesHelper.getLatitude(context)
                            )
                        )
                        sendMessage(inputText, geoPoint, distance)
                        inputText = ""
                        isFocused = false
                        focusManager.clearFocus()
                    }, modifier = Modifier.size(48.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.Send,
                            contentDescription = stringResource(id = send_button),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }

    val distanceAdviceTitle = stringResource(id = R.string.distance_advice_title)
    val doNotShowAgain = stringResource(id = R.string.do_not_show_again)

    if (!doNotShowAgainDistanceAlert && showDistanceAdviceDialog) {
        AlertDialog(onDismissRequest = { showDistanceAdviceDialog = false }, title = {
            Text(text = stringResource(id = R.string.distance_advice_title),
                 style = MaterialTheme.typography.titleMedium,
                 modifier = Modifier.semantics { contentDescription = distanceAdviceTitle })
        }, text = {
            Column {
                Text(
                    text = stringResource(id = R.string.distance_advice_description),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = doNotShowAgainChecked, onCheckedChange = { checked ->
                        doNotShowAgainChecked = checked
                        SharedPreferencesHelper.setDoNotShowAgainDistanceAlert(context, checked)
                    }, modifier = Modifier.semantics { contentDescription = doNotShowAgain })
                    Text(
                        text = stringResource(id = R.string.do_not_show_again),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }, confirmButton = {
            Button(onClick = {
                showDistanceAdviceDialog = false
                openBottomSheet = true
            }) {
                Text(
                    text = stringResource(id = R.string.understood), style = MaterialTheme.typography.bodyMedium
                )
            }
        }, dismissButton = {
            Button(onClick = { showDistanceAdviceDialog = false }) {
                Text(
                    text = stringResource(id = R.string.cancel), style = MaterialTheme.typography.bodyMedium
                )
            }
        })
    }


    if (openBottomSheet) {
        ModalBottomSheet(onDismissRequest = { openBottomSheet = false }, sheetState = bottomSheetState) {
            SearchDistanceMapView(
                userLocation = user.location, distance = distance, onDistanceChange = {
                    distance = it
                    SharedPreferencesHelper.setDistance(context, distance)
                }, onEditUserButtonClicked
            )
        }
    }
}

data class SearchPhrase(
    val id: UUID = UUID.randomUUID(),
    val icon: ImageVector,
    val text: String
)

@Composable
fun SearchPhrasesList(onPhraseClick: (SearchPhrase) -> Unit) {
    val searchPhrases = listOf(
        SearchPhrase(icon = ImageVector.vectorResource(map), text = "I need a new MacBook Pro"),
        SearchPhrase(icon = ImageVector.vectorResource(map), text = "What are the latest iPads?"),
        SearchPhrase(icon = ImageVector.vectorResource(map), text = "Show me the best deals on iPhones"),
        SearchPhrase(icon = ImageVector.vectorResource(map), text = "Do you have Samsung 4K TVs?")
    ).shuffled(Random(System.currentTimeMillis()))

    if (searchPhrases.isNotEmpty()) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            searchPhrases.forEach { phrase ->
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable { onPhraseClick(phrase) }
                        .padding(16.dp)
                        .semantics { contentDescription = phrase.text },
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        imageVector = phrase.icon,
                        contentDescription = phrase.text,
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = phrase.text,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}
