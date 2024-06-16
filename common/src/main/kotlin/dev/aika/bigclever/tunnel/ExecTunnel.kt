package dev.aika.bigclever.tunnel

import dev.aika.bigclever.BigClever
import dev.aika.bigclever.getGameDir
import java.io.File
import java.io.IOException

open class ExecTunnel(var TunnelType: String, executableFileName: String) {
    private val tunnelDir: File
    private val executableFile: File
    private var process: Process? = null

    init {
        val tunnelsDir = getGameDir().resolve(BigClever.MOD_ID + "/bin/").toFile()
        tunnelDir = File(tunnelsDir, TunnelType)
        executableFile = File(tunnelDir, executableFileName).absoluteFile
    }

    fun start(args: List<String>?) {
        val command: MutableList<String> = ArrayList()
        command.add(executableFile.toString())
        command.addAll(args!!)

        BigClever.THREAD_POOL.execute {
            try {
                val builder = ProcessBuilder(command)
                builder.directory(tunnelDir)
                this.process = builder.start()
            } catch (e: IOException) {
                this.process = null
                BigClever.LOGGER.error("Failed to start tunnel process", e)
            }
        }
    }

    fun stop() {
        if (process == null) return
        process!!.destroy()
    }

    val isAvailable: Boolean
        get() = executableFile.exists() && executableFile.isFile && executableFile.canExecute()

    companion object
}