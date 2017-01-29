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

import cz.auderis.binbuilder.api.element.StringElement;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.OptionalLong;

public abstract class AbstractStringElement<T extends AbstractStringElement<T>>
        extends AbstractBuilderElement<T>
        implements StringElement<T>
{

    private static ThreadLocal<CharsetEncoder> SHARED_ASCII_ENCODER = ThreadLocal.withInitial(() -> StandardCharsets.US_ASCII.newEncoder());
    private static ThreadLocal<CharsetEncoder> SHARED_UTF8_ENCODER = ThreadLocal.withInitial(() -> StandardCharsets.UTF_8.newEncoder());
    private static ThreadLocal<ByteBuffer> SCRATCH_BUFFER = ThreadLocal.withInitial(() -> ByteBuffer.allocate(4096));

    private Charset charset;
    private CharsetEncoder encoder;

    protected AbstractStringElement(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
        this.charset = StandardCharsets.UTF_8;
    }

    @Override
    public Charset getCharset() {
        assert (null == encoder) != (null == charset);
        return (null != encoder) ? encoder.charset() : charset;
    }

    @Override
    public void setCharset(Charset newCharset) {
        if (null == newCharset) {
            throw new NullPointerException();
        }
        checkStateBeforeChange();
        if (!newCharset.equals(charset)) {
            this.charset = newCharset;
            this.encoder = null;
            stringParametersChanged();
        }
    }

    @Override
    public T withCharset(Charset newCharset) {
        setCharset(newCharset);
        return (T) this;
    }

    @Override
    public CharsetEncoder getEncoder() {
        return encoder;
    }

    @Override
    public void setEncoder(CharsetEncoder newEncoder) {
        checkStateBeforeChange();
        if (null != newEncoder) {
            this.encoder = newEncoder;
            this.charset = null;
            stringParametersChanged();
        } else if (null != encoder) {
            this.charset = encoder.charset();
            this.encoder = null;
            stringParametersChanged();
        }
    }

    @Override
    public T withEncoder(CharsetEncoder newEncoder) {
        setEncoder(newEncoder);
        return (T) this;
    }

    protected void stringParametersChanged() {
        setSize(OptionalLong.empty());
    }

    protected CharsetEncoder getEncoderInstance() {
        final CharsetEncoder result;
        if (null != encoder) {
            result = encoder;
        } else {
            assert null != charset;
            if (StandardCharsets.UTF_8.equals(charset)) {
                result = SHARED_UTF8_ENCODER.get();
            } else if (StandardCharsets.US_ASCII.equals(charset)) {
                result = SHARED_ASCII_ENCODER.get();
            } else {
                result = charset.newEncoder();
            }
        }
        result.reset();
        return result;
    }

    protected int computeByteLength(CharBuffer data) {
        if (!data.hasRemaining()) {
            return 0;
        }
        final CharsetEncoder enc = getEncoderInstance();
        final ByteBuffer scratchBuffer = SCRATCH_BUFFER.get();
        int totalLength = 0;
        CoderResult result;
        do {
            scratchBuffer.clear();
            result = enc.encode(data, scratchBuffer, true);
            totalLength += scratchBuffer.flip().remaining();
        } while (result.isOverflow());
        scratchBuffer.clear();
        enc.flush(scratchBuffer);
        totalLength += scratchBuffer.flip().remaining();
        return totalLength;
    }

}
