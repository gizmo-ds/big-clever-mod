@file:JvmName("BigCleverPlatform")

package dev.aika.bigclever

import dev.architectury.injectables.annotations.ExpectPlatform
import java.nio.file.Path

@ExpectPlatform
fun getGameDir(): Path = throw AssertionError()