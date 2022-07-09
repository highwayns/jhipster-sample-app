package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.PaymentJob;
import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.domain.enumeration.Locale;
import io.github.jhipster.sample.domain.enumeration.PaymentJobType;
import io.github.jhipster.sample.repository.PaymentJobRepository;
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
 * Integration tests for the {@link PaymentJobResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentJobResourceIT {

    private static final Long DEFAULT_REFERENCE = 1L;
    private static final Long UPDATED_REFERENCE = 2L;

    private static final Instant DEFAULT_CREATE_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PaymentJobType DEFAULT_TYPE = PaymentJobType.PAYMENT;
    private static final PaymentJobType UPDATED_TYPE = PaymentJobType.CREDIT;

    private static final Long DEFAULT_TRACE_REFERENCE = 1L;
    private static final Long UPDATED_TRACE_REFERENCE = 2L;

    private static final String DEFAULT_CONFIGURATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final Locale DEFAULT_LOCALE = Locale.EL_GR;
    private static final Locale UPDATED_LOCALE = Locale.EN_US;

    private static final String DEFAULT_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_ZONE = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_PAYMENT_JOB_REFERENCE = 1L;
    private static final Long UPDATED_PARENT_PAYMENT_JOB_REFERENCE = 2L;

    private static final String DEFAULT_DISPLAY_URL = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_URL = "BBBBBBBBBB";

    private static final Currency DEFAULT_CURRENCY = Currency.CNY;
    private static final Currency UPDATED_CURRENCY = Currency.JPY;

    private static final Double DEFAULT_AMOUNT_TO_COLLECT = 1D;
    private static final Double UPDATED_AMOUNT_TO_COLLECT = 2D;

    private static final Double DEFAULT_AMOUNT_COLLECTED = 1D;
    private static final Double UPDATED_AMOUNT_COLLECTED = 2D;

    private static final Double DEFAULT_PAID_AMOUNT = 1D;
    private static final Double UPDATED_PAID_AMOUNT = 2D;

    private static final Instant DEFAULT_PAID_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAID_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRATION_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUE_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_PROCESSED_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_PROCESSED_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/payment-jobs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentJobRepository paymentJobRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentJobMockMvc;

    private PaymentJob paymentJob;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentJob createEntity(EntityManager em) {
        PaymentJob paymentJob = new PaymentJob()
            .reference(DEFAULT_REFERENCE)
            .createDateTimeUtc(DEFAULT_CREATE_DATE_TIME_UTC)
            .type(DEFAULT_TYPE)
            .traceReference(DEFAULT_TRACE_REFERENCE)
            .configurationId(DEFAULT_CONFIGURATION_ID)
            .domain(DEFAULT_DOMAIN)
            .locale(DEFAULT_LOCALE)
            .timeZone(DEFAULT_TIME_ZONE)
            .parentPaymentJobReference(DEFAULT_PARENT_PAYMENT_JOB_REFERENCE)
            .displayUrl(DEFAULT_DISPLAY_URL)
            .currency(DEFAULT_CURRENCY)
            .amountToCollect(DEFAULT_AMOUNT_TO_COLLECT)
            .amountCollected(DEFAULT_AMOUNT_COLLECTED)
            .paidAmount(DEFAULT_PAID_AMOUNT)
            .paidDateTimeUtc(DEFAULT_PAID_DATE_TIME_UTC)
            .expirationDateTimeUtc(DEFAULT_EXPIRATION_DATE_TIME_UTC)
            .dueDateTimeUtc(DEFAULT_DUE_DATE_TIME_UTC)
            .lastUpdateTimeUtc(DEFAULT_LAST_UPDATE_TIME_UTC)
            .lastProcessedTimeUtc(DEFAULT_LAST_PROCESSED_TIME_UTC);
        return paymentJob;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentJob createUpdatedEntity(EntityManager em) {
        PaymentJob paymentJob = new PaymentJob()
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .type(UPDATED_TYPE)
            .traceReference(UPDATED_TRACE_REFERENCE)
            .configurationId(UPDATED_CONFIGURATION_ID)
            .domain(UPDATED_DOMAIN)
            .locale(UPDATED_LOCALE)
            .timeZone(UPDATED_TIME_ZONE)
            .parentPaymentJobReference(UPDATED_PARENT_PAYMENT_JOB_REFERENCE)
            .displayUrl(UPDATED_DISPLAY_URL)
            .currency(UPDATED_CURRENCY)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT)
            .amountCollected(UPDATED_AMOUNT_COLLECTED)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .paidDateTimeUtc(UPDATED_PAID_DATE_TIME_UTC)
            .expirationDateTimeUtc(UPDATED_EXPIRATION_DATE_TIME_UTC)
            .dueDateTimeUtc(UPDATED_DUE_DATE_TIME_UTC)
            .lastUpdateTimeUtc(UPDATED_LAST_UPDATE_TIME_UTC)
            .lastProcessedTimeUtc(UPDATED_LAST_PROCESSED_TIME_UTC);
        return paymentJob;
    }

    @BeforeEach
    public void initTest() {
        paymentJob = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentJob() throws Exception {
        int databaseSizeBeforeCreate = paymentJobRepository.findAll().size();
        // Create the PaymentJob
        restPaymentJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentJob)))
            .andExpect(status().isCreated());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentJob testPaymentJob = paymentJobList.get(paymentJobList.size() - 1);
        assertThat(testPaymentJob.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPaymentJob.getCreateDateTimeUtc()).isEqualTo(DEFAULT_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPaymentJob.getTraceReference()).isEqualTo(DEFAULT_TRACE_REFERENCE);
        assertThat(testPaymentJob.getConfigurationId()).isEqualTo(DEFAULT_CONFIGURATION_ID);
        assertThat(testPaymentJob.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testPaymentJob.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testPaymentJob.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testPaymentJob.getParentPaymentJobReference()).isEqualTo(DEFAULT_PARENT_PAYMENT_JOB_REFERENCE);
        assertThat(testPaymentJob.getDisplayUrl()).isEqualTo(DEFAULT_DISPLAY_URL);
        assertThat(testPaymentJob.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPaymentJob.getAmountToCollect()).isEqualTo(DEFAULT_AMOUNT_TO_COLLECT);
        assertThat(testPaymentJob.getAmountCollected()).isEqualTo(DEFAULT_AMOUNT_COLLECTED);
        assertThat(testPaymentJob.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testPaymentJob.getPaidDateTimeUtc()).isEqualTo(DEFAULT_PAID_DATE_TIME_UTC);
        assertThat(testPaymentJob.getExpirationDateTimeUtc()).isEqualTo(DEFAULT_EXPIRATION_DATE_TIME_UTC);
        assertThat(testPaymentJob.getDueDateTimeUtc()).isEqualTo(DEFAULT_DUE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getLastUpdateTimeUtc()).isEqualTo(DEFAULT_LAST_UPDATE_TIME_UTC);
        assertThat(testPaymentJob.getLastProcessedTimeUtc()).isEqualTo(DEFAULT_LAST_PROCESSED_TIME_UTC);
    }

    @Test
    @Transactional
    void createPaymentJobWithExistingId() throws Exception {
        // Create the PaymentJob with an existing ID
        paymentJob.setId(1L);

        int databaseSizeBeforeCreate = paymentJobRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentJob)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentJobs() throws Exception {
        // Initialize the database
        paymentJobRepository.saveAndFlush(paymentJob);

        // Get all the paymentJobList
        restPaymentJobMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].createDateTimeUtc").value(hasItem(DEFAULT_CREATE_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].traceReference").value(hasItem(DEFAULT_TRACE_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].configurationId").value(hasItem(DEFAULT_CONFIGURATION_ID)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE)))
            .andExpect(jsonPath("$.[*].parentPaymentJobReference").value(hasItem(DEFAULT_PARENT_PAYMENT_JOB_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].displayUrl").value(hasItem(DEFAULT_DISPLAY_URL)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].amountToCollect").value(hasItem(DEFAULT_AMOUNT_TO_COLLECT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountCollected").value(hasItem(DEFAULT_AMOUNT_COLLECTED.doubleValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidDateTimeUtc").value(hasItem(DEFAULT_PAID_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].expirationDateTimeUtc").value(hasItem(DEFAULT_EXPIRATION_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].dueDateTimeUtc").value(hasItem(DEFAULT_DUE_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateTimeUtc").value(hasItem(DEFAULT_LAST_UPDATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].lastProcessedTimeUtc").value(hasItem(DEFAULT_LAST_PROCESSED_TIME_UTC.toString())));
    }

    @Test
    @Transactional
    void getPaymentJob() throws Exception {
        // Initialize the database
        paymentJobRepository.saveAndFlush(paymentJob);

        // Get the paymentJob
        restPaymentJobMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentJob.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.createDateTimeUtc").value(DEFAULT_CREATE_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.traceReference").value(DEFAULT_TRACE_REFERENCE.intValue()))
            .andExpect(jsonPath("$.configurationId").value(DEFAULT_CONFIGURATION_ID))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE.toString()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE))
            .andExpect(jsonPath("$.parentPaymentJobReference").value(DEFAULT_PARENT_PAYMENT_JOB_REFERENCE.intValue()))
            .andExpect(jsonPath("$.displayUrl").value(DEFAULT_DISPLAY_URL))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.amountToCollect").value(DEFAULT_AMOUNT_TO_COLLECT.doubleValue()))
            .andExpect(jsonPath("$.amountCollected").value(DEFAULT_AMOUNT_COLLECTED.doubleValue()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paidDateTimeUtc").value(DEFAULT_PAID_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.expirationDateTimeUtc").value(DEFAULT_EXPIRATION_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.dueDateTimeUtc").value(DEFAULT_DUE_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.lastUpdateTimeUtc").value(DEFAULT_LAST_UPDATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.lastProcessedTimeUtc").value(DEFAULT_LAST_PROCESSED_TIME_UTC.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentJob() throws Exception {
        // Get the paymentJob
        restPaymentJobMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentJob() throws Exception {
        // Initialize the database
        paymentJobRepository.saveAndFlush(paymentJob);

        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();

        // Update the paymentJob
        PaymentJob updatedPaymentJob = paymentJobRepository.findById(paymentJob.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentJob are not directly saved in db
        em.detach(updatedPaymentJob);
        updatedPaymentJob
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .type(UPDATED_TYPE)
            .traceReference(UPDATED_TRACE_REFERENCE)
            .configurationId(UPDATED_CONFIGURATION_ID)
            .domain(UPDATED_DOMAIN)
            .locale(UPDATED_LOCALE)
            .timeZone(UPDATED_TIME_ZONE)
            .parentPaymentJobReference(UPDATED_PARENT_PAYMENT_JOB_REFERENCE)
            .displayUrl(UPDATED_DISPLAY_URL)
            .currency(UPDATED_CURRENCY)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT)
            .amountCollected(UPDATED_AMOUNT_COLLECTED)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .paidDateTimeUtc(UPDATED_PAID_DATE_TIME_UTC)
            .expirationDateTimeUtc(UPDATED_EXPIRATION_DATE_TIME_UTC)
            .dueDateTimeUtc(UPDATED_DUE_DATE_TIME_UTC)
            .lastUpdateTimeUtc(UPDATED_LAST_UPDATE_TIME_UTC)
            .lastProcessedTimeUtc(UPDATED_LAST_PROCESSED_TIME_UTC);

        restPaymentJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentJob.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentJob))
            )
            .andExpect(status().isOk());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
        PaymentJob testPaymentJob = paymentJobList.get(paymentJobList.size() - 1);
        assertThat(testPaymentJob.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPaymentJob.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPaymentJob.getTraceReference()).isEqualTo(UPDATED_TRACE_REFERENCE);
        assertThat(testPaymentJob.getConfigurationId()).isEqualTo(UPDATED_CONFIGURATION_ID);
        assertThat(testPaymentJob.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testPaymentJob.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testPaymentJob.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testPaymentJob.getParentPaymentJobReference()).isEqualTo(UPDATED_PARENT_PAYMENT_JOB_REFERENCE);
        assertThat(testPaymentJob.getDisplayUrl()).isEqualTo(UPDATED_DISPLAY_URL);
        assertThat(testPaymentJob.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPaymentJob.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
        assertThat(testPaymentJob.getAmountCollected()).isEqualTo(UPDATED_AMOUNT_COLLECTED);
        assertThat(testPaymentJob.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testPaymentJob.getPaidDateTimeUtc()).isEqualTo(UPDATED_PAID_DATE_TIME_UTC);
        assertThat(testPaymentJob.getExpirationDateTimeUtc()).isEqualTo(UPDATED_EXPIRATION_DATE_TIME_UTC);
        assertThat(testPaymentJob.getDueDateTimeUtc()).isEqualTo(UPDATED_DUE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getLastUpdateTimeUtc()).isEqualTo(UPDATED_LAST_UPDATE_TIME_UTC);
        assertThat(testPaymentJob.getLastProcessedTimeUtc()).isEqualTo(UPDATED_LAST_PROCESSED_TIME_UTC);
    }

    @Test
    @Transactional
    void putNonExistingPaymentJob() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();
        paymentJob.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentJob.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentJob))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentJob() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();
        paymentJob.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentJob))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentJob() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();
        paymentJob.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentJob)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentJobWithPatch() throws Exception {
        // Initialize the database
        paymentJobRepository.saveAndFlush(paymentJob);

        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();

        // Update the paymentJob using partial update
        PaymentJob partialUpdatedPaymentJob = new PaymentJob();
        partialUpdatedPaymentJob.setId(paymentJob.getId());

        partialUpdatedPaymentJob
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .traceReference(UPDATED_TRACE_REFERENCE)
            .domain(UPDATED_DOMAIN)
            .parentPaymentJobReference(UPDATED_PARENT_PAYMENT_JOB_REFERENCE)
            .displayUrl(UPDATED_DISPLAY_URL)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT)
            .amountCollected(UPDATED_AMOUNT_COLLECTED)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .dueDateTimeUtc(UPDATED_DUE_DATE_TIME_UTC)
            .lastProcessedTimeUtc(UPDATED_LAST_PROCESSED_TIME_UTC);

        restPaymentJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentJob))
            )
            .andExpect(status().isOk());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
        PaymentJob testPaymentJob = paymentJobList.get(paymentJobList.size() - 1);
        assertThat(testPaymentJob.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPaymentJob.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPaymentJob.getTraceReference()).isEqualTo(UPDATED_TRACE_REFERENCE);
        assertThat(testPaymentJob.getConfigurationId()).isEqualTo(DEFAULT_CONFIGURATION_ID);
        assertThat(testPaymentJob.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testPaymentJob.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testPaymentJob.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testPaymentJob.getParentPaymentJobReference()).isEqualTo(UPDATED_PARENT_PAYMENT_JOB_REFERENCE);
        assertThat(testPaymentJob.getDisplayUrl()).isEqualTo(UPDATED_DISPLAY_URL);
        assertThat(testPaymentJob.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPaymentJob.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
        assertThat(testPaymentJob.getAmountCollected()).isEqualTo(UPDATED_AMOUNT_COLLECTED);
        assertThat(testPaymentJob.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testPaymentJob.getPaidDateTimeUtc()).isEqualTo(DEFAULT_PAID_DATE_TIME_UTC);
        assertThat(testPaymentJob.getExpirationDateTimeUtc()).isEqualTo(DEFAULT_EXPIRATION_DATE_TIME_UTC);
        assertThat(testPaymentJob.getDueDateTimeUtc()).isEqualTo(UPDATED_DUE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getLastUpdateTimeUtc()).isEqualTo(DEFAULT_LAST_UPDATE_TIME_UTC);
        assertThat(testPaymentJob.getLastProcessedTimeUtc()).isEqualTo(UPDATED_LAST_PROCESSED_TIME_UTC);
    }

    @Test
    @Transactional
    void fullUpdatePaymentJobWithPatch() throws Exception {
        // Initialize the database
        paymentJobRepository.saveAndFlush(paymentJob);

        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();

        // Update the paymentJob using partial update
        PaymentJob partialUpdatedPaymentJob = new PaymentJob();
        partialUpdatedPaymentJob.setId(paymentJob.getId());

        partialUpdatedPaymentJob
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .type(UPDATED_TYPE)
            .traceReference(UPDATED_TRACE_REFERENCE)
            .configurationId(UPDATED_CONFIGURATION_ID)
            .domain(UPDATED_DOMAIN)
            .locale(UPDATED_LOCALE)
            .timeZone(UPDATED_TIME_ZONE)
            .parentPaymentJobReference(UPDATED_PARENT_PAYMENT_JOB_REFERENCE)
            .displayUrl(UPDATED_DISPLAY_URL)
            .currency(UPDATED_CURRENCY)
            .amountToCollect(UPDATED_AMOUNT_TO_COLLECT)
            .amountCollected(UPDATED_AMOUNT_COLLECTED)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .paidDateTimeUtc(UPDATED_PAID_DATE_TIME_UTC)
            .expirationDateTimeUtc(UPDATED_EXPIRATION_DATE_TIME_UTC)
            .dueDateTimeUtc(UPDATED_DUE_DATE_TIME_UTC)
            .lastUpdateTimeUtc(UPDATED_LAST_UPDATE_TIME_UTC)
            .lastProcessedTimeUtc(UPDATED_LAST_PROCESSED_TIME_UTC);

        restPaymentJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentJob))
            )
            .andExpect(status().isOk());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
        PaymentJob testPaymentJob = paymentJobList.get(paymentJobList.size() - 1);
        assertThat(testPaymentJob.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPaymentJob.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPaymentJob.getTraceReference()).isEqualTo(UPDATED_TRACE_REFERENCE);
        assertThat(testPaymentJob.getConfigurationId()).isEqualTo(UPDATED_CONFIGURATION_ID);
        assertThat(testPaymentJob.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testPaymentJob.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testPaymentJob.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testPaymentJob.getParentPaymentJobReference()).isEqualTo(UPDATED_PARENT_PAYMENT_JOB_REFERENCE);
        assertThat(testPaymentJob.getDisplayUrl()).isEqualTo(UPDATED_DISPLAY_URL);
        assertThat(testPaymentJob.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPaymentJob.getAmountToCollect()).isEqualTo(UPDATED_AMOUNT_TO_COLLECT);
        assertThat(testPaymentJob.getAmountCollected()).isEqualTo(UPDATED_AMOUNT_COLLECTED);
        assertThat(testPaymentJob.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testPaymentJob.getPaidDateTimeUtc()).isEqualTo(UPDATED_PAID_DATE_TIME_UTC);
        assertThat(testPaymentJob.getExpirationDateTimeUtc()).isEqualTo(UPDATED_EXPIRATION_DATE_TIME_UTC);
        assertThat(testPaymentJob.getDueDateTimeUtc()).isEqualTo(UPDATED_DUE_DATE_TIME_UTC);
        assertThat(testPaymentJob.getLastUpdateTimeUtc()).isEqualTo(UPDATED_LAST_UPDATE_TIME_UTC);
        assertThat(testPaymentJob.getLastProcessedTimeUtc()).isEqualTo(UPDATED_LAST_PROCESSED_TIME_UTC);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentJob() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();
        paymentJob.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentJob))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentJob() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();
        paymentJob.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentJob))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentJob() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobRepository.findAll().size();
        paymentJob.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentJob))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentJob in the database
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentJob() throws Exception {
        // Initialize the database
        paymentJobRepository.saveAndFlush(paymentJob);

        int databaseSizeBeforeDelete = paymentJobRepository.findAll().size();

        // Delete the paymentJob
        restPaymentJobMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentJob.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentJob> paymentJobList = paymentJobRepository.findAll();
        assertThat(paymentJobList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
