package dev.aika.bigclever;

import dev.aika.bigclever.config.BigCleverConfig;
import dev.aika.bigclever.tunnel.TunnelManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class BigClever {
    public static final String MOD_ID = "bigclever";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
    public static final TunnelManager TUNNEL_MANAGER = new TunnelManager();

    public static void init() {
        AutoConfig.register(BigCleverConfig.class, Toml4jConfigSerializer::new);

        TUNNEL_MANAGER.checkTunnels();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            TUNNEL_MANAGER.closeAll();
            THREAD_POOL.shutdownNow();
        }));
    }
}
