package com.pokeskies.impactorplaceholders.placeholders.services

import com.pokeskies.impactorplaceholders.placeholders.IPlaceholderService
import com.pokeskies.impactorplaceholders.utils.Utils
import net.impactdev.impactor.api.Impactor
import net.impactdev.impactor.api.economy.EconomyService
import net.impactdev.impactor.api.economy.accounts.Account
import net.impactdev.impactor.api.platform.sources.PlatformSource
import net.impactdev.impactor.api.text.TextProcessor
import net.impactdev.impactor.api.text.placeholders.PlaceholderArguments
import net.impactdev.impactor.api.text.placeholders.PlaceholderService
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver


class ImpactorPlaceholderService : IPlaceholderService {
    private val processor = TextProcessor.mini(
        MiniMessage.builder()
            .tags(TagResolver.builder().build())
            .build()
    )

    init {
        Utils.printInfo("Impactor mod found! Registering placeholders...")
    }

    override fun registerPlaceholders() {
        val service = Impactor.instance().services().provide(PlaceholderService::class.java)

        service.register(
            Key.key("impactor_placeholders", "currency_plural")
        ) { viewer, ctx ->
            var currency = EconomyService.instance().currencies().primary()

            val arguments = ctx.request(PlaceholderArguments::class.java)
            if (arguments.isPresent && arguments.get().hasNext()) {
                val optCurrency = Utils.getCurrency(arguments.get().pop())
                if (optCurrency.isEmpty) {
                    return@register Component.text(
                        "Invalid currency argument provided!"
                    )
                }

                currency = optCurrency.get()
            }

            return@register currency.plural()
        }
        service.register(
            Key.key("impactor_placeholders", "currency_singular")
        ) { viewer, ctx ->
            var currency = EconomyService.instance().currencies().primary()

            val arguments = ctx.request(PlaceholderArguments::class.java)
            if (arguments.isPresent && arguments.get().hasNext()) {
                val optCurrency = Utils.getCurrency(arguments.get().pop())
                if (optCurrency.isEmpty)
                    return@register Component.text(
                        "Invalid currency argument provided!"
                    )

                currency = optCurrency.get()
            }

            return@register currency.singular()
        }
        service.register(
            Key.key("impactor_placeholders", "currency_symbol")
        ) { viewer, ctx ->
            var currency = EconomyService.instance().currencies().primary()

            val arguments = ctx.request(PlaceholderArguments::class.java)
            if (arguments.isPresent && arguments.get().hasNext()) {
                val optCurrency = Utils.getCurrency(arguments.get().pop())
                if (optCurrency.isEmpty)
                    return@register Component.text(
                        "Invalid currency argument provided!"
                    )

                currency = optCurrency.get()
            }

            return@register currency.symbol()
        }
        service.register(
            Key.key("impactor_placeholders", "balance")
        ) { viewer, ctx ->
            val player: PlatformSource = ctx.require(PlatformSource::class.java)

            var currency = EconomyService.instance().currencies().primary()

            val arguments = ctx.request(PlaceholderArguments::class.java)
            if (arguments.isPresent && arguments.get().hasNext()) {
                val optCurrency = Utils.getCurrency(arguments.get().pop())
                if (optCurrency.isEmpty)
                    return@register Component.text(
                        "Invalid currency argument provided!"
                    )

                currency = optCurrency.get()
            }

            return@register Component.text(
                Utils.getAccount(player.uuid(), currency).thenCompose(Account::balanceAsync).join().toDouble()
            )
        }
    }
}