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

import cz.auderis.binbuilder.api.element.BuilderElement;
import cz.auderis.binbuilder.api.element.ElementId;
import cz.auderis.binbuilder.api.element.LengthElement;

import java.util.Optional;
import java.util.OptionalLong;

public class BasicLengthElement
        extends AbstractGeneralIntegerElement<BasicLengthElement>
        implements LengthElement<BasicLengthElement>
{

    private final ElementReference reference;

    protected BasicLengthElement(AbstractElementFrame<?> parent, ElementContext context, ElementId<?> dependantId) {
        super(parent, context);
        this.reference = new ElementReference(dependantId);
    }

    @Override
    public <U extends ElementId<U>> U getReferenceId() {
        return reference.getReferenceId();
    }

    @Override
    public boolean isSourceLocationFixed() {
        final Optional<BuilderElement<?>> dependant = (Optional<BuilderElement<?>>) reference.getDependant();
        if (!dependant.isPresent()) {
            return false;
        }
        final OptionalLong dependantSize = dependant.get().getSize();
        return dependantSize.isPresent();
    }

    @Override
    public OptionalLong getSourceLength() {
        final Optional<BuilderElement<?>> dependant = (Optional<BuilderElement<?>>) reference.getDependant();
        if (!dependant.isPresent()) {
            return OptionalLong.empty();
        }
        final OptionalLong dependantSize = dependant.get().getSize();
        return dependantSize;
    }

    @Override
    public byte getByteValue() {
        return (byte) getSourceLength().orElse(-1L);
    }

    @Override
    public short getShortValue() {
        return (short) getSourceLength().orElse(-1L);
    }

    @Override
    public int getValue() {
        return (int) getSourceLength().orElse(-1L);
    }

    @Override
    public long getLongValue() {
        return getSourceLength().orElse(-1L);
    }

}
