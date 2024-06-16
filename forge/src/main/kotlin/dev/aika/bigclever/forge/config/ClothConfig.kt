package dev.aika.bigclever.forge.config

import dev.aika.bigclever.config.BigCleverConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraftforge.client.ConfigScreenHandler
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.ModLoadingContext

object ClothConfig {
    fun setupConfig() {
        if (!isClothConfigLoaded) return
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory::class.java
        ) {
            ConfigScreenHandler.ConfigScreenFactory { _: MinecraftClient?, parent: Screen? ->
                AutoConfig.getConfigScreen(BigCleverConfig::class.java, parent).get()
            }
        }
    }

    private val isClothConfigLoaded: Boolean
        get() = ModList.get().isLoaded("cloth_config")
}