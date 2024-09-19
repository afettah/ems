package com.tech.ems.employee.infrastructure;

import com.tech.ems.employee.position.Position;
import com.tech.ems.employee.position.PositionRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.util.Optional;

import static com.tech.ems.jooq.generated.Tables.POSITION;

@AllArgsConstructor
class PositionJooqRepositoryImpl implements PositionRepository {
    private final DSLContext dslContext;

    @Override
    public void create(Position position) {
        dslContext.insertInto(POSITION)
                .set(POSITION.CODE, position.code())
                .set(POSITION.NAME, position.name())
                .execute();
    }

    @Override
    public Optional<Position> findByCode(String code) {
        return dslContext.selectFrom(POSITION)
                .where(POSITION.CODE.eq(code))
                .fetchOptional(position -> new Position(position.getCode(), position.getName()));
    }
}
