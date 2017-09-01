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
import info.sleeplessacorn.morechisels.util.ColorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = MoreChisels.MOD_ID)
public class ClientRegistry extends ChiselRegistry {

    public static final Map<String, Integer> ORE_COLORS = new HashMap<>();

    @Override
    public void registerColorHandler() {
        IReloadableResourceManager rm = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
        rm.registerReloadListener(resourceManager -> cacheOreColors(CHISEL_INGOT, CHISEL_GEM));
        registerChiselColours(CHISEL_INGOT, CHISEL_GEM);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        registerOreChiselModels(CHISEL_INGOT, CHISEL_GEM);
    }

    private static void registerOreChiselModels(ItemChiselOreDict... chisels) {
        Arrays.stream(chisels).forEach(chisel -> {
            ResourceLocation name = new ResourceLocation(MoreChisels.MOD_ID, "chisel_ore");
            ModelResourceLocation mrl = new ModelResourceLocation(name, "inventory");
            ModelLoader.setCustomModelResourceLocation(chisel, 0, mrl);
        });
    }

    private static void cacheOreColors(ItemChiselOreDict... chisels) {
        ORE_COLORS.clear();
        Arrays.stream(chisels).forEach(chisel -> chisel.getOreMap().forEach((ore, stack) -> {
            MoreChisels.LOGGER.debug("Caching ore color for <{}> from <{}:{}>",
                    ore, stack.getItem().getRegistryName(), stack.getMetadata());
            ORE_COLORS.put(ore, ColorHelper.getStackColor(stack));
        }));
    }

    public static void registerChiselColours(ItemChiselOreDict... chisels){
        Arrays.stream(chisels).forEach(chisel -> Minecraft.getMinecraft()
                .getItemColors().registerItemColorHandler((stack, index) -> {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null && index == 0) {
                String ore = nbt.getString("ore");
                return ORE_COLORS.get(ore);
            }
            return -1;
        }, chisel));
    }

}
