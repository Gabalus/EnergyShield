package org.gabalus.energyshield.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.gabalus.energyshield.ESConfig;

public class ModAttributes {
    public static final DeferredRegister<Attribute> REGISTER =
            DeferredRegister.create(Registries.ATTRIBUTE, "energyshield");

    public static final DeferredHolder<Attribute, Attribute> MAX = REGISTER.register("max",
            () -> new RangedAttribute("attribute.name.energyshield.max", 0, 0.0D, 1024.0).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> RECHARGE_RATE = REGISTER.register("recharge_rate",
            () -> new RangedAttribute("attribute.name.energyshield.recharge_rate", 1, 0.0D, 1024.0).setSyncable(true)); // per second

    public static final DeferredHolder<Attribute, Attribute> RECHARGE_DELAY = REGISTER.register("recharge_delay",
            () -> new RangedAttribute("attribute.name.energyshield.recharge_delay", 60, 0.0D, 20 * 60 * 60).setSyncable(true)); // ticks

    public static final DeferredHolder<Attribute, Attribute> BREAK_THRESHOLD = REGISTER.register("break_threshold",
            () -> new RangedAttribute("attribute.name.energyshield.break_threshold", 0.1, 0.0D, 1024.0).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> ON_KILL_GAIN = REGISTER.register("on_kill_gain",
            () -> new RangedAttribute("attribute.name.energyshield.on_kill_gain", 0, 0.0D, 1024.0).setSyncable(true));
}
