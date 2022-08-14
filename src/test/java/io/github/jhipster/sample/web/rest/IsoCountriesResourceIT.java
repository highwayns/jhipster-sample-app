package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.IsoCountries;
import io.github.jhipster.sample.repository.IsoCountriesRepository;
import io.github.jhipster.sample.service.dto.IsoCountriesDTO;
import io.github.jhipster.sample.service.mapper.IsoCountriesMapper;
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
 * Integration tests for the {@link IsoCountriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IsoCountriesResourceIT {

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/iso-countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IsoCountriesRepository isoCountriesRepository;

    @Autowired
    private IsoCountriesMapper isoCountriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIsoCountriesMockMvc;

    private IsoCountries isoCountries;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsoCountries createEntity(EntityManager em) {
        IsoCountries isoCountries = new IsoCountries().locale(DEFAULT_LOCALE).code(DEFAULT_CODE).name(DEFAULT_NAME).prefix(DEFAULT_PREFIX);
        return isoCountries;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsoCountries createUpdatedEntity(EntityManager em) {
        IsoCountries isoCountries = new IsoCountries().locale(UPDATED_LOCALE).code(UPDATED_CODE).name(UPDATED_NAME).prefix(UPDATED_PREFIX);
        return isoCountries;
    }

    @BeforeEach
    public void initTest() {
        isoCountries = createEntity(em);
    }

    @Test
    @Transactional
    void createIsoCountries() throws Exception {
        int databaseSizeBeforeCreate = isoCountriesRepository.findAll().size();
        // Create the IsoCountries
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);
        restIsoCountriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeCreate + 1);
        IsoCountries testIsoCountries = isoCountriesList.get(isoCountriesList.size() - 1);
        assertThat(testIsoCountries.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testIsoCountries.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIsoCountries.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIsoCountries.getPrefix()).isEqualTo(DEFAULT_PREFIX);
    }

    @Test
    @Transactional
    void createIsoCountriesWithExistingId() throws Exception {
        // Create the IsoCountries with an existing ID
        isoCountries.setId(1L);
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        int databaseSizeBeforeCreate = isoCountriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsoCountriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = isoCountriesRepository.findAll().size();
        // set the field null
        isoCountries.setLocale(null);

        // Create the IsoCountries, which fails.
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        restIsoCountriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = isoCountriesRepository.findAll().size();
        // set the field null
        isoCountries.setCode(null);

        // Create the IsoCountries, which fails.
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        restIsoCountriesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIsoCountries() throws Exception {
        // Initialize the database
        isoCountriesRepository.saveAndFlush(isoCountries);

        // Get all the isoCountriesList
        restIsoCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isoCountries.getId().intValue())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)));
    }

    @Test
    @Transactional
    void getIsoCountries() throws Exception {
        // Initialize the database
        isoCountriesRepository.saveAndFlush(isoCountries);

        // Get the isoCountries
        restIsoCountriesMockMvc
            .perform(get(ENTITY_API_URL_ID, isoCountries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(isoCountries.getId().intValue()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX));
    }

    @Test
    @Transactional
    void getNonExistingIsoCountries() throws Exception {
        // Get the isoCountries
        restIsoCountriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIsoCountries() throws Exception {
        // Initialize the database
        isoCountriesRepository.saveAndFlush(isoCountries);

        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();

        // Update the isoCountries
        IsoCountries updatedIsoCountries = isoCountriesRepository.findById(isoCountries.getId()).get();
        // Disconnect from session so that the updates on updatedIsoCountries are not directly saved in db
        em.detach(updatedIsoCountries);
        updatedIsoCountries.locale(UPDATED_LOCALE).code(UPDATED_CODE).name(UPDATED_NAME).prefix(UPDATED_PREFIX);
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(updatedIsoCountries);

        restIsoCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isoCountriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
        IsoCountries testIsoCountries = isoCountriesList.get(isoCountriesList.size() - 1);
        assertThat(testIsoCountries.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testIsoCountries.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIsoCountries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIsoCountries.getPrefix()).isEqualTo(UPDATED_PREFIX);
    }

    @Test
    @Transactional
    void putNonExistingIsoCountries() throws Exception {
        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();
        isoCountries.setId(count.incrementAndGet());

        // Create the IsoCountries
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsoCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, isoCountriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIsoCountries() throws Exception {
        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();
        isoCountries.setId(count.incrementAndGet());

        // Create the IsoCountries
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsoCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIsoCountries() throws Exception {
        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();
        isoCountries.setId(count.incrementAndGet());

        // Create the IsoCountries
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsoCountriesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIsoCountriesWithPatch() throws Exception {
        // Initialize the database
        isoCountriesRepository.saveAndFlush(isoCountries);

        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();

        // Update the isoCountries using partial update
        IsoCountries partialUpdatedIsoCountries = new IsoCountries();
        partialUpdatedIsoCountries.setId(isoCountries.getId());

        partialUpdatedIsoCountries.locale(UPDATED_LOCALE);

        restIsoCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsoCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsoCountries))
            )
            .andExpect(status().isOk());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
        IsoCountries testIsoCountries = isoCountriesList.get(isoCountriesList.size() - 1);
        assertThat(testIsoCountries.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testIsoCountries.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIsoCountries.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIsoCountries.getPrefix()).isEqualTo(DEFAULT_PREFIX);
    }

    @Test
    @Transactional
    void fullUpdateIsoCountriesWithPatch() throws Exception {
        // Initialize the database
        isoCountriesRepository.saveAndFlush(isoCountries);

        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();

        // Update the isoCountries using partial update
        IsoCountries partialUpdatedIsoCountries = new IsoCountries();
        partialUpdatedIsoCountries.setId(isoCountries.getId());

        partialUpdatedIsoCountries.locale(UPDATED_LOCALE).code(UPDATED_CODE).name(UPDATED_NAME).prefix(UPDATED_PREFIX);

        restIsoCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIsoCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIsoCountries))
            )
            .andExpect(status().isOk());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
        IsoCountries testIsoCountries = isoCountriesList.get(isoCountriesList.size() - 1);
        assertThat(testIsoCountries.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testIsoCountries.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIsoCountries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIsoCountries.getPrefix()).isEqualTo(UPDATED_PREFIX);
    }

    @Test
    @Transactional
    void patchNonExistingIsoCountries() throws Exception {
        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();
        isoCountries.setId(count.incrementAndGet());

        // Create the IsoCountries
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIsoCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, isoCountriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIsoCountries() throws Exception {
        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();
        isoCountries.setId(count.incrementAndGet());

        // Create the IsoCountries
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsoCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIsoCountries() throws Exception {
        int databaseSizeBeforeUpdate = isoCountriesRepository.findAll().size();
        isoCountries.setId(count.incrementAndGet());

        // Create the IsoCountries
        IsoCountriesDTO isoCountriesDTO = isoCountriesMapper.toDto(isoCountries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIsoCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(isoCountriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IsoCountries in the database
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIsoCountries() throws Exception {
        // Initialize the database
        isoCountriesRepository.saveAndFlush(isoCountries);

        int databaseSizeBeforeDelete = isoCountriesRepository.findAll().size();

        // Delete the isoCountries
        restIsoCountriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, isoCountries.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IsoCountries> isoCountriesList = isoCountriesRepository.findAll();
        assertThat(isoCountriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
