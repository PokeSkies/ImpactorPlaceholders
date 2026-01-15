package com.pokeskies.impactorplaceholders

import com.pokeskies.impactorplaceholders.placeholders.services.IPlaceholderService
import com.pokeskies.impactorplaceholders.placeholders.services.PlaceholderServices
import com.pokeskies.impactorplaceholders.placeholders.types.*
import com.pokeskies.impactorplaceholders.utils.Utils
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.kyori.adventure.platform.fabric.FabricServerAudiences
import net.minecraft.server.MinecraftServer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.stream.Stream

class ImpactorPlaceholders : ModInitializer {
    companion object {
        lateinit var INSTANCE: ImpactorPlaceholders
        val LOGGER: Logger = LogManager.getLogger("impactorplaceholders")
    }

    lateinit var adventure: FabricServerAudiences
    lateinit var server: MinecraftServer

    var placeholderServices: List<IPlaceholderService> = emptyList()

    override fun onInitialize() {
        Utils.printInfo("Initializing ImpactorPlaceholders...")
        INSTANCE = this

        ServerLifecycleEvents.SERVER_STARTING.register(ServerLifecycleEvents.ServerStarting { server ->
            this.server = server
            this.adventure = FabricServerAudiences.of(
                server!!
            )
            Utils.printInfo("Registering placeholders...")

            this.placeholderServices = PlaceholderServices.getActiveServices()
            registerPlaceholders()
            Utils.printInfo("All placeholders now registered!")
        })
    }

    private fun registerPlaceholders() {
        // SERVER PLACEHOLDERS
        Stream.of(
            CurrencySingular(),
            CurrencyPlural(),
            CurrencySymbol(),
        ).forEach { placeholder -> placeholderServices.forEach { it.registerServer(placeholder) } }

        // PLAYER PLACEHOLDERS
        Stream.of(
            Balance(),
            BalanceShort(),
            BalanceStrip(),
            BalanceFormatted(),
            BalanceFormattedStrip(),
            BalanceShort(),
            BalanceShortStrip(),
        ).forEach { placeholder -> placeholderServices.forEach { it.registerPlayer(placeholder) } }

        placeholderServices.forEach { it.finalizeRegister() }
    }
}
