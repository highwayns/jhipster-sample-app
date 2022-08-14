package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.StatusEscrows;
import io.github.jhipster.sample.repository.StatusEscrowsRepository;
import io.github.jhipster.sample.service.dto.StatusEscrowsDTO;
import io.github.jhipster.sample.service.mapper.StatusEscrowsMapper;
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
 * Integration tests for the {@link StatusEscrowsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatusEscrowsResourceIT {

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final String ENTITY_API_URL = "/api/status-escrows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusEscrowsRepository statusEscrowsRepository;

    @Autowired
    private StatusEscrowsMapper statusEscrowsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusEscrowsMockMvc;

    private StatusEscrows statusEscrows;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusEscrows createEntity(EntityManager em) {
        StatusEscrows statusEscrows = new StatusEscrows().balance(DEFAULT_BALANCE);
        return statusEscrows;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusEscrows createUpdatedEntity(EntityManager em) {
        StatusEscrows statusEscrows = new StatusEscrows().balance(UPDATED_BALANCE);
        return statusEscrows;
    }

    @BeforeEach
    public void initTest() {
        statusEscrows = createEntity(em);
    }

    @Test
    @Transactional
    void createStatusEscrows() throws Exception {
        int databaseSizeBeforeCreate = statusEscrowsRepository.findAll().size();
        // Create the StatusEscrows
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);
        restStatusEscrowsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeCreate + 1);
        StatusEscrows testStatusEscrows = statusEscrowsList.get(statusEscrowsList.size() - 1);
        assertThat(testStatusEscrows.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void createStatusEscrowsWithExistingId() throws Exception {
        // Create the StatusEscrows with an existing ID
        statusEscrows.setId(1L);
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        int databaseSizeBeforeCreate = statusEscrowsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusEscrowsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusEscrowsRepository.findAll().size();
        // set the field null
        statusEscrows.setBalance(null);

        // Create the StatusEscrows, which fails.
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        restStatusEscrowsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isBadRequest());

        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStatusEscrows() throws Exception {
        // Initialize the database
        statusEscrowsRepository.saveAndFlush(statusEscrows);

        // Get all the statusEscrowsList
        restStatusEscrowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusEscrows.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())));
    }

    @Test
    @Transactional
    void getStatusEscrows() throws Exception {
        // Initialize the database
        statusEscrowsRepository.saveAndFlush(statusEscrows);

        // Get the statusEscrows
        restStatusEscrowsMockMvc
            .perform(get(ENTITY_API_URL_ID, statusEscrows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusEscrows.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingStatusEscrows() throws Exception {
        // Get the statusEscrows
        restStatusEscrowsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatusEscrows() throws Exception {
        // Initialize the database
        statusEscrowsRepository.saveAndFlush(statusEscrows);

        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();

        // Update the statusEscrows
        StatusEscrows updatedStatusEscrows = statusEscrowsRepository.findById(statusEscrows.getId()).get();
        // Disconnect from session so that the updates on updatedStatusEscrows are not directly saved in db
        em.detach(updatedStatusEscrows);
        updatedStatusEscrows.balance(UPDATED_BALANCE);
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(updatedStatusEscrows);

        restStatusEscrowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusEscrowsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isOk());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
        StatusEscrows testStatusEscrows = statusEscrowsList.get(statusEscrowsList.size() - 1);
        assertThat(testStatusEscrows.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingStatusEscrows() throws Exception {
        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();
        statusEscrows.setId(count.incrementAndGet());

        // Create the StatusEscrows
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusEscrowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusEscrowsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatusEscrows() throws Exception {
        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();
        statusEscrows.setId(count.incrementAndGet());

        // Create the StatusEscrows
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusEscrowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatusEscrows() throws Exception {
        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();
        statusEscrows.setId(count.incrementAndGet());

        // Create the StatusEscrows
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusEscrowsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusEscrowsWithPatch() throws Exception {
        // Initialize the database
        statusEscrowsRepository.saveAndFlush(statusEscrows);

        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();

        // Update the statusEscrows using partial update
        StatusEscrows partialUpdatedStatusEscrows = new StatusEscrows();
        partialUpdatedStatusEscrows.setId(statusEscrows.getId());

        restStatusEscrowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusEscrows.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusEscrows))
            )
            .andExpect(status().isOk());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
        StatusEscrows testStatusEscrows = statusEscrowsList.get(statusEscrowsList.size() - 1);
        assertThat(testStatusEscrows.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdateStatusEscrowsWithPatch() throws Exception {
        // Initialize the database
        statusEscrowsRepository.saveAndFlush(statusEscrows);

        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();

        // Update the statusEscrows using partial update
        StatusEscrows partialUpdatedStatusEscrows = new StatusEscrows();
        partialUpdatedStatusEscrows.setId(statusEscrows.getId());

        partialUpdatedStatusEscrows.balance(UPDATED_BALANCE);

        restStatusEscrowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusEscrows.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusEscrows))
            )
            .andExpect(status().isOk());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
        StatusEscrows testStatusEscrows = statusEscrowsList.get(statusEscrowsList.size() - 1);
        assertThat(testStatusEscrows.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingStatusEscrows() throws Exception {
        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();
        statusEscrows.setId(count.incrementAndGet());

        // Create the StatusEscrows
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusEscrowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusEscrowsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatusEscrows() throws Exception {
        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();
        statusEscrows.setId(count.incrementAndGet());

        // Create the StatusEscrows
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusEscrowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatusEscrows() throws Exception {
        int databaseSizeBeforeUpdate = statusEscrowsRepository.findAll().size();
        statusEscrows.setId(count.incrementAndGet());

        // Create the StatusEscrows
        StatusEscrowsDTO statusEscrowsDTO = statusEscrowsMapper.toDto(statusEscrows);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusEscrowsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusEscrowsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusEscrows in the database
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatusEscrows() throws Exception {
        // Initialize the database
        statusEscrowsRepository.saveAndFlush(statusEscrows);

        int databaseSizeBeforeDelete = statusEscrowsRepository.findAll().size();

        // Delete the statusEscrows
        restStatusEscrowsMockMvc
            .perform(delete(ENTITY_API_URL_ID, statusEscrows.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusEscrows> statusEscrowsList = statusEscrowsRepository.findAll();
        assertThat(statusEscrowsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
