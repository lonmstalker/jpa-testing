package io.lonmstalker.service;

import io.lonmstalker.model.ExampleObject;

import java.util.List;

public interface ExampleService {
    void disable(ExampleObject obj);
    void enable(ExampleObject obj);
    List<ExampleObject> findAll();
}
