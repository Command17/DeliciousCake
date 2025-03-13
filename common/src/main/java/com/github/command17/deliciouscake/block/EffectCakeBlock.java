package com.github.command17.deliciouscake.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Supplier;

public class EffectCakeBlock extends CustomCakeBlock {
    private final Supplier<List<MobEffectInstance>> effectFactory;

    public EffectCakeBlock(Supplier<List<MobEffectInstance>> effectFactory, Properties properties) {
        super(properties);
        this.effectFactory = effectFactory;
    }

    public List<MobEffectInstance> getEffects() {
        return effectFactory.get();
    }

    public Supplier<List<MobEffectInstance>> getEffectFactory() {
        return effectFactory;
    }

    @Override
    public void onEat(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            this.effectFactory.get().forEach(player::addEffect);
        }
    }
}
