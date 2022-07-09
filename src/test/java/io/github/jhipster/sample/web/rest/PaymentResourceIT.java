package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Payment;
import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.domain.enumeration.PaymentStatus;
import io.github.jhipster.sample.repository.PaymentRepository;
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
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentResourceIT {

    private static final Long DEFAULT_REFERENCE = 1L;
    private static final Long UPDATED_REFERENCE = 2L;

    private static final Instant DEFAULT_CREATE_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PaymentStatus DEFAULT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_STATUS = PaymentStatus.RESERVED;

    private static final Double DEFAULT_AMOUNT_TO_COLLECT = 1D;
    private static final Double UPDATED_AMOUNT_TO_COLLECT = 2D;

    private static final Double DEFAULT_SURCHARGE_AMOUNT = 1D;
    private static final Double UPDATED_SURCHARGE_AMOUNT = 2D;

    private static final Double DEFAULT_CONVERTED_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_CONVERTED_TOTAL_AMOUNT = 2D;

    private static final Currency DEFAULT_CONVERTED_CURRENCY = Currency.CNY;
    private static final Currency UPDATED_CONVERTED_CURRENCY = Currency.JPY;

    private static final Double DEFAULT_CONVERSION_RATE = 1D;
    private static final Double UPDATED_CONVERSION_RATE = 2D;

    private static final Double DEFAULT_PAID_AMOUNT = 1D;
    private static final Double UPDATED_PAID_AMOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .reference(DEFAULT_REFERENCE)
            .createDateTimeUtc(DEFAULT_CREATE_DATE_TIME_UTC)
            .status(DEFAULT_STATUS)
            .amountToCollect(DEFAULT_AMOUNT_TO_COLLECT)
            .surchargeAmount(DEFAULT_SURCHARGE_AMOUNT)
            .convertedTotalAmount(DEFAULT_CONVERTED_TOTAL_AMOUNT)
            .convertedCurrency(DEFAULT_CONVERTED_CURRENCY)
            .conversionRate(DEFAULT_CONVERSION_RATE)
            .paidAmount(DEFAULT_PAID_AMOUNT);
        return payment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .status(UPDATED_STATUS)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT)
            .surchargeAmount(UPDATED_SURCHARGE_AMOUNT)
            .convertedTotalAmount(UPDATED_CONVERTED_TOTAL_AMOUNT)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .paidAmount(UPDATED_PAID_AMOUNT);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPayment.getCreateDateTimeUtc()).isEqualTo(DEFAULT_CREATE_DATE_TIME_UTC);
        assertThat(testPayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayment.getAmountToCollect()).isEqualTo(DEFAULT_AMOUNT_TO_COLLECT);
        assertThat(testPayment.getSurchargeAmount()).isEqualTo(DEFAULT_SURCHARGE_AMOUNT);
        assertThat(testPayment.getConvertedTotalAmount()).isEqualTo(DEFAULT_CONVERTED_TOTAL_AMOUNT);
        assertThat(testPayment.getConvertedCurrency()).isEqualTo(DEFAULT_CONVERTED_CURRENCY);
        assertThat(testPayment.getConversionRate()).isEqualTo(DEFAULT_CONVERSION_RATE);
        assertThat(testPayment.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
    }

    @Test
    @Transactional
    void createPaymentWithExistingId() throws Exception {
        // Create the Payment with an existing ID
        payment.setId(1L);

        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].createDateTimeUtc").value(hasItem(DEFAULT_CREATE_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].amountToCollect").value(hasItem(DEFAULT_AMOUNT_TO_COLLECT.doubleValue())))
            .andExpect(jsonPath("$.[*].surchargeAmount").value(hasItem(DEFAULT_SURCHARGE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].convertedTotalAmount").value(hasItem(DEFAULT_CONVERTED_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].convertedCurrency").value(hasItem(DEFAULT_CONVERTED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].conversionRate").value(hasItem(DEFAULT_CONVERSION_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.createDateTimeUtc").value(DEFAULT_CREATE_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.amountToCollect").value(DEFAULT_AMOUNT_TO_COLLECT.doubleValue()))
            .andExpect(jsonPath("$.surchargeAmount").value(DEFAULT_SURCHARGE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.convertedTotalAmount").value(DEFAULT_CONVERTED_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.convertedCurrency").value(DEFAULT_CONVERTED_CURRENCY.toString()))
            .andExpect(jsonPath("$.conversionRate").value(DEFAULT_CONVERSION_RATE.doubleValue()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .status(UPDATED_STATUS)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT)
            .surchargeAmount(UPDATED_SURCHARGE_AMOUNT)
            .convertedTotalAmount(UPDATED_CONVERTED_TOTAL_AMOUNT)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .paidAmount(UPDATED_PAID_AMOUNT);

        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPayment.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
        assertThat(testPayment.getSurchargeAmount()).isEqualTo(UPDATED_SURCHARGE_AMOUNT);
        assertThat(testPayment.getConvertedTotalAmount()).isEqualTo(UPDATED_CONVERTED_TOTAL_AMOUNT);
        assertThat(testPayment.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testPayment.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
        assertThat(testPayment.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .surchargeAmount(UPDATED_SURCHARGE_AMOUNT)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPayment.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayment.getAmountToCollect()).isEqualTo(DEFAULT_AMOUNT_TO_COLLECT);
        assertThat(testPayment.getSurchargeAmount()).isEqualTo(UPDATED_SURCHARGE_AMOUNT);
        assertThat(testPayment.getConvertedTotalAmount()).isEqualTo(DEFAULT_CONVERTED_TOTAL_AMOUNT);
        assertThat(testPayment.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testPayment.getConversionRate()).isEqualTo(DEFAULT_CONVERSION_RATE);
        assertThat(testPayment.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .status(UPDATED_STATUS)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT)
            .surchargeAmount(UPDATED_SURCHARGE_AMOUNT)
            .convertedTotalAmount(UPDATED_CONVERTED_TOTAL_AMOUNT)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .paidAmount(UPDATED_PAID_AMOUNT);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPayment.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
        assertThat(testPayment.getSurchargeAmount()).isEqualTo(UPDATED_SURCHARGE_AMOUNT);
        assertThat(testPayment.getConvertedTotalAmount()).isEqualTo(UPDATED_CONVERTED_TOTAL_AMOUNT);
        assertThat(testPayment.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testPayment.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
        assertThat(testPayment.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, payment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
