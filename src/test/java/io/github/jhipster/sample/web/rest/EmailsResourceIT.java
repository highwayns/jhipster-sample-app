package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Emails;
import io.github.jhipster.sample.repository.EmailsRepository;
import io.github.jhipster.sample.service.dto.EmailsDTO;
import io.github.jhipster.sample.service.mapper.EmailsMapper;
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
 * Integration tests for the {@link EmailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailsResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmailsRepository emailsRepository;

    @Autowired
    private EmailsMapper emailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailsMockMvc;

    private Emails emails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emails createEntity(EntityManager em) {
        Emails emails = new Emails()
            .key(DEFAULT_KEY)
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
        return emails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emails createUpdatedEntity(EntityManager em) {
        Emails emails = new Emails()
            .key(UPDATED_KEY)
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
        return emails;
    }

    @BeforeEach
    public void initTest() {
        emails = createEntity(em);
    }

    @Test
    @Transactional
    void createEmails() throws Exception {
        int databaseSizeBeforeCreate = emailsRepository.findAll().size();
        // Create the Emails
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);
        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isCreated());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeCreate + 1);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testEmails.getTitleEn()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testEmails.getTitleEs()).isEqualTo(DEFAULT_TITLE_ES);
        assertThat(testEmails.getContentEn()).isEqualTo(DEFAULT_CONTENT_EN);
        assertThat(testEmails.getContentEnContentType()).isEqualTo(DEFAULT_CONTENT_EN_CONTENT_TYPE);
        assertThat(testEmails.getContentEs()).isEqualTo(DEFAULT_CONTENT_ES);
        assertThat(testEmails.getContentEsContentType()).isEqualTo(DEFAULT_CONTENT_ES_CONTENT_TYPE);
        assertThat(testEmails.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testEmails.getTitleZh()).isEqualTo(DEFAULT_TITLE_ZH);
        assertThat(testEmails.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testEmails.getContentRuContentType()).isEqualTo(DEFAULT_CONTENT_RU_CONTENT_TYPE);
        assertThat(testEmails.getContentZh()).isEqualTo(DEFAULT_CONTENT_ZH);
        assertThat(testEmails.getContentZhContentType()).isEqualTo(DEFAULT_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createEmailsWithExistingId() throws Exception {
        // Create the Emails with an existing ID
        emails.setId(1L);
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        int databaseSizeBeforeCreate = emailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailsRepository.findAll().size();
        // set the field null
        emails.setKey(null);

        // Create the Emails, which fails.
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isBadRequest());

        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailsRepository.findAll().size();
        // set the field null
        emails.setTitleEn(null);

        // Create the Emails, which fails.
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isBadRequest());

        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailsRepository.findAll().size();
        // set the field null
        emails.setTitleEs(null);

        // Create the Emails, which fails.
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isBadRequest());

        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailsRepository.findAll().size();
        // set the field null
        emails.setTitleRu(null);

        // Create the Emails, which fails.
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isBadRequest());

        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailsRepository.findAll().size();
        // set the field null
        emails.setTitleZh(null);

        // Create the Emails, which fails.
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isBadRequest());

        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emails.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
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
    void getEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get the emails
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL_ID, emails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emails.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
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
    void getNonExistingEmails() throws Exception {
        // Get the emails
        restEmailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Update the emails
        Emails updatedEmails = emailsRepository.findById(emails.getId()).get();
        // Disconnect from session so that the updates on updatedEmails are not directly saved in db
        em.detach(updatedEmails);
        updatedEmails
            .key(UPDATED_KEY)
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
        EmailsDTO emailsDTO = emailsMapper.toDto(updatedEmails);

        restEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testEmails.getTitleEn()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testEmails.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testEmails.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testEmails.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testEmails.getContentEs()).isEqualTo(UPDATED_CONTENT_ES);
        assertThat(testEmails.getContentEsContentType()).isEqualTo(UPDATED_CONTENT_ES_CONTENT_TYPE);
        assertThat(testEmails.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testEmails.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testEmails.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testEmails.getContentRuContentType()).isEqualTo(UPDATED_CONTENT_RU_CONTENT_TYPE);
        assertThat(testEmails.getContentZh()).isEqualTo(UPDATED_CONTENT_ZH);
        assertThat(testEmails.getContentZhContentType()).isEqualTo(UPDATED_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // Create the Emails
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // Create the Emails
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // Create the Emails
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailsWithPatch() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Update the emails using partial update
        Emails partialUpdatedEmails = new Emails();
        partialUpdatedEmails.setId(emails.getId());

        partialUpdatedEmails.key(UPDATED_KEY).titleEs(UPDATED_TITLE_ES).titleZh(UPDATED_TITLE_ZH);

        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmails))
            )
            .andExpect(status().isOk());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testEmails.getTitleEn()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testEmails.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testEmails.getContentEn()).isEqualTo(DEFAULT_CONTENT_EN);
        assertThat(testEmails.getContentEnContentType()).isEqualTo(DEFAULT_CONTENT_EN_CONTENT_TYPE);
        assertThat(testEmails.getContentEs()).isEqualTo(DEFAULT_CONTENT_ES);
        assertThat(testEmails.getContentEsContentType()).isEqualTo(DEFAULT_CONTENT_ES_CONTENT_TYPE);
        assertThat(testEmails.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testEmails.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testEmails.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testEmails.getContentRuContentType()).isEqualTo(DEFAULT_CONTENT_RU_CONTENT_TYPE);
        assertThat(testEmails.getContentZh()).isEqualTo(DEFAULT_CONTENT_ZH);
        assertThat(testEmails.getContentZhContentType()).isEqualTo(DEFAULT_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEmailsWithPatch() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Update the emails using partial update
        Emails partialUpdatedEmails = new Emails();
        partialUpdatedEmails.setId(emails.getId());

        partialUpdatedEmails
            .key(UPDATED_KEY)
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

        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmails))
            )
            .andExpect(status().isOk());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testEmails.getTitleEn()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testEmails.getTitleEs()).isEqualTo(UPDATED_TITLE_ES);
        assertThat(testEmails.getContentEn()).isEqualTo(UPDATED_CONTENT_EN);
        assertThat(testEmails.getContentEnContentType()).isEqualTo(UPDATED_CONTENT_EN_CONTENT_TYPE);
        assertThat(testEmails.getContentEs()).isEqualTo(UPDATED_CONTENT_ES);
        assertThat(testEmails.getContentEsContentType()).isEqualTo(UPDATED_CONTENT_ES_CONTENT_TYPE);
        assertThat(testEmails.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testEmails.getTitleZh()).isEqualTo(UPDATED_TITLE_ZH);
        assertThat(testEmails.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testEmails.getContentRuContentType()).isEqualTo(UPDATED_CONTENT_RU_CONTENT_TYPE);
        assertThat(testEmails.getContentZh()).isEqualTo(UPDATED_CONTENT_ZH);
        assertThat(testEmails.getContentZhContentType()).isEqualTo(UPDATED_CONTENT_ZH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // Create the Emails
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // Create the Emails
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // Create the Emails
        EmailsDTO emailsDTO = emailsMapper.toDto(emails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeDelete = emailsRepository.findAll().size();

        // Delete the emails
        restEmailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, emails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
