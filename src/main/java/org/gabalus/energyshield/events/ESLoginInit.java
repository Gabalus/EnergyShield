package org.gabalus.energyshield.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import org.gabalus.energyshield.EnergyShield;
import org.gabalus.energyshield.network.ESNetwork;
import org.gabalus.energyshield.network.SyncESPacket;
import org.gabalus.energyshield.registry.ModAttributes;

@EventBusSubscriber(modid = EnergyShield.MODID, value = Dist.DEDICATED_SERVER)
public final class ESLoginInit {
    private static final String ES_CUR = "es_current";

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;

        var tag = sp.getPersistentData();
        if (!tag.contains(ES_CUR)) {
            tag.putDouble(ES_CUR, 0.0);
        }

        double cur = tag.getDouble(ES_CUR);
        double max = sp.getAttributeValue(ModAttributes.MAX);
        ESNetwork.sendToClient(sp, new SyncESPacket(cur, max));
    }
}
