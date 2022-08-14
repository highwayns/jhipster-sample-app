package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.HistoryActions;
import io.github.jhipster.sample.repository.HistoryActionsRepository;
import io.github.jhipster.sample.service.dto.HistoryActionsDTO;
import io.github.jhipster.sample.service.mapper.HistoryActionsMapper;
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
 * Integration tests for the {@link HistoryActionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoryActionsResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ES = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ZH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ZH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/history-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoryActionsRepository historyActionsRepository;

    @Autowired
    private HistoryActionsMapper historyActionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoryActionsMockMvc;

    private HistoryActions historyActions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoryActions createEntity(EntityManager em) {
        HistoryActions historyActions = new HistoryActions()
            .nameEn(DEFAULT_NAME_EN)
            .nameEs(DEFAULT_NAME_ES)
            .nameRu(DEFAULT_NAME_RU)
            .nameZh(DEFAULT_NAME_ZH);
        return historyActions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoryActions createUpdatedEntity(EntityManager em) {
        HistoryActions historyActions = new HistoryActions()
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        return historyActions;
    }

    @BeforeEach
    public void initTest() {
        historyActions = createEntity(em);
    }

    @Test
    @Transactional
    void createHistoryActions() throws Exception {
        int databaseSizeBeforeCreate = historyActionsRepository.findAll().size();
        // Create the HistoryActions
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);
        restHistoryActionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeCreate + 1);
        HistoryActions testHistoryActions = historyActionsList.get(historyActionsList.size() - 1);
        assertThat(testHistoryActions.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testHistoryActions.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testHistoryActions.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testHistoryActions.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void createHistoryActionsWithExistingId() throws Exception {
        // Create the HistoryActions with an existing ID
        historyActions.setId(1L);
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        int databaseSizeBeforeCreate = historyActionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoryActionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyActionsRepository.findAll().size();
        // set the field null
        historyActions.setNameEn(null);

        // Create the HistoryActions, which fails.
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        restHistoryActionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyActionsRepository.findAll().size();
        // set the field null
        historyActions.setNameEs(null);

        // Create the HistoryActions, which fails.
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        restHistoryActionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyActionsRepository.findAll().size();
        // set the field null
        historyActions.setNameRu(null);

        // Create the HistoryActions, which fails.
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        restHistoryActionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyActionsRepository.findAll().size();
        // set the field null
        historyActions.setNameZh(null);

        // Create the HistoryActions, which fails.
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        restHistoryActionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHistoryActions() throws Exception {
        // Initialize the database
        historyActionsRepository.saveAndFlush(historyActions);

        // Get all the historyActionsList
        restHistoryActionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historyActions.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameEs").value(hasItem(DEFAULT_NAME_ES)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameZh").value(hasItem(DEFAULT_NAME_ZH)));
    }

    @Test
    @Transactional
    void getHistoryActions() throws Exception {
        // Initialize the database
        historyActionsRepository.saveAndFlush(historyActions);

        // Get the historyActions
        restHistoryActionsMockMvc
            .perform(get(ENTITY_API_URL_ID, historyActions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historyActions.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameEs").value(DEFAULT_NAME_ES))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameZh").value(DEFAULT_NAME_ZH));
    }

    @Test
    @Transactional
    void getNonExistingHistoryActions() throws Exception {
        // Get the historyActions
        restHistoryActionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHistoryActions() throws Exception {
        // Initialize the database
        historyActionsRepository.saveAndFlush(historyActions);

        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();

        // Update the historyActions
        HistoryActions updatedHistoryActions = historyActionsRepository.findById(historyActions.getId()).get();
        // Disconnect from session so that the updates on updatedHistoryActions are not directly saved in db
        em.detach(updatedHistoryActions);
        updatedHistoryActions.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(updatedHistoryActions);

        restHistoryActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyActionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
        HistoryActions testHistoryActions = historyActionsList.get(historyActionsList.size() - 1);
        assertThat(testHistoryActions.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testHistoryActions.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testHistoryActions.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testHistoryActions.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void putNonExistingHistoryActions() throws Exception {
        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();
        historyActions.setId(count.incrementAndGet());

        // Create the HistoryActions
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyActionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistoryActions() throws Exception {
        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();
        historyActions.setId(count.incrementAndGet());

        // Create the HistoryActions
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistoryActions() throws Exception {
        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();
        historyActions.setId(count.incrementAndGet());

        // Create the HistoryActions
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryActionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoryActionsWithPatch() throws Exception {
        // Initialize the database
        historyActionsRepository.saveAndFlush(historyActions);

        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();

        // Update the historyActions using partial update
        HistoryActions partialUpdatedHistoryActions = new HistoryActions();
        partialUpdatedHistoryActions.setId(historyActions.getId());

        partialUpdatedHistoryActions.nameEs(UPDATED_NAME_ES).nameZh(UPDATED_NAME_ZH);

        restHistoryActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoryActions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoryActions))
            )
            .andExpect(status().isOk());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
        HistoryActions testHistoryActions = historyActionsList.get(historyActionsList.size() - 1);
        assertThat(testHistoryActions.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testHistoryActions.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testHistoryActions.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testHistoryActions.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void fullUpdateHistoryActionsWithPatch() throws Exception {
        // Initialize the database
        historyActionsRepository.saveAndFlush(historyActions);

        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();

        // Update the historyActions using partial update
        HistoryActions partialUpdatedHistoryActions = new HistoryActions();
        partialUpdatedHistoryActions.setId(historyActions.getId());

        partialUpdatedHistoryActions.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restHistoryActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoryActions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoryActions))
            )
            .andExpect(status().isOk());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
        HistoryActions testHistoryActions = historyActionsList.get(historyActionsList.size() - 1);
        assertThat(testHistoryActions.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testHistoryActions.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testHistoryActions.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testHistoryActions.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void patchNonExistingHistoryActions() throws Exception {
        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();
        historyActions.setId(count.incrementAndGet());

        // Create the HistoryActions
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historyActionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistoryActions() throws Exception {
        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();
        historyActions.setId(count.incrementAndGet());

        // Create the HistoryActions
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoryActions() throws Exception {
        int databaseSizeBeforeUpdate = historyActionsRepository.findAll().size();
        historyActions.setId(count.incrementAndGet());

        // Create the HistoryActions
        HistoryActionsDTO historyActionsDTO = historyActionsMapper.toDto(historyActions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryActionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historyActionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoryActions in the database
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistoryActions() throws Exception {
        // Initialize the database
        historyActionsRepository.saveAndFlush(historyActions);

        int databaseSizeBeforeDelete = historyActionsRepository.findAll().size();

        // Delete the historyActions
        restHistoryActionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, historyActions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoryActions> historyActionsList = historyActionsRepository.findAll();
        assertThat(historyActionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
