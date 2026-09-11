package com.linkbridge.app.remote

import com.linkbridge.app.connection.ConnectionManager

class RemoteInputManager(private val connectionManager: ConnectionManager) {

    fun sendMouseMove(dx: Int, dy: Int) {
        val payload = """{"version":1,"type":"input","action":"mouse_move","payload":{"dx":$dx,"dy":$dy}}"""
        connectionManager.sendMessage(payload)
    }

    fun sendMouseClick(button: String) {
        val payload = """{"version":1,"type":"input","action":"mouse_click","payload":{"button":"$button"}}"""
        connectionManager.sendMessage(payload)
    }

    fun sendScroll(delta: Int) {
        val payload = """{"version":1,"type":"input","action":"scroll","payload":{"delta":$delta}}"""
        connectionManager.sendMessage(payload)
    }

    fun sendKeyPress(keyCode: Int) {
        val payload = """{"version":1,"type":"input","action":"key_press","payload":{"keyCode":$keyCode}}"""
        connectionManager.sendMessage(payload)
    }
}
