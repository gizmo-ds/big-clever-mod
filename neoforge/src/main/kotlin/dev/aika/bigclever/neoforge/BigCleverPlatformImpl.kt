@file:JvmName("BigCleverPlatformImpl")

package dev.aika.bigclever.neoforge

import net.neoforged.fml.loading.FMLPaths
import java.nio.file.Path

@Suppress("unused")
fun getGameDir(): Path = FMLPaths.GAMEDIR.get()
