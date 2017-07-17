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

import info.sleeplessacorn.morechisels.RegistryManager.ChiselRegistry;
import info.sleeplessacorn.morechisels.RegistryManager.RecipeRegistry;
import info.sleeplessacorn.morechisels.util.OreDictHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(   modid = MoreChisels.MOD_ID,
        name = MoreChisels.MOD_NAME,
        version = MoreChisels.MOD_VERSION,
        dependencies = MoreChisels.DEPENDENCIES,
        acceptedMinecraftVersions = MoreChisels.MC_VERSION)
public class MoreChisels {

    public static final String
            MOD_ID = "morechisels",
            MOD_NAME = "More Chisels",
            MOD_VERSION = "%mod_version%",
            MC_VERSION = "%mc_version%",
            DEPENDENCIES = "required-after:chisel@[%chisel_version%,);after:*;",
            CLIENT_PROXY = "info.sleeplessacorn.morechisels.util.ColorHandler",
            SERVER_PROXY = "info.sleeplessacorn.morechisels.MoreChisels$ProxyWrapper";

    public static final TabMoreChisels TAB = new TabMoreChisels();
    public static final Logger LOGGER = LogManager.getLogger(MoreChisels.MOD_NAME);

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static ProxyWrapper proxy;

    @Mod.EventHandler
    public void onOreRegistry(FMLInitializationEvent event) {
        for (String entry : OreDictHelper.getAllFromPrefix("ingot")) {
            NonNullList<ItemStack> items = OreDictionary.getOres(entry);
            if (!ConfigManager.isBlacklisted(entry) && OreDictHelper.hasItems(items)) {
                ChiselRegistry.INGOTS.put(entry, items.get(0));
            }
        }
        for (String entry : OreDictHelper.getAllFromPrefix("gem")) {
            NonNullList<ItemStack> items = OreDictionary.getOres(entry);
            if (!ConfigManager.isBlacklisted(entry) && OreDictHelper.hasItems(items)) {
                ChiselRegistry.GEMS.put(entry, items.get(0));
            }
        }
    }

    @Mod.EventHandler
    public void onRecipeRegistry(FMLPostInitializationEvent event) {
        for (String ore : ChiselRegistry.INGOTS.keySet()) {
            RecipeRegistry.addChiselOreRecipe(
                    ChiselRegistry.CHISEL_INGOT, ore);
        }
        for (String ore : ChiselRegistry.GEMS.keySet()) {
            RecipeRegistry.addChiselOreRecipe(
                    ChiselRegistry.CHISEL_GEM, ore);
        }
    }

    @Mod.EventHandler
    public void onColorRegistry(FMLPostInitializationEvent event) {
        proxy.registerColorHandler();
    }

    public static class ProxyWrapper {
        public void registerColorHandler() {}
    }

    @SuppressWarnings("ConstantConditions")
    private static class TabMoreChisels extends CreativeTabs {
        TabMoreChisels() {
            super(MoreChisels.MOD_ID);
            setBackgroundImageName("item_search.png");
        }

        @Override public boolean hasSearchBar() { return true; }

        @Override @Nonnull
        public ItemStack getTabIconItem() {
            ItemStack stack = new ItemStack(ChiselRegistry.CHISEL_INGOT);
            stack.setTagInfo("ore", new NBTTagString("gemEmerald"));
            return stack;
        }
    }

}
