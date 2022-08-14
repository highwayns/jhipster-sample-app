package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.CurrentStats;
import io.github.jhipster.sample.repository.CurrentStatsRepository;
import io.github.jhipster.sample.service.dto.CurrentStatsDTO;
import io.github.jhipster.sample.service.mapper.CurrentStatsMapper;
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
 * Integration tests for the {@link CurrentStatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrentStatsResourceIT {

    private static final Long DEFAULT_TOTAL_BTC = 1L;
    private static final Long UPDATED_TOTAL_BTC = 2L;

    private static final Long DEFAULT_MARKET_CAP = 1L;
    private static final Long UPDATED_MARKET_CAP = 2L;

    private static final Long DEFAULT_TRADE_VOLUME = 1L;
    private static final Long UPDATED_TRADE_VOLUME = 2L;

    private static final String ENTITY_API_URL = "/api/current-stats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrentStatsRepository currentStatsRepository;

    @Autowired
    private CurrentStatsMapper currentStatsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrentStatsMockMvc;

    private CurrentStats currentStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentStats createEntity(EntityManager em) {
        CurrentStats currentStats = new CurrentStats()
            .totalBtc(DEFAULT_TOTAL_BTC)
            .marketCap(DEFAULT_MARKET_CAP)
            .tradeVolume(DEFAULT_TRADE_VOLUME);
        return currentStats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentStats createUpdatedEntity(EntityManager em) {
        CurrentStats currentStats = new CurrentStats()
            .totalBtc(UPDATED_TOTAL_BTC)
            .marketCap(UPDATED_MARKET_CAP)
            .tradeVolume(UPDATED_TRADE_VOLUME);
        return currentStats;
    }

    @BeforeEach
    public void initTest() {
        currentStats = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrentStats() throws Exception {
        int databaseSizeBeforeCreate = currentStatsRepository.findAll().size();
        // Create the CurrentStats
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);
        restCurrentStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeCreate + 1);
        CurrentStats testCurrentStats = currentStatsList.get(currentStatsList.size() - 1);
        assertThat(testCurrentStats.getTotalBtc()).isEqualTo(DEFAULT_TOTAL_BTC);
        assertThat(testCurrentStats.getMarketCap()).isEqualTo(DEFAULT_MARKET_CAP);
        assertThat(testCurrentStats.getTradeVolume()).isEqualTo(DEFAULT_TRADE_VOLUME);
    }

    @Test
    @Transactional
    void createCurrentStatsWithExistingId() throws Exception {
        // Create the CurrentStats with an existing ID
        currentStats.setId(1L);
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        int databaseSizeBeforeCreate = currentStatsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTotalBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = currentStatsRepository.findAll().size();
        // set the field null
        currentStats.setTotalBtc(null);

        // Create the CurrentStats, which fails.
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        restCurrentStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarketCapIsRequired() throws Exception {
        int databaseSizeBeforeTest = currentStatsRepository.findAll().size();
        // set the field null
        currentStats.setMarketCap(null);

        // Create the CurrentStats, which fails.
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        restCurrentStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradeVolumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = currentStatsRepository.findAll().size();
        // set the field null
        currentStats.setTradeVolume(null);

        // Create the CurrentStats, which fails.
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        restCurrentStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCurrentStats() throws Exception {
        // Initialize the database
        currentStatsRepository.saveAndFlush(currentStats);

        // Get all the currentStatsList
        restCurrentStatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currentStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalBtc").value(hasItem(DEFAULT_TOTAL_BTC.intValue())))
            .andExpect(jsonPath("$.[*].marketCap").value(hasItem(DEFAULT_MARKET_CAP.intValue())))
            .andExpect(jsonPath("$.[*].tradeVolume").value(hasItem(DEFAULT_TRADE_VOLUME.intValue())));
    }

    @Test
    @Transactional
    void getCurrentStats() throws Exception {
        // Initialize the database
        currentStatsRepository.saveAndFlush(currentStats);

        // Get the currentStats
        restCurrentStatsMockMvc
            .perform(get(ENTITY_API_URL_ID, currentStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currentStats.getId().intValue()))
            .andExpect(jsonPath("$.totalBtc").value(DEFAULT_TOTAL_BTC.intValue()))
            .andExpect(jsonPath("$.marketCap").value(DEFAULT_MARKET_CAP.intValue()))
            .andExpect(jsonPath("$.tradeVolume").value(DEFAULT_TRADE_VOLUME.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCurrentStats() throws Exception {
        // Get the currentStats
        restCurrentStatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCurrentStats() throws Exception {
        // Initialize the database
        currentStatsRepository.saveAndFlush(currentStats);

        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();

        // Update the currentStats
        CurrentStats updatedCurrentStats = currentStatsRepository.findById(currentStats.getId()).get();
        // Disconnect from session so that the updates on updatedCurrentStats are not directly saved in db
        em.detach(updatedCurrentStats);
        updatedCurrentStats.totalBtc(UPDATED_TOTAL_BTC).marketCap(UPDATED_MARKET_CAP).tradeVolume(UPDATED_TRADE_VOLUME);
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(updatedCurrentStats);

        restCurrentStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currentStatsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
        CurrentStats testCurrentStats = currentStatsList.get(currentStatsList.size() - 1);
        assertThat(testCurrentStats.getTotalBtc()).isEqualTo(UPDATED_TOTAL_BTC);
        assertThat(testCurrentStats.getMarketCap()).isEqualTo(UPDATED_MARKET_CAP);
        assertThat(testCurrentStats.getTradeVolume()).isEqualTo(UPDATED_TRADE_VOLUME);
    }

    @Test
    @Transactional
    void putNonExistingCurrentStats() throws Exception {
        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();
        currentStats.setId(count.incrementAndGet());

        // Create the CurrentStats
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currentStatsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrentStats() throws Exception {
        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();
        currentStats.setId(count.incrementAndGet());

        // Create the CurrentStats
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrentStats() throws Exception {
        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();
        currentStats.setId(count.incrementAndGet());

        // Create the CurrentStats
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentStatsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCurrentStatsWithPatch() throws Exception {
        // Initialize the database
        currentStatsRepository.saveAndFlush(currentStats);

        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();

        // Update the currentStats using partial update
        CurrentStats partialUpdatedCurrentStats = new CurrentStats();
        partialUpdatedCurrentStats.setId(currentStats.getId());

        partialUpdatedCurrentStats.tradeVolume(UPDATED_TRADE_VOLUME);

        restCurrentStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrentStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrentStats))
            )
            .andExpect(status().isOk());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
        CurrentStats testCurrentStats = currentStatsList.get(currentStatsList.size() - 1);
        assertThat(testCurrentStats.getTotalBtc()).isEqualTo(DEFAULT_TOTAL_BTC);
        assertThat(testCurrentStats.getMarketCap()).isEqualTo(DEFAULT_MARKET_CAP);
        assertThat(testCurrentStats.getTradeVolume()).isEqualTo(UPDATED_TRADE_VOLUME);
    }

    @Test
    @Transactional
    void fullUpdateCurrentStatsWithPatch() throws Exception {
        // Initialize the database
        currentStatsRepository.saveAndFlush(currentStats);

        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();

        // Update the currentStats using partial update
        CurrentStats partialUpdatedCurrentStats = new CurrentStats();
        partialUpdatedCurrentStats.setId(currentStats.getId());

        partialUpdatedCurrentStats.totalBtc(UPDATED_TOTAL_BTC).marketCap(UPDATED_MARKET_CAP).tradeVolume(UPDATED_TRADE_VOLUME);

        restCurrentStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrentStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrentStats))
            )
            .andExpect(status().isOk());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
        CurrentStats testCurrentStats = currentStatsList.get(currentStatsList.size() - 1);
        assertThat(testCurrentStats.getTotalBtc()).isEqualTo(UPDATED_TOTAL_BTC);
        assertThat(testCurrentStats.getMarketCap()).isEqualTo(UPDATED_MARKET_CAP);
        assertThat(testCurrentStats.getTradeVolume()).isEqualTo(UPDATED_TRADE_VOLUME);
    }

    @Test
    @Transactional
    void patchNonExistingCurrentStats() throws Exception {
        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();
        currentStats.setId(count.incrementAndGet());

        // Create the CurrentStats
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currentStatsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrentStats() throws Exception {
        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();
        currentStats.setId(count.incrementAndGet());

        // Create the CurrentStats
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrentStats() throws Exception {
        int databaseSizeBeforeUpdate = currentStatsRepository.findAll().size();
        currentStats.setId(count.incrementAndGet());

        // Create the CurrentStats
        CurrentStatsDTO currentStatsDTO = currentStatsMapper.toDto(currentStats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentStatsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currentStatsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrentStats in the database
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurrentStats() throws Exception {
        // Initialize the database
        currentStatsRepository.saveAndFlush(currentStats);

        int databaseSizeBeforeDelete = currentStatsRepository.findAll().size();

        // Delete the currentStats
        restCurrentStatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, currentStats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrentStats> currentStatsList = currentStatsRepository.findAll();
        assertThat(currentStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
