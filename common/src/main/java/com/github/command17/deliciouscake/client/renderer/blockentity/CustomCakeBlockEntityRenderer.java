package com.github.command17.deliciouscake.client.renderer.blockentity;

import com.github.command17.deliciouscake.block.entity.CustomCakeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Environment(EnvType.CLIENT)
public class CustomCakeBlockEntityRenderer<T extends CustomCakeBlockEntity> implements BlockEntityRenderer<T> {
    private final BlockRenderDispatcher blockRender;

    public CustomCakeBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        CandleBlock candleBlock = blockEntity.getCandle();
        poseStack.pushPose();
        poseStack.translate(0, 0.5, 0);
        if (candleBlock != null) {
            BlockState candleState = candleBlock.defaultBlockState().setValue(BlockStateProperties.LIT, blockEntity.isLit());
            this.blockRender.renderSingleBlock(candleState, poseStack, bufferSource, packedLight, packedOverlay);
        }

        poseStack.popPose();
    }
}
