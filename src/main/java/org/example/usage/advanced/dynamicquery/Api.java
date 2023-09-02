package org.example.usage.advanced.dynamicquery;

import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface Api<V> {

    @RequestLine("GET /find")
    V find(@QueryMap Map<String, Object> queryMap);

    @RequestLine("GET /find")
    V find(@QueryMap CustomPojo customPojo);
}
