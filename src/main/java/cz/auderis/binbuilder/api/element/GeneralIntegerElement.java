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

import java.util.OptionalInt;

public interface GeneralIntegerElement<T extends GeneralIntegerElement<T>> extends NumericElement<T> {

    byte getByteValue();
    short getShortValue();
    int getValue();
    long getLongValue();

    boolean isUnsigned();
    void setUnsigned(boolean signMode);
    T asUnsigned();
    T asSigned();

    OptionalInt getMaxBits();
    void setMaxBits(OptionalInt newLimit);
    T withMaxBits(OptionalInt newLimit);

}
