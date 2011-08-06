package org.apache.tapestry.finder.entities;

import org.apache.tapestry.finder.entities.auto._ComponentSearchMap;

public class ComponentSearchMap extends _ComponentSearchMap {

    private static ComponentSearchMap instance;

    private ComponentSearchMap() {}

    public static ComponentSearchMap getInstance() {
        if(instance == null) {
            instance = new ComponentSearchMap();
        }

        return instance;
    }
}
