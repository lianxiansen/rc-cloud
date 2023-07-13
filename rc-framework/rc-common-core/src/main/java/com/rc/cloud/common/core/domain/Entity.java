//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.rc.cloud.common.core.domain;


import java.io.Serializable;

public abstract class Entity implements Serializable {



    public Entity() {
        super();
    }

    public boolean sameIdentityAs(Entity other){
        return this.getId().sameValueAs(other.getId());
    }
    public abstract AbstractId getId();

    @Override
    public boolean equals(Object anObject) {
        if (anObject != null && this.getClass() == anObject.getClass()) {
            Entity typedObject = (Entity) anObject;
            return this.sameIdentityAs((Entity)anObject);
        }
        return false;
    }
}
