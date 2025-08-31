package org.gabalus.energyshield.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.gabalus.energyshield.EnergyShield;

@EventBusSubscriber(modid = EnergyShield.MODID, value = Dist.CLIENT)
public final class ClientOverlayInit {
    @SubscribeEvent
    public static void onRegisterLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.PLAYER_HEALTH,
                ResourceLocation.fromNamespaceAndPath(EnergyShield.MODID, "energy_shield"),
                ESHudOverlay::render // DeltaTracker
        );
    }
}
