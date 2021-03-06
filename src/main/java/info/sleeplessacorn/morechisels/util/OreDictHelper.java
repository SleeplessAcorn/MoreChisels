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

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OreDictHelper {

    public static List<String> getAllFromPrefix(String prefix) {
        return Arrays.stream(OreDictionary.getOreNames())
                .filter(entry -> entry.startsWith(prefix))
                .collect(Collectors.toList());
    }

    public static String format(String input) {
        return input.replaceAll("[A-Z]","_$0");
    }

    public static boolean hasItems(NonNullList<ItemStack> ores) {
        return ores.size() > 0 && !ores.get(0).isEmpty();
    }

}
