package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminGroups;
import io.github.jhipster.sample.repository.AdminGroupsRepository;
import io.github.jhipster.sample.service.dto.AdminGroupsDTO;
import io.github.jhipster.sample.service.mapper.AdminGroupsMapper;
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
 * Integration tests for the {@link AdminGroupsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminGroupsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/admin-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminGroupsRepository adminGroupsRepository;

    @Autowired
    private AdminGroupsMapper adminGroupsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminGroupsMockMvc;

    private AdminGroups adminGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminGroups createEntity(EntityManager em) {
        AdminGroups adminGroups = new AdminGroups().name(DEFAULT_NAME).order(DEFAULT_ORDER);
        return adminGroups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminGroups createUpdatedEntity(EntityManager em) {
        AdminGroups adminGroups = new AdminGroups().name(UPDATED_NAME).order(UPDATED_ORDER);
        return adminGroups;
    }

    @BeforeEach
    public void initTest() {
        adminGroups = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminGroups() throws Exception {
        int databaseSizeBeforeCreate = adminGroupsRepository.findAll().size();
        // Create the AdminGroups
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);
        restAdminGroupsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        AdminGroups testAdminGroups = adminGroupsList.get(adminGroupsList.size() - 1);
        assertThat(testAdminGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdminGroups.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    void createAdminGroupsWithExistingId() throws Exception {
        // Create the AdminGroups with an existing ID
        adminGroups.setId(1L);
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        int databaseSizeBeforeCreate = adminGroupsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminGroupsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminGroupsRepository.findAll().size();
        // set the field null
        adminGroups.setName(null);

        // Create the AdminGroups, which fails.
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        restAdminGroupsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminGroupsRepository.findAll().size();
        // set the field null
        adminGroups.setOrder(null);

        // Create the AdminGroups, which fails.
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        restAdminGroupsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminGroups() throws Exception {
        // Initialize the database
        adminGroupsRepository.saveAndFlush(adminGroups);

        // Get all the adminGroupsList
        restAdminGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @Test
    @Transactional
    void getAdminGroups() throws Exception {
        // Initialize the database
        adminGroupsRepository.saveAndFlush(adminGroups);

        // Get the adminGroups
        restAdminGroupsMockMvc
            .perform(get(ENTITY_API_URL_ID, adminGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminGroups.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    void getNonExistingAdminGroups() throws Exception {
        // Get the adminGroups
        restAdminGroupsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminGroups() throws Exception {
        // Initialize the database
        adminGroupsRepository.saveAndFlush(adminGroups);

        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();

        // Update the adminGroups
        AdminGroups updatedAdminGroups = adminGroupsRepository.findById(adminGroups.getId()).get();
        // Disconnect from session so that the updates on updatedAdminGroups are not directly saved in db
        em.detach(updatedAdminGroups);
        updatedAdminGroups.name(UPDATED_NAME).order(UPDATED_ORDER);
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(updatedAdminGroups);

        restAdminGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminGroupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
        AdminGroups testAdminGroups = adminGroupsList.get(adminGroupsList.size() - 1);
        assertThat(testAdminGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminGroups.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    void putNonExistingAdminGroups() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();
        adminGroups.setId(count.incrementAndGet());

        // Create the AdminGroups
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminGroupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminGroups() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();
        adminGroups.setId(count.incrementAndGet());

        // Create the AdminGroups
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminGroups() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();
        adminGroups.setId(count.incrementAndGet());

        // Create the AdminGroups
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminGroupsWithPatch() throws Exception {
        // Initialize the database
        adminGroupsRepository.saveAndFlush(adminGroups);

        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();

        // Update the adminGroups using partial update
        AdminGroups partialUpdatedAdminGroups = new AdminGroups();
        partialUpdatedAdminGroups.setId(adminGroups.getId());

        partialUpdatedAdminGroups.name(UPDATED_NAME);

        restAdminGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminGroups))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
        AdminGroups testAdminGroups = adminGroupsList.get(adminGroupsList.size() - 1);
        assertThat(testAdminGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminGroups.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    void fullUpdateAdminGroupsWithPatch() throws Exception {
        // Initialize the database
        adminGroupsRepository.saveAndFlush(adminGroups);

        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();

        // Update the adminGroups using partial update
        AdminGroups partialUpdatedAdminGroups = new AdminGroups();
        partialUpdatedAdminGroups.setId(adminGroups.getId());

        partialUpdatedAdminGroups.name(UPDATED_NAME).order(UPDATED_ORDER);

        restAdminGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminGroups))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
        AdminGroups testAdminGroups = adminGroupsList.get(adminGroupsList.size() - 1);
        assertThat(testAdminGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminGroups.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    void patchNonExistingAdminGroups() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();
        adminGroups.setId(count.incrementAndGet());

        // Create the AdminGroups
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminGroupsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminGroups() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();
        adminGroups.setId(count.incrementAndGet());

        // Create the AdminGroups
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminGroups() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsRepository.findAll().size();
        adminGroups.setId(count.incrementAndGet());

        // Create the AdminGroups
        AdminGroupsDTO adminGroupsDTO = adminGroupsMapper.toDto(adminGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adminGroupsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminGroups in the database
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminGroups() throws Exception {
        // Initialize the database
        adminGroupsRepository.saveAndFlush(adminGroups);

        int databaseSizeBeforeDelete = adminGroupsRepository.findAll().size();

        // Delete the adminGroups
        restAdminGroupsMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminGroups.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminGroups> adminGroupsList = adminGroupsRepository.findAll();
        assertThat(adminGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
