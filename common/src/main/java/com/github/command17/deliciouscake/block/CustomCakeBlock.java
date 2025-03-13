package com.github.command17.deliciouscake.block;

import com.github.command17.deliciouscake.block.entity.CustomCakeBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomCakeBlock extends BaseEntityBlock {
    public static final int MAX_BITES = 6;
    public static final MapCodec<CustomCakeBlock> CODEC = simpleCodec(CustomCakeBlock::new);
    public static final IntegerProperty BITES = BlockStateProperties.BITES;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final Vec3 PARTICLE_OFFSET = new Vec3(0.5, 1, 0.5);
    public static final VoxelShape CANDLE_SHAPE = Block.box(7, 8, 7, 9, 14, 9);
    public static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{
            Block.box(1, 0, 1, 15, 8, 15), // Slice 0
            Block.box(3, 0, 1, 15, 8, 15), // Slice 1
            Block.box(5, 0, 1, 15, 8, 15), // Slice 2
            Block.box(7, 0, 1, 15, 8, 15), // Slice 3
            Block.box(9, 0, 1, 15, 8, 15), // Slice 4
            Block.box(11, 0, 1, 15, 8, 15), // Slice 5
            Block.box(13, 0, 1, 15, 8, 15) // Slice 6
    };

    public CustomCakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BITES, 0).setValue(LIT, false));
    }

    private static boolean candleHit(BlockHitResult hit) {
        return hit.getLocation().y - hit.getBlockPos().getY() > 0.5f;
    }

    @NotNull
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES, LIT);
    }

    @NotNull
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @NotNull
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = SHAPE_BY_BITE[state.getValue(BITES)];
        if (level.getBlockEntity(pos) instanceof CustomCakeBlockEntity blockEntity && blockEntity.hasCandle()) {
            shape = Shapes.or(shape, CANDLE_SHAPE);
        }

        return shape;
    }

    @NotNull
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Item item = stack.getItem();

        if (level.getBlockEntity(pos) instanceof CustomCakeBlockEntity blockEntity) {
            // Candle Lighting
            if (blockEntity.hasCandle()) {
                if (!stack.is(Items.FLINT_AND_STEEL) && !stack.is(Items.FIRE_CHARGE)) {
                    if (candleHit(hitResult) && stack.isEmpty() && blockEntity.isLit()) {
                        blockEntity.setLit(false);
                        level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
                        return ItemInteractionResult.sidedSuccess(level.isClientSide);
                    } else {
                        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
                    }
                } else if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE) && !blockEntity.isLit()) {
                    stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    if (stack.is(Items.FLINT_AND_STEEL)) {
                        level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1);
                    } else {
                        level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1, 1);
                    }

                    blockEntity.setLit(true);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    player.awardStat(Stats.ITEM_USED.get(item));
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                }
            }

            // Candle Placing
            if (stack.is(ItemTags.CANDLES) && state.getValue(BITES) == 0 && !blockEntity.hasCandle()) {
                Block block = Block.byItem(item);

                if (block instanceof CandleBlock candleBlock) {
                    stack.consume(1, player);
                    level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1, 1);
                    blockEntity.setCandle(candleBlock);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    player.awardStat(Stats.ITEM_USED.get(item));
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @NotNull
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            if (eat(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return eat(level, pos, state, player);
    }

    protected InteractionResult eat(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getFoodData().eat(this.getFoodLevelModifier(), this.getSaturationLevelModifier());
            level.gameEvent(player, GameEvent.EAT, pos);
            int bites = state.getValue(BITES);
            if (bites < MAX_BITES) {
                if (level.getBlockEntity(pos) instanceof CustomCakeBlockEntity blockEntity) {
                    blockEntity.drop();
                    state = blockEntity.getLitState(false, state);
                    blockEntity.setCandle(null);
                }

                level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
            } else {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            this.onEat(state, level, pos, player);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof CustomCakeBlockEntity blockEntity) {
                blockEntity.drop();
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof CustomCakeBlockEntity blockEntity && blockEntity.hasCandle() && blockEntity.isLit()) {
            this.addParticlesAndSound(level, PARTICLE_OFFSET.add(pos.getX(), pos.getY(), pos.getZ()), level.random);
        }
    }

    protected void addParticlesAndSound(Level level, Vec3 offset, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3f) {
            level.addParticle(ParticleTypes.SMOKE, offset.x, offset.y, offset.z, 0, 0, 0);
            if (f < 0.17f) {
                level.playLocalSound(offset.x + 0.5, offset.y + 0.5, offset.z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1 + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            }
        }

        level.addParticle(ParticleTypes.SMALL_FLAME, offset.x, offset.y, offset.z, 0, 0, 0);
    }

    @NotNull
    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return CakeBlock.getOutputSignal(state.getValue(BITES));
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    public void onEat(BlockState state, Level level, BlockPos pos, Player player) {}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomCakeBlockEntity(pos, state);
    }

    public int getFoodLevelModifier() {
        return 2;
    }

    public float getSaturationLevelModifier() {
        return 0.1f;
    }
}
