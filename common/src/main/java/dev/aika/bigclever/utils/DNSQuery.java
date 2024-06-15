package dev.aika.bigclever.utils;

import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class DNSQuery {
    public static boolean isSuitableForDNSQuery(String hostname) {
        if ("localhost".equalsIgnoreCase(hostname) || "::1".equals(hostname)) return false;

        try {
            InetAddress address = InetAddress.getByName(hostname);
            return !address.isLoopbackAddress() && !address.isSiteLocalAddress() && !address.isLinkLocalAddress() && !address.isAnyLocalAddress();
        } catch (UnknownHostException ignored) {
        }
        return false;
    }

    public static List<String> queryTXTRecords(String domain) {
        List<String> txtRecords = new ArrayList<>();
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");

        try {
            DirContext ctx = new InitialDirContext(env);
            Attribute attr = ctx.getAttributes(domain, new String[]{"TXT"}).get("TXT");
            if (attr != null) for (int i = 0; i < attr.size(); i++) txtRecords.add((String) attr.get(i));
        } catch (Exception ignored) {
        }
        return txtRecords;
    }
}
