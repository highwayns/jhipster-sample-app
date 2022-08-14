package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.ChangeSettings;
import io.github.jhipster.sample.repository.ChangeSettingsRepository;
import io.github.jhipster.sample.service.dto.ChangeSettingsDTO;
import io.github.jhipster.sample.service.mapper.ChangeSettingsMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ChangeSettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChangeSettingsResourceIT {

    private static final byte[] DEFAULT_REQUEST = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REQUEST = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REQUEST_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REQUEST_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/change-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChangeSettingsRepository changeSettingsRepository;

    @Autowired
    private ChangeSettingsMapper changeSettingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChangeSettingsMockMvc;

    private ChangeSettings changeSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChangeSettings createEntity(EntityManager em) {
        ChangeSettings changeSettings = new ChangeSettings()
            .request(DEFAULT_REQUEST)
            .requestContentType(DEFAULT_REQUEST_CONTENT_TYPE)
            .date(DEFAULT_DATE);
        return changeSettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChangeSettings createUpdatedEntity(EntityManager em) {
        ChangeSettings changeSettings = new ChangeSettings()
            .request(UPDATED_REQUEST)
            .requestContentType(UPDATED_REQUEST_CONTENT_TYPE)
            .date(UPDATED_DATE);
        return changeSettings;
    }

    @BeforeEach
    public void initTest() {
        changeSettings = createEntity(em);
    }

    @Test
    @Transactional
    void createChangeSettings() throws Exception {
        int databaseSizeBeforeCreate = changeSettingsRepository.findAll().size();
        // Create the ChangeSettings
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);
        restChangeSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        ChangeSettings testChangeSettings = changeSettingsList.get(changeSettingsList.size() - 1);
        assertThat(testChangeSettings.getRequest()).isEqualTo(DEFAULT_REQUEST);
        assertThat(testChangeSettings.getRequestContentType()).isEqualTo(DEFAULT_REQUEST_CONTENT_TYPE);
        assertThat(testChangeSettings.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createChangeSettingsWithExistingId() throws Exception {
        // Create the ChangeSettings with an existing ID
        changeSettings.setId(1L);
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        int databaseSizeBeforeCreate = changeSettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChangeSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = changeSettingsRepository.findAll().size();
        // set the field null
        changeSettings.setDate(null);

        // Create the ChangeSettings, which fails.
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        restChangeSettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChangeSettings() throws Exception {
        // Initialize the database
        changeSettingsRepository.saveAndFlush(changeSettings);

        // Get all the changeSettingsList
        restChangeSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(changeSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestContentType").value(hasItem(DEFAULT_REQUEST_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].request").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUEST))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getChangeSettings() throws Exception {
        // Initialize the database
        changeSettingsRepository.saveAndFlush(changeSettings);

        // Get the changeSettings
        restChangeSettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, changeSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(changeSettings.getId().intValue()))
            .andExpect(jsonPath("$.requestContentType").value(DEFAULT_REQUEST_CONTENT_TYPE))
            .andExpect(jsonPath("$.request").value(Base64Utils.encodeToString(DEFAULT_REQUEST)))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChangeSettings() throws Exception {
        // Get the changeSettings
        restChangeSettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChangeSettings() throws Exception {
        // Initialize the database
        changeSettingsRepository.saveAndFlush(changeSettings);

        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();

        // Update the changeSettings
        ChangeSettings updatedChangeSettings = changeSettingsRepository.findById(changeSettings.getId()).get();
        // Disconnect from session so that the updates on updatedChangeSettings are not directly saved in db
        em.detach(updatedChangeSettings);
        updatedChangeSettings.request(UPDATED_REQUEST).requestContentType(UPDATED_REQUEST_CONTENT_TYPE).date(UPDATED_DATE);
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(updatedChangeSettings);

        restChangeSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, changeSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
        ChangeSettings testChangeSettings = changeSettingsList.get(changeSettingsList.size() - 1);
        assertThat(testChangeSettings.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testChangeSettings.getRequestContentType()).isEqualTo(UPDATED_REQUEST_CONTENT_TYPE);
        assertThat(testChangeSettings.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingChangeSettings() throws Exception {
        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();
        changeSettings.setId(count.incrementAndGet());

        // Create the ChangeSettings
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangeSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, changeSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChangeSettings() throws Exception {
        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();
        changeSettings.setId(count.incrementAndGet());

        // Create the ChangeSettings
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChangeSettings() throws Exception {
        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();
        changeSettings.setId(count.incrementAndGet());

        // Create the ChangeSettings
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeSettingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChangeSettingsWithPatch() throws Exception {
        // Initialize the database
        changeSettingsRepository.saveAndFlush(changeSettings);

        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();

        // Update the changeSettings using partial update
        ChangeSettings partialUpdatedChangeSettings = new ChangeSettings();
        partialUpdatedChangeSettings.setId(changeSettings.getId());

        partialUpdatedChangeSettings.request(UPDATED_REQUEST).requestContentType(UPDATED_REQUEST_CONTENT_TYPE);

        restChangeSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangeSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChangeSettings))
            )
            .andExpect(status().isOk());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
        ChangeSettings testChangeSettings = changeSettingsList.get(changeSettingsList.size() - 1);
        assertThat(testChangeSettings.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testChangeSettings.getRequestContentType()).isEqualTo(UPDATED_REQUEST_CONTENT_TYPE);
        assertThat(testChangeSettings.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateChangeSettingsWithPatch() throws Exception {
        // Initialize the database
        changeSettingsRepository.saveAndFlush(changeSettings);

        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();

        // Update the changeSettings using partial update
        ChangeSettings partialUpdatedChangeSettings = new ChangeSettings();
        partialUpdatedChangeSettings.setId(changeSettings.getId());

        partialUpdatedChangeSettings.request(UPDATED_REQUEST).requestContentType(UPDATED_REQUEST_CONTENT_TYPE).date(UPDATED_DATE);

        restChangeSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangeSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChangeSettings))
            )
            .andExpect(status().isOk());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
        ChangeSettings testChangeSettings = changeSettingsList.get(changeSettingsList.size() - 1);
        assertThat(testChangeSettings.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testChangeSettings.getRequestContentType()).isEqualTo(UPDATED_REQUEST_CONTENT_TYPE);
        assertThat(testChangeSettings.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingChangeSettings() throws Exception {
        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();
        changeSettings.setId(count.incrementAndGet());

        // Create the ChangeSettings
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangeSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, changeSettingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChangeSettings() throws Exception {
        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();
        changeSettings.setId(count.incrementAndGet());

        // Create the ChangeSettings
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChangeSettings() throws Exception {
        int databaseSizeBeforeUpdate = changeSettingsRepository.findAll().size();
        changeSettings.setId(count.incrementAndGet());

        // Create the ChangeSettings
        ChangeSettingsDTO changeSettingsDTO = changeSettingsMapper.toDto(changeSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChangeSettings in the database
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChangeSettings() throws Exception {
        // Initialize the database
        changeSettingsRepository.saveAndFlush(changeSettings);

        int databaseSizeBeforeDelete = changeSettingsRepository.findAll().size();

        // Delete the changeSettings
        restChangeSettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, changeSettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChangeSettings> changeSettingsList = changeSettingsRepository.findAll();
        assertThat(changeSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
