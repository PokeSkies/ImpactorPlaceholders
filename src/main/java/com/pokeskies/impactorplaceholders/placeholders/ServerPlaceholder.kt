package com.pokeskies.impactorplaceholders.placeholders

interface ServerPlaceholder {
    fun handle(args: List<String>): GenericResult
    fun ids(): List<String>
}
