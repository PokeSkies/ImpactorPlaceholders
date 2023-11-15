package com.pokeskies.impactorplaceholders.utils

import com.pokeskies.impactorplaceholders.ImpactorPlaceholders
import net.impactdev.impactor.api.economy.EconomyService
import net.impactdev.impactor.api.economy.accounts.Account
import net.impactdev.impactor.api.economy.currency.Currency
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minecraft.util.Identifier
import java.util.*
import java.util.concurrent.CompletableFuture


object Utils {
    private val miniMessage: MiniMessage = MiniMessage.miniMessage()

    fun deserializeText(text: String): Component {
        return miniMessage.deserialize(text)
    }

    fun printError(message: String) {
        ImpactorPlaceholders.LOGGER.error("[ImpactorPlaceholders] ERROR: $message")
    }

    fun printInfo(message: String) {
        ImpactorPlaceholders.LOGGER.info("[ImpactorPlaceholders] $message")
    }

    fun getAccount(uuid: UUID, currency: Currency): CompletableFuture<Account> {
        return EconomyService.instance().account(currency, uuid)
    }

    fun getCurrency(id: String?) : Optional<Currency> {
        if (id.isNullOrEmpty()) return Optional.empty()

        val key = if (id.contains(":")) id else Identifier("impactor", id).toString()

        val currency: Optional<Currency> = EconomyService.instance().currencies().currency(Key.key(key))
        if (currency.isEmpty) {
            printError("Could not find a currency by the ID $id! Valid currencies: " +
                    "${EconomyService.instance().currencies().registered().map { it.key() } }")
            return Optional.empty()
        }

        return currency
    }
}