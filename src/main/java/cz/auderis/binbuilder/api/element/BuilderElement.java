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

package cz.auderis.binbuilder.api.element;

import java.nio.ByteOrder;
import java.util.Optional;
import java.util.OptionalLong;

public interface BuilderElement<T extends BuilderElement<T>> {

    <U extends ElementFrame<U>> U getParentFrame();
    <U extends BuilderElement<U>> U getPreviousElement();
    <U extends BuilderElement<U>> U getNextElement();

    <U extends ElementId<U>> Optional<U> getId();

    OptionalLong getSize();
    ElementLocation withinLocalFrame();
    ElementLocation within(ElementFrame<?> referenceFrame);

    boolean isSizeDetermined();
    boolean isContentDetermined();

    ByteOrder getByteOrder();

    boolean isFrozen();
    boolean tryFreeze();

}