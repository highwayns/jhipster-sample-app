package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Parameters;
import io.github.jhipster.sample.repository.ParametersRepository;
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
 * Integration tests for the {@link ParametersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParametersResourceIT {

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parameters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParametersRepository parametersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametersMockMvc;

    private Parameters parameters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createEntity(EntityManager em) {
        Parameters parameters = new Parameters().parameter(DEFAULT_PARAMETER);
        return parameters;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createUpdatedEntity(EntityManager em) {
        Parameters parameters = new Parameters().parameter(UPDATED_PARAMETER);
        return parameters;
    }

    @BeforeEach
    public void initTest() {
        parameters = createEntity(em);
    }

    @Test
    @Transactional
    void createParameters() throws Exception {
        int databaseSizeBeforeCreate = parametersRepository.findAll().size();
        // Create the Parameters
        restParametersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameters)))
            .andExpect(status().isCreated());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate + 1);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getParameter()).isEqualTo(DEFAULT_PARAMETER);
    }

    @Test
    @Transactional
    void createParametersWithExistingId() throws Exception {
        // Create the Parameters with an existing ID
        parameters.setId(1L);

        int databaseSizeBeforeCreate = parametersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameters)))
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList
        restParametersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER)));
    }

    @Test
    @Transactional
    void getParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get the parameters
        restParametersMockMvc
            .perform(get(ENTITY_API_URL_ID, parameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parameters.getId().intValue()))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER));
    }

    @Test
    @Transactional
    void getNonExistingParameters() throws Exception {
        // Get the parameters
        restParametersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters
        Parameters updatedParameters = parametersRepository.findById(parameters.getId()).get();
        // Disconnect from session so that the updates on updatedParameters are not directly saved in db
        em.detach(updatedParameters);
        updatedParameters.parameter(UPDATED_PARAMETER);

        restParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParameters.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParameters))
            )
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getParameter()).isEqualTo(UPDATED_PARAMETER);
    }

    @Test
    @Transactional
    void putNonExistingParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parameters.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameters)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParametersWithPatch() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters using partial update
        Parameters partialUpdatedParameters = new Parameters();
        partialUpdatedParameters.setId(parameters.getId());

        partialUpdatedParameters.parameter(UPDATED_PARAMETER);

        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameters))
            )
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getParameter()).isEqualTo(UPDATED_PARAMETER);
    }

    @Test
    @Transactional
    void fullUpdateParametersWithPatch() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters using partial update
        Parameters partialUpdatedParameters = new Parameters();
        partialUpdatedParameters.setId(parameters.getId());

        partialUpdatedParameters.parameter(UPDATED_PARAMETER);

        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameters))
            )
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getParameter()).isEqualTo(UPDATED_PARAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(parameters))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeDelete = parametersRepository.findAll().size();

        // Delete the parameters
        restParametersMockMvc
            .perform(delete(ENTITY_API_URL_ID, parameters.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
