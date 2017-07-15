package info.sleeplessacorn.morechisels.util;

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

import info.sleeplessacorn.morechisels.ConfigMoreChisels;
import info.sleeplessacorn.morechisels.MoreChisels;
import info.sleeplessacorn.morechisels.chisel.ItemChiselOreDict;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MoreChisels.MOD_ID)
public class ChiselRegistrar {

    public static final String[] TYPES = new String[] {"ingot", "gem"};

    public static final Map<String, ItemStack> INGOTS = new HashMap<String, ItemStack>();
    public static final Map<String, ItemStack> GEMS = new HashMap<String, ItemStack>();

    public static final ItemChiselOreDict CHISEL_INGOT = new ItemChiselOreDict("ingot", INGOTS, 128, true, false);
    public static final ItemChiselOreDict CHISEL_GEM = new ItemChiselOreDict("gem", GEMS, 512, true, true);

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {

        event.getRegistry().register(CHISEL_INGOT);
        event.getRegistry().register(CHISEL_GEM);

        for (String type : TYPES) {
            for (String entry : OreDictHelper.getAllFromPrefix(type)) {
                NonNullList<ItemStack> items = OreDictionary.getOres(entry);
                if (!ConfigMoreChisels.isBlacklisted(entry)
                        && OreDictHelper.hasItems(items)) {
                    INGOTS.put(entry, items.get(0));
                }
            }
        }

    }


}
