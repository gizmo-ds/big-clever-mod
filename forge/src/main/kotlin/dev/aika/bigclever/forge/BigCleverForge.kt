package dev.aika.bigclever.forge

import dev.aika.bigclever.BigClever
import dev.aika.bigclever.forge.config.ClothConfig
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.DistExecutor.SafeRunnable
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Suppress("unused")
@Mod(BigClever.MOD_ID)
class BigCleverForge {
    init {
        MOD_BUS.register(this)

        BigClever.init()

        DistExecutor.safeRunWhenOn(Dist.CLIENT) { SafeRunnable(ClothConfig::setupConfig) }
    }
}