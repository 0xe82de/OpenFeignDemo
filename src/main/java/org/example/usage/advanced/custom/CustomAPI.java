package org.example.usage.advanced.custom;

import feign.RequestLine;

public interface CustomAPI extends BaseAPI {

    @RequestLine("GET /custom")
    String custom();
}
