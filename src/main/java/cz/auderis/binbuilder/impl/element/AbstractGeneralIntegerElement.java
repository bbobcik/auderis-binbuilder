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

package cz.auderis.binbuilder.impl.element;

import cz.auderis.binbuilder.api.element.GeneralIntegerElement;

import java.util.OptionalInt;

public abstract class AbstractGeneralIntegerElement<T extends AbstractGeneralIntegerElement<T>>
        extends AbstractNumericElement<T>
        implements GeneralIntegerElement<T>
{

    private OptionalInt maxBits;
    private boolean unsigned;

    protected AbstractGeneralIntegerElement(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
        this.maxBits = OptionalInt.empty();
    }

    @Override
    public OptionalInt getMaxBits() {
        return maxBits;
    }

    @Override
    public void setMaxBits(OptionalInt newLimit) {
        if (null == newLimit) {
            throw new NullPointerException();
        }
        checkStateBeforeChange();
        if (!maxBits.equals(newLimit)) {
            this.maxBits = newLimit;
            numericParametersChanged();
        }
    }

    @Override
    public T withMaxBits(OptionalInt newLimit) {
        setMaxBits(newLimit);
        return (T) this;
    }

    protected void setMaxBitsInternal(int newMaxBits) {
        assert newMaxBits > 0;
        this.maxBits = OptionalInt.of(newMaxBits);
        // Does not check state nor is the change callback invoked
    }

    @Override
    public boolean isUnsigned() {
        return unsigned;
    }

    @Override
    public void setUnsigned(boolean unsignedNumber) {
        checkStateBeforeChange();
        if (unsigned != unsignedNumber) {
            this.unsigned = unsignedNumber;
            numericParametersChanged();
        }
    }

    @Override
    public T asUnsigned() {
        setUnsigned(true);
        return (T) this;
    }

    @Override
    public T asSigned() {
        setUnsigned(false);
        return (T) this;
    }

}
