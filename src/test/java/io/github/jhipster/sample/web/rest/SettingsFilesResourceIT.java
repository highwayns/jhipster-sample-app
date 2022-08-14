package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.SettingsFiles;
import io.github.jhipster.sample.repository.SettingsFilesRepository;
import io.github.jhipster.sample.service.dto.SettingsFilesDTO;
import io.github.jhipster.sample.service.mapper.SettingsFilesMapper;
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
 * Integration tests for the {@link SettingsFilesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SettingsFilesResourceIT {

    private static final Integer DEFAULT_F_ID = 1;
    private static final Integer UPDATED_F_ID = 2;

    private static final String DEFAULT_EXT = "AAAA";
    private static final String UPDATED_EXT = "BBBB";

    private static final String DEFAULT_DIR = "AAAAAAAAAA";
    private static final String UPDATED_DIR = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_OLD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OLD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/settings-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SettingsFilesRepository settingsFilesRepository;

    @Autowired
    private SettingsFilesMapper settingsFilesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSettingsFilesMockMvc;

    private SettingsFiles settingsFiles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingsFiles createEntity(EntityManager em) {
        SettingsFiles settingsFiles = new SettingsFiles()
            .fId(DEFAULT_F_ID)
            .ext(DEFAULT_EXT)
            .dir(DEFAULT_DIR)
            .url(DEFAULT_URL)
            .oldName(DEFAULT_OLD_NAME)
            .fieldName(DEFAULT_FIELD_NAME);
        return settingsFiles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingsFiles createUpdatedEntity(EntityManager em) {
        SettingsFiles settingsFiles = new SettingsFiles()
            .fId(UPDATED_F_ID)
            .ext(UPDATED_EXT)
            .dir(UPDATED_DIR)
            .url(UPDATED_URL)
            .oldName(UPDATED_OLD_NAME)
            .fieldName(UPDATED_FIELD_NAME);
        return settingsFiles;
    }

    @BeforeEach
    public void initTest() {
        settingsFiles = createEntity(em);
    }

    @Test
    @Transactional
    void createSettingsFiles() throws Exception {
        int databaseSizeBeforeCreate = settingsFilesRepository.findAll().size();
        // Create the SettingsFiles
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);
        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeCreate + 1);
        SettingsFiles testSettingsFiles = settingsFilesList.get(settingsFilesList.size() - 1);
        assertThat(testSettingsFiles.getfId()).isEqualTo(DEFAULT_F_ID);
        assertThat(testSettingsFiles.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testSettingsFiles.getDir()).isEqualTo(DEFAULT_DIR);
        assertThat(testSettingsFiles.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSettingsFiles.getOldName()).isEqualTo(DEFAULT_OLD_NAME);
        assertThat(testSettingsFiles.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void createSettingsFilesWithExistingId() throws Exception {
        // Create the SettingsFiles with an existing ID
        settingsFiles.setId(1L);
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        int databaseSizeBeforeCreate = settingsFilesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkfIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingsFilesRepository.findAll().size();
        // set the field null
        settingsFiles.setfId(null);

        // Create the SettingsFiles, which fails.
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExtIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingsFilesRepository.findAll().size();
        // set the field null
        settingsFiles.setExt(null);

        // Create the SettingsFiles, which fails.
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDirIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingsFilesRepository.findAll().size();
        // set the field null
        settingsFiles.setDir(null);

        // Create the SettingsFiles, which fails.
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingsFilesRepository.findAll().size();
        // set the field null
        settingsFiles.setUrl(null);

        // Create the SettingsFiles, which fails.
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingsFilesRepository.findAll().size();
        // set the field null
        settingsFiles.setOldName(null);

        // Create the SettingsFiles, which fails.
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingsFilesRepository.findAll().size();
        // set the field null
        settingsFiles.setFieldName(null);

        // Create the SettingsFiles, which fails.
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        restSettingsFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSettingsFiles() throws Exception {
        // Initialize the database
        settingsFilesRepository.saveAndFlush(settingsFiles);

        // Get all the settingsFilesList
        restSettingsFilesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingsFiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].fId").value(hasItem(DEFAULT_F_ID)))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT)))
            .andExpect(jsonPath("$.[*].dir").value(hasItem(DEFAULT_DIR)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].oldName").value(hasItem(DEFAULT_OLD_NAME)))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getSettingsFiles() throws Exception {
        // Initialize the database
        settingsFilesRepository.saveAndFlush(settingsFiles);

        // Get the settingsFiles
        restSettingsFilesMockMvc
            .perform(get(ENTITY_API_URL_ID, settingsFiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(settingsFiles.getId().intValue()))
            .andExpect(jsonPath("$.fId").value(DEFAULT_F_ID))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT))
            .andExpect(jsonPath("$.dir").value(DEFAULT_DIR))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.oldName").value(DEFAULT_OLD_NAME))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSettingsFiles() throws Exception {
        // Get the settingsFiles
        restSettingsFilesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSettingsFiles() throws Exception {
        // Initialize the database
        settingsFilesRepository.saveAndFlush(settingsFiles);

        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();

        // Update the settingsFiles
        SettingsFiles updatedSettingsFiles = settingsFilesRepository.findById(settingsFiles.getId()).get();
        // Disconnect from session so that the updates on updatedSettingsFiles are not directly saved in db
        em.detach(updatedSettingsFiles);
        updatedSettingsFiles
            .fId(UPDATED_F_ID)
            .ext(UPDATED_EXT)
            .dir(UPDATED_DIR)
            .url(UPDATED_URL)
            .oldName(UPDATED_OLD_NAME)
            .fieldName(UPDATED_FIELD_NAME);
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(updatedSettingsFiles);

        restSettingsFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingsFilesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isOk());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
        SettingsFiles testSettingsFiles = settingsFilesList.get(settingsFilesList.size() - 1);
        assertThat(testSettingsFiles.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testSettingsFiles.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testSettingsFiles.getDir()).isEqualTo(UPDATED_DIR);
        assertThat(testSettingsFiles.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSettingsFiles.getOldName()).isEqualTo(UPDATED_OLD_NAME);
        assertThat(testSettingsFiles.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSettingsFiles() throws Exception {
        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();
        settingsFiles.setId(count.incrementAndGet());

        // Create the SettingsFiles
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingsFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingsFilesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSettingsFiles() throws Exception {
        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();
        settingsFiles.setId(count.incrementAndGet());

        // Create the SettingsFiles
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSettingsFiles() throws Exception {
        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();
        settingsFiles.setId(count.incrementAndGet());

        // Create the SettingsFiles
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsFilesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSettingsFilesWithPatch() throws Exception {
        // Initialize the database
        settingsFilesRepository.saveAndFlush(settingsFiles);

        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();

        // Update the settingsFiles using partial update
        SettingsFiles partialUpdatedSettingsFiles = new SettingsFiles();
        partialUpdatedSettingsFiles.setId(settingsFiles.getId());

        partialUpdatedSettingsFiles.fId(UPDATED_F_ID).ext(UPDATED_EXT).url(UPDATED_URL).oldName(UPDATED_OLD_NAME);

        restSettingsFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingsFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingsFiles))
            )
            .andExpect(status().isOk());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
        SettingsFiles testSettingsFiles = settingsFilesList.get(settingsFilesList.size() - 1);
        assertThat(testSettingsFiles.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testSettingsFiles.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testSettingsFiles.getDir()).isEqualTo(DEFAULT_DIR);
        assertThat(testSettingsFiles.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSettingsFiles.getOldName()).isEqualTo(UPDATED_OLD_NAME);
        assertThat(testSettingsFiles.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSettingsFilesWithPatch() throws Exception {
        // Initialize the database
        settingsFilesRepository.saveAndFlush(settingsFiles);

        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();

        // Update the settingsFiles using partial update
        SettingsFiles partialUpdatedSettingsFiles = new SettingsFiles();
        partialUpdatedSettingsFiles.setId(settingsFiles.getId());

        partialUpdatedSettingsFiles
            .fId(UPDATED_F_ID)
            .ext(UPDATED_EXT)
            .dir(UPDATED_DIR)
            .url(UPDATED_URL)
            .oldName(UPDATED_OLD_NAME)
            .fieldName(UPDATED_FIELD_NAME);

        restSettingsFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingsFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingsFiles))
            )
            .andExpect(status().isOk());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
        SettingsFiles testSettingsFiles = settingsFilesList.get(settingsFilesList.size() - 1);
        assertThat(testSettingsFiles.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testSettingsFiles.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testSettingsFiles.getDir()).isEqualTo(UPDATED_DIR);
        assertThat(testSettingsFiles.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSettingsFiles.getOldName()).isEqualTo(UPDATED_OLD_NAME);
        assertThat(testSettingsFiles.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSettingsFiles() throws Exception {
        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();
        settingsFiles.setId(count.incrementAndGet());

        // Create the SettingsFiles
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingsFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, settingsFilesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSettingsFiles() throws Exception {
        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();
        settingsFiles.setId(count.incrementAndGet());

        // Create the SettingsFiles
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSettingsFiles() throws Exception {
        int databaseSizeBeforeUpdate = settingsFilesRepository.findAll().size();
        settingsFiles.setId(count.incrementAndGet());

        // Create the SettingsFiles
        SettingsFilesDTO settingsFilesDTO = settingsFilesMapper.toDto(settingsFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingsFilesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingsFilesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SettingsFiles in the database
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSettingsFiles() throws Exception {
        // Initialize the database
        settingsFilesRepository.saveAndFlush(settingsFiles);

        int databaseSizeBeforeDelete = settingsFilesRepository.findAll().size();

        // Delete the settingsFiles
        restSettingsFilesMockMvc
            .perform(delete(ENTITY_API_URL_ID, settingsFiles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SettingsFiles> settingsFilesList = settingsFilesRepository.findAll();
        assertThat(settingsFilesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
