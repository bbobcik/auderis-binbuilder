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
import cz.auderis.binbuilder.api.element.ElementId;

import java.util.Optional;
import java.util.OptionalLong;

public class BasicIntegerElement extends AbstractNumericElement<BasicIntegerElement> implements IntegerElement<BasicIntegerElement> {

    protected long value;

    protected BasicIntegerElement(long size) {
        super(size);
    }

    protected BasicIntegerElement(OptionalLong offset, long size) {
        super(offset, size);
    }

    protected BasicIntegerElement(OptionalLong offset, long size, Optional<ElementId> id) {
        super(offset, size, id);
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
        final long newValue = unsigned ? Byte.toUnsignedLong(value) : value;
        updateValue(newValue);
    }

    @Override
    public void setShortValue(short value) {
        final long newValue = unsigned ? Short.toUnsignedLong(value) : value;
        updateValue(newValue);
    }

    @Override
    public void setValue(int value) {
        final long newValue = unsigned ? Integer.toUnsignedLong(value) : value;
        updateValue(newValue);
    }

    @Override
    public void setLongValue(long value) {
        updateValue(value);
    }

    protected void updateValue(long newValue) {
        checkStateBeforeUpdate();
        this.value = newValue;
    }

}
