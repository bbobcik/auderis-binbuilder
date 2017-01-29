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

import java.nio.ByteOrder;
import java.util.EnumMap;
import java.util.Map;

public class ElementContext {

    private final ElementContext parentContext;
    private ByteOrder order;
    private Map<NumberRepresentationMode, NumberRepresentationEncoder> numberEncoders;

    public ElementContext(ElementContext parentContext) {
        this.parentContext = parentContext;
    }

    public ByteOrder getOrder() {
        if (null != order) {
            return order;
        }
        assert null != parentContext;
        return parentContext.getOrder();
    }

    public void setOrder(ByteOrder newOrder) {
        this.order = newOrder;
    }

    public NumberRepresentationEncoder getEncoder(NumberRepresentationMode mode) {
        if (null == mode) {
            throw new NullPointerException();
        }
        final NumberRepresentationEncoder encoder;
        if (null != numberEncoders) {
            encoder = numberEncoders.get(mode);
        } else {
            encoder = null;
        }
        if (null != encoder) {
            return encoder;
        }
        assert null != parentContext;
        return parentContext.getEncoder(mode);
    }

    public void setEncoder(NumberRepresentationMode mode, NumberRepresentationEncoder encoder) {
        if (null == mode) {
            throw new NullPointerException();
        } else if (null != encoder) {
            if (null == numberEncoders) {
                this.numberEncoders = new EnumMap<>(NumberRepresentationMode.class);
            }
            numberEncoders.put(mode, encoder);
        } else if (null != numberEncoders) {
            numberEncoders.remove(mode);
            if (numberEncoders.isEmpty()) {
                this.numberEncoders = null;
            }
        }
    }

    protected Map<NumberRepresentationMode, NumberRepresentationEncoder> getNumberEncoders() {
        return numberEncoders;
    }

    protected void setNumberEncoders(Map<NumberRepresentationMode, NumberRepresentationEncoder> numberEncoders) {
        this.numberEncoders = numberEncoders;
    }

}
