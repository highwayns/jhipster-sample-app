package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.CurrenciesRepository;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.mapper.CurrenciesMapper;
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
 * Integration tests for the {@link CurrenciesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrenciesResourceIT {

    private static final String DEFAULT_CURRENCY = "AAA";
    private static final String UPDATED_CURRENCY = "BBB";

    private static final String DEFAULT_FA_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_FA_SYMBOL = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_ACCOUNT_NUMBER = 2L;

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final YesNo DEFAULT_IS_ACTIVE = YesNo.Y;
    private static final YesNo UPDATED_IS_ACTIVE = YesNo.N;

    private static final String DEFAULT_USD_BID = "AAAAAAAAAA";
    private static final String UPDATED_USD_BID = "BBBBBBBBBB";

    private static final String DEFAULT_USD_ASK = "AAAAAAAAAA";
    private static final String UPDATED_USD_ASK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ES = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ZH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ZH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/currencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrenciesRepository currenciesRepository;

    @Autowired
    private CurrenciesMapper currenciesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrenciesMockMvc;

    private Currencies currencies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currencies createEntity(EntityManager em) {
        Currencies currencies = new Currencies()
            .currency(DEFAULT_CURRENCY)
            .faSymbol(DEFAULT_FA_SYMBOL)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .usdBid(DEFAULT_USD_BID)
            .usdAsk(DEFAULT_USD_ASK)
            .nameEn(DEFAULT_NAME_EN)
            .nameEs(DEFAULT_NAME_ES)
            .nameRu(DEFAULT_NAME_RU)
            .nameZh(DEFAULT_NAME_ZH);
        return currencies;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currencies createUpdatedEntity(EntityManager em) {
        Currencies currencies = new Currencies()
            .currency(UPDATED_CURRENCY)
            .faSymbol(UPDATED_FA_SYMBOL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .usdBid(UPDATED_USD_BID)
            .usdAsk(UPDATED_USD_ASK)
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        return currencies;
    }

    @BeforeEach
    public void initTest() {
        currencies = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrencies() throws Exception {
        int databaseSizeBeforeCreate = currenciesRepository.findAll().size();
        // Create the Currencies
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);
        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isCreated());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeCreate + 1);
        Currencies testCurrencies = currenciesList.get(currenciesList.size() - 1);
        assertThat(testCurrencies.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testCurrencies.getFaSymbol()).isEqualTo(DEFAULT_FA_SYMBOL);
        assertThat(testCurrencies.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCurrencies.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testCurrencies.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCurrencies.getUsdBid()).isEqualTo(DEFAULT_USD_BID);
        assertThat(testCurrencies.getUsdAsk()).isEqualTo(DEFAULT_USD_ASK);
        assertThat(testCurrencies.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testCurrencies.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testCurrencies.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testCurrencies.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void createCurrenciesWithExistingId() throws Exception {
        // Create the Currencies with an existing ID
        currencies.setId(1L);
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        int databaseSizeBeforeCreate = currenciesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setCurrency(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFaSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setFaSymbol(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setAccountNumber(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setAccountName(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setIsActive(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsdBidIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setUsdBid(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsdAskIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setUsdAsk(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setNameEn(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setNameEs(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setNameRu(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = currenciesRepository.findAll().size();
        // set the field null
        currencies.setNameZh(null);

        // Create the Currencies, which fails.
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        restCurrenciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isBadRequest());

        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCurrencies() throws Exception {
        // Initialize the database
        currenciesRepository.saveAndFlush(currencies);

        // Get all the currenciesList
        restCurrenciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencies.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].faSymbol").value(hasItem(DEFAULT_FA_SYMBOL)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].usdBid").value(hasItem(DEFAULT_USD_BID)))
            .andExpect(jsonPath("$.[*].usdAsk").value(hasItem(DEFAULT_USD_ASK)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameEs").value(hasItem(DEFAULT_NAME_ES)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameZh").value(hasItem(DEFAULT_NAME_ZH)));
    }

    @Test
    @Transactional
    void getCurrencies() throws Exception {
        // Initialize the database
        currenciesRepository.saveAndFlush(currencies);

        // Get the currencies
        restCurrenciesMockMvc
            .perform(get(ENTITY_API_URL_ID, currencies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currencies.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.faSymbol").value(DEFAULT_FA_SYMBOL))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.toString()))
            .andExpect(jsonPath("$.usdBid").value(DEFAULT_USD_BID))
            .andExpect(jsonPath("$.usdAsk").value(DEFAULT_USD_ASK))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameEs").value(DEFAULT_NAME_ES))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameZh").value(DEFAULT_NAME_ZH));
    }

    @Test
    @Transactional
    void getNonExistingCurrencies() throws Exception {
        // Get the currencies
        restCurrenciesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCurrencies() throws Exception {
        // Initialize the database
        currenciesRepository.saveAndFlush(currencies);

        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();

        // Update the currencies
        Currencies updatedCurrencies = currenciesRepository.findById(currencies.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencies are not directly saved in db
        em.detach(updatedCurrencies);
        updatedCurrencies
            .currency(UPDATED_CURRENCY)
            .faSymbol(UPDATED_FA_SYMBOL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .usdBid(UPDATED_USD_BID)
            .usdAsk(UPDATED_USD_ASK)
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(updatedCurrencies);

        restCurrenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currenciesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currenciesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
        Currencies testCurrencies = currenciesList.get(currenciesList.size() - 1);
        assertThat(testCurrencies.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testCurrencies.getFaSymbol()).isEqualTo(UPDATED_FA_SYMBOL);
        assertThat(testCurrencies.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCurrencies.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCurrencies.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCurrencies.getUsdBid()).isEqualTo(UPDATED_USD_BID);
        assertThat(testCurrencies.getUsdAsk()).isEqualTo(UPDATED_USD_ASK);
        assertThat(testCurrencies.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testCurrencies.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testCurrencies.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testCurrencies.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void putNonExistingCurrencies() throws Exception {
        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();
        currencies.setId(count.incrementAndGet());

        // Create the Currencies
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currenciesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrencies() throws Exception {
        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();
        currencies.setId(count.incrementAndGet());

        // Create the Currencies
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrencies() throws Exception {
        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();
        currencies.setId(count.incrementAndGet());

        // Create the Currencies
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrenciesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currenciesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCurrenciesWithPatch() throws Exception {
        // Initialize the database
        currenciesRepository.saveAndFlush(currencies);

        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();

        // Update the currencies using partial update
        Currencies partialUpdatedCurrencies = new Currencies();
        partialUpdatedCurrencies.setId(currencies.getId());

        partialUpdatedCurrencies
            .faSymbol(UPDATED_FA_SYMBOL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU);

        restCurrenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencies))
            )
            .andExpect(status().isOk());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
        Currencies testCurrencies = currenciesList.get(currenciesList.size() - 1);
        assertThat(testCurrencies.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testCurrencies.getFaSymbol()).isEqualTo(UPDATED_FA_SYMBOL);
        assertThat(testCurrencies.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCurrencies.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testCurrencies.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCurrencies.getUsdBid()).isEqualTo(DEFAULT_USD_BID);
        assertThat(testCurrencies.getUsdAsk()).isEqualTo(DEFAULT_USD_ASK);
        assertThat(testCurrencies.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testCurrencies.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testCurrencies.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testCurrencies.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void fullUpdateCurrenciesWithPatch() throws Exception {
        // Initialize the database
        currenciesRepository.saveAndFlush(currencies);

        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();

        // Update the currencies using partial update
        Currencies partialUpdatedCurrencies = new Currencies();
        partialUpdatedCurrencies.setId(currencies.getId());

        partialUpdatedCurrencies
            .currency(UPDATED_CURRENCY)
            .faSymbol(UPDATED_FA_SYMBOL)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .usdBid(UPDATED_USD_BID)
            .usdAsk(UPDATED_USD_ASK)
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);

        restCurrenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencies))
            )
            .andExpect(status().isOk());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
        Currencies testCurrencies = currenciesList.get(currenciesList.size() - 1);
        assertThat(testCurrencies.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testCurrencies.getFaSymbol()).isEqualTo(UPDATED_FA_SYMBOL);
        assertThat(testCurrencies.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCurrencies.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCurrencies.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCurrencies.getUsdBid()).isEqualTo(UPDATED_USD_BID);
        assertThat(testCurrencies.getUsdAsk()).isEqualTo(UPDATED_USD_ASK);
        assertThat(testCurrencies.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testCurrencies.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testCurrencies.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testCurrencies.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void patchNonExistingCurrencies() throws Exception {
        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();
        currencies.setId(count.incrementAndGet());

        // Create the Currencies
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currenciesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrencies() throws Exception {
        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();
        currencies.setId(count.incrementAndGet());

        // Create the Currencies
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrencies() throws Exception {
        int databaseSizeBeforeUpdate = currenciesRepository.findAll().size();
        currencies.setId(count.incrementAndGet());

        // Create the Currencies
        CurrenciesDTO currenciesDTO = currenciesMapper.toDto(currencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrenciesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(currenciesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Currencies in the database
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurrencies() throws Exception {
        // Initialize the database
        currenciesRepository.saveAndFlush(currencies);

        int databaseSizeBeforeDelete = currenciesRepository.findAll().size();

        // Delete the currencies
        restCurrenciesMockMvc
            .perform(delete(ENTITY_API_URL_ID, currencies.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currencies> currenciesList = currenciesRepository.findAll();
        assertThat(currenciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
