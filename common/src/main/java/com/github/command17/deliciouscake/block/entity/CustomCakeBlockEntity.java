package com.github.command17.deliciouscake.block.entity;

import com.github.command17.deliciouscake.block.CustomCakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomCakeBlockEntity extends BlockEntity {
    @Nullable
    private CandleBlock candle;

    public CustomCakeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CUSTOM_CAKE.get(), pos, blockState);
    }

    public void drop() {
        if (this.hasCandle()) {
            ItemStack stack = new ItemStack(BlockItem.byBlock(this.candle));
            Block.popResource(this.getLevel(), this.getBlockPos(), stack);
        }
    }

    public boolean hasCandle() {
        return this.candle != null;
    }

    @Nullable
    public CandleBlock getCandle() {
        return candle;
    }

    public boolean isLit() {
        return this.getBlockState().getValue(CustomCakeBlock.LIT);
    }

    public void setCandle(@Nullable CandleBlock candle) {
        this.candle = candle;
    }

    // Helper method for getting the lit block state (makes stuff easier to read)
    public BlockState getLitState(boolean lit, BlockState state) {
        return state.setValue(CustomCakeBlock.LIT, lit);
    }

    // Helper method for setting the lit block state
    public void setLit(boolean lit) {
        this.level.setBlock(this.getBlockPos(), this.getLitState(lit, this.getBlockState()), 3);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        Block candleBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(tag.getString("Candle")));
        if (candleBlock instanceof CandleBlock) {
            this.candle = (CandleBlock) candleBlock;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        this.saveSyncedData(tag);
    }

    private void saveSyncedData(CompoundTag tag) {
        if (this.candle != null) {
            tag.putString("Candle", BuiltInRegistries.BLOCK.getKey(this.candle).toString());
        }
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveSyncedData(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
