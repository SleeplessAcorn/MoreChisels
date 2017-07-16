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

import net.minecraftforge.common.config.Config;
import org.apache.commons.lang3.ArrayUtils;

@Config(modid = MoreChisels.MOD_ID)
public class ConfigManager {

    @Config.Name("Ore Dictionary Blacklist")
    @Config.Comment({"Entries in the ore dictionary that " +
            "More Chisels shouldn't generate chisels from."})
    @Config.LangKey("config.morechisels.blacklist")
    public static String[] oreBlacklist = {
            "ingotIron",
            "gemDiamond",
            "ingotBrick",
            "ingotBrickNether",
            "ingotHotTungstensteel",
            "ingotIridium",
            "ingotAdvancedAlloy",
            "ingotMixedMetalAlloy",
            "ingotIridiumAlloy"
    };

    public static boolean isBlacklisted(String oredict) {
        return ArrayUtils.contains(oreBlacklist, oredict);
    }

}
