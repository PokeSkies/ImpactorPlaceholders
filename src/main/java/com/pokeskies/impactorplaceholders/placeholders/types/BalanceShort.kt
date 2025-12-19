package com.pokeskies.impactorplaceholders.placeholders.types

import com.pokeskies.impactorplaceholders.placeholders.GenericResult
import com.pokeskies.impactorplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.impactorplaceholders.utils.ImpactorUtils
import net.impactdev.impactor.api.economy.EconomyService
import net.minecraft.server.level.ServerPlayer

class BalanceShort : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        var currency = EconomyService.instance().currencies().primary()

        val (currencyId, decimals) = ImpactorUtils.parseArgs(args)
        if (currencyId != null) {
            currency = ImpactorUtils.getCurrency(currencyId) ?: run {
                return GenericResult.invalid("Invalid currency argument provided!")
            }
        }

        return GenericResult.valid(
            ImpactorUtils.formatShort(ImpactorUtils.balance(player, currency), decimals)
        )
    }

    override fun ids(): List<String> = listOf("balance_short")
}
