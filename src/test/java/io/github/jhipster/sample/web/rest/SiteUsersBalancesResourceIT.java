package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.SiteUsersBalances;
import io.github.jhipster.sample.repository.SiteUsersBalancesRepository;
import io.github.jhipster.sample.service.dto.SiteUsersBalancesDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersBalancesMapper;
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
 * Integration tests for the {@link SiteUsersBalancesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteUsersBalancesResourceIT {

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final String ENTITY_API_URL = "/api/site-users-balances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteUsersBalancesRepository siteUsersBalancesRepository;

    @Autowired
    private SiteUsersBalancesMapper siteUsersBalancesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteUsersBalancesMockMvc;

    private SiteUsersBalances siteUsersBalances;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsersBalances createEntity(EntityManager em) {
        SiteUsersBalances siteUsersBalances = new SiteUsersBalances().balance(DEFAULT_BALANCE);
        return siteUsersBalances;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsersBalances createUpdatedEntity(EntityManager em) {
        SiteUsersBalances siteUsersBalances = new SiteUsersBalances().balance(UPDATED_BALANCE);
        return siteUsersBalances;
    }

    @BeforeEach
    public void initTest() {
        siteUsersBalances = createEntity(em);
    }

    @Test
    @Transactional
    void createSiteUsersBalances() throws Exception {
        int databaseSizeBeforeCreate = siteUsersBalancesRepository.findAll().size();
        // Create the SiteUsersBalances
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);
        restSiteUsersBalancesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeCreate + 1);
        SiteUsersBalances testSiteUsersBalances = siteUsersBalancesList.get(siteUsersBalancesList.size() - 1);
        assertThat(testSiteUsersBalances.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void createSiteUsersBalancesWithExistingId() throws Exception {
        // Create the SiteUsersBalances with an existing ID
        siteUsersBalances.setId(1L);
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        int databaseSizeBeforeCreate = siteUsersBalancesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteUsersBalancesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersBalancesRepository.findAll().size();
        // set the field null
        siteUsersBalances.setBalance(null);

        // Create the SiteUsersBalances, which fails.
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        restSiteUsersBalancesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isBadRequest());

        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSiteUsersBalances() throws Exception {
        // Initialize the database
        siteUsersBalancesRepository.saveAndFlush(siteUsersBalances);

        // Get all the siteUsersBalancesList
        restSiteUsersBalancesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteUsersBalances.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())));
    }

    @Test
    @Transactional
    void getSiteUsersBalances() throws Exception {
        // Initialize the database
        siteUsersBalancesRepository.saveAndFlush(siteUsersBalances);

        // Get the siteUsersBalances
        restSiteUsersBalancesMockMvc
            .perform(get(ENTITY_API_URL_ID, siteUsersBalances.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteUsersBalances.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSiteUsersBalances() throws Exception {
        // Get the siteUsersBalances
        restSiteUsersBalancesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiteUsersBalances() throws Exception {
        // Initialize the database
        siteUsersBalancesRepository.saveAndFlush(siteUsersBalances);

        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();

        // Update the siteUsersBalances
        SiteUsersBalances updatedSiteUsersBalances = siteUsersBalancesRepository.findById(siteUsersBalances.getId()).get();
        // Disconnect from session so that the updates on updatedSiteUsersBalances are not directly saved in db
        em.detach(updatedSiteUsersBalances);
        updatedSiteUsersBalances.balance(UPDATED_BALANCE);
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(updatedSiteUsersBalances);

        restSiteUsersBalancesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersBalancesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersBalances testSiteUsersBalances = siteUsersBalancesList.get(siteUsersBalancesList.size() - 1);
        assertThat(testSiteUsersBalances.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingSiteUsersBalances() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();
        siteUsersBalances.setId(count.incrementAndGet());

        // Create the SiteUsersBalances
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersBalancesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersBalancesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteUsersBalances() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();
        siteUsersBalances.setId(count.incrementAndGet());

        // Create the SiteUsersBalances
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersBalancesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteUsersBalances() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();
        siteUsersBalances.setId(count.incrementAndGet());

        // Create the SiteUsersBalances
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersBalancesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteUsersBalancesWithPatch() throws Exception {
        // Initialize the database
        siteUsersBalancesRepository.saveAndFlush(siteUsersBalances);

        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();

        // Update the siteUsersBalances using partial update
        SiteUsersBalances partialUpdatedSiteUsersBalances = new SiteUsersBalances();
        partialUpdatedSiteUsersBalances.setId(siteUsersBalances.getId());

        restSiteUsersBalancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsersBalances.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsersBalances))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersBalances testSiteUsersBalances = siteUsersBalancesList.get(siteUsersBalancesList.size() - 1);
        assertThat(testSiteUsersBalances.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdateSiteUsersBalancesWithPatch() throws Exception {
        // Initialize the database
        siteUsersBalancesRepository.saveAndFlush(siteUsersBalances);

        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();

        // Update the siteUsersBalances using partial update
        SiteUsersBalances partialUpdatedSiteUsersBalances = new SiteUsersBalances();
        partialUpdatedSiteUsersBalances.setId(siteUsersBalances.getId());

        partialUpdatedSiteUsersBalances.balance(UPDATED_BALANCE);

        restSiteUsersBalancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsersBalances.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsersBalances))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
        SiteUsersBalances testSiteUsersBalances = siteUsersBalancesList.get(siteUsersBalancesList.size() - 1);
        assertThat(testSiteUsersBalances.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingSiteUsersBalances() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();
        siteUsersBalances.setId(count.incrementAndGet());

        // Create the SiteUsersBalances
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersBalancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteUsersBalancesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteUsersBalances() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();
        siteUsersBalances.setId(count.incrementAndGet());

        // Create the SiteUsersBalances
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersBalancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteUsersBalances() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersBalancesRepository.findAll().size();
        siteUsersBalances.setId(count.incrementAndGet());

        // Create the SiteUsersBalances
        SiteUsersBalancesDTO siteUsersBalancesDTO = siteUsersBalancesMapper.toDto(siteUsersBalances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersBalancesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersBalancesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsersBalances in the database
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteUsersBalances() throws Exception {
        // Initialize the database
        siteUsersBalancesRepository.saveAndFlush(siteUsersBalances);

        int databaseSizeBeforeDelete = siteUsersBalancesRepository.findAll().size();

        // Delete the siteUsersBalances
        restSiteUsersBalancesMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteUsersBalances.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteUsersBalances> siteUsersBalancesList = siteUsersBalancesRepository.findAll();
        assertThat(siteUsersBalancesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
