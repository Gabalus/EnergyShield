package org.gabalus.energyshield;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ESConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    static final ModConfigSpec SPEC = BUILDER.build();

    public static final ModConfigSpec.DoubleValue MAX;
    public static final ModConfigSpec.DoubleValue RECHARGE_RATE;
    public static final ModConfigSpec.DoubleValue RECHARGE_DELAY;
    public static final ModConfigSpec.DoubleValue BREAK_THRESHOLD;
    public static final ModConfigSpec.DoubleValue ON_KILL_GAIN;

    static {

        BUILDER.push("EnergyShield");

        MAX = BUILDER.comment("Default max shield capacity")
                .defineInRange("max", 5.0, 0.0, 1024.0);

        RECHARGE_RATE = BUILDER.comment("Default recharge rate (shield units per second)")
                .defineInRange("recharge_rate", 5.0, 0.0, 1024.0);

        RECHARGE_DELAY = BUILDER.comment("Default recharge delay (ticks)")
                .defineInRange("recharge_delay", 60.0, 0.0, 20 * 60 * 60);

        BREAK_THRESHOLD = BUILDER.comment("Damage required to reset recharge delay")
                .defineInRange("break_threshold", 1.0, 0.0, 1024.0);

        ON_KILL_GAIN = BUILDER.comment("Shield gained on kill")
                .defineInRange("on_kill_gain", 5.0, 0.0, 1024.0);
    }
}
