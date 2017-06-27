package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Entity_2;
import com.mycompany.myapp.repository.Entity_2Repository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Entity_2Resource REST controller.
 *
 * @see Entity_2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class Entity_2ResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    @Autowired
    private Entity_2Repository entity_2Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEntity_2MockMvc;

    private Entity_2 entity_2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Entity_2Resource entity_2Resource = new Entity_2Resource(entity_2Repository);
        this.restEntity_2MockMvc = MockMvcBuilders.standaloneSetup(entity_2Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entity_2 createEntity() {
        Entity_2 entity_2 = new Entity_2()
            .username(DEFAULT_USERNAME);
        return entity_2;
    }

    @Before
    public void initTest() {
        entity_2Repository.deleteAll();
        entity_2 = createEntity();
    }

    @Test
    public void createEntity_2() throws Exception {
        int databaseSizeBeforeCreate = entity_2Repository.findAll().size();

        // Create the Entity_2
        restEntity_2MockMvc.perform(post("/api/entity-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_2)))
            .andExpect(status().isCreated());

        // Validate the Entity_2 in the database
        List<Entity_2> entity_2List = entity_2Repository.findAll();
        assertThat(entity_2List).hasSize(databaseSizeBeforeCreate + 1);
        Entity_2 testEntity_2 = entity_2List.get(entity_2List.size() - 1);
        assertThat(testEntity_2.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    public void createEntity_2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entity_2Repository.findAll().size();

        // Create the Entity_2 with an existing ID
        entity_2.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntity_2MockMvc.perform(post("/api/entity-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_2)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entity_2> entity_2List = entity_2Repository.findAll();
        assertThat(entity_2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entity_2Repository.findAll().size();
        // set the field null
        entity_2.setUsername(null);

        // Create the Entity_2, which fails.

        restEntity_2MockMvc.perform(post("/api/entity-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_2)))
            .andExpect(status().isBadRequest());

        List<Entity_2> entity_2List = entity_2Repository.findAll();
        assertThat(entity_2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEntity_2S() throws Exception {
        // Initialize the database
        entity_2Repository.save(entity_2);

        // Get all the entity_2List
        restEntity_2MockMvc.perform(get("/api/entity-2-s"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entity_2.getId().toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())));
    }

    @Test
    public void getEntity_2() throws Exception {
        // Initialize the database
        entity_2Repository.save(entity_2);

        // Get the entity_2
        restEntity_2MockMvc.perform(get("/api/entity-2-s/{id}", entity_2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entity_2.getId().toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()));
    }

    @Test
    public void getNonExistingEntity_2() throws Exception {
        // Get the entity_2
        restEntity_2MockMvc.perform(get("/api/entity-2-s/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEntity_2() throws Exception {
        // Initialize the database
        entity_2Repository.save(entity_2);
        int databaseSizeBeforeUpdate = entity_2Repository.findAll().size();

        // Update the entity_2
        Entity_2 updatedEntity_2 = entity_2Repository.findOne(entity_2.getId());
        updatedEntity_2
            .username(UPDATED_USERNAME);

        restEntity_2MockMvc.perform(put("/api/entity-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntity_2)))
            .andExpect(status().isOk());

        // Validate the Entity_2 in the database
        List<Entity_2> entity_2List = entity_2Repository.findAll();
        assertThat(entity_2List).hasSize(databaseSizeBeforeUpdate);
        Entity_2 testEntity_2 = entity_2List.get(entity_2List.size() - 1);
        assertThat(testEntity_2.getUsername()).isEqualTo(UPDATED_USERNAME);
    }

    @Test
    public void updateNonExistingEntity_2() throws Exception {
        int databaseSizeBeforeUpdate = entity_2Repository.findAll().size();

        // Create the Entity_2

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntity_2MockMvc.perform(put("/api/entity-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_2)))
            .andExpect(status().isCreated());

        // Validate the Entity_2 in the database
        List<Entity_2> entity_2List = entity_2Repository.findAll();
        assertThat(entity_2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEntity_2() throws Exception {
        // Initialize the database
        entity_2Repository.save(entity_2);
        int databaseSizeBeforeDelete = entity_2Repository.findAll().size();

        // Get the entity_2
        restEntity_2MockMvc.perform(delete("/api/entity-2-s/{id}", entity_2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entity_2> entity_2List = entity_2Repository.findAll();
        assertThat(entity_2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entity_2.class);
        Entity_2 entity_21 = new Entity_2();
        entity_21.setId(UUID.randomUUID());
        Entity_2 entity_22 = new Entity_2();
        entity_22.setId(entity_21.getId());
        assertThat(entity_21).isEqualTo(entity_22);
        entity_22.setId(UUID.randomUUID());
        assertThat(entity_21).isNotEqualTo(entity_22);
        entity_21.setId(null);
        assertThat(entity_21).isNotEqualTo(entity_22);
    }
}
