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

import cz.auderis.binbuilder.api.element.ElementFrame;
import cz.auderis.binbuilder.api.element.IntegerElement;
import cz.auderis.binbuilder.api.element.LengthElement;
import cz.auderis.binbuilder.api.element.RawByteSequence;
import cz.auderis.binbuilder.api.element.StringElement;

public class BasicElementFrame extends AbstractElementFrame<BasicElementFrame> {

    protected BasicElementFrame(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
    }

    @Override
    public BasicRawByteSequence addRawBytes() {
        final ElementContext ctx = new ElementContext(context);
        final BasicRawByteSequence byteSequence = new BasicRawByteSequence(this, ctx);
        addElement(byteSequence);
        return byteSequence;
    }

    @Override
    public BasicIntegerElement addNumber() {
        final ElementContext ctx = new ElementContext(context);
        final BasicIntegerElement intElement = new BasicIntegerElement(this, ctx);
        addElement(intElement);
        return intElement;
    }

    @Override
    public <U extends StringElement<U>> U addText() {
        final ElementContext ctx = new ElementContext(context);
        return null;
    }

    @Override
    public <U extends LengthElement<U>> U addLengthElement() {
        return null;
    }

    @Override
    public <U extends ElementFrame<U>> U addSubFrame() {
        return null;
    }

}
