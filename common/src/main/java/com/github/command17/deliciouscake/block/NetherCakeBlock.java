package com.github.command17.deliciouscake.block;

import com.github.command17.deliciouscake.DeliciousCake;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NetherCakeBlock extends EffectCakeBlock {
    public NetherCakeBlock(Properties properties) {
        super(() -> List.of(
                new MobEffectInstance(
                        MobEffects.FIRE_RESISTANCE,
                        DeliciousCake.SERVER_CONFIG.netherCakeFireResistanceDuration.get(),
                        DeliciousCake.SERVER_CONFIG.netherCakeFireResistanceAmplifier.get()
                )
        ), properties);
    }

    @Override
    protected void addParticlesAndSound(Level level, Vec3 offset, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3f) {
            level.addParticle(ParticleTypes.SMOKE, offset.x, offset.y + 0.25, offset.z, 0, 0, 0);
            if (f < 0.17f) {
                level.playLocalSound(offset.x + 0.5, offset.y + 0.5, offset.z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1 + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            }
        }

        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, offset.x, offset.y + 0.05, offset.z, 0, 0, 0);
    }
}
