package dev.aika.bigclever.tunnel;

import dev.aika.bigclever.BigClever;
import dev.aika.bigclever.BigCleverPlatform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecTunnel {
    public static String TunnelType;

    private final File tunnelDir;
    private final File executableFile;
    private Process process;

    public ExecTunnel(String tunnelType, String executableFileName) {
        TunnelType = tunnelType;
        File tunnelsDir = BigCleverPlatform.getGameDir().resolve(BigClever.MOD_ID + "/bin/").toFile();
        tunnelDir = new File(tunnelsDir, tunnelType);
        executableFile = new File(tunnelDir, executableFileName).getAbsoluteFile();
    }

    public void start(List<String> args) {
        List<String> command = new ArrayList<>();
        command.add(executableFile.toString());
        command.addAll(args);

        BigClever.THREAD_POOL.execute(() -> {
            try {
                ProcessBuilder builder = new ProcessBuilder(command);
                builder.directory(tunnelDir);
                this.process = builder.start();
            } catch (IOException e) {
                this.process = null;
                BigClever.LOGGER.error("Failed to start tunnel process", e);
            }
        });
    }

    public void stop() {
        if (process == null) return;
        process.destroy();
    }

    public boolean isAvailable() {
        return executableFile.exists() && executableFile.isFile() && executableFile.canExecute();
    }
}
