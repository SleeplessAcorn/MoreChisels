package info.sleeplessacorn.morechisels;

/*
 *  Copyright 2017 Sleepless Acorn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import info.sleeplessacorn.morechisels.chisel.ItemChiselOreDict;
import info.sleeplessacorn.morechisels.util.OreDictHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import team.chisel.common.config.Configurations;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MoreChisels.MOD_ID)
public class ChiselRegistry {

    public static final Map<String, ItemStack> INGOTS = new HashMap<>();
    public static final Map<String, ItemStack> GEMS = new HashMap<>();

    public static final ItemChiselOreDict CHISEL_INGOT = new ItemChiselOreDict("ingot", INGOTS, Configurations.ironChiselMaxDamage, true, false);
    public static final ItemChiselOreDict CHISEL_GEM = new ItemChiselOreDict("gem", GEMS, Configurations.diamondChiselMaxDamage, true, true);

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(CHISEL_INGOT);
        event.getRegistry().register(CHISEL_GEM);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        registerOreChiselModel(CHISEL_INGOT, CHISEL_GEM);
    }

    public void registerColorHandler() {}

    public void onOreRegistry(FMLInitializationEvent event) {
        OreDictHelper.getAllFromPrefix("ingot").forEach(entry -> {
            NonNullList<ItemStack> items = OreDictionary.getOres(entry);
            if (!ConfigManager.isBlacklisted(entry) && OreDictHelper.hasItems(items)) {
                INGOTS.put(entry, items.get(0));
            }
        });
        OreDictHelper.getAllFromPrefix("gem").forEach(entry -> {
            NonNullList<ItemStack> items = OreDictionary.getOres(entry);
            if (!ConfigManager.isBlacklisted(entry) && OreDictHelper.hasItems(items)) {
                GEMS.put(entry, items.get(0));
            }
        });
    }

    public void onRecipeRegistry(FMLPostInitializationEvent event) {
        registerChiselOreRecipes(CHISEL_INGOT, CHISEL_GEM);
    }

    @SideOnly(Side.CLIENT)
    private static void registerOreChiselModel(ItemChiselOreDict... chisels) {
        for (ItemChiselOreDict chisel : chisels) {
            ResourceLocation name = new ResourceLocation(MoreChisels.MOD_ID, "chisel_ore");
            ModelResourceLocation mrl = new ModelResourceLocation(name, "inventory");
            ModelLoader.setCustomModelResourceLocation(chisel, 0, mrl);
        }
    }

    private static void registerChiselOreRecipes(ItemChiselOreDict... chisels) {
        for (ItemChiselOreDict chisel : chisels) {
            chisel.getOreMap().forEach((ore, stack) -> {
                ResourceLocation group = new ResourceLocation(MoreChisels.MOD_ID, "chisel");
                ResourceLocation name = new ResourceLocation(MoreChisels.MOD_ID, "chisel_" + OreDictHelper.format(ore));
                ItemStack variant = new ItemStack(chisel);
                variant.setTagInfo("ore", new NBTTagString(ore));
                GameRegistry.addShapedRecipe(name, group, variant, " O", "S ", 'O', ore, 'S', "stickWood");
            });
        }
    }

}
