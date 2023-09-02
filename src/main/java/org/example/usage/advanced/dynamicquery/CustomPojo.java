package org.example.usage.advanced.dynamicquery;

import feign.Param;

public class CustomPojo {

    private final String name;

    private final int number;

    @Param("region_id")
    private final String regionId;

    public CustomPojo(String name, int number, String regionId) {
        this.name = name;
        this.number = number;
        this.regionId = regionId;
    }
}
