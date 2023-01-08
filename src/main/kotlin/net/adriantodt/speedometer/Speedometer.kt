package net.adriantodt.speedometer;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.adriantodt.speedometer.data.SpeedometerConfig;
import net.adriantodt.speedometer.item.SpeedometerItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Rarity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Mod entry point
@Suppress("MemberVisibilityCanBePrivate")
object Speedometer : ModInitializer {
    // Logger for the mod
    val logger: Logger = LogManager.getLogger(Speedometer.javaClass)

    // Holder for the mod's config values
    val configHolder: ConfigHolder<SpeedometerConfig> =
        AutoConfig.register(SpeedometerConfig::class.java, ::JanksonConfigSerializer)

    // Instance of the speedometer item
    val speedometer = SpeedometerItem(itemSettings().maxCount(1).rarity(Rarity.UNCOMMON))

    // Called when the mod is initialized
    override fun onInitialize() {
        // Registers the speedometer item in the game registry
        identifier("speedometer").item(speedometer)
    }
}
