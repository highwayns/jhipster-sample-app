package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.RequestStatus;
import io.github.jhipster.sample.repository.RequestStatusRepository;
import io.github.jhipster.sample.service.dto.RequestStatusDTO;
import io.github.jhipster.sample.service.mapper.RequestStatusMapper;
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
 * Integration tests for the {@link RequestStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestStatusResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ES = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ZH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ZH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Autowired
    private RequestStatusMapper requestStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestStatusMockMvc;

    private RequestStatus requestStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestStatus createEntity(EntityManager em) {
        RequestStatus requestStatus = new RequestStatus()
            .nameEn(DEFAULT_NAME_EN)
            .nameEs(DEFAULT_NAME_ES)
            .nameRu(DEFAULT_NAME_RU)
            .nameZh(DEFAULT_NAME_ZH);
        return requestStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestStatus createUpdatedEntity(EntityManager em) {
        RequestStatus requestStatus = new RequestStatus()
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        return requestStatus;
    }

    @BeforeEach
    public void initTest() {
        requestStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestStatus() throws Exception {
        int databaseSizeBeforeCreate = requestStatusRepository.findAll().size();
        // Create the RequestStatus
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);
        restRequestStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeCreate + 1);
        RequestStatus testRequestStatus = requestStatusList.get(requestStatusList.size() - 1);
        assertThat(testRequestStatus.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testRequestStatus.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testRequestStatus.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testRequestStatus.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void createRequestStatusWithExistingId() throws Exception {
        // Create the RequestStatus with an existing ID
        requestStatus.setId(1L);
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        int databaseSizeBeforeCreate = requestStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusRepository.findAll().size();
        // set the field null
        requestStatus.setNameEn(null);

        // Create the RequestStatus, which fails.
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        restRequestStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusRepository.findAll().size();
        // set the field null
        requestStatus.setNameEs(null);

        // Create the RequestStatus, which fails.
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        restRequestStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusRepository.findAll().size();
        // set the field null
        requestStatus.setNameRu(null);

        // Create the RequestStatus, which fails.
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        restRequestStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestStatusRepository.findAll().size();
        // set the field null
        requestStatus.setNameZh(null);

        // Create the RequestStatus, which fails.
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        restRequestStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestStatuses() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        // Get all the requestStatusList
        restRequestStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameEs").value(hasItem(DEFAULT_NAME_ES)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameZh").value(hasItem(DEFAULT_NAME_ZH)));
    }

    @Test
    @Transactional
    void getRequestStatus() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        // Get the requestStatus
        restRequestStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, requestStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestStatus.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameEs").value(DEFAULT_NAME_ES))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameZh").value(DEFAULT_NAME_ZH));
    }

    @Test
    @Transactional
    void getNonExistingRequestStatus() throws Exception {
        // Get the requestStatus
        restRequestStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequestStatus() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();

        // Update the requestStatus
        RequestStatus updatedRequestStatus = requestStatusRepository.findById(requestStatus.getId()).get();
        // Disconnect from session so that the updates on updatedRequestStatus are not directly saved in db
        em.detach(updatedRequestStatus);
        updatedRequestStatus.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(updatedRequestStatus);

        restRequestStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
        RequestStatus testRequestStatus = requestStatusList.get(requestStatusList.size() - 1);
        assertThat(testRequestStatus.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testRequestStatus.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestStatus.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testRequestStatus.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void putNonExistingRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();
        requestStatus.setId(count.incrementAndGet());

        // Create the RequestStatus
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();
        requestStatus.setId(count.incrementAndGet());

        // Create the RequestStatus
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();
        requestStatus.setId(count.incrementAndGet());

        // Create the RequestStatus
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestStatusWithPatch() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();

        // Update the requestStatus using partial update
        RequestStatus partialUpdatedRequestStatus = new RequestStatus();
        partialUpdatedRequestStatus.setId(requestStatus.getId());

        partialUpdatedRequestStatus.nameEs(UPDATED_NAME_ES).nameZh(UPDATED_NAME_ZH);

        restRequestStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestStatus))
            )
            .andExpect(status().isOk());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
        RequestStatus testRequestStatus = requestStatusList.get(requestStatusList.size() - 1);
        assertThat(testRequestStatus.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testRequestStatus.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestStatus.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testRequestStatus.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void fullUpdateRequestStatusWithPatch() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();

        // Update the requestStatus using partial update
        RequestStatus partialUpdatedRequestStatus = new RequestStatus();
        partialUpdatedRequestStatus.setId(requestStatus.getId());

        partialUpdatedRequestStatus.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restRequestStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestStatus))
            )
            .andExpect(status().isOk());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
        RequestStatus testRequestStatus = requestStatusList.get(requestStatusList.size() - 1);
        assertThat(testRequestStatus.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testRequestStatus.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testRequestStatus.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testRequestStatus.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void patchNonExistingRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();
        requestStatus.setId(count.incrementAndGet());

        // Create the RequestStatus
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();
        requestStatus.setId(count.incrementAndGet());

        // Create the RequestStatus
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestStatus() throws Exception {
        int databaseSizeBeforeUpdate = requestStatusRepository.findAll().size();
        requestStatus.setId(count.incrementAndGet());

        // Create the RequestStatus
        RequestStatusDTO requestStatusDTO = requestStatusMapper.toDto(requestStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestStatus in the database
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestStatus() throws Exception {
        // Initialize the database
        requestStatusRepository.saveAndFlush(requestStatus);

        int databaseSizeBeforeDelete = requestStatusRepository.findAll().size();

        // Delete the requestStatus
        restRequestStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestStatus> requestStatusList = requestStatusRepository.findAll();
        assertThat(requestStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
