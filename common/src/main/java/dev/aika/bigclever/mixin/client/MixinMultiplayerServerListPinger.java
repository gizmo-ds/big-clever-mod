package dev.aika.bigclever.mixin.client;

import dev.aika.bigclever.BigClever;
import dev.aika.bigclever.interfaces.ClientConnection;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.InetSocketAddress;

@Mixin(MultiplayerServerListPinger.class)
public class MixinMultiplayerServerListPinger {

    @Redirect(method = "add", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/ClientConnection;connect(Ljava/net/InetSocketAddress;ZLnet/minecraft/util/profiler/MultiValueDebugSampleLogImpl;)Lnet/minecraft/network/ClientConnection;"))
    public net.minecraft.network.ClientConnection connect(InetSocketAddress address, boolean useEpoll, MultiValueDebugSampleLogImpl packetSizeLog) {
        var tunnel = BigClever.TUNNEL_MANAGER.useTunnel(address.getHostName().trim(), address.getPort(), true);
        if (tunnel != null) {
            var newAddress = tunnel.getServerAddress();
            if (newAddress != null) {
                var conn = net.minecraft.network.ClientConnection.connect(new InetSocketAddress(newAddress.getAddress(), newAddress.getPort()), useEpoll, packetSizeLog);
                ((ClientConnection) conn).setTunnel(tunnel);
                return conn;
            }
        }
        return net.minecraft.network.ClientConnection.connect(address, useEpoll, packetSizeLog);
    }
}
