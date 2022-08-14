package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.RequestTypes;
import io.github.jhipster.sample.repository.RequestTypesRepository;
import io.github.jhipster.sample.service.dto.RequestTypesDTO;
import io.github.jhipster.sample.service.mapper.RequestTypesMapper;
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
 * Integration tests for the {@link RequestTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestTypesResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ES = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ZH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ZH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestTypesRepository requestTypesRepository;

    @Autowired
    private RequestTypesMapper requestTypesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestTypesMockMvc;

    private RequestTypes requestTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestTypes createEntity(EntityManager em) {
        RequestTypes requestTypes = new RequestTypes()
            .nameEn(DEFAULT_NAME_EN)
            .nameEs(DEFAULT_NAME_ES)
            .nameRu(DEFAULT_NAME_RU)
            .nameZh(DEFAULT_NAME_ZH);
        return requestTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestTypes createUpdatedEntity(EntityManager em) {
        RequestTypes requestTypes = new RequestTypes()
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        return requestTypes;
    }

    @BeforeEach
    public void initTest() {
        requestTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestTypes() throws Exception {
        int databaseSizeBeforeCreate = requestTypesRepository.findAll().size();
        // Create the RequestTypes
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);
        restRequestTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeCreate + 1);
        RequestTypes testRequestTypes = requestTypesList.get(requestTypesList.size() - 1);
        assertThat(testRequestTypes.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testRequestTypes.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testRequestTypes.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testRequestTypes.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void createRequestTypesWithExistingId() throws Exception {
        // Create the RequestTypes with an existing ID
        requestTypes.setId(1L);
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        int databaseSizeBeforeCreate = requestTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestTypesRepository.findAll().size();
        // set the field null
        requestTypes.setNameEn(null);

        // Create the RequestTypes, which fails.
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        restRequestTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestTypesRepository.findAll().size();
        // set the field null
        requestTypes.setNameEs(null);

        // Create the RequestTypes, which fails.
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        restRequestTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestTypesRepository.findAll().size();
        // set the field null
        requestTypes.setNameRu(null);

        // Create the RequestTypes, which fails.
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        restRequestTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestTypesRepository.findAll().size();
        // set the field null
        requestTypes.setNameZh(null);

        // Create the RequestTypes, which fails.
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        restRequestTypesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestTypes() throws Exception {
        // Initialize the database
        requestTypesRepository.saveAndFlush(requestTypes);

        // Get all the requestTypesList
        restRequestTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameEs").value(hasItem(DEFAULT_NAME_ES)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameZh").value(hasItem(DEFAULT_NAME_ZH)));
    }

    @Test
    @Transactional
    void getRequestTypes() throws Exception {
        // Initialize the database
        requestTypesRepository.saveAndFlush(requestTypes);

        // Get the requestTypes
        restRequestTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, requestTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestTypes.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameEs").value(DEFAULT_NAME_ES))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameZh").value(DEFAULT_NAME_ZH));
    }

    @Test
    @Transactional
    void getNonExistingRequestTypes() throws Exception {
        // Get the requestTypes
        restRequestTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequestTypes() throws Exception {
        // Initialize the database
        requestTypesRepository.saveAndFlush(requestTypes);

        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();

        // Update the requestTypes
        RequestTypes updatedRequestTypes = requestTypesRepository.findById(requestTypes.getId()).get();
        // Disconnect from session so that the updates on updatedRequestTypes are not directly saved in db
        em.detach(updatedRequestTypes);
        updatedRequestTypes.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(updatedRequestTypes);

        restRequestTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestTypesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
        RequestTypes testRequestTypes = requestTypesList.get(requestTypesList.size() - 1);
        assertThat(testRequestTypes.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testRequestTypes.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testRequestTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void putNonExistingRequestTypes() throws Exception {
        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();
        requestTypes.setId(count.incrementAndGet());

        // Create the RequestTypes
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestTypesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestTypes() throws Exception {
        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();
        requestTypes.setId(count.incrementAndGet());

        // Create the RequestTypes
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestTypes() throws Exception {
        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();
        requestTypes.setId(count.incrementAndGet());

        // Create the RequestTypes
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestTypesWithPatch() throws Exception {
        // Initialize the database
        requestTypesRepository.saveAndFlush(requestTypes);

        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();

        // Update the requestTypes using partial update
        RequestTypes partialUpdatedRequestTypes = new RequestTypes();
        partialUpdatedRequestTypes.setId(requestTypes.getId());

        partialUpdatedRequestTypes.nameEs(UPDATED_NAME_ES).nameZh(UPDATED_NAME_ZH);

        restRequestTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestTypes))
            )
            .andExpect(status().isOk());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
        RequestTypes testRequestTypes = requestTypesList.get(requestTypesList.size() - 1);
        assertThat(testRequestTypes.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testRequestTypes.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestTypes.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testRequestTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void fullUpdateRequestTypesWithPatch() throws Exception {
        // Initialize the database
        requestTypesRepository.saveAndFlush(requestTypes);

        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();

        // Update the requestTypes using partial update
        RequestTypes partialUpdatedRequestTypes = new RequestTypes();
        partialUpdatedRequestTypes.setId(requestTypes.getId());

        partialUpdatedRequestTypes.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restRequestTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestTypes))
            )
            .andExpect(status().isOk());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
        RequestTypes testRequestTypes = requestTypesList.get(requestTypesList.size() - 1);
        assertThat(testRequestTypes.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testRequestTypes.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testRequestTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void patchNonExistingRequestTypes() throws Exception {
        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();
        requestTypes.setId(count.incrementAndGet());

        // Create the RequestTypes
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestTypesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestTypes() throws Exception {
        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();
        requestTypes.setId(count.incrementAndGet());

        // Create the RequestTypes
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestTypes() throws Exception {
        int databaseSizeBeforeUpdate = requestTypesRepository.findAll().size();
        requestTypes.setId(count.incrementAndGet());

        // Create the RequestTypes
        RequestTypesDTO requestTypesDTO = requestTypesMapper.toDto(requestTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestTypesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestTypesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestTypes in the database
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestTypes() throws Exception {
        // Initialize the database
        requestTypesRepository.saveAndFlush(requestTypes);

        int databaseSizeBeforeDelete = requestTypesRepository.findAll().size();

        // Delete the requestTypes
        restRequestTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestTypes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestTypes> requestTypesList = requestTypesRepository.findAll();
        assertThat(requestTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
