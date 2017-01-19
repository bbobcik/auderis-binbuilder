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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class BasicNamedSubsequenceRegistry implements NamedSubsequenceRegistry {

    private final Map<BasicSubsequenceId, Ref> referenceById;
    private final Map<String, BasicSubsequenceId> idByName;
    int unresolvedReferences;

    public BasicNamedSubsequenceRegistry() {
        this.referenceById = new HashMap<>(32);
        this.idByName = new HashMap<>(32);
    }

    @Override
    public BasicSubsequenceId getId(String name) {
        if (null == name) {
            throw new NullPointerException();
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return idByName.get(name);
    }

    @Override
    public BasicSubsequenceId createId(String name) {
        if (null == name) {
            throw new NullPointerException();
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final BasicSubsequenceId id = idByName.computeIfAbsent(name, k -> new BasicSubsequenceId(k));
        return id;
    }

    @Override
    public Set<? extends ElementId> getIds() {
        return Collections.unmodifiableSet(referenceById.keySet());
    }

    @Override
    public BasicNamedSubsequenceReference getReference(ElementId id) {
        if (null == id) {
            throw new NullPointerException();
        }
        final BasicSubsequenceId storedId = idByName.get(id.getName());
        if ((null == storedId) ||  (storedId != id)) {
            throw new IllegalArgumentException("Subsequence ID not in registry");
        }
        final BasicNamedSubsequenceReference ref = referenceById.computeIfAbsent(storedId, this::createNewReference);
        return ref;
    }

    @Override
    public BasicNamedSubsequenceReference getReferenceByName(String name) {
        final BasicSubsequenceId id = createId(name);
        final BasicNamedSubsequenceReference ref = referenceById.computeIfAbsent(id, this::createNewReference);
        return ref;
    }

    @Override
    public Collection<? extends NamedSubsequenceReference> getReferences() {
        return Collections.unmodifiableCollection(referenceById.values());
    }

    @Override
    public Collection<? extends NamedSubsequenceReference> getUnresolvedReferences() {
        if (0L == unresolvedReferences) {
            return Collections.emptyList();
        }
        final List<BasicNamedSubsequenceReference> result = new ArrayList<>(unresolvedReferences);
        referenceById.values().stream()
                     .filter(ref -> !ref.isResolved())
                     .forEach(result::add);
        return result;
    }

    @Override
    public void register(NamedByteSubsequence subsequence) {
        if (null == subsequence) {
            throw new NullPointerException();
        }
        final Optional<ElementId> optId = subsequence.getId();
        if (optId.isPresent()) {
            final ElementId id = optId.get();
            getReference(id).setSubsequence(subsequence);
        }
    }

    @Override
    public void unregister(NamedByteSubsequence<?> subsequence) {
        if (null == subsequence) {
            throw new NullPointerException();
        }
        final Optional<ElementId> optId = subsequence.getId();
        if (optId.isPresent()) {
            final ElementId id = optId.get();
            final Ref ref = referenceById.get(id);
            if (null != ref) {
                final Optional<? extends NamedByteSubsequence<?>> referencedSubseq = ref.getSubsequence();
                if (referencedSubseq.isPresent()) {
                    assert subsequence == referencedSubseq.get();
                    ref.setSubsequence(null);
                    referenceById.remove(id, referencedSubseq);
                }
            }
        }
    }

    Ref createNewReference(BasicSubsequenceId id) {
        assert null != id;
        assert !referenceById.containsKey(id);
        ++unresolvedReferences;
        return new Ref(id);
    }

    final class Ref extends BasicNamedSubsequenceReference {
        Ref(BasicSubsequenceId id) {
            super(id, null);
        }

        @Override
        public void setSubsequence(NamedByteSubsequence<?> newRef) {
            if ((null == reference) != (null == newRef)) {
                if (null == reference) {
                    --unresolvedReferences;
                } else {
                    ++unresolvedReferences;
                }
            }
            super.setSubsequence(newRef);
        }
    }

}
