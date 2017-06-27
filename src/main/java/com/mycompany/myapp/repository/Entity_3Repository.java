package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Entity_3;
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
 * Cassandra repository for the Entity_3 entity.
 */
@Repository
public class Entity_3Repository {

    private final Session session;

    private final Validator validator;

    private Mapper<Entity_3> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Entity_3Repository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Entity_3.class);
        this.findAllStmt = session.prepare("SELECT * FROM entity_3");
        this.truncateStmt = session.prepare("TRUNCATE entity_3");
    }

    public List<Entity_3> findAll() {
        List<Entity_3> entity_3SList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Entity_3 entity_3 = new Entity_3();
                entity_3.setId(row.getUUID("id"));
                entity_3.setUsername(row.getString("username"));
                entity_3.setPassword(row.getString("password"));
                entity_3.setOtp(row.getInt("otp"));
                return entity_3;
            }
        ).forEach(entity_3SList::add);
        return entity_3SList;
    }

    public Entity_3 findOne(UUID id) {
        return mapper.get(id);
    }

    public Entity_3 save(Entity_3 entity_3) {
        if (entity_3.getId() == null) {
            entity_3.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Entity_3>> violations = validator.validate(entity_3);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(entity_3);
        return entity_3;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
