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

package cz.auderis.binbuilder.impl;

import cz.auderis.binbuilder.api.element.ElementId;

import java.util.Optional;

class BasicNamedSubsequenceReference implements NamedSubsequenceReference {

    private final ElementId id;
    private final boolean forwardReference;
    protected NamedByteSubsequence<?> reference;

    BasicNamedSubsequenceReference(ElementId id, NamedByteSubsequence<?> reference) {
        if (null == id) {
            throw new NullPointerException();
        }
        this.id = id;
        this.forwardReference = (null == reference);
        this.reference = reference;
    }

    @Override
    public ElementId getId() {
        return id;
    }

    @Override
    public boolean isForwardReference() {
        return forwardReference;
    }

    @Override
    public boolean isResolved() {
        return null != reference;
    }

    @Override
    public <T extends NamedByteSubsequence<T>> Optional<T> getSubsequence() {
        return Optional.ofNullable((T) reference);
    }

    public void setSubsequence(NamedByteSubsequence<?> newRef) {
        this.reference = newRef;
    }

}
