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

import java.util.Locale;

public enum EnumChiselType {

    DIRTY("dirt", 32, false, false),
    WOODEN("plankWood", 64, false, false),
    GOLDEN("ingotGold", 512, true, false);

    private final String oreDictMaterial;
    private final int durability;
    private final boolean hasGui;
    private final boolean isAdvanced;

    EnumChiselType(String oreDictMaterial, int durability, boolean hasGui, boolean isAdvanced) {
        this.oreDictMaterial = oreDictMaterial;
        this.durability = durability;
        this.hasGui = hasGui;
        this.isAdvanced = isAdvanced;
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    public String getOreDict() {
        return oreDictMaterial;
    }

    public int getDurability() {
        return durability;
    }

    public boolean hasGui() {
        return hasGui;
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

}
