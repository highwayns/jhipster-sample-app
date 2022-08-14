package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.History;
import io.github.jhipster.sample.repository.HistoryRepository;
import io.github.jhipster.sample.service.dto.HistoryDTO;
import io.github.jhipster.sample.service.mapper.HistoryMapper;
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
 * Integration tests for the {@link HistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoryResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_BALANCE_BEFORE = 1D;
    private static final Double UPDATED_BALANCE_BEFORE = 2D;

    private static final Double DEFAULT_BALANCE_AFTER = 1D;
    private static final Double UPDATED_BALANCE_AFTER = 2D;

    private static final String ENTITY_API_URL = "/api/histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoryMockMvc;

    private History history;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createEntity(EntityManager em) {
        History history = new History()
            .date(DEFAULT_DATE)
            .ip(DEFAULT_IP)
            .bitcoinAddress(DEFAULT_BITCOIN_ADDRESS)
            .balanceBefore(DEFAULT_BALANCE_BEFORE)
            .balanceAfter(DEFAULT_BALANCE_AFTER);
        return history;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createUpdatedEntity(EntityManager em) {
        History history = new History()
            .date(UPDATED_DATE)
            .ip(UPDATED_IP)
            .bitcoinAddress(UPDATED_BITCOIN_ADDRESS)
            .balanceBefore(UPDATED_BALANCE_BEFORE)
            .balanceAfter(UPDATED_BALANCE_AFTER);
        return history;
    }

    @BeforeEach
    public void initTest() {
        history = createEntity(em);
    }

    @Test
    @Transactional
    void createHistory() throws Exception {
        int databaseSizeBeforeCreate = historyRepository.findAll().size();
        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);
        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isCreated());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeCreate + 1);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHistory.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testHistory.getBitcoinAddress()).isEqualTo(DEFAULT_BITCOIN_ADDRESS);
        assertThat(testHistory.getBalanceBefore()).isEqualTo(DEFAULT_BALANCE_BEFORE);
        assertThat(testHistory.getBalanceAfter()).isEqualTo(DEFAULT_BALANCE_AFTER);
    }

    @Test
    @Transactional
    void createHistoryWithExistingId() throws Exception {
        // Create the History with an existing ID
        history.setId(1L);
        HistoryDTO historyDTO = historyMapper.toDto(history);

        int databaseSizeBeforeCreate = historyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyRepository.findAll().size();
        // set the field null
        history.setDate(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.toDto(history);

        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyRepository.findAll().size();
        // set the field null
        history.setIp(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.toDto(history);

        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyRepository.findAll().size();
        // set the field null
        history.setBitcoinAddress(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.toDto(history);

        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBalanceBeforeIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyRepository.findAll().size();
        // set the field null
        history.setBalanceBefore(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.toDto(history);

        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBalanceAfterIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyRepository.findAll().size();
        // set the field null
        history.setBalanceAfter(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.toDto(history);

        restHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHistories() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get all the historyList
        restHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(history.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].bitcoinAddress").value(hasItem(DEFAULT_BITCOIN_ADDRESS)))
            .andExpect(jsonPath("$.[*].balanceBefore").value(hasItem(DEFAULT_BALANCE_BEFORE.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceAfter").value(hasItem(DEFAULT_BALANCE_AFTER.doubleValue())));
    }

    @Test
    @Transactional
    void getHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get the history
        restHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(history.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.bitcoinAddress").value(DEFAULT_BITCOIN_ADDRESS))
            .andExpect(jsonPath("$.balanceBefore").value(DEFAULT_BALANCE_BEFORE.doubleValue()))
            .andExpect(jsonPath("$.balanceAfter").value(DEFAULT_BALANCE_AFTER.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHistory() throws Exception {
        // Get the history
        restHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Update the history
        History updatedHistory = historyRepository.findById(history.getId()).get();
        // Disconnect from session so that the updates on updatedHistory are not directly saved in db
        em.detach(updatedHistory);
        updatedHistory
            .date(UPDATED_DATE)
            .ip(UPDATED_IP)
            .bitcoinAddress(UPDATED_BITCOIN_ADDRESS)
            .balanceBefore(UPDATED_BALANCE_BEFORE)
            .balanceAfter(UPDATED_BALANCE_AFTER);
        HistoryDTO historyDTO = historyMapper.toDto(updatedHistory);

        restHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyDTO))
            )
            .andExpect(status().isOk());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHistory.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testHistory.getBitcoinAddress()).isEqualTo(UPDATED_BITCOIN_ADDRESS);
        assertThat(testHistory.getBalanceBefore()).isEqualTo(UPDATED_BALANCE_BEFORE);
        assertThat(testHistory.getBalanceAfter()).isEqualTo(UPDATED_BALANCE_AFTER);
    }

    @Test
    @Transactional
    void putNonExistingHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();
        history.setId(count.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();
        history.setId(count.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();
        history.setId(count.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoryWithPatch() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Update the history using partial update
        History partialUpdatedHistory = new History();
        partialUpdatedHistory.setId(history.getId());

        partialUpdatedHistory.balanceBefore(UPDATED_BALANCE_BEFORE).balanceAfter(UPDATED_BALANCE_AFTER);

        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistory))
            )
            .andExpect(status().isOk());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHistory.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testHistory.getBitcoinAddress()).isEqualTo(DEFAULT_BITCOIN_ADDRESS);
        assertThat(testHistory.getBalanceBefore()).isEqualTo(UPDATED_BALANCE_BEFORE);
        assertThat(testHistory.getBalanceAfter()).isEqualTo(UPDATED_BALANCE_AFTER);
    }

    @Test
    @Transactional
    void fullUpdateHistoryWithPatch() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Update the history using partial update
        History partialUpdatedHistory = new History();
        partialUpdatedHistory.setId(history.getId());

        partialUpdatedHistory
            .date(UPDATED_DATE)
            .ip(UPDATED_IP)
            .bitcoinAddress(UPDATED_BITCOIN_ADDRESS)
            .balanceBefore(UPDATED_BALANCE_BEFORE)
            .balanceAfter(UPDATED_BALANCE_AFTER);

        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistory))
            )
            .andExpect(status().isOk());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHistory.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testHistory.getBitcoinAddress()).isEqualTo(UPDATED_BITCOIN_ADDRESS);
        assertThat(testHistory.getBalanceBefore()).isEqualTo(UPDATED_BALANCE_BEFORE);
        assertThat(testHistory.getBalanceAfter()).isEqualTo(UPDATED_BALANCE_AFTER);
    }

    @Test
    @Transactional
    void patchNonExistingHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();
        history.setId(count.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();
        history.setId(count.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();
        history.setId(count.incrementAndGet());

        // Create the History
        HistoryDTO historyDTO = historyMapper.toDto(history);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(historyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        int databaseSizeBeforeDelete = historyRepository.findAll().size();

        // Delete the history
        restHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, history.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
