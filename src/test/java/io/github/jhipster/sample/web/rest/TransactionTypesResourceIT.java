package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.TransactionTypes;
import io.github.jhipster.sample.repository.TransactionTypesRepository;
import io.github.jhipster.sample.service.dto.TransactionTypesDTO;
import io.github.jhipster.sample.service.mapper.TransactionTypesMapper;
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
 * Integration tests for the {@link TransactionTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionTypesResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ES = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ZH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ZH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transaction-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;

    @Autowired
    private TransactionTypesMapper transactionTypesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionTypesMockMvc;

    private TransactionTypes transactionTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionTypes createEntity(EntityManager em) {
        TransactionTypes transactionTypes = new TransactionTypes()
            .nameEn(DEFAULT_NAME_EN)
            .nameEs(DEFAULT_NAME_ES)
            .nameRu(DEFAULT_NAME_RU)
            .nameZh(DEFAULT_NAME_ZH);
        return transactionTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionTypes createUpdatedEntity(EntityManager em) {
        TransactionTypes transactionTypes = new TransactionTypes()
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        return transactionTypes;
    }

    @BeforeEach
    public void initTest() {
        transactionTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createTransactionTypes() throws Exception {
        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();
        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);
        restTransactionTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testTransactionTypes.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testTransactionTypes.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testTransactionTypes.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void createTransactionTypesWithExistingId() throws Exception {
        // Create the TransactionTypes with an existing ID
        transactionTypes.setId(1L);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setNameEn(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setNameEs(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setNameRu(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setNameZh(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList
        restTransactionTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameEs").value(hasItem(DEFAULT_NAME_ES)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameZh").value(hasItem(DEFAULT_NAME_ZH)));
    }

    @Test
    @Transactional
    void getTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get the transactionTypes
        restTransactionTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, transactionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionTypes.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameEs").value(DEFAULT_NAME_ES))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameZh").value(DEFAULT_NAME_ZH));
    }

    @Test
    @Transactional
    void getNonExistingTransactionTypes() throws Exception {
        // Get the transactionTypes
        restTransactionTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Update the transactionTypes
        TransactionTypes updatedTransactionTypes = transactionTypesRepository.findById(transactionTypes.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionTypes are not directly saved in db
        em.detach(updatedTransactionTypes);
        updatedTransactionTypes.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(updatedTransactionTypes);

        restTransactionTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionTypesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testTransactionTypes.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testTransactionTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testTransactionTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void putNonExistingTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();
        transactionTypes.setId(count.incrementAndGet());

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionTypesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();
        transactionTypes.setId(count.incrementAndGet());

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();
        transactionTypes.setId(count.incrementAndGet());

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionTypesWithPatch() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Update the transactionTypes using partial update
        TransactionTypes partialUpdatedTransactionTypes = new TransactionTypes();
        partialUpdatedTransactionTypes.setId(transactionTypes.getId());

        partialUpdatedTransactionTypes.nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restTransactionTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionTypes))
            )
            .andExpect(status().isOk());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testTransactionTypes.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testTransactionTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testTransactionTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void fullUpdateTransactionTypesWithPatch() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Update the transactionTypes using partial update
        TransactionTypes partialUpdatedTransactionTypes = new TransactionTypes();
        partialUpdatedTransactionTypes.setId(transactionTypes.getId());

        partialUpdatedTransactionTypes.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restTransactionTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactionTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactionTypes))
            )
            .andExpect(status().isOk());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testTransactionTypes.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testTransactionTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testTransactionTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void patchNonExistingTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();
        transactionTypes.setId(count.incrementAndGet());

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionTypesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();
        transactionTypes.setId(count.incrementAndGet());

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();
        transactionTypes.setId(count.incrementAndGet());

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeDelete = transactionTypesRepository.findAll().size();

        // Delete the transactionTypes
        restTransactionTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactionTypes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
