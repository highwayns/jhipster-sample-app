package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminGroupsPages;
import io.github.jhipster.sample.repository.AdminGroupsPagesRepository;
import io.github.jhipster.sample.service.dto.AdminGroupsPagesDTO;
import io.github.jhipster.sample.service.mapper.AdminGroupsPagesMapper;
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
 * Integration tests for the {@link AdminGroupsPagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminGroupsPagesResourceIT {

    private static final Boolean DEFAULT_PERMISSION = false;
    private static final Boolean UPDATED_PERMISSION = true;

    private static final String ENTITY_API_URL = "/api/admin-groups-pages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminGroupsPagesRepository adminGroupsPagesRepository;

    @Autowired
    private AdminGroupsPagesMapper adminGroupsPagesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminGroupsPagesMockMvc;

    private AdminGroupsPages adminGroupsPages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminGroupsPages createEntity(EntityManager em) {
        AdminGroupsPages adminGroupsPages = new AdminGroupsPages().permission(DEFAULT_PERMISSION);
        return adminGroupsPages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminGroupsPages createUpdatedEntity(EntityManager em) {
        AdminGroupsPages adminGroupsPages = new AdminGroupsPages().permission(UPDATED_PERMISSION);
        return adminGroupsPages;
    }

    @BeforeEach
    public void initTest() {
        adminGroupsPages = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminGroupsPages() throws Exception {
        int databaseSizeBeforeCreate = adminGroupsPagesRepository.findAll().size();
        // Create the AdminGroupsPages
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);
        restAdminGroupsPagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeCreate + 1);
        AdminGroupsPages testAdminGroupsPages = adminGroupsPagesList.get(adminGroupsPagesList.size() - 1);
        assertThat(testAdminGroupsPages.getPermission()).isEqualTo(DEFAULT_PERMISSION);
    }

    @Test
    @Transactional
    void createAdminGroupsPagesWithExistingId() throws Exception {
        // Create the AdminGroupsPages with an existing ID
        adminGroupsPages.setId(1L);
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        int databaseSizeBeforeCreate = adminGroupsPagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminGroupsPagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPermissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminGroupsPagesRepository.findAll().size();
        // set the field null
        adminGroupsPages.setPermission(null);

        // Create the AdminGroupsPages, which fails.
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        restAdminGroupsPagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminGroupsPages() throws Exception {
        // Initialize the database
        adminGroupsPagesRepository.saveAndFlush(adminGroupsPages);

        // Get all the adminGroupsPagesList
        restAdminGroupsPagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminGroupsPages.getId().intValue())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.booleanValue())));
    }

    @Test
    @Transactional
    void getAdminGroupsPages() throws Exception {
        // Initialize the database
        adminGroupsPagesRepository.saveAndFlush(adminGroupsPages);

        // Get the adminGroupsPages
        restAdminGroupsPagesMockMvc
            .perform(get(ENTITY_API_URL_ID, adminGroupsPages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminGroupsPages.getId().intValue()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAdminGroupsPages() throws Exception {
        // Get the adminGroupsPages
        restAdminGroupsPagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminGroupsPages() throws Exception {
        // Initialize the database
        adminGroupsPagesRepository.saveAndFlush(adminGroupsPages);

        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();

        // Update the adminGroupsPages
        AdminGroupsPages updatedAdminGroupsPages = adminGroupsPagesRepository.findById(adminGroupsPages.getId()).get();
        // Disconnect from session so that the updates on updatedAdminGroupsPages are not directly saved in db
        em.detach(updatedAdminGroupsPages);
        updatedAdminGroupsPages.permission(UPDATED_PERMISSION);
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(updatedAdminGroupsPages);

        restAdminGroupsPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminGroupsPagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
        AdminGroupsPages testAdminGroupsPages = adminGroupsPagesList.get(adminGroupsPagesList.size() - 1);
        assertThat(testAdminGroupsPages.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void putNonExistingAdminGroupsPages() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();
        adminGroupsPages.setId(count.incrementAndGet());

        // Create the AdminGroupsPages
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminGroupsPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminGroupsPagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminGroupsPages() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();
        adminGroupsPages.setId(count.incrementAndGet());

        // Create the AdminGroupsPages
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminGroupsPages() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();
        adminGroupsPages.setId(count.incrementAndGet());

        // Create the AdminGroupsPages
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsPagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminGroupsPagesWithPatch() throws Exception {
        // Initialize the database
        adminGroupsPagesRepository.saveAndFlush(adminGroupsPages);

        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();

        // Update the adminGroupsPages using partial update
        AdminGroupsPages partialUpdatedAdminGroupsPages = new AdminGroupsPages();
        partialUpdatedAdminGroupsPages.setId(adminGroupsPages.getId());

        partialUpdatedAdminGroupsPages.permission(UPDATED_PERMISSION);

        restAdminGroupsPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminGroupsPages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminGroupsPages))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
        AdminGroupsPages testAdminGroupsPages = adminGroupsPagesList.get(adminGroupsPagesList.size() - 1);
        assertThat(testAdminGroupsPages.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void fullUpdateAdminGroupsPagesWithPatch() throws Exception {
        // Initialize the database
        adminGroupsPagesRepository.saveAndFlush(adminGroupsPages);

        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();

        // Update the adminGroupsPages using partial update
        AdminGroupsPages partialUpdatedAdminGroupsPages = new AdminGroupsPages();
        partialUpdatedAdminGroupsPages.setId(adminGroupsPages.getId());

        partialUpdatedAdminGroupsPages.permission(UPDATED_PERMISSION);

        restAdminGroupsPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminGroupsPages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminGroupsPages))
            )
            .andExpect(status().isOk());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
        AdminGroupsPages testAdminGroupsPages = adminGroupsPagesList.get(adminGroupsPagesList.size() - 1);
        assertThat(testAdminGroupsPages.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void patchNonExistingAdminGroupsPages() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();
        adminGroupsPages.setId(count.incrementAndGet());

        // Create the AdminGroupsPages
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminGroupsPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminGroupsPagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminGroupsPages() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();
        adminGroupsPages.setId(count.incrementAndGet());

        // Create the AdminGroupsPages
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminGroupsPages() throws Exception {
        int databaseSizeBeforeUpdate = adminGroupsPagesRepository.findAll().size();
        adminGroupsPages.setId(count.incrementAndGet());

        // Create the AdminGroupsPages
        AdminGroupsPagesDTO adminGroupsPagesDTO = adminGroupsPagesMapper.toDto(adminGroupsPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminGroupsPagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminGroupsPagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminGroupsPages in the database
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminGroupsPages() throws Exception {
        // Initialize the database
        adminGroupsPagesRepository.saveAndFlush(adminGroupsPages);

        int databaseSizeBeforeDelete = adminGroupsPagesRepository.findAll().size();

        // Delete the adminGroupsPages
        restAdminGroupsPagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminGroupsPages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminGroupsPages> adminGroupsPagesList = adminGroupsPagesRepository.findAll();
        assertThat(adminGroupsPagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
