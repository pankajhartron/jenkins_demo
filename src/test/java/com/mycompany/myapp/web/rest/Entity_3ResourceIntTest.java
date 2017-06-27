package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Entity_3;
import com.mycompany.myapp.repository.Entity_3Repository;
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
 * Test class for the Entity_3Resource REST controller.
 *
 * @see Entity_3Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class Entity_3ResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_OTP = 1;
    private static final Integer UPDATED_OTP = 2;

    @Autowired
    private Entity_3Repository entity_3Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEntity_3MockMvc;

    private Entity_3 entity_3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Entity_3Resource entity_3Resource = new Entity_3Resource(entity_3Repository);
        this.restEntity_3MockMvc = MockMvcBuilders.standaloneSetup(entity_3Resource)
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
    public static Entity_3 createEntity() {
        Entity_3 entity_3 = new Entity_3()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .otp(DEFAULT_OTP);
        return entity_3;
    }

    @Before
    public void initTest() {
        entity_3Repository.deleteAll();
        entity_3 = createEntity();
    }

    @Test
    public void createEntity_3() throws Exception {
        int databaseSizeBeforeCreate = entity_3Repository.findAll().size();

        // Create the Entity_3
        restEntity_3MockMvc.perform(post("/api/entity-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_3)))
            .andExpect(status().isCreated());

        // Validate the Entity_3 in the database
        List<Entity_3> entity_3List = entity_3Repository.findAll();
        assertThat(entity_3List).hasSize(databaseSizeBeforeCreate + 1);
        Entity_3 testEntity_3 = entity_3List.get(entity_3List.size() - 1);
        assertThat(testEntity_3.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEntity_3.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEntity_3.getOtp()).isEqualTo(DEFAULT_OTP);
    }

    @Test
    public void createEntity_3WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entity_3Repository.findAll().size();

        // Create the Entity_3 with an existing ID
        entity_3.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntity_3MockMvc.perform(post("/api/entity-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_3)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entity_3> entity_3List = entity_3Repository.findAll();
        assertThat(entity_3List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entity_3Repository.findAll().size();
        // set the field null
        entity_3.setUsername(null);

        // Create the Entity_3, which fails.

        restEntity_3MockMvc.perform(post("/api/entity-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_3)))
            .andExpect(status().isBadRequest());

        List<Entity_3> entity_3List = entity_3Repository.findAll();
        assertThat(entity_3List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = entity_3Repository.findAll().size();
        // set the field null
        entity_3.setPassword(null);

        // Create the Entity_3, which fails.

        restEntity_3MockMvc.perform(post("/api/entity-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_3)))
            .andExpect(status().isBadRequest());

        List<Entity_3> entity_3List = entity_3Repository.findAll();
        assertThat(entity_3List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEntity_3S() throws Exception {
        // Initialize the database
        entity_3Repository.save(entity_3);

        // Get all the entity_3List
        restEntity_3MockMvc.perform(get("/api/entity-3-s"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entity_3.getId().toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].otp").value(hasItem(DEFAULT_OTP)));
    }

    @Test
    public void getEntity_3() throws Exception {
        // Initialize the database
        entity_3Repository.save(entity_3);

        // Get the entity_3
        restEntity_3MockMvc.perform(get("/api/entity-3-s/{id}", entity_3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entity_3.getId().toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.otp").value(DEFAULT_OTP));
    }

    @Test
    public void getNonExistingEntity_3() throws Exception {
        // Get the entity_3
        restEntity_3MockMvc.perform(get("/api/entity-3-s/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEntity_3() throws Exception {
        // Initialize the database
        entity_3Repository.save(entity_3);
        int databaseSizeBeforeUpdate = entity_3Repository.findAll().size();

        // Update the entity_3
        Entity_3 updatedEntity_3 = entity_3Repository.findOne(entity_3.getId());
        updatedEntity_3
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .otp(UPDATED_OTP);

        restEntity_3MockMvc.perform(put("/api/entity-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntity_3)))
            .andExpect(status().isOk());

        // Validate the Entity_3 in the database
        List<Entity_3> entity_3List = entity_3Repository.findAll();
        assertThat(entity_3List).hasSize(databaseSizeBeforeUpdate);
        Entity_3 testEntity_3 = entity_3List.get(entity_3List.size() - 1);
        assertThat(testEntity_3.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEntity_3.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEntity_3.getOtp()).isEqualTo(UPDATED_OTP);
    }

    @Test
    public void updateNonExistingEntity_3() throws Exception {
        int databaseSizeBeforeUpdate = entity_3Repository.findAll().size();

        // Create the Entity_3

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntity_3MockMvc.perform(put("/api/entity-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity_3)))
            .andExpect(status().isCreated());

        // Validate the Entity_3 in the database
        List<Entity_3> entity_3List = entity_3Repository.findAll();
        assertThat(entity_3List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEntity_3() throws Exception {
        // Initialize the database
        entity_3Repository.save(entity_3);
        int databaseSizeBeforeDelete = entity_3Repository.findAll().size();

        // Get the entity_3
        restEntity_3MockMvc.perform(delete("/api/entity-3-s/{id}", entity_3.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entity_3> entity_3List = entity_3Repository.findAll();
        assertThat(entity_3List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entity_3.class);
        Entity_3 entity_31 = new Entity_3();
        entity_31.setId(UUID.randomUUID());
        Entity_3 entity_32 = new Entity_3();
        entity_32.setId(entity_31.getId());
        assertThat(entity_31).isEqualTo(entity_32);
        entity_32.setId(UUID.randomUUID());
        assertThat(entity_31).isNotEqualTo(entity_32);
        entity_31.setId(null);
        assertThat(entity_31).isNotEqualTo(entity_32);
    }
}
