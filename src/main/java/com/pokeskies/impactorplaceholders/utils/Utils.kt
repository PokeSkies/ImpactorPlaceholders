package com.pokeskies.impactorplaceholders.utils

import com.pokeskies.impactorplaceholders.ImpactorPlaceholders
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer


object Utils {
    val miniMessage: MiniMessage = MiniMessage.miniMessage()
    val plainTextSerializer = PlainTextComponentSerializer.plainText()

    fun deserializeText(text: String): Component {
        return miniMessage.deserialize(text)
    }

    fun printError(message: String) {
        ImpactorPlaceholders.LOGGER.error("[ImpactorPlaceholders] ERROR: $message")
    }

    fun printInfo(message: String) {
        ImpactorPlaceholders.LOGGER.info("[ImpactorPlaceholders] $message")
    }
}
