package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.News;
import io.github.jhipster.sample.repository.NewsRepository;
import io.github.jhipster.sample.service.dto.NewsDTO;
import io.github.jhipster.sample.service.mapper.NewsMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link NewsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewsResourceIT {

    private static final String DEFAULT_TITLE_EN = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_ES = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_ES = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/news";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsMockMvc;

    private News news;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createEntity(EntityManager em) {
        News news = new News()
            .titleEn(DEFAULT_TITLE_EN)
            .titleEs(DEFAULT_TITLE_ES)
            .date(DEFAULT_DATE)
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
        return news;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createUpdatedEntity(EntityManager em) {
        News news = new News()
            .titleEn(UPDATED_TITLE_EN)
            .titleEs(UPDATED_TITLE_ES)
            .date(UPDATED_DATE)
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
        return news;
    }

    @BeforeEach
    public void initTest() {
        news = createEntity(em);
    }

    @Test
    @Transactional
    void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();
        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);
        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleEn()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testNews.getTitleEs()).isEqualTo(DEFAULT_TITLE_ES);
        assertThat(testNews.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNews.getContentEn()).isEqualTo(DEFAULT_CONTENT_EN);
        assertThat(testNews.getContentEnContentType()).isEqualTo(DEFAULT_CONTENT_EN_CONTENT_TYPE);
        assertThat(testNews.getContentEs()).isEqualTo(DEFAULT_CONTENT_ES);
        assertThat(testNews.getContentEsContentType()).isEqualTo(DEFAULT_CONTENT_ES_CONTENT_TYPE);
        assertThat(testNews.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testNews.getTitleZh()).isEqualTo(DEFAULT_TITLE_ZH);
        assertThat(testNews.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testNews.getContentRuContentType()).isEqualTo(DEFAULT_CONTENT_RU_CONTENT_TYPE);
        assertThat(testNews.getContentZh()).isEqualTo(DEFAULT_CONTENT_ZH);
        assertThat(testNews.getContentZhContentType()).isEqualTo(DEFAULT_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createNewsWithExistingId() throws Exception {
        // Create the News with an existing ID
        news.setId(1L);
        NewsDTO newsDTO = newsMapper.toDto(news);

        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setTitleEn(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setTitleEs(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setDate(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setTitleRu(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setTitleZh(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList
        restNewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleEn").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleEs").value(hasItem(DEFAULT_TITLE_ES)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
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
    void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc
            .perform(get(ENTITY_API_URL_ID, news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.titleEn").value(DEFAULT_TITLE_EN))
            .andExpect(jsonPath("$.titleEs").value(DEFAULT_TITLE_ES))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
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
    void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = newsRepository.findById(news.getId()).get();
        // Disconnect from session so that the updates on updatedNews are not directly saved in db
        em.detach(updatedNews);
        updatedNews
            .titleEn(UPDATED_TITLE_EN)
            .titleEs(UPDATED_TITLE_ES)
            .date(UPDATED_DATE)
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
        NewsDTO newsDTO = newsMapper.toDto(updatedNews);

        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleEn()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testNews.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testNews.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNews.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testNews.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testNews.getContentEs()).isEqualTo(UPDATED_CONTENT_ES);
        assertThat(testNews.getContentEsContentType()).isEqualTo(UPDATED_CONTENT_ES_CONTENT_TYPE);
        assertThat(testNews.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testNews.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testNews.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testNews.getContentRuContentType()).isEqualTo(UPDATED_CONTENT_RU_CONTENT_TYPE);
        assertThat(testNews.getContentZh()).isEqualTo(UPDATED_CONTENT_ZH);
        assertThat(testNews.getContentZhContentType()).isEqualTo(UPDATED_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewsWithPatch() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news using partial update
        News partialUpdatedNews = new News();
        partialUpdatedNews.setId(news.getId());

        partialUpdatedNews
            .titleEn(UPDATED_TITLE_EN)
            .date(UPDATED_DATE)
            .contentEn(UPDATED_CONTENT_EN)
            .contentEnContentType(UPDATED_CONTENT_EN_CONTENT_TYPE)
            .contentEs(UPDATED_CONTENT_ES)
            .contentEsContentType(UPDATED_CONTENT_ES_CONTENT_TYPE)
            .titleRu(UPDATED_TITLE_RU)
            .titleZh(UPDATED_TITLE_ZH);

        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNews))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleEn()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testNews.getTitleEs()).isEqualTo(DEFAULT_TITLE_ES);
        assertThat(testNews.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNews.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testNews.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testNews.getContentEs()).isEqualTo(UPDATED_CONTENT_ES);
        assertThat(testNews.getContentEsContentType()).isEqualTo(UPDATED_CONTENT_ES_CONTENT_TYPE);
        assertThat(testNews.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testNews.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testNews.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testNews.getContentRuContentType()).isEqualTo(DEFAULT_CONTENT_RU_CONTENT_TYPE);
        assertThat(testNews.getContentZh()).isEqualTo(DEFAULT_CONTENT_ZH);
        assertThat(testNews.getContentZhContentType()).isEqualTo(DEFAULT_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateNewsWithPatch() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news using partial update
        News partialUpdatedNews = new News();
        partialUpdatedNews.setId(news.getId());

        partialUpdatedNews
            .titleEn(UPDATED_TITLE_EN)
            .titleEs(UPDATED_TITLE_ES)
            .date(UPDATED_DATE)
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

        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNews))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitleEn()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testNews.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testNews.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNews.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testNews.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testNews.getContentEs()).isEqualTo(UPDATED_CONTENT_ES);
        assertThat(testNews.getContentEsContentType()).isEqualTo(UPDATED_CONTENT_ES_CONTENT_TYPE);
        assertThat(testNews.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testNews.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testNews.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testNews.getContentRuContentType()).isEqualTo(UPDATED_CONTENT_RU_CONTENT_TYPE);
        assertThat(testNews.getContentZh()).isEqualTo(UPDATED_CONTENT_ZH);
        assertThat(testNews.getContentZhContentType()).isEqualTo(UPDATED_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, newsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Delete the news
        restNewsMockMvc
            .perform(delete(ENTITY_API_URL_ID, news.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
