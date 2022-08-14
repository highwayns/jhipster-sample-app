package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Lang;
import io.github.jhipster.sample.repository.LangRepository;
import io.github.jhipster.sample.service.dto.LangDTO;
import io.github.jhipster.sample.service.mapper.LangMapper;
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
 * Integration tests for the {@link LangResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LangResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ESP = "AAAAAAAAAA";
    private static final String UPDATED_ESP = "BBBBBBBBBB";

    private static final String DEFAULT_ENG = "AAAAAAAAAA";
    private static final String UPDATED_ENG = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER = "BBBBBBBBBB";

    private static final Integer DEFAULT_P_ID = 1;
    private static final Integer UPDATED_P_ID = 2;

    private static final String DEFAULT_ZH = "AAAAAAAAAA";
    private static final String UPDATED_ZH = "BBBBBBBBBB";

    private static final String DEFAULT_RU = "AAAAAAAAAA";
    private static final String UPDATED_RU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/langs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LangRepository langRepository;

    @Autowired
    private LangMapper langMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLangMockMvc;

    private Lang lang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lang createEntity(EntityManager em) {
        Lang lang = new Lang()
            .key(DEFAULT_KEY)
            .esp(DEFAULT_ESP)
            .eng(DEFAULT_ENG)
            .order(DEFAULT_ORDER)
            .pId(DEFAULT_P_ID)
            .zh(DEFAULT_ZH)
            .ru(DEFAULT_RU);
        return lang;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lang createUpdatedEntity(EntityManager em) {
        Lang lang = new Lang()
            .key(UPDATED_KEY)
            .esp(UPDATED_ESP)
            .eng(UPDATED_ENG)
            .order(UPDATED_ORDER)
            .pId(UPDATED_P_ID)
            .zh(UPDATED_ZH)
            .ru(UPDATED_RU);
        return lang;
    }

    @BeforeEach
    public void initTest() {
        lang = createEntity(em);
    }

    @Test
    @Transactional
    void createLang() throws Exception {
        int databaseSizeBeforeCreate = langRepository.findAll().size();
        // Create the Lang
        LangDTO langDTO = langMapper.toDto(lang);
        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isCreated());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeCreate + 1);
        Lang testLang = langList.get(langList.size() - 1);
        assertThat(testLang.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testLang.getEsp()).isEqualTo(DEFAULT_ESP);
        assertThat(testLang.getEng()).isEqualTo(DEFAULT_ENG);
        assertThat(testLang.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testLang.getpId()).isEqualTo(DEFAULT_P_ID);
        assertThat(testLang.getZh()).isEqualTo(DEFAULT_ZH);
        assertThat(testLang.getRu()).isEqualTo(DEFAULT_RU);
    }

    @Test
    @Transactional
    void createLangWithExistingId() throws Exception {
        // Create the Lang with an existing ID
        lang.setId(1L);
        LangDTO langDTO = langMapper.toDto(lang);

        int databaseSizeBeforeCreate = langRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setKey(null);

        // Create the Lang, which fails.
        LangDTO langDTO = langMapper.toDto(lang);

        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEspIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setEsp(null);

        // Create the Lang, which fails.
        LangDTO langDTO = langMapper.toDto(lang);

        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEngIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setEng(null);

        // Create the Lang, which fails.
        LangDTO langDTO = langMapper.toDto(lang);

        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setOrder(null);

        // Create the Lang, which fails.
        LangDTO langDTO = langMapper.toDto(lang);

        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setpId(null);

        // Create the Lang, which fails.
        LangDTO langDTO = langMapper.toDto(lang);

        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setZh(null);

        // Create the Lang, which fails.
        LangDTO langDTO = langMapper.toDto(lang);

        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = langRepository.findAll().size();
        // set the field null
        lang.setRu(null);

        // Create the Lang, which fails.
        LangDTO langDTO = langMapper.toDto(lang);

        restLangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isBadRequest());

        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLangs() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        // Get all the langList
        restLangMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lang.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].esp").value(hasItem(DEFAULT_ESP)))
            .andExpect(jsonPath("$.[*].eng").value(hasItem(DEFAULT_ENG)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID)))
            .andExpect(jsonPath("$.[*].zh").value(hasItem(DEFAULT_ZH)))
            .andExpect(jsonPath("$.[*].ru").value(hasItem(DEFAULT_RU)));
    }

    @Test
    @Transactional
    void getLang() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        // Get the lang
        restLangMockMvc
            .perform(get(ENTITY_API_URL_ID, lang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lang.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.esp").value(DEFAULT_ESP))
            .andExpect(jsonPath("$.eng").value(DEFAULT_ENG))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID))
            .andExpect(jsonPath("$.zh").value(DEFAULT_ZH))
            .andExpect(jsonPath("$.ru").value(DEFAULT_RU));
    }

    @Test
    @Transactional
    void getNonExistingLang() throws Exception {
        // Get the lang
        restLangMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLang() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        int databaseSizeBeforeUpdate = langRepository.findAll().size();

        // Update the lang
        Lang updatedLang = langRepository.findById(lang.getId()).get();
        // Disconnect from session so that the updates on updatedLang are not directly saved in db
        em.detach(updatedLang);
        updatedLang.key(UPDATED_KEY).esp(UPDATED_ESP).eng(UPDATED_ENG).order(UPDATED_ORDER).pId(UPDATED_P_ID).zh(UPDATED_ZH).ru(UPDATED_RU);
        LangDTO langDTO = langMapper.toDto(updatedLang);

        restLangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, langDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(langDTO))
            )
            .andExpect(status().isOk());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
        Lang testLang = langList.get(langList.size() - 1);
        assertThat(testLang.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testLang.getEsp()).isEqualTo(UPDATED_ESP);
        assertThat(testLang.getEng()).isEqualTo(UPDATED_ENG);
        assertThat(testLang.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testLang.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testLang.getZh()).isEqualTo(UPDATED_ZH);
        assertThat(testLang.getRu()).isEqualTo(UPDATED_RU);
    }

    @Test
    @Transactional
    void putNonExistingLang() throws Exception {
        int databaseSizeBeforeUpdate = langRepository.findAll().size();
        lang.setId(count.incrementAndGet());

        // Create the Lang
        LangDTO langDTO = langMapper.toDto(lang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, langDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(langDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLang() throws Exception {
        int databaseSizeBeforeUpdate = langRepository.findAll().size();
        lang.setId(count.incrementAndGet());

        // Create the Lang
        LangDTO langDTO = langMapper.toDto(lang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(langDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLang() throws Exception {
        int databaseSizeBeforeUpdate = langRepository.findAll().size();
        lang.setId(count.incrementAndGet());

        // Create the Lang
        LangDTO langDTO = langMapper.toDto(lang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLangWithPatch() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        int databaseSizeBeforeUpdate = langRepository.findAll().size();

        // Update the lang using partial update
        Lang partialUpdatedLang = new Lang();
        partialUpdatedLang.setId(lang.getId());

        partialUpdatedLang.esp(UPDATED_ESP).eng(UPDATED_ENG).pId(UPDATED_P_ID).zh(UPDATED_ZH).ru(UPDATED_RU);

        restLangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLang))
            )
            .andExpect(status().isOk());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
        Lang testLang = langList.get(langList.size() - 1);
        assertThat(testLang.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testLang.getEsp()).isEqualTo(UPDATED_ESP);
        assertThat(testLang.getEng()).isEqualTo(UPDATED_ENG);
        assertThat(testLang.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testLang.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testLang.getZh()).isEqualTo(UPDATED_ZH);
        assertThat(testLang.getRu()).isEqualTo(UPDATED_RU);
    }

    @Test
    @Transactional
    void fullUpdateLangWithPatch() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        int databaseSizeBeforeUpdate = langRepository.findAll().size();

        // Update the lang using partial update
        Lang partialUpdatedLang = new Lang();
        partialUpdatedLang.setId(lang.getId());

        partialUpdatedLang
            .key(UPDATED_KEY)
            .esp(UPDATED_ESP)
            .eng(UPDATED_ENG)
            .order(UPDATED_ORDER)
            .pId(UPDATED_P_ID)
            .zh(UPDATED_ZH)
            .ru(UPDATED_RU);

        restLangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLang))
            )
            .andExpect(status().isOk());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
        Lang testLang = langList.get(langList.size() - 1);
        assertThat(testLang.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testLang.getEsp()).isEqualTo(UPDATED_ESP);
        assertThat(testLang.getEng()).isEqualTo(UPDATED_ENG);
        assertThat(testLang.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testLang.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testLang.getZh()).isEqualTo(UPDATED_ZH);
        assertThat(testLang.getRu()).isEqualTo(UPDATED_RU);
    }

    @Test
    @Transactional
    void patchNonExistingLang() throws Exception {
        int databaseSizeBeforeUpdate = langRepository.findAll().size();
        lang.setId(count.incrementAndGet());

        // Create the Lang
        LangDTO langDTO = langMapper.toDto(lang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, langDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(langDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLang() throws Exception {
        int databaseSizeBeforeUpdate = langRepository.findAll().size();
        lang.setId(count.incrementAndGet());

        // Create the Lang
        LangDTO langDTO = langMapper.toDto(lang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(langDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLang() throws Exception {
        int databaseSizeBeforeUpdate = langRepository.findAll().size();
        lang.setId(count.incrementAndGet());

        // Create the Lang
        LangDTO langDTO = langMapper.toDto(lang);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(langDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lang in the database
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLang() throws Exception {
        // Initialize the database
        langRepository.saveAndFlush(lang);

        int databaseSizeBeforeDelete = langRepository.findAll().size();

        // Delete the lang
        restLangMockMvc
            .perform(delete(ENTITY_API_URL_ID, lang.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lang> langList = langRepository.findAll();
        assertThat(langList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
