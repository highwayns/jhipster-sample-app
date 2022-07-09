package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.PaymentJobAttributes;
import io.github.jhipster.sample.repository.PaymentJobAttributesRepository;
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
 * Integration tests for the {@link PaymentJobAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentJobAttributesResourceIT {

    private static final String DEFAULT_WEBHOOK_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEBHOOK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_ANALYTICS_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_ANALYTICS_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ALLOWED_PARENT_FRAME_DOMAINS = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_PARENT_FRAME_DOMAINS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_PAGE_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_PAGE_REFERENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-job-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentJobAttributesRepository paymentJobAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentJobAttributesMockMvc;

    private PaymentJobAttributes paymentJobAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentJobAttributes createEntity(EntityManager em) {
        PaymentJobAttributes paymentJobAttributes = new PaymentJobAttributes()
            .webhookUrl(DEFAULT_WEBHOOK_URL)
            .googleAnalyticsClientId(DEFAULT_GOOGLE_ANALYTICS_CLIENT_ID)
            .allowedParentFrameDomains(DEFAULT_ALLOWED_PARENT_FRAME_DOMAINS)
            .paymentPageReference(DEFAULT_PAYMENT_PAGE_REFERENCE);
        return paymentJobAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentJobAttributes createUpdatedEntity(EntityManager em) {
        PaymentJobAttributes paymentJobAttributes = new PaymentJobAttributes()
            .webhookUrl(UPDATED_WEBHOOK_URL)
            .googleAnalyticsClientId(UPDATED_GOOGLE_ANALYTICS_CLIENT_ID)
            .allowedParentFrameDomains(UPDATED_ALLOWED_PARENT_FRAME_DOMAINS)
            .paymentPageReference(UPDATED_PAYMENT_PAGE_REFERENCE);
        return paymentJobAttributes;
    }

    @BeforeEach
    public void initTest() {
        paymentJobAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentJobAttributes() throws Exception {
        int databaseSizeBeforeCreate = paymentJobAttributesRepository.findAll().size();
        // Create the PaymentJobAttributes
        restPaymentJobAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentJobAttributes testPaymentJobAttributes = paymentJobAttributesList.get(paymentJobAttributesList.size() - 1);
        assertThat(testPaymentJobAttributes.getWebhookUrl()).isEqualTo(DEFAULT_WEBHOOK_URL);
        assertThat(testPaymentJobAttributes.getGoogleAnalyticsClientId()).isEqualTo(DEFAULT_GOOGLE_ANALYTICS_CLIENT_ID);
        assertThat(testPaymentJobAttributes.getAllowedParentFrameDomains()).isEqualTo(DEFAULT_ALLOWED_PARENT_FRAME_DOMAINS);
        assertThat(testPaymentJobAttributes.getPaymentPageReference()).isEqualTo(DEFAULT_PAYMENT_PAGE_REFERENCE);
    }

    @Test
    @Transactional
    void createPaymentJobAttributesWithExistingId() throws Exception {
        // Create the PaymentJobAttributes with an existing ID
        paymentJobAttributes.setId(1L);

        int databaseSizeBeforeCreate = paymentJobAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentJobAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentJobAttributes() throws Exception {
        // Initialize the database
        paymentJobAttributesRepository.saveAndFlush(paymentJobAttributes);

        // Get all the paymentJobAttributesList
        restPaymentJobAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentJobAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].webhookUrl").value(hasItem(DEFAULT_WEBHOOK_URL)))
            .andExpect(jsonPath("$.[*].googleAnalyticsClientId").value(hasItem(DEFAULT_GOOGLE_ANALYTICS_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].allowedParentFrameDomains").value(hasItem(DEFAULT_ALLOWED_PARENT_FRAME_DOMAINS)))
            .andExpect(jsonPath("$.[*].paymentPageReference").value(hasItem(DEFAULT_PAYMENT_PAGE_REFERENCE)));
    }

    @Test
    @Transactional
    void getPaymentJobAttributes() throws Exception {
        // Initialize the database
        paymentJobAttributesRepository.saveAndFlush(paymentJobAttributes);

        // Get the paymentJobAttributes
        restPaymentJobAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentJobAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentJobAttributes.getId().intValue()))
            .andExpect(jsonPath("$.webhookUrl").value(DEFAULT_WEBHOOK_URL))
            .andExpect(jsonPath("$.googleAnalyticsClientId").value(DEFAULT_GOOGLE_ANALYTICS_CLIENT_ID))
            .andExpect(jsonPath("$.allowedParentFrameDomains").value(DEFAULT_ALLOWED_PARENT_FRAME_DOMAINS))
            .andExpect(jsonPath("$.paymentPageReference").value(DEFAULT_PAYMENT_PAGE_REFERENCE));
    }

    @Test
    @Transactional
    void getNonExistingPaymentJobAttributes() throws Exception {
        // Get the paymentJobAttributes
        restPaymentJobAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentJobAttributes() throws Exception {
        // Initialize the database
        paymentJobAttributesRepository.saveAndFlush(paymentJobAttributes);

        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();

        // Update the paymentJobAttributes
        PaymentJobAttributes updatedPaymentJobAttributes = paymentJobAttributesRepository.findById(paymentJobAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentJobAttributes are not directly saved in db
        em.detach(updatedPaymentJobAttributes);
        updatedPaymentJobAttributes
            .webhookUrl(UPDATED_WEBHOOK_URL)
            .googleAnalyticsClientId(UPDATED_GOOGLE_ANALYTICS_CLIENT_ID)
            .allowedParentFrameDomains(UPDATED_ALLOWED_PARENT_FRAME_DOMAINS)
            .paymentPageReference(UPDATED_PAYMENT_PAGE_REFERENCE);

        restPaymentJobAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentJobAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentJobAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
        PaymentJobAttributes testPaymentJobAttributes = paymentJobAttributesList.get(paymentJobAttributesList.size() - 1);
        assertThat(testPaymentJobAttributes.getWebhookUrl()).isEqualTo(UPDATED_WEBHOOK_URL);
        assertThat(testPaymentJobAttributes.getGoogleAnalyticsClientId()).isEqualTo(UPDATED_GOOGLE_ANALYTICS_CLIENT_ID);
        assertThat(testPaymentJobAttributes.getAllowedParentFrameDomains()).isEqualTo(UPDATED_ALLOWED_PARENT_FRAME_DOMAINS);
        assertThat(testPaymentJobAttributes.getPaymentPageReference()).isEqualTo(UPDATED_PAYMENT_PAGE_REFERENCE);
    }

    @Test
    @Transactional
    void putNonExistingPaymentJobAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();
        paymentJobAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentJobAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentJobAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentJobAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();
        paymentJobAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentJobAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();
        paymentJobAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobAttributesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentJobAttributesWithPatch() throws Exception {
        // Initialize the database
        paymentJobAttributesRepository.saveAndFlush(paymentJobAttributes);

        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();

        // Update the paymentJobAttributes using partial update
        PaymentJobAttributes partialUpdatedPaymentJobAttributes = new PaymentJobAttributes();
        partialUpdatedPaymentJobAttributes.setId(paymentJobAttributes.getId());

        partialUpdatedPaymentJobAttributes
            .webhookUrl(UPDATED_WEBHOOK_URL)
            .googleAnalyticsClientId(UPDATED_GOOGLE_ANALYTICS_CLIENT_ID)
            .allowedParentFrameDomains(UPDATED_ALLOWED_PARENT_FRAME_DOMAINS);

        restPaymentJobAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentJobAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentJobAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
        PaymentJobAttributes testPaymentJobAttributes = paymentJobAttributesList.get(paymentJobAttributesList.size() - 1);
        assertThat(testPaymentJobAttributes.getWebhookUrl()).isEqualTo(UPDATED_WEBHOOK_URL);
        assertThat(testPaymentJobAttributes.getGoogleAnalyticsClientId()).isEqualTo(UPDATED_GOOGLE_ANALYTICS_CLIENT_ID);
        assertThat(testPaymentJobAttributes.getAllowedParentFrameDomains()).isEqualTo(UPDATED_ALLOWED_PARENT_FRAME_DOMAINS);
        assertThat(testPaymentJobAttributes.getPaymentPageReference()).isEqualTo(DEFAULT_PAYMENT_PAGE_REFERENCE);
    }

    @Test
    @Transactional
    void fullUpdatePaymentJobAttributesWithPatch() throws Exception {
        // Initialize the database
        paymentJobAttributesRepository.saveAndFlush(paymentJobAttributes);

        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();

        // Update the paymentJobAttributes using partial update
        PaymentJobAttributes partialUpdatedPaymentJobAttributes = new PaymentJobAttributes();
        partialUpdatedPaymentJobAttributes.setId(paymentJobAttributes.getId());

        partialUpdatedPaymentJobAttributes
            .webhookUrl(UPDATED_WEBHOOK_URL)
            .googleAnalyticsClientId(UPDATED_GOOGLE_ANALYTICS_CLIENT_ID)
            .allowedParentFrameDomains(UPDATED_ALLOWED_PARENT_FRAME_DOMAINS)
            .paymentPageReference(UPDATED_PAYMENT_PAGE_REFERENCE);

        restPaymentJobAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentJobAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentJobAttributes))
            )
            .andExpect(status().isOk());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
        PaymentJobAttributes testPaymentJobAttributes = paymentJobAttributesList.get(paymentJobAttributesList.size() - 1);
        assertThat(testPaymentJobAttributes.getWebhookUrl()).isEqualTo(UPDATED_WEBHOOK_URL);
        assertThat(testPaymentJobAttributes.getGoogleAnalyticsClientId()).isEqualTo(UPDATED_GOOGLE_ANALYTICS_CLIENT_ID);
        assertThat(testPaymentJobAttributes.getAllowedParentFrameDomains()).isEqualTo(UPDATED_ALLOWED_PARENT_FRAME_DOMAINS);
        assertThat(testPaymentJobAttributes.getPaymentPageReference()).isEqualTo(UPDATED_PAYMENT_PAGE_REFERENCE);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentJobAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();
        paymentJobAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentJobAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentJobAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentJobAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();
        paymentJobAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentJobAttributes() throws Exception {
        int databaseSizeBeforeUpdate = paymentJobAttributesRepository.findAll().size();
        paymentJobAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentJobAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentJobAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentJobAttributes in the database
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentJobAttributes() throws Exception {
        // Initialize the database
        paymentJobAttributesRepository.saveAndFlush(paymentJobAttributes);

        int databaseSizeBeforeDelete = paymentJobAttributesRepository.findAll().size();

        // Delete the paymentJobAttributes
        restPaymentJobAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentJobAttributes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentJobAttributes> paymentJobAttributesList = paymentJobAttributesRepository.findAll();
        assertThat(paymentJobAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
