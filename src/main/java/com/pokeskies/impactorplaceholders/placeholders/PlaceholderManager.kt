package com.pokeskies.impactorplaceholders.placeholders

import com.pokeskies.impactorplaceholders.placeholders.services.ImpactorPlaceholderService
import com.pokeskies.impactorplaceholders.placeholders.services.MiniPlaceholdersService
import com.pokeskies.impactorplaceholders.placeholders.services.PlaceholderAPIService

class PlaceholderManager {
    private val services: MutableList<IPlaceholderService> = mutableListOf()

    init {
        for (service in PlaceholderMod.values()) {
            if (service.isModPresent()) {
                services.add(getServiceForType(service))
            }
        }
    }

    fun registerPlaceholders() {
        for (service in services) {
            service.registerPlaceholders()
        }
    }

    private fun getServiceForType(placeholderMod: PlaceholderMod): IPlaceholderService {
        return when (placeholderMod) {
            PlaceholderMod.IMPACTOR -> ImpactorPlaceholderService()
            PlaceholderMod.PLACEHOLDERAPI -> PlaceholderAPIService()
            PlaceholderMod.MINIPLACEHOLDERS -> MiniPlaceholdersService()
        }
    }
}