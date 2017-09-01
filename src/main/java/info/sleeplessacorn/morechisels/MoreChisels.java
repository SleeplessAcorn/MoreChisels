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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@Mod(   modid = MoreChisels.MOD_ID,
        name = MoreChisels.MOD_NAME,
        version = MoreChisels.MOD_VERSION,
        dependencies = MoreChisels.DEPENDENCIES,
        acceptedMinecraftVersions = MoreChisels.MC_VERSION)

public class MoreChisels {

    public static final String MOD_ID = "morechisels", MOD_NAME = "More Chisels";
    public static final String MOD_VERSION = "%mod_version%", MC_VERSION = "%mc_version%";
    public static final String DEPENDENCIES = "required-after:chisel@[%chisel_version%,);after:*;";
    public static final String CLIENT_PROXY = "info.sleeplessacorn.morechisels.ClientRegistry";
    public static final String SERVER_PROXY = "info.sleeplessacorn.morechisels.ChiselRegistry";

    public static final Logger LOGGER = LogManager.getLogger(MoreChisels.MOD_NAME);

    public static final CreativeTabs CTAB = new CreativeTabs(MoreChisels.MOD_ID) {

        @Override
        public ItemStack getTabIconItem() {
            ItemStack stack = new ItemStack(ChiselRegistry.CHISEL_INGOT);
            stack.setTagInfo("ore", new NBTTagString("gemEmerald"));
            return stack;
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }

        @Override
        public String getBackgroundImageName() {
            return "item_search.png";
        }

    };

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static ChiselRegistry proxy;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onInit(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.onPostInit(event);
        proxy.registerColorHandler();
    }

    @Config(modid = MoreChisels.MOD_ID, name = MoreChisels.MOD_ID)
    @Mod.EventBusSubscriber
    public static class ModConfig {

        @Config.Name("Ore Dictionary Blacklist")
        @Config.Comment({"Entries in the ore dictionary that chisels shouldn't be generated from."})
        @Config.LangKey("config.morechisels.blacklist")
        @Config.RequiresMcRestart
        public static String[] oreBlacklist = {
                "gemDiamond",
                "gemLapis",
                "ingotAdvancedAlloy",
                "ingotBrick",
                "ingotBrickNether",
                "ingotHotTungstensteel",
                "ingotIridium",
                "ingotIridiumAlloy",
                "ingotIron",
                "ingotMixedMetalAlloy"
        };

        public static boolean isBlacklisted(String oredict) {
            return ArrayUtils.contains(oreBlacklist, oredict);
        }

        @SubscribeEvent
        protected static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (MoreChisels.MOD_ID.equals(event.getModID())) {
                Arrays.sort(oreBlacklist);
                ConfigManager.sync(MoreChisels.MOD_ID, Config.Type.INSTANCE);
            }
        }

    }

}
