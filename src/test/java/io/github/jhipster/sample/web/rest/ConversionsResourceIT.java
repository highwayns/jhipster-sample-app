package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Conversions;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.ConversionsRepository;
import io.github.jhipster.sample.service.dto.ConversionsDTO;
import io.github.jhipster.sample.service.mapper.ConversionsMapper;
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
 * Integration tests for the {@link ConversionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConversionsResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final YesNo DEFAULT_IS_ACTIVE = YesNo.Y;
    private static final YesNo UPDATED_IS_ACTIVE = YesNo.N;

    private static final Double DEFAULT_TOTAL_WITHDRAWALS = 1D;
    private static final Double UPDATED_TOTAL_WITHDRAWALS = 2D;

    private static final Double DEFAULT_PROFIT_TO_FACTOR = 1D;
    private static final Double UPDATED_PROFIT_TO_FACTOR = 2D;

    private static final YesNo DEFAULT_FACTORED = YesNo.Y;
    private static final YesNo UPDATED_FACTORED = YesNo.N;

    private static final Instant DEFAULT_DATE_1 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_1 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/conversions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConversionsRepository conversionsRepository;

    @Autowired
    private ConversionsMapper conversionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConversionsMockMvc;

    private Conversions conversions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversions createEntity(EntityManager em) {
        Conversions conversions = new Conversions()
            .amount(DEFAULT_AMOUNT)
            .date(DEFAULT_DATE)
            .isActive(DEFAULT_IS_ACTIVE)
            .totalWithdrawals(DEFAULT_TOTAL_WITHDRAWALS)
            .profitToFactor(DEFAULT_PROFIT_TO_FACTOR)
            .factored(DEFAULT_FACTORED)
            .date1(DEFAULT_DATE_1);
        return conversions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversions createUpdatedEntity(EntityManager em) {
        Conversions conversions = new Conversions()
            .amount(UPDATED_AMOUNT)
            .date(UPDATED_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .totalWithdrawals(UPDATED_TOTAL_WITHDRAWALS)
            .profitToFactor(UPDATED_PROFIT_TO_FACTOR)
            .factored(UPDATED_FACTORED)
            .date1(UPDATED_DATE_1);
        return conversions;
    }

    @BeforeEach
    public void initTest() {
        conversions = createEntity(em);
    }

    @Test
    @Transactional
    void createConversions() throws Exception {
        int databaseSizeBeforeCreate = conversionsRepository.findAll().size();
        // Create the Conversions
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);
        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeCreate + 1);
        Conversions testConversions = conversionsList.get(conversionsList.size() - 1);
        assertThat(testConversions.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testConversions.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testConversions.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testConversions.getTotalWithdrawals()).isEqualTo(DEFAULT_TOTAL_WITHDRAWALS);
        assertThat(testConversions.getProfitToFactor()).isEqualTo(DEFAULT_PROFIT_TO_FACTOR);
        assertThat(testConversions.getFactored()).isEqualTo(DEFAULT_FACTORED);
        assertThat(testConversions.getDate1()).isEqualTo(DEFAULT_DATE_1);
    }

    @Test
    @Transactional
    void createConversionsWithExistingId() throws Exception {
        // Create the Conversions with an existing ID
        conversions.setId(1L);
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        int databaseSizeBeforeCreate = conversionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionsRepository.findAll().size();
        // set the field null
        conversions.setAmount(null);

        // Create the Conversions, which fails.
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionsRepository.findAll().size();
        // set the field null
        conversions.setDate(null);

        // Create the Conversions, which fails.
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionsRepository.findAll().size();
        // set the field null
        conversions.setIsActive(null);

        // Create the Conversions, which fails.
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalWithdrawalsIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionsRepository.findAll().size();
        // set the field null
        conversions.setTotalWithdrawals(null);

        // Create the Conversions, which fails.
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProfitToFactorIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionsRepository.findAll().size();
        // set the field null
        conversions.setProfitToFactor(null);

        // Create the Conversions, which fails.
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFactoredIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionsRepository.findAll().size();
        // set the field null
        conversions.setFactored(null);

        // Create the Conversions, which fails.
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDate1IsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionsRepository.findAll().size();
        // set the field null
        conversions.setDate1(null);

        // Create the Conversions, which fails.
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        restConversionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConversions() throws Exception {
        // Initialize the database
        conversionsRepository.saveAndFlush(conversions);

        // Get all the conversionsList
        restConversionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversions.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].totalWithdrawals").value(hasItem(DEFAULT_TOTAL_WITHDRAWALS.doubleValue())))
            .andExpect(jsonPath("$.[*].profitToFactor").value(hasItem(DEFAULT_PROFIT_TO_FACTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].factored").value(hasItem(DEFAULT_FACTORED.toString())))
            .andExpect(jsonPath("$.[*].date1").value(hasItem(DEFAULT_DATE_1.toString())));
    }

    @Test
    @Transactional
    void getConversions() throws Exception {
        // Initialize the database
        conversionsRepository.saveAndFlush(conversions);

        // Get the conversions
        restConversionsMockMvc
            .perform(get(ENTITY_API_URL_ID, conversions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conversions.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.toString()))
            .andExpect(jsonPath("$.totalWithdrawals").value(DEFAULT_TOTAL_WITHDRAWALS.doubleValue()))
            .andExpect(jsonPath("$.profitToFactor").value(DEFAULT_PROFIT_TO_FACTOR.doubleValue()))
            .andExpect(jsonPath("$.factored").value(DEFAULT_FACTORED.toString()))
            .andExpect(jsonPath("$.date1").value(DEFAULT_DATE_1.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConversions() throws Exception {
        // Get the conversions
        restConversionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConversions() throws Exception {
        // Initialize the database
        conversionsRepository.saveAndFlush(conversions);

        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();

        // Update the conversions
        Conversions updatedConversions = conversionsRepository.findById(conversions.getId()).get();
        // Disconnect from session so that the updates on updatedConversions are not directly saved in db
        em.detach(updatedConversions);
        updatedConversions
            .amount(UPDATED_AMOUNT)
            .date(UPDATED_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .totalWithdrawals(UPDATED_TOTAL_WITHDRAWALS)
            .profitToFactor(UPDATED_PROFIT_TO_FACTOR)
            .factored(UPDATED_FACTORED)
            .date1(UPDATED_DATE_1);
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(updatedConversions);

        restConversionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conversionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
        Conversions testConversions = conversionsList.get(conversionsList.size() - 1);
        assertThat(testConversions.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testConversions.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testConversions.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testConversions.getTotalWithdrawals()).isEqualTo(UPDATED_TOTAL_WITHDRAWALS);
        assertThat(testConversions.getProfitToFactor()).isEqualTo(UPDATED_PROFIT_TO_FACTOR);
        assertThat(testConversions.getFactored()).isEqualTo(UPDATED_FACTORED);
        assertThat(testConversions.getDate1()).isEqualTo(UPDATED_DATE_1);
    }

    @Test
    @Transactional
    void putNonExistingConversions() throws Exception {
        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();
        conversions.setId(count.incrementAndGet());

        // Create the Conversions
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conversionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConversions() throws Exception {
        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();
        conversions.setId(count.incrementAndGet());

        // Create the Conversions
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConversions() throws Exception {
        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();
        conversions.setId(count.incrementAndGet());

        // Create the Conversions
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConversionsWithPatch() throws Exception {
        // Initialize the database
        conversionsRepository.saveAndFlush(conversions);

        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();

        // Update the conversions using partial update
        Conversions partialUpdatedConversions = new Conversions();
        partialUpdatedConversions.setId(conversions.getId());

        partialUpdatedConversions.date(UPDATED_DATE).totalWithdrawals(UPDATED_TOTAL_WITHDRAWALS).date1(UPDATED_DATE_1);

        restConversionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConversions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConversions))
            )
            .andExpect(status().isOk());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
        Conversions testConversions = conversionsList.get(conversionsList.size() - 1);
        assertThat(testConversions.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testConversions.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testConversions.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testConversions.getTotalWithdrawals()).isEqualTo(UPDATED_TOTAL_WITHDRAWALS);
        assertThat(testConversions.getProfitToFactor()).isEqualTo(DEFAULT_PROFIT_TO_FACTOR);
        assertThat(testConversions.getFactored()).isEqualTo(DEFAULT_FACTORED);
        assertThat(testConversions.getDate1()).isEqualTo(UPDATED_DATE_1);
    }

    @Test
    @Transactional
    void fullUpdateConversionsWithPatch() throws Exception {
        // Initialize the database
        conversionsRepository.saveAndFlush(conversions);

        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();

        // Update the conversions using partial update
        Conversions partialUpdatedConversions = new Conversions();
        partialUpdatedConversions.setId(conversions.getId());

        partialUpdatedConversions
            .amount(UPDATED_AMOUNT)
            .date(UPDATED_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .totalWithdrawals(UPDATED_TOTAL_WITHDRAWALS)
            .profitToFactor(UPDATED_PROFIT_TO_FACTOR)
            .factored(UPDATED_FACTORED)
            .date1(UPDATED_DATE_1);

        restConversionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConversions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConversions))
            )
            .andExpect(status().isOk());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
        Conversions testConversions = conversionsList.get(conversionsList.size() - 1);
        assertThat(testConversions.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testConversions.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testConversions.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testConversions.getTotalWithdrawals()).isEqualTo(UPDATED_TOTAL_WITHDRAWALS);
        assertThat(testConversions.getProfitToFactor()).isEqualTo(UPDATED_PROFIT_TO_FACTOR);
        assertThat(testConversions.getFactored()).isEqualTo(UPDATED_FACTORED);
        assertThat(testConversions.getDate1()).isEqualTo(UPDATED_DATE_1);
    }

    @Test
    @Transactional
    void patchNonExistingConversions() throws Exception {
        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();
        conversions.setId(count.incrementAndGet());

        // Create the Conversions
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conversionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConversions() throws Exception {
        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();
        conversions.setId(count.incrementAndGet());

        // Create the Conversions
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConversions() throws Exception {
        int databaseSizeBeforeUpdate = conversionsRepository.findAll().size();
        conversions.setId(count.incrementAndGet());

        // Create the Conversions
        ConversionsDTO conversionsDTO = conversionsMapper.toDto(conversions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conversionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conversions in the database
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConversions() throws Exception {
        // Initialize the database
        conversionsRepository.saveAndFlush(conversions);

        int databaseSizeBeforeDelete = conversionsRepository.findAll().size();

        // Delete the conversions
        restConversionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, conversions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conversions> conversionsList = conversionsRepository.findAll();
        assertThat(conversionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
