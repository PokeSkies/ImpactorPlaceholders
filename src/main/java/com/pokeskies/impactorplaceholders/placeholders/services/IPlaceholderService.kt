package com.pokeskies.impactorplaceholders.placeholders.services

import com.pokeskies.impactorplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.impactorplaceholders.placeholders.ServerPlaceholder
import net.minecraft.server.level.ServerPlayer

interface IPlaceholderService {
    fun registerPlayer(placeholder: PlayerPlaceholder)
    fun registerServer(placeholder: ServerPlaceholder)
    fun finalizeRegister()
    fun parse(input: String, player: ServerPlayer? = null): String
}