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
import info.sleeplessacorn.morechisels.ProxyWrapper;
import info.sleeplessacorn.morechisels.chisel.ItemChiselOreDict;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ColorHandler extends ProxyWrapper {

    public static final Map<String, Integer> ORE_COLORS = new HashMap<>();

    @Override
    public void registerColorHandler() {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager())
                .registerReloadListener(resourceManager -> {
                    cacheOreColors("ingot");
                    cacheOreColors("gem");
                });
        for (Item chisel : ChiselRegistrar.CHISELS) {
            if (chisel instanceof ItemChiselOreDict)
                registerChiselColor((ItemChiselOreDict) chisel);
        }
    }

    private void cacheOreColors(String oredict) {
        for (String entry : OreDictHelper.getAllFromPrefix(oredict)) {
            if (MoreChisels.DEOBF)
                MoreChisels.LOGGER.info("Caching color for ore dictionary entry <{}>", entry);
            ORE_COLORS.put(entry, getOreColor(entry));
        }
    }

    private void registerChiselColor(ItemChiselOreDict chisel) {
        ItemColors colors = Minecraft.getMinecraft().getItemColors();
        colors.registerItemColorHandler((stack, tintIndex) -> {
            boolean isHead = tintIndex == 1;
            if (ORE_COLORS.containsKey(chisel.getOreDict())) {
                return isHead ? ORE_COLORS.get(chisel.getOreDict()) : -1;
            } else {
                // This else statement should never be reached, and if it is, you done fucked up, Kit
                String msg = "Could not find a cached color value for <{}>, generating a new one...";
                MoreChisels.LOGGER.warn(msg, chisel.getOreDict());
                ORE_COLORS.put(chisel.getOreDict(), getOreColor(chisel.getOreDict()));
            }
            return -1;
        }, chisel);
    }

    private int getOreColor(String oredict) {
        if (OreDictionary.getOres(oredict).size() > 0) {
            ItemStack stack = OreDictionary.getOres(oredict).get(0);
            return getStackColor(stack);
        } else return 0xFF000000;
    }

    private int getStackColor(ItemStack stack) {
        IBakedModel model = Minecraft.getMinecraft().getRenderItem()
                .getItemModelWithOverrides(stack, null, null);
        TextureAtlasSprite sprite = model.getParticleTexture();
        int[] pixels = sprite.getFrameTextureData(0)[0];
        int r = 0, g = 0, b = 0, count = 0;
        for (int argb : pixels) {
            int ca = argb >> 24 & 0xFF;
            int cr = argb >> 16 & 0xFF;
            int cg = argb >> 8 & 0xFF;
            int cb = argb & 0xFF;
            if (ca > 0x7F && NumberUtils.max(cr, cg, cb) > 0x1F) {
                r += cr;
                g += cg;
                b += cb;
                count++;
            }
        }
        if (count > 0) {
            r /= count;
            g /= count;
            b /= count;
        }
        return 0xFF000000 | r << 16 | g << 8 | b;
    }

}
