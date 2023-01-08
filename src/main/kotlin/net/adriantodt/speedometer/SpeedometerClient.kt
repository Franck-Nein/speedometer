package net.adriantodt.speedometer

import net.adriantodt.speedometer.Speedometer.speedometer
import net.adriantodt.speedometer.item.SpeedometerModelPredicateProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry

// Class that initializes the mod on the client side
object SpeedometerClient : ClientModInitializer {
    // Called when the mod is initialized on the client side
    override fun onInitializeClient() {
        // Registers a model predicate provider for the speedometer item
        FabricModelPredicateProviderRegistry.register(
            speedometer, identifier("speed"), SpeedometerModelPredicateProvider
        );
    }
}

