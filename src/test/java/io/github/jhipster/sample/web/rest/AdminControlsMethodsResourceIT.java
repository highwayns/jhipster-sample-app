package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminControlsMethods;
import io.github.jhipster.sample.repository.AdminControlsMethodsRepository;
import io.github.jhipster.sample.service.dto.AdminControlsMethodsDTO;
import io.github.jhipster.sample.service.mapper.AdminControlsMethodsMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdminControlsMethodsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminControlsMethodsResourceIT {

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_ARGUMENT = "AAAAAAAAAA";
    private static final String UPDATED_ARGUMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Integer DEFAULT_P_ID = 1;
    private static final Integer UPDATED_P_ID = 2;

    private static final String ENTITY_API_URL = "/api/admin-controls-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminControlsMethodsRepository adminControlsMethodsRepository;

    @Autowired
    private AdminControlsMethodsMapper adminControlsMethodsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminControlsMethodsMockMvc;

    private AdminControlsMethods adminControlsMethods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminControlsMethods createEntity(EntityManager em) {
        AdminControlsMethods adminControlsMethods = new AdminControlsMethods()
            .method(DEFAULT_METHOD)
            .argument(DEFAULT_ARGUMENT)
            .order(DEFAULT_ORDER)
            .pId(DEFAULT_P_ID);
        return adminControlsMethods;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminControlsMethods createUpdatedEntity(EntityManager em) {
        AdminControlsMethods adminControlsMethods = new AdminControlsMethods()
            .method(UPDATED_METHOD)
            .argument(UPDATED_ARGUMENT)
            .order(UPDATED_ORDER)
            .pId(UPDATED_P_ID);
        return adminControlsMethods;
    }

    @BeforeEach
    public void initTest() {
        adminControlsMethods = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminControlsMethods() throws Exception {
        int databaseSizeBeforeCreate = adminControlsMethodsRepository.findAll().size();
        // Create the AdminControlsMethods
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);
        restAdminControlsMethodsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeCreate + 1);
        AdminControlsMethods testAdminControlsMethods = adminControlsMethodsList.get(adminControlsMethodsList.size() - 1);
        assertThat(testAdminControlsMethods.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testAdminControlsMethods.getArgument()).isEqualTo(DEFAULT_ARGUMENT);
        assertThat(testAdminControlsMethods.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAdminControlsMethods.getpId()).isEqualTo(DEFAULT_P_ID);
    }

    @Test
    @Transactional
    void createAdminControlsMethodsWithExistingId() throws Exception {
        // Create the AdminControlsMethods with an existing ID
        adminControlsMethods.setId(1L);
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        int databaseSizeBeforeCreate = adminControlsMethodsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminControlsMethodsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkArgumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminControlsMethodsRepository.findAll().size();
        // set the field null
        adminControlsMethods.setArgument(null);

        // Create the AdminControlsMethods, which fails.
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        restAdminControlsMethodsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminControlsMethodsRepository.findAll().size();
        // set the field null
        adminControlsMethods.setOrder(null);

        // Create the AdminControlsMethods, which fails.
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        restAdminControlsMethodsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminControlsMethodsRepository.findAll().size();
        // set the field null
        adminControlsMethods.setpId(null);

        // Create the AdminControlsMethods, which fails.
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        restAdminControlsMethodsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminControlsMethods() throws Exception {
        // Initialize the database
        adminControlsMethodsRepository.saveAndFlush(adminControlsMethods);

        // Get all the adminControlsMethodsList
        restAdminControlsMethodsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminControlsMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD)))
            .andExpect(jsonPath("$.[*].argument").value(hasItem(DEFAULT_ARGUMENT)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID)));
    }

    @Test
    @Transactional
    void getAdminControlsMethods() throws Exception {
        // Initialize the database
        adminControlsMethodsRepository.saveAndFlush(adminControlsMethods);

        // Get the adminControlsMethods
        restAdminControlsMethodsMockMvc
            .perform(get(ENTITY_API_URL_ID, adminControlsMethods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminControlsMethods.getId().intValue()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD))
            .andExpect(jsonPath("$.argument").value(DEFAULT_ARGUMENT))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID));
    }

    @Test
    @Transactional
    void getNonExistingAdminControlsMethods() throws Exception {
        // Get the adminControlsMethods
        restAdminControlsMethodsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminControlsMethods() throws Exception {
        // Initialize the database
        adminControlsMethodsRepository.saveAndFlush(adminControlsMethods);

        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();

        // Update the adminControlsMethods
        AdminControlsMethods updatedAdminControlsMethods = adminControlsMethodsRepository.findById(adminControlsMethods.getId()).get();
        // Disconnect from session so that the updates on updatedAdminControlsMethods are not directly saved in db
        em.detach(updatedAdminControlsMethods);
        updatedAdminControlsMethods.method(UPDATED_METHOD).argument(UPDATED_ARGUMENT).order(UPDATED_ORDER).pId(UPDATED_P_ID);
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(updatedAdminControlsMethods);

        restAdminControlsMethodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminControlsMethodsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
        AdminControlsMethods testAdminControlsMethods = adminControlsMethodsList.get(adminControlsMethodsList.size() - 1);
        assertThat(testAdminControlsMethods.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testAdminControlsMethods.getArgument()).isEqualTo(UPDATED_ARGUMENT);
        assertThat(testAdminControlsMethods.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminControlsMethods.getpId()).isEqualTo(UPDATED_P_ID);
    }

    @Test
    @Transactional
    void putNonExistingAdminControlsMethods() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();
        adminControlsMethods.setId(count.incrementAndGet());

        // Create the AdminControlsMethods
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminControlsMethodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminControlsMethodsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminControlsMethods() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();
        adminControlsMethods.setId(count.incrementAndGet());

        // Create the AdminControlsMethods
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMethodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminControlsMethods() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();
        adminControlsMethods.setId(count.incrementAndGet());

        // Create the AdminControlsMethods
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMethodsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminControlsMethodsWithPatch() throws Exception {
        // Initialize the database
        adminControlsMethodsRepository.saveAndFlush(adminControlsMethods);

        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();

        // Update the adminControlsMethods using partial update
        AdminControlsMethods partialUpdatedAdminControlsMethods = new AdminControlsMethods();
        partialUpdatedAdminControlsMethods.setId(adminControlsMethods.getId());

        partialUpdatedAdminControlsMethods.order(UPDATED_ORDER);

        restAdminControlsMethodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminControlsMethods.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminControlsMethods))
            )
            .andExpect(status().isOk());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
        AdminControlsMethods testAdminControlsMethods = adminControlsMethodsList.get(adminControlsMethodsList.size() - 1);
        assertThat(testAdminControlsMethods.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testAdminControlsMethods.getArgument()).isEqualTo(DEFAULT_ARGUMENT);
        assertThat(testAdminControlsMethods.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminControlsMethods.getpId()).isEqualTo(DEFAULT_P_ID);
    }

    @Test
    @Transactional
    void fullUpdateAdminControlsMethodsWithPatch() throws Exception {
        // Initialize the database
        adminControlsMethodsRepository.saveAndFlush(adminControlsMethods);

        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();

        // Update the adminControlsMethods using partial update
        AdminControlsMethods partialUpdatedAdminControlsMethods = new AdminControlsMethods();
        partialUpdatedAdminControlsMethods.setId(adminControlsMethods.getId());

        partialUpdatedAdminControlsMethods.method(UPDATED_METHOD).argument(UPDATED_ARGUMENT).order(UPDATED_ORDER).pId(UPDATED_P_ID);

        restAdminControlsMethodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminControlsMethods.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminControlsMethods))
            )
            .andExpect(status().isOk());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
        AdminControlsMethods testAdminControlsMethods = adminControlsMethodsList.get(adminControlsMethodsList.size() - 1);
        assertThat(testAdminControlsMethods.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testAdminControlsMethods.getArgument()).isEqualTo(UPDATED_ARGUMENT);
        assertThat(testAdminControlsMethods.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminControlsMethods.getpId()).isEqualTo(UPDATED_P_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAdminControlsMethods() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();
        adminControlsMethods.setId(count.incrementAndGet());

        // Create the AdminControlsMethods
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminControlsMethodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminControlsMethodsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminControlsMethods() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();
        adminControlsMethods.setId(count.incrementAndGet());

        // Create the AdminControlsMethods
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMethodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminControlsMethods() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsMethodsRepository.findAll().size();
        adminControlsMethods.setId(count.incrementAndGet());

        // Create the AdminControlsMethods
        AdminControlsMethodsDTO adminControlsMethodsDTO = adminControlsMethodsMapper.toDto(adminControlsMethods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMethodsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsMethodsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminControlsMethods in the database
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminControlsMethods() throws Exception {
        // Initialize the database
        adminControlsMethodsRepository.saveAndFlush(adminControlsMethods);

        int databaseSizeBeforeDelete = adminControlsMethodsRepository.findAll().size();

        // Delete the adminControlsMethods
        restAdminControlsMethodsMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminControlsMethods.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminControlsMethods> adminControlsMethodsList = adminControlsMethodsRepository.findAll();
        assertThat(adminControlsMethodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
