package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminControls;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.AdminControlsRepository;
import io.github.jhipster.sample.service.dto.AdminControlsDTO;
import io.github.jhipster.sample.service.mapper.AdminControlsMapper;
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
 * Integration tests for the {@link AdminControlsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminControlsResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROL_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_ARGUMENT = "AAAAAAAAAA";
    private static final String UPDATED_ARGUMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final YesNo DEFAULT_IS_STATIC = YesNo.Y;
    private static final YesNo UPDATED_IS_STATIC = YesNo.N;

    private static final String ENTITY_API_URL = "/api/admin-controls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminControlsRepository adminControlsRepository;

    @Autowired
    private AdminControlsMapper adminControlsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminControlsMockMvc;

    private AdminControls adminControls;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminControls createEntity(EntityManager em) {
        AdminControls adminControls = new AdminControls()
            .action(DEFAULT_ACTION)
            .controlClass(DEFAULT_CONTROL_CLASS)
            .argument(DEFAULT_ARGUMENT)
            .order(DEFAULT_ORDER)
            .isStatic(DEFAULT_IS_STATIC);
        return adminControls;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminControls createUpdatedEntity(EntityManager em) {
        AdminControls adminControls = new AdminControls()
            .action(UPDATED_ACTION)
            .controlClass(UPDATED_CONTROL_CLASS)
            .argument(UPDATED_ARGUMENT)
            .order(UPDATED_ORDER)
            .isStatic(UPDATED_IS_STATIC);
        return adminControls;
    }

    @BeforeEach
    public void initTest() {
        adminControls = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminControls() throws Exception {
        int databaseSizeBeforeCreate = adminControlsRepository.findAll().size();
        // Create the AdminControls
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);
        restAdminControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeCreate + 1);
        AdminControls testAdminControls = adminControlsList.get(adminControlsList.size() - 1);
        assertThat(testAdminControls.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAdminControls.getControlClass()).isEqualTo(DEFAULT_CONTROL_CLASS);
        assertThat(testAdminControls.getArgument()).isEqualTo(DEFAULT_ARGUMENT);
        assertThat(testAdminControls.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAdminControls.getIsStatic()).isEqualTo(DEFAULT_IS_STATIC);
    }

    @Test
    @Transactional
    void createAdminControlsWithExistingId() throws Exception {
        // Create the AdminControls with an existing ID
        adminControls.setId(1L);
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        int databaseSizeBeforeCreate = adminControlsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminControlsRepository.findAll().size();
        // set the field null
        adminControls.setAction(null);

        // Create the AdminControls, which fails.
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        restAdminControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkArgumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminControlsRepository.findAll().size();
        // set the field null
        adminControls.setArgument(null);

        // Create the AdminControls, which fails.
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        restAdminControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminControlsRepository.findAll().size();
        // set the field null
        adminControls.setOrder(null);

        // Create the AdminControls, which fails.
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        restAdminControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsStaticIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminControlsRepository.findAll().size();
        // set the field null
        adminControls.setIsStatic(null);

        // Create the AdminControls, which fails.
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        restAdminControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminControls() throws Exception {
        // Initialize the database
        adminControlsRepository.saveAndFlush(adminControls);

        // Get all the adminControlsList
        restAdminControlsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminControls.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].controlClass").value(hasItem(DEFAULT_CONTROL_CLASS)))
            .andExpect(jsonPath("$.[*].argument").value(hasItem(DEFAULT_ARGUMENT)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].isStatic").value(hasItem(DEFAULT_IS_STATIC.toString())));
    }

    @Test
    @Transactional
    void getAdminControls() throws Exception {
        // Initialize the database
        adminControlsRepository.saveAndFlush(adminControls);

        // Get the adminControls
        restAdminControlsMockMvc
            .perform(get(ENTITY_API_URL_ID, adminControls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminControls.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.controlClass").value(DEFAULT_CONTROL_CLASS))
            .andExpect(jsonPath("$.argument").value(DEFAULT_ARGUMENT))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.isStatic").value(DEFAULT_IS_STATIC.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdminControls() throws Exception {
        // Get the adminControls
        restAdminControlsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminControls() throws Exception {
        // Initialize the database
        adminControlsRepository.saveAndFlush(adminControls);

        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();

        // Update the adminControls
        AdminControls updatedAdminControls = adminControlsRepository.findById(adminControls.getId()).get();
        // Disconnect from session so that the updates on updatedAdminControls are not directly saved in db
        em.detach(updatedAdminControls);
        updatedAdminControls
            .action(UPDATED_ACTION)
            .controlClass(UPDATED_CONTROL_CLASS)
            .argument(UPDATED_ARGUMENT)
            .order(UPDATED_ORDER)
            .isStatic(UPDATED_IS_STATIC);
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(updatedAdminControls);

        restAdminControlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminControlsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
        AdminControls testAdminControls = adminControlsList.get(adminControlsList.size() - 1);
        assertThat(testAdminControls.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAdminControls.getControlClass()).isEqualTo(UPDATED_CONTROL_CLASS);
        assertThat(testAdminControls.getArgument()).isEqualTo(UPDATED_ARGUMENT);
        assertThat(testAdminControls.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminControls.getIsStatic()).isEqualTo(UPDATED_IS_STATIC);
    }

    @Test
    @Transactional
    void putNonExistingAdminControls() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();
        adminControls.setId(count.incrementAndGet());

        // Create the AdminControls
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminControlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminControlsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminControls() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();
        adminControls.setId(count.incrementAndGet());

        // Create the AdminControls
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminControls() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();
        adminControls.setId(count.incrementAndGet());

        // Create the AdminControls
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminControlsWithPatch() throws Exception {
        // Initialize the database
        adminControlsRepository.saveAndFlush(adminControls);

        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();

        // Update the adminControls using partial update
        AdminControls partialUpdatedAdminControls = new AdminControls();
        partialUpdatedAdminControls.setId(adminControls.getId());

        partialUpdatedAdminControls.action(UPDATED_ACTION).argument(UPDATED_ARGUMENT);

        restAdminControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminControls.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminControls))
            )
            .andExpect(status().isOk());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
        AdminControls testAdminControls = adminControlsList.get(adminControlsList.size() - 1);
        assertThat(testAdminControls.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAdminControls.getControlClass()).isEqualTo(DEFAULT_CONTROL_CLASS);
        assertThat(testAdminControls.getArgument()).isEqualTo(UPDATED_ARGUMENT);
        assertThat(testAdminControls.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAdminControls.getIsStatic()).isEqualTo(DEFAULT_IS_STATIC);
    }

    @Test
    @Transactional
    void fullUpdateAdminControlsWithPatch() throws Exception {
        // Initialize the database
        adminControlsRepository.saveAndFlush(adminControls);

        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();

        // Update the adminControls using partial update
        AdminControls partialUpdatedAdminControls = new AdminControls();
        partialUpdatedAdminControls.setId(adminControls.getId());

        partialUpdatedAdminControls
            .action(UPDATED_ACTION)
            .controlClass(UPDATED_CONTROL_CLASS)
            .argument(UPDATED_ARGUMENT)
            .order(UPDATED_ORDER)
            .isStatic(UPDATED_IS_STATIC);

        restAdminControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminControls.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminControls))
            )
            .andExpect(status().isOk());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
        AdminControls testAdminControls = adminControlsList.get(adminControlsList.size() - 1);
        assertThat(testAdminControls.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAdminControls.getControlClass()).isEqualTo(UPDATED_CONTROL_CLASS);
        assertThat(testAdminControls.getArgument()).isEqualTo(UPDATED_ARGUMENT);
        assertThat(testAdminControls.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminControls.getIsStatic()).isEqualTo(UPDATED_IS_STATIC);
    }

    @Test
    @Transactional
    void patchNonExistingAdminControls() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();
        adminControls.setId(count.incrementAndGet());

        // Create the AdminControls
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminControlsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminControls() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();
        adminControls.setId(count.incrementAndGet());

        // Create the AdminControls
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminControls() throws Exception {
        int databaseSizeBeforeUpdate = adminControlsRepository.findAll().size();
        adminControls.setId(count.incrementAndGet());

        // Create the AdminControls
        AdminControlsDTO adminControlsDTO = adminControlsMapper.toDto(adminControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminControlsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminControlsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminControls in the database
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminControls() throws Exception {
        // Initialize the database
        adminControlsRepository.saveAndFlush(adminControls);

        int databaseSizeBeforeDelete = adminControlsRepository.findAll().size();

        // Delete the adminControls
        restAdminControlsMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminControls.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminControls> adminControlsList = adminControlsRepository.findAll();
        assertThat(adminControlsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
