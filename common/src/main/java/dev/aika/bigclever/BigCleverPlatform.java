package dev.aika.bigclever;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class BigCleverPlatform {
    @ExpectPlatform
    public static Path getGameDir() {
        throw new AssertionError();
    }
}
