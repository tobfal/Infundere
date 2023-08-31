package de.tobfal.infundere.datagen;

import de.tobfal.infundere.Infundere;
import de.tobfal.infundere.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Infundere.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.COAL_INFUSION_POWDER);
        simpleItem(ModItems.COPPER_INFUSION_POWDER);
        simpleItem(ModItems.DIAMOND_INFUSION_POWDER);
        simpleItem(ModItems.EMERALD_INFUSION_POWDER);
        simpleItem(ModItems.GOLD_INFUSION_POWDER);
        simpleItem(ModItems.IRON_INFUSION_POWDER);
        simpleItem(ModItems.LAPIS_LAZULI_INFUSION_POWDER);
        simpleItem(ModItems.REDSTONE_INFUSION_POWDER);
        simpleItem(ModItems.GLOWSTONE_INFUSION_POWDER);
        simpleItem(ModItems.NETHER_QUARTZ_INFUSION_POWDER);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Infundere.MODID, "item/" + item.getId().getPath()));
    }
}
