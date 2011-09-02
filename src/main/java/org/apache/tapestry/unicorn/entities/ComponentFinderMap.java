package org.apache.tapestry.unicorn.entities;

import org.apache.tapestry.unicorn.entities.auto._ComponentFinderMap;

public class ComponentFinderMap extends _ComponentFinderMap {

    private static ComponentFinderMap instance;

    private ComponentFinderMap() {}

    public static ComponentFinderMap getInstance() {
        if(instance == null) {
            instance = new ComponentFinderMap();
        }

        return instance;
    }
}
