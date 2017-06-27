package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Entity_1;
import com.mycompany.myapp.repository.Entity_1Repository;
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
 * Test class for the Entity_1Resource REST controller.
 *
 * @see Entity_1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class Entity_1ResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    @Autowired
    private Entity_1Repository entity_1Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEntity_1MockMvc;

    private Entity_1 entity_1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Entity_1Resource entity_1Resource = new Entity_1Resource(entity_1Repository);
        this.restEntity_1MockMvc = MockMvcBuilders.standaloneSetup(entity_1Resource)
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
    public static Entity_1 createEntity() {
        Entity_1 entity_1 = new Entity_1()
            .username(DEFAULT_USERNAME);
        return entity_1;
    }

    @Before
    public void initTest() {
        entity_1Repository.deleteAll();
        entity_1 = createEntity();
    }

    @Test
    public void createEntity_1() throws Exception {
        int databaseSizeBeforeCreate = entity_1Repository.findAll().size();

        // Create the Entity_1
        restEntity_1MockMvc.perform(post("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_1)))
            .andExpect(status().isCreated());

        // Validate the Entity_1 in the database
        List<Entity_1> entity_1List = entity_1Repository.findAll();
        assertThat(entity_1List).hasSize(databaseSizeBeforeCreate + 1);
        Entity_1 testEntity_1 = entity_1List.get(entity_1List.size() - 1);
        assertThat(testEntity_1.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    public void createEntity_1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entity_1Repository.findAll().size();

        // Create the Entity_1 with an existing ID
        entity_1.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntity_1MockMvc.perform(post("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_1)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entity_1> entity_1List = entity_1Repository.findAll();
        assertThat(entity_1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entity_1Repository.findAll().size();
        // set the field null
        entity_1.setUsername(null);

        // Create the Entity_1, which fails.

        restEntity_1MockMvc.perform(post("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_1)))
            .andExpect(status().isBadRequest());

        List<Entity_1> entity_1List = entity_1Repository.findAll();
        assertThat(entity_1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEntity_1S() throws Exception {
        // Initialize the database
        entity_1Repository.save(entity_1);

        // Get all the entity_1List
        restEntity_1MockMvc.perform(get("/api/entity-1-s"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entity_1.getId().toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())));
    }

    @Test
    public void getEntity_1() throws Exception {
        // Initialize the database
        entity_1Repository.save(entity_1);

        // Get the entity_1
        restEntity_1MockMvc.perform(get("/api/entity-1-s/{id}", entity_1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entity_1.getId().toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()));
    }

    @Test
    public void getNonExistingEntity_1() throws Exception {
        // Get the entity_1
        restEntity_1MockMvc.perform(get("/api/entity-1-s/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEntity_1() throws Exception {
        // Initialize the database
        entity_1Repository.save(entity_1);
        int databaseSizeBeforeUpdate = entity_1Repository.findAll().size();

        // Update the entity_1
        Entity_1 updatedEntity_1 = entity_1Repository.findOne(entity_1.getId());
        updatedEntity_1
            .username(UPDATED_USERNAME);

        restEntity_1MockMvc.perform(put("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntity_1)))
            .andExpect(status().isOk());

        // Validate the Entity_1 in the database
        List<Entity_1> entity_1List = entity_1Repository.findAll();
        assertThat(entity_1List).hasSize(databaseSizeBeforeUpdate);
        Entity_1 testEntity_1 = entity_1List.get(entity_1List.size() - 1);
        assertThat(testEntity_1.getUsername()).isEqualTo(UPDATED_USERNAME);
    }

    @Test
    public void updateNonExistingEntity_1() throws Exception {
        int databaseSizeBeforeUpdate = entity_1Repository.findAll().size();

        // Create the Entity_1

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntity_1MockMvc.perform(put("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_1)))
            .andExpect(status().isCreated());

        // Validate the Entity_1 in the database
        List<Entity_1> entity_1List = entity_1Repository.findAll();
        assertThat(entity_1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEntity_1() throws Exception {
        // Initialize the database
        entity_1Repository.save(entity_1);
        int databaseSizeBeforeDelete = entity_1Repository.findAll().size();

        // Get the entity_1
        restEntity_1MockMvc.perform(delete("/api/entity-1-s/{id}", entity_1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entity_1> entity_1List = entity_1Repository.findAll();
        assertThat(entity_1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entity_1.class);
        Entity_1 entity_11 = new Entity_1();
        entity_11.setId(UUID.randomUUID());
        Entity_1 entity_12 = new Entity_1();
        entity_12.setId(entity_11.getId());
        assertThat(entity_11).isEqualTo(entity_12);
        entity_12.setId(UUID.randomUUID());
        assertThat(entity_11).isNotEqualTo(entity_12);
        entity_11.setId(null);
        assertThat(entity_11).isNotEqualTo(entity_12);
    }
}
