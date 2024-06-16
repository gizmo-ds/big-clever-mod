package dev.aika.bigclever.fabric

import dev.aika.bigclever.BigClever
import net.fabricmc.api.ModInitializer

@Suppress("unused")
class BigCleverFabric : ModInitializer {
    override fun onInitialize() {
        BigClever.init()
    }
}