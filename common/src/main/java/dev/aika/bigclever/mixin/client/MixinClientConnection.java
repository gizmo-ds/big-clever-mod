package dev.aika.bigclever.mixin.client;

import dev.aika.bigclever.interfaces.ClientConnection;
import dev.aika.bigclever.tunnel.Tunnel;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Implements(@Interface(iface = ClientConnection.class, prefix = "bigclever$"))
@Mixin(net.minecraft.network.ClientConnection.class)
public class MixinClientConnection {
    @Unique
    private Tunnel bigclever$tunnel = null;

    @Intrinsic
    public void bigclever$setTunnel(Tunnel runningTunnel) {
        this.bigclever$tunnel = runningTunnel;
    }

    public Tunnel bigclever$getTunnel() {
        return this.bigclever$tunnel;
    }

    @Inject(method = "disconnect(Lnet/minecraft/text/Text;)V", at = @At("TAIL"))
    public void disconnect(Text disconnectReason, CallbackInfo ci) {
        synchronized (this) {
            if (this.bigclever$tunnel != null) {
                this.bigclever$tunnel.stop();
                this.bigclever$tunnel = null;
            }
        }
    }
}
