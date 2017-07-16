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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ColorHandler extends MoreChisels.ProxyWrapper {

    public static final Map<String, Integer> ORE_COLORS = new HashMap<>();

    @Override
    public void registerColorHandler() {
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager())
                .registerReloadListener(resourceManager -> {
                    if (!ORE_COLORS.isEmpty())
                        ORE_COLORS.clear();
                    cacheOreColors();
                });

    }

    private void cacheOreColors() {
        for (Map.Entry<String, ItemStack> ingots : ChiselRegistrar.INGOTS.entrySet()) {
            MoreChisels.LOGGER.info("Caching ore color for <{}> from <{}>",
                    ingots.getKey(), ingots.getValue());
            ORE_COLORS.put(ingots.getKey(), getStackColor(ingots.getValue()));
        }
        for (Map.Entry<String, ItemStack> gems : ChiselRegistrar.GEMS.entrySet()) {
            MoreChisels.LOGGER.info("Caching ore color for <{}> from <{}>",
                    gems.getKey(), gems.getValue());
            ORE_COLORS.put(gems.getKey(), getStackColor(gems.getValue()));
        }
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
