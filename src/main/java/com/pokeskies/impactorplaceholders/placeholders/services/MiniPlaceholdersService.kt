package com.pokeskies.impactorplaceholders.placeholders.services

import com.pokeskies.impactorplaceholders.placeholders.IPlaceholderService
import com.pokeskies.impactorplaceholders.utils.Utils
import io.github.miniplaceholders.api.Expansion
import net.impactdev.impactor.api.economy.EconomyService
import net.impactdev.impactor.api.economy.accounts.Account
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.minecraft.server.level.ServerPlayer

class MiniPlaceholdersService : IPlaceholderService {
    private val miniMessage = MiniMessage.builder()
        .tags(TagResolver.builder().build())
        .build()

    init {
        Utils.printInfo("MiniPlaceholders mod found! Registering placeholders...")
    }

    override fun registerPlaceholders() {
        val builder = Expansion.builder("impactor")
            .globalPlaceholder("currency_plural") { queue, _ ->
                var currency = EconomyService.instance().currencies().primary()

                if (queue.peek() != null) {
                    val optCurrency = Utils.getCurrency(queue.peek()?.value())
                    if (optCurrency.isEmpty)
                        return@globalPlaceholder Tag.inserting(Component.text(
                            "Invalid currency argument provided!"
                        ))

                    currency = optCurrency.get()
                }

                return@globalPlaceholder Tag.inserting(currency.plural())
            }
            .globalPlaceholder("currency_singular") { queue, _ ->
                var currency = EconomyService.instance().currencies().primary()

                if (queue.peek() != null) {
                    val optCurrency = Utils.getCurrency(queue.peek()?.value())
                    if (optCurrency.isEmpty)
                        return@globalPlaceholder Tag.inserting(Component.text(
                            "Invalid currency argument provided!"
                        ))

                    currency = optCurrency.get()
                }

                return@globalPlaceholder Tag.inserting(currency.singular())
            }
            .globalPlaceholder("currency_symbol") { queue, _ ->
                var currency = EconomyService.instance().currencies().primary()

                if (queue.peek() != null) {
                    val optCurrency = Utils.getCurrency(queue.peek()?.value())
                    if (optCurrency.isEmpty)
                        return@globalPlaceholder Tag.inserting(Component.text(
                            "Invalid currency argument provided!"
                        ))

                    currency = optCurrency.get()
                }

                return@globalPlaceholder Tag.inserting(currency.symbol())
            }
            .filter(ServerPlayer::class.java)
            .audiencePlaceholder("balance") { audience, queue, _ ->
                val player = audience as ServerPlayer

                var currency = EconomyService.instance().currencies().primary()

                if (queue.peek() != null) {
                    val optCurrency = Utils.getCurrency(queue.peek()?.value())
                    if (optCurrency.isEmpty)
                        return@audiencePlaceholder Tag.inserting(Component.text(
                            "Invalid currency argument provided!"
                        ))

                    currency = optCurrency.get()
                }

                return@audiencePlaceholder Tag.inserting(Component.text(
                    Utils.getAccount(player.uuid, currency).thenCompose(Account::balanceAsync).join().toDouble()
                ))
            }
            .audiencePlaceholder("balance_short") { audience, queue, _ ->
                val player = audience as ServerPlayer

                var currency = EconomyService.instance().currencies().primary()

                if (queue.peek() != null) {
                    val optCurrency = Utils.getCurrency(queue.peek()?.value())
                    if (optCurrency.isEmpty)
                        return@audiencePlaceholder Tag.inserting(Component.text(
                            "Invalid currency argument provided!"
                        ))

                    currency = optCurrency.get()
                }

                val result = Utils.getAccount(player.uuid, currency).thenCompose(Account::balanceAsync).join().toDouble()

                return@audiencePlaceholder Tag.inserting(
                    Component.text(if (result % 1 == 0.0) result.toInt().toString() else result.toString())
                )
            }

        builder.build().register()
    }
}