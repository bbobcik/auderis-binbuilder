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

import cz.auderis.binbuilder.api.element.LengthElement;
import cz.auderis.binbuilder.api.element.ElementId;

import java.util.Optional;
import java.util.OptionalLong;

public class BasicLengthElement extends AbstractNumericElement<BasicLengthElement> implements LengthElement<BasicLengthElement> {

    protected NamedSubsequenceReference reference;

    protected BasicLengthElement(NamedSubsequenceReference ref, long size) {
        this(ref, OptionalLong.empty(), size, Optional.empty());
    }

    protected BasicLengthElement(NamedSubsequenceReference ref, OptionalLong offset, long size) {
        this(ref, offset.empty(), size, Optional.empty());
    }

    protected BasicLengthElement(NamedSubsequenceReference ref, OptionalLong offset, long size, Optional<ElementId> id) {
        super(offset, size, id);
        this.reference = ref;
    }

    @Override
    public Optional<NamedSubsequenceReference> getSourceReference() {
        return Optional.ofNullable(reference);
    }

    @Override
    public void setSourceReference(Optional<NamedSubsequenceReference> ref) {
        this.reference = ref.orElse(null);
    }

    @Override
    public BasicLengthElement withSourceReference(Optional<NamedSubsequenceReference> ref) {
        setSourceReference(ref);
        return this;
    }

    @Override
    public boolean isSourceLocationFixed() {
        if (null == reference) {
            return false;
        }
        final Optional<NamedByteSubsequence> optSubsequence = reference.getSubsequence();
        if (!optSubsequence.isPresent()) {
            return false;
        }
        return optSubsequence.get().isOutputLocationFixed();
    }

    @Override
    public OptionalLong getSourceLength() {
        if (null == reference) {
            return OptionalLong.empty();
        }
        final Optional<NamedByteSubsequence> optSubsequence = reference.getSubsequence();
        if (!optSubsequence.isPresent()) {
            return OptionalLong.empty();
        }
        return optSubsequence.get().getSize();
    }

    @Override
    protected boolean prepareForClose() {
        return isSourceLocationFixed();
    }

}
