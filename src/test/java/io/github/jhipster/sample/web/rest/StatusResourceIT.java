package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Status;
import io.github.jhipster.sample.repository.StatusRepository;
import io.github.jhipster.sample.service.dto.StatusDTO;
import io.github.jhipster.sample.service.mapper.StatusMapper;
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
 * Integration tests for the {@link StatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatusResourceIT {

    private static final Instant DEFAULT_LAST_SWEEP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_SWEEP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_DEFICIT_BTC = 1D;
    private static final Double UPDATED_DEFICIT_BTC = 2D;

    private static final Double DEFAULT_HOT_WALLET_BTC = 1D;
    private static final Double UPDATED_HOT_WALLET_BTC = 2D;

    private static final Double DEFAULT_WARM_WALLET_BTC = 1D;
    private static final Double UPDATED_WARM_WALLET_BTC = 2D;

    private static final Double DEFAULT_TOTAL_BTC = 1D;
    private static final Double UPDATED_TOTAL_BTC = 2D;

    private static final Double DEFAULT_RECEIVED_BTC_PENDING = 1D;
    private static final Double UPDATED_RECEIVED_BTC_PENDING = 2D;

    private static final Double DEFAULT_PENDING_WITHDRAWALS = 1D;
    private static final Double UPDATED_PENDING_WITHDRAWALS = 2D;

    private static final String DEFAULT_TRADING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_WITHDRAWALS_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_WITHDRAWALS_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_DB_VERSION = 1D;
    private static final Double UPDATED_DB_VERSION = 2D;

    private static final Instant DEFAULT_CRON_DAILY_STATS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRON_DAILY_STATS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CRON_GET_STATS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRON_GET_STATS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CRON_MAINTENANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRON_MAINTENANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CRON_MONTHLY_STATS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRON_MONTHLY_STATS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CRON_RECEIVE_BITCOIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRON_RECEIVE_BITCOIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CRON_SEND_BITCOIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRON_SEND_BITCOIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusMockMvc;

    private Status status;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createEntity(EntityManager em) {
        Status status = new Status()
            .lastSweep(DEFAULT_LAST_SWEEP)
            .deficitBtc(DEFAULT_DEFICIT_BTC)
            .hotWalletBtc(DEFAULT_HOT_WALLET_BTC)
            .warmWalletBtc(DEFAULT_WARM_WALLET_BTC)
            .totalBtc(DEFAULT_TOTAL_BTC)
            .receivedBtcPending(DEFAULT_RECEIVED_BTC_PENDING)
            .pendingWithdrawals(DEFAULT_PENDING_WITHDRAWALS)
            .tradingStatus(DEFAULT_TRADING_STATUS)
            .withdrawalsStatus(DEFAULT_WITHDRAWALS_STATUS)
            .dbVersion(DEFAULT_DB_VERSION)
            .cronDailyStats(DEFAULT_CRON_DAILY_STATS)
            .cronGetStats(DEFAULT_CRON_GET_STATS)
            .cronMaintenance(DEFAULT_CRON_MAINTENANCE)
            .cronMonthlyStats(DEFAULT_CRON_MONTHLY_STATS)
            .cronReceiveBitcoin(DEFAULT_CRON_RECEIVE_BITCOIN)
            .cronSendBitcoin(DEFAULT_CRON_SEND_BITCOIN);
        return status;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createUpdatedEntity(EntityManager em) {
        Status status = new Status()
            .lastSweep(UPDATED_LAST_SWEEP)
            .deficitBtc(UPDATED_DEFICIT_BTC)
            .hotWalletBtc(UPDATED_HOT_WALLET_BTC)
            .warmWalletBtc(UPDATED_WARM_WALLET_BTC)
            .totalBtc(UPDATED_TOTAL_BTC)
            .receivedBtcPending(UPDATED_RECEIVED_BTC_PENDING)
            .pendingWithdrawals(UPDATED_PENDING_WITHDRAWALS)
            .tradingStatus(UPDATED_TRADING_STATUS)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS)
            .dbVersion(UPDATED_DB_VERSION)
            .cronDailyStats(UPDATED_CRON_DAILY_STATS)
            .cronGetStats(UPDATED_CRON_GET_STATS)
            .cronMaintenance(UPDATED_CRON_MAINTENANCE)
            .cronMonthlyStats(UPDATED_CRON_MONTHLY_STATS)
            .cronReceiveBitcoin(UPDATED_CRON_RECEIVE_BITCOIN)
            .cronSendBitcoin(UPDATED_CRON_SEND_BITCOIN);
        return status;
    }

    @BeforeEach
    public void initTest() {
        status = createEntity(em);
    }

    @Test
    @Transactional
    void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();
        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);
        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getLastSweep()).isEqualTo(DEFAULT_LAST_SWEEP);
        assertThat(testStatus.getDeficitBtc()).isEqualTo(DEFAULT_DEFICIT_BTC);
        assertThat(testStatus.getHotWalletBtc()).isEqualTo(DEFAULT_HOT_WALLET_BTC);
        assertThat(testStatus.getWarmWalletBtc()).isEqualTo(DEFAULT_WARM_WALLET_BTC);
        assertThat(testStatus.getTotalBtc()).isEqualTo(DEFAULT_TOTAL_BTC);
        assertThat(testStatus.getReceivedBtcPending()).isEqualTo(DEFAULT_RECEIVED_BTC_PENDING);
        assertThat(testStatus.getPendingWithdrawals()).isEqualTo(DEFAULT_PENDING_WITHDRAWALS);
        assertThat(testStatus.getTradingStatus()).isEqualTo(DEFAULT_TRADING_STATUS);
        assertThat(testStatus.getWithdrawalsStatus()).isEqualTo(DEFAULT_WITHDRAWALS_STATUS);
        assertThat(testStatus.getDbVersion()).isEqualTo(DEFAULT_DB_VERSION);
        assertThat(testStatus.getCronDailyStats()).isEqualTo(DEFAULT_CRON_DAILY_STATS);
        assertThat(testStatus.getCronGetStats()).isEqualTo(DEFAULT_CRON_GET_STATS);
        assertThat(testStatus.getCronMaintenance()).isEqualTo(DEFAULT_CRON_MAINTENANCE);
        assertThat(testStatus.getCronMonthlyStats()).isEqualTo(DEFAULT_CRON_MONTHLY_STATS);
        assertThat(testStatus.getCronReceiveBitcoin()).isEqualTo(DEFAULT_CRON_RECEIVE_BITCOIN);
        assertThat(testStatus.getCronSendBitcoin()).isEqualTo(DEFAULT_CRON_SEND_BITCOIN);
    }

    @Test
    @Transactional
    void createStatusWithExistingId() throws Exception {
        // Create the Status with an existing ID
        status.setId(1L);
        StatusDTO statusDTO = statusMapper.toDto(status);

        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastSweepIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setLastSweep(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeficitBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setDeficitBtc(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHotWalletBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setHotWalletBtc(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWarmWalletBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setWarmWalletBtc(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setTotalBtc(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReceivedBtcPendingIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setReceivedBtcPending(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPendingWithdrawalsIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setPendingWithdrawals(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradingStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setTradingStatus(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWithdrawalsStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setWithdrawalsStatus(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDbVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setDbVersion(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronDailyStatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setCronDailyStats(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronGetStatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setCronGetStats(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronMaintenanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setCronMaintenance(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronMonthlyStatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setCronMonthlyStats(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronReceiveBitcoinIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setCronReceiveBitcoin(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronSendBitcoinIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setCronSendBitcoin(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastSweep").value(hasItem(DEFAULT_LAST_SWEEP.toString())))
            .andExpect(jsonPath("$.[*].deficitBtc").value(hasItem(DEFAULT_DEFICIT_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].hotWalletBtc").value(hasItem(DEFAULT_HOT_WALLET_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].warmWalletBtc").value(hasItem(DEFAULT_WARM_WALLET_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].totalBtc").value(hasItem(DEFAULT_TOTAL_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedBtcPending").value(hasItem(DEFAULT_RECEIVED_BTC_PENDING.doubleValue())))
            .andExpect(jsonPath("$.[*].pendingWithdrawals").value(hasItem(DEFAULT_PENDING_WITHDRAWALS.doubleValue())))
            .andExpect(jsonPath("$.[*].tradingStatus").value(hasItem(DEFAULT_TRADING_STATUS)))
            .andExpect(jsonPath("$.[*].withdrawalsStatus").value(hasItem(DEFAULT_WITHDRAWALS_STATUS)))
            .andExpect(jsonPath("$.[*].dbVersion").value(hasItem(DEFAULT_DB_VERSION.doubleValue())))
            .andExpect(jsonPath("$.[*].cronDailyStats").value(hasItem(DEFAULT_CRON_DAILY_STATS.toString())))
            .andExpect(jsonPath("$.[*].cronGetStats").value(hasItem(DEFAULT_CRON_GET_STATS.toString())))
            .andExpect(jsonPath("$.[*].cronMaintenance").value(hasItem(DEFAULT_CRON_MAINTENANCE.toString())))
            .andExpect(jsonPath("$.[*].cronMonthlyStats").value(hasItem(DEFAULT_CRON_MONTHLY_STATS.toString())))
            .andExpect(jsonPath("$.[*].cronReceiveBitcoin").value(hasItem(DEFAULT_CRON_RECEIVE_BITCOIN.toString())))
            .andExpect(jsonPath("$.[*].cronSendBitcoin").value(hasItem(DEFAULT_CRON_SEND_BITCOIN.toString())));
    }

    @Test
    @Transactional
    void getStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get the status
        restStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId().intValue()))
            .andExpect(jsonPath("$.lastSweep").value(DEFAULT_LAST_SWEEP.toString()))
            .andExpect(jsonPath("$.deficitBtc").value(DEFAULT_DEFICIT_BTC.doubleValue()))
            .andExpect(jsonPath("$.hotWalletBtc").value(DEFAULT_HOT_WALLET_BTC.doubleValue()))
            .andExpect(jsonPath("$.warmWalletBtc").value(DEFAULT_WARM_WALLET_BTC.doubleValue()))
            .andExpect(jsonPath("$.totalBtc").value(DEFAULT_TOTAL_BTC.doubleValue()))
            .andExpect(jsonPath("$.receivedBtcPending").value(DEFAULT_RECEIVED_BTC_PENDING.doubleValue()))
            .andExpect(jsonPath("$.pendingWithdrawals").value(DEFAULT_PENDING_WITHDRAWALS.doubleValue()))
            .andExpect(jsonPath("$.tradingStatus").value(DEFAULT_TRADING_STATUS))
            .andExpect(jsonPath("$.withdrawalsStatus").value(DEFAULT_WITHDRAWALS_STATUS))
            .andExpect(jsonPath("$.dbVersion").value(DEFAULT_DB_VERSION.doubleValue()))
            .andExpect(jsonPath("$.cronDailyStats").value(DEFAULT_CRON_DAILY_STATS.toString()))
            .andExpect(jsonPath("$.cronGetStats").value(DEFAULT_CRON_GET_STATS.toString()))
            .andExpect(jsonPath("$.cronMaintenance").value(DEFAULT_CRON_MAINTENANCE.toString()))
            .andExpect(jsonPath("$.cronMonthlyStats").value(DEFAULT_CRON_MONTHLY_STATS.toString()))
            .andExpect(jsonPath("$.cronReceiveBitcoin").value(DEFAULT_CRON_RECEIVE_BITCOIN.toString()))
            .andExpect(jsonPath("$.cronSendBitcoin").value(DEFAULT_CRON_SEND_BITCOIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findById(status.getId()).get();
        // Disconnect from session so that the updates on updatedStatus are not directly saved in db
        em.detach(updatedStatus);
        updatedStatus
            .lastSweep(UPDATED_LAST_SWEEP)
            .deficitBtc(UPDATED_DEFICIT_BTC)
            .hotWalletBtc(UPDATED_HOT_WALLET_BTC)
            .warmWalletBtc(UPDATED_WARM_WALLET_BTC)
            .totalBtc(UPDATED_TOTAL_BTC)
            .receivedBtcPending(UPDATED_RECEIVED_BTC_PENDING)
            .pendingWithdrawals(UPDATED_PENDING_WITHDRAWALS)
            .tradingStatus(UPDATED_TRADING_STATUS)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS)
            .dbVersion(UPDATED_DB_VERSION)
            .cronDailyStats(UPDATED_CRON_DAILY_STATS)
            .cronGetStats(UPDATED_CRON_GET_STATS)
            .cronMaintenance(UPDATED_CRON_MAINTENANCE)
            .cronMonthlyStats(UPDATED_CRON_MONTHLY_STATS)
            .cronReceiveBitcoin(UPDATED_CRON_RECEIVE_BITCOIN)
            .cronSendBitcoin(UPDATED_CRON_SEND_BITCOIN);
        StatusDTO statusDTO = statusMapper.toDto(updatedStatus);

        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getLastSweep()).isEqualTo(UPDATED_LAST_SWEEP);
        assertThat(testStatus.getDeficitBtc()).isEqualTo(UPDATED_DEFICIT_BTC);
        assertThat(testStatus.getHotWalletBtc()).isEqualTo(UPDATED_HOT_WALLET_BTC);
        assertThat(testStatus.getWarmWalletBtc()).isEqualTo(UPDATED_WARM_WALLET_BTC);
        assertThat(testStatus.getTotalBtc()).isEqualTo(UPDATED_TOTAL_BTC);
        assertThat(testStatus.getReceivedBtcPending()).isEqualTo(UPDATED_RECEIVED_BTC_PENDING);
        assertThat(testStatus.getPendingWithdrawals()).isEqualTo(UPDATED_PENDING_WITHDRAWALS);
        assertThat(testStatus.getTradingStatus()).isEqualTo(UPDATED_TRADING_STATUS);
        assertThat(testStatus.getWithdrawalsStatus()).isEqualTo(UPDATED_WITHDRAWALS_STATUS);
        assertThat(testStatus.getDbVersion()).isEqualTo(UPDATED_DB_VERSION);
        assertThat(testStatus.getCronDailyStats()).isEqualTo(UPDATED_CRON_DAILY_STATS);
        assertThat(testStatus.getCronGetStats()).isEqualTo(UPDATED_CRON_GET_STATS);
        assertThat(testStatus.getCronMaintenance()).isEqualTo(UPDATED_CRON_MAINTENANCE);
        assertThat(testStatus.getCronMonthlyStats()).isEqualTo(UPDATED_CRON_MONTHLY_STATS);
        assertThat(testStatus.getCronReceiveBitcoin()).isEqualTo(UPDATED_CRON_RECEIVE_BITCOIN);
        assertThat(testStatus.getCronSendBitcoin()).isEqualTo(UPDATED_CRON_SEND_BITCOIN);
    }

    @Test
    @Transactional
    void putNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusWithPatch() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status using partial update
        Status partialUpdatedStatus = new Status();
        partialUpdatedStatus.setId(status.getId());

        partialUpdatedStatus
            .hotWalletBtc(UPDATED_HOT_WALLET_BTC)
            .receivedBtcPending(UPDATED_RECEIVED_BTC_PENDING)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS)
            .cronMaintenance(UPDATED_CRON_MAINTENANCE);

        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatus))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getLastSweep()).isEqualTo(DEFAULT_LAST_SWEEP);
        assertThat(testStatus.getDeficitBtc()).isEqualTo(DEFAULT_DEFICIT_BTC);
        assertThat(testStatus.getHotWalletBtc()).isEqualTo(UPDATED_HOT_WALLET_BTC);
        assertThat(testStatus.getWarmWalletBtc()).isEqualTo(DEFAULT_WARM_WALLET_BTC);
        assertThat(testStatus.getTotalBtc()).isEqualTo(DEFAULT_TOTAL_BTC);
        assertThat(testStatus.getReceivedBtcPending()).isEqualTo(UPDATED_RECEIVED_BTC_PENDING);
        assertThat(testStatus.getPendingWithdrawals()).isEqualTo(DEFAULT_PENDING_WITHDRAWALS);
        assertThat(testStatus.getTradingStatus()).isEqualTo(DEFAULT_TRADING_STATUS);
        assertThat(testStatus.getWithdrawalsStatus()).isEqualTo(UPDATED_WITHDRAWALS_STATUS);
        assertThat(testStatus.getDbVersion()).isEqualTo(DEFAULT_DB_VERSION);
        assertThat(testStatus.getCronDailyStats()).isEqualTo(DEFAULT_CRON_DAILY_STATS);
        assertThat(testStatus.getCronGetStats()).isEqualTo(DEFAULT_CRON_GET_STATS);
        assertThat(testStatus.getCronMaintenance()).isEqualTo(UPDATED_CRON_MAINTENANCE);
        assertThat(testStatus.getCronMonthlyStats()).isEqualTo(DEFAULT_CRON_MONTHLY_STATS);
        assertThat(testStatus.getCronReceiveBitcoin()).isEqualTo(DEFAULT_CRON_RECEIVE_BITCOIN);
        assertThat(testStatus.getCronSendBitcoin()).isEqualTo(DEFAULT_CRON_SEND_BITCOIN);
    }

    @Test
    @Transactional
    void fullUpdateStatusWithPatch() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status using partial update
        Status partialUpdatedStatus = new Status();
        partialUpdatedStatus.setId(status.getId());

        partialUpdatedStatus
            .lastSweep(UPDATED_LAST_SWEEP)
            .deficitBtc(UPDATED_DEFICIT_BTC)
            .hotWalletBtc(UPDATED_HOT_WALLET_BTC)
            .warmWalletBtc(UPDATED_WARM_WALLET_BTC)
            .totalBtc(UPDATED_TOTAL_BTC)
            .receivedBtcPending(UPDATED_RECEIVED_BTC_PENDING)
            .pendingWithdrawals(UPDATED_PENDING_WITHDRAWALS)
            .tradingStatus(UPDATED_TRADING_STATUS)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS)
            .dbVersion(UPDATED_DB_VERSION)
            .cronDailyStats(UPDATED_CRON_DAILY_STATS)
            .cronGetStats(UPDATED_CRON_GET_STATS)
            .cronMaintenance(UPDATED_CRON_MAINTENANCE)
            .cronMonthlyStats(UPDATED_CRON_MONTHLY_STATS)
            .cronReceiveBitcoin(UPDATED_CRON_RECEIVE_BITCOIN)
            .cronSendBitcoin(UPDATED_CRON_SEND_BITCOIN);

        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatus))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getLastSweep()).isEqualTo(UPDATED_LAST_SWEEP);
        assertThat(testStatus.getDeficitBtc()).isEqualTo(UPDATED_DEFICIT_BTC);
        assertThat(testStatus.getHotWalletBtc()).isEqualTo(UPDATED_HOT_WALLET_BTC);
        assertThat(testStatus.getWarmWalletBtc()).isEqualTo(UPDATED_WARM_WALLET_BTC);
        assertThat(testStatus.getTotalBtc()).isEqualTo(UPDATED_TOTAL_BTC);
        assertThat(testStatus.getReceivedBtcPending()).isEqualTo(UPDATED_RECEIVED_BTC_PENDING);
        assertThat(testStatus.getPendingWithdrawals()).isEqualTo(UPDATED_PENDING_WITHDRAWALS);
        assertThat(testStatus.getTradingStatus()).isEqualTo(UPDATED_TRADING_STATUS);
        assertThat(testStatus.getWithdrawalsStatus()).isEqualTo(UPDATED_WITHDRAWALS_STATUS);
        assertThat(testStatus.getDbVersion()).isEqualTo(UPDATED_DB_VERSION);
        assertThat(testStatus.getCronDailyStats()).isEqualTo(UPDATED_CRON_DAILY_STATS);
        assertThat(testStatus.getCronGetStats()).isEqualTo(UPDATED_CRON_GET_STATS);
        assertThat(testStatus.getCronMaintenance()).isEqualTo(UPDATED_CRON_MAINTENANCE);
        assertThat(testStatus.getCronMonthlyStats()).isEqualTo(UPDATED_CRON_MONTHLY_STATS);
        assertThat(testStatus.getCronReceiveBitcoin()).isEqualTo(UPDATED_CRON_RECEIVE_BITCOIN);
        assertThat(testStatus.getCronSendBitcoin()).isEqualTo(UPDATED_CRON_SEND_BITCOIN);
    }

    @Test
    @Transactional
    void patchNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Delete the status
        restStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, status.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
