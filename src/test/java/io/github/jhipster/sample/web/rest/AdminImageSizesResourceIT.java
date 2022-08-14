package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminImageSizes;
import io.github.jhipster.sample.repository.AdminImageSizesRepository;
import io.github.jhipster.sample.service.dto.AdminImageSizesDTO;
import io.github.jhipster.sample.service.mapper.AdminImageSizesMapper;
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
 * Integration tests for the {@link AdminImageSizesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminImageSizesResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/admin-image-sizes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminImageSizesRepository adminImageSizesRepository;

    @Autowired
    private AdminImageSizesMapper adminImageSizesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminImageSizesMockMvc;

    private AdminImageSizes adminImageSizes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminImageSizes createEntity(EntityManager em) {
        AdminImageSizes adminImageSizes = new AdminImageSizes().fieldName(DEFAULT_FIELD_NAME).value(DEFAULT_VALUE);
        return adminImageSizes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminImageSizes createUpdatedEntity(EntityManager em) {
        AdminImageSizes adminImageSizes = new AdminImageSizes().fieldName(UPDATED_FIELD_NAME).value(UPDATED_VALUE);
        return adminImageSizes;
    }

    @BeforeEach
    public void initTest() {
        adminImageSizes = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminImageSizes() throws Exception {
        int databaseSizeBeforeCreate = adminImageSizesRepository.findAll().size();
        // Create the AdminImageSizes
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);
        restAdminImageSizesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeCreate + 1);
        AdminImageSizes testAdminImageSizes = adminImageSizesList.get(adminImageSizesList.size() - 1);
        assertThat(testAdminImageSizes.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testAdminImageSizes.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createAdminImageSizesWithExistingId() throws Exception {
        // Create the AdminImageSizes with an existing ID
        adminImageSizes.setId(1L);
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        int databaseSizeBeforeCreate = adminImageSizesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminImageSizesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminImageSizesRepository.findAll().size();
        // set the field null
        adminImageSizes.setFieldName(null);

        // Create the AdminImageSizes, which fails.
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        restAdminImageSizesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminImageSizesRepository.findAll().size();
        // set the field null
        adminImageSizes.setValue(null);

        // Create the AdminImageSizes, which fails.
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        restAdminImageSizesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminImageSizes() throws Exception {
        // Initialize the database
        adminImageSizesRepository.saveAndFlush(adminImageSizes);

        // Get all the adminImageSizesList
        restAdminImageSizesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminImageSizes.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getAdminImageSizes() throws Exception {
        // Initialize the database
        adminImageSizesRepository.saveAndFlush(adminImageSizes);

        // Get the adminImageSizes
        restAdminImageSizesMockMvc
            .perform(get(ENTITY_API_URL_ID, adminImageSizes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminImageSizes.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingAdminImageSizes() throws Exception {
        // Get the adminImageSizes
        restAdminImageSizesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminImageSizes() throws Exception {
        // Initialize the database
        adminImageSizesRepository.saveAndFlush(adminImageSizes);

        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();

        // Update the adminImageSizes
        AdminImageSizes updatedAdminImageSizes = adminImageSizesRepository.findById(adminImageSizes.getId()).get();
        // Disconnect from session so that the updates on updatedAdminImageSizes are not directly saved in db
        em.detach(updatedAdminImageSizes);
        updatedAdminImageSizes.fieldName(UPDATED_FIELD_NAME).value(UPDATED_VALUE);
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(updatedAdminImageSizes);

        restAdminImageSizesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminImageSizesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
        AdminImageSizes testAdminImageSizes = adminImageSizesList.get(adminImageSizesList.size() - 1);
        assertThat(testAdminImageSizes.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testAdminImageSizes.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingAdminImageSizes() throws Exception {
        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();
        adminImageSizes.setId(count.incrementAndGet());

        // Create the AdminImageSizes
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminImageSizesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminImageSizesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminImageSizes() throws Exception {
        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();
        adminImageSizes.setId(count.incrementAndGet());

        // Create the AdminImageSizes
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminImageSizesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminImageSizes() throws Exception {
        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();
        adminImageSizes.setId(count.incrementAndGet());

        // Create the AdminImageSizes
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminImageSizesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminImageSizesWithPatch() throws Exception {
        // Initialize the database
        adminImageSizesRepository.saveAndFlush(adminImageSizes);

        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();

        // Update the adminImageSizes using partial update
        AdminImageSizes partialUpdatedAdminImageSizes = new AdminImageSizes();
        partialUpdatedAdminImageSizes.setId(adminImageSizes.getId());

        restAdminImageSizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminImageSizes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminImageSizes))
            )
            .andExpect(status().isOk());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
        AdminImageSizes testAdminImageSizes = adminImageSizesList.get(adminImageSizesList.size() - 1);
        assertThat(testAdminImageSizes.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testAdminImageSizes.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateAdminImageSizesWithPatch() throws Exception {
        // Initialize the database
        adminImageSizesRepository.saveAndFlush(adminImageSizes);

        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();

        // Update the adminImageSizes using partial update
        AdminImageSizes partialUpdatedAdminImageSizes = new AdminImageSizes();
        partialUpdatedAdminImageSizes.setId(adminImageSizes.getId());

        partialUpdatedAdminImageSizes.fieldName(UPDATED_FIELD_NAME).value(UPDATED_VALUE);

        restAdminImageSizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminImageSizes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminImageSizes))
            )
            .andExpect(status().isOk());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
        AdminImageSizes testAdminImageSizes = adminImageSizesList.get(adminImageSizesList.size() - 1);
        assertThat(testAdminImageSizes.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testAdminImageSizes.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingAdminImageSizes() throws Exception {
        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();
        adminImageSizes.setId(count.incrementAndGet());

        // Create the AdminImageSizes
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminImageSizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminImageSizesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminImageSizes() throws Exception {
        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();
        adminImageSizes.setId(count.incrementAndGet());

        // Create the AdminImageSizes
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminImageSizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminImageSizes() throws Exception {
        int databaseSizeBeforeUpdate = adminImageSizesRepository.findAll().size();
        adminImageSizes.setId(count.incrementAndGet());

        // Create the AdminImageSizes
        AdminImageSizesDTO adminImageSizesDTO = adminImageSizesMapper.toDto(adminImageSizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminImageSizesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminImageSizesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminImageSizes in the database
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminImageSizes() throws Exception {
        // Initialize the database
        adminImageSizesRepository.saveAndFlush(adminImageSizes);

        int databaseSizeBeforeDelete = adminImageSizesRepository.findAll().size();

        // Delete the adminImageSizes
        restAdminImageSizesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminImageSizes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminImageSizes> adminImageSizesList = adminImageSizesRepository.findAll();
        assertThat(adminImageSizesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
