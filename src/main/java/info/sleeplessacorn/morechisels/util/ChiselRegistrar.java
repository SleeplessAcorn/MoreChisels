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

import info.sleeplessacorn.morechisels.MoreChisels;
import info.sleeplessacorn.morechisels.chisel.EnumChiselType;
import info.sleeplessacorn.morechisels.chisel.ItemChiselEnum;
import info.sleeplessacorn.morechisels.chisel.ItemChiselOreDict;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.chisel.common.config.Configurations;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoreChisels.MOD_ID)
public class ChiselRegistrar {

    public static final List<Item> CHISELS = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        registerChisels();
        event.getRegistry().registerAll(CHISELS.toArray(new Item[0]));
    }

    public static void registerChisels() {

        // Default chisels
        for (EnumChiselType type : EnumChiselType.values())
            CHISELS.add(new ItemChiselEnum(type));

        // Metal chisels
        int ingotDurability = Configurations.ironChiselMaxDamage;
        for (String ingot : OreDictHelper.getAllFromPrefix("ingot")) {
            if (!ingot.toLowerCase().contains("brick") && !ingot.equals("ingotIron"))
                CHISELS.add(new ItemChiselOreDict(ingot.substring(5), ingotDurability, ingot));
        }

        // Gem chisels
        int gemDurability = Configurations.diamondChiselMaxDamage;
        for (String gem : OreDictHelper.getAllFromPrefix("gem")) {
            if (!gem.toLowerCase().contains("diamond"))
                CHISELS.add(new ItemChiselOreDict(gem.substring(3), gemDurability, gem));
        }

    }

}
