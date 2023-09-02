package org.example.usage.advanced.generic;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

@Headers("Accept: application/json")
public interface BaseApi<V> {

    @RequestLine("GET /api/{key}")
    V get(@Param("key") String key);

    @RequestLine("GET /api")
    List<V> list();

    @Headers("Content-Type: application/json")
    @RequestLine("PUT /api/{key}")
    void put(@Param("key") String key, V value);
}
