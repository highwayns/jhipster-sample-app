package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminPages;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.AdminPagesRepository;
import io.github.jhipster.sample.service.dto.AdminPagesDTO;
import io.github.jhipster.sample.service.mapper.AdminPagesMapper;
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
 * Integration tests for the {@link AdminPagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminPagesResourceIT {

    private static final Integer DEFAULT_F_ID = 1;
    private static final Integer UPDATED_F_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Boolean DEFAULT_PAGE_MAP_REORDERS = false;
    private static final Boolean UPDATED_PAGE_MAP_REORDERS = true;

    private static final YesNo DEFAULT_ONE_RECORD = YesNo.Y;
    private static final YesNo UPDATED_ONE_RECORD = YesNo.N;

    private static final String ENTITY_API_URL = "/api/admin-pages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminPagesRepository adminPagesRepository;

    @Autowired
    private AdminPagesMapper adminPagesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminPagesMockMvc;

    private AdminPages adminPages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminPages createEntity(EntityManager em) {
        AdminPages adminPages = new AdminPages()
            .fId(DEFAULT_F_ID)
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL)
            .icon(DEFAULT_ICON)
            .order(DEFAULT_ORDER)
            .pageMapReorders(DEFAULT_PAGE_MAP_REORDERS)
            .oneRecord(DEFAULT_ONE_RECORD);
        return adminPages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminPages createUpdatedEntity(EntityManager em) {
        AdminPages adminPages = new AdminPages()
            .fId(UPDATED_F_ID)
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .icon(UPDATED_ICON)
            .order(UPDATED_ORDER)
            .pageMapReorders(UPDATED_PAGE_MAP_REORDERS)
            .oneRecord(UPDATED_ONE_RECORD);
        return adminPages;
    }

    @BeforeEach
    public void initTest() {
        adminPages = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminPages() throws Exception {
        int databaseSizeBeforeCreate = adminPagesRepository.findAll().size();
        // Create the AdminPages
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);
        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeCreate + 1);
        AdminPages testAdminPages = adminPagesList.get(adminPagesList.size() - 1);
        assertThat(testAdminPages.getfId()).isEqualTo(DEFAULT_F_ID);
        assertThat(testAdminPages.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdminPages.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAdminPages.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testAdminPages.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAdminPages.getPageMapReorders()).isEqualTo(DEFAULT_PAGE_MAP_REORDERS);
        assertThat(testAdminPages.getOneRecord()).isEqualTo(DEFAULT_ONE_RECORD);
    }

    @Test
    @Transactional
    void createAdminPagesWithExistingId() throws Exception {
        // Create the AdminPages with an existing ID
        adminPages.setId(1L);
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        int databaseSizeBeforeCreate = adminPagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkfIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminPagesRepository.findAll().size();
        // set the field null
        adminPages.setfId(null);

        // Create the AdminPages, which fails.
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminPagesRepository.findAll().size();
        // set the field null
        adminPages.setName(null);

        // Create the AdminPages, which fails.
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminPagesRepository.findAll().size();
        // set the field null
        adminPages.setUrl(null);

        // Create the AdminPages, which fails.
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminPagesRepository.findAll().size();
        // set the field null
        adminPages.setIcon(null);

        // Create the AdminPages, which fails.
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminPagesRepository.findAll().size();
        // set the field null
        adminPages.setOrder(null);

        // Create the AdminPages, which fails.
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPageMapReordersIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminPagesRepository.findAll().size();
        // set the field null
        adminPages.setPageMapReorders(null);

        // Create the AdminPages, which fails.
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOneRecordIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminPagesRepository.findAll().size();
        // set the field null
        adminPages.setOneRecord(null);

        // Create the AdminPages, which fails.
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        restAdminPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isBadRequest());

        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminPages() throws Exception {
        // Initialize the database
        adminPagesRepository.saveAndFlush(adminPages);

        // Get all the adminPagesList
        restAdminPagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminPages.getId().intValue())))
            .andExpect(jsonPath("$.[*].fId").value(hasItem(DEFAULT_F_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].pageMapReorders").value(hasItem(DEFAULT_PAGE_MAP_REORDERS.booleanValue())))
            .andExpect(jsonPath("$.[*].oneRecord").value(hasItem(DEFAULT_ONE_RECORD.toString())));
    }

    @Test
    @Transactional
    void getAdminPages() throws Exception {
        // Initialize the database
        adminPagesRepository.saveAndFlush(adminPages);

        // Get the adminPages
        restAdminPagesMockMvc
            .perform(get(ENTITY_API_URL_ID, adminPages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminPages.getId().intValue()))
            .andExpect(jsonPath("$.fId").value(DEFAULT_F_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.pageMapReorders").value(DEFAULT_PAGE_MAP_REORDERS.booleanValue()))
            .andExpect(jsonPath("$.oneRecord").value(DEFAULT_ONE_RECORD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdminPages() throws Exception {
        // Get the adminPages
        restAdminPagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminPages() throws Exception {
        // Initialize the database
        adminPagesRepository.saveAndFlush(adminPages);

        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();

        // Update the adminPages
        AdminPages updatedAdminPages = adminPagesRepository.findById(adminPages.getId()).get();
        // Disconnect from session so that the updates on updatedAdminPages are not directly saved in db
        em.detach(updatedAdminPages);
        updatedAdminPages
            .fId(UPDATED_F_ID)
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .icon(UPDATED_ICON)
            .order(UPDATED_ORDER)
            .pageMapReorders(UPDATED_PAGE_MAP_REORDERS)
            .oneRecord(UPDATED_ONE_RECORD);
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(updatedAdminPages);

        restAdminPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminPagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminPagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
        AdminPages testAdminPages = adminPagesList.get(adminPagesList.size() - 1);
        assertThat(testAdminPages.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testAdminPages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminPages.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAdminPages.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testAdminPages.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminPages.getPageMapReorders()).isEqualTo(UPDATED_PAGE_MAP_REORDERS);
        assertThat(testAdminPages.getOneRecord()).isEqualTo(UPDATED_ONE_RECORD);
    }

    @Test
    @Transactional
    void putNonExistingAdminPages() throws Exception {
        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();
        adminPages.setId(count.incrementAndGet());

        // Create the AdminPages
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminPagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminPages() throws Exception {
        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();
        adminPages.setId(count.incrementAndGet());

        // Create the AdminPages
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminPages() throws Exception {
        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();
        adminPages.setId(count.incrementAndGet());

        // Create the AdminPages
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminPagesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminPagesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminPagesWithPatch() throws Exception {
        // Initialize the database
        adminPagesRepository.saveAndFlush(adminPages);

        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();

        // Update the adminPages using partial update
        AdminPages partialUpdatedAdminPages = new AdminPages();
        partialUpdatedAdminPages.setId(adminPages.getId());

        partialUpdatedAdminPages.fId(UPDATED_F_ID).name(UPDATED_NAME).icon(UPDATED_ICON).order(UPDATED_ORDER);

        restAdminPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminPages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminPages))
            )
            .andExpect(status().isOk());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
        AdminPages testAdminPages = adminPagesList.get(adminPagesList.size() - 1);
        assertThat(testAdminPages.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testAdminPages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminPages.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAdminPages.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testAdminPages.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminPages.getPageMapReorders()).isEqualTo(DEFAULT_PAGE_MAP_REORDERS);
        assertThat(testAdminPages.getOneRecord()).isEqualTo(DEFAULT_ONE_RECORD);
    }

    @Test
    @Transactional
    void fullUpdateAdminPagesWithPatch() throws Exception {
        // Initialize the database
        adminPagesRepository.saveAndFlush(adminPages);

        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();

        // Update the adminPages using partial update
        AdminPages partialUpdatedAdminPages = new AdminPages();
        partialUpdatedAdminPages.setId(adminPages.getId());

        partialUpdatedAdminPages
            .fId(UPDATED_F_ID)
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .icon(UPDATED_ICON)
            .order(UPDATED_ORDER)
            .pageMapReorders(UPDATED_PAGE_MAP_REORDERS)
            .oneRecord(UPDATED_ONE_RECORD);

        restAdminPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminPages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminPages))
            )
            .andExpect(status().isOk());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
        AdminPages testAdminPages = adminPagesList.get(adminPagesList.size() - 1);
        assertThat(testAdminPages.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testAdminPages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminPages.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAdminPages.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testAdminPages.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminPages.getPageMapReorders()).isEqualTo(UPDATED_PAGE_MAP_REORDERS);
        assertThat(testAdminPages.getOneRecord()).isEqualTo(UPDATED_ONE_RECORD);
    }

    @Test
    @Transactional
    void patchNonExistingAdminPages() throws Exception {
        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();
        adminPages.setId(count.incrementAndGet());

        // Create the AdminPages
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminPagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminPages() throws Exception {
        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();
        adminPages.setId(count.incrementAndGet());

        // Create the AdminPages
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminPagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminPages() throws Exception {
        int databaseSizeBeforeUpdate = adminPagesRepository.findAll().size();
        adminPages.setId(count.incrementAndGet());

        // Create the AdminPages
        AdminPagesDTO adminPagesDTO = adminPagesMapper.toDto(adminPages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminPagesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adminPagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminPages in the database
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminPages() throws Exception {
        // Initialize the database
        adminPagesRepository.saveAndFlush(adminPages);

        int databaseSizeBeforeDelete = adminPagesRepository.findAll().size();

        // Delete the adminPages
        restAdminPagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminPages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminPages> adminPagesList = adminPagesRepository.findAll();
        assertThat(adminPagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
