package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Transactions;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.TransactionsRepository;
import io.github.jhipster.sample.service.dto.TransactionsDTO;
import io.github.jhipster.sample.service.mapper.TransactionsMapper;
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
 * Integration tests for the {@link TransactionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionsResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_BTC = 1D;
    private static final Double UPDATED_BTC = 2D;

    private static final Double DEFAULT_BTC_PRICE = 1D;
    private static final Double UPDATED_BTC_PRICE = 2D;

    private static final Double DEFAULT_FIAT = 1D;
    private static final Double UPDATED_FIAT = 2D;

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    private static final Double DEFAULT_FEE_1 = 1D;
    private static final Double UPDATED_FEE_1 = 2D;

    private static final Double DEFAULT_BTC_NET = 1D;
    private static final Double UPDATED_BTC_NET = 2D;

    private static final Double DEFAULT_BTC_NET_1 = 1D;
    private static final Double UPDATED_BTC_NET_1 = 2D;

    private static final Double DEFAULT_BTC_BEFORE_1 = 1D;
    private static final Double UPDATED_BTC_BEFORE_1 = 2D;

    private static final Double DEFAULT_BTC_AFTER_1 = 1D;
    private static final Double UPDATED_BTC_AFTER_1 = 2D;

    private static final Double DEFAULT_FIAT_BEFORE_1 = 1D;
    private static final Double UPDATED_FIAT_BEFORE_1 = 2D;

    private static final Double DEFAULT_FIAT_AFTER_1 = 1D;
    private static final Double UPDATED_FIAT_AFTER_1 = 2D;

    private static final Double DEFAULT_BTC_BEFORE = 1D;
    private static final Double UPDATED_BTC_BEFORE = 2D;

    private static final Double DEFAULT_BTC_AFTER = 1D;
    private static final Double UPDATED_BTC_AFTER = 2D;

    private static final Double DEFAULT_FIAT_BEFORE = 1D;
    private static final Double UPDATED_FIAT_BEFORE = 2D;

    private static final Double DEFAULT_FIAT_AFTER = 1D;
    private static final Double UPDATED_FIAT_AFTER = 2D;

    private static final Double DEFAULT_FEE_LEVEL = 1D;
    private static final Double UPDATED_FEE_LEVEL = 2D;

    private static final Double DEFAULT_FEE_LEVEL_1 = 1D;
    private static final Double UPDATED_FEE_LEVEL_1 = 2D;

    private static final Double DEFAULT_ORIG_BTC_PRICE = 1D;
    private static final Double UPDATED_ORIG_BTC_PRICE = 2D;

    private static final Double DEFAULT_CONVERSION_FEE = 1D;
    private static final Double UPDATED_CONVERSION_FEE = 2D;

    private static final Double DEFAULT_CONVERT_AMOUNT = 1D;
    private static final Double UPDATED_CONVERT_AMOUNT = 2D;

    private static final Double DEFAULT_CONVERT_RATE_GIVEN = 1D;
    private static final Double UPDATED_CONVERT_RATE_GIVEN = 2D;

    private static final Double DEFAULT_CONVERT_SYSTEM_RATE = 1D;
    private static final Double UPDATED_CONVERT_SYSTEM_RATE = 2D;

    private static final YesNo DEFAULT_CONVERSION = YesNo.Y;
    private static final YesNo UPDATED_CONVERSION = YesNo.N;

    private static final Double DEFAULT_BID_AT_TRANSACTION = 1D;
    private static final Double UPDATED_BID_AT_TRANSACTION = 2D;

    private static final Double DEFAULT_ASK_AT_TRANSACTION = 1D;
    private static final Double UPDATED_ASK_AT_TRANSACTION = 2D;

    private static final YesNo DEFAULT_FACTORED = YesNo.Y;
    private static final YesNo UPDATED_FACTORED = YesNo.N;

    private static final String ENTITY_API_URL = "/api/transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private TransactionsMapper transactionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionsMockMvc;

    private Transactions transactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transactions createEntity(EntityManager em) {
        Transactions transactions = new Transactions()
            .date(DEFAULT_DATE)
            .btc(DEFAULT_BTC)
            .btcPrice(DEFAULT_BTC_PRICE)
            .fiat(DEFAULT_FIAT)
            .fee(DEFAULT_FEE)
            .fee1(DEFAULT_FEE_1)
            .btcNet(DEFAULT_BTC_NET)
            .btcNet1(DEFAULT_BTC_NET_1)
            .btcBefore1(DEFAULT_BTC_BEFORE_1)
            .btcAfter1(DEFAULT_BTC_AFTER_1)
            .fiatBefore1(DEFAULT_FIAT_BEFORE_1)
            .fiatAfter1(DEFAULT_FIAT_AFTER_1)
            .btcBefore(DEFAULT_BTC_BEFORE)
            .btcAfter(DEFAULT_BTC_AFTER)
            .fiatBefore(DEFAULT_FIAT_BEFORE)
            .fiatAfter(DEFAULT_FIAT_AFTER)
            .feeLevel(DEFAULT_FEE_LEVEL)
            .feeLevel1(DEFAULT_FEE_LEVEL_1)
            .origBtcPrice(DEFAULT_ORIG_BTC_PRICE)
            .conversionFee(DEFAULT_CONVERSION_FEE)
            .convertAmount(DEFAULT_CONVERT_AMOUNT)
            .convertRateGiven(DEFAULT_CONVERT_RATE_GIVEN)
            .convertSystemRate(DEFAULT_CONVERT_SYSTEM_RATE)
            .conversion(DEFAULT_CONVERSION)
            .bidAtTransaction(DEFAULT_BID_AT_TRANSACTION)
            .askAtTransaction(DEFAULT_ASK_AT_TRANSACTION)
            .factored(DEFAULT_FACTORED);
        return transactions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transactions createUpdatedEntity(EntityManager em) {
        Transactions transactions = new Transactions()
            .date(UPDATED_DATE)
            .btc(UPDATED_BTC)
            .btcPrice(UPDATED_BTC_PRICE)
            .fiat(UPDATED_FIAT)
            .fee(UPDATED_FEE)
            .fee1(UPDATED_FEE_1)
            .btcNet(UPDATED_BTC_NET)
            .btcNet1(UPDATED_BTC_NET_1)
            .btcBefore1(UPDATED_BTC_BEFORE_1)
            .btcAfter1(UPDATED_BTC_AFTER_1)
            .fiatBefore1(UPDATED_FIAT_BEFORE_1)
            .fiatAfter1(UPDATED_FIAT_AFTER_1)
            .btcBefore(UPDATED_BTC_BEFORE)
            .btcAfter(UPDATED_BTC_AFTER)
            .fiatBefore(UPDATED_FIAT_BEFORE)
            .fiatAfter(UPDATED_FIAT_AFTER)
            .feeLevel(UPDATED_FEE_LEVEL)
            .feeLevel1(UPDATED_FEE_LEVEL_1)
            .origBtcPrice(UPDATED_ORIG_BTC_PRICE)
            .conversionFee(UPDATED_CONVERSION_FEE)
            .convertAmount(UPDATED_CONVERT_AMOUNT)
            .convertRateGiven(UPDATED_CONVERT_RATE_GIVEN)
            .convertSystemRate(UPDATED_CONVERT_SYSTEM_RATE)
            .conversion(UPDATED_CONVERSION)
            .bidAtTransaction(UPDATED_BID_AT_TRANSACTION)
            .askAtTransaction(UPDATED_ASK_AT_TRANSACTION)
            .factored(UPDATED_FACTORED);
        return transactions;
    }

    @BeforeEach
    public void initTest() {
        transactions = createEntity(em);
    }

    @Test
    @Transactional
    void createTransactions() throws Exception {
        int databaseSizeBeforeCreate = transactionsRepository.findAll().size();
        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);
        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeCreate + 1);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTransactions.getBtc()).isEqualTo(DEFAULT_BTC);
        assertThat(testTransactions.getBtcPrice()).isEqualTo(DEFAULT_BTC_PRICE);
        assertThat(testTransactions.getFiat()).isEqualTo(DEFAULT_FIAT);
        assertThat(testTransactions.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testTransactions.getFee1()).isEqualTo(DEFAULT_FEE_1);
        assertThat(testTransactions.getBtcNet()).isEqualTo(DEFAULT_BTC_NET);
        assertThat(testTransactions.getBtcNet1()).isEqualTo(DEFAULT_BTC_NET_1);
        assertThat(testTransactions.getBtcBefore1()).isEqualTo(DEFAULT_BTC_BEFORE_1);
        assertThat(testTransactions.getBtcAfter1()).isEqualTo(DEFAULT_BTC_AFTER_1);
        assertThat(testTransactions.getFiatBefore1()).isEqualTo(DEFAULT_FIAT_BEFORE_1);
        assertThat(testTransactions.getFiatAfter1()).isEqualTo(DEFAULT_FIAT_AFTER_1);
        assertThat(testTransactions.getBtcBefore()).isEqualTo(DEFAULT_BTC_BEFORE);
        assertThat(testTransactions.getBtcAfter()).isEqualTo(DEFAULT_BTC_AFTER);
        assertThat(testTransactions.getFiatBefore()).isEqualTo(DEFAULT_FIAT_BEFORE);
        assertThat(testTransactions.getFiatAfter()).isEqualTo(DEFAULT_FIAT_AFTER);
        assertThat(testTransactions.getFeeLevel()).isEqualTo(DEFAULT_FEE_LEVEL);
        assertThat(testTransactions.getFeeLevel1()).isEqualTo(DEFAULT_FEE_LEVEL_1);
        assertThat(testTransactions.getOrigBtcPrice()).isEqualTo(DEFAULT_ORIG_BTC_PRICE);
        assertThat(testTransactions.getConversionFee()).isEqualTo(DEFAULT_CONVERSION_FEE);
        assertThat(testTransactions.getConvertAmount()).isEqualTo(DEFAULT_CONVERT_AMOUNT);
        assertThat(testTransactions.getConvertRateGiven()).isEqualTo(DEFAULT_CONVERT_RATE_GIVEN);
        assertThat(testTransactions.getConvertSystemRate()).isEqualTo(DEFAULT_CONVERT_SYSTEM_RATE);
        assertThat(testTransactions.getConversion()).isEqualTo(DEFAULT_CONVERSION);
        assertThat(testTransactions.getBidAtTransaction()).isEqualTo(DEFAULT_BID_AT_TRANSACTION);
        assertThat(testTransactions.getAskAtTransaction()).isEqualTo(DEFAULT_ASK_AT_TRANSACTION);
        assertThat(testTransactions.getFactored()).isEqualTo(DEFAULT_FACTORED);
    }

    @Test
    @Transactional
    void createTransactionsWithExistingId() throws Exception {
        // Create the Transactions with an existing ID
        transactions.setId(1L);
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        int databaseSizeBeforeCreate = transactionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setDate(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtc(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtcPrice(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiatIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFiat(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFee(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFee1IsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFee1(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcNetIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtcNet(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcNet1IsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtcNet1(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcBefore1IsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtcBefore1(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcAfter1IsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtcAfter1(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiatBefore1IsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFiatBefore1(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiatAfter1IsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFiatAfter1(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcBeforeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtcBefore(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcAfterIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBtcAfter(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiatBeforeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFiatBefore(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiatAfterIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFiatAfter(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFeeLevel(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeLevel1IsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFeeLevel1(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrigBtcPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setOrigBtcPrice(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConversionFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setConversionFee(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConvertAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setConvertAmount(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConvertRateGivenIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setConvertRateGiven(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConvertSystemRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setConvertSystemRate(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConversionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setConversion(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBidAtTransactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setBidAtTransaction(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAskAtTransactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setAskAtTransaction(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFactoredIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setFactored(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].btc").value(hasItem(DEFAULT_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].btcPrice").value(hasItem(DEFAULT_BTC_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].fiat").value(hasItem(DEFAULT_FIAT.doubleValue())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].fee1").value(hasItem(DEFAULT_FEE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].btcNet").value(hasItem(DEFAULT_BTC_NET.doubleValue())))
            .andExpect(jsonPath("$.[*].btcNet1").value(hasItem(DEFAULT_BTC_NET_1.doubleValue())))
            .andExpect(jsonPath("$.[*].btcBefore1").value(hasItem(DEFAULT_BTC_BEFORE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].btcAfter1").value(hasItem(DEFAULT_BTC_AFTER_1.doubleValue())))
            .andExpect(jsonPath("$.[*].fiatBefore1").value(hasItem(DEFAULT_FIAT_BEFORE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].fiatAfter1").value(hasItem(DEFAULT_FIAT_AFTER_1.doubleValue())))
            .andExpect(jsonPath("$.[*].btcBefore").value(hasItem(DEFAULT_BTC_BEFORE.doubleValue())))
            .andExpect(jsonPath("$.[*].btcAfter").value(hasItem(DEFAULT_BTC_AFTER.doubleValue())))
            .andExpect(jsonPath("$.[*].fiatBefore").value(hasItem(DEFAULT_FIAT_BEFORE.doubleValue())))
            .andExpect(jsonPath("$.[*].fiatAfter").value(hasItem(DEFAULT_FIAT_AFTER.doubleValue())))
            .andExpect(jsonPath("$.[*].feeLevel").value(hasItem(DEFAULT_FEE_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].feeLevel1").value(hasItem(DEFAULT_FEE_LEVEL_1.doubleValue())))
            .andExpect(jsonPath("$.[*].origBtcPrice").value(hasItem(DEFAULT_ORIG_BTC_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].conversionFee").value(hasItem(DEFAULT_CONVERSION_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].convertAmount").value(hasItem(DEFAULT_CONVERT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].convertRateGiven").value(hasItem(DEFAULT_CONVERT_RATE_GIVEN.doubleValue())))
            .andExpect(jsonPath("$.[*].convertSystemRate").value(hasItem(DEFAULT_CONVERT_SYSTEM_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].conversion").value(hasItem(DEFAULT_CONVERSION.toString())))
            .andExpect(jsonPath("$.[*].bidAtTransaction").value(hasItem(DEFAULT_BID_AT_TRANSACTION.doubleValue())))
            .andExpect(jsonPath("$.[*].askAtTransaction").value(hasItem(DEFAULT_ASK_AT_TRANSACTION.doubleValue())))
            .andExpect(jsonPath("$.[*].factored").value(hasItem(DEFAULT_FACTORED.toString())));
    }

    @Test
    @Transactional
    void getTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get the transactions
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL_ID, transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactions.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.btc").value(DEFAULT_BTC.doubleValue()))
            .andExpect(jsonPath("$.btcPrice").value(DEFAULT_BTC_PRICE.doubleValue()))
            .andExpect(jsonPath("$.fiat").value(DEFAULT_FIAT.doubleValue()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.doubleValue()))
            .andExpect(jsonPath("$.fee1").value(DEFAULT_FEE_1.doubleValue()))
            .andExpect(jsonPath("$.btcNet").value(DEFAULT_BTC_NET.doubleValue()))
            .andExpect(jsonPath("$.btcNet1").value(DEFAULT_BTC_NET_1.doubleValue()))
            .andExpect(jsonPath("$.btcBefore1").value(DEFAULT_BTC_BEFORE_1.doubleValue()))
            .andExpect(jsonPath("$.btcAfter1").value(DEFAULT_BTC_AFTER_1.doubleValue()))
            .andExpect(jsonPath("$.fiatBefore1").value(DEFAULT_FIAT_BEFORE_1.doubleValue()))
            .andExpect(jsonPath("$.fiatAfter1").value(DEFAULT_FIAT_AFTER_1.doubleValue()))
            .andExpect(jsonPath("$.btcBefore").value(DEFAULT_BTC_BEFORE.doubleValue()))
            .andExpect(jsonPath("$.btcAfter").value(DEFAULT_BTC_AFTER.doubleValue()))
            .andExpect(jsonPath("$.fiatBefore").value(DEFAULT_FIAT_BEFORE.doubleValue()))
            .andExpect(jsonPath("$.fiatAfter").value(DEFAULT_FIAT_AFTER.doubleValue()))
            .andExpect(jsonPath("$.feeLevel").value(DEFAULT_FEE_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.feeLevel1").value(DEFAULT_FEE_LEVEL_1.doubleValue()))
            .andExpect(jsonPath("$.origBtcPrice").value(DEFAULT_ORIG_BTC_PRICE.doubleValue()))
            .andExpect(jsonPath("$.conversionFee").value(DEFAULT_CONVERSION_FEE.doubleValue()))
            .andExpect(jsonPath("$.convertAmount").value(DEFAULT_CONVERT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.convertRateGiven").value(DEFAULT_CONVERT_RATE_GIVEN.doubleValue()))
            .andExpect(jsonPath("$.convertSystemRate").value(DEFAULT_CONVERT_SYSTEM_RATE.doubleValue()))
            .andExpect(jsonPath("$.conversion").value(DEFAULT_CONVERSION.toString()))
            .andExpect(jsonPath("$.bidAtTransaction").value(DEFAULT_BID_AT_TRANSACTION.doubleValue()))
            .andExpect(jsonPath("$.askAtTransaction").value(DEFAULT_ASK_AT_TRANSACTION.doubleValue()))
            .andExpect(jsonPath("$.factored").value(DEFAULT_FACTORED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTransactions() throws Exception {
        // Get the transactions
        restTransactionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Update the transactions
        Transactions updatedTransactions = transactionsRepository.findById(transactions.getId()).get();
        // Disconnect from session so that the updates on updatedTransactions are not directly saved in db
        em.detach(updatedTransactions);
        updatedTransactions
            .date(UPDATED_DATE)
            .btc(UPDATED_BTC)
            .btcPrice(UPDATED_BTC_PRICE)
            .fiat(UPDATED_FIAT)
            .fee(UPDATED_FEE)
            .fee1(UPDATED_FEE_1)
            .btcNet(UPDATED_BTC_NET)
            .btcNet1(UPDATED_BTC_NET_1)
            .btcBefore1(UPDATED_BTC_BEFORE_1)
            .btcAfter1(UPDATED_BTC_AFTER_1)
            .fiatBefore1(UPDATED_FIAT_BEFORE_1)
            .fiatAfter1(UPDATED_FIAT_AFTER_1)
            .btcBefore(UPDATED_BTC_BEFORE)
            .btcAfter(UPDATED_BTC_AFTER)
            .fiatBefore(UPDATED_FIAT_BEFORE)
            .fiatAfter(UPDATED_FIAT_AFTER)
            .feeLevel(UPDATED_FEE_LEVEL)
            .feeLevel1(UPDATED_FEE_LEVEL_1)
            .origBtcPrice(UPDATED_ORIG_BTC_PRICE)
            .conversionFee(UPDATED_CONVERSION_FEE)
            .convertAmount(UPDATED_CONVERT_AMOUNT)
            .convertRateGiven(UPDATED_CONVERT_RATE_GIVEN)
            .convertSystemRate(UPDATED_CONVERT_SYSTEM_RATE)
            .conversion(UPDATED_CONVERSION)
            .bidAtTransaction(UPDATED_BID_AT_TRANSACTION)
            .askAtTransaction(UPDATED_ASK_AT_TRANSACTION)
            .factored(UPDATED_FACTORED);
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(updatedTransactions);

        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransactions.getBtc()).isEqualTo(UPDATED_BTC);
        assertThat(testTransactions.getBtcPrice()).isEqualTo(UPDATED_BTC_PRICE);
        assertThat(testTransactions.getFiat()).isEqualTo(UPDATED_FIAT);
        assertThat(testTransactions.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testTransactions.getFee1()).isEqualTo(UPDATED_FEE_1);
        assertThat(testTransactions.getBtcNet()).isEqualTo(UPDATED_BTC_NET);
        assertThat(testTransactions.getBtcNet1()).isEqualTo(UPDATED_BTC_NET_1);
        assertThat(testTransactions.getBtcBefore1()).isEqualTo(UPDATED_BTC_BEFORE_1);
        assertThat(testTransactions.getBtcAfter1()).isEqualTo(UPDATED_BTC_AFTER_1);
        assertThat(testTransactions.getFiatBefore1()).isEqualTo(UPDATED_FIAT_BEFORE_1);
        assertThat(testTransactions.getFiatAfter1()).isEqualTo(UPDATED_FIAT_AFTER_1);
        assertThat(testTransactions.getBtcBefore()).isEqualTo(UPDATED_BTC_BEFORE);
        assertThat(testTransactions.getBtcAfter()).isEqualTo(UPDATED_BTC_AFTER);
        assertThat(testTransactions.getFiatBefore()).isEqualTo(UPDATED_FIAT_BEFORE);
        assertThat(testTransactions.getFiatAfter()).isEqualTo(UPDATED_FIAT_AFTER);
        assertThat(testTransactions.getFeeLevel()).isEqualTo(UPDATED_FEE_LEVEL);
        assertThat(testTransactions.getFeeLevel1()).isEqualTo(UPDATED_FEE_LEVEL_1);
        assertThat(testTransactions.getOrigBtcPrice()).isEqualTo(UPDATED_ORIG_BTC_PRICE);
        assertThat(testTransactions.getConversionFee()).isEqualTo(UPDATED_CONVERSION_FEE);
        assertThat(testTransactions.getConvertAmount()).isEqualTo(UPDATED_CONVERT_AMOUNT);
        assertThat(testTransactions.getConvertRateGiven()).isEqualTo(UPDATED_CONVERT_RATE_GIVEN);
        assertThat(testTransactions.getConvertSystemRate()).isEqualTo(UPDATED_CONVERT_SYSTEM_RATE);
        assertThat(testTransactions.getConversion()).isEqualTo(UPDATED_CONVERSION);
        assertThat(testTransactions.getBidAtTransaction()).isEqualTo(UPDATED_BID_AT_TRANSACTION);
        assertThat(testTransactions.getAskAtTransaction()).isEqualTo(UPDATED_ASK_AT_TRANSACTION);
        assertThat(testTransactions.getFactored()).isEqualTo(UPDATED_FACTORED);
    }

    @Test
    @Transactional
    void putNonExistingTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionsWithPatch() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Update the transactions using partial update
        Transactions partialUpdatedTransactions = new Transactions();
        partialUpdatedTransactions.setId(transactions.getId());

        partialUpdatedTransactions
            .date(UPDATED_DATE)
            .btc(UPDATED_BTC)
            .btcPrice(UPDATED_BTC_PRICE)
            .fiat(UPDATED_FIAT)
            .fee(UPDATED_FEE)
            .fee1(UPDATED_FEE_1)
            .btcNet1(UPDATED_BTC_NET_1)
            .fiatAfter1(UPDATED_FIAT_AFTER_1)
            .btcBefore(UPDATED_BTC_BEFORE)
            .btcAfter(UPDATED_BTC_AFTER)
            .origBtcPrice(UPDATED_ORIG_BTC_PRICE)
            .askAtTransaction(UPDATED_ASK_AT_TRANSACTION)
            .factored(UPDATED_FACTORED);

        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactions))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransactions.getBtc()).isEqualTo(UPDATED_BTC);
        assertThat(testTransactions.getBtcPrice()).isEqualTo(UPDATED_BTC_PRICE);
        assertThat(testTransactions.getFiat()).isEqualTo(UPDATED_FIAT);
        assertThat(testTransactions.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testTransactions.getFee1()).isEqualTo(UPDATED_FEE_1);
        assertThat(testTransactions.getBtcNet()).isEqualTo(DEFAULT_BTC_NET);
        assertThat(testTransactions.getBtcNet1()).isEqualTo(UPDATED_BTC_NET_1);
        assertThat(testTransactions.getBtcBefore1()).isEqualTo(DEFAULT_BTC_BEFORE_1);
        assertThat(testTransactions.getBtcAfter1()).isEqualTo(DEFAULT_BTC_AFTER_1);
        assertThat(testTransactions.getFiatBefore1()).isEqualTo(DEFAULT_FIAT_BEFORE_1);
        assertThat(testTransactions.getFiatAfter1()).isEqualTo(UPDATED_FIAT_AFTER_1);
        assertThat(testTransactions.getBtcBefore()).isEqualTo(UPDATED_BTC_BEFORE);
        assertThat(testTransactions.getBtcAfter()).isEqualTo(UPDATED_BTC_AFTER);
        assertThat(testTransactions.getFiatBefore()).isEqualTo(DEFAULT_FIAT_BEFORE);
        assertThat(testTransactions.getFiatAfter()).isEqualTo(DEFAULT_FIAT_AFTER);
        assertThat(testTransactions.getFeeLevel()).isEqualTo(DEFAULT_FEE_LEVEL);
        assertThat(testTransactions.getFeeLevel1()).isEqualTo(DEFAULT_FEE_LEVEL_1);
        assertThat(testTransactions.getOrigBtcPrice()).isEqualTo(UPDATED_ORIG_BTC_PRICE);
        assertThat(testTransactions.getConversionFee()).isEqualTo(DEFAULT_CONVERSION_FEE);
        assertThat(testTransactions.getConvertAmount()).isEqualTo(DEFAULT_CONVERT_AMOUNT);
        assertThat(testTransactions.getConvertRateGiven()).isEqualTo(DEFAULT_CONVERT_RATE_GIVEN);
        assertThat(testTransactions.getConvertSystemRate()).isEqualTo(DEFAULT_CONVERT_SYSTEM_RATE);
        assertThat(testTransactions.getConversion()).isEqualTo(DEFAULT_CONVERSION);
        assertThat(testTransactions.getBidAtTransaction()).isEqualTo(DEFAULT_BID_AT_TRANSACTION);
        assertThat(testTransactions.getAskAtTransaction()).isEqualTo(UPDATED_ASK_AT_TRANSACTION);
        assertThat(testTransactions.getFactored()).isEqualTo(UPDATED_FACTORED);
    }

    @Test
    @Transactional
    void fullUpdateTransactionsWithPatch() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Update the transactions using partial update
        Transactions partialUpdatedTransactions = new Transactions();
        partialUpdatedTransactions.setId(transactions.getId());

        partialUpdatedTransactions
            .date(UPDATED_DATE)
            .btc(UPDATED_BTC)
            .btcPrice(UPDATED_BTC_PRICE)
            .fiat(UPDATED_FIAT)
            .fee(UPDATED_FEE)
            .fee1(UPDATED_FEE_1)
            .btcNet(UPDATED_BTC_NET)
            .btcNet1(UPDATED_BTC_NET_1)
            .btcBefore1(UPDATED_BTC_BEFORE_1)
            .btcAfter1(UPDATED_BTC_AFTER_1)
            .fiatBefore1(UPDATED_FIAT_BEFORE_1)
            .fiatAfter1(UPDATED_FIAT_AFTER_1)
            .btcBefore(UPDATED_BTC_BEFORE)
            .btcAfter(UPDATED_BTC_AFTER)
            .fiatBefore(UPDATED_FIAT_BEFORE)
            .fiatAfter(UPDATED_FIAT_AFTER)
            .feeLevel(UPDATED_FEE_LEVEL)
            .feeLevel1(UPDATED_FEE_LEVEL_1)
            .origBtcPrice(UPDATED_ORIG_BTC_PRICE)
            .conversionFee(UPDATED_CONVERSION_FEE)
            .convertAmount(UPDATED_CONVERT_AMOUNT)
            .convertRateGiven(UPDATED_CONVERT_RATE_GIVEN)
            .convertSystemRate(UPDATED_CONVERT_SYSTEM_RATE)
            .conversion(UPDATED_CONVERSION)
            .bidAtTransaction(UPDATED_BID_AT_TRANSACTION)
            .askAtTransaction(UPDATED_ASK_AT_TRANSACTION)
            .factored(UPDATED_FACTORED);

        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactions))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransactions.getBtc()).isEqualTo(UPDATED_BTC);
        assertThat(testTransactions.getBtcPrice()).isEqualTo(UPDATED_BTC_PRICE);
        assertThat(testTransactions.getFiat()).isEqualTo(UPDATED_FIAT);
        assertThat(testTransactions.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testTransactions.getFee1()).isEqualTo(UPDATED_FEE_1);
        assertThat(testTransactions.getBtcNet()).isEqualTo(UPDATED_BTC_NET);
        assertThat(testTransactions.getBtcNet1()).isEqualTo(UPDATED_BTC_NET_1);
        assertThat(testTransactions.getBtcBefore1()).isEqualTo(UPDATED_BTC_BEFORE_1);
        assertThat(testTransactions.getBtcAfter1()).isEqualTo(UPDATED_BTC_AFTER_1);
        assertThat(testTransactions.getFiatBefore1()).isEqualTo(UPDATED_FIAT_BEFORE_1);
        assertThat(testTransactions.getFiatAfter1()).isEqualTo(UPDATED_FIAT_AFTER_1);
        assertThat(testTransactions.getBtcBefore()).isEqualTo(UPDATED_BTC_BEFORE);
        assertThat(testTransactions.getBtcAfter()).isEqualTo(UPDATED_BTC_AFTER);
        assertThat(testTransactions.getFiatBefore()).isEqualTo(UPDATED_FIAT_BEFORE);
        assertThat(testTransactions.getFiatAfter()).isEqualTo(UPDATED_FIAT_AFTER);
        assertThat(testTransactions.getFeeLevel()).isEqualTo(UPDATED_FEE_LEVEL);
        assertThat(testTransactions.getFeeLevel1()).isEqualTo(UPDATED_FEE_LEVEL_1);
        assertThat(testTransactions.getOrigBtcPrice()).isEqualTo(UPDATED_ORIG_BTC_PRICE);
        assertThat(testTransactions.getConversionFee()).isEqualTo(UPDATED_CONVERSION_FEE);
        assertThat(testTransactions.getConvertAmount()).isEqualTo(UPDATED_CONVERT_AMOUNT);
        assertThat(testTransactions.getConvertRateGiven()).isEqualTo(UPDATED_CONVERT_RATE_GIVEN);
        assertThat(testTransactions.getConvertSystemRate()).isEqualTo(UPDATED_CONVERT_SYSTEM_RATE);
        assertThat(testTransactions.getConversion()).isEqualTo(UPDATED_CONVERSION);
        assertThat(testTransactions.getBidAtTransaction()).isEqualTo(UPDATED_BID_AT_TRANSACTION);
        assertThat(testTransactions.getAskAtTransaction()).isEqualTo(UPDATED_ASK_AT_TRANSACTION);
        assertThat(testTransactions.getFactored()).isEqualTo(UPDATED_FACTORED);
    }

    @Test
    @Transactional
    void patchNonExistingTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeDelete = transactionsRepository.findAll().size();

        // Delete the transactions
        restTransactionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
