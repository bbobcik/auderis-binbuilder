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
import cz.auderis.binbuilder.api.element.ElementFrame;
import cz.auderis.binbuilder.api.element.ElementRegistry;

public abstract class AbstractElementFrame<T extends AbstractElementFrame<T>>
        extends AbstractBuilderElement<T>
        implements ElementFrame<T>
{

    private AbstractBuilderElement<?> firstElement;
    private AbstractBuilderElement<?> lastElement;
    private int elementCount;


    protected AbstractElementFrame(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
    }

    @Override
    public int getChildrenCount() {
        return elementCount;
    }

    @Override
    public <U extends BuilderElement<U>> U getFirstElement() {
        return (U) firstElement;
    }

    @Override
    public <U extends ElementRegistry<U>> U getElementRegistry() {
        // TODO
        return null;
    }

    protected void addElement(AbstractBuilderElement<?> newElement) {
        assert null != newElement;
        assert this == newElement.getParentFrame();
        assert !containsElement(newElement);
        if (null == lastElement) {
            firstElement = newElement;
            lastElement = newElement;
            newElement.setCachedOffset(0L);
        } else {
            final AbstractBuilderElement<?> secondLastElement = lastElement;
            secondLastElement.setNextElement(newElement);
            newElement.setPreviousElement(secondLastElement);
            this.lastElement = newElement;
            newElement.computeLocalFrameOffset();
        }
        ++elementCount;
    }

    boolean containsElement(BuilderElement<?> elem) {
        assert null != elem;
        if (0 == elementCount) {
            return false;
        }
        BuilderElement<?> fromStart = firstElement;
        BuilderElement<?> fromEnd = lastElement;
        do {
            if ((elem == fromStart) || (elem == fromEnd)) {
                return true;
            }
            fromStart = fromStart.getNextElement();
            if (fromStart != fromEnd) {
                fromEnd = fromStart.getPreviousElement();
            }
        } while (fromStart != fromEnd);
        return false;
    }

}
