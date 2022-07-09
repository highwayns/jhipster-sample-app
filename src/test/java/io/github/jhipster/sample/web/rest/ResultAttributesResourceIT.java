package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.ResultAttributes;
import io.github.jhipster.sample.repository.ResultAttributesRepository;
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
 * Integration tests for the {@link ResultAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultAttributesResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/result-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResultAttributesRepository resultAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultAttributesMockMvc;

    private ResultAttributes resultAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultAttributes createEntity(EntityManager em) {
        ResultAttributes resultAttributes = new ResultAttributes().key(DEFAULT_KEY).value(DEFAULT_VALUE);
        return resultAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultAttributes createUpdatedEntity(EntityManager em) {
        ResultAttributes resultAttributes = new ResultAttributes().key(UPDATED_KEY).value(UPDATED_VALUE);
        return resultAttributes;
    }

    @BeforeEach
    public void initTest() {
        resultAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createResultAttributes() throws Exception {
        int databaseSizeBeforeCreate = resultAttributesRepository.findAll().size();
        // Create the ResultAttributes
        restResultAttributesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        ResultAttributes testResultAttributes = resultAttributesList.get(resultAttributesList.size() - 1);
        assertThat(testResultAttributes.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testResultAttributes.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createResultAttributesWithExistingId() throws Exception {
        // Create the ResultAttributes with an existing ID
        resultAttributes.setId(1L);

        int databaseSizeBeforeCreate = resultAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultAttributesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResultAttributes() throws Exception {
        // Initialize the database
        resultAttributesRepository.saveAndFlush(resultAttributes);

        // Get all the resultAttributesList
        restResultAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getResultAttributes() throws Exception {
        // Initialize the database
        resultAttributesRepository.saveAndFlush(resultAttributes);

        // Get the resultAttributes
        restResultAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, resultAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultAttributes.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingResultAttributes() throws Exception {
        // Get the resultAttributes
        restResultAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResultAttributes() throws Exception {
        // Initialize the database
        resultAttributesRepository.saveAndFlush(resultAttributes);

        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();

        // Update the resultAttributes
        ResultAttributes updatedResultAttributes = resultAttributesRepository.findById(resultAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedResultAttributes are not directly saved in db
        em.detach(updatedResultAttributes);
        updatedResultAttributes.key(UPDATED_KEY).value(UPDATED_VALUE);

        restResultAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResultAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResultAttributes))
            )
            .andExpect(status().isOk());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
        ResultAttributes testResultAttributes = resultAttributesList.get(resultAttributesList.size() - 1);
        assertThat(testResultAttributes.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testResultAttributes.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingResultAttributes() throws Exception {
        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();
        resultAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResultAttributes() throws Exception {
        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();
        resultAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResultAttributes() throws Exception {
        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();
        resultAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultAttributesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResultAttributesWithPatch() throws Exception {
        // Initialize the database
        resultAttributesRepository.saveAndFlush(resultAttributes);

        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();

        // Update the resultAttributes using partial update
        ResultAttributes partialUpdatedResultAttributes = new ResultAttributes();
        partialUpdatedResultAttributes.setId(resultAttributes.getId());

        partialUpdatedResultAttributes.key(UPDATED_KEY).value(UPDATED_VALUE);

        restResultAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultAttributes))
            )
            .andExpect(status().isOk());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
        ResultAttributes testResultAttributes = resultAttributesList.get(resultAttributesList.size() - 1);
        assertThat(testResultAttributes.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testResultAttributes.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateResultAttributesWithPatch() throws Exception {
        // Initialize the database
        resultAttributesRepository.saveAndFlush(resultAttributes);

        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();

        // Update the resultAttributes using partial update
        ResultAttributes partialUpdatedResultAttributes = new ResultAttributes();
        partialUpdatedResultAttributes.setId(resultAttributes.getId());

        partialUpdatedResultAttributes.key(UPDATED_KEY).value(UPDATED_VALUE);

        restResultAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultAttributes))
            )
            .andExpect(status().isOk());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
        ResultAttributes testResultAttributes = resultAttributesList.get(resultAttributesList.size() - 1);
        assertThat(testResultAttributes.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testResultAttributes.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingResultAttributes() throws Exception {
        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();
        resultAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resultAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResultAttributes() throws Exception {
        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();
        resultAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResultAttributes() throws Exception {
        int databaseSizeBeforeUpdate = resultAttributesRepository.findAll().size();
        resultAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultAttributes in the database
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResultAttributes() throws Exception {
        // Initialize the database
        resultAttributesRepository.saveAndFlush(resultAttributes);

        int databaseSizeBeforeDelete = resultAttributesRepository.findAll().size();

        // Delete the resultAttributes
        restResultAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, resultAttributes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultAttributes> resultAttributesList = resultAttributesRepository.findAll();
        assertThat(resultAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
