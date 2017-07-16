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

import info.sleeplessacorn.morechisels.MoreChisels;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.chisel.api.IChiselGuiType;
import team.chisel.api.IChiselItem;
import team.chisel.api.carving.ICarvingVariation;
import team.chisel.common.config.Configurations;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("NullableProblems")
public class ItemChiselBase extends Item implements IChiselItem {

    private boolean hasGui;
    private boolean isAdvanced;

    public ItemChiselBase(String name,
                          int durability,
                          boolean hasGui,
                          boolean isAdvanced) {
        setRegistryName("chisel_"
                + name.toLowerCase(Locale.ROOT));
        setUnlocalizedName(MoreChisels.MOD_ID + ".chisel."
                + name.toLowerCase(Locale.ROOT));
        setMaxStackSize(1);
        setMaxDamage(durability);
        setCreativeTab(MoreChisels.TAB);
        this.hasGui = hasGui;
        this.isAdvanced = isAdvanced;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(
            ItemStack stack,
            @Nullable World world,
            List<String> tooltip,
            ITooltipFlag flag) {
        String loc = "item.chisel.chisel.desc.";
        if (hasGui) tooltip.add(I18n.format(loc + "gui"));
        tooltip.add(I18n.format(loc + "lc1"));
        tooltip.add(I18n.format(loc + "lc2"));
        if (isAdvanced) {
            tooltip.add("");
            tooltip.add(I18n.format(loc + "modes"));
        }
    }

    @Override
    public boolean isDamageable() {
        return Configurations.allowChiselDamage;
    }

    @Override
    public boolean canOpenGui(
            World world,
            EntityPlayer player,
            EnumHand hand) {
        return hasGui;
    }

    @Override
    public IChiselGuiType getGuiType(
            World world,
            EntityPlayer player,
            EnumHand hand) {
        return IChiselGuiType.ChiselGuiType.NORMAL;
    }

    @Override
    public boolean onChisel(
            World world,
            EntityPlayer player,
            ItemStack chisel,
            ICarvingVariation target) {
        return true;
    }

    @Override
    public boolean canChisel(
            World world,
            EntityPlayer player,
            ItemStack chisel,
            ICarvingVariation target) {
        return true;
    }

    @Override
    public boolean canChiselBlock(
            World world,
            EntityPlayer player,
            EnumHand hand,
            BlockPos pos,
            IBlockState state) {
        return true;
    }

    @Override
    public boolean hasModes(
            EntityPlayer player,
            EnumHand hand) {
        return isAdvanced;
    }

}
