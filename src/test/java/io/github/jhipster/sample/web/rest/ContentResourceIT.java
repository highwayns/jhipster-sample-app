package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Content;
import io.github.jhipster.sample.repository.ContentRepository;
import io.github.jhipster.sample.service.dto.ContentDTO;
import io.github.jhipster.sample.service.mapper.ContentMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ContentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContentResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_EN = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_ES = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_ES = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT_EN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_EN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_EN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_EN_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CONTENT_ES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_ES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_ES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_ES_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TITLE_RU = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_RU = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_ZH = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_ZH = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT_RU = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_RU = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_RU_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_RU_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CONTENT_ZH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_ZH = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_ZH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_ZH_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentMockMvc;

    private Content content;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Content createEntity(EntityManager em) {
        Content content = new Content()
            .url(DEFAULT_URL)
            .titleEn(DEFAULT_TITLE_EN)
            .titleEs(DEFAULT_TITLE_ES)
            .contentEn(DEFAULT_CONTENT_EN)
            .contentEnContentType(DEFAULT_CONTENT_EN_CONTENT_TYPE)
            .contentEs(DEFAULT_CONTENT_ES)
            .contentEsContentType(DEFAULT_CONTENT_ES_CONTENT_TYPE)
            .titleRu(DEFAULT_TITLE_RU)
            .titleZh(DEFAULT_TITLE_ZH)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentRuContentType(DEFAULT_CONTENT_RU_CONTENT_TYPE)
            .contentZh(DEFAULT_CONTENT_ZH)
            .contentZhContentType(DEFAULT_CONTENT_ZH_CONTENT_TYPE);
        return content;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Content createUpdatedEntity(EntityManager em) {
        Content content = new Content()
            .url(UPDATED_URL)
            .titleEn(UPDATED_TITLE_EN)
            .titleEs(UPDATED_TITLE_ES)
            .contentEn(UPDATED_CONTENT_EN)
            .contentEnContentType(UPDATED_CONTENT_EN_CONTENT_TYPE)
            .contentEs(UPDATED_CONTENT_ES)
            .contentEsContentType(UPDATED_CONTENT_ES_CONTENT_TYPE)
            .titleRu(UPDATED_TITLE_RU)
            .titleZh(UPDATED_TITLE_ZH)
            .contentRu(UPDATED_CONTENT_RU)
            .contentRuContentType(UPDATED_CONTENT_RU_CONTENT_TYPE)
            .contentZh(UPDATED_CONTENT_ZH)
            .contentZhContentType(UPDATED_CONTENT_ZH_CONTENT_TYPE);
        return content;
    }

    @BeforeEach
    public void initTest() {
        content = createEntity(em);
    }

    @Test
    @Transactional
    void createContent() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();
        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);
        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate + 1);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testContent.getTitleEn()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testContent.getTitleEs()).isEqualTo(DEFAULT_TITLE_ES);
        assertThat(testContent.getContentEn()).isEqualTo(DEFAULT_CONTENT_EN);
        assertThat(testContent.getContentEnContentType()).isEqualTo(DEFAULT_CONTENT_EN_CONTENT_TYPE);
        assertThat(testContent.getContentEs()).isEqualTo(DEFAULT_CONTENT_ES);
        assertThat(testContent.getContentEsContentType()).isEqualTo(DEFAULT_CONTENT_ES_CONTENT_TYPE);
        assertThat(testContent.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testContent.getTitleZh()).isEqualTo(DEFAULT_TITLE_ZH);
        assertThat(testContent.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testContent.getContentRuContentType()).isEqualTo(DEFAULT_CONTENT_RU_CONTENT_TYPE);
        assertThat(testContent.getContentZh()).isEqualTo(DEFAULT_CONTENT_ZH);
        assertThat(testContent.getContentZhContentType()).isEqualTo(DEFAULT_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createContentWithExistingId() throws Exception {
        // Create the Content with an existing ID
        content.setId(1L);
        ContentDTO contentDTO = contentMapper.toDto(content);

        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setUrl(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setTitleEn(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setTitleEs(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setTitleRu(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setTitleZh(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contentList
        restContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].titleEn").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleEs").value(hasItem(DEFAULT_TITLE_ES)))
            .andExpect(jsonPath("$.[*].contentEnContentType").value(hasItem(DEFAULT_CONTENT_EN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentEn").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_EN))))
            .andExpect(jsonPath("$.[*].contentEsContentType").value(hasItem(DEFAULT_CONTENT_ES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentEs").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_ES))))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleZh").value(hasItem(DEFAULT_TITLE_ZH)))
            .andExpect(jsonPath("$.[*].contentRuContentType").value(hasItem(DEFAULT_CONTENT_RU_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_RU))))
            .andExpect(jsonPath("$.[*].contentZhContentType").value(hasItem(DEFAULT_CONTENT_ZH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentZh").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_ZH))));
    }

    @Test
    @Transactional
    void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc
            .perform(get(ENTITY_API_URL_ID, content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.titleEn").value(DEFAULT_TITLE_EN))
            .andExpect(jsonPath("$.titleEs").value(DEFAULT_TITLE_ES))
            .andExpect(jsonPath("$.contentEnContentType").value(DEFAULT_CONTENT_EN_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentEn").value(Base64Utils.encodeToString(DEFAULT_CONTENT_EN)))
            .andExpect(jsonPath("$.contentEsContentType").value(DEFAULT_CONTENT_ES_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentEs").value(Base64Utils.encodeToString(DEFAULT_CONTENT_ES)))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleZh").value(DEFAULT_TITLE_ZH))
            .andExpect(jsonPath("$.contentRuContentType").value(DEFAULT_CONTENT_RU_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentRu").value(Base64Utils.encodeToString(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.contentZhContentType").value(DEFAULT_CONTENT_ZH_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentZh").value(Base64Utils.encodeToString(DEFAULT_CONTENT_ZH)));
    }

    @Test
    @Transactional
    void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content
        Content updatedContent = contentRepository.findById(content.getId()).get();
        // Disconnect from session so that the updates on updatedContent are not directly saved in db
        em.detach(updatedContent);
        updatedContent
            .url(UPDATED_URL)
            .titleEn(UPDATED_TITLE_EN)
            .titleEs(UPDATED_TITLE_ES)
            .contentEn(UPDATED_CONTENT_EN)
            .contentEnContentType(UPDATED_CONTENT_EN_CONTENT_TYPE)
            .contentEs(UPDATED_CONTENT_ES)
            .contentEsContentType(UPDATED_CONTENT_ES_CONTENT_TYPE)
            .titleRu(UPDATED_TITLE_RU)
            .titleZh(UPDATED_TITLE_ZH)
            .contentRu(UPDATED_CONTENT_RU)
            .contentRuContentType(UPDATED_CONTENT_RU_CONTENT_TYPE)
            .contentZh(UPDATED_CONTENT_ZH)
            .contentZhContentType(UPDATED_CONTENT_ZH_CONTENT_TYPE);
        ContentDTO contentDTO = contentMapper.toDto(updatedContent);

        restContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testContent.getTitleEn()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testContent.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testContent.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testContent.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testContent.getContentEs()).isEqualTo(UPDATED_CONTENT_ES);
        assertThat(testContent.getContentEsContentType()).isEqualTo(UPDATED_CONTENT_ES_CONTENT_TYPE);
        assertThat(testContent.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testContent.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testContent.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testContent.getContentRuContentType()).isEqualTo(UPDATED_CONTENT_RU_CONTENT_TYPE);
        assertThat(testContent.getContentZh()).isEqualTo(UPDATED_CONTENT_ZH);
        assertThat(testContent.getContentZhContentType()).isEqualTo(UPDATED_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();
        content.setId(count.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();
        content.setId(count.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();
        content.setId(count.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContentWithPatch() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content using partial update
        Content partialUpdatedContent = new Content();
        partialUpdatedContent.setId(content.getId());

        partialUpdatedContent
            .url(UPDATED_URL)
            .titleEs(UPDATED_TITLE_ES)
            .contentEn(UPDATED_CONTENT_EN)
            .contentEnContentType(UPDATED_CONTENT_EN_CONTENT_TYPE)
            .titleZh(UPDATED_TITLE_ZH)
            .contentRu(UPDATED_CONTENT_RU)
            .contentRuContentType(UPDATED_CONTENT_RU_CONTENT_TYPE);

        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContent))
            )
            .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testContent.getTitleEn()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testContent.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testContent.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testContent.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testContent.getContentEs()).isEqualTo(DEFAULT_CONTENT_ES);
        assertThat(testContent.getContentEsContentType()).isEqualTo(DEFAULT_CONTENT_ES_CONTENT_TYPE);
        assertThat(testContent.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testContent.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testContent.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testContent.getContentRuContentType()).isEqualTo(UPDATED_CONTENT_RU_CONTENT_TYPE);
        assertThat(testContent.getContentZh()).isEqualTo(DEFAULT_CONTENT_ZH);
        assertThat(testContent.getContentZhContentType()).isEqualTo(DEFAULT_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateContentWithPatch() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content using partial update
        Content partialUpdatedContent = new Content();
        partialUpdatedContent.setId(content.getId());

        partialUpdatedContent
            .url(UPDATED_URL)
            .titleEn(UPDATED_TITLE_EN)
            .titleEs(UPDATED_TITLE_ES)
            .contentEn(UPDATED_CONTENT_EN)
            .contentEnContentType(UPDATED_CONTENT_EN_CONTENT_TYPE)
            .contentEs(UPDATED_CONTENT_ES)
            .contentEsContentType(UPDATED_CONTENT_ES_CONTENT_TYPE)
            .titleRu(UPDATED_TITLE_RU)
            .titleZh(UPDATED_TITLE_ZH)
            .contentRu(UPDATED_CONTENT_RU)
            .contentRuContentType(UPDATED_CONTENT_RU_CONTENT_TYPE)
            .contentZh(UPDATED_CONTENT_ZH)
            .contentZhContentType(UPDATED_CONTENT_ZH_CONTENT_TYPE);

        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContent))
            )
            .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testContent.getTitleEn()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testContent.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testContent.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testContent.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testContent.getContentEs()).isEqualTo(UPDATED_CONTENT_ES);
        assertThat(testContent.getContentEsContentType()).isEqualTo(UPDATED_CONTENT_ES_CONTENT_TYPE);
        assertThat(testContent.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testContent.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testContent.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testContent.getContentRuContentType()).isEqualTo(UPDATED_CONTENT_RU_CONTENT_TYPE);
        assertThat(testContent.getContentZh()).isEqualTo(UPDATED_CONTENT_ZH);
        assertThat(testContent.getContentZhContentType()).isEqualTo(UPDATED_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();
        content.setId(count.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();
        content.setId(count.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();
        content.setId(count.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeDelete = contentRepository.findAll().size();

        // Delete the content
        restContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, content.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
