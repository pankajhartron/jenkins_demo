package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Entity_1;

import com.mycompany.myapp.repository.Entity_1Repository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Entity_1.
 */
@RestController
@RequestMapping("/api")
public class Entity_1Resource {

    private final Logger log = LoggerFactory.getLogger(Entity_1Resource.class);

    private static final String ENTITY_NAME = "entity_1";

    private final Entity_1Repository entity_1Repository;

    public Entity_1Resource(Entity_1Repository entity_1Repository) {
        this.entity_1Repository = entity_1Repository;
    }

    /**
     * POST  /entity-1-s : Create a new entity_1.
     *
     * @param entity_1 the entity_1 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entity_1, or with status 400 (Bad Request) if the entity_1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entity-1-s")
    @Timed
    public ResponseEntity<Entity_1> createEntity_1(@Valid @RequestBody Entity_1 entity_1) throws URISyntaxException {
        log.debug("REST request to save Entity_1 : {}", entity_1);
        if (entity_1.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entity_1 cannot already have an ID")).body(null);
        }
        Entity_1 result = entity_1Repository.save(entity_1);
        return ResponseEntity.created(new URI("/api/entity-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entity-1-s : Updates an existing entity_1.
     *
     * @param entity_1 the entity_1 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entity_1,
     * or with status 400 (Bad Request) if the entity_1 is not valid,
     * or with status 500 (Internal Server Error) if the entity_1 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entity-1-s")
    @Timed
    public ResponseEntity<Entity_1> updateEntity_1(@Valid @RequestBody Entity_1 entity_1) throws URISyntaxException {
        log.debug("REST request to update Entity_1 : {}", entity_1);
        if (entity_1.getId() == null) {
            return createEntity_1(entity_1);
        }
        Entity_1 result = entity_1Repository.save(entity_1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entity_1.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entity-1-s : get all the entity_1S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entity_1S in body
     */
    @GetMapping("/entity-1-s")
    @Timed
    public List<Entity_1> getAllEntity_1S() {
        log.debug("REST request to get all Entity_1S");
        return entity_1Repository.findAll();
    }

    /**
     * GET  /entity-1-s/:id : get the "id" entity_1.
     *
     * @param id the id of the entity_1 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entity_1, or with status 404 (Not Found)
     */
    @GetMapping("/entity-1-s/{id}")
    @Timed
    public ResponseEntity<Entity_1> getEntity_1(@PathVariable String id) {
        log.debug("REST request to get Entity_1 : {}", id);
        Entity_1 entity_1 = entity_1Repository.findOne(UUID.fromString(id));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entity_1));
    }

    /**
     * DELETE  /entity-1-s/:id : delete the "id" entity_1.
     *
     * @param id the id of the entity_1 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entity-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntity_1(@PathVariable String id) {
        log.debug("REST request to delete Entity_1 : {}", id);
        entity_1Repository.delete(UUID.fromString(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
