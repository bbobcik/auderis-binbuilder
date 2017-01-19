/*
 * Copyright 2017 Boleslav Bobcik - Auderis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cz.auderis.binbuilder.api;

import cz.auderis.binbuilder.api.element.ElementFrame;

import java.util.OptionalLong;

public interface BinaryBuilder<U extends BinaryBuilder<U>> extends AutoCloseable {

    OptionalLong getSize();

    boolean isClosed();
    @Override void close();

    <T extends ElementFrame<T>> T getRootFrame();
    <T extends ElementFrame<T>> T getScratchFrame(String name);

}
