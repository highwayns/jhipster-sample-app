package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AbuseReport;
import io.github.jhipster.sample.repository.AbuseReportRepository;
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
 * Integration tests for the {@link AbuseReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AbuseReportResourceIT {

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final Instant DEFAULT_CREATED_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/abuse-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AbuseReportRepository abuseReportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbuseReportMockMvc;

    private AbuseReport abuseReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbuseReport createEntity(EntityManager em) {
        AbuseReport abuseReport = new AbuseReport().score(DEFAULT_SCORE).createdDateTimeUtc(DEFAULT_CREATED_DATE_TIME_UTC);
        return abuseReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbuseReport createUpdatedEntity(EntityManager em) {
        AbuseReport abuseReport = new AbuseReport().score(UPDATED_SCORE).createdDateTimeUtc(UPDATED_CREATED_DATE_TIME_UTC);
        return abuseReport;
    }

    @BeforeEach
    public void initTest() {
        abuseReport = createEntity(em);
    }

    @Test
    @Transactional
    void createAbuseReport() throws Exception {
        int databaseSizeBeforeCreate = abuseReportRepository.findAll().size();
        // Create the AbuseReport
        restAbuseReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abuseReport)))
            .andExpect(status().isCreated());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeCreate + 1);
        AbuseReport testAbuseReport = abuseReportList.get(abuseReportList.size() - 1);
        assertThat(testAbuseReport.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAbuseReport.getCreatedDateTimeUtc()).isEqualTo(DEFAULT_CREATED_DATE_TIME_UTC);
    }

    @Test
    @Transactional
    void createAbuseReportWithExistingId() throws Exception {
        // Create the AbuseReport with an existing ID
        abuseReport.setId(1L);

        int databaseSizeBeforeCreate = abuseReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbuseReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abuseReport)))
            .andExpect(status().isBadRequest());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAbuseReports() throws Exception {
        // Initialize the database
        abuseReportRepository.saveAndFlush(abuseReport);

        // Get all the abuseReportList
        restAbuseReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abuseReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDateTimeUtc").value(hasItem(DEFAULT_CREATED_DATE_TIME_UTC.toString())));
    }

    @Test
    @Transactional
    void getAbuseReport() throws Exception {
        // Initialize the database
        abuseReportRepository.saveAndFlush(abuseReport);

        // Get the abuseReport
        restAbuseReportMockMvc
            .perform(get(ENTITY_API_URL_ID, abuseReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(abuseReport.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.createdDateTimeUtc").value(DEFAULT_CREATED_DATE_TIME_UTC.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAbuseReport() throws Exception {
        // Get the abuseReport
        restAbuseReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAbuseReport() throws Exception {
        // Initialize the database
        abuseReportRepository.saveAndFlush(abuseReport);

        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();

        // Update the abuseReport
        AbuseReport updatedAbuseReport = abuseReportRepository.findById(abuseReport.getId()).get();
        // Disconnect from session so that the updates on updatedAbuseReport are not directly saved in db
        em.detach(updatedAbuseReport);
        updatedAbuseReport.score(UPDATED_SCORE).createdDateTimeUtc(UPDATED_CREATED_DATE_TIME_UTC);

        restAbuseReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAbuseReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAbuseReport))
            )
            .andExpect(status().isOk());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
        AbuseReport testAbuseReport = abuseReportList.get(abuseReportList.size() - 1);
        assertThat(testAbuseReport.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAbuseReport.getCreatedDateTimeUtc()).isEqualTo(UPDATED_CREATED_DATE_TIME_UTC);
    }

    @Test
    @Transactional
    void putNonExistingAbuseReport() throws Exception {
        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();
        abuseReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbuseReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, abuseReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(abuseReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAbuseReport() throws Exception {
        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();
        abuseReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(abuseReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAbuseReport() throws Exception {
        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();
        abuseReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abuseReport)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAbuseReportWithPatch() throws Exception {
        // Initialize the database
        abuseReportRepository.saveAndFlush(abuseReport);

        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();

        // Update the abuseReport using partial update
        AbuseReport partialUpdatedAbuseReport = new AbuseReport();
        partialUpdatedAbuseReport.setId(abuseReport.getId());

        partialUpdatedAbuseReport.createdDateTimeUtc(UPDATED_CREATED_DATE_TIME_UTC);

        restAbuseReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbuseReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAbuseReport))
            )
            .andExpect(status().isOk());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
        AbuseReport testAbuseReport = abuseReportList.get(abuseReportList.size() - 1);
        assertThat(testAbuseReport.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAbuseReport.getCreatedDateTimeUtc()).isEqualTo(UPDATED_CREATED_DATE_TIME_UTC);
    }

    @Test
    @Transactional
    void fullUpdateAbuseReportWithPatch() throws Exception {
        // Initialize the database
        abuseReportRepository.saveAndFlush(abuseReport);

        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();

        // Update the abuseReport using partial update
        AbuseReport partialUpdatedAbuseReport = new AbuseReport();
        partialUpdatedAbuseReport.setId(abuseReport.getId());

        partialUpdatedAbuseReport.score(UPDATED_SCORE).createdDateTimeUtc(UPDATED_CREATED_DATE_TIME_UTC);

        restAbuseReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbuseReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAbuseReport))
            )
            .andExpect(status().isOk());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
        AbuseReport testAbuseReport = abuseReportList.get(abuseReportList.size() - 1);
        assertThat(testAbuseReport.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAbuseReport.getCreatedDateTimeUtc()).isEqualTo(UPDATED_CREATED_DATE_TIME_UTC);
    }

    @Test
    @Transactional
    void patchNonExistingAbuseReport() throws Exception {
        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();
        abuseReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbuseReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, abuseReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(abuseReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAbuseReport() throws Exception {
        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();
        abuseReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(abuseReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAbuseReport() throws Exception {
        int databaseSizeBeforeUpdate = abuseReportRepository.findAll().size();
        abuseReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(abuseReport))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AbuseReport in the database
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAbuseReport() throws Exception {
        // Initialize the database
        abuseReportRepository.saveAndFlush(abuseReport);

        int databaseSizeBeforeDelete = abuseReportRepository.findAll().size();

        // Delete the abuseReport
        restAbuseReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, abuseReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AbuseReport> abuseReportList = abuseReportRepository.findAll();
        assertThat(abuseReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
