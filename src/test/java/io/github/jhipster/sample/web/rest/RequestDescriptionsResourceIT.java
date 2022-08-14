package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.RequestDescriptions;
import io.github.jhipster.sample.repository.RequestDescriptionsRepository;
import io.github.jhipster.sample.service.dto.RequestDescriptionsDTO;
import io.github.jhipster.sample.service.mapper.RequestDescriptionsMapper;
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
 * Integration tests for the {@link RequestDescriptionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestDescriptionsResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ES = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ZH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ZH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-descriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestDescriptionsRepository requestDescriptionsRepository;

    @Autowired
    private RequestDescriptionsMapper requestDescriptionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestDescriptionsMockMvc;

    private RequestDescriptions requestDescriptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestDescriptions createEntity(EntityManager em) {
        RequestDescriptions requestDescriptions = new RequestDescriptions()
            .nameEn(DEFAULT_NAME_EN)
            .nameEs(DEFAULT_NAME_ES)
            .nameRu(DEFAULT_NAME_RU)
            .nameZh(DEFAULT_NAME_ZH);
        return requestDescriptions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestDescriptions createUpdatedEntity(EntityManager em) {
        RequestDescriptions requestDescriptions = new RequestDescriptions()
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        return requestDescriptions;
    }

    @BeforeEach
    public void initTest() {
        requestDescriptions = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestDescriptions() throws Exception {
        int databaseSizeBeforeCreate = requestDescriptionsRepository.findAll().size();
        // Create the RequestDescriptions
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);
        restRequestDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeCreate + 1);
        RequestDescriptions testRequestDescriptions = requestDescriptionsList.get(requestDescriptionsList.size() - 1);
        assertThat(testRequestDescriptions.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testRequestDescriptions.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testRequestDescriptions.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testRequestDescriptions.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void createRequestDescriptionsWithExistingId() throws Exception {
        // Create the RequestDescriptions with an existing ID
        requestDescriptions.setId(1L);
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        int databaseSizeBeforeCreate = requestDescriptionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestDescriptionsRepository.findAll().size();
        // set the field null
        requestDescriptions.setNameEn(null);

        // Create the RequestDescriptions, which fails.
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        restRequestDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestDescriptionsRepository.findAll().size();
        // set the field null
        requestDescriptions.setNameEs(null);

        // Create the RequestDescriptions, which fails.
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        restRequestDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestDescriptionsRepository.findAll().size();
        // set the field null
        requestDescriptions.setNameRu(null);

        // Create the RequestDescriptions, which fails.
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        restRequestDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestDescriptionsRepository.findAll().size();
        // set the field null
        requestDescriptions.setNameZh(null);

        // Create the RequestDescriptions, which fails.
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        restRequestDescriptionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestDescriptions() throws Exception {
        // Initialize the database
        requestDescriptionsRepository.saveAndFlush(requestDescriptions);

        // Get all the requestDescriptionsList
        restRequestDescriptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestDescriptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameEs").value(hasItem(DEFAULT_NAME_ES)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameZh").value(hasItem(DEFAULT_NAME_ZH)));
    }

    @Test
    @Transactional
    void getRequestDescriptions() throws Exception {
        // Initialize the database
        requestDescriptionsRepository.saveAndFlush(requestDescriptions);

        // Get the requestDescriptions
        restRequestDescriptionsMockMvc
            .perform(get(ENTITY_API_URL_ID, requestDescriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestDescriptions.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameEs").value(DEFAULT_NAME_ES))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameZh").value(DEFAULT_NAME_ZH));
    }

    @Test
    @Transactional
    void getNonExistingRequestDescriptions() throws Exception {
        // Get the requestDescriptions
        restRequestDescriptionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequestDescriptions() throws Exception {
        // Initialize the database
        requestDescriptionsRepository.saveAndFlush(requestDescriptions);

        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();

        // Update the requestDescriptions
        RequestDescriptions updatedRequestDescriptions = requestDescriptionsRepository.findById(requestDescriptions.getId()).get();
        // Disconnect from session so that the updates on updatedRequestDescriptions are not directly saved in db
        em.detach(updatedRequestDescriptions);
        updatedRequestDescriptions.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(updatedRequestDescriptions);

        restRequestDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestDescriptionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
        RequestDescriptions testRequestDescriptions = requestDescriptionsList.get(requestDescriptionsList.size() - 1);
        assertThat(testRequestDescriptions.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testRequestDescriptions.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestDescriptions.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testRequestDescriptions.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void putNonExistingRequestDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();
        requestDescriptions.setId(count.incrementAndGet());

        // Create the RequestDescriptions
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestDescriptionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();
        requestDescriptions.setId(count.incrementAndGet());

        // Create the RequestDescriptions
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();
        requestDescriptions.setId(count.incrementAndGet());

        // Create the RequestDescriptions
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestDescriptionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestDescriptionsWithPatch() throws Exception {
        // Initialize the database
        requestDescriptionsRepository.saveAndFlush(requestDescriptions);

        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();

        // Update the requestDescriptions using partial update
        RequestDescriptions partialUpdatedRequestDescriptions = new RequestDescriptions();
        partialUpdatedRequestDescriptions.setId(requestDescriptions.getId());

        partialUpdatedRequestDescriptions.nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restRequestDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestDescriptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestDescriptions))
            )
            .andExpect(status().isOk());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
        RequestDescriptions testRequestDescriptions = requestDescriptionsList.get(requestDescriptionsList.size() - 1);
        assertThat(testRequestDescriptions.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testRequestDescriptions.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestDescriptions.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testRequestDescriptions.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void fullUpdateRequestDescriptionsWithPatch() throws Exception {
        // Initialize the database
        requestDescriptionsRepository.saveAndFlush(requestDescriptions);

        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();

        // Update the requestDescriptions using partial update
        RequestDescriptions partialUpdatedRequestDescriptions = new RequestDescriptions();
        partialUpdatedRequestDescriptions.setId(requestDescriptions.getId());

        partialUpdatedRequestDescriptions.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restRequestDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestDescriptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestDescriptions))
            )
            .andExpect(status().isOk());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
        RequestDescriptions testRequestDescriptions = requestDescriptionsList.get(requestDescriptionsList.size() - 1);
        assertThat(testRequestDescriptions.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testRequestDescriptions.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestDescriptions.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testRequestDescriptions.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void patchNonExistingRequestDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();
        requestDescriptions.setId(count.incrementAndGet());

        // Create the RequestDescriptions
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestDescriptionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();
        requestDescriptions.setId(count.incrementAndGet());

        // Create the RequestDescriptions
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestDescriptions() throws Exception {
        int databaseSizeBeforeUpdate = requestDescriptionsRepository.findAll().size();
        requestDescriptions.setId(count.incrementAndGet());

        // Create the RequestDescriptions
        RequestDescriptionsDTO requestDescriptionsDTO = requestDescriptionsMapper.toDto(requestDescriptions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestDescriptionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestDescriptionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestDescriptions in the database
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestDescriptions() throws Exception {
        // Initialize the database
        requestDescriptionsRepository.saveAndFlush(requestDescriptions);

        int databaseSizeBeforeDelete = requestDescriptionsRepository.findAll().size();

        // Delete the requestDescriptions
        restRequestDescriptionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestDescriptions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestDescriptions> requestDescriptionsList = requestDescriptionsRepository.findAll();
        assertThat(requestDescriptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
