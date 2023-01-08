package net.adriantodt.speedometer.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.adriantodt.speedometer.data.SpeedometerConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Class that integrates the mod with the ModMenu mod
@Environment(EnvType.CLIENT)
class ModMenuIntegration : ModMenuApi {
    // Returns a config screen factory for the mod's config screen
    override fun getModConfigScreenFactory() = ConfigScreenFactory {
        // Create the config screen for the mod's config values
        AutoConfig.getConfigScreen(SpeedometerConfig::class.java, it).get();
    }
}

