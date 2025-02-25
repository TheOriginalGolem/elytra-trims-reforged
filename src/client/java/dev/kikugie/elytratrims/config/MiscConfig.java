package dev.kikugie.elytratrims.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kikugie.elytratrims.ElytraTrimsMod;
import net.minecraft.text.Text;

public class MiscConfig {
    public static final Codec<MiscConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("lockDefaultPack").forGetter(state -> state.lockDefaultPack.value)
    ).apply(instance, MiscConfig::new));
    public final BooleanEntry lockDefaultPack;

    public MiscConfig(boolean lockDefaultPack) {
        this.lockDefaultPack = new BooleanEntry(lockDefaultPack, "lock_pack");
    }

    public static MapCodec<MiscConfig> getCodec() {
        return CODEC.fieldOf("misc");
    }

    private void save() {
        ElytraTrimsMod.getConfigState().save();
    }

    public static class BooleanEntry {
        private final String translation;
        public boolean value;

        public BooleanEntry(boolean value, String name) {
            this.value = value;
            this.translation = "elytratrims.config.misc." + name;
        }

        public Text getName() {
            return Text.translatable(translation);
        }

        public Text getTooltip() {
            return Text.translatable(translation + ".tooltip");
        }
    }
}
