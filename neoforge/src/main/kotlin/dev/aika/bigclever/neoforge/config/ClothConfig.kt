package dev.aika.bigclever.neoforge.config

import dev.aika.bigclever.config.BigCleverConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.neoforged.fml.ModList
import net.neoforged.fml.ModLoadingContext
import net.neoforged.neoforge.client.gui.IConfigScreenFactory

object ClothConfig {
    fun setupConfig() {
        if (!isClothConfigLoaded) return
        ModLoadingContext.get().registerExtensionPoint(
            IConfigScreenFactory::class.java
        ) {
            (IConfigScreenFactory { _: MinecraftClient?, parent: Screen? ->
                AutoConfig.getConfigScreen(BigCleverConfig::class.java, parent).get()
            })
        }
    }

    private val isClothConfigLoaded: Boolean
        get() = ModList.get().isLoaded("cloth_config")
}