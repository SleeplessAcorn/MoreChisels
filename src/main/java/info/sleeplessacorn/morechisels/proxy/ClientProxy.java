package info.sleeplessacorn.morechisels.proxy;

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
import info.sleeplessacorn.morechisels.util.ChiselRegistrar;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class ClientProxy {

    public void getChiselTab() {
        Arrays.stream(CreativeTabs.CREATIVE_TAB_ARRAY)
                .filter(t -> t.getTabLabel().equals("chiselCreativeTab"))
                .findFirst().ifPresent(tab -> MoreChisels.CHISEL_TAB = tab);
        // blame tterrag, but hey, it works :derp1::derp2:
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent e) {
        for (Item chisel : ChiselRegistrar.CHISELS) {
            assert chisel.getRegistryName() != null;
            ModelResourceLocation mrl = new ModelResourceLocation(chisel.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(chisel, 0, mrl);
        }
    }

}
