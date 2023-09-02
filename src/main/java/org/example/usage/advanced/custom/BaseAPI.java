package org.example.usage.advanced.custom;

import feign.RequestLine;
import org.example.usage.advanced.generic.Entity;

import java.util.List;

public interface BaseAPI {

    @RequestLine("GET /health")
    String health();

    @RequestLine("GET /all")
    List<Entity> all();
}
