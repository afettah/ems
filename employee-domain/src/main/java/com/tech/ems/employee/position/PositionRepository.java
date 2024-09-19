package com.tech.ems.employee.position;

import java.util.Optional;

public interface PositionRepository {
    void create(Position position);
    Optional<Position> findByCode(String code);
}
