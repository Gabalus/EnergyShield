package org.gabalus.energyshield;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.gabalus.energyshield.registry.ModAttributes;

@Mod(EnergyShield.MODID)
public class EnergyShield {
    public static final String MODID = "energyshield";

    public EnergyShield(IEventBus modBus, ModContainer modContainer) {
        ModAttributes.REGISTER.register(modBus);
        //modContainer.registerConfig(ModConfig.Type.COMMON, ESConfig.SPEC);


    }
}
