package io.github.jhipster.sample.web.rest;

import static io.github.jhipster.sample.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.HistoricalData;
import io.github.jhipster.sample.repository.HistoricalDataRepository;
import io.github.jhipster.sample.service.dto.HistoricalDataDTO;
import io.github.jhipster.sample.service.mapper.HistoricalDataMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link HistoricalDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoricalDataResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_USD = new BigDecimal(1);
    private static final BigDecimal UPDATED_USD = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/historical-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoricalDataRepository historicalDataRepository;

    @Autowired
    private HistoricalDataMapper historicalDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoricalDataMockMvc;

    private HistoricalData historicalData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoricalData createEntity(EntityManager em) {
        HistoricalData historicalData = new HistoricalData().date(DEFAULT_DATE).usd(DEFAULT_USD);
        return historicalData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoricalData createUpdatedEntity(EntityManager em) {
        HistoricalData historicalData = new HistoricalData().date(UPDATED_DATE).usd(UPDATED_USD);
        return historicalData;
    }

    @BeforeEach
    public void initTest() {
        historicalData = createEntity(em);
    }

    @Test
    @Transactional
    void createHistoricalData() throws Exception {
        int databaseSizeBeforeCreate = historicalDataRepository.findAll().size();
        // Create the HistoricalData
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);
        restHistoricalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeCreate + 1);
        HistoricalData testHistoricalData = historicalDataList.get(historicalDataList.size() - 1);
        assertThat(testHistoricalData.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHistoricalData.getUsd()).isEqualByComparingTo(DEFAULT_USD);
    }

    @Test
    @Transactional
    void createHistoricalDataWithExistingId() throws Exception {
        // Create the HistoricalData with an existing ID
        historicalData.setId(1L);
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        int databaseSizeBeforeCreate = historicalDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoricalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalDataRepository.findAll().size();
        // set the field null
        historicalData.setDate(null);

        // Create the HistoricalData, which fails.
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        restHistoricalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsdIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalDataRepository.findAll().size();
        // set the field null
        historicalData.setUsd(null);

        // Create the HistoricalData, which fails.
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        restHistoricalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHistoricalData() throws Exception {
        // Initialize the database
        historicalDataRepository.saveAndFlush(historicalData);

        // Get all the historicalDataList
        restHistoricalDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].usd").value(hasItem(sameNumber(DEFAULT_USD))));
    }

    @Test
    @Transactional
    void getHistoricalData() throws Exception {
        // Initialize the database
        historicalDataRepository.saveAndFlush(historicalData);

        // Get the historicalData
        restHistoricalDataMockMvc
            .perform(get(ENTITY_API_URL_ID, historicalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historicalData.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.usd").value(sameNumber(DEFAULT_USD)));
    }

    @Test
    @Transactional
    void getNonExistingHistoricalData() throws Exception {
        // Get the historicalData
        restHistoricalDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHistoricalData() throws Exception {
        // Initialize the database
        historicalDataRepository.saveAndFlush(historicalData);

        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();

        // Update the historicalData
        HistoricalData updatedHistoricalData = historicalDataRepository.findById(historicalData.getId()).get();
        // Disconnect from session so that the updates on updatedHistoricalData are not directly saved in db
        em.detach(updatedHistoricalData);
        updatedHistoricalData.date(UPDATED_DATE).usd(UPDATED_USD);
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(updatedHistoricalData);

        restHistoricalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historicalDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
        HistoricalData testHistoricalData = historicalDataList.get(historicalDataList.size() - 1);
        assertThat(testHistoricalData.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHistoricalData.getUsd()).isEqualByComparingTo(UPDATED_USD);
    }

    @Test
    @Transactional
    void putNonExistingHistoricalData() throws Exception {
        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();
        historicalData.setId(count.incrementAndGet());

        // Create the HistoricalData
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historicalDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistoricalData() throws Exception {
        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();
        historicalData.setId(count.incrementAndGet());

        // Create the HistoricalData
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistoricalData() throws Exception {
        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();
        historicalData.setId(count.incrementAndGet());

        // Create the HistoricalData
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoricalDataWithPatch() throws Exception {
        // Initialize the database
        historicalDataRepository.saveAndFlush(historicalData);

        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();

        // Update the historicalData using partial update
        HistoricalData partialUpdatedHistoricalData = new HistoricalData();
        partialUpdatedHistoricalData.setId(historicalData.getId());

        partialUpdatedHistoricalData.date(UPDATED_DATE);

        restHistoricalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoricalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoricalData))
            )
            .andExpect(status().isOk());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
        HistoricalData testHistoricalData = historicalDataList.get(historicalDataList.size() - 1);
        assertThat(testHistoricalData.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHistoricalData.getUsd()).isEqualByComparingTo(DEFAULT_USD);
    }

    @Test
    @Transactional
    void fullUpdateHistoricalDataWithPatch() throws Exception {
        // Initialize the database
        historicalDataRepository.saveAndFlush(historicalData);

        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();

        // Update the historicalData using partial update
        HistoricalData partialUpdatedHistoricalData = new HistoricalData();
        partialUpdatedHistoricalData.setId(historicalData.getId());

        partialUpdatedHistoricalData.date(UPDATED_DATE).usd(UPDATED_USD);

        restHistoricalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoricalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoricalData))
            )
            .andExpect(status().isOk());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
        HistoricalData testHistoricalData = historicalDataList.get(historicalDataList.size() - 1);
        assertThat(testHistoricalData.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHistoricalData.getUsd()).isEqualByComparingTo(UPDATED_USD);
    }

    @Test
    @Transactional
    void patchNonExistingHistoricalData() throws Exception {
        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();
        historicalData.setId(count.incrementAndGet());

        // Create the HistoricalData
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historicalDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistoricalData() throws Exception {
        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();
        historicalData.setId(count.incrementAndGet());

        // Create the HistoricalData
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoricalData() throws Exception {
        int databaseSizeBeforeUpdate = historicalDataRepository.findAll().size();
        historicalData.setId(count.incrementAndGet());

        // Create the HistoricalData
        HistoricalDataDTO historicalDataDTO = historicalDataMapper.toDto(historicalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicalDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoricalData in the database
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistoricalData() throws Exception {
        // Initialize the database
        historicalDataRepository.saveAndFlush(historicalData);

        int databaseSizeBeforeDelete = historicalDataRepository.findAll().size();

        // Delete the historicalData
        restHistoricalDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, historicalData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoricalData> historicalDataList = historicalDataRepository.findAll();
        assertThat(historicalDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
