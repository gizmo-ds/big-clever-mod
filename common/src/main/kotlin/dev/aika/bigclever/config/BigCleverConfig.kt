package dev.aika.bigclever.config

import dev.aika.bigclever.BigClever
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = BigClever.MOD_ID)
class BigCleverConfig : ConfigData {
    @JvmField
    @ConfigEntry.Category("serverTunnel")
    var wstunnelEnable: Boolean = true

    companion object {
        @JvmStatic
        val config: BigCleverConfig
            get() = AutoConfig.getConfigHolder(BigCleverConfig::class.java).config
    }
}