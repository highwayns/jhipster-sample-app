package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.InnodbLockMonitor;
import io.github.jhipster.sample.repository.InnodbLockMonitorRepository;
import io.github.jhipster.sample.service.dto.InnodbLockMonitorDTO;
import io.github.jhipster.sample.service.mapper.InnodbLockMonitorMapper;
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
 * Integration tests for the {@link InnodbLockMonitorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InnodbLockMonitorResourceIT {

    private static final Integer DEFAULT_A = 1;
    private static final Integer UPDATED_A = 2;

    private static final String ENTITY_API_URL = "/api/innodb-lock-monitors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InnodbLockMonitorRepository innodbLockMonitorRepository;

    @Autowired
    private InnodbLockMonitorMapper innodbLockMonitorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInnodbLockMonitorMockMvc;

    private InnodbLockMonitor innodbLockMonitor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InnodbLockMonitor createEntity(EntityManager em) {
        InnodbLockMonitor innodbLockMonitor = new InnodbLockMonitor().a(DEFAULT_A);
        return innodbLockMonitor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InnodbLockMonitor createUpdatedEntity(EntityManager em) {
        InnodbLockMonitor innodbLockMonitor = new InnodbLockMonitor().a(UPDATED_A);
        return innodbLockMonitor;
    }

    @BeforeEach
    public void initTest() {
        innodbLockMonitor = createEntity(em);
    }

    @Test
    @Transactional
    void createInnodbLockMonitor() throws Exception {
        int databaseSizeBeforeCreate = innodbLockMonitorRepository.findAll().size();
        // Create the InnodbLockMonitor
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);
        restInnodbLockMonitorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeCreate + 1);
        InnodbLockMonitor testInnodbLockMonitor = innodbLockMonitorList.get(innodbLockMonitorList.size() - 1);
        assertThat(testInnodbLockMonitor.getA()).isEqualTo(DEFAULT_A);
    }

    @Test
    @Transactional
    void createInnodbLockMonitorWithExistingId() throws Exception {
        // Create the InnodbLockMonitor with an existing ID
        innodbLockMonitor.setId(1L);
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);

        int databaseSizeBeforeCreate = innodbLockMonitorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInnodbLockMonitorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInnodbLockMonitors() throws Exception {
        // Initialize the database
        innodbLockMonitorRepository.saveAndFlush(innodbLockMonitor);

        // Get all the innodbLockMonitorList
        restInnodbLockMonitorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(innodbLockMonitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].a").value(hasItem(DEFAULT_A)));
    }

    @Test
    @Transactional
    void getInnodbLockMonitor() throws Exception {
        // Initialize the database
        innodbLockMonitorRepository.saveAndFlush(innodbLockMonitor);

        // Get the innodbLockMonitor
        restInnodbLockMonitorMockMvc
            .perform(get(ENTITY_API_URL_ID, innodbLockMonitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(innodbLockMonitor.getId().intValue()))
            .andExpect(jsonPath("$.a").value(DEFAULT_A));
    }

    @Test
    @Transactional
    void getNonExistingInnodbLockMonitor() throws Exception {
        // Get the innodbLockMonitor
        restInnodbLockMonitorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInnodbLockMonitor() throws Exception {
        // Initialize the database
        innodbLockMonitorRepository.saveAndFlush(innodbLockMonitor);

        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();

        // Update the innodbLockMonitor
        InnodbLockMonitor updatedInnodbLockMonitor = innodbLockMonitorRepository.findById(innodbLockMonitor.getId()).get();
        // Disconnect from session so that the updates on updatedInnodbLockMonitor are not directly saved in db
        em.detach(updatedInnodbLockMonitor);
        updatedInnodbLockMonitor.a(UPDATED_A);
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(updatedInnodbLockMonitor);

        restInnodbLockMonitorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, innodbLockMonitorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isOk());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
        InnodbLockMonitor testInnodbLockMonitor = innodbLockMonitorList.get(innodbLockMonitorList.size() - 1);
        assertThat(testInnodbLockMonitor.getA()).isEqualTo(UPDATED_A);
    }

    @Test
    @Transactional
    void putNonExistingInnodbLockMonitor() throws Exception {
        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();
        innodbLockMonitor.setId(count.incrementAndGet());

        // Create the InnodbLockMonitor
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInnodbLockMonitorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, innodbLockMonitorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInnodbLockMonitor() throws Exception {
        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();
        innodbLockMonitor.setId(count.incrementAndGet());

        // Create the InnodbLockMonitor
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnodbLockMonitorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInnodbLockMonitor() throws Exception {
        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();
        innodbLockMonitor.setId(count.incrementAndGet());

        // Create the InnodbLockMonitor
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnodbLockMonitorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInnodbLockMonitorWithPatch() throws Exception {
        // Initialize the database
        innodbLockMonitorRepository.saveAndFlush(innodbLockMonitor);

        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();

        // Update the innodbLockMonitor using partial update
        InnodbLockMonitor partialUpdatedInnodbLockMonitor = new InnodbLockMonitor();
        partialUpdatedInnodbLockMonitor.setId(innodbLockMonitor.getId());

        restInnodbLockMonitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInnodbLockMonitor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInnodbLockMonitor))
            )
            .andExpect(status().isOk());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
        InnodbLockMonitor testInnodbLockMonitor = innodbLockMonitorList.get(innodbLockMonitorList.size() - 1);
        assertThat(testInnodbLockMonitor.getA()).isEqualTo(DEFAULT_A);
    }

    @Test
    @Transactional
    void fullUpdateInnodbLockMonitorWithPatch() throws Exception {
        // Initialize the database
        innodbLockMonitorRepository.saveAndFlush(innodbLockMonitor);

        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();

        // Update the innodbLockMonitor using partial update
        InnodbLockMonitor partialUpdatedInnodbLockMonitor = new InnodbLockMonitor();
        partialUpdatedInnodbLockMonitor.setId(innodbLockMonitor.getId());

        partialUpdatedInnodbLockMonitor.a(UPDATED_A);

        restInnodbLockMonitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInnodbLockMonitor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInnodbLockMonitor))
            )
            .andExpect(status().isOk());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
        InnodbLockMonitor testInnodbLockMonitor = innodbLockMonitorList.get(innodbLockMonitorList.size() - 1);
        assertThat(testInnodbLockMonitor.getA()).isEqualTo(UPDATED_A);
    }

    @Test
    @Transactional
    void patchNonExistingInnodbLockMonitor() throws Exception {
        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();
        innodbLockMonitor.setId(count.incrementAndGet());

        // Create the InnodbLockMonitor
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInnodbLockMonitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, innodbLockMonitorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInnodbLockMonitor() throws Exception {
        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();
        innodbLockMonitor.setId(count.incrementAndGet());

        // Create the InnodbLockMonitor
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnodbLockMonitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInnodbLockMonitor() throws Exception {
        int databaseSizeBeforeUpdate = innodbLockMonitorRepository.findAll().size();
        innodbLockMonitor.setId(count.incrementAndGet());

        // Create the InnodbLockMonitor
        InnodbLockMonitorDTO innodbLockMonitorDTO = innodbLockMonitorMapper.toDto(innodbLockMonitor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInnodbLockMonitorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(innodbLockMonitorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InnodbLockMonitor in the database
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInnodbLockMonitor() throws Exception {
        // Initialize the database
        innodbLockMonitorRepository.saveAndFlush(innodbLockMonitor);

        int databaseSizeBeforeDelete = innodbLockMonitorRepository.findAll().size();

        // Delete the innodbLockMonitor
        restInnodbLockMonitorMockMvc
            .perform(delete(ENTITY_API_URL_ID, innodbLockMonitor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InnodbLockMonitor> innodbLockMonitorList = innodbLockMonitorRepository.findAll();
        assertThat(innodbLockMonitorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
