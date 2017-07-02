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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber(modid = MoreChisels.MOD_ID)
public class RecipeRegistrar {

    @SubscribeEvent
    public static void onRecipeRegistry(RegistryEvent.Register<IRecipe> event) {
        for (Item chisel : ChiselRegistrar.CHISELS) {
            if (chisel instanceof ItemChiselOreDict) {
                addChiselOreRecipe((ItemChiselOreDict) chisel);
            }
        }
    }

    public static void addChiselOreRecipe(ItemChiselOreDict chisel) {
        String oredict = chisel.getOreDict();
        if (!OreDictionary.doesOreNameExist(oredict))
            return;
        GameRegistry.addShapedRecipe(
                chisel.getRegistryName(),
                null, new ItemStack(chisel),
                " O", "S ",
                'O', oredict,
                'S', "stickWood");
    }

}
