package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.ContentFiles;
import io.github.jhipster.sample.repository.ContentFilesRepository;
import io.github.jhipster.sample.service.dto.ContentFilesDTO;
import io.github.jhipster.sample.service.mapper.ContentFilesMapper;
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
 * Integration tests for the {@link ContentFilesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContentFilesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/content-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContentFilesRepository contentFilesRepository;

    @Autowired
    private ContentFilesMapper contentFilesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentFilesMockMvc;

    private ContentFiles contentFiles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentFiles createEntity(EntityManager em) {
        ContentFiles contentFiles = new ContentFiles()
            .fId(DEFAULT_F_ID)
            .ext(DEFAULT_EXT)
            .dir(DEFAULT_DIR)
            .url(DEFAULT_URL)
            .oldName(DEFAULT_OLD_NAME)
            .fieldName(DEFAULT_FIELD_NAME);
        return contentFiles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentFiles createUpdatedEntity(EntityManager em) {
        ContentFiles contentFiles = new ContentFiles()
            .fId(UPDATED_F_ID)
            .ext(UPDATED_EXT)
            .dir(UPDATED_DIR)
            .url(UPDATED_URL)
            .oldName(UPDATED_OLD_NAME)
            .fieldName(UPDATED_FIELD_NAME);
        return contentFiles;
    }

    @BeforeEach
    public void initTest() {
        contentFiles = createEntity(em);
    }

    @Test
    @Transactional
    void createContentFiles() throws Exception {
        int databaseSizeBeforeCreate = contentFilesRepository.findAll().size();
        // Create the ContentFiles
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);
        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeCreate + 1);
        ContentFiles testContentFiles = contentFilesList.get(contentFilesList.size() - 1);
        assertThat(testContentFiles.getfId()).isEqualTo(DEFAULT_F_ID);
        assertThat(testContentFiles.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testContentFiles.getDir()).isEqualTo(DEFAULT_DIR);
        assertThat(testContentFiles.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testContentFiles.getOldName()).isEqualTo(DEFAULT_OLD_NAME);
        assertThat(testContentFiles.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void createContentFilesWithExistingId() throws Exception {
        // Create the ContentFiles with an existing ID
        contentFiles.setId(1L);
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        int databaseSizeBeforeCreate = contentFilesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkfIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentFilesRepository.findAll().size();
        // set the field null
        contentFiles.setfId(null);

        // Create the ContentFiles, which fails.
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExtIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentFilesRepository.findAll().size();
        // set the field null
        contentFiles.setExt(null);

        // Create the ContentFiles, which fails.
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDirIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentFilesRepository.findAll().size();
        // set the field null
        contentFiles.setDir(null);

        // Create the ContentFiles, which fails.
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentFilesRepository.findAll().size();
        // set the field null
        contentFiles.setUrl(null);

        // Create the ContentFiles, which fails.
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentFilesRepository.findAll().size();
        // set the field null
        contentFiles.setOldName(null);

        // Create the ContentFiles, which fails.
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentFilesRepository.findAll().size();
        // set the field null
        contentFiles.setFieldName(null);

        // Create the ContentFiles, which fails.
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        restContentFilesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContentFiles() throws Exception {
        // Initialize the database
        contentFilesRepository.saveAndFlush(contentFiles);

        // Get all the contentFilesList
        restContentFilesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentFiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].fId").value(hasItem(DEFAULT_F_ID)))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT)))
            .andExpect(jsonPath("$.[*].dir").value(hasItem(DEFAULT_DIR)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].oldName").value(hasItem(DEFAULT_OLD_NAME)))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getContentFiles() throws Exception {
        // Initialize the database
        contentFilesRepository.saveAndFlush(contentFiles);

        // Get the contentFiles
        restContentFilesMockMvc
            .perform(get(ENTITY_API_URL_ID, contentFiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentFiles.getId().intValue()))
            .andExpect(jsonPath("$.fId").value(DEFAULT_F_ID))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT))
            .andExpect(jsonPath("$.dir").value(DEFAULT_DIR))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.oldName").value(DEFAULT_OLD_NAME))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingContentFiles() throws Exception {
        // Get the contentFiles
        restContentFilesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContentFiles() throws Exception {
        // Initialize the database
        contentFilesRepository.saveAndFlush(contentFiles);

        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();

        // Update the contentFiles
        ContentFiles updatedContentFiles = contentFilesRepository.findById(contentFiles.getId()).get();
        // Disconnect from session so that the updates on updatedContentFiles are not directly saved in db
        em.detach(updatedContentFiles);
        updatedContentFiles
            .fId(UPDATED_F_ID)
            .ext(UPDATED_EXT)
            .dir(UPDATED_DIR)
            .url(UPDATED_URL)
            .oldName(UPDATED_OLD_NAME)
            .fieldName(UPDATED_FIELD_NAME);
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(updatedContentFiles);

        restContentFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contentFilesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
        ContentFiles testContentFiles = contentFilesList.get(contentFilesList.size() - 1);
        assertThat(testContentFiles.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testContentFiles.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testContentFiles.getDir()).isEqualTo(UPDATED_DIR);
        assertThat(testContentFiles.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testContentFiles.getOldName()).isEqualTo(UPDATED_OLD_NAME);
        assertThat(testContentFiles.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void putNonExistingContentFiles() throws Exception {
        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();
        contentFiles.setId(count.incrementAndGet());

        // Create the ContentFiles
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contentFilesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContentFiles() throws Exception {
        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();
        contentFiles.setId(count.incrementAndGet());

        // Create the ContentFiles
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContentFiles() throws Exception {
        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();
        contentFiles.setId(count.incrementAndGet());

        // Create the ContentFiles
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentFilesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContentFilesWithPatch() throws Exception {
        // Initialize the database
        contentFilesRepository.saveAndFlush(contentFiles);

        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();

        // Update the contentFiles using partial update
        ContentFiles partialUpdatedContentFiles = new ContentFiles();
        partialUpdatedContentFiles.setId(contentFiles.getId());

        partialUpdatedContentFiles.ext(UPDATED_EXT);

        restContentFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContentFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContentFiles))
            )
            .andExpect(status().isOk());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
        ContentFiles testContentFiles = contentFilesList.get(contentFilesList.size() - 1);
        assertThat(testContentFiles.getfId()).isEqualTo(DEFAULT_F_ID);
        assertThat(testContentFiles.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testContentFiles.getDir()).isEqualTo(DEFAULT_DIR);
        assertThat(testContentFiles.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testContentFiles.getOldName()).isEqualTo(DEFAULT_OLD_NAME);
        assertThat(testContentFiles.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
    }

    @Test
    @Transactional
    void fullUpdateContentFilesWithPatch() throws Exception {
        // Initialize the database
        contentFilesRepository.saveAndFlush(contentFiles);

        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();

        // Update the contentFiles using partial update
        ContentFiles partialUpdatedContentFiles = new ContentFiles();
        partialUpdatedContentFiles.setId(contentFiles.getId());

        partialUpdatedContentFiles
            .fId(UPDATED_F_ID)
            .ext(UPDATED_EXT)
            .dir(UPDATED_DIR)
            .url(UPDATED_URL)
            .oldName(UPDATED_OLD_NAME)
            .fieldName(UPDATED_FIELD_NAME);

        restContentFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContentFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContentFiles))
            )
            .andExpect(status().isOk());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
        ContentFiles testContentFiles = contentFilesList.get(contentFilesList.size() - 1);
        assertThat(testContentFiles.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testContentFiles.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testContentFiles.getDir()).isEqualTo(UPDATED_DIR);
        assertThat(testContentFiles.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testContentFiles.getOldName()).isEqualTo(UPDATED_OLD_NAME);
        assertThat(testContentFiles.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingContentFiles() throws Exception {
        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();
        contentFiles.setId(count.incrementAndGet());

        // Create the ContentFiles
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contentFilesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContentFiles() throws Exception {
        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();
        contentFiles.setId(count.incrementAndGet());

        // Create the ContentFiles
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContentFiles() throws Exception {
        int databaseSizeBeforeUpdate = contentFilesRepository.findAll().size();
        contentFiles.setId(count.incrementAndGet());

        // Create the ContentFiles
        ContentFilesDTO contentFilesDTO = contentFilesMapper.toDto(contentFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentFilesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contentFilesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContentFiles in the database
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContentFiles() throws Exception {
        // Initialize the database
        contentFilesRepository.saveAndFlush(contentFiles);

        int databaseSizeBeforeDelete = contentFilesRepository.findAll().size();

        // Delete the contentFiles
        restContentFilesMockMvc
            .perform(delete(ENTITY_API_URL_ID, contentFiles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentFiles> contentFilesList = contentFilesRepository.findAll();
        assertThat(contentFilesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
