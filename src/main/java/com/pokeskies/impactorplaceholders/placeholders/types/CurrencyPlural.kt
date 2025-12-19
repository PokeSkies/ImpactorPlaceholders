package com.pokeskies.impactorplaceholders.placeholders.types

import com.pokeskies.impactorplaceholders.placeholders.GenericResult
import com.pokeskies.impactorplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.impactorplaceholders.utils.ImpactorUtils
import net.impactdev.impactor.api.economy.EconomyService

class CurrencyPlural : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        var currency = EconomyService.instance().currencies().primary()

        if (args.isNotEmpty()) {
            currency = ImpactorUtils.getCurrency(args.getOrNull(0)) ?: run {
                return GenericResult.invalid("Invalid currency argument provided!")
            }
        }

        return GenericResult.valid(currency.plural())
    }

    override fun ids(): List<String> = listOf("currency_plural")
}
