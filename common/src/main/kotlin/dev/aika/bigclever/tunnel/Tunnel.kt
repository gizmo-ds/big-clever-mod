package dev.aika.bigclever.tunnel

import net.minecraft.client.network.ServerAddress

interface Tunnel {
    val isAvailable: Boolean
    fun stop()
    fun tunnelType(): String
    fun startClient(host: String, port: Int, mcPortOnly: Boolean)
    val serverAddress: ServerAddress?
}