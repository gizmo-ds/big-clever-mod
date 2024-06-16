package dev.aika.bigclever.utils

import dev.aika.bigclever.BigClever
import java.io.IOException
import java.net.*

object NetworkUtils {
    fun findFreePort(): Int {
        try {
            ServerSocket(0).use { socket ->
                return socket.localPort
            }
        } catch (e: Exception) {
            throw RuntimeException("Unable to find a free port", e)
        }
    }

    fun isConnectable(host: String?, port: Int, totalTimeout: Int): Boolean {
        val startTime = System.currentTimeMillis()
        val connectionTimeout = 1000

        val address: SocketAddress = InetSocketAddress(host, port)
        while (true) {
            try {
                Socket().use { socket ->
                    socket.connect(address, connectionTimeout)
                    BigClever.LOGGER.debug("Successfully connected to {}:{}", host, port)
                    return true
                }
            } catch (e: SocketTimeoutException) {
                if (System.currentTimeMillis() - startTime > totalTimeout) {
                    BigClever.LOGGER.error("Total timeout exceeded for {}:{}", host, port)
                    return false
                }
            } catch (e: ConnectException) {
                if (System.currentTimeMillis() - startTime > totalTimeout) {
                    BigClever.LOGGER.error("Total timeout exceeded for {}:{}", host, port)
                    return false
                }
            } catch (e: IOException) {
                BigClever.LOGGER.error("I/O error occurred when trying to connect to {}:{}: {}", host, port, e.message)
                return false
            }
        }
    }
}