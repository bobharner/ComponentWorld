package org.apache.tapestry.finder.entities;

import org.apache.tapestry.finder.entities.auto._ComponentFinderMap;

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
