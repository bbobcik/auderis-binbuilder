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

import cz.auderis.binbuilder.api.element.NumberRepresentationMode;
import cz.auderis.binbuilder.api.element.NumericElement;

import java.nio.ByteOrder;
import java.util.OptionalLong;

public abstract class AbstractNumericElement<T extends AbstractNumericElement<T>>
        extends AbstractBuilderElement<T>
        implements NumericElement<T>
{

    private NumberRepresentationMode representation;

    protected AbstractNumericElement(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
        this.representation = NumberRepresentationMode.STANDARD;
    }

    @Override
    public NumberRepresentationMode getRepresentation() {
        return representation;
    }

    @Override
    public void setRepresentation(NumberRepresentationMode newMode) {
        if (null == newMode) {
            throw new NullPointerException();
        }
        checkStateBeforeChange();
        if (newMode != representation) {
            this.representation = newMode;
            numericParametersChanged();
        }
    }

    @Override
    public T withRepresentation(NumberRepresentationMode newMode) {
        setRepresentation(newMode);
        return (T) this;
    }

    @Override
    public void setByteOrder(ByteOrder endianness) {
        checkStateBeforeChange();
        context.setOrder(endianness);
    }

    @Override
    public T withByteOrder(ByteOrder endianness) {
        setByteOrder(endianness);
        return (T) this;
    }

    protected void numericParametersChanged() {
        setSize(OptionalLong.empty());
    }

}
