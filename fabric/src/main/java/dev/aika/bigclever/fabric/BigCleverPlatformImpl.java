package dev.aika.bigclever.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class BigCleverPlatformImpl {
    public static Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }
}
