package dev.aika.bigclever.neoforge

import dev.aika.bigclever.BigClever
import dev.aika.bigclever.neoforge.config.ClothConfig
import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLEnvironment

@Suppress("unused")
@Mod(BigClever.MOD_ID)
class BigCleverNeoForge {
    init {
        BigClever.init()

        if (FMLEnvironment.dist == Dist.CLIENT) ClothConfig.setupConfig()
    }
}