@file:JvmName("BigCleverPlatformImpl")

package dev.aika.bigclever.fabric

import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path

fun getGameDir(): Path = FabricLoader.getInstance().gameDir
