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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ColorHandler extends MoreChisels.ProxyWrapper {

    public static final Map<String, Integer> ORE_COLORS = new HashMap<String, Integer>();

    @Override
    public void registerColorHandler() {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager())
                .registerReloadListener(resourceManager -> {
                    if (!ORE_COLORS.isEmpty())
                        ORE_COLORS.clear();
                    cacheOreColors(ChiselRegistrar.CHISEL_INGOT);
                    cacheOreColors(ChiselRegistrar.CHISEL_GEM);
                });
        registerChiselColours(ChiselRegistrar.CHISEL_INGOT);
        registerChiselColours(ChiselRegistrar.CHISEL_GEM);
    }

    private void cacheOreColors(ItemChiselOreDict chisel) {
        for (Map.Entry<String, ItemStack> entry : chisel.getOreMap().entrySet()) {
            String ore = entry.getKey();
            ItemStack stack = entry.getValue();
            MoreChisels.LOGGER.info("Caching ore color for <{}> from <{}>",
                    ore, stack.getItem().getRegistryName());
            ORE_COLORS.put(ore, getStackColor(stack));
        }
    }

    public void registerChiselColours(ItemChiselOreDict chisel){
        ItemColors colors = Minecraft.getMinecraft().getItemColors();
        colors.registerItemColorHandler((stack, index) -> {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null || index != 0) return -1;
            String ore = nbt.getString("ore");
            return ORE_COLORS.get(ore);
        }, chisel);
    }

    private int getStackColor(ItemStack stack) {
        IBakedModel model = Minecraft.getMinecraft().getRenderItem()
                .getItemModelWithOverrides(stack, null, null);
        TextureAtlasSprite sprite = model.getParticleTexture();
        int[] pixels = sprite.getFrameTextureData(0)[0];
        float r = 0, g = 0, b = 0, count = 0;
        float[] hsb = new float[3];
        for (int argb : pixels) {
            int ca = argb >> 24 & 0xFF;
            int cr = argb >> 16 & 0xFF;
            int cg = argb >> 8 & 0xFF;
            int cb = argb & 0xFF;
            if (ca > 0x7F && NumberUtils.max(cr, cg, cb) > 0x1F) {
                Color.RGBtoHSB(ca, cr, cg, hsb);
                float weight = hsb[1];
                r += cr * weight;
                g += cg * weight;
                b += cb * weight;
                count += weight;
            }
        }
        if (count > 0) {
            r /= count;
            g /= count;
            b /= count;
        }
        return 0xFF000000 | (int) r << 16 | (int) g << 8 | (int) b;
    }

}
