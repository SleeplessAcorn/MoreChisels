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

@Config(modid = MoreChisels.MOD_ID)
public class ConfigMoreChisels {

    @Config.Name("Ore Dictionary Blacklist")
    @Config.Comment({"Entries in the ore dictionary that More Chisels shouldn't generate tools from.",
            "Please note that additions and removals will alter the items that are registered.",
            "You may lose items if you add to the blacklist and load an existing world!"})
    @Config.LangKey("config.morechisels.blacklist")
    public static String[] oreBlacklist = createDefaultBlacklist();

    public static String[] createDefaultBlacklist() {
        String[] entries = new String[4];
        entries[0] = "ingotIron";
        entries[1] = "gemDiamond";
        entries[2] = "ingotBrick";
        entries[3] = "ingotBrickNether";
        return entries;
    }

    public static boolean isBlacklisted(String oredict) {
        for (String entry : oreBlacklist)
            if (entry.equals(oredict))
                return true;
        return false;
    }

}