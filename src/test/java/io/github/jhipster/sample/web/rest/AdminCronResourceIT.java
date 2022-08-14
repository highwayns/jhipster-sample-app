package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminCron;
import io.github.jhipster.sample.repository.AdminCronRepository;
import io.github.jhipster.sample.service.dto.AdminCronDTO;
import io.github.jhipster.sample.service.mapper.AdminCronMapper;
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
 * Integration tests for the {@link AdminCronResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminCronResourceIT {

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_SEND_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_SEND_CONDITION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/admin-crons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminCronRepository adminCronRepository;

    @Autowired
    private AdminCronMapper adminCronMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminCronMockMvc;

    private AdminCron adminCron;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminCron createEntity(EntityManager em) {
        AdminCron adminCron = new AdminCron()
            .day(DEFAULT_DAY)
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .sendCondition(DEFAULT_SEND_CONDITION);
        return adminCron;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminCron createUpdatedEntity(EntityManager em) {
        AdminCron adminCron = new AdminCron()
            .day(UPDATED_DAY)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .sendCondition(UPDATED_SEND_CONDITION);
        return adminCron;
    }

    @BeforeEach
    public void initTest() {
        adminCron = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminCron() throws Exception {
        int databaseSizeBeforeCreate = adminCronRepository.findAll().size();
        // Create the AdminCron
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);
        restAdminCronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminCronDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeCreate + 1);
        AdminCron testAdminCron = adminCronList.get(adminCronList.size() - 1);
        assertThat(testAdminCron.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testAdminCron.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testAdminCron.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAdminCron.getSendCondition()).isEqualTo(DEFAULT_SEND_CONDITION);
    }

    @Test
    @Transactional
    void createAdminCronWithExistingId() throws Exception {
        // Create the AdminCron with an existing ID
        adminCron.setId(1L);
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        int databaseSizeBeforeCreate = adminCronRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminCronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminCronDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminCronRepository.findAll().size();
        // set the field null
        adminCron.setDay(null);

        // Create the AdminCron, which fails.
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        restAdminCronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminCronDTO)))
            .andExpect(status().isBadRequest());

        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminCronRepository.findAll().size();
        // set the field null
        adminCron.setMonth(null);

        // Create the AdminCron, which fails.
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        restAdminCronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminCronDTO)))
            .andExpect(status().isBadRequest());

        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminCronRepository.findAll().size();
        // set the field null
        adminCron.setYear(null);

        // Create the AdminCron, which fails.
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        restAdminCronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminCronDTO)))
            .andExpect(status().isBadRequest());

        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSendConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminCronRepository.findAll().size();
        // set the field null
        adminCron.setSendCondition(null);

        // Create the AdminCron, which fails.
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        restAdminCronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminCronDTO)))
            .andExpect(status().isBadRequest());

        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminCrons() throws Exception {
        // Initialize the database
        adminCronRepository.saveAndFlush(adminCron);

        // Get all the adminCronList
        restAdminCronMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminCron.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].sendCondition").value(hasItem(DEFAULT_SEND_CONDITION)));
    }

    @Test
    @Transactional
    void getAdminCron() throws Exception {
        // Initialize the database
        adminCronRepository.saveAndFlush(adminCron);

        // Get the adminCron
        restAdminCronMockMvc
            .perform(get(ENTITY_API_URL_ID, adminCron.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminCron.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.sendCondition").value(DEFAULT_SEND_CONDITION));
    }

    @Test
    @Transactional
    void getNonExistingAdminCron() throws Exception {
        // Get the adminCron
        restAdminCronMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminCron() throws Exception {
        // Initialize the database
        adminCronRepository.saveAndFlush(adminCron);

        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();

        // Update the adminCron
        AdminCron updatedAdminCron = adminCronRepository.findById(adminCron.getId()).get();
        // Disconnect from session so that the updates on updatedAdminCron are not directly saved in db
        em.detach(updatedAdminCron);
        updatedAdminCron.day(UPDATED_DAY).month(UPDATED_MONTH).year(UPDATED_YEAR).sendCondition(UPDATED_SEND_CONDITION);
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(updatedAdminCron);

        restAdminCronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminCronDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminCronDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
        AdminCron testAdminCron = adminCronList.get(adminCronList.size() - 1);
        assertThat(testAdminCron.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testAdminCron.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testAdminCron.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAdminCron.getSendCondition()).isEqualTo(UPDATED_SEND_CONDITION);
    }

    @Test
    @Transactional
    void putNonExistingAdminCron() throws Exception {
        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();
        adminCron.setId(count.incrementAndGet());

        // Create the AdminCron
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminCronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminCronDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminCronDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminCron() throws Exception {
        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();
        adminCron.setId(count.incrementAndGet());

        // Create the AdminCron
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminCronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminCronDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminCron() throws Exception {
        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();
        adminCron.setId(count.incrementAndGet());

        // Create the AdminCron
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminCronMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminCronDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminCronWithPatch() throws Exception {
        // Initialize the database
        adminCronRepository.saveAndFlush(adminCron);

        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();

        // Update the adminCron using partial update
        AdminCron partialUpdatedAdminCron = new AdminCron();
        partialUpdatedAdminCron.setId(adminCron.getId());

        partialUpdatedAdminCron.month(UPDATED_MONTH).year(UPDATED_YEAR).sendCondition(UPDATED_SEND_CONDITION);

        restAdminCronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminCron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminCron))
            )
            .andExpect(status().isOk());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
        AdminCron testAdminCron = adminCronList.get(adminCronList.size() - 1);
        assertThat(testAdminCron.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testAdminCron.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testAdminCron.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAdminCron.getSendCondition()).isEqualTo(UPDATED_SEND_CONDITION);
    }

    @Test
    @Transactional
    void fullUpdateAdminCronWithPatch() throws Exception {
        // Initialize the database
        adminCronRepository.saveAndFlush(adminCron);

        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();

        // Update the adminCron using partial update
        AdminCron partialUpdatedAdminCron = new AdminCron();
        partialUpdatedAdminCron.setId(adminCron.getId());

        partialUpdatedAdminCron.day(UPDATED_DAY).month(UPDATED_MONTH).year(UPDATED_YEAR).sendCondition(UPDATED_SEND_CONDITION);

        restAdminCronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminCron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminCron))
            )
            .andExpect(status().isOk());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
        AdminCron testAdminCron = adminCronList.get(adminCronList.size() - 1);
        assertThat(testAdminCron.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testAdminCron.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testAdminCron.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAdminCron.getSendCondition()).isEqualTo(UPDATED_SEND_CONDITION);
    }

    @Test
    @Transactional
    void patchNonExistingAdminCron() throws Exception {
        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();
        adminCron.setId(count.incrementAndGet());

        // Create the AdminCron
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminCronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminCronDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminCronDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminCron() throws Exception {
        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();
        adminCron.setId(count.incrementAndGet());

        // Create the AdminCron
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminCronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminCronDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminCron() throws Exception {
        int databaseSizeBeforeUpdate = adminCronRepository.findAll().size();
        adminCron.setId(count.incrementAndGet());

        // Create the AdminCron
        AdminCronDTO adminCronDTO = adminCronMapper.toDto(adminCron);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminCronMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adminCronDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminCron in the database
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminCron() throws Exception {
        // Initialize the database
        adminCronRepository.saveAndFlush(adminCron);

        int databaseSizeBeforeDelete = adminCronRepository.findAll().size();

        // Delete the adminCron
        restAdminCronMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminCron.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminCron> adminCronList = adminCronRepository.findAll();
        assertThat(adminCronList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
