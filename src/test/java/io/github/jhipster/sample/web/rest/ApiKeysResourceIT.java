package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.ApiKeys;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.ApiKeysRepository;
import io.github.jhipster.sample.service.dto.ApiKeysDTO;
import io.github.jhipster.sample.service.mapper.ApiKeysMapper;
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
 * Integration tests for the {@link ApiKeysResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiKeysResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_SECRET = "BBBBBBBBBB";

    private static final YesNo DEFAULT_VIEW = YesNo.Y;
    private static final YesNo UPDATED_VIEW = YesNo.N;

    private static final YesNo DEFAULT_ORDERS = YesNo.Y;
    private static final YesNo UPDATED_ORDERS = YesNo.N;

    private static final YesNo DEFAULT_WITHDRAW = YesNo.Y;
    private static final YesNo UPDATED_WITHDRAW = YesNo.N;

    private static final Long DEFAULT_NONCE = 1L;
    private static final Long UPDATED_NONCE = 2L;

    private static final String ENTITY_API_URL = "/api/api-keys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApiKeysRepository apiKeysRepository;

    @Autowired
    private ApiKeysMapper apiKeysMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiKeysMockMvc;

    private ApiKeys apiKeys;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiKeys createEntity(EntityManager em) {
        ApiKeys apiKeys = new ApiKeys()
            .key(DEFAULT_KEY)
            .secret(DEFAULT_SECRET)
            .view(DEFAULT_VIEW)
            .orders(DEFAULT_ORDERS)
            .withdraw(DEFAULT_WITHDRAW)
            .nonce(DEFAULT_NONCE);
        return apiKeys;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiKeys createUpdatedEntity(EntityManager em) {
        ApiKeys apiKeys = new ApiKeys()
            .key(UPDATED_KEY)
            .secret(UPDATED_SECRET)
            .view(UPDATED_VIEW)
            .orders(UPDATED_ORDERS)
            .withdraw(UPDATED_WITHDRAW)
            .nonce(UPDATED_NONCE);
        return apiKeys;
    }

    @BeforeEach
    public void initTest() {
        apiKeys = createEntity(em);
    }

    @Test
    @Transactional
    void createApiKeys() throws Exception {
        int databaseSizeBeforeCreate = apiKeysRepository.findAll().size();
        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);
        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeCreate + 1);
        ApiKeys testApiKeys = apiKeysList.get(apiKeysList.size() - 1);
        assertThat(testApiKeys.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testApiKeys.getSecret()).isEqualTo(DEFAULT_SECRET);
        assertThat(testApiKeys.getView()).isEqualTo(DEFAULT_VIEW);
        assertThat(testApiKeys.getOrders()).isEqualTo(DEFAULT_ORDERS);
        assertThat(testApiKeys.getWithdraw()).isEqualTo(DEFAULT_WITHDRAW);
        assertThat(testApiKeys.getNonce()).isEqualTo(DEFAULT_NONCE);
    }

    @Test
    @Transactional
    void createApiKeysWithExistingId() throws Exception {
        // Create the ApiKeys with an existing ID
        apiKeys.setId(1L);
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        int databaseSizeBeforeCreate = apiKeysRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setKey(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSecretIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setSecret(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkViewIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setView(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrdersIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setOrders(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWithdrawIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setWithdraw(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNonceIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiKeysRepository.findAll().size();
        // set the field null
        apiKeys.setNonce(null);

        // Create the ApiKeys, which fails.
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        restApiKeysMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isBadRequest());

        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        // Get all the apiKeysList
        restApiKeysMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiKeys.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET)))
            .andExpect(jsonPath("$.[*].view").value(hasItem(DEFAULT_VIEW.toString())))
            .andExpect(jsonPath("$.[*].orders").value(hasItem(DEFAULT_ORDERS.toString())))
            .andExpect(jsonPath("$.[*].withdraw").value(hasItem(DEFAULT_WITHDRAW.toString())))
            .andExpect(jsonPath("$.[*].nonce").value(hasItem(DEFAULT_NONCE.intValue())));
    }

    @Test
    @Transactional
    void getApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        // Get the apiKeys
        restApiKeysMockMvc
            .perform(get(ENTITY_API_URL_ID, apiKeys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiKeys.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.secret").value(DEFAULT_SECRET))
            .andExpect(jsonPath("$.view").value(DEFAULT_VIEW.toString()))
            .andExpect(jsonPath("$.orders").value(DEFAULT_ORDERS.toString()))
            .andExpect(jsonPath("$.withdraw").value(DEFAULT_WITHDRAW.toString()))
            .andExpect(jsonPath("$.nonce").value(DEFAULT_NONCE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingApiKeys() throws Exception {
        // Get the apiKeys
        restApiKeysMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();

        // Update the apiKeys
        ApiKeys updatedApiKeys = apiKeysRepository.findById(apiKeys.getId()).get();
        // Disconnect from session so that the updates on updatedApiKeys are not directly saved in db
        em.detach(updatedApiKeys);
        updatedApiKeys
            .key(UPDATED_KEY)
            .secret(UPDATED_SECRET)
            .view(UPDATED_VIEW)
            .orders(UPDATED_ORDERS)
            .withdraw(UPDATED_WITHDRAW)
            .nonce(UPDATED_NONCE);
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(updatedApiKeys);

        restApiKeysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiKeysDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
        ApiKeys testApiKeys = apiKeysList.get(apiKeysList.size() - 1);
        assertThat(testApiKeys.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testApiKeys.getSecret()).isEqualTo(UPDATED_SECRET);
        assertThat(testApiKeys.getView()).isEqualTo(UPDATED_VIEW);
        assertThat(testApiKeys.getOrders()).isEqualTo(UPDATED_ORDERS);
        assertThat(testApiKeys.getWithdraw()).isEqualTo(UPDATED_WITHDRAW);
        assertThat(testApiKeys.getNonce()).isEqualTo(UPDATED_NONCE);
    }

    @Test
    @Transactional
    void putNonExistingApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();
        apiKeys.setId(count.incrementAndGet());

        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiKeysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiKeysDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();
        apiKeys.setId(count.incrementAndGet());

        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiKeysMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();
        apiKeys.setId(count.incrementAndGet());

        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiKeysMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiKeysDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiKeysWithPatch() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();

        // Update the apiKeys using partial update
        ApiKeys partialUpdatedApiKeys = new ApiKeys();
        partialUpdatedApiKeys.setId(apiKeys.getId());

        partialUpdatedApiKeys.key(UPDATED_KEY).orders(UPDATED_ORDERS);

        restApiKeysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiKeys.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiKeys))
            )
            .andExpect(status().isOk());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
        ApiKeys testApiKeys = apiKeysList.get(apiKeysList.size() - 1);
        assertThat(testApiKeys.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testApiKeys.getSecret()).isEqualTo(DEFAULT_SECRET);
        assertThat(testApiKeys.getView()).isEqualTo(DEFAULT_VIEW);
        assertThat(testApiKeys.getOrders()).isEqualTo(UPDATED_ORDERS);
        assertThat(testApiKeys.getWithdraw()).isEqualTo(DEFAULT_WITHDRAW);
        assertThat(testApiKeys.getNonce()).isEqualTo(DEFAULT_NONCE);
    }

    @Test
    @Transactional
    void fullUpdateApiKeysWithPatch() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();

        // Update the apiKeys using partial update
        ApiKeys partialUpdatedApiKeys = new ApiKeys();
        partialUpdatedApiKeys.setId(apiKeys.getId());

        partialUpdatedApiKeys
            .key(UPDATED_KEY)
            .secret(UPDATED_SECRET)
            .view(UPDATED_VIEW)
            .orders(UPDATED_ORDERS)
            .withdraw(UPDATED_WITHDRAW)
            .nonce(UPDATED_NONCE);

        restApiKeysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiKeys.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiKeys))
            )
            .andExpect(status().isOk());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
        ApiKeys testApiKeys = apiKeysList.get(apiKeysList.size() - 1);
        assertThat(testApiKeys.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testApiKeys.getSecret()).isEqualTo(UPDATED_SECRET);
        assertThat(testApiKeys.getView()).isEqualTo(UPDATED_VIEW);
        assertThat(testApiKeys.getOrders()).isEqualTo(UPDATED_ORDERS);
        assertThat(testApiKeys.getWithdraw()).isEqualTo(UPDATED_WITHDRAW);
        assertThat(testApiKeys.getNonce()).isEqualTo(UPDATED_NONCE);
    }

    @Test
    @Transactional
    void patchNonExistingApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();
        apiKeys.setId(count.incrementAndGet());

        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiKeysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiKeysDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();
        apiKeys.setId(count.incrementAndGet());

        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiKeysMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiKeysDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiKeys() throws Exception {
        int databaseSizeBeforeUpdate = apiKeysRepository.findAll().size();
        apiKeys.setId(count.incrementAndGet());

        // Create the ApiKeys
        ApiKeysDTO apiKeysDTO = apiKeysMapper.toDto(apiKeys);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiKeysMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apiKeysDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiKeys in the database
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiKeys() throws Exception {
        // Initialize the database
        apiKeysRepository.saveAndFlush(apiKeys);

        int databaseSizeBeforeDelete = apiKeysRepository.findAll().size();

        // Delete the apiKeys
        restApiKeysMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiKeys.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiKeys> apiKeysList = apiKeysRepository.findAll();
        assertThat(apiKeysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
