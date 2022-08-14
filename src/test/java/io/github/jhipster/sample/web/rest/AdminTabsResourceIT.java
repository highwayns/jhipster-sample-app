package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminTabs;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.AdminTabsRepository;
import io.github.jhipster.sample.service.dto.AdminTabsDTO;
import io.github.jhipster.sample.service.mapper.AdminTabsMapper;
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
 * Integration tests for the {@link AdminTabsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminTabsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final YesNo DEFAULT_HIDDEN = YesNo.Y;
    private static final YesNo UPDATED_HIDDEN = YesNo.N;

    private static final YesNo DEFAULT_IS_CTRL_PANEL = YesNo.Y;
    private static final YesNo UPDATED_IS_CTRL_PANEL = YesNo.N;

    private static final Integer DEFAULT_FOR_GROUP = 1;
    private static final Integer UPDATED_FOR_GROUP = 2;

    private static final YesNo DEFAULT_ONE_RECORD = YesNo.Y;
    private static final YesNo UPDATED_ONE_RECORD = YesNo.N;

    private static final String ENTITY_API_URL = "/api/admin-tabs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminTabsRepository adminTabsRepository;

    @Autowired
    private AdminTabsMapper adminTabsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminTabsMockMvc;

    private AdminTabs adminTabs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminTabs createEntity(EntityManager em) {
        AdminTabs adminTabs = new AdminTabs()
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER)
            .icon(DEFAULT_ICON)
            .url(DEFAULT_URL)
            .hidden(DEFAULT_HIDDEN)
            .isCtrlPanel(DEFAULT_IS_CTRL_PANEL)
            .forGroup(DEFAULT_FOR_GROUP)
            .oneRecord(DEFAULT_ONE_RECORD);
        return adminTabs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminTabs createUpdatedEntity(EntityManager em) {
        AdminTabs adminTabs = new AdminTabs()
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .icon(UPDATED_ICON)
            .url(UPDATED_URL)
            .hidden(UPDATED_HIDDEN)
            .isCtrlPanel(UPDATED_IS_CTRL_PANEL)
            .forGroup(UPDATED_FOR_GROUP)
            .oneRecord(UPDATED_ONE_RECORD);
        return adminTabs;
    }

    @BeforeEach
    public void initTest() {
        adminTabs = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminTabs() throws Exception {
        int databaseSizeBeforeCreate = adminTabsRepository.findAll().size();
        // Create the AdminTabs
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);
        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeCreate + 1);
        AdminTabs testAdminTabs = adminTabsList.get(adminTabsList.size() - 1);
        assertThat(testAdminTabs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdminTabs.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAdminTabs.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testAdminTabs.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAdminTabs.getHidden()).isEqualTo(DEFAULT_HIDDEN);
        assertThat(testAdminTabs.getIsCtrlPanel()).isEqualTo(DEFAULT_IS_CTRL_PANEL);
        assertThat(testAdminTabs.getForGroup()).isEqualTo(DEFAULT_FOR_GROUP);
        assertThat(testAdminTabs.getOneRecord()).isEqualTo(DEFAULT_ONE_RECORD);
    }

    @Test
    @Transactional
    void createAdminTabsWithExistingId() throws Exception {
        // Create the AdminTabs with an existing ID
        adminTabs.setId(1L);
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        int databaseSizeBeforeCreate = adminTabsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setName(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setOrder(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setIcon(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setUrl(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHiddenIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setHidden(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsCtrlPanelIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setIsCtrlPanel(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkForGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setForGroup(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOneRecordIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminTabsRepository.findAll().size();
        // set the field null
        adminTabs.setOneRecord(null);

        // Create the AdminTabs, which fails.
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        restAdminTabsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isBadRequest());

        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminTabs() throws Exception {
        // Initialize the database
        adminTabsRepository.saveAndFlush(adminTabs);

        // Get all the adminTabsList
        restAdminTabsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminTabs.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].hidden").value(hasItem(DEFAULT_HIDDEN.toString())))
            .andExpect(jsonPath("$.[*].isCtrlPanel").value(hasItem(DEFAULT_IS_CTRL_PANEL.toString())))
            .andExpect(jsonPath("$.[*].forGroup").value(hasItem(DEFAULT_FOR_GROUP)))
            .andExpect(jsonPath("$.[*].oneRecord").value(hasItem(DEFAULT_ONE_RECORD.toString())));
    }

    @Test
    @Transactional
    void getAdminTabs() throws Exception {
        // Initialize the database
        adminTabsRepository.saveAndFlush(adminTabs);

        // Get the adminTabs
        restAdminTabsMockMvc
            .perform(get(ENTITY_API_URL_ID, adminTabs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminTabs.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.hidden").value(DEFAULT_HIDDEN.toString()))
            .andExpect(jsonPath("$.isCtrlPanel").value(DEFAULT_IS_CTRL_PANEL.toString()))
            .andExpect(jsonPath("$.forGroup").value(DEFAULT_FOR_GROUP))
            .andExpect(jsonPath("$.oneRecord").value(DEFAULT_ONE_RECORD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdminTabs() throws Exception {
        // Get the adminTabs
        restAdminTabsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminTabs() throws Exception {
        // Initialize the database
        adminTabsRepository.saveAndFlush(adminTabs);

        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();

        // Update the adminTabs
        AdminTabs updatedAdminTabs = adminTabsRepository.findById(adminTabs.getId()).get();
        // Disconnect from session so that the updates on updatedAdminTabs are not directly saved in db
        em.detach(updatedAdminTabs);
        updatedAdminTabs
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .icon(UPDATED_ICON)
            .url(UPDATED_URL)
            .hidden(UPDATED_HIDDEN)
            .isCtrlPanel(UPDATED_IS_CTRL_PANEL)
            .forGroup(UPDATED_FOR_GROUP)
            .oneRecord(UPDATED_ONE_RECORD);
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(updatedAdminTabs);

        restAdminTabsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminTabsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminTabsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
        AdminTabs testAdminTabs = adminTabsList.get(adminTabsList.size() - 1);
        assertThat(testAdminTabs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminTabs.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminTabs.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testAdminTabs.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAdminTabs.getHidden()).isEqualTo(UPDATED_HIDDEN);
        assertThat(testAdminTabs.getIsCtrlPanel()).isEqualTo(UPDATED_IS_CTRL_PANEL);
        assertThat(testAdminTabs.getForGroup()).isEqualTo(UPDATED_FOR_GROUP);
        assertThat(testAdminTabs.getOneRecord()).isEqualTo(UPDATED_ONE_RECORD);
    }

    @Test
    @Transactional
    void putNonExistingAdminTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();
        adminTabs.setId(count.incrementAndGet());

        // Create the AdminTabs
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminTabsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminTabsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();
        adminTabs.setId(count.incrementAndGet());

        // Create the AdminTabs
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminTabsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();
        adminTabs.setId(count.incrementAndGet());

        // Create the AdminTabs
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminTabsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminTabsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminTabsWithPatch() throws Exception {
        // Initialize the database
        adminTabsRepository.saveAndFlush(adminTabs);

        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();

        // Update the adminTabs using partial update
        AdminTabs partialUpdatedAdminTabs = new AdminTabs();
        partialUpdatedAdminTabs.setId(adminTabs.getId());

        partialUpdatedAdminTabs.url(UPDATED_URL).oneRecord(UPDATED_ONE_RECORD);

        restAdminTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminTabs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminTabs))
            )
            .andExpect(status().isOk());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
        AdminTabs testAdminTabs = adminTabsList.get(adminTabsList.size() - 1);
        assertThat(testAdminTabs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdminTabs.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAdminTabs.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testAdminTabs.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAdminTabs.getHidden()).isEqualTo(DEFAULT_HIDDEN);
        assertThat(testAdminTabs.getIsCtrlPanel()).isEqualTo(DEFAULT_IS_CTRL_PANEL);
        assertThat(testAdminTabs.getForGroup()).isEqualTo(DEFAULT_FOR_GROUP);
        assertThat(testAdminTabs.getOneRecord()).isEqualTo(UPDATED_ONE_RECORD);
    }

    @Test
    @Transactional
    void fullUpdateAdminTabsWithPatch() throws Exception {
        // Initialize the database
        adminTabsRepository.saveAndFlush(adminTabs);

        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();

        // Update the adminTabs using partial update
        AdminTabs partialUpdatedAdminTabs = new AdminTabs();
        partialUpdatedAdminTabs.setId(adminTabs.getId());

        partialUpdatedAdminTabs
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .icon(UPDATED_ICON)
            .url(UPDATED_URL)
            .hidden(UPDATED_HIDDEN)
            .isCtrlPanel(UPDATED_IS_CTRL_PANEL)
            .forGroup(UPDATED_FOR_GROUP)
            .oneRecord(UPDATED_ONE_RECORD);

        restAdminTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminTabs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminTabs))
            )
            .andExpect(status().isOk());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
        AdminTabs testAdminTabs = adminTabsList.get(adminTabsList.size() - 1);
        assertThat(testAdminTabs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminTabs.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminTabs.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testAdminTabs.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAdminTabs.getHidden()).isEqualTo(UPDATED_HIDDEN);
        assertThat(testAdminTabs.getIsCtrlPanel()).isEqualTo(UPDATED_IS_CTRL_PANEL);
        assertThat(testAdminTabs.getForGroup()).isEqualTo(UPDATED_FOR_GROUP);
        assertThat(testAdminTabs.getOneRecord()).isEqualTo(UPDATED_ONE_RECORD);
    }

    @Test
    @Transactional
    void patchNonExistingAdminTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();
        adminTabs.setId(count.incrementAndGet());

        // Create the AdminTabs
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminTabsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();
        adminTabs.setId(count.incrementAndGet());

        // Create the AdminTabs
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminTabsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminTabsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminTabs() throws Exception {
        int databaseSizeBeforeUpdate = adminTabsRepository.findAll().size();
        adminTabs.setId(count.incrementAndGet());

        // Create the AdminTabs
        AdminTabsDTO adminTabsDTO = adminTabsMapper.toDto(adminTabs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminTabsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adminTabsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminTabs in the database
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminTabs() throws Exception {
        // Initialize the database
        adminTabsRepository.saveAndFlush(adminTabs);

        int databaseSizeBeforeDelete = adminTabsRepository.findAll().size();

        // Delete the adminTabs
        restAdminTabsMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminTabs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminTabs> adminTabsList = adminTabsRepository.findAll();
        assertThat(adminTabsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
