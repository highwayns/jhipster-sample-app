package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Requests;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.RequestsRepository;
import io.github.jhipster.sample.service.dto.RequestsDTO;
import io.github.jhipster.sample.service.mapper.RequestsMapper;
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
 * Integration tests for the {@link RequestsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestsResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Integer DEFAULT_ADDRESS_ID = 1;
    private static final Integer UPDATED_ADDRESS_ID = 2;

    private static final Long DEFAULT_ACCOUNT = 1L;
    private static final Long UPDATED_ACCOUNT = 2L;

    private static final String DEFAULT_SEND_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SEND_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_INCREMENT = 1D;
    private static final Double UPDATED_INCREMENT = 2D;

    private static final YesNo DEFAULT_DONE = YesNo.Y;
    private static final YesNo UPDATED_DONE = YesNo.N;

    private static final Integer DEFAULT_CRYPTO_ID = 1;
    private static final Integer UPDATED_CRYPTO_ID = 2;

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    private static final Double DEFAULT_NET_AMOUNT = 1D;
    private static final Double UPDATED_NET_AMOUNT = 2D;

    private static final Integer DEFAULT_NOTIFIED = 1;
    private static final Integer UPDATED_NOTIFIED = 2;

    private static final String ENTITY_API_URL = "/api/requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private RequestsMapper requestsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestsMockMvc;

    private Requests requests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requests createEntity(EntityManager em) {
        Requests requests = new Requests()
            .date(DEFAULT_DATE)
            .amount(DEFAULT_AMOUNT)
            .addressId(DEFAULT_ADDRESS_ID)
            .account(DEFAULT_ACCOUNT)
            .sendAddress(DEFAULT_SEND_ADDRESS)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .increment(DEFAULT_INCREMENT)
            .done(DEFAULT_DONE)
            .cryptoId(DEFAULT_CRYPTO_ID)
            .fee(DEFAULT_FEE)
            .netAmount(DEFAULT_NET_AMOUNT)
            .notified(DEFAULT_NOTIFIED);
        return requests;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requests createUpdatedEntity(EntityManager em) {
        Requests requests = new Requests()
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .addressId(UPDATED_ADDRESS_ID)
            .account(UPDATED_ACCOUNT)
            .sendAddress(UPDATED_SEND_ADDRESS)
            .transactionId(UPDATED_TRANSACTION_ID)
            .increment(UPDATED_INCREMENT)
            .done(UPDATED_DONE)
            .cryptoId(UPDATED_CRYPTO_ID)
            .fee(UPDATED_FEE)
            .netAmount(UPDATED_NET_AMOUNT)
            .notified(UPDATED_NOTIFIED);
        return requests;
    }

    @BeforeEach
    public void initTest() {
        requests = createEntity(em);
    }

    @Test
    @Transactional
    void createRequests() throws Exception {
        int databaseSizeBeforeCreate = requestsRepository.findAll().size();
        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);
        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isCreated());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeCreate + 1);
        Requests testRequests = requestsList.get(requestsList.size() - 1);
        assertThat(testRequests.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRequests.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRequests.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testRequests.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testRequests.getSendAddress()).isEqualTo(DEFAULT_SEND_ADDRESS);
        assertThat(testRequests.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testRequests.getIncrement()).isEqualTo(DEFAULT_INCREMENT);
        assertThat(testRequests.getDone()).isEqualTo(DEFAULT_DONE);
        assertThat(testRequests.getCryptoId()).isEqualTo(DEFAULT_CRYPTO_ID);
        assertThat(testRequests.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testRequests.getNetAmount()).isEqualTo(DEFAULT_NET_AMOUNT);
        assertThat(testRequests.getNotified()).isEqualTo(DEFAULT_NOTIFIED);
    }

    @Test
    @Transactional
    void createRequestsWithExistingId() throws Exception {
        // Create the Requests with an existing ID
        requests.setId(1L);
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        int databaseSizeBeforeCreate = requestsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setDate(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setAmount(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setAddressId(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setAccount(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSendAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setSendAddress(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setTransactionId(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIncrementIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setIncrement(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setDone(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCryptoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setCryptoId(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setFee(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNetAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setNetAmount(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestsRepository.findAll().size();
        // set the field null
        requests.setNotified(null);

        // Create the Requests, which fails.
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        restRequestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        // Get all the requestsList
        restRequestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requests.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].sendAddress").value(hasItem(DEFAULT_SEND_ADDRESS)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].increment").value(hasItem(DEFAULT_INCREMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].done").value(hasItem(DEFAULT_DONE.toString())))
            .andExpect(jsonPath("$.[*].cryptoId").value(hasItem(DEFAULT_CRYPTO_ID)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].netAmount").value(hasItem(DEFAULT_NET_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].notified").value(hasItem(DEFAULT_NOTIFIED)));
    }

    @Test
    @Transactional
    void getRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        // Get the requests
        restRequestsMockMvc
            .perform(get(ENTITY_API_URL_ID, requests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requests.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.sendAddress").value(DEFAULT_SEND_ADDRESS))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.increment").value(DEFAULT_INCREMENT.doubleValue()))
            .andExpect(jsonPath("$.done").value(DEFAULT_DONE.toString()))
            .andExpect(jsonPath("$.cryptoId").value(DEFAULT_CRYPTO_ID))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.doubleValue()))
            .andExpect(jsonPath("$.netAmount").value(DEFAULT_NET_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.notified").value(DEFAULT_NOTIFIED));
    }

    @Test
    @Transactional
    void getNonExistingRequests() throws Exception {
        // Get the requests
        restRequestsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();

        // Update the requests
        Requests updatedRequests = requestsRepository.findById(requests.getId()).get();
        // Disconnect from session so that the updates on updatedRequests are not directly saved in db
        em.detach(updatedRequests);
        updatedRequests
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .addressId(UPDATED_ADDRESS_ID)
            .account(UPDATED_ACCOUNT)
            .sendAddress(UPDATED_SEND_ADDRESS)
            .transactionId(UPDATED_TRANSACTION_ID)
            .increment(UPDATED_INCREMENT)
            .done(UPDATED_DONE)
            .cryptoId(UPDATED_CRYPTO_ID)
            .fee(UPDATED_FEE)
            .netAmount(UPDATED_NET_AMOUNT)
            .notified(UPDATED_NOTIFIED);
        RequestsDTO requestsDTO = requestsMapper.toDto(updatedRequests);

        restRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
        Requests testRequests = requestsList.get(requestsList.size() - 1);
        assertThat(testRequests.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequests.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRequests.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testRequests.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testRequests.getSendAddress()).isEqualTo(UPDATED_SEND_ADDRESS);
        assertThat(testRequests.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testRequests.getIncrement()).isEqualTo(UPDATED_INCREMENT);
        assertThat(testRequests.getDone()).isEqualTo(UPDATED_DONE);
        assertThat(testRequests.getCryptoId()).isEqualTo(UPDATED_CRYPTO_ID);
        assertThat(testRequests.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testRequests.getNetAmount()).isEqualTo(UPDATED_NET_AMOUNT);
        assertThat(testRequests.getNotified()).isEqualTo(UPDATED_NOTIFIED);
    }

    @Test
    @Transactional
    void putNonExistingRequests() throws Exception {
        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();
        requests.setId(count.incrementAndGet());

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequests() throws Exception {
        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();
        requests.setId(count.incrementAndGet());

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequests() throws Exception {
        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();
        requests.setId(count.incrementAndGet());

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestsWithPatch() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();

        // Update the requests using partial update
        Requests partialUpdatedRequests = new Requests();
        partialUpdatedRequests.setId(requests.getId());

        partialUpdatedRequests
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .addressId(UPDATED_ADDRESS_ID)
            .done(UPDATED_DONE)
            .cryptoId(UPDATED_CRYPTO_ID)
            .fee(UPDATED_FEE)
            .notified(UPDATED_NOTIFIED);

        restRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequests.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequests))
            )
            .andExpect(status().isOk());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
        Requests testRequests = requestsList.get(requestsList.size() - 1);
        assertThat(testRequests.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequests.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRequests.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testRequests.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testRequests.getSendAddress()).isEqualTo(DEFAULT_SEND_ADDRESS);
        assertThat(testRequests.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testRequests.getIncrement()).isEqualTo(DEFAULT_INCREMENT);
        assertThat(testRequests.getDone()).isEqualTo(UPDATED_DONE);
        assertThat(testRequests.getCryptoId()).isEqualTo(UPDATED_CRYPTO_ID);
        assertThat(testRequests.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testRequests.getNetAmount()).isEqualTo(DEFAULT_NET_AMOUNT);
        assertThat(testRequests.getNotified()).isEqualTo(UPDATED_NOTIFIED);
    }

    @Test
    @Transactional
    void fullUpdateRequestsWithPatch() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();

        // Update the requests using partial update
        Requests partialUpdatedRequests = new Requests();
        partialUpdatedRequests.setId(requests.getId());

        partialUpdatedRequests
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .addressId(UPDATED_ADDRESS_ID)
            .account(UPDATED_ACCOUNT)
            .sendAddress(UPDATED_SEND_ADDRESS)
            .transactionId(UPDATED_TRANSACTION_ID)
            .increment(UPDATED_INCREMENT)
            .done(UPDATED_DONE)
            .cryptoId(UPDATED_CRYPTO_ID)
            .fee(UPDATED_FEE)
            .netAmount(UPDATED_NET_AMOUNT)
            .notified(UPDATED_NOTIFIED);

        restRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequests.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequests))
            )
            .andExpect(status().isOk());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
        Requests testRequests = requestsList.get(requestsList.size() - 1);
        assertThat(testRequests.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequests.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRequests.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testRequests.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testRequests.getSendAddress()).isEqualTo(UPDATED_SEND_ADDRESS);
        assertThat(testRequests.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testRequests.getIncrement()).isEqualTo(UPDATED_INCREMENT);
        assertThat(testRequests.getDone()).isEqualTo(UPDATED_DONE);
        assertThat(testRequests.getCryptoId()).isEqualTo(UPDATED_CRYPTO_ID);
        assertThat(testRequests.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testRequests.getNetAmount()).isEqualTo(UPDATED_NET_AMOUNT);
        assertThat(testRequests.getNotified()).isEqualTo(UPDATED_NOTIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingRequests() throws Exception {
        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();
        requests.setId(count.incrementAndGet());

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequests() throws Exception {
        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();
        requests.setId(count.incrementAndGet());

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequests() throws Exception {
        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();
        requests.setId(count.incrementAndGet());

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(requestsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        int databaseSizeBeforeDelete = requestsRepository.findAll().size();

        // Delete the requests
        restRequestsMockMvc
            .perform(delete(ENTITY_API_URL_ID, requests.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
