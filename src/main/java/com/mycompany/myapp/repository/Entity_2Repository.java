package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Entity_2;
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
 * Cassandra repository for the Entity_2 entity.
 */
@Repository
public class Entity_2Repository {

    private final Session session;

    private final Validator validator;

    private Mapper<Entity_2> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Entity_2Repository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Entity_2.class);
        this.findAllStmt = session.prepare("SELECT * FROM entity_2");
        this.truncateStmt = session.prepare("TRUNCATE entity_2");
    }

    public List<Entity_2> findAll() {
        List<Entity_2> entity_2SList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Entity_2 entity_2 = new Entity_2();
                entity_2.setId(row.getUUID("id"));
                entity_2.setUsername(row.getString("username"));
                return entity_2;
            }
        ).forEach(entity_2SList::add);
        return entity_2SList;
    }

    public Entity_2 findOne(UUID id) {
        return mapper.get(id);
    }

    public Entity_2 save(Entity_2 entity_2) {
        if (entity_2.getId() == null) {
            entity_2.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Entity_2>> violations = validator.validate(entity_2);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(entity_2);
        return entity_2;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
