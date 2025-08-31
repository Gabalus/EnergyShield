package org.gabalus.energyshield.events;

import net.neoforged.fml.common.EventBusSubscriber;
import org.gabalus.energyshield.EnergyShield;
import org.gabalus.energyshield.registry.ModAttributes;
import org.gabalus.energyshield.network.ESNetwork;
import org.gabalus.energyshield.network.SyncESPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = EnergyShield.MODID)
public class ESCommonEvents {
    private static final String ES_CUR = "es_current";
    private static final String ES_LAST_HIT_TICK = "es_last_hit_tick";

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post e) {
        Player p = e.getEntity();
        if (p.level().isClientSide) return;

        double max = p.getAttributeValue(ModAttributes.MAX);
        double cur = p.getPersistentData().getDouble(ES_CUR);
        if (cur > max) {
            p.getPersistentData().putDouble(ES_CUR, max);
            cur = max;
        }

        int now = (int) p.level().getGameTime();
        int lastHit = p.getPersistentData().getInt(ES_LAST_HIT_TICK);
        double delayTicks = p.getAttributeValue(ModAttributes.RECHARGE_DELAY);

        if (now - lastHit >= delayTicks) {
            double ratePerSec = p.getAttributeValue(ModAttributes.RECHARGE_RATE);
            double inc = ratePerSec / 20.0;
            if (inc > 0 && cur < max) {
                cur = Math.min(max, cur + inc);
                p.getPersistentData().putDouble(ES_CUR, cur);
            }
        }

        if (p instanceof ServerPlayer sp && now % 10 == 0) {
            ESNetwork.sendToClient(sp, new SyncESPacket(cur, max));
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Pre e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (p.level().isClientSide) return;

        double amount = e.getNewDamage();
        if (amount <= 0) return;

        double cur = p.getPersistentData().getDouble(ES_CUR);
        if (cur <= 0) {
            markHit(p, amount);
            return;
        }

        double used = Math.min(amount, cur);
        double remaining = amount - used;
        p.getPersistentData().putDouble(ES_CUR, cur - used);

        markHit(p, amount);

        e.setNewDamage((float) remaining);

        if (p instanceof ServerPlayer sp) {
            ESNetwork.sendToClient(sp, new SyncESPacket(
                    p.getPersistentData().getDouble(ES_CUR),
                    p.getAttributeValue(ModAttributes.MAX)
            ));
        }
    }


    @SubscribeEvent
    public static void onDeath(LivingDeathEvent e) {
        if (!(e.getSource().getEntity() instanceof Player p)) return;
        if (p.level().isClientSide) return;

        double gain = p.getAttributeValue(ModAttributes.ON_KILL_GAIN);
        if (gain > 0) {
            double max = p.getAttributeValue(ModAttributes.MAX);
            double cur = p.getPersistentData().getDouble(ES_CUR);
            cur = Math.min(max, cur + gain);
            p.getPersistentData().putDouble(ES_CUR, cur);

            if (p instanceof ServerPlayer sp) {
                ESNetwork.sendToClient(sp, new SyncESPacket(cur, max));
            }
        }
    }

    private static void markHit(Player p, double rawAmount) {
        double threshold = p.getAttributeValue(ModAttributes.BREAK_THRESHOLD);
        if (rawAmount >= threshold) {
            int now = (int) p.level().getGameTime();
            p.getPersistentData().putInt(ES_LAST_HIT_TICK, now);
        }
    }
}
