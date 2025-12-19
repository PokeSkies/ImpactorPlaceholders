package com.pokeskies.impactorplaceholders.placeholders

import com.pokeskies.impactorplaceholders.ImpactorPlaceholders
import com.pokeskies.impactorplaceholders.utils.Utils
import net.kyori.adventure.text.Component

class GenericResult private constructor(
    val component: Component,
    val isSuccessful: Boolean = true
) {
    companion object {
        fun valid(result: Component): GenericResult {
            return GenericResult(result, true)
        }

        fun valid(result: String): GenericResult {
            return valid(Utils.deserializeText(result))
        }

        fun valid(result: Int): GenericResult {
            return valid(result.toString())
        }

        fun valid(result: Boolean): GenericResult {
            return valid(result.toString())
        }

        fun valid(result: Float): GenericResult {
            return valid(result.toString())
        }

        fun valid(result: Double): GenericResult {
            return valid(result.toString())
        }

        fun invalid(result: Component): GenericResult {
            return GenericResult(result, false)
        }

        fun invalid(result: String): GenericResult {
            return invalid(Utils.deserializeText(result))
        }
    }

    fun asString(): String {
        return Utils.plainTextSerializer.serialize(component)
    }

    fun asNative(): net.minecraft.network.chat.Component {
        return ImpactorPlaceholders.INSTANCE.adventure.toNative(component)
    }
}
