package org.gabalus.energyshield.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.gabalus.energyshield.EnergyShield;

@EventBusSubscriber(modid = EnergyShield.MODID)
public final class ESNetwork {
    private static final String PROTOCOL = "1";

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(EnergyShield.MODID).versioned(PROTOCOL);
        registrar.playToClient(SyncESPacket.TYPE, SyncESPacket.CODEC, SyncESPacket::handle);
    }

    public static void sendToClient(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }
}
