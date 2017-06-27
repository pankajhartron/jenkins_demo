package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Entity_2;

import com.mycompany.myapp.repository.Entity_2Repository;
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
 * REST controller for managing Entity_2.
 */
@RestController
@RequestMapping("/api")
public class Entity_2Resource {

    private final Logger log = LoggerFactory.getLogger(Entity_2Resource.class);

    private static final String ENTITY_NAME = "entity_2";

    private final Entity_2Repository entity_2Repository;

    public Entity_2Resource(Entity_2Repository entity_2Repository) {
        this.entity_2Repository = entity_2Repository;
    }

    /**
     * POST  /entity-2-s : Create a new entity_2.
     *
     * @param entity_2 the entity_2 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entity_2, or with status 400 (Bad Request) if the entity_2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entity-2-s")
    @Timed
    public ResponseEntity<Entity_2> createEntity_2(@Valid @RequestBody Entity_2 entity_2) throws URISyntaxException {
        log.debug("REST request to save Entity_2 : {}", entity_2);
        if (entity_2.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entity_2 cannot already have an ID")).body(null);
        }
        Entity_2 result = entity_2Repository.save(entity_2);
        return ResponseEntity.created(new URI("/api/entity-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entity-2-s : Updates an existing entity_2.
     *
     * @param entity_2 the entity_2 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entity_2,
     * or with status 400 (Bad Request) if the entity_2 is not valid,
     * or with status 500 (Internal Server Error) if the entity_2 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entity-2-s")
    @Timed
    public ResponseEntity<Entity_2> updateEntity_2(@Valid @RequestBody Entity_2 entity_2) throws URISyntaxException {
        log.debug("REST request to update Entity_2 : {}", entity_2);
        if (entity_2.getId() == null) {
            return createEntity_2(entity_2);
        }
        Entity_2 result = entity_2Repository.save(entity_2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entity_2.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entity-2-s : get all the entity_2S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entity_2S in body
     */
    @GetMapping("/entity-2-s")
    @Timed
    public List<Entity_2> getAllEntity_2S() {
        log.debug("REST request to get all Entity_2S");
        return entity_2Repository.findAll();
    }

    /**
     * GET  /entity-2-s/:id : get the "id" entity_2.
     *
     * @param id the id of the entity_2 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entity_2, or with status 404 (Not Found)
     */
    @GetMapping("/entity-2-s/{id}")
    @Timed
    public ResponseEntity<Entity_2> getEntity_2(@PathVariable String id) {
        log.debug("REST request to get Entity_2 : {}", id);
        Entity_2 entity_2 = entity_2Repository.findOne(UUID.fromString(id));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entity_2));
    }

    /**
     * DELETE  /entity-2-s/:id : delete the "id" entity_2.
     *
     * @param id the id of the entity_2 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entity-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntity_2(@PathVariable String id) {
        log.debug("REST request to delete Entity_2 : {}", id);
        entity_2Repository.delete(UUID.fromString(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
