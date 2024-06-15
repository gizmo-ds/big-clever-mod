package dev.aika.bigclever.tunnel;

import dev.aika.bigclever.BigClever;
import dev.aika.bigclever.utils.NetworkUtils;
import net.minecraft.client.network.ServerAddress;
import org.lwjgl.system.Platform;

import java.util.ArrayList;
import java.util.List;

public class WSTunnel extends ExecTunnel implements Tunnel {
    public static final String TUNNEL_TYPE = "wstunnel";
    private static final String PORT_PLACEHOLDER = "-mc_port-";
    private static final String LOCALHOST = "127.0.0.1";

    private final String[] listens;
    private int freePort;

    public WSTunnel(String[] listens) {
        super(TUNNEL_TYPE, "wstunnel" + (Platform.get() == Platform.WINDOWS ? ".exe" : ""));
        this.listens = listens;
    }

    public static WSTunnel fromRecord(String record) {
        return new WSTunnel(WSTunnel.parseRecord(record));
    }

    @Override
    public String tunnelType() {
        return TUNNEL_TYPE;
    }

    @Override
    public void start(String host, int port, boolean mcPortOnly) {
        freePort = NetworkUtils.findFreePort();

        List<String> command = new ArrayList<>();
        command.add("client");
        for (var l : listens) {
            if (mcPortOnly && !l.contains(PORT_PLACEHOLDER)) continue;
            command.add("-L");
            command.add(l.replaceAll(PORT_PLACEHOLDER, Integer.toString(freePort)));
        }

        if (port == 25565) command.add(String.format("wss://%s", host));
        else command.add(String.format("ws://%s:%d", host, port));

        super.start(command);
    }

    @Override
    public ServerAddress getServerAddress() {
        if (NetworkUtils.isConnectable(LOCALHOST, freePort, 1000 * 5))
            return new ServerAddress(LOCALHOST, freePort);
        return null;
    }

    private static String[] parseRecord(String record) {
        String tunnelKey = String.format("%s.%s=", BigClever.MOD_ID, TUNNEL_TYPE);
        if (!record.startsWith(tunnelKey)) return new String[0];
        return record.substring(tunnelKey.length()).split(";");
    }
}
