package dev.aika.bigclever.interfaces;

import dev.aika.bigclever.tunnel.Tunnel;

public interface ClientConnection {
    void setTunnel(Tunnel runningTunnel);
    Tunnel getTunnel();
}
