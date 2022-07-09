package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.CardTokenData;
import io.github.jhipster.sample.repository.CardTokenDataRepository;
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
 * Integration tests for the {@link CardTokenDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CardTokenDataResourceIT {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_EXPIRY_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_CARD_EXPIRY_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_EXPIRY_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_CARD_EXPIRY_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUER_RETURN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUER_RETURN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TRUNCATED_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRUNCATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/card-token-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CardTokenDataRepository cardTokenDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardTokenDataMockMvc;

    private CardTokenData cardTokenData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardTokenData createEntity(EntityManager em) {
        CardTokenData cardTokenData = new CardTokenData()
            .token(DEFAULT_TOKEN)
            .cardExpiryMonth(DEFAULT_CARD_EXPIRY_MONTH)
            .cardExpiryYear(DEFAULT_CARD_EXPIRY_YEAR)
            .issuerReturnCode(DEFAULT_ISSUER_RETURN_CODE)
            .truncatedCardNumber(DEFAULT_TRUNCATED_CARD_NUMBER);
        return cardTokenData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardTokenData createUpdatedEntity(EntityManager em) {
        CardTokenData cardTokenData = new CardTokenData()
            .token(UPDATED_TOKEN)
            .cardExpiryMonth(UPDATED_CARD_EXPIRY_MONTH)
            .cardExpiryYear(UPDATED_CARD_EXPIRY_YEAR)
            .issuerReturnCode(UPDATED_ISSUER_RETURN_CODE)
            .truncatedCardNumber(UPDATED_TRUNCATED_CARD_NUMBER);
        return cardTokenData;
    }

    @BeforeEach
    public void initTest() {
        cardTokenData = createEntity(em);
    }

    @Test
    @Transactional
    void createCardTokenData() throws Exception {
        int databaseSizeBeforeCreate = cardTokenDataRepository.findAll().size();
        // Create the CardTokenData
        restCardTokenDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardTokenData)))
            .andExpect(status().isCreated());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeCreate + 1);
        CardTokenData testCardTokenData = cardTokenDataList.get(cardTokenDataList.size() - 1);
        assertThat(testCardTokenData.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testCardTokenData.getCardExpiryMonth()).isEqualTo(DEFAULT_CARD_EXPIRY_MONTH);
        assertThat(testCardTokenData.getCardExpiryYear()).isEqualTo(DEFAULT_CARD_EXPIRY_YEAR);
        assertThat(testCardTokenData.getIssuerReturnCode()).isEqualTo(DEFAULT_ISSUER_RETURN_CODE);
        assertThat(testCardTokenData.getTruncatedCardNumber()).isEqualTo(DEFAULT_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void createCardTokenDataWithExistingId() throws Exception {
        // Create the CardTokenData with an existing ID
        cardTokenData.setId(1L);

        int databaseSizeBeforeCreate = cardTokenDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardTokenDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardTokenData)))
            .andExpect(status().isBadRequest());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCardTokenData() throws Exception {
        // Initialize the database
        cardTokenDataRepository.saveAndFlush(cardTokenData);

        // Get all the cardTokenDataList
        restCardTokenDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardTokenData.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].cardExpiryMonth").value(hasItem(DEFAULT_CARD_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].cardExpiryYear").value(hasItem(DEFAULT_CARD_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].issuerReturnCode").value(hasItem(DEFAULT_ISSUER_RETURN_CODE)))
            .andExpect(jsonPath("$.[*].truncatedCardNumber").value(hasItem(DEFAULT_TRUNCATED_CARD_NUMBER)));
    }

    @Test
    @Transactional
    void getCardTokenData() throws Exception {
        // Initialize the database
        cardTokenDataRepository.saveAndFlush(cardTokenData);

        // Get the cardTokenData
        restCardTokenDataMockMvc
            .perform(get(ENTITY_API_URL_ID, cardTokenData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cardTokenData.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.cardExpiryMonth").value(DEFAULT_CARD_EXPIRY_MONTH))
            .andExpect(jsonPath("$.cardExpiryYear").value(DEFAULT_CARD_EXPIRY_YEAR))
            .andExpect(jsonPath("$.issuerReturnCode").value(DEFAULT_ISSUER_RETURN_CODE))
            .andExpect(jsonPath("$.truncatedCardNumber").value(DEFAULT_TRUNCATED_CARD_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingCardTokenData() throws Exception {
        // Get the cardTokenData
        restCardTokenDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCardTokenData() throws Exception {
        // Initialize the database
        cardTokenDataRepository.saveAndFlush(cardTokenData);

        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();

        // Update the cardTokenData
        CardTokenData updatedCardTokenData = cardTokenDataRepository.findById(cardTokenData.getId()).get();
        // Disconnect from session so that the updates on updatedCardTokenData are not directly saved in db
        em.detach(updatedCardTokenData);
        updatedCardTokenData
            .token(UPDATED_TOKEN)
            .cardExpiryMonth(UPDATED_CARD_EXPIRY_MONTH)
            .cardExpiryYear(UPDATED_CARD_EXPIRY_YEAR)
            .issuerReturnCode(UPDATED_ISSUER_RETURN_CODE)
            .truncatedCardNumber(UPDATED_TRUNCATED_CARD_NUMBER);

        restCardTokenDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCardTokenData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCardTokenData))
            )
            .andExpect(status().isOk());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
        CardTokenData testCardTokenData = cardTokenDataList.get(cardTokenDataList.size() - 1);
        assertThat(testCardTokenData.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testCardTokenData.getCardExpiryMonth()).isEqualTo(UPDATED_CARD_EXPIRY_MONTH);
        assertThat(testCardTokenData.getCardExpiryYear()).isEqualTo(UPDATED_CARD_EXPIRY_YEAR);
        assertThat(testCardTokenData.getIssuerReturnCode()).isEqualTo(UPDATED_ISSUER_RETURN_CODE);
        assertThat(testCardTokenData.getTruncatedCardNumber()).isEqualTo(UPDATED_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingCardTokenData() throws Exception {
        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();
        cardTokenData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardTokenDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cardTokenData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cardTokenData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCardTokenData() throws Exception {
        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();
        cardTokenData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardTokenDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cardTokenData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCardTokenData() throws Exception {
        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();
        cardTokenData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardTokenDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardTokenData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCardTokenDataWithPatch() throws Exception {
        // Initialize the database
        cardTokenDataRepository.saveAndFlush(cardTokenData);

        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();

        // Update the cardTokenData using partial update
        CardTokenData partialUpdatedCardTokenData = new CardTokenData();
        partialUpdatedCardTokenData.setId(cardTokenData.getId());

        partialUpdatedCardTokenData
            .token(UPDATED_TOKEN)
            .cardExpiryMonth(UPDATED_CARD_EXPIRY_MONTH)
            .cardExpiryYear(UPDATED_CARD_EXPIRY_YEAR)
            .truncatedCardNumber(UPDATED_TRUNCATED_CARD_NUMBER);

        restCardTokenDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardTokenData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCardTokenData))
            )
            .andExpect(status().isOk());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
        CardTokenData testCardTokenData = cardTokenDataList.get(cardTokenDataList.size() - 1);
        assertThat(testCardTokenData.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testCardTokenData.getCardExpiryMonth()).isEqualTo(UPDATED_CARD_EXPIRY_MONTH);
        assertThat(testCardTokenData.getCardExpiryYear()).isEqualTo(UPDATED_CARD_EXPIRY_YEAR);
        assertThat(testCardTokenData.getIssuerReturnCode()).isEqualTo(DEFAULT_ISSUER_RETURN_CODE);
        assertThat(testCardTokenData.getTruncatedCardNumber()).isEqualTo(UPDATED_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateCardTokenDataWithPatch() throws Exception {
        // Initialize the database
        cardTokenDataRepository.saveAndFlush(cardTokenData);

        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();

        // Update the cardTokenData using partial update
        CardTokenData partialUpdatedCardTokenData = new CardTokenData();
        partialUpdatedCardTokenData.setId(cardTokenData.getId());

        partialUpdatedCardTokenData
            .token(UPDATED_TOKEN)
            .cardExpiryMonth(UPDATED_CARD_EXPIRY_MONTH)
            .cardExpiryYear(UPDATED_CARD_EXPIRY_YEAR)
            .issuerReturnCode(UPDATED_ISSUER_RETURN_CODE)
            .truncatedCardNumber(UPDATED_TRUNCATED_CARD_NUMBER);

        restCardTokenDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardTokenData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCardTokenData))
            )
            .andExpect(status().isOk());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
        CardTokenData testCardTokenData = cardTokenDataList.get(cardTokenDataList.size() - 1);
        assertThat(testCardTokenData.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testCardTokenData.getCardExpiryMonth()).isEqualTo(UPDATED_CARD_EXPIRY_MONTH);
        assertThat(testCardTokenData.getCardExpiryYear()).isEqualTo(UPDATED_CARD_EXPIRY_YEAR);
        assertThat(testCardTokenData.getIssuerReturnCode()).isEqualTo(UPDATED_ISSUER_RETURN_CODE);
        assertThat(testCardTokenData.getTruncatedCardNumber()).isEqualTo(UPDATED_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingCardTokenData() throws Exception {
        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();
        cardTokenData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardTokenDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cardTokenData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cardTokenData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCardTokenData() throws Exception {
        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();
        cardTokenData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardTokenDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cardTokenData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCardTokenData() throws Exception {
        int databaseSizeBeforeUpdate = cardTokenDataRepository.findAll().size();
        cardTokenData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardTokenDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cardTokenData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CardTokenData in the database
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCardTokenData() throws Exception {
        // Initialize the database
        cardTokenDataRepository.saveAndFlush(cardTokenData);

        int databaseSizeBeforeDelete = cardTokenDataRepository.findAll().size();

        // Delete the cardTokenData
        restCardTokenDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, cardTokenData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CardTokenData> cardTokenDataList = cardTokenDataRepository.findAll();
        assertThat(cardTokenDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
