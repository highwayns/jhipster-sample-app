package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminSessions;
import io.github.jhipster.sample.repository.AdminSessionsRepository;
import io.github.jhipster.sample.service.dto.AdminSessionsDTO;
import io.github.jhipster.sample.service.mapper.AdminSessionsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AdminSessionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminSessionsResourceIT {

    private static final String DEFAULT_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_SESSION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SESSION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SESSION_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SESSION_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SESSION_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/admin-sessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminSessionsRepository adminSessionsRepository;

    @Autowired
    private AdminSessionsMapper adminSessionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminSessionsMockMvc;

    private AdminSessions adminSessions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminSessions createEntity(EntityManager em) {
        AdminSessions adminSessions = new AdminSessions()
            .sessionId(DEFAULT_SESSION_ID)
            .sessionTime(DEFAULT_SESSION_TIME)
            .sessionStart(DEFAULT_SESSION_START)
            .sessionValue(DEFAULT_SESSION_VALUE)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .userAgent(DEFAULT_USER_AGENT);
        return adminSessions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminSessions createUpdatedEntity(EntityManager em) {
        AdminSessions adminSessions = new AdminSessions()
            .sessionId(UPDATED_SESSION_ID)
            .sessionTime(UPDATED_SESSION_TIME)
            .sessionStart(UPDATED_SESSION_START)
            .sessionValue(UPDATED_SESSION_VALUE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT);
        return adminSessions;
    }

    @BeforeEach
    public void initTest() {
        adminSessions = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminSessions() throws Exception {
        int databaseSizeBeforeCreate = adminSessionsRepository.findAll().size();
        // Create the AdminSessions
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);
        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeCreate + 1);
        AdminSessions testAdminSessions = adminSessionsList.get(adminSessionsList.size() - 1);
        assertThat(testAdminSessions.getSessionId()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testAdminSessions.getSessionTime()).isEqualTo(DEFAULT_SESSION_TIME);
        assertThat(testAdminSessions.getSessionStart()).isEqualTo(DEFAULT_SESSION_START);
        assertThat(testAdminSessions.getSessionValue()).isEqualTo(DEFAULT_SESSION_VALUE);
        assertThat(testAdminSessions.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testAdminSessions.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
    }

    @Test
    @Transactional
    void createAdminSessionsWithExistingId() throws Exception {
        // Create the AdminSessions with an existing ID
        adminSessions.setId(1L);
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        int databaseSizeBeforeCreate = adminSessionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSessionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminSessionsRepository.findAll().size();
        // set the field null
        adminSessions.setSessionId(null);

        // Create the AdminSessions, which fails.
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminSessionsRepository.findAll().size();
        // set the field null
        adminSessions.setSessionTime(null);

        // Create the AdminSessions, which fails.
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminSessionsRepository.findAll().size();
        // set the field null
        adminSessions.setSessionStart(null);

        // Create the AdminSessions, which fails.
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminSessionsRepository.findAll().size();
        // set the field null
        adminSessions.setSessionValue(null);

        // Create the AdminSessions, which fails.
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminSessionsRepository.findAll().size();
        // set the field null
        adminSessions.setIpAddress(null);

        // Create the AdminSessions, which fails.
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserAgentIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminSessionsRepository.findAll().size();
        // set the field null
        adminSessions.setUserAgent(null);

        // Create the AdminSessions, which fails.
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        restAdminSessionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminSessions() throws Exception {
        // Initialize the database
        adminSessionsRepository.saveAndFlush(adminSessions);

        // Get all the adminSessionsList
        restAdminSessionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminSessions.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID)))
            .andExpect(jsonPath("$.[*].sessionTime").value(hasItem(DEFAULT_SESSION_TIME.toString())))
            .andExpect(jsonPath("$.[*].sessionStart").value(hasItem(DEFAULT_SESSION_START.toString())))
            .andExpect(jsonPath("$.[*].sessionValue").value(hasItem(DEFAULT_SESSION_VALUE)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)));
    }

    @Test
    @Transactional
    void getAdminSessions() throws Exception {
        // Initialize the database
        adminSessionsRepository.saveAndFlush(adminSessions);

        // Get the adminSessions
        restAdminSessionsMockMvc
            .perform(get(ENTITY_API_URL_ID, adminSessions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminSessions.getId().intValue()))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID))
            .andExpect(jsonPath("$.sessionTime").value(DEFAULT_SESSION_TIME.toString()))
            .andExpect(jsonPath("$.sessionStart").value(DEFAULT_SESSION_START.toString()))
            .andExpect(jsonPath("$.sessionValue").value(DEFAULT_SESSION_VALUE))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT));
    }

    @Test
    @Transactional
    void getNonExistingAdminSessions() throws Exception {
        // Get the adminSessions
        restAdminSessionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminSessions() throws Exception {
        // Initialize the database
        adminSessionsRepository.saveAndFlush(adminSessions);

        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();

        // Update the adminSessions
        AdminSessions updatedAdminSessions = adminSessionsRepository.findById(adminSessions.getId()).get();
        // Disconnect from session so that the updates on updatedAdminSessions are not directly saved in db
        em.detach(updatedAdminSessions);
        updatedAdminSessions
            .sessionId(UPDATED_SESSION_ID)
            .sessionTime(UPDATED_SESSION_TIME)
            .sessionStart(UPDATED_SESSION_START)
            .sessionValue(UPDATED_SESSION_VALUE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT);
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(updatedAdminSessions);

        restAdminSessionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminSessionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
        AdminSessions testAdminSessions = adminSessionsList.get(adminSessionsList.size() - 1);
        assertThat(testAdminSessions.getSessionId()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testAdminSessions.getSessionTime()).isEqualTo(UPDATED_SESSION_TIME);
        assertThat(testAdminSessions.getSessionStart()).isEqualTo(UPDATED_SESSION_START);
        assertThat(testAdminSessions.getSessionValue()).isEqualTo(UPDATED_SESSION_VALUE);
        assertThat(testAdminSessions.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testAdminSessions.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    void putNonExistingAdminSessions() throws Exception {
        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();
        adminSessions.setId(count.incrementAndGet());

        // Create the AdminSessions
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminSessionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminSessionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminSessions() throws Exception {
        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();
        adminSessions.setId(count.incrementAndGet());

        // Create the AdminSessions
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminSessionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminSessions() throws Exception {
        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();
        adminSessions.setId(count.incrementAndGet());

        // Create the AdminSessions
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminSessionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminSessionsWithPatch() throws Exception {
        // Initialize the database
        adminSessionsRepository.saveAndFlush(adminSessions);

        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();

        // Update the adminSessions using partial update
        AdminSessions partialUpdatedAdminSessions = new AdminSessions();
        partialUpdatedAdminSessions.setId(adminSessions.getId());

        partialUpdatedAdminSessions
            .sessionTime(UPDATED_SESSION_TIME)
            .sessionStart(UPDATED_SESSION_START)
            .sessionValue(UPDATED_SESSION_VALUE);

        restAdminSessionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminSessions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminSessions))
            )
            .andExpect(status().isOk());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
        AdminSessions testAdminSessions = adminSessionsList.get(adminSessionsList.size() - 1);
        assertThat(testAdminSessions.getSessionId()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testAdminSessions.getSessionTime()).isEqualTo(UPDATED_SESSION_TIME);
        assertThat(testAdminSessions.getSessionStart()).isEqualTo(UPDATED_SESSION_START);
        assertThat(testAdminSessions.getSessionValue()).isEqualTo(UPDATED_SESSION_VALUE);
        assertThat(testAdminSessions.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testAdminSessions.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
    }

    @Test
    @Transactional
    void fullUpdateAdminSessionsWithPatch() throws Exception {
        // Initialize the database
        adminSessionsRepository.saveAndFlush(adminSessions);

        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();

        // Update the adminSessions using partial update
        AdminSessions partialUpdatedAdminSessions = new AdminSessions();
        partialUpdatedAdminSessions.setId(adminSessions.getId());

        partialUpdatedAdminSessions
            .sessionId(UPDATED_SESSION_ID)
            .sessionTime(UPDATED_SESSION_TIME)
            .sessionStart(UPDATED_SESSION_START)
            .sessionValue(UPDATED_SESSION_VALUE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT);

        restAdminSessionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminSessions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminSessions))
            )
            .andExpect(status().isOk());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
        AdminSessions testAdminSessions = adminSessionsList.get(adminSessionsList.size() - 1);
        assertThat(testAdminSessions.getSessionId()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testAdminSessions.getSessionTime()).isEqualTo(UPDATED_SESSION_TIME);
        assertThat(testAdminSessions.getSessionStart()).isEqualTo(UPDATED_SESSION_START);
        assertThat(testAdminSessions.getSessionValue()).isEqualTo(UPDATED_SESSION_VALUE);
        assertThat(testAdminSessions.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testAdminSessions.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    void patchNonExistingAdminSessions() throws Exception {
        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();
        adminSessions.setId(count.incrementAndGet());

        // Create the AdminSessions
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminSessionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminSessionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminSessions() throws Exception {
        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();
        adminSessions.setId(count.incrementAndGet());

        // Create the AdminSessions
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminSessionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminSessions() throws Exception {
        int databaseSizeBeforeUpdate = adminSessionsRepository.findAll().size();
        adminSessions.setId(count.incrementAndGet());

        // Create the AdminSessions
        AdminSessionsDTO adminSessionsDTO = adminSessionsMapper.toDto(adminSessions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminSessionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminSessionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminSessions in the database
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminSessions() throws Exception {
        // Initialize the database
        adminSessionsRepository.saveAndFlush(adminSessions);

        int databaseSizeBeforeDelete = adminSessionsRepository.findAll().size();

        // Delete the adminSessions
        restAdminSessionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminSessions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminSessions> adminSessionsList = adminSessionsRepository.findAll();
        assertThat(adminSessionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
