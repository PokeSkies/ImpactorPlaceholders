package com.pokeskies.impactorplaceholders

import com.pokeskies.impactorplaceholders.placeholders.PlaceholderManager
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.kyori.adventure.platform.fabric.FabricServerAudiences
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ImpactorPlaceholders : ModInitializer {
    companion object {
        lateinit var INSTANCE: ImpactorPlaceholders
        val LOGGER: Logger = LogManager.getLogger("impactorplaceholders")
    }

    lateinit var placeholderManager: PlaceholderManager
    var adventure: FabricServerAudiences? = null

    override fun onInitialize() {
        INSTANCE = this

        this.placeholderManager = PlaceholderManager()

        ServerLifecycleEvents.SERVER_STARTING.register(ServerLifecycleEvents.ServerStarting { server ->
            this.adventure = FabricServerAudiences.of(
                server!!
            )
            placeholderManager.registerPlaceholders()
        })
        ServerLifecycleEvents.SERVER_STOPPED.register(ServerLifecycleEvents.ServerStopped { _ ->
            this.adventure = null
        })
    }
}
