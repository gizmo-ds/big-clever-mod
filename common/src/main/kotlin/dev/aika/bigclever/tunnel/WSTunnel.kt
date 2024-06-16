package dev.aika.bigclever.tunnel

import dev.aika.bigclever.BigClever
import dev.aika.bigclever.utils.NetworkUtils
import net.minecraft.client.network.ServerAddress
import org.lwjgl.system.Platform

class WSTunnel(private val listens: Array<String?>) :
    ExecTunnel(TUNNEL_TYPE, "wstunnel" + (if (Platform.get() === Platform.WINDOWS) ".exe" else "")),
    Tunnel {
    private var freePort = 0

    override fun tunnelType(): String {
        return TUNNEL_TYPE
    }

    override fun startClient(host: String, port: Int, mcPortOnly: Boolean) {
        freePort = NetworkUtils.findFreePort()

        val command: MutableList<String> = ArrayList()
        command.add("client")
        for (l in listens) {
            if (mcPortOnly && !l!!.contains(PORT_PLACEHOLDER)) continue
            command.add("-L")
            command.add(l!!.replace(PORT_PLACEHOLDER.toRegex(), freePort.toString()))
        }

        if (port == 25565) command.add(String.format("wss://%s", host))
        else command.add(String.format("ws://%s:%d", host, port))

        super.start(command)
    }

    override val serverAddress: ServerAddress?
        get() {
            if (NetworkUtils.isConnectable(LOCALHOST, freePort, 1000 * 5)) return ServerAddress(
                LOCALHOST, freePort
            )
            return null
        }

    companion object {
        const val TUNNEL_TYPE: String = "wstunnel"
        private const val PORT_PLACEHOLDER = "-mc_port-"
        private const val LOCALHOST = "127.0.0.1"

        private const val VERSION = "9.7.0"
        private val CHECK_SUMS: Map<String, String> = java.util.Map.of(
            "windows_amd64", "da8ac111316b887871a16e3a91ea0277d691d9d6c25f6e8ec3fa6ddea9e8e43b",
            "darwin_arm64", "ef8bfe18f11672a135402eec34f2ebfb16dd5d1d6a429a2f8e9b180d9b5c62d4",
            "linux_armv7", "a162e230ad1cc2e86f4ed1e2b54330f2472844f24ba0b8ffe33d9208bfb8fe43",
            "linux_arm64", "2c2cc1a107b04b6a2dec7897de8a925598e92a492d5aa4e4a10caddeb0d9f7f5",
            "linux_amd64", "11b512bc516c967ea1767356a39a7ea5f3510175fef703e8dad60876ceed5f94"
        )

        fun fromRecord(record: String): WSTunnel {
            return WSTunnel(parseRecord(record))
        }

        private fun parseRecord(record: String): Array<String?> {
            val tunnelKey = java.lang.String.format("%s.%s=", BigClever.MOD_ID, TUNNEL_TYPE)
            if (!record.startsWith(tunnelKey)) return arrayOfNulls(0)
            return record.substring(tunnelKey.length).split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
    }
}