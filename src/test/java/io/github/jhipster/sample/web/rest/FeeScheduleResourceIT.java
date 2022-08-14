package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.FeeSchedule;
import io.github.jhipster.sample.repository.FeeScheduleRepository;
import io.github.jhipster.sample.service.dto.FeeScheduleDTO;
import io.github.jhipster.sample.service.mapper.FeeScheduleMapper;
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
 * Integration tests for the {@link FeeScheduleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeeScheduleResourceIT {

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    private static final Double DEFAULT_FROM_USD = 1D;
    private static final Double UPDATED_FROM_USD = 2D;

    private static final Double DEFAULT_TO_USD = 1D;
    private static final Double UPDATED_TO_USD = 2D;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Double DEFAULT_FEE_1 = 1D;
    private static final Double UPDATED_FEE_1 = 2D;

    private static final Double DEFAULT_GLOBAL_BTC = 1D;
    private static final Double UPDATED_GLOBAL_BTC = 2D;

    private static final String ENTITY_API_URL = "/api/fee-schedules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeeScheduleRepository feeScheduleRepository;

    @Autowired
    private FeeScheduleMapper feeScheduleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeeScheduleMockMvc;

    private FeeSchedule feeSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeeSchedule createEntity(EntityManager em) {
        FeeSchedule feeSchedule = new FeeSchedule()
            .fee(DEFAULT_FEE)
            .fromUsd(DEFAULT_FROM_USD)
            .toUsd(DEFAULT_TO_USD)
            .order(DEFAULT_ORDER)
            .fee1(DEFAULT_FEE_1)
            .globalBtc(DEFAULT_GLOBAL_BTC);
        return feeSchedule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeeSchedule createUpdatedEntity(EntityManager em) {
        FeeSchedule feeSchedule = new FeeSchedule()
            .fee(UPDATED_FEE)
            .fromUsd(UPDATED_FROM_USD)
            .toUsd(UPDATED_TO_USD)
            .order(UPDATED_ORDER)
            .fee1(UPDATED_FEE_1)
            .globalBtc(UPDATED_GLOBAL_BTC);
        return feeSchedule;
    }

    @BeforeEach
    public void initTest() {
        feeSchedule = createEntity(em);
    }

    @Test
    @Transactional
    void createFeeSchedule() throws Exception {
        int databaseSizeBeforeCreate = feeScheduleRepository.findAll().size();
        // Create the FeeSchedule
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);
        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        FeeSchedule testFeeSchedule = feeScheduleList.get(feeScheduleList.size() - 1);
        assertThat(testFeeSchedule.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testFeeSchedule.getFromUsd()).isEqualTo(DEFAULT_FROM_USD);
        assertThat(testFeeSchedule.getToUsd()).isEqualTo(DEFAULT_TO_USD);
        assertThat(testFeeSchedule.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testFeeSchedule.getFee1()).isEqualTo(DEFAULT_FEE_1);
        assertThat(testFeeSchedule.getGlobalBtc()).isEqualTo(DEFAULT_GLOBAL_BTC);
    }

    @Test
    @Transactional
    void createFeeScheduleWithExistingId() throws Exception {
        // Create the FeeSchedule with an existing ID
        feeSchedule.setId(1L);
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        int databaseSizeBeforeCreate = feeScheduleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeScheduleRepository.findAll().size();
        // set the field null
        feeSchedule.setFee(null);

        // Create the FeeSchedule, which fails.
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFromUsdIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeScheduleRepository.findAll().size();
        // set the field null
        feeSchedule.setFromUsd(null);

        // Create the FeeSchedule, which fails.
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkToUsdIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeScheduleRepository.findAll().size();
        // set the field null
        feeSchedule.setToUsd(null);

        // Create the FeeSchedule, which fails.
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeScheduleRepository.findAll().size();
        // set the field null
        feeSchedule.setOrder(null);

        // Create the FeeSchedule, which fails.
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFee1IsRequired() throws Exception {
        int databaseSizeBeforeTest = feeScheduleRepository.findAll().size();
        // set the field null
        feeSchedule.setFee1(null);

        // Create the FeeSchedule, which fails.
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGlobalBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeScheduleRepository.findAll().size();
        // set the field null
        feeSchedule.setGlobalBtc(null);

        // Create the FeeSchedule, which fails.
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        restFeeScheduleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeeSchedules() throws Exception {
        // Initialize the database
        feeScheduleRepository.saveAndFlush(feeSchedule);

        // Get all the feeScheduleList
        restFeeScheduleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feeSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].fromUsd").value(hasItem(DEFAULT_FROM_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].toUsd").value(hasItem(DEFAULT_TO_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].fee1").value(hasItem(DEFAULT_FEE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].globalBtc").value(hasItem(DEFAULT_GLOBAL_BTC.doubleValue())));
    }

    @Test
    @Transactional
    void getFeeSchedule() throws Exception {
        // Initialize the database
        feeScheduleRepository.saveAndFlush(feeSchedule);

        // Get the feeSchedule
        restFeeScheduleMockMvc
            .perform(get(ENTITY_API_URL_ID, feeSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feeSchedule.getId().intValue()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.doubleValue()))
            .andExpect(jsonPath("$.fromUsd").value(DEFAULT_FROM_USD.doubleValue()))
            .andExpect(jsonPath("$.toUsd").value(DEFAULT_TO_USD.doubleValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.fee1").value(DEFAULT_FEE_1.doubleValue()))
            .andExpect(jsonPath("$.globalBtc").value(DEFAULT_GLOBAL_BTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFeeSchedule() throws Exception {
        // Get the feeSchedule
        restFeeScheduleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFeeSchedule() throws Exception {
        // Initialize the database
        feeScheduleRepository.saveAndFlush(feeSchedule);

        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();

        // Update the feeSchedule
        FeeSchedule updatedFeeSchedule = feeScheduleRepository.findById(feeSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedFeeSchedule are not directly saved in db
        em.detach(updatedFeeSchedule);
        updatedFeeSchedule
            .fee(UPDATED_FEE)
            .fromUsd(UPDATED_FROM_USD)
            .toUsd(UPDATED_TO_USD)
            .order(UPDATED_ORDER)
            .fee1(UPDATED_FEE_1)
            .globalBtc(UPDATED_GLOBAL_BTC);
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(updatedFeeSchedule);

        restFeeScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feeScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isOk());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
        FeeSchedule testFeeSchedule = feeScheduleList.get(feeScheduleList.size() - 1);
        assertThat(testFeeSchedule.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testFeeSchedule.getFromUsd()).isEqualTo(UPDATED_FROM_USD);
        assertThat(testFeeSchedule.getToUsd()).isEqualTo(UPDATED_TO_USD);
        assertThat(testFeeSchedule.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testFeeSchedule.getFee1()).isEqualTo(UPDATED_FEE_1);
        assertThat(testFeeSchedule.getGlobalBtc()).isEqualTo(UPDATED_GLOBAL_BTC);
    }

    @Test
    @Transactional
    void putNonExistingFeeSchedule() throws Exception {
        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();
        feeSchedule.setId(count.incrementAndGet());

        // Create the FeeSchedule
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeeScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feeScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeeSchedule() throws Exception {
        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();
        feeSchedule.setId(count.incrementAndGet());

        // Create the FeeSchedule
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeeSchedule() throws Exception {
        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();
        feeSchedule.setId(count.incrementAndGet());

        // Create the FeeSchedule
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeScheduleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeeScheduleWithPatch() throws Exception {
        // Initialize the database
        feeScheduleRepository.saveAndFlush(feeSchedule);

        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();

        // Update the feeSchedule using partial update
        FeeSchedule partialUpdatedFeeSchedule = new FeeSchedule();
        partialUpdatedFeeSchedule.setId(feeSchedule.getId());

        partialUpdatedFeeSchedule
            .fee(UPDATED_FEE)
            .fromUsd(UPDATED_FROM_USD)
            .order(UPDATED_ORDER)
            .fee1(UPDATED_FEE_1)
            .globalBtc(UPDATED_GLOBAL_BTC);

        restFeeScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeeSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeeSchedule))
            )
            .andExpect(status().isOk());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
        FeeSchedule testFeeSchedule = feeScheduleList.get(feeScheduleList.size() - 1);
        assertThat(testFeeSchedule.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testFeeSchedule.getFromUsd()).isEqualTo(UPDATED_FROM_USD);
        assertThat(testFeeSchedule.getToUsd()).isEqualTo(DEFAULT_TO_USD);
        assertThat(testFeeSchedule.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testFeeSchedule.getFee1()).isEqualTo(UPDATED_FEE_1);
        assertThat(testFeeSchedule.getGlobalBtc()).isEqualTo(UPDATED_GLOBAL_BTC);
    }

    @Test
    @Transactional
    void fullUpdateFeeScheduleWithPatch() throws Exception {
        // Initialize the database
        feeScheduleRepository.saveAndFlush(feeSchedule);

        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();

        // Update the feeSchedule using partial update
        FeeSchedule partialUpdatedFeeSchedule = new FeeSchedule();
        partialUpdatedFeeSchedule.setId(feeSchedule.getId());

        partialUpdatedFeeSchedule
            .fee(UPDATED_FEE)
            .fromUsd(UPDATED_FROM_USD)
            .toUsd(UPDATED_TO_USD)
            .order(UPDATED_ORDER)
            .fee1(UPDATED_FEE_1)
            .globalBtc(UPDATED_GLOBAL_BTC);

        restFeeScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeeSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeeSchedule))
            )
            .andExpect(status().isOk());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
        FeeSchedule testFeeSchedule = feeScheduleList.get(feeScheduleList.size() - 1);
        assertThat(testFeeSchedule.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testFeeSchedule.getFromUsd()).isEqualTo(UPDATED_FROM_USD);
        assertThat(testFeeSchedule.getToUsd()).isEqualTo(UPDATED_TO_USD);
        assertThat(testFeeSchedule.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testFeeSchedule.getFee1()).isEqualTo(UPDATED_FEE_1);
        assertThat(testFeeSchedule.getGlobalBtc()).isEqualTo(UPDATED_GLOBAL_BTC);
    }

    @Test
    @Transactional
    void patchNonExistingFeeSchedule() throws Exception {
        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();
        feeSchedule.setId(count.incrementAndGet());

        // Create the FeeSchedule
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeeScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feeScheduleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeeSchedule() throws Exception {
        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();
        feeSchedule.setId(count.incrementAndGet());

        // Create the FeeSchedule
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeeSchedule() throws Exception {
        int databaseSizeBeforeUpdate = feeScheduleRepository.findAll().size();
        feeSchedule.setId(count.incrementAndGet());

        // Create the FeeSchedule
        FeeScheduleDTO feeScheduleDTO = feeScheduleMapper.toDto(feeSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeeScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(feeScheduleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeeSchedule in the database
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeeSchedule() throws Exception {
        // Initialize the database
        feeScheduleRepository.saveAndFlush(feeSchedule);

        int databaseSizeBeforeDelete = feeScheduleRepository.findAll().size();

        // Delete the feeSchedule
        restFeeScheduleMockMvc
            .perform(delete(ENTITY_API_URL_ID, feeSchedule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeeSchedule> feeScheduleList = feeScheduleRepository.findAll();
        assertThat(feeScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
