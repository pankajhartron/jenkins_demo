package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Foo;
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
 * Cassandra repository for the Foo entity.
 */
@Repository
public class FooRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Foo> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public FooRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Foo.class);
        this.findAllStmt = session.prepare("SELECT * FROM foo");
        this.truncateStmt = session.prepare("TRUNCATE foo");
    }

    public List<Foo> findAll() {
        List<Foo> foosList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Foo foo = new Foo();
                foo.setId(row.getUUID("id"));
                return foo;
            }
        ).forEach(foosList::add);
        return foosList;
    }

    public Foo findOne(UUID id) {
        return mapper.get(id);
    }

    public Foo save(Foo foo) {
        if (foo.getId() == null) {
            foo.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Foo>> violations = validator.validate(foo);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(foo);
        return foo;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
