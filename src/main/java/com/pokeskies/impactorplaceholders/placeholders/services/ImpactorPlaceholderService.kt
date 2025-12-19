package com.pokeskies.impactorplaceholders.placeholders.services

import com.pokeskies.impactorplaceholders.ImpactorPlaceholders
import com.pokeskies.impactorplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.impactorplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.impactorplaceholders.utils.Utils
import net.impactdev.impactor.api.Impactor
import net.impactdev.impactor.api.platform.players.PlatformPlayer
import net.impactdev.impactor.api.platform.sources.PlatformSource
import net.impactdev.impactor.api.text.TextProcessor
import net.impactdev.impactor.api.text.placeholders.PlaceholderArguments
import net.impactdev.impactor.api.text.placeholders.PlaceholderService
import net.impactdev.impactor.api.utility.Context
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.minecraft.server.level.ServerPlayer
import kotlin.jvm.optionals.getOrNull


class ImpactorPlaceholderService : IPlaceholderService {
    private val keyId = "impactor_placeholders"
    private val service = Impactor.instance().services().provide(PlaceholderService::class.java)
    private val processor = TextProcessor.mini(
        MiniMessage.builder()
            .tags(TagResolver.builder().build())
            .build()
    )

    init {
        Utils.printInfo("Impactor mod found! Registering placeholders...")
    }

    override fun registerPlayer(placeholder: PlayerPlaceholder) {
        placeholder.ids().forEach { id ->
            service.register(
                Key.key(keyId, id)
            ) { viewer, ctx ->
                val platformPlayer: PlatformSource = ctx.require(PlatformSource::class.java)
                val player = ImpactorPlaceholders.INSTANCE.server.playerList.getPlayer(platformPlayer.uuid()) ?: run {
                    return@register Component.text("No Player")
                }

                val arguments = ctx.request(PlaceholderArguments::class.java).getOrNull()?.let {
                    val args = mutableListOf<String>()
                    while (it.hasNext()) {
                        args.add(it.pop())
                    }
                    args.toList()
                } ?: emptyList()

                return@register placeholder.handle(player, arguments).component
            }
        }
    }

    override fun registerServer(placeholder: ServerPlaceholder) {
        placeholder.ids().forEach { id ->
            service.register(
                Key.key(keyId, id)
            ) { viewer, ctx ->
                val arguments = ctx.request(PlaceholderArguments::class.java).getOrNull()?.let {
                    val args = mutableListOf<String>()
                    while (it.hasNext()) {
                        args.add(it.pop())
                    }
                    args.toList()
                } ?: emptyList()

                return@register placeholder.handle(arguments).component
            }
        }
    }

    override fun finalizeRegister() {

    }

    override fun parse(input: String, player: ServerPlayer?): String {
        val parsed = if (player != null) {
            val platformPlayer = PlatformPlayer.getOrCreate(player.uuid)
            processor.parse(platformPlayer, input, Context().append(PlatformSource::class.java, platformPlayer))
        } else {
            processor.parse(input)
        }
        return ImpactorPlaceholders.INSTANCE.adventure.toNative(parsed).string
    }
}
