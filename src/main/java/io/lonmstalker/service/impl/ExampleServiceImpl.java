package io.lonmstalker.service.impl;

import io.lonmstalker.model.ExampleObject;
import io.lonmstalker.repo.ExampleRepository;
import io.lonmstalker.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExampleServiceImpl implements ExampleService {
    private final ExampleRepository exampleRepository;

    @Autowired
    public ExampleServiceImpl(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    @Transactional
    public void disable(ExampleObject obj) {
        if (!obj.getEnabled())
            throw new RuntimeException("must be enabled");
        obj.setEnabled(false);
        this.exampleRepository.save(obj);
    }

    @Override
    @Transactional
    public void enable(ExampleObject obj) {
        if (obj.getEnabled())
            throw new RuntimeException("must be disabled");
        obj.setEnabled(true);
        this.exampleRepository.save(obj);
    }

    @Override
    public List<ExampleObject> findAll() {
        return this.exampleRepository.findAll();
    }
}
