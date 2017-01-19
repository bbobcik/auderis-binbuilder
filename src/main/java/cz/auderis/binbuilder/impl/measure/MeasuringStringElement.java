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

package cz.auderis.binbuilder.impl.measure;

import cz.auderis.binbuilder.api.element.ElementId;
import cz.auderis.binbuilder.impl.element.AbstractStringElement;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Optional;
import java.util.OptionalLong;

class MeasuringStringElement extends AbstractStringElement<MeasuringStringElement> {

    private static final byte[] SINK_BYTES = new byte[128];

    private long charCount;
    private long byteCount;
    private final ByteBuffer sinkBuffer;
    private final CharBuffer singleCharBuffer;

    protected MeasuringStringElement() {
        this(OptionalLong.empty(), Optional.empty());
    }

    protected MeasuringStringElement(OptionalLong offset) {
        this(offset, Optional.empty());
    }

    protected MeasuringStringElement(OptionalLong offset, Optional<ElementId> id) {
        super(offset, id);
        this.sinkBuffer = ByteBuffer.wrap(SINK_BYTES);
        this.singleCharBuffer = CharBuffer.allocate(1);
    }

    @Override
    protected void storeChar(char c) {
        singleCharBuffer.clear();
        singleCharBuffer.put(0, c);
        storeBuffer(singleCharBuffer);
    }

    @Override
    protected void storeText(CharSequence text) {
        final CharBuffer buffer = CharBuffer.wrap(text);
        storeBuffer(buffer);
    }

    protected void storeBuffer(CharBuffer buffer) {
        assert null != buffer;
        initializeEncoder();
        charCount += buffer.remaining();
        CoderResult result;
        do {
            sinkBuffer.clear();
            result = encoder.encode(buffer, sinkBuffer, false);
            sinkBuffer.flip();
            byteCount += sinkBuffer.remaining();
        } while (result.isOverflow());
    }

    @Override
    protected boolean prepareOutputData() {
        if (0L != charCount) {
            assert null != encoder;
            singleCharBuffer.position(singleCharBuffer.limit());
            sinkBuffer.clear();
            encoder.encode(singleCharBuffer, sinkBuffer, true);
            byteCount += sinkBuffer.flip().remaining();
            sinkBuffer.clear();
            encoder.flush(sinkBuffer);
            byteCount += sinkBuffer.flip().remaining();
        }
        size = OptionalLong.of(byteCount);
        return true;
    }

    void initializeEncoder() {
        if (0 != charCount) {
            assert null != encoder;
        } else if (null == encoder) {
            assert null != charset;
            encoder = charset.newEncoder();
        } else {
            encoder.reset();
        }
    }

    @Override
    public void setCharset(Charset newCharset) {
        if (0L != charCount) {
            throw new IllegalStateException("Cannot change charset, string already contains data");
        }
        super.setCharset(newCharset);
    }

    @Override
    public void setEncoder(CharsetEncoder newEncoder) {
        if (0L != charCount) {
            throw new IllegalStateException("Cannot change charset encoder, string already contains data");
        }
        super.setEncoder(newEncoder);
    }

}
