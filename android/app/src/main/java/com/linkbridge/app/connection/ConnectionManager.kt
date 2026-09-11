package com.linkbridge.app.connection

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import javax.net.ssl.SSLServerSocketFactory
import javax.net.ssl.SSLSocketFactory

enum class ConnectionState {
    DISCONNECTED,
    LISTENING,
    CONNECTED,
    ERROR
}

class ConnectionManager {
    private var serverSocket: ServerSocket? = null
    private var clientSocket: Socket? = null
    private var outStream: PrintWriter? = null
    private var inStream: BufferedReader? = null

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState

    suspend fun startServer(port: Int) {
        withContext(Dispatchers.IO) {
            try {
                _connectionState.value = ConnectionState.LISTENING
                serverSocket = ServerSocket(port)
                Log.d("ConnectionManager", "Listening on port $port...")
                
                clientSocket = serverSocket?.accept()
                _connectionState.value = ConnectionState.CONNECTED
                Log.d("ConnectionManager", "Client connected: ${clientSocket?.inetAddress}")

                setupStreams(clientSocket!!)
                listenForMessages()
            } catch (e: Exception) {
                Log.e("ConnectionManager", "Server error", e)
                _connectionState.value = ConnectionState.ERROR
            }
        }
    }

    private fun setupStreams(socket: Socket) {
        outStream = PrintWriter(socket.getOutputStream(), true)
        inStream = BufferedReader(InputStreamReader(socket.getInputStream()))
    }

    private fun listenForMessages() {
        try {
            var message: String?
            while (inStream?.readLine().also { message = it } != null) {
                Log.d("ConnectionManager", "Received: $message")
                // Handle protocol message
            }
        } catch (e: Exception) {
            Log.e("ConnectionManager", "Message read error", e)
        } finally {
            disconnect()
        }
    }

    fun sendMessage(json: String) {
        outStream?.println(json)
    }

    fun disconnect() {
        try {
            inStream?.close()
            outStream?.close()
            clientSocket?.close()
            serverSocket?.close()
        } catch (e: Exception) {
            Log.e("ConnectionManager", "Error closing sockets", e)
        }
        _connectionState.value = ConnectionState.DISCONNECTED
    }
}
