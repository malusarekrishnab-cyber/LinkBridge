package com.linkbridge.app.command

import com.linkbridge.app.connection.ConnectionManager

class CommandManager(private val connectionManager: ConnectionManager) {

    fun sendCommand(commandName: String) {
        val payload = """{"version":1,"type":"command","action":"execute","payload":{"command":"$commandName"}}"""
        connectionManager.sendMessage(payload)
    }
}
