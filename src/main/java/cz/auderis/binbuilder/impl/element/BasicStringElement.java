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

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.OptionalLong;

public class BasicStringElement extends AbstractStringElement<BasicStringElement> {

    static final int INITIAL_CAPACITY = 16;

    private char[] data;
    private int dataCapacity;
    private int dataLength;

    protected BasicStringElement(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
        this.dataCapacity = INITIAL_CAPACITY;
        this.data = new char[dataCapacity];
    }

    @Override
    public BasicStringElement append(CharSequence csq) {
        checkStateBeforeChange();
        if (null == csq) {
            csq = "null";
        }
        copySubsequenceToData(csq, 0, csq.length());
        return this;
    }

    @Override
    public BasicStringElement append(CharSequence csq, int start, int end) {
        checkStateBeforeChange();
        if (null == csq) {
            csq = "null";
            start = Math.min(start, csq.length());
            end = Math.min(end, csq.length());
        }
        if ((start < 0) || (start > end) || (end > csq.length())) {
            throw new IndexOutOfBoundsException("csq='" + csq + "', start=" + start + ", end=" + end);
        }
        copySubsequenceToData(csq, start, end - start);
        return this;
    }

    @Override
    public BasicStringElement append(char c) {
        checkStateBeforeChange();
        increaseDataCapacity(dataCapacity + 1);
        data[dataLength] = c;
        updateSize(dataLength + 1);
        return this;
    }

    @Override
    public int length() {
        return dataLength;
    }

    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= dataLength)) {
            throw new IndexOutOfBoundsException("Index=" + index + ", length=" + dataLength);
        }
        return data[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if ((start < 0) || (start > end) || (end > dataLength)) {
            throw new IndexOutOfBoundsException("Length=" + dataLength + ", start=" + start + ", end=" + end);
        }
        final CharBuffer view = CharBuffer.wrap(data, start, end - start);
        return view;
    }

    @Override
    public int read(CharBuffer cb) {
        final int copyLength = Math.min(dataLength, cb.remaining());
        cb.put(data, 0, copyLength);
        return copyLength;
    }

    void increaseDataCapacity(int neededCapacity) {
        if (neededCapacity <= dataCapacity) {
            return;
        }
        final int minNewCapacity = Math.max(neededCapacity, 2 * dataCapacity);
        final int newCapacity = Integer.highestOneBit(neededCapacity);
        assert newCapacity >= minNewCapacity;
        final char[] copy = Arrays.copyOf(data, newCapacity);
        this.dataCapacity = newCapacity;
        this.data = copy;
    }

    void copySubsequenceToData(CharSequence src, int offset, int size) {
        if (0 == size) {
            return;
        }
        increaseDataCapacity(dataLength + size);
        if (src instanceof String) {
            ((String) src).getChars(offset, size, data, dataLength);
        } else if (src instanceof CharBuffer) {
            final CharBuffer srcCopy = ((CharBuffer) src).asReadOnlyBuffer();
            srcCopy.limit(offset + size);
            srcCopy.position(offset);
            srcCopy.get(data, dataLength, size);
        } else {
            for (int s=offset, d=dataLength; s<size; ++s, ++d) {
                data[d] = src.charAt(s);
            }
        }
        updateSize(dataLength + size);
    }

    void updateSize(int newSize) {
        dataLength = newSize;
        final CharBuffer buffer = CharBuffer.wrap(data, 0, dataLength);
        final int byteLength = computeByteLength(buffer);
        setSize(OptionalLong.of(byteLength));
    }

}
