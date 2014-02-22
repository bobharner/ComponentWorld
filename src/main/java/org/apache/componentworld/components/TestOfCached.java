package org.apache.componentworld.components;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.annotations.Cached;

public class TestOfCached
{
    private Integer accessCounter;

    @Cached
    public String getSomeValue() {
        
        if (accessCounter == null) {
            accessCounter = RandomUtils.nextInt();
        }
        accessCounter = accessCounter + 1;
        System.out.println("harner Cached textfield test: accessCounter=" + accessCounter);
        return "accessCounter=" + accessCounter;
    }
}
