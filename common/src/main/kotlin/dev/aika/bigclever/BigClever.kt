package dev.aika.bigclever

import dev.aika.bigclever.config.BigCleverConfig
import dev.aika.bigclever.tunnel.TunnelManager
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object BigClever {
    const val MOD_ID: String = "bigclever"

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    val THREAD_POOL: ExecutorService = Executors.newCachedThreadPool()
    @JvmField
    val TUNNEL_MANAGER: TunnelManager = TunnelManager()

    @JvmStatic
    fun init() {
        AutoConfig.register(
            BigCleverConfig::class.java
        ) { definition: Config?, configClass: Class<BigCleverConfig?>? ->
            Toml4jConfigSerializer(
                definition,
                configClass
            )
        }

        Runtime.getRuntime().addShutdownHook(Thread {
            TUNNEL_MANAGER.closeAll()
            THREAD_POOL.shutdownNow()
        })
    }
}