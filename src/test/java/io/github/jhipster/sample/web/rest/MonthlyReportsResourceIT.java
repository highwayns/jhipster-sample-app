package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.MonthlyReports;
import io.github.jhipster.sample.repository.MonthlyReportsRepository;
import io.github.jhipster.sample.service.dto.MonthlyReportsDTO;
import io.github.jhipster.sample.service.mapper.MonthlyReportsMapper;
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
 * Integration tests for the {@link MonthlyReportsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MonthlyReportsResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TRANSACTIONS_BTC = 1D;
    private static final Double UPDATED_TRANSACTIONS_BTC = 2D;

    private static final Double DEFAULT_AVG_TRANSACTION_SIZE_BTC = 1D;
    private static final Double UPDATED_AVG_TRANSACTION_SIZE_BTC = 2D;

    private static final Double DEFAULT_TRANSACTION_VOLUME_PER_USER = 1D;
    private static final Double UPDATED_TRANSACTION_VOLUME_PER_USER = 2D;

    private static final Double DEFAULT_TOTAL_FEES_BTC = 1D;
    private static final Double UPDATED_TOTAL_FEES_BTC = 2D;

    private static final Double DEFAULT_FEES_PER_USER_BTC = 1D;
    private static final Double UPDATED_FEES_PER_USER_BTC = 2D;

    private static final Double DEFAULT_GROSS_PROFIT_BTC = 1D;
    private static final Double UPDATED_GROSS_PROFIT_BTC = 2D;

    private static final String ENTITY_API_URL = "/api/monthly-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MonthlyReportsRepository monthlyReportsRepository;

    @Autowired
    private MonthlyReportsMapper monthlyReportsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonthlyReportsMockMvc;

    private MonthlyReports monthlyReports;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlyReports createEntity(EntityManager em) {
        MonthlyReports monthlyReports = new MonthlyReports()
            .date(DEFAULT_DATE)
            .transactionsBtc(DEFAULT_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(DEFAULT_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(DEFAULT_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(DEFAULT_TOTAL_FEES_BTC)
            .feesPerUserBtc(DEFAULT_FEES_PER_USER_BTC)
            .grossProfitBtc(DEFAULT_GROSS_PROFIT_BTC);
        return monthlyReports;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlyReports createUpdatedEntity(EntityManager em) {
        MonthlyReports monthlyReports = new MonthlyReports()
            .date(UPDATED_DATE)
            .transactionsBtc(UPDATED_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(UPDATED_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(UPDATED_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(UPDATED_TOTAL_FEES_BTC)
            .feesPerUserBtc(UPDATED_FEES_PER_USER_BTC)
            .grossProfitBtc(UPDATED_GROSS_PROFIT_BTC);
        return monthlyReports;
    }

    @BeforeEach
    public void initTest() {
        monthlyReports = createEntity(em);
    }

    @Test
    @Transactional
    void createMonthlyReports() throws Exception {
        int databaseSizeBeforeCreate = monthlyReportsRepository.findAll().size();
        // Create the MonthlyReports
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);
        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlyReports testMonthlyReports = monthlyReportsList.get(monthlyReportsList.size() - 1);
        assertThat(testMonthlyReports.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMonthlyReports.getTransactionsBtc()).isEqualTo(DEFAULT_TRANSACTIONS_BTC);
        assertThat(testMonthlyReports.getAvgTransactionSizeBtc()).isEqualTo(DEFAULT_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testMonthlyReports.getTransactionVolumePerUser()).isEqualTo(DEFAULT_TRANSACTION_VOLUME_PER_USER);
        assertThat(testMonthlyReports.getTotalFeesBtc()).isEqualTo(DEFAULT_TOTAL_FEES_BTC);
        assertThat(testMonthlyReports.getFeesPerUserBtc()).isEqualTo(DEFAULT_FEES_PER_USER_BTC);
        assertThat(testMonthlyReports.getGrossProfitBtc()).isEqualTo(DEFAULT_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void createMonthlyReportsWithExistingId() throws Exception {
        // Create the MonthlyReports with an existing ID
        monthlyReports.setId(1L);
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        int databaseSizeBeforeCreate = monthlyReportsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportsRepository.findAll().size();
        // set the field null
        monthlyReports.setDate(null);

        // Create the MonthlyReports, which fails.
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionsBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportsRepository.findAll().size();
        // set the field null
        monthlyReports.setTransactionsBtc(null);

        // Create the MonthlyReports, which fails.
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvgTransactionSizeBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportsRepository.findAll().size();
        // set the field null
        monthlyReports.setAvgTransactionSizeBtc(null);

        // Create the MonthlyReports, which fails.
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionVolumePerUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportsRepository.findAll().size();
        // set the field null
        monthlyReports.setTransactionVolumePerUser(null);

        // Create the MonthlyReports, which fails.
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalFeesBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportsRepository.findAll().size();
        // set the field null
        monthlyReports.setTotalFeesBtc(null);

        // Create the MonthlyReports, which fails.
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeesPerUserBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportsRepository.findAll().size();
        // set the field null
        monthlyReports.setFeesPerUserBtc(null);

        // Create the MonthlyReports, which fails.
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGrossProfitBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyReportsRepository.findAll().size();
        // set the field null
        monthlyReports.setGrossProfitBtc(null);

        // Create the MonthlyReports, which fails.
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMonthlyReports() throws Exception {
        // Initialize the database
        monthlyReportsRepository.saveAndFlush(monthlyReports);

        // Get all the monthlyReportsList
        restMonthlyReportsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyReports.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionsBtc").value(hasItem(DEFAULT_TRANSACTIONS_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].avgTransactionSizeBtc").value(hasItem(DEFAULT_AVG_TRANSACTION_SIZE_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionVolumePerUser").value(hasItem(DEFAULT_TRANSACTION_VOLUME_PER_USER.doubleValue())))
            .andExpect(jsonPath("$.[*].totalFeesBtc").value(hasItem(DEFAULT_TOTAL_FEES_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].feesPerUserBtc").value(hasItem(DEFAULT_FEES_PER_USER_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].grossProfitBtc").value(hasItem(DEFAULT_GROSS_PROFIT_BTC.doubleValue())));
    }

    @Test
    @Transactional
    void getMonthlyReports() throws Exception {
        // Initialize the database
        monthlyReportsRepository.saveAndFlush(monthlyReports);

        // Get the monthlyReports
        restMonthlyReportsMockMvc
            .perform(get(ENTITY_API_URL_ID, monthlyReports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monthlyReports.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.transactionsBtc").value(DEFAULT_TRANSACTIONS_BTC.doubleValue()))
            .andExpect(jsonPath("$.avgTransactionSizeBtc").value(DEFAULT_AVG_TRANSACTION_SIZE_BTC.doubleValue()))
            .andExpect(jsonPath("$.transactionVolumePerUser").value(DEFAULT_TRANSACTION_VOLUME_PER_USER.doubleValue()))
            .andExpect(jsonPath("$.totalFeesBtc").value(DEFAULT_TOTAL_FEES_BTC.doubleValue()))
            .andExpect(jsonPath("$.feesPerUserBtc").value(DEFAULT_FEES_PER_USER_BTC.doubleValue()))
            .andExpect(jsonPath("$.grossProfitBtc").value(DEFAULT_GROSS_PROFIT_BTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMonthlyReports() throws Exception {
        // Get the monthlyReports
        restMonthlyReportsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMonthlyReports() throws Exception {
        // Initialize the database
        monthlyReportsRepository.saveAndFlush(monthlyReports);

        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();

        // Update the monthlyReports
        MonthlyReports updatedMonthlyReports = monthlyReportsRepository.findById(monthlyReports.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlyReports are not directly saved in db
        em.detach(updatedMonthlyReports);
        updatedMonthlyReports
            .date(UPDATED_DATE)
            .transactionsBtc(UPDATED_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(UPDATED_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(UPDATED_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(UPDATED_TOTAL_FEES_BTC)
            .feesPerUserBtc(UPDATED_FEES_PER_USER_BTC)
            .grossProfitBtc(UPDATED_GROSS_PROFIT_BTC);
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(updatedMonthlyReports);

        restMonthlyReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monthlyReportsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
        MonthlyReports testMonthlyReports = monthlyReportsList.get(monthlyReportsList.size() - 1);
        assertThat(testMonthlyReports.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMonthlyReports.getTransactionsBtc()).isEqualTo(UPDATED_TRANSACTIONS_BTC);
        assertThat(testMonthlyReports.getAvgTransactionSizeBtc()).isEqualTo(UPDATED_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testMonthlyReports.getTransactionVolumePerUser()).isEqualTo(UPDATED_TRANSACTION_VOLUME_PER_USER);
        assertThat(testMonthlyReports.getTotalFeesBtc()).isEqualTo(UPDATED_TOTAL_FEES_BTC);
        assertThat(testMonthlyReports.getFeesPerUserBtc()).isEqualTo(UPDATED_FEES_PER_USER_BTC);
        assertThat(testMonthlyReports.getGrossProfitBtc()).isEqualTo(UPDATED_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void putNonExistingMonthlyReports() throws Exception {
        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();
        monthlyReports.setId(count.incrementAndGet());

        // Create the MonthlyReports
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monthlyReportsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMonthlyReports() throws Exception {
        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();
        monthlyReports.setId(count.incrementAndGet());

        // Create the MonthlyReports
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMonthlyReports() throws Exception {
        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();
        monthlyReports.setId(count.incrementAndGet());

        // Create the MonthlyReports
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyReportsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMonthlyReportsWithPatch() throws Exception {
        // Initialize the database
        monthlyReportsRepository.saveAndFlush(monthlyReports);

        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();

        // Update the monthlyReports using partial update
        MonthlyReports partialUpdatedMonthlyReports = new MonthlyReports();
        partialUpdatedMonthlyReports.setId(monthlyReports.getId());

        partialUpdatedMonthlyReports.date(UPDATED_DATE).feesPerUserBtc(UPDATED_FEES_PER_USER_BTC);

        restMonthlyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonthlyReports.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonthlyReports))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
        MonthlyReports testMonthlyReports = monthlyReportsList.get(monthlyReportsList.size() - 1);
        assertThat(testMonthlyReports.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMonthlyReports.getTransactionsBtc()).isEqualTo(DEFAULT_TRANSACTIONS_BTC);
        assertThat(testMonthlyReports.getAvgTransactionSizeBtc()).isEqualTo(DEFAULT_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testMonthlyReports.getTransactionVolumePerUser()).isEqualTo(DEFAULT_TRANSACTION_VOLUME_PER_USER);
        assertThat(testMonthlyReports.getTotalFeesBtc()).isEqualTo(DEFAULT_TOTAL_FEES_BTC);
        assertThat(testMonthlyReports.getFeesPerUserBtc()).isEqualTo(UPDATED_FEES_PER_USER_BTC);
        assertThat(testMonthlyReports.getGrossProfitBtc()).isEqualTo(DEFAULT_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void fullUpdateMonthlyReportsWithPatch() throws Exception {
        // Initialize the database
        monthlyReportsRepository.saveAndFlush(monthlyReports);

        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();

        // Update the monthlyReports using partial update
        MonthlyReports partialUpdatedMonthlyReports = new MonthlyReports();
        partialUpdatedMonthlyReports.setId(monthlyReports.getId());

        partialUpdatedMonthlyReports
            .date(UPDATED_DATE)
            .transactionsBtc(UPDATED_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(UPDATED_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(UPDATED_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(UPDATED_TOTAL_FEES_BTC)
            .feesPerUserBtc(UPDATED_FEES_PER_USER_BTC)
            .grossProfitBtc(UPDATED_GROSS_PROFIT_BTC);

        restMonthlyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonthlyReports.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonthlyReports))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
        MonthlyReports testMonthlyReports = monthlyReportsList.get(monthlyReportsList.size() - 1);
        assertThat(testMonthlyReports.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMonthlyReports.getTransactionsBtc()).isEqualTo(UPDATED_TRANSACTIONS_BTC);
        assertThat(testMonthlyReports.getAvgTransactionSizeBtc()).isEqualTo(UPDATED_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testMonthlyReports.getTransactionVolumePerUser()).isEqualTo(UPDATED_TRANSACTION_VOLUME_PER_USER);
        assertThat(testMonthlyReports.getTotalFeesBtc()).isEqualTo(UPDATED_TOTAL_FEES_BTC);
        assertThat(testMonthlyReports.getFeesPerUserBtc()).isEqualTo(UPDATED_FEES_PER_USER_BTC);
        assertThat(testMonthlyReports.getGrossProfitBtc()).isEqualTo(UPDATED_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void patchNonExistingMonthlyReports() throws Exception {
        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();
        monthlyReports.setId(count.incrementAndGet());

        // Create the MonthlyReports
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, monthlyReportsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMonthlyReports() throws Exception {
        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();
        monthlyReports.setId(count.incrementAndGet());

        // Create the MonthlyReports
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMonthlyReports() throws Exception {
        int databaseSizeBeforeUpdate = monthlyReportsRepository.findAll().size();
        monthlyReports.setId(count.incrementAndGet());

        // Create the MonthlyReports
        MonthlyReportsDTO monthlyReportsDTO = monthlyReportsMapper.toDto(monthlyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyReportsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonthlyReports in the database
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMonthlyReports() throws Exception {
        // Initialize the database
        monthlyReportsRepository.saveAndFlush(monthlyReports);

        int databaseSizeBeforeDelete = monthlyReportsRepository.findAll().size();

        // Delete the monthlyReports
        restMonthlyReportsMockMvc
            .perform(delete(ENTITY_API_URL_ID, monthlyReports.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonthlyReports> monthlyReportsList = monthlyReportsRepository.findAll();
        assertThat(monthlyReportsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
