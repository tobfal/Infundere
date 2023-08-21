package de.tobfal.infundere.block.entity;

import de.tobfal.infundere.block.menu.BreacerMenu;
import de.tobfal.infundere.init.ModBlockEntities;
import de.tobfal.infundere.utils.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class BreacerBlockEntity extends BlockEntity implements MenuProvider, ITickableBlockEntity {

    //<editor-fold desc="Properties">
    public final ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot >= 6) {
                return super.insertItem(slot, stack, simulate);
            }

            if (!(stack.getItem() instanceof BlockItem)) {
                return stack;
            }

            return super.insertItem(slot, stack, simulate);
        }
    };

    protected final ContainerData data;
    public int processTime = 0;
    public int maxProcessTime = 50;
    private Block lastBlockSet = Blocks.AIR;
    private final BlockPos blockPosToBreak = this.getBlockPos().relative(getBlockState().getValue(BlockStateProperties.FACING));
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BreacerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BREACER.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BreacerBlockEntity.this.processTime;
                    case 1 -> BreacerBlockEntity.this.maxProcessTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BreacerBlockEntity.this.processTime = pValue;
                    case 1 -> BreacerBlockEntity.this.maxProcessTime = pValue;
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
        return Component.translatable("block.infundere.breacer");
    }

    @Nullable
    @Override
    public BreacerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new BreacerMenu(pContainerId, pPlayerInventory, this, this.data);
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
    public void tick() {
        if (this.level == null || this.level.isClientSide()) {
            return;
        }

        boolean hasBrokenBlock = breakTick();
        boolean hasPlacedBlock = placeTick();

        if (this.processTime < this.maxProcessTime) {
            this.processTime++;
            return;
        }

        if (hasBrokenBlock || hasPlacedBlock) {
            resetProcess();
        }
    }

    public boolean breakTick() {
        assert level != null;
        Block blockToBreak = level.getBlockState(this.blockPosToBreak).getBlock();

        if (blockToBreak == this.lastBlockSet) {
            return false;
        }

        this.lastBlockSet = Blocks.AIR;

        if (blockToBreak == Blocks.AIR) {
            return false;
        }

        if (this.processTime < this.maxProcessTime) {
            return false;
        }

        List<ItemStack> drops = BlockUtils.getBlockDrops(level, this.blockPosToBreak);

        for (ItemStack drop : drops) {
            for (int i = 0; i < 6; i++) {
                drop = this.itemHandler.insertItem(6 + i, drop, false);
                if (drop.isEmpty()) break;
            }
        }
        this.level.setBlockAndUpdate(this.blockPosToBreak, Blocks.AIR.defaultBlockState());
        return true;
    }

    public boolean placeTick() {
        int slot = 0;
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < 6; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof final BlockItem blockItem) {
                slot = i;
                itemStack = stack;
                break;
            }
        }

        if (itemStack == ItemStack.EMPTY) {
            return false;
        }

        assert level != null;
        Block block = ((BlockItem)itemStack.getItem()).getBlock();
        Block blockObstructing = level.getBlockState(this.blockPosToBreak).getBlock();

        if (!blockObstructing.equals(Blocks.AIR)) {
            return false;
        }

        if (this.processTime < this.maxProcessTime) {
            return false;
        }

        this.itemHandler.extractItem(slot, 1, false);
        this.level.setBlockAndUpdate(this.blockPosToBreak, block.defaultBlockState());
        this.lastBlockSet = block;
        return true;
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
