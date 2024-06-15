package dev.aika.bigclever.forge;

import dev.aika.bigclever.forge.config.ClothConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import dev.aika.bigclever.BigClever;

@Mod(BigClever.MOD_ID)
public final class BigCleverForge {
    public BigCleverForge() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        BigClever.init();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClothConfig::setupConfig);
    }
}
