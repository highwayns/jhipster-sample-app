package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Refund;
import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.domain.enumeration.RefundStatus;
import io.github.jhipster.sample.repository.RefundRepository;
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
 * Integration tests for the {@link RefundResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RefundResourceIT {

    private static final Long DEFAULT_REFERENCE = 1L;
    private static final Long UPDATED_REFERENCE = 2L;

    private static final Instant DEFAULT_CREATE_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REFUND_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_NUMBER = "BBBBBBBBBB";

    private static final RefundStatus DEFAULT_STATUS = RefundStatus.PENDING;
    private static final RefundStatus UPDATED_STATUS = RefundStatus.COMPLETED;

    private static final Double DEFAULT_AMOUNT_TO_REFUND = 1D;
    private static final Double UPDATED_AMOUNT_TO_REFUND = 2D;

    private static final Double DEFAULT_CONVERTED_AMOUNT_TO_REFUND = 1D;
    private static final Double UPDATED_CONVERTED_AMOUNT_TO_REFUND = 2D;

    private static final Currency DEFAULT_CONVERTED_CURRENCY = Currency.CNY;
    private static final Currency UPDATED_CONVERTED_CURRENCY = Currency.JPY;

    private static final Double DEFAULT_CONVERSION_RATE = 1D;
    private static final Double UPDATED_CONVERSION_RATE = 2D;

    private static final String ENTITY_API_URL = "/api/refunds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefundMockMvc;

    private Refund refund;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createEntity(EntityManager em) {
        Refund refund = new Refund()
            .reference(DEFAULT_REFERENCE)
            .createDateTimeUtc(DEFAULT_CREATE_DATE_TIME_UTC)
            .refundNumber(DEFAULT_REFUND_NUMBER)
            .status(DEFAULT_STATUS)
            .amountToRefund(DEFAULT_AMOUNT_TO_REFUND)
            .convertedAmountToRefund(DEFAULT_CONVERTED_AMOUNT_TO_REFUND)
            .convertedCurrency(DEFAULT_CONVERTED_CURRENCY)
            .conversionRate(DEFAULT_CONVERSION_RATE);
        return refund;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createUpdatedEntity(EntityManager em) {
        Refund refund = new Refund()
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .refundNumber(UPDATED_REFUND_NUMBER)
            .status(UPDATED_STATUS)
            .amountToRefund(UPDATED_AMOUNT_TO_REFUND)
            .convertedAmountToRefund(UPDATED_CONVERTED_AMOUNT_TO_REFUND)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE);
        return refund;
    }

    @BeforeEach
    public void initTest() {
        refund = createEntity(em);
    }

    @Test
    @Transactional
    void createRefund() throws Exception {
        int databaseSizeBeforeCreate = refundRepository.findAll().size();
        // Create the Refund
        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isCreated());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate + 1);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testRefund.getCreateDateTimeUtc()).isEqualTo(DEFAULT_CREATE_DATE_TIME_UTC);
        assertThat(testRefund.getRefundNumber()).isEqualTo(DEFAULT_REFUND_NUMBER);
        assertThat(testRefund.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRefund.getAmountToRefund()).isEqualTo(DEFAULT_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedAmountToRefund()).isEqualTo(DEFAULT_CONVERTED_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedCurrency()).isEqualTo(DEFAULT_CONVERTED_CURRENCY);
        assertThat(testRefund.getConversionRate()).isEqualTo(DEFAULT_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void createRefundWithExistingId() throws Exception {
        // Create the Refund with an existing ID
        refund.setId(1L);

        int databaseSizeBeforeCreate = refundRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRefunds() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        // Get all the refundList
        restRefundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refund.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].createDateTimeUtc").value(hasItem(DEFAULT_CREATE_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].refundNumber").value(hasItem(DEFAULT_REFUND_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].amountToRefund").value(hasItem(DEFAULT_AMOUNT_TO_REFUND.doubleValue())))
            .andExpect(jsonPath("$.[*].convertedAmountToRefund").value(hasItem(DEFAULT_CONVERTED_AMOUNT_TO_REFUND.doubleValue())))
            .andExpect(jsonPath("$.[*].convertedCurrency").value(hasItem(DEFAULT_CONVERTED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].conversionRate").value(hasItem(DEFAULT_CONVERSION_RATE.doubleValue())));
    }

    @Test
    @Transactional
    void getRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        // Get the refund
        restRefundMockMvc
            .perform(get(ENTITY_API_URL_ID, refund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refund.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.createDateTimeUtc").value(DEFAULT_CREATE_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.refundNumber").value(DEFAULT_REFUND_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.amountToRefund").value(DEFAULT_AMOUNT_TO_REFUND.doubleValue()))
            .andExpect(jsonPath("$.convertedAmountToRefund").value(DEFAULT_CONVERTED_AMOUNT_TO_REFUND.doubleValue()))
            .andExpect(jsonPath("$.convertedCurrency").value(DEFAULT_CONVERTED_CURRENCY.toString()))
            .andExpect(jsonPath("$.conversionRate").value(DEFAULT_CONVERSION_RATE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRefund() throws Exception {
        // Get the refund
        restRefundMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund
        Refund updatedRefund = refundRepository.findById(refund.getId()).get();
        // Disconnect from session so that the updates on updatedRefund are not directly saved in db
        em.detach(updatedRefund);
        updatedRefund
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .refundNumber(UPDATED_REFUND_NUMBER)
            .status(UPDATED_STATUS)
            .amountToRefund(UPDATED_AMOUNT_TO_REFUND)
            .convertedAmountToRefund(UPDATED_CONVERTED_AMOUNT_TO_REFUND)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE);

        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRefund.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefund.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testRefund.getRefundNumber()).isEqualTo(UPDATED_REFUND_NUMBER);
        assertThat(testRefund.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRefund.getAmountToRefund()).isEqualTo(UPDATED_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedAmountToRefund()).isEqualTo(UPDATED_CONVERTED_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testRefund.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void putNonExistingRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refund.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .refundNumber(UPDATED_REFUND_NUMBER)
            .convertedAmountToRefund(UPDATED_CONVERTED_AMOUNT_TO_REFUND)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefund.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testRefund.getRefundNumber()).isEqualTo(UPDATED_REFUND_NUMBER);
        assertThat(testRefund.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRefund.getAmountToRefund()).isEqualTo(DEFAULT_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedAmountToRefund()).isEqualTo(UPDATED_CONVERTED_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testRefund.getConversionRate()).isEqualTo(DEFAULT_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void fullUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .refundNumber(UPDATED_REFUND_NUMBER)
            .status(UPDATED_STATUS)
            .amountToRefund(UPDATED_AMOUNT_TO_REFUND)
            .convertedAmountToRefund(UPDATED_CONVERTED_AMOUNT_TO_REFUND)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testRefund.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testRefund.getRefundNumber()).isEqualTo(UPDATED_REFUND_NUMBER);
        assertThat(testRefund.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRefund.getAmountToRefund()).isEqualTo(UPDATED_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedAmountToRefund()).isEqualTo(UPDATED_CONVERTED_AMOUNT_TO_REFUND);
        assertThat(testRefund.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testRefund.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
    }

    @Test
    @Transactional
    void patchNonExistingRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refund.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();
        refund.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeDelete = refundRepository.findAll().size();

        // Delete the refund
        restRefundMockMvc
            .perform(delete(ENTITY_API_URL_ID, refund.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
