package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.request.ClientProductRequest
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.response.ClientProductResponse
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat.ChatMessage
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.state.ChatGPTMessageState
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.ai.SendMessageToAIUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.cart.DeleteCartProductUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.cart.InsertProductToCartUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.cart.ReadProductsFromCartUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.chat.DeleteChatMessagesUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.chat.InsertChatMessageUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.chat.ReadChatGPTMessagesUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.message.FetchMessagesUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.message.MarkMessageAsReadUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store.DeleteStoreByIdUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store.GetAllStoresUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store.GetStoreByIdUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store.InsertStoreUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store.SendMessageToStoreUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store.UpdateStoreUseCase
import com.premierdarkcoffee.sales.hermes.root.util.function.getUrlFor
import com.premierdarkcoffee.sales.hermes.root.util.key.getClientKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(application: Application,
        //
                                        private val sendMessageToAIUseCase: SendMessageToAIUseCase,
                                        private val sendMessageToStoreUseCase: SendMessageToStoreUseCase,
                                        private val fetchMessagesUseCase: FetchMessagesUseCase,
//    private val fetchLocalMessagesUseCase: FetchLocalMessagesUseCase,
                                        private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,

        //
                                        private val getStoreByIdUseCase: GetStoreByIdUseCase,
        //Cart
                                        private val insertProductToCartUseCase: InsertProductToCartUseCase,
                                        private val readProductsFromCartUseCase: ReadProductsFromCartUseCase,
                                        private val deleteCartProductUseCase: DeleteCartProductUseCase,
        //Chat
                                        private val insertChatMessageUseCase: InsertChatMessageUseCase,
                                        private val readChatGPTMessagesUseCase: ReadChatGPTMessagesUseCase,
                                        private val deleteChatMessagesUseCase: DeleteChatMessagesUseCase,
        //Store
                                        private val insertStoreUseCase: InsertStoreUseCase,
                                        private val updateStoreUseCase: UpdateStoreUseCase,
                                        private val getAllStoresUseCase: GetAllStoresUseCase,
                                        private val deleteStoreByIdUseCase: DeleteStoreByIdUseCase) :
    AndroidViewModel(application) {

    // Retrieve client-specific key for API requests
    private val clientKey: String = getClientKey(getApplication<Application>().applicationContext)

    // User ID fetched from Firebase Authentication (null if not logged in)
    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid

    // StateFlows to manage chat messages, typing indicator, store messages, etc.
    private val _gptMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val gptMessages: StateFlow<List<ChatMessage>> get() = _gptMessages

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> get() = _isTyping

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    // Mutex and Set to handle store IDs that have been processed
    private val seenStoreIds = mutableSetOf<String>()
    private val mutex = Mutex()

    private val _stores = MutableStateFlow<Set<Store>>(emptySet())
    val stores: StateFlow<Set<Store>> get() = _stores

    private val _cart = MutableStateFlow<List<Product>>(emptyList())
    val cart: StateFlow<List<Product>> get() = _cart

    private val _chatGPTMessageState = MutableStateFlow(ChatGPTMessageState())
    val chatGPTMessageState: StateFlow<ChatGPTMessageState> get() = _chatGPTMessageState

    init {
//        getLocalMessages()
        // Initialize ViewModel by loading initial data
        initializeData()
    }

    /**
     * Initializes data by loading client messages, user data, cart products, and chat messages.
     */
    private fun initializeData() {
        viewModelScope.launch {
//            readProductsFromCart()
            getMessages()
            readChatGPTMessages()
            readStores()
        }
    }

    /**
     * Sends a message to the AI and updates the chat messages with both user and server responses.
     *
     * @param inputText The text message input by the user.
     * @param distance The distance parameter for location-based queries.
     */
    fun sendMessage(inputText: String, geoPoint: GeoPoint, distance: Int) {
        val clientMessage =
            ChatMessage(isUser = true, firstMessage = inputText, products = null, secondMessage = null, optionalProducts = null)
        _isTyping.value = true

        viewModelScope.launch {
            try {
                addChatMessage(clientMessage)
                val responseResult = withContext(Dispatchers.IO) {
                    delay(1000) // Simulated network delay
                    val request = ClientProductRequest(key = clientKey,
                                                       query = inputText,
                                                       clientId = userId ?: "",
                                                       location = geoPoint.toGeoPointDto(),
                                                       distance = distance)
                    sendMessageToAIUseCase.invoke(getUrlFor(endpoint = "hermes"), request = request).toList().first()
                }

                handleAIResponse(responseResult)

            } catch (e: Exception) {
                handleError(e, isTyping = true)
            } finally {
                _isTyping.value = false
            }
        }
    }

    /**
     * Handles the AI response by updating the chat with the response message or an error message.
     *
     * @param responseResult The result of the AI response, which could be success or failure.
     */
    private suspend fun handleAIResponse(responseResult: Result<ClientProductResponse>) {
        withContext(Dispatchers.Main) {
            try {
                responseResult.onSuccess { response ->
                    val serverMessage = ChatMessage(isUser = false,
                                                    firstMessage = response.firstMessage,
                                                    products = response.products?.map { it.toProductInformation() },
                                                    secondMessage = response.secondMessage,
                                                    optionalProducts = response.optionalProducts?.map { it.toProductInformation() })
                    addChatMessage(serverMessage)
                }.onFailure { exception ->
                    val errorMessage = ChatMessage(isUser = false,
                                                   firstMessage = exception.message.orEmpty(),
                                                   products = null,
                                                   secondMessage = null,
                                                   optionalProducts = null)
                    addChatMessage(errorMessage)
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur while processing the response
                val errorMessage = ChatMessage(isUser = false,
                                               firstMessage = "An error occurred: ${e.message}",
                                               products = null,
                                               secondMessage = null,
                                               optionalProducts = null)
                addChatMessage(errorMessage)
            }
        }
    }

    /**
     * Sends a message to the store's messaging system.
     *
     * @param message The [MessageDto] object representing the message to be sent to the store.
     */
    fun sendMessageToStore(message: MessageDto) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sendMessageToStoreUseCase.invoke(message)
            }
        }
    }

    /**
     * Fetches all client messages and updates the corresponding state flow.
     */
    private fun getMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchMessagesUseCase.invoke { messages ->
                _messages.value = messages
            }
        }
    }

    fun markMessageAsReadLaunchedEffect(message: MessageEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "markMessageAsReadLaunchedEffect: Updating message with id: ${message.id}, text: ${message.text}")
                markMessageAsReadUseCase.invoke(message) {
                    Log.d(TAG, "markMessageAsReadLaunchedEffect: Updated message with id: ${message.id}, text: ${message.text}")
                }
            }
        }
    }

    /**
     * Retrieves store details for a list of store IDs.
     *
     * @param storeIds The list of store IDs to fetch details for.
     */
    fun getStores(storeIds: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Filter out store IDs that have already been processed
                val uniqueStoreIds = mutex.withLock {
                    storeIds.filterNot { seenStoreIds.contains(it) }.apply { seenStoreIds.addAll(this) }
                }

                // Launch parallel fetching for each unique store ID
                val fetchJobs = uniqueStoreIds.map { storeId ->
                    async {
                        fetchStore(storeId) { store ->
                            Log.d(TAG, "getStores: ${store.name}")
                            viewModelScope.launch {
                                insertStoreUseCase.invoke(store.toStoreEntity())
                            }
                        }
                    }
                }

                // Wait for all fetches to complete
                fetchJobs.awaitAll()

            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch stores: ${e.message}")
            } finally {
                // Once all store fetching is complete, read the updated stores from the database
                delay(1000)
                readStores()
            }
        }
    }


    /**
     * Fetches store details by store ID and invokes a completion callback with the result.
     *
     * @param storeId The ID of the store to fetch.
     * @param completion A callback to be invoked with the fetched store data.
     */
    private suspend fun fetchStore(storeId: String, completion: (Store) -> Unit) {
        try {
            getStoreByIdUseCase(getUrlFor("hermes/store", storeId = storeId)).collect { result ->
                result.onSuccess { storeDto ->
                    Log.d(TAG, "fetchStore: ${storeDto.name}")
                    completion(storeDto.toStore())
                }.onFailure { exception ->
                    Log.e(TAG, "Failed to fetch store: $exception")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in fetchStore: $e")
        }
    }

    private fun readStores() {
        viewModelScope.launch {
            val storeEntities = getAllStoresUseCase.invoke()
            Log.d(TAG, "readStores: ${storeEntities.map { it.name }}")
            _stores.value = storeEntities.map { it.toStore() }.toSet()
        }
    }

    /**
     * Adds a product to the user's cart.
     *
     * @param product The [Product] object representing the product to be added.
     */
    fun insertCartProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertProductToCartUseCase.invoke(product.toProductEntity())
                readProductsFromCart()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to add product to cart: $e")
            }
        }
    }

    /**
     * Reads all products from the user's cart and updates the cart state flow.
     */
    fun readProductsFromCart() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val products = readProductsFromCartUseCase.invoke().map { it.product.toProduct() }
                _cart.value = products
            } catch (e: Exception) {
                Log.e(TAG, "Failed to read products from cart: $e")
            }
        }
    }

    /**
     * Deletes a product from the cart based on the product ID.
     *
     * @param id The ID of the product to be deleted from the cart.
     */
    fun deleteCartProduct(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteCartProductUseCase.invoke(id)
                readProductsFromCart()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to delete product from cart: $e")
            }
        }
    }

    /**
     * Handles errors by logging the error message and updating the chat state if necessary.
     *
     * @param e The exception to be handled.
     * @param isTyping A boolean indicating whether the typing state should be updated.
     */
    private fun handleError(exception: Exception, isTyping: Boolean = false) {
        Log.e(TAG, "Error: ${exception.message}")
        _isTyping.value = isTyping
        val errorMessage = ChatMessage(isUser = false,
                                       firstMessage = exception.message.orEmpty(),
                                       products = null,
                                       secondMessage = null,
                                       optionalProducts = null)
        _gptMessages.value += errorMessage
        addChatMessage(errorMessage)
    }

    /**
     * Adds a chat message to the local database and reloads the chat messages.
     *
     * @param message The [ChatMessage] to be added to the chat.
     */
    private fun addChatMessage(message: ChatMessage) {
        viewModelScope.launch {
            try {
                insertChatMessageUseCase.invoke(message.toChatMessageEntity())
                readChatGPTMessages()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to add chat message: $e")
            }
        }
    }

    /**
     * Reads all chat messages from the local database and updates the chat message state.
     */
    private fun readChatGPTMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch all chat messages from the DB
                val chatMessages = readChatGPTMessagesUseCase.invoke()

                // Update the chat state and trigger the UI updates after retrieval
                _chatGPTMessageState.value = ChatGPTMessageState(messages = chatMessages)

                // Now that the messages are successfully retrieved, update _gptMessages
                _gptMessages.value = chatMessages.map { it.toChatMessage() }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to read chat messages: $e")
                _chatGPTMessageState.value = ChatGPTMessageState(null)
            }
        }
    }

    /**
     * Deletes all chat messages from the local database and reloads the chat messages.
     */
    fun deleteChatMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteChatMessagesUseCase.invoke()
                readChatGPTMessages()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to delete chat messages: $e")
            }
        }
    }
}
