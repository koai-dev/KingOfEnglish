package com.koai.kingofenglish.service

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.koai.base.main.action.event.NavigationEvent
import com.koai.kingofenglish.NewsEvent
import com.koai.kingofenglish.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

object Socket {
    var session: DefaultClientWebSocketSession? = null
    var channel: Channel<NavigationEvent>? = null
    private var lastShowNews = 0L
    init {
        val client = HttpClient {
            install(WebSockets) {
                pingInterval = 20_000
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                client.webSocket(
                    method = HttpMethod.Get,
                    host = Constants.BASE_WEBSOCKET_URL,
                    port = 80,
                    path = "/news"
                ) {
                    session = this
                    try {
                        Log.d("WEB_SOCKET: ", "Connected")
                        while (true) {
                            val othersMessage = incoming.receive() as? Frame.Text ?: continue
                            Log.d("WEB_SOCKET: ",othersMessage.readText())
                            val myMessage = othersMessage.readText()
                            if (myMessage.isNotEmpty()&& System.currentTimeMillis() - lastShowNews >= 10000) {
                                lastShowNews = System.currentTimeMillis()
                                channel?.send(NewsEvent("$myMessage "))
                            }
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}