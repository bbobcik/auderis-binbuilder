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

package cz.auderis.binbuilder.impl;

import cz.auderis.binbuilder.api.BinaryBuilder;
import cz.auderis.binbuilder.impl.element.AbstractByteSubsequence;
import cz.auderis.binbuilder.impl.element.AbstractStringElement;
import cz.auderis.binbuilder.impl.element.BasicIntegerElement;
import cz.auderis.binbuilder.impl.element.BasicLengthElement;
import cz.auderis.binbuilder.impl.element.MeasuringStringElement;
import cz.auderis.binbuilder.impl.element.PlainByteSequence;

import java.nio.ByteBuffer;
import java.util.Deque;
import java.util.LinkedList;
import java.util.OptionalLong;

public class MeasuringBinaryBuilder implements BinaryBuilder {

    private final Deque<AbstractByteSubsequence<?>> sequences;
    private final NamedSubsequenceRegistry registry;

    private long size;
    private boolean closed;
    private long offset;
    private boolean offsetValid;

    public MeasuringBinaryBuilder() {
        this.offsetValid = true;
        this.sequences = new LinkedList<>();
        this.registry = new BasicNamedSubsequenceRegistry();
    }

    @Override
    public OptionalLong getSize() {
        return closed ? OptionalLong.of(size) : OptionalLong.empty();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() {
        if (!closed) {
            resolveRemainingSubsequences();
            assert sequences.stream().allMatch(ByteSequence::isClosed);
            closed = true;
        }
    }

    private void resolveRemainingSubsequences() {
        int changes;
        boolean unresolved;
        OptionalLong pos;
        do {
            changes = 0;
            unresolved = false;
            pos = OptionalLong.of(0L);
            for (final AbstractByteSubsequence<?> seq : sequences) {
                if (!seq.isClosed()) {
                    seq.updateOffset(pos);
                    if (seq.tryClose()) {
                        ++changes;
                    } else {
                        unresolved = true;
                    }
                }
                pos = seq.getOffsetPlusSize();
            }
        } while (unresolved && changes > 0);
        if (!pos.isPresent()) {
            throw new RuntimeException();
        }
        this.size = pos.getAsLong();
    }

    @Override
    public void addByte(int byteValue) {
        final PlainByteSequence byteRun = getActiveByteSequence();
        byteRun.addByte(byteValue);
    }

    @Override
    public void addBytes(byte[] byteArray) {
        if (null != byteArray) {
            final PlainByteSequence byteRun = getActiveByteSequence();
            byteRun.addBytes(byteArray);
        }
    }

    @Override
    public void addBytes(ByteBuffer buffer) {
        if (null != buffer) {
            final PlainByteSequence byteRun = getActiveByteSequence();
            byteRun.addBytes(buffer);
        }
    }

    @Override
    public BasicIntegerElement addNumber(int wordSize) {
        validateWordSize(wordSize);
        final OptionalLong offset = getCurrentPosition();
        final long byteSize = wordSize / Byte.SIZE;
        final BasicIntegerElement value = new BasicIntegerElement(offset, byteSize);
        value.setRegistry(registry);
        sequences.add(value);
        return value;
    }

    @Override
    public AbstractStringElement addText(CharSequence text) {
        final OptionalLong offset = getCurrentPosition();
        final MeasuringStringElement stringElement = new MeasuringStringElement(offset);
        stringElement.setRegistry(registry);
        stringElement.appendText(text);
        sequences.add(stringElement);
        return stringElement;
    }

    @Override
    public BasicLengthElement addSubsequenceLength(int wordSize) {
        validateWordSize(wordSize);
        final OptionalLong offset = getCurrentPosition();
        final long byteSize = wordSize / Byte.SIZE;
        final BasicLengthElement lengthElement = new BasicLengthElement(null, offset, byteSize);
        lengthElement.setRegistry(registry);
        sequences.add(lengthElement);
        return lengthElement;
    }

    void validateWordSize(int wordSize) {
        if ((wordSize <= 0) || (0 != wordSize % Byte.SIZE)) {
            throw new IllegalArgumentException("Invalid word size: bits=" + wordSize);
        }
    }

    PlainByteSequence getActiveByteSequence() {
        final ByteSequence<?> lastSequence = sequences.peekLast();
        if ((null != lastSequence) && (lastSequence instanceof PlainByteSequence)) {
            return (PlainByteSequence) lastSequence;
        }
        final OptionalLong offset = getCurrentPosition();
        final PlainByteSequence newByteRun = new PlainByteSequence(offset);
        sequences.add(newByteRun);
        return newByteRun;
    }

    OptionalLong getCurrentPosition() {
        final ByteSequence<?> lastSequence = sequences.peekLast();
        final OptionalLong result;
        if (null == lastSequence) {
            result = OptionalLong.of(0L);
        } else {
            result = lastSequence.getOffsetPlusSize();
        }
        return result;
    }

    public NamedSubsequenceRegistry getRegistry() {
        return registry;
    }

}
