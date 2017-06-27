package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Entity_1;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Entity_1 entity.
 */
@Repository
public class Entity_1Repository {

    private final Session session;

    private final Validator validator;

    private Mapper<Entity_1> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Entity_1Repository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Entity_1.class);
        this.findAllStmt = session.prepare("SELECT * FROM entity_1");
        this.truncateStmt = session.prepare("TRUNCATE entity_1");
    }

    public List<Entity_1> findAll() {
        List<Entity_1> entity_1SList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Entity_1 entity_1 = new Entity_1();
                entity_1.setId(row.getUUID("id"));
                entity_1.setUsername(row.getString("username"));
                return entity_1;
            }
        ).forEach(entity_1SList::add);
        return entity_1SList;
    }

    public Entity_1 findOne(UUID id) {
        return mapper.get(id);
    }

    public Entity_1 save(Entity_1 entity_1) {
        if (entity_1.getId() == null) {
            entity_1.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Entity_1>> violations = validator.validate(entity_1);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(entity_1);
        return entity_1;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
