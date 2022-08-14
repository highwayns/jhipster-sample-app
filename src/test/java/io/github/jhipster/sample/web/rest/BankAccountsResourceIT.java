package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.BankAccounts;
import io.github.jhipster.sample.repository.BankAccountsRepository;
import io.github.jhipster.sample.service.dto.BankAccountsDTO;
import io.github.jhipster.sample.service.mapper.BankAccountsMapper;
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
 * Integration tests for the {@link BankAccountsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankAccountsResourceIT {

    private static final Long DEFAULT_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_ACCOUNT_NUMBER = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankAccountsRepository bankAccountsRepository;

    @Autowired
    private BankAccountsMapper bankAccountsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankAccountsMockMvc;

    private BankAccounts bankAccounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccounts createEntity(EntityManager em) {
        BankAccounts bankAccounts = new BankAccounts().accountNumber(DEFAULT_ACCOUNT_NUMBER).description(DEFAULT_DESCRIPTION);
        return bankAccounts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccounts createUpdatedEntity(EntityManager em) {
        BankAccounts bankAccounts = new BankAccounts().accountNumber(UPDATED_ACCOUNT_NUMBER).description(UPDATED_DESCRIPTION);
        return bankAccounts;
    }

    @BeforeEach
    public void initTest() {
        bankAccounts = createEntity(em);
    }

    @Test
    @Transactional
    void createBankAccounts() throws Exception {
        int databaseSizeBeforeCreate = bankAccountsRepository.findAll().size();
        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);
        restBankAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeCreate + 1);
        BankAccounts testBankAccounts = bankAccountsList.get(bankAccountsList.size() - 1);
        assertThat(testBankAccounts.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankAccounts.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createBankAccountsWithExistingId() throws Exception {
        // Create the BankAccounts with an existing ID
        bankAccounts.setId(1L);
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        int databaseSizeBeforeCreate = bankAccountsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountsRepository.findAll().size();
        // set the field null
        bankAccounts.setAccountNumber(null);

        // Create the BankAccounts, which fails.
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        restBankAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountsRepository.findAll().size();
        // set the field null
        bankAccounts.setDescription(null);

        // Create the BankAccounts, which fails.
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        restBankAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList
        restBankAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get the bankAccounts
        restBankAccountsMockMvc
            .perform(get(ENTITY_API_URL_ID, bankAccounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccounts.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingBankAccounts() throws Exception {
        // Get the bankAccounts
        restBankAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();

        // Update the bankAccounts
        BankAccounts updatedBankAccounts = bankAccountsRepository.findById(bankAccounts.getId()).get();
        // Disconnect from session so that the updates on updatedBankAccounts are not directly saved in db
        em.detach(updatedBankAccounts);
        updatedBankAccounts.accountNumber(UPDATED_ACCOUNT_NUMBER).description(UPDATED_DESCRIPTION);
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(updatedBankAccounts);

        restBankAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankAccountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
        BankAccounts testBankAccounts = bankAccountsList.get(bankAccountsList.size() - 1);
        assertThat(testBankAccounts.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccounts.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingBankAccounts() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();
        bankAccounts.setId(count.incrementAndGet());

        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankAccountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankAccounts() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();
        bankAccounts.setId(count.incrementAndGet());

        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankAccounts() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();
        bankAccounts.setId(count.incrementAndGet());

        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankAccountsWithPatch() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();

        // Update the bankAccounts using partial update
        BankAccounts partialUpdatedBankAccounts = new BankAccounts();
        partialUpdatedBankAccounts.setId(bankAccounts.getId());

        partialUpdatedBankAccounts.accountNumber(UPDATED_ACCOUNT_NUMBER);

        restBankAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccounts))
            )
            .andExpect(status().isOk());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
        BankAccounts testBankAccounts = bankAccountsList.get(bankAccountsList.size() - 1);
        assertThat(testBankAccounts.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccounts.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateBankAccountsWithPatch() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();

        // Update the bankAccounts using partial update
        BankAccounts partialUpdatedBankAccounts = new BankAccounts();
        partialUpdatedBankAccounts.setId(bankAccounts.getId());

        partialUpdatedBankAccounts.accountNumber(UPDATED_ACCOUNT_NUMBER).description(UPDATED_DESCRIPTION);

        restBankAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccounts))
            )
            .andExpect(status().isOk());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
        BankAccounts testBankAccounts = bankAccountsList.get(bankAccountsList.size() - 1);
        assertThat(testBankAccounts.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccounts.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingBankAccounts() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();
        bankAccounts.setId(count.incrementAndGet());

        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankAccountsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankAccounts() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();
        bankAccounts.setId(count.incrementAndGet());

        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankAccounts() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();
        bankAccounts.setId(count.incrementAndGet());

        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        int databaseSizeBeforeDelete = bankAccountsRepository.findAll().size();

        // Delete the bankAccounts
        restBankAccountsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankAccounts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
