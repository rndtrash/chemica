package team.teasanctuary.chemica.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import team.teasanctuary.chemica.api.MachineBlock;
import team.teasanctuary.chemica.entities.BeehiveOvenControlBlockEntity;

import java.util.Random;

public class BeehiveOvenControlBlock extends MachineBlock implements BlockEntityProvider {
    public static final Identifier ID = new Identifier("chemica", "beehive_oven_control");
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
    public static final BooleanProperty BURNING = BooleanProperty.of("burning");

    public BeehiveOvenControlBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState()
                .with(LIT, false)
                .with(BURNING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.LIT);
        builder.add(BURNING);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(BURNING)) {
            double d = (double)pos.getX() + 0.5D;
            double e = (double)pos.getY() + 0.4D;
            double f = (double)pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double g = 0.52D;
            double h = random.nextDouble() * 0.6D - 0.3D;
            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * g : h;
            double j = random.nextDouble() * 6.0D / 16.0D;
            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * g : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite())
                .with(LIT, false)
                .with(BURNING, false);
    }

    @Override
    public int getLuminance(BlockState state) {
        return state.get(LIT) ? super.getLuminance(state) : 0;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new BeehiveOvenControlBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.SUCCESS;

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof BeehiveOvenControlBlockEntity))
            return ActionResult.SUCCESS;

        BeehiveOvenControlBlockEntity bocbe = (BeehiveOvenControlBlockEntity) be;
        bocbe.checkStructure();
        ContainerProviderRegistry.INSTANCE.openContainer(BeehiveOvenControlBlock.ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));

        player.swingHand(Hand.MAIN_HAND);
        return ActionResult.SUCCESS;
    }
}
