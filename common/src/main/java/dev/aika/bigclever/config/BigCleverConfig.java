package dev.aika.bigclever.config;

import dev.aika.bigclever.BigClever;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = BigClever.MOD_ID)
public class BigCleverConfig implements ConfigData {

    @ConfigEntry.Category("serverTunnel")
    public boolean wstunnelEnable = true;

    @ConfigEntry.Category("clientTweaks")
    @ConfigEntry.Gui.Tooltip()
    public boolean tweaksMyForgeServerIsCompatible = true;

    public static BigCleverConfig get() {
        return AutoConfig.getConfigHolder(BigCleverConfig.class).getConfig();
    }
}
