package io.lonmstalker.repo;

import io.lonmstalker.model.ExampleObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExampleRepository extends JpaRepository<ExampleObject, UUID> {
}
