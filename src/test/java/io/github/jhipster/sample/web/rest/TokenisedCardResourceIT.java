package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.TokenisedCard;
import io.github.jhipster.sample.repository.TokenisedCardRepository;
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
 * Integration tests for the {@link TokenisedCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TokenisedCardResourceIT {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_EXPIRY_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_CARD_EXPIRY_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_EXPIRY_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_CARD_EXPIRY_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_TRUNCATED_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRUNCATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tokenised-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TokenisedCardRepository tokenisedCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTokenisedCardMockMvc;

    private TokenisedCard tokenisedCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TokenisedCard createEntity(EntityManager em) {
        TokenisedCard tokenisedCard = new TokenisedCard()
            .token(DEFAULT_TOKEN)
            .cardExpiryMonth(DEFAULT_CARD_EXPIRY_MONTH)
            .cardExpiryYear(DEFAULT_CARD_EXPIRY_YEAR)
            .truncatedCardNumber(DEFAULT_TRUNCATED_CARD_NUMBER);
        return tokenisedCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TokenisedCard createUpdatedEntity(EntityManager em) {
        TokenisedCard tokenisedCard = new TokenisedCard()
            .token(UPDATED_TOKEN)
            .cardExpiryMonth(UPDATED_CARD_EXPIRY_MONTH)
            .cardExpiryYear(UPDATED_CARD_EXPIRY_YEAR)
            .truncatedCardNumber(UPDATED_TRUNCATED_CARD_NUMBER);
        return tokenisedCard;
    }

    @BeforeEach
    public void initTest() {
        tokenisedCard = createEntity(em);
    }

    @Test
    @Transactional
    void createTokenisedCard() throws Exception {
        int databaseSizeBeforeCreate = tokenisedCardRepository.findAll().size();
        // Create the TokenisedCard
        restTokenisedCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tokenisedCard)))
            .andExpect(status().isCreated());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeCreate + 1);
        TokenisedCard testTokenisedCard = tokenisedCardList.get(tokenisedCardList.size() - 1);
        assertThat(testTokenisedCard.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testTokenisedCard.getCardExpiryMonth()).isEqualTo(DEFAULT_CARD_EXPIRY_MONTH);
        assertThat(testTokenisedCard.getCardExpiryYear()).isEqualTo(DEFAULT_CARD_EXPIRY_YEAR);
        assertThat(testTokenisedCard.getTruncatedCardNumber()).isEqualTo(DEFAULT_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void createTokenisedCardWithExistingId() throws Exception {
        // Create the TokenisedCard with an existing ID
        tokenisedCard.setId(1L);

        int databaseSizeBeforeCreate = tokenisedCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTokenisedCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tokenisedCard)))
            .andExpect(status().isBadRequest());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTokenisedCards() throws Exception {
        // Initialize the database
        tokenisedCardRepository.saveAndFlush(tokenisedCard);

        // Get all the tokenisedCardList
        restTokenisedCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tokenisedCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].cardExpiryMonth").value(hasItem(DEFAULT_CARD_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].cardExpiryYear").value(hasItem(DEFAULT_CARD_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].truncatedCardNumber").value(hasItem(DEFAULT_TRUNCATED_CARD_NUMBER)));
    }

    @Test
    @Transactional
    void getTokenisedCard() throws Exception {
        // Initialize the database
        tokenisedCardRepository.saveAndFlush(tokenisedCard);

        // Get the tokenisedCard
        restTokenisedCardMockMvc
            .perform(get(ENTITY_API_URL_ID, tokenisedCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tokenisedCard.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.cardExpiryMonth").value(DEFAULT_CARD_EXPIRY_MONTH))
            .andExpect(jsonPath("$.cardExpiryYear").value(DEFAULT_CARD_EXPIRY_YEAR))
            .andExpect(jsonPath("$.truncatedCardNumber").value(DEFAULT_TRUNCATED_CARD_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingTokenisedCard() throws Exception {
        // Get the tokenisedCard
        restTokenisedCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTokenisedCard() throws Exception {
        // Initialize the database
        tokenisedCardRepository.saveAndFlush(tokenisedCard);

        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();

        // Update the tokenisedCard
        TokenisedCard updatedTokenisedCard = tokenisedCardRepository.findById(tokenisedCard.getId()).get();
        // Disconnect from session so that the updates on updatedTokenisedCard are not directly saved in db
        em.detach(updatedTokenisedCard);
        updatedTokenisedCard
            .token(UPDATED_TOKEN)
            .cardExpiryMonth(UPDATED_CARD_EXPIRY_MONTH)
            .cardExpiryYear(UPDATED_CARD_EXPIRY_YEAR)
            .truncatedCardNumber(UPDATED_TRUNCATED_CARD_NUMBER);

        restTokenisedCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTokenisedCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTokenisedCard))
            )
            .andExpect(status().isOk());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
        TokenisedCard testTokenisedCard = tokenisedCardList.get(tokenisedCardList.size() - 1);
        assertThat(testTokenisedCard.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testTokenisedCard.getCardExpiryMonth()).isEqualTo(UPDATED_CARD_EXPIRY_MONTH);
        assertThat(testTokenisedCard.getCardExpiryYear()).isEqualTo(UPDATED_CARD_EXPIRY_YEAR);
        assertThat(testTokenisedCard.getTruncatedCardNumber()).isEqualTo(UPDATED_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingTokenisedCard() throws Exception {
        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();
        tokenisedCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenisedCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tokenisedCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tokenisedCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTokenisedCard() throws Exception {
        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();
        tokenisedCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenisedCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tokenisedCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTokenisedCard() throws Exception {
        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();
        tokenisedCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenisedCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tokenisedCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTokenisedCardWithPatch() throws Exception {
        // Initialize the database
        tokenisedCardRepository.saveAndFlush(tokenisedCard);

        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();

        // Update the tokenisedCard using partial update
        TokenisedCard partialUpdatedTokenisedCard = new TokenisedCard();
        partialUpdatedTokenisedCard.setId(tokenisedCard.getId());

        restTokenisedCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTokenisedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTokenisedCard))
            )
            .andExpect(status().isOk());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
        TokenisedCard testTokenisedCard = tokenisedCardList.get(tokenisedCardList.size() - 1);
        assertThat(testTokenisedCard.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testTokenisedCard.getCardExpiryMonth()).isEqualTo(DEFAULT_CARD_EXPIRY_MONTH);
        assertThat(testTokenisedCard.getCardExpiryYear()).isEqualTo(DEFAULT_CARD_EXPIRY_YEAR);
        assertThat(testTokenisedCard.getTruncatedCardNumber()).isEqualTo(DEFAULT_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateTokenisedCardWithPatch() throws Exception {
        // Initialize the database
        tokenisedCardRepository.saveAndFlush(tokenisedCard);

        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();

        // Update the tokenisedCard using partial update
        TokenisedCard partialUpdatedTokenisedCard = new TokenisedCard();
        partialUpdatedTokenisedCard.setId(tokenisedCard.getId());

        partialUpdatedTokenisedCard
            .token(UPDATED_TOKEN)
            .cardExpiryMonth(UPDATED_CARD_EXPIRY_MONTH)
            .cardExpiryYear(UPDATED_CARD_EXPIRY_YEAR)
            .truncatedCardNumber(UPDATED_TRUNCATED_CARD_NUMBER);

        restTokenisedCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTokenisedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTokenisedCard))
            )
            .andExpect(status().isOk());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
        TokenisedCard testTokenisedCard = tokenisedCardList.get(tokenisedCardList.size() - 1);
        assertThat(testTokenisedCard.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testTokenisedCard.getCardExpiryMonth()).isEqualTo(UPDATED_CARD_EXPIRY_MONTH);
        assertThat(testTokenisedCard.getCardExpiryYear()).isEqualTo(UPDATED_CARD_EXPIRY_YEAR);
        assertThat(testTokenisedCard.getTruncatedCardNumber()).isEqualTo(UPDATED_TRUNCATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingTokenisedCard() throws Exception {
        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();
        tokenisedCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenisedCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tokenisedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tokenisedCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTokenisedCard() throws Exception {
        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();
        tokenisedCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenisedCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tokenisedCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTokenisedCard() throws Exception {
        int databaseSizeBeforeUpdate = tokenisedCardRepository.findAll().size();
        tokenisedCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenisedCardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tokenisedCard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TokenisedCard in the database
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTokenisedCard() throws Exception {
        // Initialize the database
        tokenisedCardRepository.saveAndFlush(tokenisedCard);

        int databaseSizeBeforeDelete = tokenisedCardRepository.findAll().size();

        // Delete the tokenisedCard
        restTokenisedCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, tokenisedCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TokenisedCard> tokenisedCardList = tokenisedCardRepository.findAll();
        assertThat(tokenisedCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
