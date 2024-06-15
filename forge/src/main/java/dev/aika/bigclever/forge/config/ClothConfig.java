package dev.aika.bigclever.forge.config;

import dev.aika.bigclever.config.BigCleverConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;

public class ClothConfig {
    public static void setupConfig() {
        if (!isClothConfigLoaded()) return;
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(BigCleverConfig.class, parent).get()));
    }

    private static boolean isClothConfigLoaded() {
        return ModList.get().isLoaded("cloth_config");
    }
}
