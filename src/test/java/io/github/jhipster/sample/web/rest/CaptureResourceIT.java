package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Capture;
import io.github.jhipster.sample.domain.enumeration.CaptureStatus;
import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.repository.CaptureRepository;
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
 * Integration tests for the {@link CaptureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaptureResourceIT {

    private static final Long DEFAULT_REFERENCE = 1L;
    private static final Long UPDATED_REFERENCE = 2L;

    private static final Instant DEFAULT_CREATE_DATE_TIME_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE_TIME_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CaptureStatus DEFAULT_STATUS = CaptureStatus.PENDING;
    private static final CaptureStatus UPDATED_STATUS = CaptureStatus.COMPLETED;

    private static final Double DEFAULT_AMOUNT_TO_CAPTURE = 1D;
    private static final Double UPDATED_AMOUNT_TO_CAPTURE = 2D;

    private static final Double DEFAULT_CONVERTED_AMOUNT_TO_CAPTURE = 1D;
    private static final Double UPDATED_CONVERTED_AMOUNT_TO_CAPTURE = 2D;

    private static final Currency DEFAULT_CONVERTED_CURRENCY = Currency.CNY;
    private static final Currency UPDATED_CONVERTED_CURRENCY = Currency.JPY;

    private static final Double DEFAULT_CONVERSION_RATE = 1D;
    private static final Double UPDATED_CONVERSION_RATE = 2D;

    private static final Boolean DEFAULT_IS_FINAL_CAPTURE = false;
    private static final Boolean UPDATED_IS_FINAL_CAPTURE = true;

    private static final String ENTITY_API_URL = "/api/captures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CaptureRepository captureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaptureMockMvc;

    private Capture capture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capture createEntity(EntityManager em) {
        Capture capture = new Capture()
            .reference(DEFAULT_REFERENCE)
            .createDateTimeUtc(DEFAULT_CREATE_DATE_TIME_UTC)
            .status(DEFAULT_STATUS)
            .amountToCapture(DEFAULT_AMOUNT_TO_CAPTURE)
            .convertedAmountToCapture(DEFAULT_CONVERTED_AMOUNT_TO_CAPTURE)
            .convertedCurrency(DEFAULT_CONVERTED_CURRENCY)
            .conversionRate(DEFAULT_CONVERSION_RATE)
            .isFinalCapture(DEFAULT_IS_FINAL_CAPTURE);
        return capture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capture createUpdatedEntity(EntityManager em) {
        Capture capture = new Capture()
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .status(UPDATED_STATUS)
            .amountToCapture(UPDATED_AMOUNT_TO_CAPTURE)
            .convertedAmountToCapture(UPDATED_CONVERTED_AMOUNT_TO_CAPTURE)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .isFinalCapture(UPDATED_IS_FINAL_CAPTURE);
        return capture;
    }

    @BeforeEach
    public void initTest() {
        capture = createEntity(em);
    }

    @Test
    @Transactional
    void createCapture() throws Exception {
        int databaseSizeBeforeCreate = captureRepository.findAll().size();
        // Create the Capture
        restCaptureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capture)))
            .andExpect(status().isCreated());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeCreate + 1);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCapture.getCreateDateTimeUtc()).isEqualTo(DEFAULT_CREATE_DATE_TIME_UTC);
        assertThat(testCapture.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCapture.getAmountToCapture()).isEqualTo(DEFAULT_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedAmountToCapture()).isEqualTo(DEFAULT_CONVERTED_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedCurrency()).isEqualTo(DEFAULT_CONVERTED_CURRENCY);
        assertThat(testCapture.getConversionRate()).isEqualTo(DEFAULT_CONVERSION_RATE);
        assertThat(testCapture.getIsFinalCapture()).isEqualTo(DEFAULT_IS_FINAL_CAPTURE);
    }

    @Test
    @Transactional
    void createCaptureWithExistingId() throws Exception {
        // Create the Capture with an existing ID
        capture.setId(1L);

        int databaseSizeBeforeCreate = captureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaptureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capture)))
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaptures() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        // Get all the captureList
        restCaptureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capture.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].createDateTimeUtc").value(hasItem(DEFAULT_CREATE_DATE_TIME_UTC.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].amountToCapture").value(hasItem(DEFAULT_AMOUNT_TO_CAPTURE.doubleValue())))
            .andExpect(jsonPath("$.[*].convertedAmountToCapture").value(hasItem(DEFAULT_CONVERTED_AMOUNT_TO_CAPTURE.doubleValue())))
            .andExpect(jsonPath("$.[*].convertedCurrency").value(hasItem(DEFAULT_CONVERTED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].conversionRate").value(hasItem(DEFAULT_CONVERSION_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isFinalCapture").value(hasItem(DEFAULT_IS_FINAL_CAPTURE.booleanValue())));
    }

    @Test
    @Transactional
    void getCapture() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        // Get the capture
        restCaptureMockMvc
            .perform(get(ENTITY_API_URL_ID, capture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(capture.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.createDateTimeUtc").value(DEFAULT_CREATE_DATE_TIME_UTC.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.amountToCapture").value(DEFAULT_AMOUNT_TO_CAPTURE.doubleValue()))
            .andExpect(jsonPath("$.convertedAmountToCapture").value(DEFAULT_CONVERTED_AMOUNT_TO_CAPTURE.doubleValue()))
            .andExpect(jsonPath("$.convertedCurrency").value(DEFAULT_CONVERTED_CURRENCY.toString()))
            .andExpect(jsonPath("$.conversionRate").value(DEFAULT_CONVERSION_RATE.doubleValue()))
            .andExpect(jsonPath("$.isFinalCapture").value(DEFAULT_IS_FINAL_CAPTURE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCapture() throws Exception {
        // Get the capture
        restCaptureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCapture() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeUpdate = captureRepository.findAll().size();

        // Update the capture
        Capture updatedCapture = captureRepository.findById(capture.getId()).get();
        // Disconnect from session so that the updates on updatedCapture are not directly saved in db
        em.detach(updatedCapture);
        updatedCapture
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .status(UPDATED_STATUS)
            .amountToCapture(UPDATED_AMOUNT_TO_CAPTURE)
            .convertedAmountToCapture(UPDATED_CONVERTED_AMOUNT_TO_CAPTURE)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .isFinalCapture(UPDATED_IS_FINAL_CAPTURE);

        restCaptureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCapture.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCapture))
            )
            .andExpect(status().isOk());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCapture.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testCapture.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCapture.getAmountToCapture()).isEqualTo(UPDATED_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedAmountToCapture()).isEqualTo(UPDATED_CONVERTED_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testCapture.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
        assertThat(testCapture.getIsFinalCapture()).isEqualTo(UPDATED_IS_FINAL_CAPTURE);
    }

    @Test
    @Transactional
    void putNonExistingCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, capture.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(capture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(capture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capture)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaptureWithPatch() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeUpdate = captureRepository.findAll().size();

        // Update the capture using partial update
        Capture partialUpdatedCapture = new Capture();
        partialUpdatedCapture.setId(capture.getId());

        partialUpdatedCapture
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .convertedAmountToCapture(UPDATED_CONVERTED_AMOUNT_TO_CAPTURE)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .isFinalCapture(UPDATED_IS_FINAL_CAPTURE);

        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapture))
            )
            .andExpect(status().isOk());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCapture.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testCapture.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCapture.getAmountToCapture()).isEqualTo(DEFAULT_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedAmountToCapture()).isEqualTo(UPDATED_CONVERTED_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedCurrency()).isEqualTo(DEFAULT_CONVERTED_CURRENCY);
        assertThat(testCapture.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
        assertThat(testCapture.getIsFinalCapture()).isEqualTo(UPDATED_IS_FINAL_CAPTURE);
    }

    @Test
    @Transactional
    void fullUpdateCaptureWithPatch() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeUpdate = captureRepository.findAll().size();

        // Update the capture using partial update
        Capture partialUpdatedCapture = new Capture();
        partialUpdatedCapture.setId(capture.getId());

        partialUpdatedCapture
            .reference(UPDATED_REFERENCE)
            .createDateTimeUtc(UPDATED_CREATE_DATE_TIME_UTC)
            .status(UPDATED_STATUS)
            .amountToCapture(UPDATED_AMOUNT_TO_CAPTURE)
            .convertedAmountToCapture(UPDATED_CONVERTED_AMOUNT_TO_CAPTURE)
            .convertedCurrency(UPDATED_CONVERTED_CURRENCY)
            .conversionRate(UPDATED_CONVERSION_RATE)
            .isFinalCapture(UPDATED_IS_FINAL_CAPTURE);

        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapture))
            )
            .andExpect(status().isOk());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCapture.getCreateDateTimeUtc()).isEqualTo(UPDATED_CREATE_DATE_TIME_UTC);
        assertThat(testCapture.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCapture.getAmountToCapture()).isEqualTo(UPDATED_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedAmountToCapture()).isEqualTo(UPDATED_CONVERTED_AMOUNT_TO_CAPTURE);
        assertThat(testCapture.getConvertedCurrency()).isEqualTo(UPDATED_CONVERTED_CURRENCY);
        assertThat(testCapture.getConversionRate()).isEqualTo(UPDATED_CONVERSION_RATE);
        assertThat(testCapture.getIsFinalCapture()).isEqualTo(UPDATED_IS_FINAL_CAPTURE);
    }

    @Test
    @Transactional
    void patchNonExistingCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, capture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(capture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(capture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(capture)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCapture() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeDelete = captureRepository.findAll().size();

        // Delete the capture
        restCaptureMockMvc
            .perform(delete(ENTITY_API_URL_ID, capture.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
