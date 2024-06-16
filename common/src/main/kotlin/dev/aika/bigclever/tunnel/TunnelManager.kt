package dev.aika.bigclever.tunnel

import dev.aika.bigclever.BigClever
import dev.aika.bigclever.config.BigCleverConfig.Companion.config
import dev.aika.bigclever.utils.DNSQuery

class TunnelManager {
    private val activeTunnels: MutableList<Tunnel> = ArrayList()

    private var wstunnelWaitForDownload = false

    fun checkTunnels(): Boolean {
        wstunnelWaitForDownload = config.wstunnelEnable && !WSTunnel(arrayOf()).isAvailable
        if (wstunnelWaitForDownload) BigClever.LOGGER.warn("wstunnel is not available, waiting for download...")

        return wstunnelWaitForDownload
    }

    fun useTunnel(host: String, port: Int, mcPortOnly: Boolean): Tunnel? {
        if (!DNSQuery.isSuitableForDNSQuery(host)) return null

        for (record in DNSQuery.queryTXTRecords(host)) {
            if (wstunnelWaitForDownload || !config.wstunnelEnable) continue

            val tunnel: WSTunnel = WSTunnel.fromRecord(record)
            if (tunnel.isAvailable) {
                tunnel.startClient(host, port, mcPortOnly)
                if (tunnel.serverAddress == null) {
                    tunnel.stop()
                    continue
                }
                activeTunnels.add(tunnel)
                return tunnel
            }
        }
        return null
    }

    fun closeAll() {
        for (tunnel in activeTunnels) tunnel.stop()
    }
}