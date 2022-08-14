package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.DailyReports;
import io.github.jhipster.sample.repository.DailyReportsRepository;
import io.github.jhipster.sample.service.dto.DailyReportsDTO;
import io.github.jhipster.sample.service.mapper.DailyReportsMapper;
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
 * Integration tests for the {@link DailyReportsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DailyReportsResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_BTC = 1D;
    private static final Double UPDATED_TOTAL_BTC = 2D;

    private static final Double DEFAULT_TOTAL_FIAT_USD = 1D;
    private static final Double UPDATED_TOTAL_FIAT_USD = 2D;

    private static final Double DEFAULT_OPEN_ORDERS_BTC = 1D;
    private static final Double UPDATED_OPEN_ORDERS_BTC = 2D;

    private static final Double DEFAULT_BTC_PER_USER = 1D;
    private static final Double UPDATED_BTC_PER_USER = 2D;

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

    private static final Double DEFAULT_USD_PER_USER = 1D;
    private static final Double UPDATED_USD_PER_USER = 2D;

    private static final Double DEFAULT_GROSS_PROFIT_BTC = 1D;
    private static final Double UPDATED_GROSS_PROFIT_BTC = 2D;

    private static final String ENTITY_API_URL = "/api/daily-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DailyReportsRepository dailyReportsRepository;

    @Autowired
    private DailyReportsMapper dailyReportsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyReportsMockMvc;

    private DailyReports dailyReports;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyReports createEntity(EntityManager em) {
        DailyReports dailyReports = new DailyReports()
            .date(DEFAULT_DATE)
            .totalBtc(DEFAULT_TOTAL_BTC)
            .totalFiatUsd(DEFAULT_TOTAL_FIAT_USD)
            .openOrdersBtc(DEFAULT_OPEN_ORDERS_BTC)
            .btcPerUser(DEFAULT_BTC_PER_USER)
            .transactionsBtc(DEFAULT_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(DEFAULT_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(DEFAULT_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(DEFAULT_TOTAL_FEES_BTC)
            .feesPerUserBtc(DEFAULT_FEES_PER_USER_BTC)
            .usdPerUser(DEFAULT_USD_PER_USER)
            .grossProfitBtc(DEFAULT_GROSS_PROFIT_BTC);
        return dailyReports;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyReports createUpdatedEntity(EntityManager em) {
        DailyReports dailyReports = new DailyReports()
            .date(UPDATED_DATE)
            .totalBtc(UPDATED_TOTAL_BTC)
            .totalFiatUsd(UPDATED_TOTAL_FIAT_USD)
            .openOrdersBtc(UPDATED_OPEN_ORDERS_BTC)
            .btcPerUser(UPDATED_BTC_PER_USER)
            .transactionsBtc(UPDATED_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(UPDATED_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(UPDATED_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(UPDATED_TOTAL_FEES_BTC)
            .feesPerUserBtc(UPDATED_FEES_PER_USER_BTC)
            .usdPerUser(UPDATED_USD_PER_USER)
            .grossProfitBtc(UPDATED_GROSS_PROFIT_BTC);
        return dailyReports;
    }

    @BeforeEach
    public void initTest() {
        dailyReports = createEntity(em);
    }

    @Test
    @Transactional
    void createDailyReports() throws Exception {
        int databaseSizeBeforeCreate = dailyReportsRepository.findAll().size();
        // Create the DailyReports
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);
        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeCreate + 1);
        DailyReports testDailyReports = dailyReportsList.get(dailyReportsList.size() - 1);
        assertThat(testDailyReports.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDailyReports.getTotalBtc()).isEqualTo(DEFAULT_TOTAL_BTC);
        assertThat(testDailyReports.getTotalFiatUsd()).isEqualTo(DEFAULT_TOTAL_FIAT_USD);
        assertThat(testDailyReports.getOpenOrdersBtc()).isEqualTo(DEFAULT_OPEN_ORDERS_BTC);
        assertThat(testDailyReports.getBtcPerUser()).isEqualTo(DEFAULT_BTC_PER_USER);
        assertThat(testDailyReports.getTransactionsBtc()).isEqualTo(DEFAULT_TRANSACTIONS_BTC);
        assertThat(testDailyReports.getAvgTransactionSizeBtc()).isEqualTo(DEFAULT_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testDailyReports.getTransactionVolumePerUser()).isEqualTo(DEFAULT_TRANSACTION_VOLUME_PER_USER);
        assertThat(testDailyReports.getTotalFeesBtc()).isEqualTo(DEFAULT_TOTAL_FEES_BTC);
        assertThat(testDailyReports.getFeesPerUserBtc()).isEqualTo(DEFAULT_FEES_PER_USER_BTC);
        assertThat(testDailyReports.getUsdPerUser()).isEqualTo(DEFAULT_USD_PER_USER);
        assertThat(testDailyReports.getGrossProfitBtc()).isEqualTo(DEFAULT_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void createDailyReportsWithExistingId() throws Exception {
        // Create the DailyReports with an existing ID
        dailyReports.setId(1L);
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        int databaseSizeBeforeCreate = dailyReportsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setDate(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setTotalBtc(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalFiatUsdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setTotalFiatUsd(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOpenOrdersBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setOpenOrdersBtc(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcPerUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setBtcPerUser(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionsBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setTransactionsBtc(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvgTransactionSizeBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setAvgTransactionSizeBtc(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionVolumePerUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setTransactionVolumePerUser(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalFeesBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setTotalFeesBtc(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeesPerUserBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setFeesPerUserBtc(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsdPerUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setUsdPerUser(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGrossProfitBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyReportsRepository.findAll().size();
        // set the field null
        dailyReports.setGrossProfitBtc(null);

        // Create the DailyReports, which fails.
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        restDailyReportsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDailyReports() throws Exception {
        // Initialize the database
        dailyReportsRepository.saveAndFlush(dailyReports);

        // Get all the dailyReportsList
        restDailyReportsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyReports.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalBtc").value(hasItem(DEFAULT_TOTAL_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].totalFiatUsd").value(hasItem(DEFAULT_TOTAL_FIAT_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].openOrdersBtc").value(hasItem(DEFAULT_OPEN_ORDERS_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].btcPerUser").value(hasItem(DEFAULT_BTC_PER_USER.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionsBtc").value(hasItem(DEFAULT_TRANSACTIONS_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].avgTransactionSizeBtc").value(hasItem(DEFAULT_AVG_TRANSACTION_SIZE_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionVolumePerUser").value(hasItem(DEFAULT_TRANSACTION_VOLUME_PER_USER.doubleValue())))
            .andExpect(jsonPath("$.[*].totalFeesBtc").value(hasItem(DEFAULT_TOTAL_FEES_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].feesPerUserBtc").value(hasItem(DEFAULT_FEES_PER_USER_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].usdPerUser").value(hasItem(DEFAULT_USD_PER_USER.doubleValue())))
            .andExpect(jsonPath("$.[*].grossProfitBtc").value(hasItem(DEFAULT_GROSS_PROFIT_BTC.doubleValue())));
    }

    @Test
    @Transactional
    void getDailyReports() throws Exception {
        // Initialize the database
        dailyReportsRepository.saveAndFlush(dailyReports);

        // Get the dailyReports
        restDailyReportsMockMvc
            .perform(get(ENTITY_API_URL_ID, dailyReports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyReports.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.totalBtc").value(DEFAULT_TOTAL_BTC.doubleValue()))
            .andExpect(jsonPath("$.totalFiatUsd").value(DEFAULT_TOTAL_FIAT_USD.doubleValue()))
            .andExpect(jsonPath("$.openOrdersBtc").value(DEFAULT_OPEN_ORDERS_BTC.doubleValue()))
            .andExpect(jsonPath("$.btcPerUser").value(DEFAULT_BTC_PER_USER.doubleValue()))
            .andExpect(jsonPath("$.transactionsBtc").value(DEFAULT_TRANSACTIONS_BTC.doubleValue()))
            .andExpect(jsonPath("$.avgTransactionSizeBtc").value(DEFAULT_AVG_TRANSACTION_SIZE_BTC.doubleValue()))
            .andExpect(jsonPath("$.transactionVolumePerUser").value(DEFAULT_TRANSACTION_VOLUME_PER_USER.doubleValue()))
            .andExpect(jsonPath("$.totalFeesBtc").value(DEFAULT_TOTAL_FEES_BTC.doubleValue()))
            .andExpect(jsonPath("$.feesPerUserBtc").value(DEFAULT_FEES_PER_USER_BTC.doubleValue()))
            .andExpect(jsonPath("$.usdPerUser").value(DEFAULT_USD_PER_USER.doubleValue()))
            .andExpect(jsonPath("$.grossProfitBtc").value(DEFAULT_GROSS_PROFIT_BTC.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDailyReports() throws Exception {
        // Get the dailyReports
        restDailyReportsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDailyReports() throws Exception {
        // Initialize the database
        dailyReportsRepository.saveAndFlush(dailyReports);

        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();

        // Update the dailyReports
        DailyReports updatedDailyReports = dailyReportsRepository.findById(dailyReports.getId()).get();
        // Disconnect from session so that the updates on updatedDailyReports are not directly saved in db
        em.detach(updatedDailyReports);
        updatedDailyReports
            .date(UPDATED_DATE)
            .totalBtc(UPDATED_TOTAL_BTC)
            .totalFiatUsd(UPDATED_TOTAL_FIAT_USD)
            .openOrdersBtc(UPDATED_OPEN_ORDERS_BTC)
            .btcPerUser(UPDATED_BTC_PER_USER)
            .transactionsBtc(UPDATED_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(UPDATED_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(UPDATED_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(UPDATED_TOTAL_FEES_BTC)
            .feesPerUserBtc(UPDATED_FEES_PER_USER_BTC)
            .usdPerUser(UPDATED_USD_PER_USER)
            .grossProfitBtc(UPDATED_GROSS_PROFIT_BTC);
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(updatedDailyReports);

        restDailyReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyReportsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
        DailyReports testDailyReports = dailyReportsList.get(dailyReportsList.size() - 1);
        assertThat(testDailyReports.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDailyReports.getTotalBtc()).isEqualTo(UPDATED_TOTAL_BTC);
        assertThat(testDailyReports.getTotalFiatUsd()).isEqualTo(UPDATED_TOTAL_FIAT_USD);
        assertThat(testDailyReports.getOpenOrdersBtc()).isEqualTo(UPDATED_OPEN_ORDERS_BTC);
        assertThat(testDailyReports.getBtcPerUser()).isEqualTo(UPDATED_BTC_PER_USER);
        assertThat(testDailyReports.getTransactionsBtc()).isEqualTo(UPDATED_TRANSACTIONS_BTC);
        assertThat(testDailyReports.getAvgTransactionSizeBtc()).isEqualTo(UPDATED_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testDailyReports.getTransactionVolumePerUser()).isEqualTo(UPDATED_TRANSACTION_VOLUME_PER_USER);
        assertThat(testDailyReports.getTotalFeesBtc()).isEqualTo(UPDATED_TOTAL_FEES_BTC);
        assertThat(testDailyReports.getFeesPerUserBtc()).isEqualTo(UPDATED_FEES_PER_USER_BTC);
        assertThat(testDailyReports.getUsdPerUser()).isEqualTo(UPDATED_USD_PER_USER);
        assertThat(testDailyReports.getGrossProfitBtc()).isEqualTo(UPDATED_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void putNonExistingDailyReports() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();
        dailyReports.setId(count.incrementAndGet());

        // Create the DailyReports
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyReportsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDailyReports() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();
        dailyReports.setId(count.incrementAndGet());

        // Create the DailyReports
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDailyReports() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();
        dailyReports.setId(count.incrementAndGet());

        // Create the DailyReports
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDailyReportsWithPatch() throws Exception {
        // Initialize the database
        dailyReportsRepository.saveAndFlush(dailyReports);

        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();

        // Update the dailyReports using partial update
        DailyReports partialUpdatedDailyReports = new DailyReports();
        partialUpdatedDailyReports.setId(dailyReports.getId());

        partialUpdatedDailyReports
            .totalBtc(UPDATED_TOTAL_BTC)
            .totalFiatUsd(UPDATED_TOTAL_FIAT_USD)
            .openOrdersBtc(UPDATED_OPEN_ORDERS_BTC)
            .avgTransactionSizeBtc(UPDATED_AVG_TRANSACTION_SIZE_BTC)
            .feesPerUserBtc(UPDATED_FEES_PER_USER_BTC)
            .usdPerUser(UPDATED_USD_PER_USER);

        restDailyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyReports.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyReports))
            )
            .andExpect(status().isOk());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
        DailyReports testDailyReports = dailyReportsList.get(dailyReportsList.size() - 1);
        assertThat(testDailyReports.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDailyReports.getTotalBtc()).isEqualTo(UPDATED_TOTAL_BTC);
        assertThat(testDailyReports.getTotalFiatUsd()).isEqualTo(UPDATED_TOTAL_FIAT_USD);
        assertThat(testDailyReports.getOpenOrdersBtc()).isEqualTo(UPDATED_OPEN_ORDERS_BTC);
        assertThat(testDailyReports.getBtcPerUser()).isEqualTo(DEFAULT_BTC_PER_USER);
        assertThat(testDailyReports.getTransactionsBtc()).isEqualTo(DEFAULT_TRANSACTIONS_BTC);
        assertThat(testDailyReports.getAvgTransactionSizeBtc()).isEqualTo(UPDATED_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testDailyReports.getTransactionVolumePerUser()).isEqualTo(DEFAULT_TRANSACTION_VOLUME_PER_USER);
        assertThat(testDailyReports.getTotalFeesBtc()).isEqualTo(DEFAULT_TOTAL_FEES_BTC);
        assertThat(testDailyReports.getFeesPerUserBtc()).isEqualTo(UPDATED_FEES_PER_USER_BTC);
        assertThat(testDailyReports.getUsdPerUser()).isEqualTo(UPDATED_USD_PER_USER);
        assertThat(testDailyReports.getGrossProfitBtc()).isEqualTo(DEFAULT_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void fullUpdateDailyReportsWithPatch() throws Exception {
        // Initialize the database
        dailyReportsRepository.saveAndFlush(dailyReports);

        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();

        // Update the dailyReports using partial update
        DailyReports partialUpdatedDailyReports = new DailyReports();
        partialUpdatedDailyReports.setId(dailyReports.getId());

        partialUpdatedDailyReports
            .date(UPDATED_DATE)
            .totalBtc(UPDATED_TOTAL_BTC)
            .totalFiatUsd(UPDATED_TOTAL_FIAT_USD)
            .openOrdersBtc(UPDATED_OPEN_ORDERS_BTC)
            .btcPerUser(UPDATED_BTC_PER_USER)
            .transactionsBtc(UPDATED_TRANSACTIONS_BTC)
            .avgTransactionSizeBtc(UPDATED_AVG_TRANSACTION_SIZE_BTC)
            .transactionVolumePerUser(UPDATED_TRANSACTION_VOLUME_PER_USER)
            .totalFeesBtc(UPDATED_TOTAL_FEES_BTC)
            .feesPerUserBtc(UPDATED_FEES_PER_USER_BTC)
            .usdPerUser(UPDATED_USD_PER_USER)
            .grossProfitBtc(UPDATED_GROSS_PROFIT_BTC);

        restDailyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyReports.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyReports))
            )
            .andExpect(status().isOk());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
        DailyReports testDailyReports = dailyReportsList.get(dailyReportsList.size() - 1);
        assertThat(testDailyReports.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDailyReports.getTotalBtc()).isEqualTo(UPDATED_TOTAL_BTC);
        assertThat(testDailyReports.getTotalFiatUsd()).isEqualTo(UPDATED_TOTAL_FIAT_USD);
        assertThat(testDailyReports.getOpenOrdersBtc()).isEqualTo(UPDATED_OPEN_ORDERS_BTC);
        assertThat(testDailyReports.getBtcPerUser()).isEqualTo(UPDATED_BTC_PER_USER);
        assertThat(testDailyReports.getTransactionsBtc()).isEqualTo(UPDATED_TRANSACTIONS_BTC);
        assertThat(testDailyReports.getAvgTransactionSizeBtc()).isEqualTo(UPDATED_AVG_TRANSACTION_SIZE_BTC);
        assertThat(testDailyReports.getTransactionVolumePerUser()).isEqualTo(UPDATED_TRANSACTION_VOLUME_PER_USER);
        assertThat(testDailyReports.getTotalFeesBtc()).isEqualTo(UPDATED_TOTAL_FEES_BTC);
        assertThat(testDailyReports.getFeesPerUserBtc()).isEqualTo(UPDATED_FEES_PER_USER_BTC);
        assertThat(testDailyReports.getUsdPerUser()).isEqualTo(UPDATED_USD_PER_USER);
        assertThat(testDailyReports.getGrossProfitBtc()).isEqualTo(UPDATED_GROSS_PROFIT_BTC);
    }

    @Test
    @Transactional
    void patchNonExistingDailyReports() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();
        dailyReports.setId(count.incrementAndGet());

        // Create the DailyReports
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dailyReportsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDailyReports() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();
        dailyReports.setId(count.incrementAndGet());

        // Create the DailyReports
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDailyReports() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportsRepository.findAll().size();
        dailyReports.setId(count.incrementAndGet());

        // Create the DailyReports
        DailyReportsDTO dailyReportsDTO = dailyReportsMapper.toDto(dailyReports);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyReportsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyReports in the database
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDailyReports() throws Exception {
        // Initialize the database
        dailyReportsRepository.saveAndFlush(dailyReports);

        int databaseSizeBeforeDelete = dailyReportsRepository.findAll().size();

        // Delete the dailyReports
        restDailyReportsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dailyReports.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyReports> dailyReportsList = dailyReportsRepository.findAll();
        assertThat(dailyReportsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
