package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.RecurrenceCriteria;
import io.github.jhipster.sample.domain.enumeration.RecurrenceType;
import io.github.jhipster.sample.repository.RecurrenceCriteriaRepository;
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
 * Integration tests for the {@link RecurrenceCriteriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecurrenceCriteriaResourceIT {

    private static final RecurrenceType DEFAULT_RECURRENCE_TYPE = RecurrenceType.SUBSCRIPTION;
    private static final RecurrenceType UPDATED_RECURRENCE_TYPE = RecurrenceType.INSTALMENT;

    private static final Instant DEFAULT_RECURRING_EXPIRY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECURRING_EXPIRY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_RECURRING_FREQUENCY = 1;
    private static final Integer UPDATED_RECURRING_FREQUENCY = 2;

    private static final Integer DEFAULT_INSTALMENTS = 1;
    private static final Integer UPDATED_INSTALMENTS = 2;

    private static final String ENTITY_API_URL = "/api/recurrence-criteria";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecurrenceCriteriaRepository recurrenceCriteriaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecurrenceCriteriaMockMvc;

    private RecurrenceCriteria recurrenceCriteria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecurrenceCriteria createEntity(EntityManager em) {
        RecurrenceCriteria recurrenceCriteria = new RecurrenceCriteria()
            .recurrenceType(DEFAULT_RECURRENCE_TYPE)
            .recurringExpiry(DEFAULT_RECURRING_EXPIRY)
            .recurringFrequency(DEFAULT_RECURRING_FREQUENCY)
            .instalments(DEFAULT_INSTALMENTS);
        return recurrenceCriteria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecurrenceCriteria createUpdatedEntity(EntityManager em) {
        RecurrenceCriteria recurrenceCriteria = new RecurrenceCriteria()
            .recurrenceType(UPDATED_RECURRENCE_TYPE)
            .recurringExpiry(UPDATED_RECURRING_EXPIRY)
            .recurringFrequency(UPDATED_RECURRING_FREQUENCY)
            .instalments(UPDATED_INSTALMENTS);
        return recurrenceCriteria;
    }

    @BeforeEach
    public void initTest() {
        recurrenceCriteria = createEntity(em);
    }

    @Test
    @Transactional
    void createRecurrenceCriteria() throws Exception {
        int databaseSizeBeforeCreate = recurrenceCriteriaRepository.findAll().size();
        // Create the RecurrenceCriteria
        restRecurrenceCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isCreated());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeCreate + 1);
        RecurrenceCriteria testRecurrenceCriteria = recurrenceCriteriaList.get(recurrenceCriteriaList.size() - 1);
        assertThat(testRecurrenceCriteria.getRecurrenceType()).isEqualTo(DEFAULT_RECURRENCE_TYPE);
        assertThat(testRecurrenceCriteria.getRecurringExpiry()).isEqualTo(DEFAULT_RECURRING_EXPIRY);
        assertThat(testRecurrenceCriteria.getRecurringFrequency()).isEqualTo(DEFAULT_RECURRING_FREQUENCY);
        assertThat(testRecurrenceCriteria.getInstalments()).isEqualTo(DEFAULT_INSTALMENTS);
    }

    @Test
    @Transactional
    void createRecurrenceCriteriaWithExistingId() throws Exception {
        // Create the RecurrenceCriteria with an existing ID
        recurrenceCriteria.setId(1L);

        int databaseSizeBeforeCreate = recurrenceCriteriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecurrenceCriteriaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecurrenceCriteria() throws Exception {
        // Initialize the database
        recurrenceCriteriaRepository.saveAndFlush(recurrenceCriteria);

        // Get all the recurrenceCriteriaList
        restRecurrenceCriteriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurrenceCriteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].recurrenceType").value(hasItem(DEFAULT_RECURRENCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].recurringExpiry").value(hasItem(DEFAULT_RECURRING_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].recurringFrequency").value(hasItem(DEFAULT_RECURRING_FREQUENCY)))
            .andExpect(jsonPath("$.[*].instalments").value(hasItem(DEFAULT_INSTALMENTS)));
    }

    @Test
    @Transactional
    void getRecurrenceCriteria() throws Exception {
        // Initialize the database
        recurrenceCriteriaRepository.saveAndFlush(recurrenceCriteria);

        // Get the recurrenceCriteria
        restRecurrenceCriteriaMockMvc
            .perform(get(ENTITY_API_URL_ID, recurrenceCriteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recurrenceCriteria.getId().intValue()))
            .andExpect(jsonPath("$.recurrenceType").value(DEFAULT_RECURRENCE_TYPE.toString()))
            .andExpect(jsonPath("$.recurringExpiry").value(DEFAULT_RECURRING_EXPIRY.toString()))
            .andExpect(jsonPath("$.recurringFrequency").value(DEFAULT_RECURRING_FREQUENCY))
            .andExpect(jsonPath("$.instalments").value(DEFAULT_INSTALMENTS));
    }

    @Test
    @Transactional
    void getNonExistingRecurrenceCriteria() throws Exception {
        // Get the recurrenceCriteria
        restRecurrenceCriteriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRecurrenceCriteria() throws Exception {
        // Initialize the database
        recurrenceCriteriaRepository.saveAndFlush(recurrenceCriteria);

        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();

        // Update the recurrenceCriteria
        RecurrenceCriteria updatedRecurrenceCriteria = recurrenceCriteriaRepository.findById(recurrenceCriteria.getId()).get();
        // Disconnect from session so that the updates on updatedRecurrenceCriteria are not directly saved in db
        em.detach(updatedRecurrenceCriteria);
        updatedRecurrenceCriteria
            .recurrenceType(UPDATED_RECURRENCE_TYPE)
            .recurringExpiry(UPDATED_RECURRING_EXPIRY)
            .recurringFrequency(UPDATED_RECURRING_FREQUENCY)
            .instalments(UPDATED_INSTALMENTS);

        restRecurrenceCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecurrenceCriteria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecurrenceCriteria))
            )
            .andExpect(status().isOk());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
        RecurrenceCriteria testRecurrenceCriteria = recurrenceCriteriaList.get(recurrenceCriteriaList.size() - 1);
        assertThat(testRecurrenceCriteria.getRecurrenceType()).isEqualTo(UPDATED_RECURRENCE_TYPE);
        assertThat(testRecurrenceCriteria.getRecurringExpiry()).isEqualTo(UPDATED_RECURRING_EXPIRY);
        assertThat(testRecurrenceCriteria.getRecurringFrequency()).isEqualTo(UPDATED_RECURRING_FREQUENCY);
        assertThat(testRecurrenceCriteria.getInstalments()).isEqualTo(UPDATED_INSTALMENTS);
    }

    @Test
    @Transactional
    void putNonExistingRecurrenceCriteria() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();
        recurrenceCriteria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecurrenceCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recurrenceCriteria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecurrenceCriteria() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();
        recurrenceCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecurrenceCriteria() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();
        recurrenceCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceCriteriaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecurrenceCriteriaWithPatch() throws Exception {
        // Initialize the database
        recurrenceCriteriaRepository.saveAndFlush(recurrenceCriteria);

        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();

        // Update the recurrenceCriteria using partial update
        RecurrenceCriteria partialUpdatedRecurrenceCriteria = new RecurrenceCriteria();
        partialUpdatedRecurrenceCriteria.setId(recurrenceCriteria.getId());

        partialUpdatedRecurrenceCriteria.recurrenceType(UPDATED_RECURRENCE_TYPE).recurringFrequency(UPDATED_RECURRING_FREQUENCY);

        restRecurrenceCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurrenceCriteria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecurrenceCriteria))
            )
            .andExpect(status().isOk());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
        RecurrenceCriteria testRecurrenceCriteria = recurrenceCriteriaList.get(recurrenceCriteriaList.size() - 1);
        assertThat(testRecurrenceCriteria.getRecurrenceType()).isEqualTo(UPDATED_RECURRENCE_TYPE);
        assertThat(testRecurrenceCriteria.getRecurringExpiry()).isEqualTo(DEFAULT_RECURRING_EXPIRY);
        assertThat(testRecurrenceCriteria.getRecurringFrequency()).isEqualTo(UPDATED_RECURRING_FREQUENCY);
        assertThat(testRecurrenceCriteria.getInstalments()).isEqualTo(DEFAULT_INSTALMENTS);
    }

    @Test
    @Transactional
    void fullUpdateRecurrenceCriteriaWithPatch() throws Exception {
        // Initialize the database
        recurrenceCriteriaRepository.saveAndFlush(recurrenceCriteria);

        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();

        // Update the recurrenceCriteria using partial update
        RecurrenceCriteria partialUpdatedRecurrenceCriteria = new RecurrenceCriteria();
        partialUpdatedRecurrenceCriteria.setId(recurrenceCriteria.getId());

        partialUpdatedRecurrenceCriteria
            .recurrenceType(UPDATED_RECURRENCE_TYPE)
            .recurringExpiry(UPDATED_RECURRING_EXPIRY)
            .recurringFrequency(UPDATED_RECURRING_FREQUENCY)
            .instalments(UPDATED_INSTALMENTS);

        restRecurrenceCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurrenceCriteria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecurrenceCriteria))
            )
            .andExpect(status().isOk());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
        RecurrenceCriteria testRecurrenceCriteria = recurrenceCriteriaList.get(recurrenceCriteriaList.size() - 1);
        assertThat(testRecurrenceCriteria.getRecurrenceType()).isEqualTo(UPDATED_RECURRENCE_TYPE);
        assertThat(testRecurrenceCriteria.getRecurringExpiry()).isEqualTo(UPDATED_RECURRING_EXPIRY);
        assertThat(testRecurrenceCriteria.getRecurringFrequency()).isEqualTo(UPDATED_RECURRING_FREQUENCY);
        assertThat(testRecurrenceCriteria.getInstalments()).isEqualTo(UPDATED_INSTALMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingRecurrenceCriteria() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();
        recurrenceCriteria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecurrenceCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recurrenceCriteria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecurrenceCriteria() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();
        recurrenceCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecurrenceCriteria() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceCriteriaRepository.findAll().size();
        recurrenceCriteria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceCriteriaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceCriteria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecurrenceCriteria in the database
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecurrenceCriteria() throws Exception {
        // Initialize the database
        recurrenceCriteriaRepository.saveAndFlush(recurrenceCriteria);

        int databaseSizeBeforeDelete = recurrenceCriteriaRepository.findAll().size();

        // Delete the recurrenceCriteria
        restRecurrenceCriteriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, recurrenceCriteria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecurrenceCriteria> recurrenceCriteriaList = recurrenceCriteriaRepository.findAll();
        assertThat(recurrenceCriteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
