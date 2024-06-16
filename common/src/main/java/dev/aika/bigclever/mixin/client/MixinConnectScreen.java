package dev.aika.bigclever.mixin.client;

import dev.aika.bigclever.BigClever;
import dev.aika.bigclever.interfaces.ClientConnection;
import dev.aika.bigclever.tunnel.Tunnel;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class MixinConnectScreen extends Screen {

    protected MixinConnectScreen(Text title) {
        super(title);
    }

    @Unique
    @Nullable
    public Tunnel bigclever$tunnel;

    @Shadow
    @Nullable
    net.minecraft.network.ClientConnection connection;

    @ModifyVariable(
            method = "connect(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;Lnet/minecraft/client/network/CookieStorage;)V",
            at = @At("HEAD"), argsOnly = true)
    private ServerAddress connect(ServerAddress address) {
        var tunnel = BigClever.TUNNEL_MANAGER.useTunnel(address.getAddress().trim(), address.getPort(), false);
        if (tunnel != null) {
            var newAddress = tunnel.getServerAddress();
            if (newAddress != null) {
                this.bigclever$tunnel = tunnel;
                return newAddress;
            }
        }
        return address;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (this.connection == null) return;
        var _connection = (ClientConnection) connection;
        if (this.bigclever$tunnel != null && _connection.getTunnel() == null)
            _connection.setTunnel(this.bigclever$tunnel);
    }
}
