package com.pokeskies.impactorplaceholders.placeholders.services

import com.pokeskies.impactorplaceholders.ImpactorPlaceholders
import com.pokeskies.impactorplaceholders.placeholders.IPlaceholderService
import com.pokeskies.impactorplaceholders.utils.Utils
import eu.pb4.placeholders.api.PlaceholderResult
import eu.pb4.placeholders.api.Placeholders
import net.impactdev.impactor.api.economy.EconomyService
import net.impactdev.impactor.api.economy.accounts.Account
import net.minecraft.util.Identifier

class PlaceholderAPIService : IPlaceholderService {
    init {
        Utils.printInfo("PlaceholderAPI mod found! Registering placeholders...")
    }

    override fun registerPlaceholders() {
        Placeholders.register(Identifier("impactor", "currency_plural")) { ctx, arg ->
            var currency = EconomyService.instance().currencies().primary()

            if (!arg.isNullOrEmpty()) {
                val optCurrency = Utils.getCurrency(arg)
                if (optCurrency.isEmpty)
                    PlaceholderResult.invalid("Invalid currency argument provided!")

                currency = optCurrency.get()
            }

            PlaceholderResult.value(ImpactorPlaceholders.INSTANCE.adventure!!.toNative(currency.plural()))
        }
        Placeholders.register(Identifier("impactor", "currency_singular")) { ctx, arg ->
            var currency = EconomyService.instance().currencies().primary()

            if (!arg.isNullOrEmpty()) {
                val optCurrency = Utils.getCurrency(arg)
                if (optCurrency.isEmpty)
                    PlaceholderResult.invalid("Invalid currency argument provided!")

                currency = optCurrency.get()
            }

            PlaceholderResult.value(ImpactorPlaceholders.INSTANCE.adventure!!.toNative(currency.singular()))
        }
        Placeholders.register(Identifier("impactor", "currency_symbol")) { ctx, arg ->
            var currency = EconomyService.instance().currencies().primary()

            if (!arg.isNullOrEmpty()) {
                val optCurrency = Utils.getCurrency(arg)
                if (optCurrency.isEmpty)
                    PlaceholderResult.invalid("Invalid currency argument provided!")

                currency = optCurrency.get()
            }

            PlaceholderResult.value(ImpactorPlaceholders.INSTANCE.adventure!!.toNative(currency.symbol()))
        }
        Placeholders.register(Identifier("impactor", "balance")) { ctx, arg ->
            if (!ctx.hasPlayer()) PlaceholderResult.invalid("No player!")

            var currency = EconomyService.instance().currencies().primary()

            if (!arg.isNullOrEmpty()) {
                val optCurrency = Utils.getCurrency(arg)
                if (optCurrency.isEmpty)
                    PlaceholderResult.invalid("Invalid currency argument provided!")

                currency = optCurrency.get()
            }

            PlaceholderResult.value(
                Utils.getAccount(ctx.player!!.uuid, currency)
                    .thenCompose(Account::balanceAsync).join().toDouble().toString()
            )
        }
    }
}