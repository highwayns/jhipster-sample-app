package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AbuseTrigger;
import io.github.jhipster.sample.repository.AbuseTriggerRepository;
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
 * Integration tests for the {@link AbuseTriggerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AbuseTriggerResourceIT {

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/abuse-triggers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AbuseTriggerRepository abuseTriggerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbuseTriggerMockMvc;

    private AbuseTrigger abuseTrigger;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbuseTrigger createEntity(EntityManager em) {
        AbuseTrigger abuseTrigger = new AbuseTrigger().score(DEFAULT_SCORE).code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return abuseTrigger;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbuseTrigger createUpdatedEntity(EntityManager em) {
        AbuseTrigger abuseTrigger = new AbuseTrigger().score(UPDATED_SCORE).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return abuseTrigger;
    }

    @BeforeEach
    public void initTest() {
        abuseTrigger = createEntity(em);
    }

    @Test
    @Transactional
    void createAbuseTrigger() throws Exception {
        int databaseSizeBeforeCreate = abuseTriggerRepository.findAll().size();
        // Create the AbuseTrigger
        restAbuseTriggerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abuseTrigger)))
            .andExpect(status().isCreated());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeCreate + 1);
        AbuseTrigger testAbuseTrigger = abuseTriggerList.get(abuseTriggerList.size() - 1);
        assertThat(testAbuseTrigger.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAbuseTrigger.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAbuseTrigger.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createAbuseTriggerWithExistingId() throws Exception {
        // Create the AbuseTrigger with an existing ID
        abuseTrigger.setId(1L);

        int databaseSizeBeforeCreate = abuseTriggerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbuseTriggerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abuseTrigger)))
            .andExpect(status().isBadRequest());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAbuseTriggers() throws Exception {
        // Initialize the database
        abuseTriggerRepository.saveAndFlush(abuseTrigger);

        // Get all the abuseTriggerList
        restAbuseTriggerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abuseTrigger.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getAbuseTrigger() throws Exception {
        // Initialize the database
        abuseTriggerRepository.saveAndFlush(abuseTrigger);

        // Get the abuseTrigger
        restAbuseTriggerMockMvc
            .perform(get(ENTITY_API_URL_ID, abuseTrigger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(abuseTrigger.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingAbuseTrigger() throws Exception {
        // Get the abuseTrigger
        restAbuseTriggerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAbuseTrigger() throws Exception {
        // Initialize the database
        abuseTriggerRepository.saveAndFlush(abuseTrigger);

        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();

        // Update the abuseTrigger
        AbuseTrigger updatedAbuseTrigger = abuseTriggerRepository.findById(abuseTrigger.getId()).get();
        // Disconnect from session so that the updates on updatedAbuseTrigger are not directly saved in db
        em.detach(updatedAbuseTrigger);
        updatedAbuseTrigger.score(UPDATED_SCORE).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restAbuseTriggerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAbuseTrigger.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAbuseTrigger))
            )
            .andExpect(status().isOk());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
        AbuseTrigger testAbuseTrigger = abuseTriggerList.get(abuseTriggerList.size() - 1);
        assertThat(testAbuseTrigger.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAbuseTrigger.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAbuseTrigger.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingAbuseTrigger() throws Exception {
        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();
        abuseTrigger.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbuseTriggerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, abuseTrigger.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(abuseTrigger))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAbuseTrigger() throws Exception {
        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();
        abuseTrigger.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseTriggerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(abuseTrigger))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAbuseTrigger() throws Exception {
        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();
        abuseTrigger.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseTriggerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abuseTrigger)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAbuseTriggerWithPatch() throws Exception {
        // Initialize the database
        abuseTriggerRepository.saveAndFlush(abuseTrigger);

        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();

        // Update the abuseTrigger using partial update
        AbuseTrigger partialUpdatedAbuseTrigger = new AbuseTrigger();
        partialUpdatedAbuseTrigger.setId(abuseTrigger.getId());

        restAbuseTriggerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbuseTrigger.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAbuseTrigger))
            )
            .andExpect(status().isOk());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
        AbuseTrigger testAbuseTrigger = abuseTriggerList.get(abuseTriggerList.size() - 1);
        assertThat(testAbuseTrigger.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAbuseTrigger.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAbuseTrigger.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateAbuseTriggerWithPatch() throws Exception {
        // Initialize the database
        abuseTriggerRepository.saveAndFlush(abuseTrigger);

        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();

        // Update the abuseTrigger using partial update
        AbuseTrigger partialUpdatedAbuseTrigger = new AbuseTrigger();
        partialUpdatedAbuseTrigger.setId(abuseTrigger.getId());

        partialUpdatedAbuseTrigger.score(UPDATED_SCORE).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restAbuseTriggerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbuseTrigger.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAbuseTrigger))
            )
            .andExpect(status().isOk());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
        AbuseTrigger testAbuseTrigger = abuseTriggerList.get(abuseTriggerList.size() - 1);
        assertThat(testAbuseTrigger.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAbuseTrigger.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAbuseTrigger.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingAbuseTrigger() throws Exception {
        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();
        abuseTrigger.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbuseTriggerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, abuseTrigger.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(abuseTrigger))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAbuseTrigger() throws Exception {
        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();
        abuseTrigger.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseTriggerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(abuseTrigger))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAbuseTrigger() throws Exception {
        int databaseSizeBeforeUpdate = abuseTriggerRepository.findAll().size();
        abuseTrigger.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbuseTriggerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(abuseTrigger))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AbuseTrigger in the database
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAbuseTrigger() throws Exception {
        // Initialize the database
        abuseTriggerRepository.saveAndFlush(abuseTrigger);

        int databaseSizeBeforeDelete = abuseTriggerRepository.findAll().size();

        // Delete the abuseTrigger
        restAbuseTriggerMockMvc
            .perform(delete(ENTITY_API_URL_ID, abuseTrigger.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AbuseTrigger> abuseTriggerList = abuseTriggerRepository.findAll();
        assertThat(abuseTriggerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
