// Copyright 2013 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry.unicorn.bindings;

import java.util.List;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.runtime.PageLifecycleListener;

/**
 * Interprets the binding expression a comma-separated list of values to
 * cycle among. This is most commonly used for applying alternating values
 * (such as CSS class names) in a loop or grid.
 *
 */
public class CycleBinding extends AbstractBinding implements PageLifecycleListener{
    private final List<Binding> delegates;
    private ThreadLocal<Integer> index;

    public CycleBinding(List<Binding> delegates) {
        this.delegates = delegates;
    }

    public Object get() {
        Object ret = delegates.get(getIndex()).get();
        incrIndex();
        return ret;
    }
    
    @Override
    public boolean isInvariant() {
        return false;
    }
    
    @Override
    public Class<Object> getBindingType() {
        return Object.class;
    }

    public void containingPageDidDetach() {
        index.remove();
    }

    public void containingPageDidAttach() {
        index = createThreadLocal();
    }

    public void containingPageDidLoad() {}

    public void restoreStateBeforePageAttach() {}

    private ThreadLocal<Integer> createThreadLocal() {
        return new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return Integer.valueOf(0);
            };
        };
    }

    private int getIndex() {
        return index.get().intValue();
    }

    private void incrIndex() {
        int i = index.get().intValue() + 1;
        if (i >= delegates.size()) {
            i = 0;
        }
        index.set(Integer.valueOf(i));
    }
}