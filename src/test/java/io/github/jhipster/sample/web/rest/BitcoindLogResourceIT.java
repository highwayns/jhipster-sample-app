package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.BitcoindLog;
import io.github.jhipster.sample.repository.BitcoindLogRepository;
import io.github.jhipster.sample.service.dto.BitcoindLogDTO;
import io.github.jhipster.sample.service.mapper.BitcoindLogMapper;
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
 * Integration tests for the {@link BitcoindLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BitcoindLogResourceIT {

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bitcoind-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BitcoindLogRepository bitcoindLogRepository;

    @Autowired
    private BitcoindLogMapper bitcoindLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBitcoindLogMockMvc;

    private BitcoindLog bitcoindLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BitcoindLog createEntity(EntityManager em) {
        BitcoindLog bitcoindLog = new BitcoindLog().transactionId(DEFAULT_TRANSACTION_ID).amount(DEFAULT_AMOUNT).date(DEFAULT_DATE);
        return bitcoindLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BitcoindLog createUpdatedEntity(EntityManager em) {
        BitcoindLog bitcoindLog = new BitcoindLog().transactionId(UPDATED_TRANSACTION_ID).amount(UPDATED_AMOUNT).date(UPDATED_DATE);
        return bitcoindLog;
    }

    @BeforeEach
    public void initTest() {
        bitcoindLog = createEntity(em);
    }

    @Test
    @Transactional
    void createBitcoindLog() throws Exception {
        int databaseSizeBeforeCreate = bitcoindLogRepository.findAll().size();
        // Create the BitcoindLog
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);
        restBitcoindLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeCreate + 1);
        BitcoindLog testBitcoindLog = bitcoindLogList.get(bitcoindLogList.size() - 1);
        assertThat(testBitcoindLog.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testBitcoindLog.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBitcoindLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createBitcoindLogWithExistingId() throws Exception {
        // Create the BitcoindLog with an existing ID
        bitcoindLog.setId(1L);
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        int databaseSizeBeforeCreate = bitcoindLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBitcoindLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoindLogRepository.findAll().size();
        // set the field null
        bitcoindLog.setTransactionId(null);

        // Create the BitcoindLog, which fails.
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        restBitcoindLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoindLogRepository.findAll().size();
        // set the field null
        bitcoindLog.setAmount(null);

        // Create the BitcoindLog, which fails.
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        restBitcoindLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoindLogRepository.findAll().size();
        // set the field null
        bitcoindLog.setDate(null);

        // Create the BitcoindLog, which fails.
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        restBitcoindLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBitcoindLogs() throws Exception {
        // Initialize the database
        bitcoindLogRepository.saveAndFlush(bitcoindLog);

        // Get all the bitcoindLogList
        restBitcoindLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bitcoindLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getBitcoindLog() throws Exception {
        // Initialize the database
        bitcoindLogRepository.saveAndFlush(bitcoindLog);

        // Get the bitcoindLog
        restBitcoindLogMockMvc
            .perform(get(ENTITY_API_URL_ID, bitcoindLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bitcoindLog.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBitcoindLog() throws Exception {
        // Get the bitcoindLog
        restBitcoindLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBitcoindLog() throws Exception {
        // Initialize the database
        bitcoindLogRepository.saveAndFlush(bitcoindLog);

        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();

        // Update the bitcoindLog
        BitcoindLog updatedBitcoindLog = bitcoindLogRepository.findById(bitcoindLog.getId()).get();
        // Disconnect from session so that the updates on updatedBitcoindLog are not directly saved in db
        em.detach(updatedBitcoindLog);
        updatedBitcoindLog.transactionId(UPDATED_TRANSACTION_ID).amount(UPDATED_AMOUNT).date(UPDATED_DATE);
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(updatedBitcoindLog);

        restBitcoindLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bitcoindLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
        BitcoindLog testBitcoindLog = bitcoindLogList.get(bitcoindLogList.size() - 1);
        assertThat(testBitcoindLog.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBitcoindLog.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBitcoindLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBitcoindLog() throws Exception {
        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();
        bitcoindLog.setId(count.incrementAndGet());

        // Create the BitcoindLog
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBitcoindLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bitcoindLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBitcoindLog() throws Exception {
        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();
        bitcoindLog.setId(count.incrementAndGet());

        // Create the BitcoindLog
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoindLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBitcoindLog() throws Exception {
        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();
        bitcoindLog.setId(count.incrementAndGet());

        // Create the BitcoindLog
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoindLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBitcoindLogWithPatch() throws Exception {
        // Initialize the database
        bitcoindLogRepository.saveAndFlush(bitcoindLog);

        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();

        // Update the bitcoindLog using partial update
        BitcoindLog partialUpdatedBitcoindLog = new BitcoindLog();
        partialUpdatedBitcoindLog.setId(bitcoindLog.getId());

        restBitcoindLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBitcoindLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBitcoindLog))
            )
            .andExpect(status().isOk());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
        BitcoindLog testBitcoindLog = bitcoindLogList.get(bitcoindLogList.size() - 1);
        assertThat(testBitcoindLog.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testBitcoindLog.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBitcoindLog.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBitcoindLogWithPatch() throws Exception {
        // Initialize the database
        bitcoindLogRepository.saveAndFlush(bitcoindLog);

        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();

        // Update the bitcoindLog using partial update
        BitcoindLog partialUpdatedBitcoindLog = new BitcoindLog();
        partialUpdatedBitcoindLog.setId(bitcoindLog.getId());

        partialUpdatedBitcoindLog.transactionId(UPDATED_TRANSACTION_ID).amount(UPDATED_AMOUNT).date(UPDATED_DATE);

        restBitcoindLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBitcoindLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBitcoindLog))
            )
            .andExpect(status().isOk());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
        BitcoindLog testBitcoindLog = bitcoindLogList.get(bitcoindLogList.size() - 1);
        assertThat(testBitcoindLog.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBitcoindLog.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBitcoindLog.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBitcoindLog() throws Exception {
        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();
        bitcoindLog.setId(count.incrementAndGet());

        // Create the BitcoindLog
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBitcoindLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bitcoindLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBitcoindLog() throws Exception {
        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();
        bitcoindLog.setId(count.incrementAndGet());

        // Create the BitcoindLog
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoindLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBitcoindLog() throws Exception {
        int databaseSizeBeforeUpdate = bitcoindLogRepository.findAll().size();
        bitcoindLog.setId(count.incrementAndGet());

        // Create the BitcoindLog
        BitcoindLogDTO bitcoindLogDTO = bitcoindLogMapper.toDto(bitcoindLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoindLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bitcoindLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BitcoindLog in the database
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBitcoindLog() throws Exception {
        // Initialize the database
        bitcoindLogRepository.saveAndFlush(bitcoindLog);

        int databaseSizeBeforeDelete = bitcoindLogRepository.findAll().size();

        // Delete the bitcoindLog
        restBitcoindLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, bitcoindLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BitcoindLog> bitcoindLogList = bitcoindLogRepository.findAll();
        assertThat(bitcoindLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
