package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminGroupsTabs;
import io.github.jhipster.sample.repository.AdminGroupsTabsRepository;
import io.github.jhipster.sample.service.dto.AdminGroupsTabsDTO;
import io.github.jhipster.sample.service.mapper.AdminGroupsTabsMapper;
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
 * Integration tests for the {@link AdminGroupsTabsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminGroupsTabsResourceIT {

    private static final Boolean DEFAULT_PERMISSION = false;
    private static final Boolean UPDATED_PERMISSION = true;

    private static final String ENTITY_API_URL = "/api/admin-groups-tabs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminGroupsTabsRepository adminGroupsTabsRepository;

    @Autowired
    private AdminGroupsTabsMapper adminGroupsTabsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminGroupsTabsMockMvc;

    private AdminGroupsTabs adminGroupsTabs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminGroupsTabs createEntity(EntityManager em) {
        AdminGroupsTabs adminGroupsTabs = new AdminGroupsTabs().permission(DEFAULT_PERMISSION);
        return adminGroupsTabs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminGroupsTabs createUpdatedEntity(EntityManager em) {
        AdminGroupsTabs adminGroupsTabs = new AdminGroupsTabs().permission(UPDATED_PERMISSION);
        return adminGroupsTabs;
    }

    @BeforeEach
    public void initTest() {
        adminGroupsTabs = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminGroupsTabs() throws Exception {
        int databaseSizeBeforeCreate = adminGroupsTabsRepository.findAll().size();
        // Create the AdminGroupsTabs
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);
        restAdminGroupsTabsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeCreate + 1);
        AdminGroupsTabs testAdminGroupsTabs = adminGroupsTabsList.get(adminGroupsTabsList.size() - 1);
        assertThat(testAdminGroupsTabs.getPermission()).isEqualTo(DEFAULT_PERMISSION);
    }

    @Test
    @Transactional
    void createAdminGroupsTabsWithExistingId() throws Exception {
        // Create the AdminGroupsTabs with an existing ID
        adminGroupsTabs.setId(1L);
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        int databaseSizeBeforeCreate = adminGroupsTabsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminGroupsTabsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPermissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminGroupsTabsRepository.findAll().size();
        // set the field null
        adminGroupsTabs.setPermission(null);

        // Create the AdminGroupsTabs, which fails.
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        restAdminGroupsTabsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminGroupsTabs() throws Exception {
        // Initialize the database
        adminGroupsTabsRepository.saveAndFlush(adminGroupsTabs);

        // Get all the adminGroupsTabsList
        restAdminGroupsTabsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminGroupsTabs.getId().intValue())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.booleanValue())));
    }

    @Test
    @Transactional
    void getAdminGroupsTabs() throws Exception {
        // Initialize the database
        adminGroupsTabsRepository.saveAndFlush(adminGroupsTabs);

        // Get the adminGroupsTabs
        restAdminGroupsTabsMockMvc
            .perform(get(ENTITY_API_URL_ID, adminGroupsTabs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminGroupsTabs.getId().intValue()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAdminGroupsTabs() throws Exception {
        // Get the adminGroupsTabs
        restAdminGroupsTabsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminGroupsTabs() throws Exception {
        // Initialize the database
        adminGroupsTabsRepository.saveAndFlush(adminGroupsTabs);

        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();

        // Update the adminGroupsTabs
        AdminGroupsTabs updatedAdminGroupsTabs = adminGroupsTabsRepository.findById(adminGroupsTabs.getId()).get();
        // Disconnect from session so that the updates on updatedAdminGroupsTabs are not directly saved in db
        em.detach(updatedAdminGroupsTabs);
        updatedAdminGroupsTabs.permission(UPDATED_PERMISSION);
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(updatedAdminGroupsTabs);

        restAdminGroupsTabsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminGroupsTabsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
        AdminGroupsTabs testAdminGroupsTabs = adminGroupsTabsList.get(adminGroupsTabsList.size() - 1);
        assertThat(testAdminGroupsTabs.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void putNonExistingAdminGroupsTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();
        adminGroupsTabs.setId(count.incrementAndGet());

        // Create the AdminGroupsTabs
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminGroupsTabsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminGroupsTabsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminGroupsTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();
        adminGroupsTabs.setId(count.incrementAndGet());

        // Create the AdminGroupsTabs
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsTabsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminGroupsTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();
        adminGroupsTabs.setId(count.incrementAndGet());

        // Create the AdminGroupsTabs
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsTabsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminGroupsTabsWithPatch() throws Exception {
        // Initialize the database
        adminGroupsTabsRepository.saveAndFlush(adminGroupsTabs);

        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();

        // Update the adminGroupsTabs using partial update
        AdminGroupsTabs partialUpdatedAdminGroupsTabs = new AdminGroupsTabs();
        partialUpdatedAdminGroupsTabs.setId(adminGroupsTabs.getId());

        partialUpdatedAdminGroupsTabs.permission(UPDATED_PERMISSION);

        restAdminGroupsTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminGroupsTabs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminGroupsTabs))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
        AdminGroupsTabs testAdminGroupsTabs = adminGroupsTabsList.get(adminGroupsTabsList.size() - 1);
        assertThat(testAdminGroupsTabs.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void fullUpdateAdminGroupsTabsWithPatch() throws Exception {
        // Initialize the database
        adminGroupsTabsRepository.saveAndFlush(adminGroupsTabs);

        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();

        // Update the adminGroupsTabs using partial update
        AdminGroupsTabs partialUpdatedAdminGroupsTabs = new AdminGroupsTabs();
        partialUpdatedAdminGroupsTabs.setId(adminGroupsTabs.getId());

        partialUpdatedAdminGroupsTabs.permission(UPDATED_PERMISSION);

        restAdminGroupsTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminGroupsTabs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminGroupsTabs))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
        AdminGroupsTabs testAdminGroupsTabs = adminGroupsTabsList.get(adminGroupsTabsList.size() - 1);
        assertThat(testAdminGroupsTabs.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void patchNonExistingAdminGroupsTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();
        adminGroupsTabs.setId(count.incrementAndGet());

        // Create the AdminGroupsTabs
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminGroupsTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminGroupsTabsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminGroupsTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();
        adminGroupsTabs.setId(count.incrementAndGet());

        // Create the AdminGroupsTabs
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminGroupsTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsTabsRepository.findAll().size();
        adminGroupsTabs.setId(count.incrementAndGet());

        // Create the AdminGroupsTabs
        AdminGroupsTabsDTO adminGroupsTabsDTO = adminGroupsTabsMapper.toDto(adminGroupsTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsTabsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsTabsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminGroupsTabs in the database
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminGroupsTabs() throws Exception {
        // Initialize the database
        adminGroupsTabsRepository.saveAndFlush(adminGroupsTabs);

        int databaseSizeBeforeDelete = adminGroupsTabsRepository.findAll().size();

        // Delete the adminGroupsTabs
        restAdminGroupsTabsMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminGroupsTabs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminGroupsTabs> adminGroupsTabsList = adminGroupsTabsRepository.findAll();
        assertThat(adminGroupsTabsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
