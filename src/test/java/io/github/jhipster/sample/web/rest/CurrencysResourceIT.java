package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Currencys;
import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.repository.CurrencysRepository;
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
 * Integration tests for the {@link CurrencysResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrencysResourceIT {

    private static final Currency DEFAULT_CURRENCY = Currency.CNY;
    private static final Currency UPDATED_CURRENCY = Currency.JPY;

    private static final String ENTITY_API_URL = "/api/currencys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrencysRepository currencysRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencysMockMvc;

    private Currencys currencys;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currencys createEntity(EntityManager em) {
        Currencys currencys = new Currencys().currency(DEFAULT_CURRENCY);
        return currencys;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currencys createUpdatedEntity(EntityManager em) {
        Currencys currencys = new Currencys().currency(UPDATED_CURRENCY);
        return currencys;
    }

    @BeforeEach
    public void initTest() {
        currencys = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrencys() throws Exception {
        int databaseSizeBeforeCreate = currencysRepository.findAll().size();
        // Create the Currencys
        restCurrencysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencys)))
            .andExpect(status().isCreated());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeCreate + 1);
        Currencys testCurrencys = currencysList.get(currencysList.size() - 1);
        assertThat(testCurrencys.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createCurrencysWithExistingId() throws Exception {
        // Create the Currencys with an existing ID
        currencys.setId(1L);

        int databaseSizeBeforeCreate = currencysRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencys)))
            .andExpect(status().isBadRequest());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCurrencys() throws Exception {
        // Initialize the database
        currencysRepository.saveAndFlush(currencys);

        // Get all the currencysList
        restCurrencysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencys.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())));
    }

    @Test
    @Transactional
    void getCurrencys() throws Exception {
        // Initialize the database
        currencysRepository.saveAndFlush(currencys);

        // Get the currencys
        restCurrencysMockMvc
            .perform(get(ENTITY_API_URL_ID, currencys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currencys.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCurrencys() throws Exception {
        // Get the currencys
        restCurrencysMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCurrencys() throws Exception {
        // Initialize the database
        currencysRepository.saveAndFlush(currencys);

        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();

        // Update the currencys
        Currencys updatedCurrencys = currencysRepository.findById(currencys.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencys are not directly saved in db
        em.detach(updatedCurrencys);
        updatedCurrencys.currency(UPDATED_CURRENCY);

        restCurrencysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCurrencys.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCurrencys))
            )
            .andExpect(status().isOk());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
        Currencys testCurrencys = currencysList.get(currencysList.size() - 1);
        assertThat(testCurrencys.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingCurrencys() throws Exception {
        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();
        currencys.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currencys.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencys))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrencys() throws Exception {
        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();
        currencys.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencys))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrencys() throws Exception {
        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();
        currencys.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencysMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencys)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCurrencysWithPatch() throws Exception {
        // Initialize the database
        currencysRepository.saveAndFlush(currencys);

        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();

        // Update the currencys using partial update
        Currencys partialUpdatedCurrencys = new Currencys();
        partialUpdatedCurrencys.setId(currencys.getId());

        restCurrencysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencys.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencys))
            )
            .andExpect(status().isOk());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
        Currencys testCurrencys = currencysList.get(currencysList.size() - 1);
        assertThat(testCurrencys.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdateCurrencysWithPatch() throws Exception {
        // Initialize the database
        currencysRepository.saveAndFlush(currencys);

        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();

        // Update the currencys using partial update
        Currencys partialUpdatedCurrencys = new Currencys();
        partialUpdatedCurrencys.setId(currencys.getId());

        partialUpdatedCurrencys.currency(UPDATED_CURRENCY);

        restCurrencysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencys.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencys))
            )
            .andExpect(status().isOk());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
        Currencys testCurrencys = currencysList.get(currencysList.size() - 1);
        assertThat(testCurrencys.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingCurrencys() throws Exception {
        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();
        currencys.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currencys.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencys))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrencys() throws Exception {
        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();
        currencys.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencys))
            )
            .andExpect(status().isBadRequest());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrencys() throws Exception {
        int databaseSizeBeforeUpdate = currencysRepository.findAll().size();
        currencys.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencysMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(currencys))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Currencys in the database
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurrencys() throws Exception {
        // Initialize the database
        currencysRepository.saveAndFlush(currencys);

        int databaseSizeBeforeDelete = currencysRepository.findAll().size();

        // Delete the currencys
        restCurrencysMockMvc
            .perform(delete(ENTITY_API_URL_ID, currencys.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currencys> currencysList = currencysRepository.findAll();
        assertThat(currencysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
