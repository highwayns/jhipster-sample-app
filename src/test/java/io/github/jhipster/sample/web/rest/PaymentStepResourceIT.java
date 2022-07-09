package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.PaymentStep;
import io.github.jhipster.sample.domain.enumeration.PaymentStatus;
import io.github.jhipster.sample.domain.enumeration.PaymentStepAction;
import io.github.jhipster.sample.repository.PaymentStepRepository;
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
 * Integration tests for the {@link PaymentStepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentStepResourceIT {

    private static final Long DEFAULT_REFERENCE = 1L;
    private static final Long UPDATED_REFERENCE = 2L;

    private static final Instant DEFAULT_CREATE_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PaymentStepAction DEFAULT_ACTION = PaymentStepAction.DISPLAYHOSTEDPAGE;
    private static final PaymentStepAction UPDATED_ACTION = PaymentStepAction.PAYMENTMETHODSELECTION;

    private static final PaymentStatus DEFAULT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_STATUS = PaymentStatus.RESERVED;

    private static final Double DEFAULT_AMOUNT_TO_COLLECT = 1D;
    private static final Double UPDATED_AMOUNT_TO_COLLECT = 2D;

    private static final String ENTITY_API_URL = "/api/payment-steps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentStepRepository paymentStepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentStepMockMvc;

    private PaymentStep paymentStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentStep createEntity(EntityManager em) {
        PaymentStep paymentStep = new PaymentStep()
            .reference(DEFAULT_REFERENCE)
            .createDateTimeUtc(DEFAULT_CREATE_DATE_TIME_UTC)
            .action(DEFAULT_ACTION)
            .status(DEFAULT_STATUS)
            .amountToCollect(DEFAULT_AMOUNT_TO_COLLECT);
        return paymentStep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentStep createUpdatedEntity(EntityManager em) {
        PaymentStep paymentStep = new PaymentStep()
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT);
        return paymentStep;
    }

    @BeforeEach
    public void initTest() {
        paymentStep = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentStep() throws Exception {
        int databaseSizeBeforeCreate = paymentStepRepository.findAll().size();
        // Create the PaymentStep
        restPaymentStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentStep)))
            .andExpect(status().isCreated());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentStep testPaymentStep = paymentStepList.get(paymentStepList.size() - 1);
        assertThat(testPaymentStep.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPaymentStep.getCreateDateTimeUtc()).isEqualTo(DEFAULT_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentStep.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testPaymentStep.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentStep.getAmountToCollect()).isEqualTo(DEFAULT_AMOUNT_TO_COLLECT);
    }

    @Test
    @Transactional
    void createPaymentStepWithExistingId() throws Exception {
        // Create the PaymentStep with an existing ID
        paymentStep.setId(1L);

        int databaseSizeBeforeCreate = paymentStepRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentStep)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentSteps() throws Exception {
        // Initialize the database
        paymentStepRepository.saveAndFlush(paymentStep);

        // Get all the paymentStepList
        restPaymentStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].createDateTimeUtc").value(hasItem(DEFAULT_CREATE_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].amountToCollect").value(hasItem(DEFAULT_AMOUNT_TO_COLLECT.doubleValue())));
    }

    @Test
    @Transactional
    void getPaymentStep() throws Exception {
        // Initialize the database
        paymentStepRepository.saveAndFlush(paymentStep);

        // Get the paymentStep
        restPaymentStepMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentStep.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.createDateTimeUtc").value(DEFAULT_CREATE_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.amountToCollect").value(DEFAULT_AMOUNT_TO_COLLECT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentStep() throws Exception {
        // Get the paymentStep
        restPaymentStepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentStep() throws Exception {
        // Initialize the database
        paymentStepRepository.saveAndFlush(paymentStep);

        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();

        // Update the paymentStep
        PaymentStep updatedPaymentStep = paymentStepRepository.findById(paymentStep.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentStep are not directly saved in db
        em.detach(updatedPaymentStep);
        updatedPaymentStep
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT);

        restPaymentStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentStep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentStep))
            )
            .andExpect(status().isOk());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
        PaymentStep testPaymentStep = paymentStepList.get(paymentStepList.size() - 1);
        assertThat(testPaymentStep.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPaymentStep.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentStep.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testPaymentStep.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentStep.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
    }

    @Test
    @Transactional
    void putNonExistingPaymentStep() throws Exception {
        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();
        paymentStep.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentStep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentStep() throws Exception {
        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();
        paymentStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentStep() throws Exception {
        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();
        paymentStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentStep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentStepWithPatch() throws Exception {
        // Initialize the database
        paymentStepRepository.saveAndFlush(paymentStep);

        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();

        // Update the paymentStep using partial update
        PaymentStep partialUpdatedPaymentStep = new PaymentStep();
        partialUpdatedPaymentStep.setId(paymentStep.getId());

        partialUpdatedPaymentStep.createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC).amountToCollect(UPDATED_AMOUNT_TO_COLLECT);

        restPaymentStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentStep))
            )
            .andExpect(status().isOk());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
        PaymentStep testPaymentStep = paymentStepList.get(paymentStepList.size() - 1);
        assertThat(testPaymentStep.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPaymentStep.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentStep.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testPaymentStep.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentStep.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentStepWithPatch() throws Exception {
        // Initialize the database
        paymentStepRepository.saveAndFlush(paymentStep);

        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();

        // Update the paymentStep using partial update
        PaymentStep partialUpdatedPaymentStep = new PaymentStep();
        partialUpdatedPaymentStep.setId(paymentStep.getId());

        partialUpdatedPaymentStep
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT);

        restPaymentStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentStep))
            )
            .andExpect(status().isOk());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
        PaymentStep testPaymentStep = paymentStepList.get(paymentStepList.size() - 1);
        assertThat(testPaymentStep.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPaymentStep.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentStep.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testPaymentStep.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentStep.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentStep() throws Exception {
        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();
        paymentStep.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentStep() throws Exception {
        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();
        paymentStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentStep() throws Exception {
        int databaseSizeBeforeUpdate = paymentStepRepository.findAll().size();
        paymentStep.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentStepMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentStep))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentStep in the database
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentStep() throws Exception {
        // Initialize the database
        paymentStepRepository.saveAndFlush(paymentStep);

        int databaseSizeBeforeDelete = paymentStepRepository.findAll().size();

        // Delete the paymentStep
        restPaymentStepMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentStep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentStep> paymentStepList = paymentStepRepository.findAll();
        assertThat(paymentStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
