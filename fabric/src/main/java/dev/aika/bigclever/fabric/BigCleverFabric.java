package dev.aika.bigclever.fabric;

import net.fabricmc.api.ModInitializer;

import dev.aika.bigclever.BigClever;

public final class BigCleverFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BigClever.init();
    }
}
