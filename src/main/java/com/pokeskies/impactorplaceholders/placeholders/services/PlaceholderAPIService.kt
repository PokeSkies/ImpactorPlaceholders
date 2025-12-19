package com.pokeskies.impactorplaceholders.placeholders.services

import com.pokeskies.impactorplaceholders.ImpactorPlaceholders
import com.pokeskies.impactorplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.impactorplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.impactorplaceholders.utils.Utils
import eu.pb4.placeholders.api.PlaceholderContext
import eu.pb4.placeholders.api.PlaceholderResult
import eu.pb4.placeholders.api.Placeholders
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

class PlaceholderAPIService : IPlaceholderService {
    init {
        Utils.printInfo("PlaceholderAPI mod found! Registering placeholders...")
    }

    override fun registerPlayer(placeholder: PlayerPlaceholder) {
        placeholder.ids().forEach { id ->
            Placeholders.register(ResourceLocation.fromNamespaceAndPath("impactor", id)) { ctx, arg ->
                val player = ctx.player ?: return@register PlaceholderResult.invalid("NO PLAYER")
                val args = if (arg != null) parse(arg, player).split(":") else emptyList()
                val result = placeholder.handle(player, args)
                return@register if (result.isSuccessful) {
                    PlaceholderResult.value(result.asNative())
                } else {
                    PlaceholderResult.invalid(result.asString())
                }
            }
        }
    }

    override fun registerServer(placeholder: ServerPlaceholder) {
        placeholder.ids().forEach { id ->
            Placeholders.register(ResourceLocation.fromNamespaceAndPath("impactor", id)) { ctx, arg ->
                val args = if (arg != null) parse(arg, ctx.player).split(":") else emptyList()
                val result = placeholder.handle(args.map { parse(it) })
                return@register if (result.isSuccessful) {
                    PlaceholderResult.value(result.asNative())
                } else {
                    PlaceholderResult.invalid(result.asString())
                }
            }
        }
    }

    override fun finalizeRegister() {

    }

    override fun parse(input: String, player: ServerPlayer?): String {
        return Placeholders.parseText(Component.literal(input),
            if (player != null)
                PlaceholderContext.of(player)
            else
                PlaceholderContext.of(ImpactorPlaceholders.INSTANCE.server)
        ).string
    }
}
