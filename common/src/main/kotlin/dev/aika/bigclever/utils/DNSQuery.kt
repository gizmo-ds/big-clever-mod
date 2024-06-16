package dev.aika.bigclever.utils

import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*
import javax.naming.Context
import javax.naming.directory.DirContext
import javax.naming.directory.InitialDirContext

object DNSQuery {
    fun isSuitableForDNSQuery(hostname: String): Boolean {
        if ("localhost".equals(hostname, ignoreCase = true) || "::1" == hostname) return false

        try {
            val address = InetAddress.getByName(hostname)
            return !address.isLoopbackAddress && !address.isSiteLocalAddress && !address.isLinkLocalAddress && !address.isAnyLocalAddress
        } catch (ignored: UnknownHostException) {
        }
        return false
    }

    fun queryTXTRecords(domain: String?): List<String> {
        val txtRecords: MutableList<String> = ArrayList()
        val env = Hashtable<String, String>()
        env[Context.INITIAL_CONTEXT_FACTORY] = "com.sun.jndi.dns.DnsContextFactory"

        try {
            val ctx: DirContext = InitialDirContext(env)
            val attr = ctx.getAttributes(domain, arrayOf("TXT"))["TXT"]
            if (attr != null) for (i in 0 until attr.size()) txtRecords.add(attr[i] as String)
        } catch (ignored: Exception) {
        }
        return txtRecords
    }
}