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
import info.sleeplessacorn.morechisels.chisel.ItemChiselOreDict;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class ModelRegistrar {

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        ResourceLocation dynamic = new ResourceLocation(MoreChisels.MOD_ID + ":chisel_dynamic");
        for (Item chisel : ChiselRegistrar.CHISELS) {
            assert chisel.getRegistryName() != null;
            ResourceLocation normal = chisel.getRegistryName();
            boolean isDynamic = chisel instanceof ItemChiselOreDict;
            ModelResourceLocation mrl = new ModelResourceLocation(isDynamic ? dynamic : normal, "inventory");
            ModelLoader.setCustomModelResourceLocation(chisel, 0, mrl);
        }
    }

}
