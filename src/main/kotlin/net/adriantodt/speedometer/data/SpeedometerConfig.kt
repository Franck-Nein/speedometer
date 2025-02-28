package net.adriantodt.speedometer.data;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;

// Class representing the mod's config values
@Config(name = "speedometer")
class SpeedometerConfig : ConfigData {
    // The unit used to display the velocity
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    var velocityUnit = VelocityUnit.METERS_PER_SECOND;

    // Enum representing the possible velocity units
    enum class VelocityUnit(val multiplier: Double, val suffix: String) : SelectionListEntry.Translatable {
        METERS_PER_SECOND(1.0, "m/s"),
        KILOMETERS_PER_HOUR(3.6, "km/h"),
        MILES_PER_HOUR(2.23694, "mph");

        // Returns the translation key for the velocity unit
        override fun getKey(): String = "text.speedometer.velocityUnit.${name.toLowerCase()}";
    }

}

