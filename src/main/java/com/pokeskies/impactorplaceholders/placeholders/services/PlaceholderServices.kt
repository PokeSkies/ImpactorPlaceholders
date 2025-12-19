package com.pokeskies.impactorplaceholders.placeholders.services

import com.pokeskies.impactorplaceholders.utils.Utils
import net.fabricmc.loader.api.FabricLoader
import kotlin.reflect.full.primaryConstructor

enum class PlaceholderServices(val id: String, val clazz: Class<out IPlaceholderService>) {
    IMPACTOR("impactor", ImpactorPlaceholderService::class.java),
    PLACEHOLDERAPI("placeholder-api", PlaceholderAPIService::class.java),
    MINIPLACEHOLDERS("miniplaceholders", MiniPlaceholdersService::class.java);

    fun isModPresent() : Boolean {
        return FabricLoader.getInstance().isModLoaded(id)
    }

    companion object {
        fun getActiveServices(): List<IPlaceholderService> {
            return PlaceholderServices.entries.filter { it.isModPresent() }.mapNotNull {
                try {
                    it.clazz.kotlin.primaryConstructor!!.call()
                } catch (ex: Exception) {
                    Utils.printError("There was an exception while initializing the Placeholder Service: ${it.id}. Is it loaded?")
                    ex.printStackTrace()
                    null
                }
            }
        }
    }
}