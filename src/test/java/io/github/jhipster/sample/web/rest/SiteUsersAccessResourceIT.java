package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.SiteUsersAccess;
import io.github.jhipster.sample.repository.SiteUsersAccessRepository;
import io.github.jhipster.sample.service.dto.SiteUsersAccessDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersAccessMapper;
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
 * Integration tests for the {@link SiteUsersAccessResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteUsersAccessResourceIT {

    private static final Long DEFAULT_START = 1L;
    private static final Long UPDATED_START = 2L;

    private static final Long DEFAULT_LAST = 1L;
    private static final Long UPDATED_LAST = 2L;

    private static final Integer DEFAULT_ATTEMPTS = 1;
    private static final Integer UPDATED_ATTEMPTS = 2;

    private static final String ENTITY_API_URL = "/api/site-users-accesses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteUsersAccessRepository siteUsersAccessRepository;

    @Autowired
    private SiteUsersAccessMapper siteUsersAccessMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteUsersAccessMockMvc;

    private SiteUsersAccess siteUsersAccess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsersAccess createEntity(EntityManager em) {
        SiteUsersAccess siteUsersAccess = new SiteUsersAccess().start(DEFAULT_START).last(DEFAULT_LAST).attempts(DEFAULT_ATTEMPTS);
        return siteUsersAccess;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsersAccess createUpdatedEntity(EntityManager em) {
        SiteUsersAccess siteUsersAccess = new SiteUsersAccess().start(UPDATED_START).last(UPDATED_LAST).attempts(UPDATED_ATTEMPTS);
        return siteUsersAccess;
    }

    @BeforeEach
    public void initTest() {
        siteUsersAccess = createEntity(em);
    }

    @Test
    @Transactional
    void createSiteUsersAccess() throws Exception {
        int databaseSizeBeforeCreate = siteUsersAccessRepository.findAll().size();
        // Create the SiteUsersAccess
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);
        restSiteUsersAccessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeCreate + 1);
        SiteUsersAccess testSiteUsersAccess = siteUsersAccessList.get(siteUsersAccessList.size() - 1);
        assertThat(testSiteUsersAccess.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testSiteUsersAccess.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testSiteUsersAccess.getAttempts()).isEqualTo(DEFAULT_ATTEMPTS);
    }

    @Test
    @Transactional
    void createSiteUsersAccessWithExistingId() throws Exception {
        // Create the SiteUsersAccess with an existing ID
        siteUsersAccess.setId(1L);
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        int databaseSizeBeforeCreate = siteUsersAccessRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteUsersAccessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersAccessRepository.findAll().size();
        // set the field null
        siteUsersAccess.setStart(null);

        // Create the SiteUsersAccess, which fails.
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        restSiteUsersAccessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersAccessRepository.findAll().size();
        // set the field null
        siteUsersAccess.setLast(null);

        // Create the SiteUsersAccess, which fails.
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        restSiteUsersAccessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttemptsIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersAccessRepository.findAll().size();
        // set the field null
        siteUsersAccess.setAttempts(null);

        // Create the SiteUsersAccess, which fails.
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        restSiteUsersAccessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSiteUsersAccesses() throws Exception {
        // Initialize the database
        siteUsersAccessRepository.saveAndFlush(siteUsersAccess);

        // Get all the siteUsersAccessList
        restSiteUsersAccessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteUsersAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.intValue())))
            .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.intValue())))
            .andExpect(jsonPath("$.[*].attempts").value(hasItem(DEFAULT_ATTEMPTS)));
    }

    @Test
    @Transactional
    void getSiteUsersAccess() throws Exception {
        // Initialize the database
        siteUsersAccessRepository.saveAndFlush(siteUsersAccess);

        // Get the siteUsersAccess
        restSiteUsersAccessMockMvc
            .perform(get(ENTITY_API_URL_ID, siteUsersAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteUsersAccess.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.intValue()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.intValue()))
            .andExpect(jsonPath("$.attempts").value(DEFAULT_ATTEMPTS));
    }

    @Test
    @Transactional
    void getNonExistingSiteUsersAccess() throws Exception {
        // Get the siteUsersAccess
        restSiteUsersAccessMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiteUsersAccess() throws Exception {
        // Initialize the database
        siteUsersAccessRepository.saveAndFlush(siteUsersAccess);

        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();

        // Update the siteUsersAccess
        SiteUsersAccess updatedSiteUsersAccess = siteUsersAccessRepository.findById(siteUsersAccess.getId()).get();
        // Disconnect from session so that the updates on updatedSiteUsersAccess are not directly saved in db
        em.detach(updatedSiteUsersAccess);
        updatedSiteUsersAccess.start(UPDATED_START).last(UPDATED_LAST).attempts(UPDATED_ATTEMPTS);
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(updatedSiteUsersAccess);

        restSiteUsersAccessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersAccessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersAccess testSiteUsersAccess = siteUsersAccessList.get(siteUsersAccessList.size() - 1);
        assertThat(testSiteUsersAccess.getStart()).isEqualTo(UPDATED_START);
        assertThat(testSiteUsersAccess.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testSiteUsersAccess.getAttempts()).isEqualTo(UPDATED_ATTEMPTS);
    }

    @Test
    @Transactional
    void putNonExistingSiteUsersAccess() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();
        siteUsersAccess.setId(count.incrementAndGet());

        // Create the SiteUsersAccess
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersAccessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersAccessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteUsersAccess() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();
        siteUsersAccess.setId(count.incrementAndGet());

        // Create the SiteUsersAccess
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersAccessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteUsersAccess() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();
        siteUsersAccess.setId(count.incrementAndGet());

        // Create the SiteUsersAccess
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersAccessMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteUsersAccessWithPatch() throws Exception {
        // Initialize the database
        siteUsersAccessRepository.saveAndFlush(siteUsersAccess);

        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();

        // Update the siteUsersAccess using partial update
        SiteUsersAccess partialUpdatedSiteUsersAccess = new SiteUsersAccess();
        partialUpdatedSiteUsersAccess.setId(siteUsersAccess.getId());

        partialUpdatedSiteUsersAccess.start(UPDATED_START).attempts(UPDATED_ATTEMPTS);

        restSiteUsersAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsersAccess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsersAccess))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersAccess testSiteUsersAccess = siteUsersAccessList.get(siteUsersAccessList.size() - 1);
        assertThat(testSiteUsersAccess.getStart()).isEqualTo(UPDATED_START);
        assertThat(testSiteUsersAccess.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testSiteUsersAccess.getAttempts()).isEqualTo(UPDATED_ATTEMPTS);
    }

    @Test
    @Transactional
    void fullUpdateSiteUsersAccessWithPatch() throws Exception {
        // Initialize the database
        siteUsersAccessRepository.saveAndFlush(siteUsersAccess);

        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();

        // Update the siteUsersAccess using partial update
        SiteUsersAccess partialUpdatedSiteUsersAccess = new SiteUsersAccess();
        partialUpdatedSiteUsersAccess.setId(siteUsersAccess.getId());

        partialUpdatedSiteUsersAccess.start(UPDATED_START).last(UPDATED_LAST).attempts(UPDATED_ATTEMPTS);

        restSiteUsersAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsersAccess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsersAccess))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersAccess testSiteUsersAccess = siteUsersAccessList.get(siteUsersAccessList.size() - 1);
        assertThat(testSiteUsersAccess.getStart()).isEqualTo(UPDATED_START);
        assertThat(testSiteUsersAccess.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testSiteUsersAccess.getAttempts()).isEqualTo(UPDATED_ATTEMPTS);
    }

    @Test
    @Transactional
    void patchNonExistingSiteUsersAccess() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();
        siteUsersAccess.setId(count.incrementAndGet());

        // Create the SiteUsersAccess
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteUsersAccessDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteUsersAccess() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();
        siteUsersAccess.setId(count.incrementAndGet());

        // Create the SiteUsersAccess
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteUsersAccess() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersAccessRepository.findAll().size();
        siteUsersAccess.setId(count.incrementAndGet());

        // Create the SiteUsersAccess
        SiteUsersAccessDTO siteUsersAccessDTO = siteUsersAccessMapper.toDto(siteUsersAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersAccessMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersAccessDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsersAccess in the database
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteUsersAccess() throws Exception {
        // Initialize the database
        siteUsersAccessRepository.saveAndFlush(siteUsersAccess);

        int databaseSizeBeforeDelete = siteUsersAccessRepository.findAll().size();

        // Delete the siteUsersAccess
        restSiteUsersAccessMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteUsersAccess.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteUsersAccess> siteUsersAccessList = siteUsersAccessRepository.findAll();
        assertThat(siteUsersAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
