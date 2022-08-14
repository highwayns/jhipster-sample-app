package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.BitcoinAddresses;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.BitcoinAddressesRepository;
import io.github.jhipster.sample.service.dto.BitcoinAddressesDTO;
import io.github.jhipster.sample.service.mapper.BitcoinAddressesMapper;
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
 * Integration tests for the {@link BitcoinAddressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BitcoinAddressesResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final YesNo DEFAULT_SYSTEM_ADDRESS = YesNo.Y;
    private static final YesNo UPDATED_SYSTEM_ADDRESS = YesNo.N;

    private static final YesNo DEFAULT_HOT_WALLET = YesNo.Y;
    private static final YesNo UPDATED_HOT_WALLET = YesNo.N;

    private static final YesNo DEFAULT_WARM_WALLET = YesNo.Y;
    private static final YesNo UPDATED_WARM_WALLET = YesNo.N;

    private static final String ENTITY_API_URL = "/api/bitcoin-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BitcoinAddressesRepository bitcoinAddressesRepository;

    @Autowired
    private BitcoinAddressesMapper bitcoinAddressesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBitcoinAddressesMockMvc;

    private BitcoinAddresses bitcoinAddresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BitcoinAddresses createEntity(EntityManager em) {
        BitcoinAddresses bitcoinAddresses = new BitcoinAddresses()
            .address(DEFAULT_ADDRESS)
            .date(DEFAULT_DATE)
            .systemAddress(DEFAULT_SYSTEM_ADDRESS)
            .hotWallet(DEFAULT_HOT_WALLET)
            .warmWallet(DEFAULT_WARM_WALLET);
        return bitcoinAddresses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BitcoinAddresses createUpdatedEntity(EntityManager em) {
        BitcoinAddresses bitcoinAddresses = new BitcoinAddresses()
            .address(UPDATED_ADDRESS)
            .date(UPDATED_DATE)
            .systemAddress(UPDATED_SYSTEM_ADDRESS)
            .hotWallet(UPDATED_HOT_WALLET)
            .warmWallet(UPDATED_WARM_WALLET);
        return bitcoinAddresses;
    }

    @BeforeEach
    public void initTest() {
        bitcoinAddresses = createEntity(em);
    }

    @Test
    @Transactional
    void createBitcoinAddresses() throws Exception {
        int databaseSizeBeforeCreate = bitcoinAddressesRepository.findAll().size();
        // Create the BitcoinAddresses
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);
        restBitcoinAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeCreate + 1);
        BitcoinAddresses testBitcoinAddresses = bitcoinAddressesList.get(bitcoinAddressesList.size() - 1);
        assertThat(testBitcoinAddresses.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBitcoinAddresses.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBitcoinAddresses.getSystemAddress()).isEqualTo(DEFAULT_SYSTEM_ADDRESS);
        assertThat(testBitcoinAddresses.getHotWallet()).isEqualTo(DEFAULT_HOT_WALLET);
        assertThat(testBitcoinAddresses.getWarmWallet()).isEqualTo(DEFAULT_WARM_WALLET);
    }

    @Test
    @Transactional
    void createBitcoinAddressesWithExistingId() throws Exception {
        // Create the BitcoinAddresses with an existing ID
        bitcoinAddresses.setId(1L);
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        int databaseSizeBeforeCreate = bitcoinAddressesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBitcoinAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoinAddressesRepository.findAll().size();
        // set the field null
        bitcoinAddresses.setAddress(null);

        // Create the BitcoinAddresses, which fails.
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        restBitcoinAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoinAddressesRepository.findAll().size();
        // set the field null
        bitcoinAddresses.setDate(null);

        // Create the BitcoinAddresses, which fails.
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        restBitcoinAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSystemAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoinAddressesRepository.findAll().size();
        // set the field null
        bitcoinAddresses.setSystemAddress(null);

        // Create the BitcoinAddresses, which fails.
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        restBitcoinAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHotWalletIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoinAddressesRepository.findAll().size();
        // set the field null
        bitcoinAddresses.setHotWallet(null);

        // Create the BitcoinAddresses, which fails.
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        restBitcoinAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWarmWalletIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitcoinAddressesRepository.findAll().size();
        // set the field null
        bitcoinAddresses.setWarmWallet(null);

        // Create the BitcoinAddresses, which fails.
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        restBitcoinAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBitcoinAddresses() throws Exception {
        // Initialize the database
        bitcoinAddressesRepository.saveAndFlush(bitcoinAddresses);

        // Get all the bitcoinAddressesList
        restBitcoinAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bitcoinAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].systemAddress").value(hasItem(DEFAULT_SYSTEM_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].hotWallet").value(hasItem(DEFAULT_HOT_WALLET.toString())))
            .andExpect(jsonPath("$.[*].warmWallet").value(hasItem(DEFAULT_WARM_WALLET.toString())));
    }

    @Test
    @Transactional
    void getBitcoinAddresses() throws Exception {
        // Initialize the database
        bitcoinAddressesRepository.saveAndFlush(bitcoinAddresses);

        // Get the bitcoinAddresses
        restBitcoinAddressesMockMvc
            .perform(get(ENTITY_API_URL_ID, bitcoinAddresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bitcoinAddresses.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.systemAddress").value(DEFAULT_SYSTEM_ADDRESS.toString()))
            .andExpect(jsonPath("$.hotWallet").value(DEFAULT_HOT_WALLET.toString()))
            .andExpect(jsonPath("$.warmWallet").value(DEFAULT_WARM_WALLET.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBitcoinAddresses() throws Exception {
        // Get the bitcoinAddresses
        restBitcoinAddressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBitcoinAddresses() throws Exception {
        // Initialize the database
        bitcoinAddressesRepository.saveAndFlush(bitcoinAddresses);

        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();

        // Update the bitcoinAddresses
        BitcoinAddresses updatedBitcoinAddresses = bitcoinAddressesRepository.findById(bitcoinAddresses.getId()).get();
        // Disconnect from session so that the updates on updatedBitcoinAddresses are not directly saved in db
        em.detach(updatedBitcoinAddresses);
        updatedBitcoinAddresses
            .address(UPDATED_ADDRESS)
            .date(UPDATED_DATE)
            .systemAddress(UPDATED_SYSTEM_ADDRESS)
            .hotWallet(UPDATED_HOT_WALLET)
            .warmWallet(UPDATED_WARM_WALLET);
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(updatedBitcoinAddresses);

        restBitcoinAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bitcoinAddressesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isOk());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
        BitcoinAddresses testBitcoinAddresses = bitcoinAddressesList.get(bitcoinAddressesList.size() - 1);
        assertThat(testBitcoinAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBitcoinAddresses.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBitcoinAddresses.getSystemAddress()).isEqualTo(UPDATED_SYSTEM_ADDRESS);
        assertThat(testBitcoinAddresses.getHotWallet()).isEqualTo(UPDATED_HOT_WALLET);
        assertThat(testBitcoinAddresses.getWarmWallet()).isEqualTo(UPDATED_WARM_WALLET);
    }

    @Test
    @Transactional
    void putNonExistingBitcoinAddresses() throws Exception {
        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();
        bitcoinAddresses.setId(count.incrementAndGet());

        // Create the BitcoinAddresses
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBitcoinAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bitcoinAddressesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBitcoinAddresses() throws Exception {
        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();
        bitcoinAddresses.setId(count.incrementAndGet());

        // Create the BitcoinAddresses
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoinAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBitcoinAddresses() throws Exception {
        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();
        bitcoinAddresses.setId(count.incrementAndGet());

        // Create the BitcoinAddresses
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoinAddressesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBitcoinAddressesWithPatch() throws Exception {
        // Initialize the database
        bitcoinAddressesRepository.saveAndFlush(bitcoinAddresses);

        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();

        // Update the bitcoinAddresses using partial update
        BitcoinAddresses partialUpdatedBitcoinAddresses = new BitcoinAddresses();
        partialUpdatedBitcoinAddresses.setId(bitcoinAddresses.getId());

        partialUpdatedBitcoinAddresses.address(UPDATED_ADDRESS).systemAddress(UPDATED_SYSTEM_ADDRESS);

        restBitcoinAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBitcoinAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBitcoinAddresses))
            )
            .andExpect(status().isOk());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
        BitcoinAddresses testBitcoinAddresses = bitcoinAddressesList.get(bitcoinAddressesList.size() - 1);
        assertThat(testBitcoinAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBitcoinAddresses.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBitcoinAddresses.getSystemAddress()).isEqualTo(UPDATED_SYSTEM_ADDRESS);
        assertThat(testBitcoinAddresses.getHotWallet()).isEqualTo(DEFAULT_HOT_WALLET);
        assertThat(testBitcoinAddresses.getWarmWallet()).isEqualTo(DEFAULT_WARM_WALLET);
    }

    @Test
    @Transactional
    void fullUpdateBitcoinAddressesWithPatch() throws Exception {
        // Initialize the database
        bitcoinAddressesRepository.saveAndFlush(bitcoinAddresses);

        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();

        // Update the bitcoinAddresses using partial update
        BitcoinAddresses partialUpdatedBitcoinAddresses = new BitcoinAddresses();
        partialUpdatedBitcoinAddresses.setId(bitcoinAddresses.getId());

        partialUpdatedBitcoinAddresses
            .address(UPDATED_ADDRESS)
            .date(UPDATED_DATE)
            .systemAddress(UPDATED_SYSTEM_ADDRESS)
            .hotWallet(UPDATED_HOT_WALLET)
            .warmWallet(UPDATED_WARM_WALLET);

        restBitcoinAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBitcoinAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBitcoinAddresses))
            )
            .andExpect(status().isOk());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
        BitcoinAddresses testBitcoinAddresses = bitcoinAddressesList.get(bitcoinAddressesList.size() - 1);
        assertThat(testBitcoinAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBitcoinAddresses.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBitcoinAddresses.getSystemAddress()).isEqualTo(UPDATED_SYSTEM_ADDRESS);
        assertThat(testBitcoinAddresses.getHotWallet()).isEqualTo(UPDATED_HOT_WALLET);
        assertThat(testBitcoinAddresses.getWarmWallet()).isEqualTo(UPDATED_WARM_WALLET);
    }

    @Test
    @Transactional
    void patchNonExistingBitcoinAddresses() throws Exception {
        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();
        bitcoinAddresses.setId(count.incrementAndGet());

        // Create the BitcoinAddresses
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBitcoinAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bitcoinAddressesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBitcoinAddresses() throws Exception {
        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();
        bitcoinAddresses.setId(count.incrementAndGet());

        // Create the BitcoinAddresses
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoinAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBitcoinAddresses() throws Exception {
        int databaseSizeBeforeUpdate = bitcoinAddressesRepository.findAll().size();
        bitcoinAddresses.setId(count.incrementAndGet());

        // Create the BitcoinAddresses
        BitcoinAddressesDTO bitcoinAddressesDTO = bitcoinAddressesMapper.toDto(bitcoinAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBitcoinAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bitcoinAddressesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BitcoinAddresses in the database
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBitcoinAddresses() throws Exception {
        // Initialize the database
        bitcoinAddressesRepository.saveAndFlush(bitcoinAddresses);

        int databaseSizeBeforeDelete = bitcoinAddressesRepository.findAll().size();

        // Delete the bitcoinAddresses
        restBitcoinAddressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, bitcoinAddresses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BitcoinAddresses> bitcoinAddressesList = bitcoinAddressesRepository.findAll();
        assertThat(bitcoinAddressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
