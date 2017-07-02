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

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class ItemChiselOreDict extends ItemChiselBase implements IColoredChisel<String> {

    private String name;
    private String oredict;

    public ItemChiselOreDict(String name, int durability, String oredict,
                             boolean hasGui, boolean isAdvanced) {
        super(name, durability, hasGui, isAdvanced);
        this.name = name;
        this.oredict = oredict;
    }

    public String getOreDict() {
        return oredict;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        ItemStack entry = ItemStack.EMPTY;
        if (OreDictionary.getOres(oredict).size() > 0)
            entry = OreDictionary.getOres(oredict).get(0);
        String material = I18n.format(entry.getDisplayName());
        return I18n.format("item.morechisels.chisel.dynamic.name", material);
    }

    @Override
    public String getColorId() {
        return oredict;
    }

    @Override
    public String getDescriptiveName() {
        ItemStack entry = ItemStack.EMPTY;
        if (OreDictionary.getOres(oredict).size() > 0)
            entry = OreDictionary.getOres(oredict).get(0);
        return entry.getDisplayName();
    }

}
