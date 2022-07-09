package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.RefundStep;
import io.github.jhipster.sample.domain.enumeration.RefundStatus;
import io.github.jhipster.sample.domain.enumeration.RefundStepAction;
import io.github.jhipster.sample.repository.RefundStepRepository;
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
 * Integration tests for the {@link RefundStepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RefundStepResourceIT {

    private static final Long DEFAULT_REFERENCE = 1L;
    private static final Long UPDATED_REFERENCE = 2L;

    private static final Instant DEFAULT_CREATE_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final RefundStepAction DEFAULT_ACTION = RefundStepAction.START;
    private static final RefundStepAction UPDATED_ACTION = RefundStepAction.PROCESS;

    private static final RefundStatus DEFAULT_STATUS = RefundStatus.PENDING;
    private static final RefundStatus UPDATED_STATUS = RefundStatus.COMPLETED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/refund-steps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RefundStepRepository refundStepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefundStepMockMvc;

    private RefundStep refundStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefundStep createEntity(EntityManager em) {
        RefundStep refundStep = new RefundStep()
            .reference(DEFAULT_REFERENCE)
            .createDateTimeUtc(DEFAULT_CREATE_DATE_TIME_UTC)
            .action(DEFAULT_ACTION)
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION);
        return refundStep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefundStep createUpdatedEntity(EntityManager em) {
        RefundStep refundStep = new RefundStep()
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION);
        return refundStep;
    }

    @BeforeEach
    public void initTest() {
        refundStep = createEntity(em);
    }

    @Test
    @Transactional
    void createRefundStep() throws Exception {
        int databaseSizeBeforeCreate = refundStepRepository.findAll().size();
        // Create the RefundStep
        restRefundStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refundStep)))
            .andExpect(status().isCreated());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeCreate + 1);
        RefundStep testRefundStep = refundStepList.get(refundStepList.size() - 1);
        assertThat(testRefundStep.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testRefundStep.getCreateDateTimeUtc()).isEqualTo(DEFAULT_CREATE_DATE_TIME_UTC);
        assertThat(testRefundStep.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testRefundStep.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRefundStep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRefundStepWithExistingId() throws Exception {
        // Create the RefundStep with an existing ID
        refundStep.setId(1L);

        int databaseSizeBeforeCreate = refundStepRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refundStep)))
            .andExpect(status().isBadRequest());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRefundSteps() throws Exception {
        // Initialize the database
        refundStepRepository.saveAndFlush(refundStep);

        // Get all the refundStepList
        restRefundStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refundStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].createDateTimeUtc").value(hasItem(DEFAULT_CREATE_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRefundStep() throws Exception {
        // Initialize the database
        refundStepRepository.saveAndFlush(refundStep);

        // Get the refundStep
        restRefundStepMockMvc
            .perform(get(ENTITY_API_URL_ID, refundStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refundStep.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.createDateTimeUtc").value(DEFAULT_CREATE_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRefundStep() throws Exception {
        // Get the refundStep
        restRefundStepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRefundStep() throws Exception {
        // Initialize the database
        refundStepRepository.saveAndFlush(refundStep);

        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();

        // Update the refundStep
        RefundStep updatedRefundStep = refundStepRepository.findById(refundStep.getId()).get();
        // Disconnect from session so that the updates on updatedRefundStep are not directly saved in db
        em.detach(updatedRefundStep);
        updatedRefundStep
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION);

        restRefundStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRefundStep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRefundStep))
            )
            .andExpect(status().isOk());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
        RefundStep testRefundStep = refundStepList.get(refundStepList.size() - 1);
        assertThat(testRefundStep.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefundStep.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testRefundStep.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testRefundStep.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRefundStep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRefundStep() throws Exception {
        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();
        refundStep.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refundStep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refundStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRefundStep() throws Exception {
        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();
        refundStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refundStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRefundStep() throws Exception {
        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();
        refundStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundStepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refundStep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRefundStepWithPatch() throws Exception {
        // Initialize the database
        refundStepRepository.saveAndFlush(refundStep);

        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();

        // Update the refundStep using partial update
        RefundStep partialUpdatedRefundStep = new RefundStep();
        partialUpdatedRefundStep.setId(refundStep.getId());

        partialUpdatedRefundStep.createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC).action(UPDATED_ACTION).status(UPDATED_STATUS);

        restRefundStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefundStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefundStep))
            )
            .andExpect(status().isOk());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
        RefundStep testRefundStep = refundStepList.get(refundStepList.size() - 1);
        assertThat(testRefundStep.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testRefundStep.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testRefundStep.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testRefundStep.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRefundStep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRefundStepWithPatch() throws Exception {
        // Initialize the database
        refundStepRepository.saveAndFlush(refundStep);

        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();

        // Update the refundStep using partial update
        RefundStep partialUpdatedRefundStep = new RefundStep();
        partialUpdatedRefundStep.setId(refundStep.getId());

        partialUpdatedRefundStep
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION);

        restRefundStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefundStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefundStep))
            )
            .andExpect(status().isOk());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
        RefundStep testRefundStep = refundStepList.get(refundStepList.size() - 1);
        assertThat(testRefundStep.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefundStep.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testRefundStep.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testRefundStep.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRefundStep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRefundStep() throws Exception {
        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();
        refundStep.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refundStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refundStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRefundStep() throws Exception {
        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();
        refundStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refundStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRefundStep() throws Exception {
        int databaseSizeBeforeUpdate = refundStepRepository.findAll().size();
        refundStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundStepMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(refundStep))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RefundStep in the database
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRefundStep() throws Exception {
        // Initialize the database
        refundStepRepository.saveAndFlush(refundStep);

        int databaseSizeBeforeDelete = refundStepRepository.findAll().size();

        // Delete the refundStep
        restRefundStepMockMvc
            .perform(delete(ENTITY_API_URL_ID, refundStep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefundStep> refundStepList = refundStepRepository.findAll();
        assertThat(refundStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
