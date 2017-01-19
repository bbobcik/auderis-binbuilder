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

import cz.auderis.binbuilder.api.element.BuilderElement;
import cz.auderis.binbuilder.api.element.DependentElement;
import cz.auderis.binbuilder.api.element.ElementFrame;
import cz.auderis.binbuilder.api.element.ElementId;
import cz.auderis.binbuilder.api.element.ElementLocation;

import java.lang.ref.WeakReference;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public abstract class AbstractBuilderElement<T extends AbstractBuilderElement<T>> implements BuilderElement<T> {

    protected final ElementFrame<?> parent;
    protected final ElementContext context;

    private AbstractBuilderElement<?> prevElement;
    private AbstractBuilderElement<?> nextElement;
    private ElementId<?> id;
    private OptionalLong size;
    private long cachedOffset;
    private boolean contentDetermined;
    private boolean frozen;
    private List<WeakReference<DependentElement<?>>> dependentElements;

    protected AbstractBuilderElement(ElementFrame<?> parent, ElementContext context) {
        if ((null == parent) || (null == context)) {
            throw new NullPointerException();
        }
        this.parent = parent;
        this.context = context;
        this.size = OptionalLong.empty();
        this.cachedOffset = -1L;
    }

    @Override
    public <U extends ElementFrame<U>> U getParentFrame() {
        return (U) parent;
    }

    @Override
    public <U extends BuilderElement<U>> U getPreviousElement() {
        return (U) prevElement;
    }

    protected void setPreviousElement(AbstractBuilderElement<?> prevElement) {
        assert (null == prevElement) || (parent == prevElement.getParentFrame());
        this.prevElement = prevElement;
    }

    @Override
    public <U extends BuilderElement<U>> U getNextElement() {
        return (U) nextElement;
    }

    protected void setNextElement(AbstractBuilderElement<?> nextElement) {
        assert (null == nextElement) || (parent == nextElement.getParentFrame());
        this.nextElement = nextElement;
    }

    @Override
    public <U extends ElementId<U>> Optional<U> getId() {
        return Optional.ofNullable((U) id);
    }

    protected void changeId(ElementId<?> id) {
        this.id = id;
    }

    @Override
    public OptionalLong getSize() {
        return size;
    }

    protected void setSize(OptionalLong size) {
        assert null != size;
        assert size.orElse(0L) >= 0L;
        this.size = size;
        recalculateOffsets();
    }

    @Override
    public boolean isSizeDetermined() {
        return size.isPresent();
    }

    @Override
    public BasicElementLocation withinLocalFrame() {
        final OptionalLong localOffset = computeLocalFrameOffset();
        return new BasicElementLocation(localOffset, size);
    }

    @Override
    public ElementLocation within(ElementFrame<?> referenceFrame) {
        if (null == referenceFrame) {
            throw new NullPointerException();
        }
        final OptionalLong localOffset = computeLocalFrameOffset();
        final OptionalLong offset;
        if (localOffset.isPresent() && (referenceFrame != parent)) {
            final OptionalLong parentOffset = parent.within(referenceFrame).getOffset();
            if (parentOffset.isPresent()) {
                final long sum = parentOffset.getAsLong() + localOffset.getAsLong();
                offset = OptionalLong.of(sum);
            } else {
                offset = OptionalLong.empty();
            }
        } else {
            // Either offset is not known or the reference frame is our direct parent
            offset = localOffset;
        }
        return new BasicElementLocation(offset, size);
    }

    @Override
    public boolean isContentDetermined() {
        return contentDetermined;
    }

    @Override
    public ByteOrder getByteOrder() {
        return context.getOrder();
    }

    @Override
    public boolean isFrozen() {
        return frozen;
    }

    @Override
    public boolean tryFreeze() {
        if (frozen) {
            return true;
        } else if (!contentDetermined) {
            boolean done = freezeContent();
            if (!done) {
                return false;
            }
            contentDetermined = true;
        }
        assert size.isPresent();
        final OptionalLong localOffset = computeLocalFrameOffset();
        if (!localOffset.isPresent()) {
            return false;
        }
        this.frozen = true;
        return true;
    }

    protected boolean freezeContent() {
        return size.isPresent();
    }

    protected void checkStateBeforeChange() {
        if (frozen) {
            throw new IllegalArgumentException("element is already frozen");
        }
    }

    long getCachedOffset() {
        return cachedOffset;
    }

    void setCachedOffset(long newOffset) {
        this.cachedOffset = newOffset;
    }

    private OptionalLong computeLocalFrameOffset() {
        if (cachedOffset >= 0L) {
            return OptionalLong.of(cachedOffset);
        }
        long offset = 0L;
        int steps = 0;
        AbstractBuilderElement<?> predecessor = prevElement;
        while (null != predecessor) {
            assert predecessor.getParentFrame() == parent;
            final OptionalLong size = predecessor.getSize();
            if (!size.isPresent()) {
                cachedOffset = -1L;
                return OptionalLong.empty();
            }
            offset += size.getAsLong();
            final long predecessorOffset = predecessor.getCachedOffset();
            if (predecessorOffset >= 0) {
                offset += predecessorOffset;
                break;
            }
            ++steps;
            predecessor = predecessor.getPreviousElement();
        }
        if (0 != steps) {
            long prevOffset = offset;
            predecessor = prevElement;
            while ((null != prevElement) && (prevElement.getCachedOffset() < 0L)) {
                final OptionalLong size = predecessor.getSize();
                assert size.isPresent();
                prevOffset -= size.getAsLong();
                predecessor.setCachedOffset(prevOffset);
                --steps;
                predecessor = predecessor.getPreviousElement();
            }
            assert 0 == steps;
        }
        cachedOffset = offset;
        return OptionalLong.of(offset);
    }

    private void recalculateOffsets() {
//        if (cachedOffset < 0L) {
//            return;
//        }
//        long offset =
        // TODO
    }

}
