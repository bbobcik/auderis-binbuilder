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

import cz.auderis.binbuilder.api.element.RawByteSequence;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.OptionalLong;

public class BasicRawByteSequence
        extends AbstractBuilderElement<BasicRawByteSequence>
        implements RawByteSequence<BasicRawByteSequence>
{

    static final int INITIAL_CAPACITY = 16;

    byte[] data;
    int dataLength;


    protected BasicRawByteSequence(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
        this.data = new byte[INITIAL_CAPACITY];
    }

    @Override
    public void addByte(int byteValue) {
        checkStateBeforeChange();
        growDataArray(1);
        data[dataLength] = (byte) byteValue;
        ++dataLength;
        setSize(OptionalLong.of(dataLength));
    }

    @Override
    public void addBytes(byte[] byteArray) {
        if (null == byteArray) {
            throw new NullPointerException();
        }
        checkStateBeforeChange();
        final int len = byteArray.length;
        if (len > 0) {
            growDataArray(len);
            System.arraycopy(byteArray, 0, data, dataLength, len);
            dataLength += len;
            setSize(OptionalLong.of(dataLength));
        }
    }

    @Override
    public void addBytes(ByteBuffer buffer) {
        if (null == buffer) {
            throw new NullPointerException();
        }
        checkStateBeforeChange();
        if (buffer.hasRemaining()) {
            final int len = buffer.remaining();
            growDataArray(len);
            buffer.get(data, dataLength, len);
            dataLength += len;
            setSize(OptionalLong.of(dataLength));
        }
    }

    protected void growDataArray(int minIncrement) {
        assert minIncrement > 0;
        final int targetLength = dataLength + minIncrement;
        if (targetLength > data.length) {
            final int newLength = Integer.highestOneBit(targetLength) << 1;
            final byte[] newData = Arrays.copyOf(data, newLength);
            this.data = newData;
        }
    }

}
