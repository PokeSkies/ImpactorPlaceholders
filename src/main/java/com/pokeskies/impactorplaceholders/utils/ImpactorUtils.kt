package com.pokeskies.impactorplaceholders.utils

import com.pokeskies.impactorplaceholders.utils.Utils.printError
import net.impactdev.impactor.api.economy.EconomyService
import net.impactdev.impactor.api.economy.accounts.Account
import net.impactdev.impactor.api.economy.currency.Currency
import net.kyori.adventure.key.Key
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.CompletableFuture

object ImpactorUtils {
    private var currencyFormatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT)

    init {
        currencyFormatter.maximumFractionDigits = 1
    }

    fun balance(player: ServerPlayer, currency: Currency) : BigDecimal {
        return getAccount(player.uuid, currency).thenCompose {
            CompletableFuture.completedFuture(it.balance())
        }.join()
    }

    private fun getAccount(uuid: UUID, currency: Currency): CompletableFuture<Account> {
        return EconomyService.instance().account(currency, uuid)
    }

    fun getCurrency(id: String?) : Currency? {
        if (id.isNullOrEmpty()) return null

        val key = if (id.contains(":")) id else ResourceLocation.fromNamespaceAndPath("impactor", id).toString()

        val currency: Optional<Currency> = EconomyService.instance().currencies().currency(Key.key(key))
        if (currency.isEmpty) {
            printError("Could not find a currency by the ID $id! Valid currencies: " +
                    "${EconomyService.instance().currencies().registered().map { it.key() } }")
            return null
        }

        return currency.get()
    }

    private fun createCompactFormatter(decimals: Int): NumberFormat {
        val formatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT)
        formatter.maximumFractionDigits = decimals
        formatter.minimumFractionDigits = decimals
        formatter.roundingMode = RoundingMode.DOWN
        return formatter
    }

    fun formatShort(value: BigDecimal, decimals: Int?, strip: Boolean = false): String {
        var formatted = createCompactFormatter(decimals ?: 2).format(value)

        if (strip) {
            val index = formatted.indexOfLast { it.isDigit() || it == '.' }
            if (index >= 0 && index < formatted.length - 1) {
                val whole = formatted.substring(0, index + 1)
                val trimmedWhole = if (whole.contains('.')) whole.trimEnd('0').trimEnd('.') else whole
                formatted = trimmedWhole + formatted.substring(index + 1)
            } else if (formatted.contains('.')) {
                formatted = formatted.trimEnd('0').trimEnd('.')
            }
        }

        return formatted
    }

    fun formatBigDecimal(value: BigDecimal, decimals: Int? = null, strip: Boolean = false): String {
        val scaled = formatDecimalPlaces(value, decimals)
        val plain = (if (strip) scaled.stripTrailingZeros() else scaled).toPlainString()
        val isNegative = plain.startsWith("-")
        val numberValue = if (isNegative) plain.substring(1) else plain
        val split = numberValue.split('.', limit = 2)
        val fracPart = split.getOrNull(1)

        // Run through the whole front part and split every 3 digits, then combine with commas (reverses then unreverses)
        val grouped = split[0].reversed().chunked(3).joinToString(",").reversed()

        return buildString {
            if (isNegative) append('-')
            append(grouped)
            if (decimals != 0 && fracPart != null) {
                append('.')
                append(fracPart)
            }
        }
    }

    fun formatDecimalPlaces(value: BigDecimal, decimals: Int?): BigDecimal {
        val decimals = decimals ?: 2
        if (decimals < 0) return value
        val scaled = value.setScale(decimals, RoundingMode.DOWN)
        return if (decimals == 0) scaled.stripTrailingZeros() else scaled
    }

    // Returns a pair of (currencyId, integer)
    fun parseArgs(args: List<String>): Pair<String?, Int?> {
        if (args.isEmpty()) return null to null

        var index: Int
        var currency: String?
        var number: Int? = null

        val first = args[0]

        // If the first argument is an int, then we just have an integer value return
        first.toIntOrNull()?.let {
            return null to it
        }

        if (args.size >= 2) {
            // If second argument is an int, then we have a simple currency to int
            args[1].toIntOrNull()?.let {
                currency = first
                return currency to it
            }

            // If it wasnt an int, then the first two values are the currency
            currency = "$first:${args[1]}"
            index = 2
        } else {
            // Then we must only have one value, which is the currency
            currency = first
            index = 1
        }

        // Check if there's a remaining argument we havent processed, which would be the int value
        if (index < args.size) {
            number = args[index].toIntOrNull()
        }

        return currency to number
    }
}