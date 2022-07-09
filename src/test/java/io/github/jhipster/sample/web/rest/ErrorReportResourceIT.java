package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.ErrorReport;
import io.github.jhipster.sample.domain.enumeration.Locale;
import io.github.jhipster.sample.repository.ErrorReportRepository;
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
 * Integration tests for the {@link ErrorReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ErrorReportResourceIT {

    private static final Locale DEFAULT_LANGUAGE = Locale.EL_GR;
    private static final Locale UPDATED_LANGUAGE = Locale.EN_US;

    private static final Boolean DEFAULT_IS_FATAL_ERROR = false;
    private static final Boolean UPDATED_IS_FATAL_ERROR = true;

    private static final String ENTITY_API_URL = "/api/error-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ErrorReportRepository errorReportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restErrorReportMockMvc;

    private ErrorReport errorReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ErrorReport createEntity(EntityManager em) {
        ErrorReport errorReport = new ErrorReport().language(DEFAULT_LANGUAGE).isFatalError(DEFAULT_IS_FATAL_ERROR);
        return errorReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ErrorReport createUpdatedEntity(EntityManager em) {
        ErrorReport errorReport = new ErrorReport().language(UPDATED_LANGUAGE).isFatalError(UPDATED_IS_FATAL_ERROR);
        return errorReport;
    }

    @BeforeEach
    public void initTest() {
        errorReport = createEntity(em);
    }

    @Test
    @Transactional
    void createErrorReport() throws Exception {
        int databaseSizeBeforeCreate = errorReportRepository.findAll().size();
        // Create the ErrorReport
        restErrorReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errorReport)))
            .andExpect(status().isCreated());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeCreate + 1);
        ErrorReport testErrorReport = errorReportList.get(errorReportList.size() - 1);
        assertThat(testErrorReport.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testErrorReport.getIsFatalError()).isEqualTo(DEFAULT_IS_FATAL_ERROR);
    }

    @Test
    @Transactional
    void createErrorReportWithExistingId() throws Exception {
        // Create the ErrorReport with an existing ID
        errorReport.setId(1L);

        int databaseSizeBeforeCreate = errorReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restErrorReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errorReport)))
            .andExpect(status().isBadRequest());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllErrorReports() throws Exception {
        // Initialize the database
        errorReportRepository.saveAndFlush(errorReport);

        // Get all the errorReportList
        restErrorReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(errorReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].isFatalError").value(hasItem(DEFAULT_IS_FATAL_ERROR.booleanValue())));
    }

    @Test
    @Transactional
    void getErrorReport() throws Exception {
        // Initialize the database
        errorReportRepository.saveAndFlush(errorReport);

        // Get the errorReport
        restErrorReportMockMvc
            .perform(get(ENTITY_API_URL_ID, errorReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(errorReport.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.isFatalError").value(DEFAULT_IS_FATAL_ERROR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingErrorReport() throws Exception {
        // Get the errorReport
        restErrorReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewErrorReport() throws Exception {
        // Initialize the database
        errorReportRepository.saveAndFlush(errorReport);

        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();

        // Update the errorReport
        ErrorReport updatedErrorReport = errorReportRepository.findById(errorReport.getId()).get();
        // Disconnect from session so that the updates on updatedErrorReport are not directly saved in db
        em.detach(updatedErrorReport);
        updatedErrorReport.language(UPDATED_LANGUAGE).isFatalError(UPDATED_IS_FATAL_ERROR);

        restErrorReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedErrorReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedErrorReport))
            )
            .andExpect(status().isOk());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
        ErrorReport testErrorReport = errorReportList.get(errorReportList.size() - 1);
        assertThat(testErrorReport.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testErrorReport.getIsFatalError()).isEqualTo(UPDATED_IS_FATAL_ERROR);
    }

    @Test
    @Transactional
    void putNonExistingErrorReport() throws Exception {
        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();
        errorReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrorReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, errorReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(errorReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchErrorReport() throws Exception {
        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();
        errorReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(errorReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamErrorReport() throws Exception {
        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();
        errorReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errorReport)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateErrorReportWithPatch() throws Exception {
        // Initialize the database
        errorReportRepository.saveAndFlush(errorReport);

        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();

        // Update the errorReport using partial update
        ErrorReport partialUpdatedErrorReport = new ErrorReport();
        partialUpdatedErrorReport.setId(errorReport.getId());

        partialUpdatedErrorReport.language(UPDATED_LANGUAGE).isFatalError(UPDATED_IS_FATAL_ERROR);

        restErrorReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedErrorReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedErrorReport))
            )
            .andExpect(status().isOk());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
        ErrorReport testErrorReport = errorReportList.get(errorReportList.size() - 1);
        assertThat(testErrorReport.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testErrorReport.getIsFatalError()).isEqualTo(UPDATED_IS_FATAL_ERROR);
    }

    @Test
    @Transactional
    void fullUpdateErrorReportWithPatch() throws Exception {
        // Initialize the database
        errorReportRepository.saveAndFlush(errorReport);

        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();

        // Update the errorReport using partial update
        ErrorReport partialUpdatedErrorReport = new ErrorReport();
        partialUpdatedErrorReport.setId(errorReport.getId());

        partialUpdatedErrorReport.language(UPDATED_LANGUAGE).isFatalError(UPDATED_IS_FATAL_ERROR);

        restErrorReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedErrorReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedErrorReport))
            )
            .andExpect(status().isOk());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
        ErrorReport testErrorReport = errorReportList.get(errorReportList.size() - 1);
        assertThat(testErrorReport.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testErrorReport.getIsFatalError()).isEqualTo(UPDATED_IS_FATAL_ERROR);
    }

    @Test
    @Transactional
    void patchNonExistingErrorReport() throws Exception {
        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();
        errorReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrorReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, errorReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(errorReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchErrorReport() throws Exception {
        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();
        errorReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(errorReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamErrorReport() throws Exception {
        int databaseSizeBeforeUpdate = errorReportRepository.findAll().size();
        errorReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(errorReport))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ErrorReport in the database
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteErrorReport() throws Exception {
        // Initialize the database
        errorReportRepository.saveAndFlush(errorReport);

        int databaseSizeBeforeDelete = errorReportRepository.findAll().size();

        // Delete the errorReport
        restErrorReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, errorReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ErrorReport> errorReportList = errorReportRepository.findAll();
        assertThat(errorReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
