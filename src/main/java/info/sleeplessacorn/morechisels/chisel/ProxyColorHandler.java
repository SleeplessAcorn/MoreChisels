package info.sleeplessacorn.morechisels.chisel;

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

import info.sleeplessacorn.morechisels.util.OreDictHelper;
import javafx.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ProxyColorHandler {

    public static List<Pair<String, Integer>> ingotColors = new ArrayList<>();

    public void registerColorHandler() {
        Minecraft mc = Minecraft.getMinecraft();
        ((IReloadableResourceManager) mc.getResourceManager())
                .registerReloadListener(resourceManager -> {
            for (String ingot : OreDictHelper.getAllFromPrefix("ingot")){
                ItemStack stack = OreDictionary.getOres(ingot).get(0);
                IBakedModel model = mc.getRenderItem()
                        .getItemModelWithOverrides(stack, null, null);
                List<BakedQuad> quads = model.getQuads(null, null, 0);
                for (BakedQuad quad : quads) {
                    TextureAtlasSprite sprite = quad.getSprite();
                    int total = 0;
                    int[]rgba = sprite.getFrameTextureData(0)[0];
                    for (int i : rgba) total += i;
                    total = total / rgba.length;
                    // TODO: Finish this lol
                }
            }
        });
    }

}
