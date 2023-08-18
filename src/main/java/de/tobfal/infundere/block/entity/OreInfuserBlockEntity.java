package de.tobfal.infundere.block.entity;

import de.tobfal.infundere.block.menu.OreInfuserMenu;
import de.tobfal.infundere.init.Config;
import de.tobfal.infundere.init.InfunderePacketHandler;
import de.tobfal.infundere.init.ModBlockEntities;
import de.tobfal.infundere.network.ClientboundOreInfuserResourcesPacket;
import de.tobfal.infundere.recipe.OreInfuserRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OreInfuserBlockEntity extends BlockEntity implements MenuProvider, ITickableBlockEntity {

    List<Block> infusibleBlocks = Arrays.asList(
            Blocks.STONE,
            Blocks.DEEPSLATE,
            Blocks.NETHERRACK
    );

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
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
                };
            }
            else if (slot == 1 && (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0)) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };

    protected final ContainerData data;
    public int processTime = 0;
    public int maxProcessTime = Config.ORE_INFUSER_PROCESS_TIME.get();
    public Block blockAbove;

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

    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.literal("Ore Infuser");
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

    public OreInfuserMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
        return new OreInfuserMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide()) {
            return;
        }

        BlockPos posAbove = this.getBlockPos().above();
        Block blockAbove = level.getBlockState(posAbove).getBlock();

        ResourceLocation resourceLocation = null;
        if (!isBlockIngredientOrOutput(blockAbove, this.level)) {
            this.blockAbove = null;
        } else {
            this.blockAbove = blockAbove;
            resourceLocation = ForgeRegistries.BLOCKS.getKey(blockAbove);
            if (resourceLocation != null) {
                resourceLocation = resourceLocation.withPrefix("textures/block/").withSuffix(".png");
            }
        }

        InfunderePacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.worldPosition)),
                new ClientboundOreInfuserResourcesPacket(resourceLocation));

        OreInfuserRecipe recipe = getRecipe();
        if (recipe == null) {
            resetProcess();
            return;
        }

        this.processTime++;
        if (this.processTime < this.maxProcessTime) {
            return;
        }

        resetProcess();

        BlockState blockState = recipe.getOutputBlock().defaultBlockState();

        this.itemHandler.extractItem(0, 1, false);

        level.removeBlock(posAbove, true);
        level.setBlock(posAbove, blockState, 1);
    }

    private OreInfuserRecipe getRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert level != null;
        Optional<OreInfuserRecipe> match = level.getRecipeManager().getRecipeFor(OreInfuserRecipe.Type.INSTANCE, inventory, level);
        if (match.isEmpty()) {
            return null;
        }

        Block ingredientBlock = match.get().getIngredientBlock();
        if (ingredientBlock == null) {
            return null;
        }

        BlockPos posAbove = getBlockPos().above();
        Block blockAbove = level.getBlockState(posAbove).getBlock();
        if (blockAbove != ingredientBlock) {
            return null;
        };

        return match.get();
    }

    private void resetProcess() {
        this.processTime = 0;
    }

    private static boolean isBlockIngredientOrOutput(Block block, Level level) {
        return level.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == OreInfuserRecipe.Type.INSTANCE).map(OreInfuserRecipe.class::cast)
                .anyMatch(recipe -> recipe.hasBlockIngredient(block, level) || recipe.hasBlockOutput(block, level));
    }
}
