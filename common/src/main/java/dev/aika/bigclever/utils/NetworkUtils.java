package dev.aika.bigclever.utils;

import dev.aika.bigclever.BigClever;

import java.io.IOException;
import java.net.*;

public class NetworkUtils {
    public static int findFreePort() {
        try (var socket = new java.net.ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (Exception e) {
            throw new RuntimeException("Unable to find a free port", e);
        }
    }

    public static boolean isConnectable(String host, int port, int totalTimeout) {
        long startTime = System.currentTimeMillis();
        int connectionTimeout = 1000;

        SocketAddress address = new InetSocketAddress(host, port);
        while (true) {
            try (Socket socket = new Socket()) {
                socket.connect(address, connectionTimeout);
                BigClever.LOGGER.debug("Successfully connected to {}:{}", host, port);
                return true;
            } catch (SocketTimeoutException | ConnectException e) {
                if (System.currentTimeMillis() - startTime > totalTimeout) {
                    BigClever.LOGGER.error("Total timeout exceeded for {}:{}", host, port);
                    return false;
                }
            } catch (IOException e) {
                BigClever.LOGGER.error("I/O error occurred when trying to connect to {}:{}: {}", host, port, e.getMessage());
                return false;
            }
        }
    }
}
