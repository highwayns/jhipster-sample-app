package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.PaymentMethodInfo;
import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.repository.PaymentMethodInfoRepository;
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
 * Integration tests for the {@link PaymentMethodInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentMethodInfoResourceIT {

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUPPORTS_TOKENISATION = false;
    private static final Boolean UPDATED_SUPPORTS_TOKENISATION = true;

    private static final Currency DEFAULT_CURRENCIES = Currency.CNY;
    private static final Currency UPDATED_CURRENCIES = Currency.JPY;

    private static final Double DEFAULT_SURCHARGE_AMOUNT = 1D;
    private static final Double UPDATED_SURCHARGE_AMOUNT = 2D;

    private static final Double DEFAULT_SURCHARGE_AMOUNT_EXCL_VAT = 1D;
    private static final Double UPDATED_SURCHARGE_AMOUNT_EXCL_VAT = 2D;

    private static final Double DEFAULT_SURCHARGE_AMOUNT_VAT = 1D;
    private static final Double UPDATED_SURCHARGE_AMOUNT_VAT = 2D;

    private static final Double DEFAULT_SURCHARGE_VAT_PERCENTAGE = 1D;
    private static final Double UPDATED_SURCHARGE_VAT_PERCENTAGE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-method-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentMethodInfoRepository paymentMethodInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMethodInfoMockMvc;

    private PaymentMethodInfo paymentMethodInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethodInfo createEntity(EntityManager em) {
        PaymentMethodInfo paymentMethodInfo = new PaymentMethodInfo()
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .logo(DEFAULT_LOGO)
            .supportsTokenisation(DEFAULT_SUPPORTS_TOKENISATION)
            .currencies(DEFAULT_CURRENCIES)
            .surchargeAmount(DEFAULT_SURCHARGE_AMOUNT)
            .surchargeAmountExclVat(DEFAULT_SURCHARGE_AMOUNT_EXCL_VAT)
            .surchargeAmountVat(DEFAULT_SURCHARGE_AMOUNT_VAT)
            .surchargeVatPercentage(DEFAULT_SURCHARGE_VAT_PERCENTAGE)
            .description(DEFAULT_DESCRIPTION);
        return paymentMethodInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethodInfo createUpdatedEntity(EntityManager em) {
        PaymentMethodInfo paymentMethodInfo = new PaymentMethodInfo()
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .logo(UPDATED_LOGO)
            .supportsTokenisation(UPDATED_SUPPORTS_TOKENISATION)
            .currencies(UPDATED_CURRENCIES)
            .surchargeAmount(UPDATED_SURCHARGE_AMOUNT)
            .surchargeAmountExclVat(UPDATED_SURCHARGE_AMOUNT_EXCL_VAT)
            .surchargeAmountVat(UPDATED_SURCHARGE_AMOUNT_VAT)
            .surchargeVatPercentage(UPDATED_SURCHARGE_VAT_PERCENTAGE)
            .description(UPDATED_DESCRIPTION);
        return paymentMethodInfo;
    }

    @BeforeEach
    public void initTest() {
        paymentMethodInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentMethodInfo() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodInfoRepository.findAll().size();
        // Create the PaymentMethodInfo
        restPaymentMethodInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethodInfo testPaymentMethodInfo = paymentMethodInfoList.get(paymentMethodInfoList.size() - 1);
        assertThat(testPaymentMethodInfo.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testPaymentMethodInfo.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testPaymentMethodInfo.getSupportsTokenisation()).isEqualTo(DEFAULT_SUPPORTS_TOKENISATION);
        assertThat(testPaymentMethodInfo.getCurrencies()).isEqualTo(DEFAULT_CURRENCIES);
        assertThat(testPaymentMethodInfo.getSurchargeAmount()).isEqualTo(DEFAULT_SURCHARGE_AMOUNT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountExclVat()).isEqualTo(DEFAULT_SURCHARGE_AMOUNT_EXCL_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountVat()).isEqualTo(DEFAULT_SURCHARGE_AMOUNT_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeVatPercentage()).isEqualTo(DEFAULT_SURCHARGE_VAT_PERCENTAGE);
        assertThat(testPaymentMethodInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPaymentMethodInfoWithExistingId() throws Exception {
        // Create the PaymentMethodInfo with an existing ID
        paymentMethodInfo.setId(1L);

        int databaseSizeBeforeCreate = paymentMethodInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentMethodInfos() throws Exception {
        // Initialize the database
        paymentMethodInfoRepository.saveAndFlush(paymentMethodInfo);

        // Get all the paymentMethodInfoList
        restPaymentMethodInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethodInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].supportsTokenisation").value(hasItem(DEFAULT_SUPPORTS_TOKENISATION.booleanValue())))
            .andExpect(jsonPath("$.[*].currencies").value(hasItem(DEFAULT_CURRENCIES.toString())))
            .andExpect(jsonPath("$.[*].surchargeAmount").value(hasItem(DEFAULT_SURCHARGE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].surchargeAmountExclVat").value(hasItem(DEFAULT_SURCHARGE_AMOUNT_EXCL_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].surchargeAmountVat").value(hasItem(DEFAULT_SURCHARGE_AMOUNT_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].surchargeVatPercentage").value(hasItem(DEFAULT_SURCHARGE_VAT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPaymentMethodInfo() throws Exception {
        // Initialize the database
        paymentMethodInfoRepository.saveAndFlush(paymentMethodInfo);

        // Get the paymentMethodInfo
        restPaymentMethodInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentMethodInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethodInfo.getId().intValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.supportsTokenisation").value(DEFAULT_SUPPORTS_TOKENISATION.booleanValue()))
            .andExpect(jsonPath("$.currencies").value(DEFAULT_CURRENCIES.toString()))
            .andExpect(jsonPath("$.surchargeAmount").value(DEFAULT_SURCHARGE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.surchargeAmountExclVat").value(DEFAULT_SURCHARGE_AMOUNT_EXCL_VAT.doubleValue()))
            .andExpect(jsonPath("$.surchargeAmountVat").value(DEFAULT_SURCHARGE_AMOUNT_VAT.doubleValue()))
            .andExpect(jsonPath("$.surchargeVatPercentage").value(DEFAULT_SURCHARGE_VAT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingPaymentMethodInfo() throws Exception {
        // Get the paymentMethodInfo
        restPaymentMethodInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentMethodInfo() throws Exception {
        // Initialize the database
        paymentMethodInfoRepository.saveAndFlush(paymentMethodInfo);

        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();

        // Update the paymentMethodInfo
        PaymentMethodInfo updatedPaymentMethodInfo = paymentMethodInfoRepository.findById(paymentMethodInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMethodInfo are not directly saved in db
        em.detach(updatedPaymentMethodInfo);
        updatedPaymentMethodInfo
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .logo(UPDATED_LOGO)
            .supportsTokenisation(UPDATED_SUPPORTS_TOKENISATION)
            .currencies(UPDATED_CURRENCIES)
            .surchargeAmount(UPDATED_SURCHARGE_AMOUNT)
            .surchargeAmountExclVat(UPDATED_SURCHARGE_AMOUNT_EXCL_VAT)
            .surchargeAmountVat(UPDATED_SURCHARGE_AMOUNT_VAT)
            .surchargeVatPercentage(UPDATED_SURCHARGE_VAT_PERCENTAGE)
            .description(UPDATED_DESCRIPTION);

        restPaymentMethodInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentMethodInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentMethodInfo))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethodInfo testPaymentMethodInfo = paymentMethodInfoList.get(paymentMethodInfoList.size() - 1);
        assertThat(testPaymentMethodInfo.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testPaymentMethodInfo.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testPaymentMethodInfo.getSupportsTokenisation()).isEqualTo(UPDATED_SUPPORTS_TOKENISATION);
        assertThat(testPaymentMethodInfo.getCurrencies()).isEqualTo(UPDATED_CURRENCIES);
        assertThat(testPaymentMethodInfo.getSurchargeAmount()).isEqualTo(UPDATED_SURCHARGE_AMOUNT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountExclVat()).isEqualTo(UPDATED_SURCHARGE_AMOUNT_EXCL_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountVat()).isEqualTo(UPDATED_SURCHARGE_AMOUNT_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeVatPercentage()).isEqualTo(UPDATED_SURCHARGE_VAT_PERCENTAGE);
        assertThat(testPaymentMethodInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPaymentMethodInfo() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();
        paymentMethodInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentMethodInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentMethodInfo() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();
        paymentMethodInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentMethodInfo() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();
        paymentMethodInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentMethodInfoWithPatch() throws Exception {
        // Initialize the database
        paymentMethodInfoRepository.saveAndFlush(paymentMethodInfo);

        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();

        // Update the paymentMethodInfo using partial update
        PaymentMethodInfo partialUpdatedPaymentMethodInfo = new PaymentMethodInfo();
        partialUpdatedPaymentMethodInfo.setId(paymentMethodInfo.getId());

        partialUpdatedPaymentMethodInfo.currencies(UPDATED_CURRENCIES).surchargeAmount(UPDATED_SURCHARGE_AMOUNT);

        restPaymentMethodInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethodInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethodInfo))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethodInfo testPaymentMethodInfo = paymentMethodInfoList.get(paymentMethodInfoList.size() - 1);
        assertThat(testPaymentMethodInfo.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testPaymentMethodInfo.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testPaymentMethodInfo.getSupportsTokenisation()).isEqualTo(DEFAULT_SUPPORTS_TOKENISATION);
        assertThat(testPaymentMethodInfo.getCurrencies()).isEqualTo(UPDATED_CURRENCIES);
        assertThat(testPaymentMethodInfo.getSurchargeAmount()).isEqualTo(UPDATED_SURCHARGE_AMOUNT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountExclVat()).isEqualTo(DEFAULT_SURCHARGE_AMOUNT_EXCL_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountVat()).isEqualTo(DEFAULT_SURCHARGE_AMOUNT_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeVatPercentage()).isEqualTo(DEFAULT_SURCHARGE_VAT_PERCENTAGE);
        assertThat(testPaymentMethodInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePaymentMethodInfoWithPatch() throws Exception {
        // Initialize the database
        paymentMethodInfoRepository.saveAndFlush(paymentMethodInfo);

        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();

        // Update the paymentMethodInfo using partial update
        PaymentMethodInfo partialUpdatedPaymentMethodInfo = new PaymentMethodInfo();
        partialUpdatedPaymentMethodInfo.setId(paymentMethodInfo.getId());

        partialUpdatedPaymentMethodInfo
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .logo(UPDATED_LOGO)
            .supportsTokenisation(UPDATED_SUPPORTS_TOKENISATION)
            .currencies(UPDATED_CURRENCIES)
            .surchargeAmount(UPDATED_SURCHARGE_AMOUNT)
            .surchargeAmountExclVat(UPDATED_SURCHARGE_AMOUNT_EXCL_VAT)
            .surchargeAmountVat(UPDATED_SURCHARGE_AMOUNT_VAT)
            .surchargeVatPercentage(UPDATED_SURCHARGE_VAT_PERCENTAGE)
            .description(UPDATED_DESCRIPTION);

        restPaymentMethodInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethodInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethodInfo))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethodInfo testPaymentMethodInfo = paymentMethodInfoList.get(paymentMethodInfoList.size() - 1);
        assertThat(testPaymentMethodInfo.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testPaymentMethodInfo.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testPaymentMethodInfo.getSupportsTokenisation()).isEqualTo(UPDATED_SUPPORTS_TOKENISATION);
        assertThat(testPaymentMethodInfo.getCurrencies()).isEqualTo(UPDATED_CURRENCIES);
        assertThat(testPaymentMethodInfo.getSurchargeAmount()).isEqualTo(UPDATED_SURCHARGE_AMOUNT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountExclVat()).isEqualTo(UPDATED_SURCHARGE_AMOUNT_EXCL_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeAmountVat()).isEqualTo(UPDATED_SURCHARGE_AMOUNT_VAT);
        assertThat(testPaymentMethodInfo.getSurchargeVatPercentage()).isEqualTo(UPDATED_SURCHARGE_VAT_PERCENTAGE);
        assertThat(testPaymentMethodInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentMethodInfo() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();
        paymentMethodInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentMethodInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentMethodInfo() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();
        paymentMethodInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentMethodInfo() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodInfoRepository.findAll().size();
        paymentMethodInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethodInfo in the database
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentMethodInfo() throws Exception {
        // Initialize the database
        paymentMethodInfoRepository.saveAndFlush(paymentMethodInfo);

        int databaseSizeBeforeDelete = paymentMethodInfoRepository.findAll().size();

        // Delete the paymentMethodInfo
        restPaymentMethodInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentMethodInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentMethodInfo> paymentMethodInfoList = paymentMethodInfoRepository.findAll();
        assertThat(paymentMethodInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
