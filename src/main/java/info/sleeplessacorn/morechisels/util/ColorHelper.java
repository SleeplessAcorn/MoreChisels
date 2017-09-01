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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.Color;

public class ColorHelper {

    @SideOnly(Side.CLIENT)
    public static int getStackColor(ItemStack stack) {
        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, null, null);
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
