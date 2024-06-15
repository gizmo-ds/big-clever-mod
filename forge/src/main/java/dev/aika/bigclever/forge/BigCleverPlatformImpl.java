package dev.aika.bigclever.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class BigCleverPlatformImpl {
    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }
}
