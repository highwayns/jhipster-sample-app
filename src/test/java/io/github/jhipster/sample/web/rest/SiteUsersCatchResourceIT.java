package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.SiteUsersCatch;
import io.github.jhipster.sample.repository.SiteUsersCatchRepository;
import io.github.jhipster.sample.service.dto.SiteUsersCatchDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersCatchMapper;
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
 * Integration tests for the {@link SiteUsersCatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteUsersCatchResourceIT {

    private static final Integer DEFAULT_ATTEMPTS = 1;
    private static final Integer UPDATED_ATTEMPTS = 2;

    private static final String ENTITY_API_URL = "/api/site-users-catches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteUsersCatchRepository siteUsersCatchRepository;

    @Autowired
    private SiteUsersCatchMapper siteUsersCatchMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteUsersCatchMockMvc;

    private SiteUsersCatch siteUsersCatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsersCatch createEntity(EntityManager em) {
        SiteUsersCatch siteUsersCatch = new SiteUsersCatch().attempts(DEFAULT_ATTEMPTS);
        return siteUsersCatch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsersCatch createUpdatedEntity(EntityManager em) {
        SiteUsersCatch siteUsersCatch = new SiteUsersCatch().attempts(UPDATED_ATTEMPTS);
        return siteUsersCatch;
    }

    @BeforeEach
    public void initTest() {
        siteUsersCatch = createEntity(em);
    }

    @Test
    @Transactional
    void createSiteUsersCatch() throws Exception {
        int databaseSizeBeforeCreate = siteUsersCatchRepository.findAll().size();
        // Create the SiteUsersCatch
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);
        restSiteUsersCatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeCreate + 1);
        SiteUsersCatch testSiteUsersCatch = siteUsersCatchList.get(siteUsersCatchList.size() - 1);
        assertThat(testSiteUsersCatch.getAttempts()).isEqualTo(DEFAULT_ATTEMPTS);
    }

    @Test
    @Transactional
    void createSiteUsersCatchWithExistingId() throws Exception {
        // Create the SiteUsersCatch with an existing ID
        siteUsersCatch.setId(1L);
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        int databaseSizeBeforeCreate = siteUsersCatchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteUsersCatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttemptsIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersCatchRepository.findAll().size();
        // set the field null
        siteUsersCatch.setAttempts(null);

        // Create the SiteUsersCatch, which fails.
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        restSiteUsersCatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isBadRequest());

        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSiteUsersCatches() throws Exception {
        // Initialize the database
        siteUsersCatchRepository.saveAndFlush(siteUsersCatch);

        // Get all the siteUsersCatchList
        restSiteUsersCatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteUsersCatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].attempts").value(hasItem(DEFAULT_ATTEMPTS)));
    }

    @Test
    @Transactional
    void getSiteUsersCatch() throws Exception {
        // Initialize the database
        siteUsersCatchRepository.saveAndFlush(siteUsersCatch);

        // Get the siteUsersCatch
        restSiteUsersCatchMockMvc
            .perform(get(ENTITY_API_URL_ID, siteUsersCatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteUsersCatch.getId().intValue()))
            .andExpect(jsonPath("$.attempts").value(DEFAULT_ATTEMPTS));
    }

    @Test
    @Transactional
    void getNonExistingSiteUsersCatch() throws Exception {
        // Get the siteUsersCatch
        restSiteUsersCatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiteUsersCatch() throws Exception {
        // Initialize the database
        siteUsersCatchRepository.saveAndFlush(siteUsersCatch);

        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();

        // Update the siteUsersCatch
        SiteUsersCatch updatedSiteUsersCatch = siteUsersCatchRepository.findById(siteUsersCatch.getId()).get();
        // Disconnect from session so that the updates on updatedSiteUsersCatch are not directly saved in db
        em.detach(updatedSiteUsersCatch);
        updatedSiteUsersCatch.attempts(UPDATED_ATTEMPTS);
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(updatedSiteUsersCatch);

        restSiteUsersCatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersCatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersCatch testSiteUsersCatch = siteUsersCatchList.get(siteUsersCatchList.size() - 1);
        assertThat(testSiteUsersCatch.getAttempts()).isEqualTo(UPDATED_ATTEMPTS);
    }

    @Test
    @Transactional
    void putNonExistingSiteUsersCatch() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();
        siteUsersCatch.setId(count.incrementAndGet());

        // Create the SiteUsersCatch
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersCatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersCatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteUsersCatch() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();
        siteUsersCatch.setId(count.incrementAndGet());

        // Create the SiteUsersCatch
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersCatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteUsersCatch() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();
        siteUsersCatch.setId(count.incrementAndGet());

        // Create the SiteUsersCatch
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersCatchMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteUsersCatchWithPatch() throws Exception {
        // Initialize the database
        siteUsersCatchRepository.saveAndFlush(siteUsersCatch);

        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();

        // Update the siteUsersCatch using partial update
        SiteUsersCatch partialUpdatedSiteUsersCatch = new SiteUsersCatch();
        partialUpdatedSiteUsersCatch.setId(siteUsersCatch.getId());

        partialUpdatedSiteUsersCatch.attempts(UPDATED_ATTEMPTS);

        restSiteUsersCatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsersCatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsersCatch))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersCatch testSiteUsersCatch = siteUsersCatchList.get(siteUsersCatchList.size() - 1);
        assertThat(testSiteUsersCatch.getAttempts()).isEqualTo(UPDATED_ATTEMPTS);
    }

    @Test
    @Transactional
    void fullUpdateSiteUsersCatchWithPatch() throws Exception {
        // Initialize the database
        siteUsersCatchRepository.saveAndFlush(siteUsersCatch);

        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();

        // Update the siteUsersCatch using partial update
        SiteUsersCatch partialUpdatedSiteUsersCatch = new SiteUsersCatch();
        partialUpdatedSiteUsersCatch.setId(siteUsersCatch.getId());

        partialUpdatedSiteUsersCatch.attempts(UPDATED_ATTEMPTS);

        restSiteUsersCatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsersCatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsersCatch))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersCatch testSiteUsersCatch = siteUsersCatchList.get(siteUsersCatchList.size() - 1);
        assertThat(testSiteUsersCatch.getAttempts()).isEqualTo(UPDATED_ATTEMPTS);
    }

    @Test
    @Transactional
    void patchNonExistingSiteUsersCatch() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();
        siteUsersCatch.setId(count.incrementAndGet());

        // Create the SiteUsersCatch
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersCatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteUsersCatchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteUsersCatch() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();
        siteUsersCatch.setId(count.incrementAndGet());

        // Create the SiteUsersCatch
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersCatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteUsersCatch() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersCatchRepository.findAll().size();
        siteUsersCatch.setId(count.incrementAndGet());

        // Create the SiteUsersCatch
        SiteUsersCatchDTO siteUsersCatchDTO = siteUsersCatchMapper.toDto(siteUsersCatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersCatchMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersCatchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsersCatch in the database
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteUsersCatch() throws Exception {
        // Initialize the database
        siteUsersCatchRepository.saveAndFlush(siteUsersCatch);

        int databaseSizeBeforeDelete = siteUsersCatchRepository.findAll().size();

        // Delete the siteUsersCatch
        restSiteUsersCatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteUsersCatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteUsersCatch> siteUsersCatchList = siteUsersCatchRepository.findAll();
        assertThat(siteUsersCatchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
