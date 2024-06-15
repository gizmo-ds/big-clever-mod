package dev.aika.bigclever.tunnel;


import dev.aika.bigclever.config.BigCleverConfig;
import dev.aika.bigclever.utils.DNSQuery;

import java.util.ArrayList;
import java.util.List;

public class TunnelManager {
    private final List<Tunnel> activeTunnels = new ArrayList<>();
    private BigCleverConfig config;

    private boolean wstunnelWaitForDownload = false;

    public void checkTunnels() {
        config = BigCleverConfig.get();

        wstunnelWaitForDownload = config.wstunnelEnable && !new WSTunnel(null).isAvailable();
    }

    public Tunnel useTunnel(String host, int port, boolean mcPortOnly) {
        if (!DNSQuery.isSuitableForDNSQuery(host)) return null;
        for (var record : DNSQuery.queryTXTRecords(host)) {
            if (wstunnelWaitForDownload || !config.wstunnelEnable) continue;

            Tunnel tunnel = WSTunnel.fromRecord(record);
            if (tunnel.isAvailable()) {
                tunnel.start(host, port, mcPortOnly);
                if (tunnel.getServerAddress() == null) {
                    tunnel.stop();
                    continue;
                }
                activeTunnels.add(tunnel);
                return tunnel;
            }
        }
        return null;
    }

    public void closeAll() {
        for (var tunnel : activeTunnels) tunnel.stop();
    }
}
