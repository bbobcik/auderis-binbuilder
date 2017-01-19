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

import java.nio.ByteBuffer;
import java.util.OptionalLong;

class PlainByteSequence extends AbstractByteSubsequence<PlainByteSequence> {

    long provisionalLength;

    protected PlainByteSequence() {
        this(OptionalLong.empty(), OptionalLong.empty());
    }

    protected PlainByteSequence(OptionalLong offset) {
        this(offset, OptionalLong.empty());
    }

    protected PlainByteSequence(OptionalLong offset, OptionalLong size) {
        super(offset, size);
    }

    public void addByte(int byteValue) {
        checkStateBeforeUpdate();
        storeByte(byteValue);
        ++provisionalLength;
    }

    public void addBytes(byte[] byteArray) {
        checkStateBeforeUpdate();
        if ((null != byteArray) && (0 != byteArray.length)) {
            storeBytes(byteArray);
            provisionalLength += byteArray.length;
        }
    }

    public void addBytes(ByteBuffer buffer) {
        if ((null != buffer) && buffer.hasRemaining()) {
            final long dataSize = buffer.remaining();
            storeBytes(buffer);
            provisionalLength += dataSize;
        }
    }

    protected void storeByte(int byteValue) {
        // Does nothing
    }

    protected void storeBytes(byte[] byteArray) {
        // Does nothing
    }

    protected void storeBytes(ByteBuffer buffer) {
        assert null != buffer;
        buffer.position(buffer.limit());
    }

    @Override
    protected void fixLocation() {
        updateSize(OptionalLong.of(provisionalLength));
    }

}
