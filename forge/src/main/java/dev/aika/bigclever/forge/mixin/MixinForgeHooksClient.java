package dev.aika.bigclever.forge.mixin;

import dev.aika.bigclever.config.BigCleverConfig;
import net.minecraft.client.network.ServerInfo;
import net.minecraftforge.client.ExtendedServerListData;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = ForgeHooksClient.class, remap = false)
public class MixinForgeHooksClient {
    @ModifyVariable(method = "drawForgePingInfo", at = @At("HEAD"), argsOnly = true)
    private static ServerInfo drawForgePingInfo(ServerInfo info) {
        if (info.forgeData != null && !info.forgeData.isCompatible() && BigCleverConfig.getConfig().tweaksMyForgeServerIsCompatible)
            info.forgeData = new ExtendedServerListData("FML", true,
                    info.forgeData.numberOfMods(), info.forgeData.extraReason(), info.forgeData.truncated());
        return info;
    }
}
