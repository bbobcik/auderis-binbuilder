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

import cz.auderis.binbuilder.api.element.ElementLocation;

import java.util.OptionalLong;

public class BasicElementLocation implements ElementLocation {

    private final OptionalLong offset;
    private final OptionalLong size;
    private final OptionalLong offsetPlusSize;

    public BasicElementLocation(OptionalLong offset, OptionalLong size) {
        if ((null == offset) || (null == size)) {
            throw new NullPointerException();
        } else if (offset.orElse(0L) < 0L) {
            throw new IllegalArgumentException("Invalid offset: " + offset.getAsLong());
        } else if (size.orElse(0L) < 0L) {
            throw new IllegalArgumentException("Invalid size: " + size.getAsLong());
        }
        this.offset = offset;
        this.size = size;
        if (offset.isPresent() && size.isPresent()) {
            final long sum = offset.getAsLong() + size.getAsLong();
            this.offsetPlusSize = OptionalLong.of(sum);
        } else {
            this.offsetPlusSize = OptionalLong.empty();
        }
    }

    @Override
    public OptionalLong getOffset() {
        return offset;
    }

    @Override
    public OptionalLong getSize() {
        return size;
    }

    @Override
    public OptionalLong getOffsetPlusSize() {
        return offsetPlusSize;
    }

    @Override
    public String toString() {
        if (offset.isPresent()) {
            if (size.isPresent()) {
                return "Location{off=" + offset.getAsLong() + ", size=" + size.getAsLong() + '}';
            } else {
                return "Location{off=" + offset.getAsLong() + ", size=?}";
            }
        } else if (size.isPresent()) {
            return "Location{off=?, size=" + size.getAsLong() + '}';
        } else {
            return "Location{off=?, size=?}";
        }
    }

}
