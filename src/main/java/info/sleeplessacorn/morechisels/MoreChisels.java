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

import info.sleeplessacorn.morechisels.util.ColorHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import team.chisel.common.init.ChiselTabs;

@Mod(
        modid = MoreChisels.MOD_ID,
        name = MoreChisels.MOD_NAME,
        version = MoreChisels.MOD_VERSION,
        dependencies = "required-after:chisel@[" + MoreChisels.CHISEL_VERSION + ",);after:chisel-api@[,0.0.1);",
        acceptedMinecraftVersions = MoreChisels.MC_VERSION
)
public class MoreChisels {

    public static final String MOD_ID = "morechisels";
    public static final String MOD_NAME = "More Chisels";
    public static final String MOD_VERSION = "%mod_version%";
    public static final String MC_VERSION = "%mc_version%";
    public static final String CHISEL_VERSION = "%chisel_version%";

    public static final CreativeTabs CHISEL_TAB = ChiselTabs.tab;

    @SidedProxy(clientSide = "info.sleeplessacorn.morechisels.util.ColorHandler")
    public static ColorHandler colorHandler;

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        colorHandler.registerColorHandler();

    }

}
