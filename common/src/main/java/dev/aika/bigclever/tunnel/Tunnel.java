package dev.aika.bigclever.tunnel;

import net.minecraft.client.network.ServerAddress;

public interface Tunnel {
    boolean isAvailable();

    void stop();

    String tunnelType();

    void start(String host, int port, boolean mcPortOnly);

    ServerAddress getServerAddress();
}
