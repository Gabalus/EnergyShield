package org.gabalus.energyshield.registry;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import org.gabalus.energyshield.EnergyShield;

@EventBusSubscriber(modid = EnergyShield.MODID)
public final class ESAttributeInit {
    private ESAttributeInit() {}

    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.MAX);
        event.add(EntityType.PLAYER, ModAttributes.RECHARGE_RATE);
        event.add(EntityType.PLAYER, ModAttributes.RECHARGE_DELAY);
        event.add(EntityType.PLAYER, ModAttributes.BREAK_THRESHOLD);
        event.add(EntityType.PLAYER, ModAttributes.ON_KILL_GAIN);
    }
}
