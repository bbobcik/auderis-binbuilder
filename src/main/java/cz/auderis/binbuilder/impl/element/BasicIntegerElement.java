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

import cz.auderis.binbuilder.api.element.IntegerElement;

import java.util.OptionalInt;
import java.util.OptionalLong;

public class BasicIntegerElement
        extends AbstractGeneralIntegerElement<BasicIntegerElement>
        implements IntegerElement<BasicIntegerElement>
{

    protected long value;

    public BasicIntegerElement(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
    }

    @Override
    public byte getByteValue() {
        return (byte) value;
    }

    @Override
    public short getShortValue() {
        return (short) value;
    }

    @Override
    public int getValue() {
        return (int) value;
    }

    @Override
    public long getLongValue() {
        return value;
    }

    @Override
    public void setByteValue(byte value) {
        final long newValue = isUnsigned() ? Byte.toUnsignedLong(value) : value;
        updateValue(newValue, Byte.SIZE);
    }

    @Override
    public void setShortValue(short value) {
        final long newValue = isUnsigned() ? Short.toUnsignedLong(value) : value;
        updateValue(newValue, Short.SIZE);
    }

    @Override
    public void setValue(int value) {
        final long newValue = isUnsigned() ? Integer.toUnsignedLong(value) : value;
        updateValue(newValue, Integer.SIZE);
    }

    @Override
    public void setLongValue(long value) {
        updateValue(value, Long.SIZE);
    }

    protected void updateValue(long newValue, int newRawBits) {
        checkStateBeforeChange();
        if ((value != newValue) || (getMaxBits().orElse(-1) != newRawBits)) {
            this.value = newValue;
            setMaxBitsInternal(newRawBits);
            numericParametersChanged();
        }
    }

    @Override
    protected void numericParametersChanged() {
        final OptionalInt maxBits = getMaxBits();
        if (!maxBits.isPresent()) {
            setSize(OptionalLong.empty());
        } else {
            final NumberRepresentationEncoder encoder = context.getEncoder(getRepresentation());
            final int byteSize = encoder.getIntegerByteCount(value, maxBits.getAsInt(), isUnsigned());
            setSize(OptionalLong.of(byteSize));
        }
    }

}
