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
import info.sleeplessacorn.morechisels.chisel.ItemChiselBase;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoreChisels.MOD_ID)
public class ChiselRegistrar {

    public static final List<Item> CHISELS = new ArrayList<>();

    static {
        for (EnumChiselType type : EnumChiselType.values())
            CHISELS.add(new ItemChiselBase(type));
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        MoreChisels.proxy.getChiselTab();
        event.getRegistry().registerAll(CHISELS.toArray(new Item[0]));
    }

}
