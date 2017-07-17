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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
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
            CLIENT_PROXY = "info.sleeplessacorn.morechisels.util.ColorRegistry",
            SERVER_PROXY = "info.sleeplessacorn.morechisels.MoreChisels$ProxyWrapper";

    public static final TabMoreChisels TAB = new TabMoreChisels();
    public static final Logger LOGGER = LogManager.getLogger(MoreChisels.MOD_NAME);

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static RegistryManager proxy;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onOreRegistry(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.onRecipeRegistry(event);
        proxy.registerColorHandler();
    }

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

    private static class ProxyWrapper extends RegistryManager {}

}
