@file:JvmName("BigCleverPlatformImpl")

package dev.aika.bigclever.forge

import net.minecraftforge.fml.loading.FMLPaths
import java.nio.file.Path

fun getGameDir(): Path = FMLPaths.GAMEDIR.get()
