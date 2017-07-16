package info.sleeplessacorn.morechisels.compat;

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

import info.sleeplessacorn.morechisels.RegistryManager.ChiselRegistry;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;

import javax.annotation.Nonnull;

@JEIPlugin
public class JEISubtypeHandler extends BlankModPlugin {

    @Override
    public void registerItemSubtypes(
            @Nonnull ISubtypeRegistry registry) {
        registry.useNbtForSubtypes(ChiselRegistry.CHISEL_INGOT);
        registry.useNbtForSubtypes(ChiselRegistry.CHISEL_GEM);
    }

}
