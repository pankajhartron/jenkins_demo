package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Entity_3;

import com.mycompany.myapp.repository.Entity_3Repository;
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
 * REST controller for managing Entity_3.
 */
@RestController
@RequestMapping("/api")
public class Entity_3Resource {

    private final Logger log = LoggerFactory.getLogger(Entity_3Resource.class);

    private static final String ENTITY_NAME = "entity_3";

    private final Entity_3Repository entity_3Repository;

    public Entity_3Resource(Entity_3Repository entity_3Repository) {
        this.entity_3Repository = entity_3Repository;
    }

    /**
     * POST  /entity-3-s : Create a new entity_3.
     *
     * @param entity_3 the entity_3 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entity_3, or with status 400 (Bad Request) if the entity_3 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entity-3-s")
    @Timed
    public ResponseEntity<Entity_3> createEntity_3(@Valid @RequestBody Entity_3 entity_3) throws URISyntaxException {
        log.debug("REST request to save Entity_3 : {}", entity_3);
        if (entity_3.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entity_3 cannot already have an ID")).body(null);
        }
        Entity_3 result = entity_3Repository.save(entity_3);
        return ResponseEntity.created(new URI("/api/entity-3-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entity-3-s : Updates an existing entity_3.
     *
     * @param entity_3 the entity_3 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entity_3,
     * or with status 400 (Bad Request) if the entity_3 is not valid,
     * or with status 500 (Internal Server Error) if the entity_3 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entity-3-s")
    @Timed
    public ResponseEntity<Entity_3> updateEntity_3(@Valid @RequestBody Entity_3 entity_3) throws URISyntaxException {
        log.debug("REST request to update Entity_3 : {}", entity_3);
        if (entity_3.getId() == null) {
            return createEntity_3(entity_3);
        }
        Entity_3 result = entity_3Repository.save(entity_3);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entity_3.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entity-3-s : get all the entity_3S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entity_3S in body
     */
    @GetMapping("/entity-3-s")
    @Timed
    public List<Entity_3> getAllEntity_3S() {
        log.debug("REST request to get all Entity_3S");
        return entity_3Repository.findAll();
    }

    /**
     * GET  /entity-3-s/:id : get the "id" entity_3.
     *
     * @param id the id of the entity_3 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entity_3, or with status 404 (Not Found)
     */
    @GetMapping("/entity-3-s/{id}")
    @Timed
    public ResponseEntity<Entity_3> getEntity_3(@PathVariable String id) {
        log.debug("REST request to get Entity_3 : {}", id);
        Entity_3 entity_3 = entity_3Repository.findOne(UUID.fromString(id));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entity_3));
    }

    /**
     * DELETE  /entity-3-s/:id : delete the "id" entity_3.
     *
     * @param id the id of the entity_3 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entity-3-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntity_3(@PathVariable String id) {
        log.debug("REST request to delete Entity_3 : {}", id);
        entity_3Repository.delete(UUID.fromString(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
