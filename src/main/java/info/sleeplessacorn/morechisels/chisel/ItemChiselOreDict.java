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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Map;

public class ItemChiselOreDict extends ItemChiselBase {

    private Map<String, ItemStack> map;

    public ItemChiselOreDict(
            String name, Map<String, ItemStack> map, int durability,
            boolean hasGui, boolean isAdvanced) {
        super(name, durability, hasGui, isAdvanced);
        this.map = map;
        setHasSubtypes(true);
    }


    @Override @SuppressWarnings("ConstantConditions")
    public void getSubItems(
            @Nonnull CreativeTabs tab,
            @Nonnull NonNullList<ItemStack> items) {
        if (!this.isInCreativeTab(tab)) return;
        for (String ore : map.keySet()) {
            ItemStack stack = new ItemStack(this);
            stack.setTagInfo("ore", new NBTTagString(ore));
            items.add(stack);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override @SideOnly(Side.CLIENT) @Nonnull
    public String getItemStackDisplayName(
            @Nonnull ItemStack stack) {
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("ore"))
            return super.getItemStackDisplayName(stack);
        String name, ore = stack.getTagCompound().getString("ore");
        if (map.containsKey(ore)) {
            name = map.get(ore).getDisplayName();
        } else name = I18n.format("item.morechisels.chisel.dynamic.error");
        String loc = "item.morechisels.chisel.dynamic.name";
        return I18n.format(loc, name);
    }

    public Map<String, ItemStack> getOreMap() { return this.map; }

}
