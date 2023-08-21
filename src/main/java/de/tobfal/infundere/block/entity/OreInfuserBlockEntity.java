package de.tobfal.infundere.block.entity;

import de.tobfal.infundere.block.menu.OreInfuserMenu;
import de.tobfal.infundere.init.InfunderePacketHandler;
import de.tobfal.infundere.init.ModBlockEntities;
import de.tobfal.infundere.network.ClientboundOreInfuserResourcesPacket;
import de.tobfal.infundere.recipe.OreInfuserRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class OreInfuserBlockEntity extends BlockEntity implements MenuProvider, ITickableBlockEntity, GeoBlockEntity {

    //<editor-fold desc="Constants">
    private static final RawAnimation PISTON_ANIMS = RawAnimation.begin().thenPlay("ore_infuser.piston");
    //</editor-fold>

    //<editor-fold desc="Properties">
    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot == 0) {
                SimpleContainer inventory = new SimpleContainer(this.getSlots());
                inventory.setItem(0, stack);
                assert level != null;
                if (level.getRecipeManager().getRecipeFor(OreInfuserRecipe.Type.INSTANCE, inventory, level).isEmpty()) {
                    return stack;
                }
            }
            return super.insertItem(slot, stack, simulate);
        }
    };

    protected final ContainerData data;
    public int processTime = 0;
    public int maxProcessTime = 1000;
    public Block blockAbove;
    public ResourceLocation processBackgroundResourceLocation;
    public ResourceLocation processResourceLocation;
    public OreInfuserRecipe currentRecipe;
    public boolean playAnimation;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public OreInfuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ORE_INFUSER.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> OreInfuserBlockEntity.this.processTime;
                    case 1 -> OreInfuserBlockEntity.this.maxProcessTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> OreInfuserBlockEntity.this.processTime = pValue;
                    case 1 -> OreInfuserBlockEntity.this.maxProcessTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infundere.ore_infuser");
    }

    @Nullable
    @Override
    public OreInfuserMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new OreInfuserMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        super.load(nbt);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            if (state.getAnimatable().playAnimation) {
                state.resetCurrentAnimation();
                state.getAnimatable().playAnimation = false;
                return state.setAndContinue(PISTON_ANIMS);
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide()) {
            return;
        }

        BlockPos posAbove = this.getBlockPos().above();
        Block blockAbove = level.getBlockState(posAbove).getBlock();

        ResourceLocation processBackgroundResourceLocation = null;
        if (!isBlockIngredientOrOutput(blockAbove, this.level)) {
            this.blockAbove = null;
        } else {
            this.blockAbove = blockAbove;
            processBackgroundResourceLocation = ForgeRegistries.BLOCKS.getKey(blockAbove);
            if (processBackgroundResourceLocation != null) {
                processBackgroundResourceLocation = processBackgroundResourceLocation.withPrefix("textures/block/").withSuffix(".png");
            }
        }

        OreInfuserRecipe recipe = getRecipe(blockAbove);
        if (recipe == null) {
            this.currentRecipe = null;
            resetProcess();
            InfunderePacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.worldPosition)),
                    new ClientboundOreInfuserResourcesPacket(this.getBlockPos(), processBackgroundResourceLocation, null, false));
            return;
        }

        if (recipe != this.currentRecipe) {
            this.currentRecipe = recipe;
            resetProcess();
        }

        this.maxProcessTime = recipe.getProcessTime();
        this.processTime++;

        Objects.requireNonNull(Objects.requireNonNull(this.level.getServer()).getLevel(level.dimension()))
                .sendParticles(ParticleTypes.PORTAL, posAbove.getX() + 0.5f, posAbove.getY() + 0.25f, posAbove.getZ() + 0.5f, 1, 0.0f, 0.0f, 0.0f, 0.3f);

        ResourceLocation processResourceLocation = ForgeRegistries.BLOCKS.getKey(recipe.getResultBlock());
        if (processResourceLocation != null) {
            processResourceLocation = processResourceLocation.withPrefix("textures/block/").withSuffix(".png");
        }

        InfunderePacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.worldPosition)),
                new ClientboundOreInfuserResourcesPacket(this.getBlockPos(), processBackgroundResourceLocation, processResourceLocation, this.processTime >= this.maxProcessTime));

        if (this.processTime < this.maxProcessTime) {
            return;
        }

        resetProcess();

        BlockState blockState = recipe.getResultBlock().defaultBlockState();

        this.itemHandler.extractItem(0, 1, false);

        level.removeBlock(posAbove, true);
        level.setBlock(posAbove, blockState, 1);

        this.level.playSound(null, posAbove, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 10.0f, 1.4f);
    }

    @Nullable
    private static OreInfuserRecipe getRecipeFor(SimpleContainer simpleContainer, Block ingredientBlock, Level level) {
        return level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == OreInfuserRecipe.Type.INSTANCE).map(OreInfuserRecipe.class::cast)
                .filter(recipe -> recipe.matches(simpleContainer, level) && recipe.hasBlockAsIngredient(ingredientBlock, level)).findFirst().orElse(null);
    }

    private static boolean isBlockIngredientOrOutput(Block block, Level level) {
        return level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == OreInfuserRecipe.Type.INSTANCE).map(OreInfuserRecipe.class::cast)
                .anyMatch(recipe -> recipe.hasBlockAsIngredient(block, level) || recipe.hasBlockAsResult(block, level));
    }

    private OreInfuserRecipe getRecipe(Block blockAbove) {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert level != null;
        return getRecipeFor(inventory, blockAbove, level);
    }

    private void resetProcess() {
        this.processTime = 0;
    }

    public void dropContents() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    //</editor-fold>
}
