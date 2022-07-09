package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.PaymentAttributes;
import io.github.jhipster.sample.domain.enumeration.PaymentStatus;
import io.github.jhipster.sample.repository.PaymentAttributesRepository;
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
 * Integration tests for the {@link PaymentAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentAttributesResourceIT {

    private static final String DEFAULT_ORIGINATING_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATING_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final String DEFAULT_RETURN_URL_SUCCESS = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_URL_SUCCESS = "BBBBBBBBBB";

    private static final String DEFAULT_RETURN_URL_FAILED = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_URL_FAILED = "BBBBBBBBBB";

    private static final String DEFAULT_RETURN_URL_CANCELLED = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_URL_CANCELLED = "BBBBBBBBBB";

    private static final String DEFAULT_SIMULATED_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_SIMULATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_IDEAL_BIC = "AAAAAAAAAA";
    private static final String UPDATED_IDEAL_BIC = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD_VOID_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_CASH_FLOWS_ACQUIRING_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CASH_FLOWS_ACQUIRING_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTOR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTOR = "BBBBBBBBBB";

    private static final String DEFAULT_EWALLET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EWALLET_TYPE = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.RESERVED;

    private static final String ENTITY_API_URL = "/api/payment-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentAttributesRepository paymentAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentAttributesMockMvc;

    private PaymentAttributes paymentAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAttributes createEntity(EntityManager em) {
        PaymentAttributes paymentAttributes = new PaymentAttributes()
            .originatingIpAddress(DEFAULT_ORIGINATING_IP_ADDRESS)
            .originHeader(DEFAULT_ORIGIN_HEADER)
            .userAgent(DEFAULT_USER_AGENT)
            .returnUrlSuccess(DEFAULT_RETURN_URL_SUCCESS)
            .returnUrlFailed(DEFAULT_RETURN_URL_FAILED)
            .returnUrlCancelled(DEFAULT_RETURN_URL_CANCELLED)
            .simulatedStatus(DEFAULT_SIMULATED_STATUS)
            .idealBic(DEFAULT_IDEAL_BIC)
            .paymentMethodTransactionId(DEFAULT_PAYMENT_METHOD_TRANSACTION_ID)
            .paymentMethodVoidTransactionId(DEFAULT_PAYMENT_METHOD_VOID_TRANSACTION_ID)
            .token(DEFAULT_TOKEN)
            .cashFlowsAcquiringDetails(DEFAULT_CASH_FLOWS_ACQUIRING_DETAILS)
            .descriptor(DEFAULT_DESCRIPTOR)
            .ewalletType(DEFAULT_EWALLET_TYPE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return paymentAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAttributes createUpdatedEntity(EntityManager em) {
        PaymentAttributes paymentAttributes = new PaymentAttributes()
            .originatingIpAddress(UPDATED_ORIGINATING_IP_ADDRESS)
            .originHeader(UPDATED_ORIGIN_HEADER)
            .userAgent(UPDATED_USER_AGENT)
            .returnUrlSuccess(UPDATED_RETURN_URL_SUCCESS)
            .returnUrlFailed(UPDATED_RETURN_URL_FAILED)
            .returnUrlCancelled(UPDATED_RETURN_URL_CANCELLED)
            .simulatedStatus(UPDATED_SIMULATED_STATUS)
            .idealBic(UPDATED_IDEAL_BIC)
            .paymentMethodTransactionId(UPDATED_PAYMENT_METHOD_TRANSACTION_ID)
            .paymentMethodVoidTransactionId(UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID)
            .token(UPDATED_TOKEN)
            .cashFlowsAcquiringDetails(UPDATED_CASH_FLOWS_ACQUIRING_DETAILS)
            .descriptor(UPDATED_DESCRIPTOR)
            .ewalletType(UPDATED_EWALLET_TYPE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        return paymentAttributes;
    }

    @BeforeEach
    public void initTest() {
        paymentAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentAttributes() throws Exception {
        int databaseSizeBeforeCreate = paymentAttributesRepository.findAll().size();
        // Create the PaymentAttributes
        restPaymentAttributesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentAttributes testPaymentAttributes = paymentAttributesList.get(paymentAttributesList.size() - 1);
        assertThat(testPaymentAttributes.getOriginatingIpAddress()).isEqualTo(DEFAULT_ORIGINATING_IP_ADDRESS);
        assertThat(testPaymentAttributes.getOriginHeader()).isEqualTo(DEFAULT_ORIGIN_HEADER);
        assertThat(testPaymentAttributes.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
        assertThat(testPaymentAttributes.getReturnUrlSuccess()).isEqualTo(DEFAULT_RETURN_URL_SUCCESS);
        assertThat(testPaymentAttributes.getReturnUrlFailed()).isEqualTo(DEFAULT_RETURN_URL_FAILED);
        assertThat(testPaymentAttributes.getReturnUrlCancelled()).isEqualTo(DEFAULT_RETURN_URL_CANCELLED);
        assertThat(testPaymentAttributes.getSimulatedStatus()).isEqualTo(DEFAULT_SIMULATED_STATUS);
        assertThat(testPaymentAttributes.getIdealBic()).isEqualTo(DEFAULT_IDEAL_BIC);
        assertThat(testPaymentAttributes.getPaymentMethodTransactionId()).isEqualTo(DEFAULT_PAYMENT_METHOD_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getPaymentMethodVoidTransactionId()).isEqualTo(DEFAULT_PAYMENT_METHOD_VOID_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testPaymentAttributes.getCashFlowsAcquiringDetails()).isEqualTo(DEFAULT_CASH_FLOWS_ACQUIRING_DETAILS);
        assertThat(testPaymentAttributes.getDescriptor()).isEqualTo(DEFAULT_DESCRIPTOR);
        assertThat(testPaymentAttributes.getEwalletType()).isEqualTo(DEFAULT_EWALLET_TYPE);
        assertThat(testPaymentAttributes.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void createPaymentAttributesWithExistingId() throws Exception {
        // Create the PaymentAttributes with an existing ID
        paymentAttributes.setId(1L);

        int databaseSizeBeforeCreate = paymentAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentAttributesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentAttributes() throws Exception {
        // Initialize the database
        paymentAttributesRepository.saveAndFlush(paymentAttributes);

        // Get all the paymentAttributesList
        restPaymentAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].originatingIpAddress").value(hasItem(DEFAULT_ORIGINATING_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].originHeader").value(hasItem(DEFAULT_ORIGIN_HEADER)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)))
            .andExpect(jsonPath("$.[*].returnUrlSuccess").value(hasItem(DEFAULT_RETURN_URL_SUCCESS)))
            .andExpect(jsonPath("$.[*].returnUrlFailed").value(hasItem(DEFAULT_RETURN_URL_FAILED)))
            .andExpect(jsonPath("$.[*].returnUrlCancelled").value(hasItem(DEFAULT_RETURN_URL_CANCELLED)))
            .andExpect(jsonPath("$.[*].simulatedStatus").value(hasItem(DEFAULT_SIMULATED_STATUS)))
            .andExpect(jsonPath("$.[*].idealBic").value(hasItem(DEFAULT_IDEAL_BIC)))
            .andExpect(jsonPath("$.[*].paymentMethodTransactionId").value(hasItem(DEFAULT_PAYMENT_METHOD_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].paymentMethodVoidTransactionId").value(hasItem(DEFAULT_PAYMENT_METHOD_VOID_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].cashFlowsAcquiringDetails").value(hasItem(DEFAULT_CASH_FLOWS_ACQUIRING_DETAILS)))
            .andExpect(jsonPath("$.[*].descriptor").value(hasItem(DEFAULT_DESCRIPTOR)))
            .andExpect(jsonPath("$.[*].ewalletType").value(hasItem(DEFAULT_EWALLET_TYPE)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPaymentAttributes() throws Exception {
        // Initialize the database
        paymentAttributesRepository.saveAndFlush(paymentAttributes);

        // Get the paymentAttributes
        restPaymentAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentAttributes.getId().intValue()))
            .andExpect(jsonPath("$.originatingIpAddress").value(DEFAULT_ORIGINATING_IP_ADDRESS))
            .andExpect(jsonPath("$.originHeader").value(DEFAULT_ORIGIN_HEADER))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT))
            .andExpect(jsonPath("$.returnUrlSuccess").value(DEFAULT_RETURN_URL_SUCCESS))
            .andExpect(jsonPath("$.returnUrlFailed").value(DEFAULT_RETURN_URL_FAILED))
            .andExpect(jsonPath("$.returnUrlCancelled").value(DEFAULT_RETURN_URL_CANCELLED))
            .andExpect(jsonPath("$.simulatedStatus").value(DEFAULT_SIMULATED_STATUS))
            .andExpect(jsonPath("$.idealBic").value(DEFAULT_IDEAL_BIC))
            .andExpect(jsonPath("$.paymentMethodTransactionId").value(DEFAULT_PAYMENT_METHOD_TRANSACTION_ID))
            .andExpect(jsonPath("$.paymentMethodVoidTransactionId").value(DEFAULT_PAYMENT_METHOD_VOID_TRANSACTION_ID))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.cashFlowsAcquiringDetails").value(DEFAULT_CASH_FLOWS_ACQUIRING_DETAILS))
            .andExpect(jsonPath("$.descriptor").value(DEFAULT_DESCRIPTOR))
            .andExpect(jsonPath("$.ewalletType").value(DEFAULT_EWALLET_TYPE))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentAttributes() throws Exception {
        // Get the paymentAttributes
        restPaymentAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentAttributes() throws Exception {
        // Initialize the database
        paymentAttributesRepository.saveAndFlush(paymentAttributes);

        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();

        // Update the paymentAttributes
        PaymentAttributes updatedPaymentAttributes = paymentAttributesRepository.findById(paymentAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentAttributes are not directly saved in db
        em.detach(updatedPaymentAttributes);
        updatedPaymentAttributes
            .originatingIpAddress(UPDATED_ORIGINATING_IP_ADDRESS)
            .originHeader(UPDATED_ORIGIN_HEADER)
            .userAgent(UPDATED_USER_AGENT)
            .returnUrlSuccess(UPDATED_RETURN_URL_SUCCESS)
            .returnUrlFailed(UPDATED_RETURN_URL_FAILED)
            .returnUrlCancelled(UPDATED_RETURN_URL_CANCELLED)
            .simulatedStatus(UPDATED_SIMULATED_STATUS)
            .idealBic(UPDATED_IDEAL_BIC)
            .paymentMethodTransactionId(UPDATED_PAYMENT_METHOD_TRANSACTION_ID)
            .paymentMethodVoidTransactionId(UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID)
            .token(UPDATED_TOKEN)
            .cashFlowsAcquiringDetails(UPDATED_CASH_FLOWS_ACQUIRING_DETAILS)
            .descriptor(UPDATED_DESCRIPTOR)
            .ewalletType(UPDATED_EWALLET_TYPE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restPaymentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
        PaymentAttributes testPaymentAttributes = paymentAttributesList.get(paymentAttributesList.size() - 1);
        assertThat(testPaymentAttributes.getOriginatingIpAddress()).isEqualTo(UPDATED_ORIGINATING_IP_ADDRESS);
        assertThat(testPaymentAttributes.getOriginHeader()).isEqualTo(UPDATED_ORIGIN_HEADER);
        assertThat(testPaymentAttributes.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
        assertThat(testPaymentAttributes.getReturnUrlSuccess()).isEqualTo(UPDATED_RETURN_URL_SUCCESS);
        assertThat(testPaymentAttributes.getReturnUrlFailed()).isEqualTo(UPDATED_RETURN_URL_FAILED);
        assertThat(testPaymentAttributes.getReturnUrlCancelled()).isEqualTo(UPDATED_RETURN_URL_CANCELLED);
        assertThat(testPaymentAttributes.getSimulatedStatus()).isEqualTo(UPDATED_SIMULATED_STATUS);
        assertThat(testPaymentAttributes.getIdealBic()).isEqualTo(UPDATED_IDEAL_BIC);
        assertThat(testPaymentAttributes.getPaymentMethodTransactionId()).isEqualTo(UPDATED_PAYMENT_METHOD_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getPaymentMethodVoidTransactionId()).isEqualTo(UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testPaymentAttributes.getCashFlowsAcquiringDetails()).isEqualTo(UPDATED_CASH_FLOWS_ACQUIRING_DETAILS);
        assertThat(testPaymentAttributes.getDescriptor()).isEqualTo(UPDATED_DESCRIPTOR);
        assertThat(testPaymentAttributes.getEwalletType()).isEqualTo(UPDATED_EWALLET_TYPE);
        assertThat(testPaymentAttributes.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPaymentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();
        paymentAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();
        paymentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();
        paymentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentAttributesWithPatch() throws Exception {
        // Initialize the database
        paymentAttributesRepository.saveAndFlush(paymentAttributes);

        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();

        // Update the paymentAttributes using partial update
        PaymentAttributes partialUpdatedPaymentAttributes = new PaymentAttributes();
        partialUpdatedPaymentAttributes.setId(paymentAttributes.getId());

        partialUpdatedPaymentAttributes.paymentMethodVoidTransactionId(UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID).token(UPDATED_TOKEN);

        restPaymentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
        PaymentAttributes testPaymentAttributes = paymentAttributesList.get(paymentAttributesList.size() - 1);
        assertThat(testPaymentAttributes.getOriginatingIpAddress()).isEqualTo(DEFAULT_ORIGINATING_IP_ADDRESS);
        assertThat(testPaymentAttributes.getOriginHeader()).isEqualTo(DEFAULT_ORIGIN_HEADER);
        assertThat(testPaymentAttributes.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
        assertThat(testPaymentAttributes.getReturnUrlSuccess()).isEqualTo(DEFAULT_RETURN_URL_SUCCESS);
        assertThat(testPaymentAttributes.getReturnUrlFailed()).isEqualTo(DEFAULT_RETURN_URL_FAILED);
        assertThat(testPaymentAttributes.getReturnUrlCancelled()).isEqualTo(DEFAULT_RETURN_URL_CANCELLED);
        assertThat(testPaymentAttributes.getSimulatedStatus()).isEqualTo(DEFAULT_SIMULATED_STATUS);
        assertThat(testPaymentAttributes.getIdealBic()).isEqualTo(DEFAULT_IDEAL_BIC);
        assertThat(testPaymentAttributes.getPaymentMethodTransactionId()).isEqualTo(DEFAULT_PAYMENT_METHOD_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getPaymentMethodVoidTransactionId()).isEqualTo(UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testPaymentAttributes.getCashFlowsAcquiringDetails()).isEqualTo(DEFAULT_CASH_FLOWS_ACQUIRING_DETAILS);
        assertThat(testPaymentAttributes.getDescriptor()).isEqualTo(DEFAULT_DESCRIPTOR);
        assertThat(testPaymentAttributes.getEwalletType()).isEqualTo(DEFAULT_EWALLET_TYPE);
        assertThat(testPaymentAttributes.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePaymentAttributesWithPatch() throws Exception {
        // Initialize the database
        paymentAttributesRepository.saveAndFlush(paymentAttributes);

        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();

        // Update the paymentAttributes using partial update
        PaymentAttributes partialUpdatedPaymentAttributes = new PaymentAttributes();
        partialUpdatedPaymentAttributes.setId(paymentAttributes.getId());

        partialUpdatedPaymentAttributes
            .originatingIpAddress(UPDATED_ORIGINATING_IP_ADDRESS)
            .originHeader(UPDATED_ORIGIN_HEADER)
            .userAgent(UPDATED_USER_AGENT)
            .returnUrlSuccess(UPDATED_RETURN_URL_SUCCESS)
            .returnUrlFailed(UPDATED_RETURN_URL_FAILED)
            .returnUrlCancelled(UPDATED_RETURN_URL_CANCELLED)
            .simulatedStatus(UPDATED_SIMULATED_STATUS)
            .idealBic(UPDATED_IDEAL_BIC)
            .paymentMethodTransactionId(UPDATED_PAYMENT_METHOD_TRANSACTION_ID)
            .paymentMethodVoidTransactionId(UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID)
            .token(UPDATED_TOKEN)
            .cashFlowsAcquiringDetails(UPDATED_CASH_FLOWS_ACQUIRING_DETAILS)
            .descriptor(UPDATED_DESCRIPTOR)
            .ewalletType(UPDATED_EWALLET_TYPE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restPaymentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
        PaymentAttributes testPaymentAttributes = paymentAttributesList.get(paymentAttributesList.size() - 1);
        assertThat(testPaymentAttributes.getOriginatingIpAddress()).isEqualTo(UPDATED_ORIGINATING_IP_ADDRESS);
        assertThat(testPaymentAttributes.getOriginHeader()).isEqualTo(UPDATED_ORIGIN_HEADER);
        assertThat(testPaymentAttributes.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
        assertThat(testPaymentAttributes.getReturnUrlSuccess()).isEqualTo(UPDATED_RETURN_URL_SUCCESS);
        assertThat(testPaymentAttributes.getReturnUrlFailed()).isEqualTo(UPDATED_RETURN_URL_FAILED);
        assertThat(testPaymentAttributes.getReturnUrlCancelled()).isEqualTo(UPDATED_RETURN_URL_CANCELLED);
        assertThat(testPaymentAttributes.getSimulatedStatus()).isEqualTo(UPDATED_SIMULATED_STATUS);
        assertThat(testPaymentAttributes.getIdealBic()).isEqualTo(UPDATED_IDEAL_BIC);
        assertThat(testPaymentAttributes.getPaymentMethodTransactionId()).isEqualTo(UPDATED_PAYMENT_METHOD_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getPaymentMethodVoidTransactionId()).isEqualTo(UPDATED_PAYMENT_METHOD_VOID_TRANSACTION_ID);
        assertThat(testPaymentAttributes.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testPaymentAttributes.getCashFlowsAcquiringDetails()).isEqualTo(UPDATED_CASH_FLOWS_ACQUIRING_DETAILS);
        assertThat(testPaymentAttributes.getDescriptor()).isEqualTo(UPDATED_DESCRIPTOR);
        assertThat(testPaymentAttributes.getEwalletType()).isEqualTo(UPDATED_EWALLET_TYPE);
        assertThat(testPaymentAttributes.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();
        paymentAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();
        paymentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentAttributesRepository.findAll().size();
        paymentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentAttributes in the database
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentAttributes() throws Exception {
        // Initialize the database
        paymentAttributesRepository.saveAndFlush(paymentAttributes);

        int databaseSizeBeforeDelete = paymentAttributesRepository.findAll().size();

        // Delete the paymentAttributes
        restPaymentAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentAttributes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentAttributes> paymentAttributesList = paymentAttributesRepository.findAll();
        assertThat(paymentAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
