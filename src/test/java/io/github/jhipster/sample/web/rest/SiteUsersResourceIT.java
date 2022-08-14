package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.SiteUsersRepository;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersMapper;
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
 * Integration tests for the {@link SiteUsersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteUsersResourceIT {

    private static final String DEFAULT_PASS = "AAAAAAAAAA";
    private static final String UPDATED_PASS = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNTRY_CODE = 1;
    private static final Integer UPDATED_COUNTRY_CODE = 2;

    private static final YesNo DEFAULT_AUTHY_REQUESTED = YesNo.Y;
    private static final YesNo UPDATED_AUTHY_REQUESTED = YesNo.N;

    private static final YesNo DEFAULT_VERIFIED_AUTHY = YesNo.Y;
    private static final YesNo UPDATED_VERIFIED_AUTHY = YesNo.N;

    private static final Integer DEFAULT_AUTHY_ID = 1;
    private static final Integer UPDATED_AUTHY_ID = 2;

    private static final YesNo DEFAULT_USING_SMS = YesNo.Y;
    private static final YesNo UPDATED_USING_SMS = YesNo.N;

    private static final YesNo DEFAULT_DONT_ASK_30_DAYS = YesNo.Y;
    private static final YesNo UPDATED_DONT_ASK_30_DAYS = YesNo.N;

    private static final Instant DEFAULT_DONT_ASK_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DONT_ASK_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final YesNo DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BTC = YesNo.Y;
    private static final YesNo UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC = YesNo.N;

    private static final YesNo DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BTC = YesNo.Y;
    private static final YesNo UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC = YesNo.N;

    private static final YesNo DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BANK = YesNo.Y;
    private static final YesNo UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK = YesNo.N;

    private static final YesNo DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BANK = YesNo.Y;
    private static final YesNo UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BANK = YesNo.N;

    private static final YesNo DEFAULT_NOTIFY_DEPOSIT_BTC = YesNo.Y;
    private static final YesNo UPDATED_NOTIFY_DEPOSIT_BTC = YesNo.N;

    private static final YesNo DEFAULT_NOTIFY_DEPOSIT_BANK = YesNo.Y;
    private static final YesNo UPDATED_NOTIFY_DEPOSIT_BANK = YesNo.N;

    private static final Instant DEFAULT_LAST_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final YesNo DEFAULT_NO_LOGINS = YesNo.Y;
    private static final YesNo UPDATED_NO_LOGINS = YesNo.N;

    private static final YesNo DEFAULT_NOTIFY_LOGIN = YesNo.Y;
    private static final YesNo UPDATED_NOTIFY_LOGIN = YesNo.N;

    private static final YesNo DEFAULT_DEACTIVATED = YesNo.Y;
    private static final YesNo UPDATED_DEACTIVATED = YesNo.N;

    private static final YesNo DEFAULT_LOCKED = YesNo.Y;
    private static final YesNo UPDATED_LOCKED = YesNo.N;

    private static final String DEFAULT_GOOGLE_2_FA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_2_FA_CODE = "BBBBBBBBBB";

    private static final YesNo DEFAULT_VERIFIED_GOOGLE = YesNo.Y;
    private static final YesNo UPDATED_VERIFIED_GOOGLE = YesNo.N;

    private static final String DEFAULT_LAST_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LAST_LANG = "BBBBBBBBBB";

    private static final YesNo DEFAULT_NOTIFY_WITHDRAW_BTC = YesNo.Y;
    private static final YesNo UPDATED_NOTIFY_WITHDRAW_BTC = YesNo.N;

    private static final YesNo DEFAULT_NOTIFY_WITHDRAW_BANK = YesNo.Y;
    private static final YesNo UPDATED_NOTIFY_WITHDRAW_BANK = YesNo.N;

    private static final YesNo DEFAULT_TRUSTED = YesNo.Y;
    private static final YesNo UPDATED_TRUSTED = YesNo.N;

    private static final String ENTITY_API_URL = "/api/site-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteUsersRepository siteUsersRepository;

    @Autowired
    private SiteUsersMapper siteUsersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteUsersMockMvc;

    private SiteUsers siteUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsers createEntity(EntityManager em) {
        SiteUsers siteUsers = new SiteUsers()
            .pass(DEFAULT_PASS)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .date(DEFAULT_DATE)
            .tel(DEFAULT_TEL)
            .user(DEFAULT_USER)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .authyRequested(DEFAULT_AUTHY_REQUESTED)
            .verifiedAuthy(DEFAULT_VERIFIED_AUTHY)
            .authyId(DEFAULT_AUTHY_ID)
            .usingSms(DEFAULT_USING_SMS)
            .dontAsk30Days(DEFAULT_DONT_ASK_30_DAYS)
            .dontAskDate(DEFAULT_DONT_ASK_DATE)
            .confirmWithdrawalEmailBtc(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BTC)
            .confirmWithdrawal2faBtc(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BTC)
            .confirmWithdrawal2faBank(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BANK)
            .confirmWithdrawalEmailBank(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BANK)
            .notifyDepositBtc(DEFAULT_NOTIFY_DEPOSIT_BTC)
            .notifyDepositBank(DEFAULT_NOTIFY_DEPOSIT_BANK)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .noLogins(DEFAULT_NO_LOGINS)
            .notifyLogin(DEFAULT_NOTIFY_LOGIN)
            .deactivated(DEFAULT_DEACTIVATED)
            .locked(DEFAULT_LOCKED)
            .google2faCode(DEFAULT_GOOGLE_2_FA_CODE)
            .verifiedGoogle(DEFAULT_VERIFIED_GOOGLE)
            .lastLang(DEFAULT_LAST_LANG)
            .notifyWithdrawBtc(DEFAULT_NOTIFY_WITHDRAW_BTC)
            .notifyWithdrawBank(DEFAULT_NOTIFY_WITHDRAW_BANK)
            .trusted(DEFAULT_TRUSTED);
        return siteUsers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUsers createUpdatedEntity(EntityManager em) {
        SiteUsers siteUsers = new SiteUsers()
            .pass(UPDATED_PASS)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .date(UPDATED_DATE)
            .tel(UPDATED_TEL)
            .user(UPDATED_USER)
            .countryCode(UPDATED_COUNTRY_CODE)
            .authyRequested(UPDATED_AUTHY_REQUESTED)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY)
            .authyId(UPDATED_AUTHY_ID)
            .usingSms(UPDATED_USING_SMS)
            .dontAsk30Days(UPDATED_DONT_ASK_30_DAYS)
            .dontAskDate(UPDATED_DONT_ASK_DATE)
            .confirmWithdrawalEmailBtc(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC)
            .confirmWithdrawal2faBtc(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC)
            .confirmWithdrawal2faBank(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK)
            .confirmWithdrawalEmailBank(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BANK)
            .notifyDepositBtc(UPDATED_NOTIFY_DEPOSIT_BTC)
            .notifyDepositBank(UPDATED_NOTIFY_DEPOSIT_BANK)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .noLogins(UPDATED_NO_LOGINS)
            .notifyLogin(UPDATED_NOTIFY_LOGIN)
            .deactivated(UPDATED_DEACTIVATED)
            .locked(UPDATED_LOCKED)
            .google2faCode(UPDATED_GOOGLE_2_FA_CODE)
            .verifiedGoogle(UPDATED_VERIFIED_GOOGLE)
            .lastLang(UPDATED_LAST_LANG)
            .notifyWithdrawBtc(UPDATED_NOTIFY_WITHDRAW_BTC)
            .notifyWithdrawBank(UPDATED_NOTIFY_WITHDRAW_BANK)
            .trusted(UPDATED_TRUSTED);
        return siteUsers;
    }

    @BeforeEach
    public void initTest() {
        siteUsers = createEntity(em);
    }

    @Test
    @Transactional
    void createSiteUsers() throws Exception {
        int databaseSizeBeforeCreate = siteUsersRepository.findAll().size();
        // Create the SiteUsers
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);
        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeCreate + 1);
        SiteUsers testSiteUsers = siteUsersList.get(siteUsersList.size() - 1);
        assertThat(testSiteUsers.getPass()).isEqualTo(DEFAULT_PASS);
        assertThat(testSiteUsers.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSiteUsers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSiteUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSiteUsers.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSiteUsers.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testSiteUsers.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testSiteUsers.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testSiteUsers.getAuthyRequested()).isEqualTo(DEFAULT_AUTHY_REQUESTED);
        assertThat(testSiteUsers.getVerifiedAuthy()).isEqualTo(DEFAULT_VERIFIED_AUTHY);
        assertThat(testSiteUsers.getAuthyId()).isEqualTo(DEFAULT_AUTHY_ID);
        assertThat(testSiteUsers.getUsingSms()).isEqualTo(DEFAULT_USING_SMS);
        assertThat(testSiteUsers.getDontAsk30Days()).isEqualTo(DEFAULT_DONT_ASK_30_DAYS);
        assertThat(testSiteUsers.getDontAskDate()).isEqualTo(DEFAULT_DONT_ASK_DATE);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBtc()).isEqualTo(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBtc()).isEqualTo(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBank()).isEqualTo(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BANK);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBank()).isEqualTo(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BANK);
        assertThat(testSiteUsers.getNotifyDepositBtc()).isEqualTo(DEFAULT_NOTIFY_DEPOSIT_BTC);
        assertThat(testSiteUsers.getNotifyDepositBank()).isEqualTo(DEFAULT_NOTIFY_DEPOSIT_BANK);
        assertThat(testSiteUsers.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testSiteUsers.getNoLogins()).isEqualTo(DEFAULT_NO_LOGINS);
        assertThat(testSiteUsers.getNotifyLogin()).isEqualTo(DEFAULT_NOTIFY_LOGIN);
        assertThat(testSiteUsers.getDeactivated()).isEqualTo(DEFAULT_DEACTIVATED);
        assertThat(testSiteUsers.getLocked()).isEqualTo(DEFAULT_LOCKED);
        assertThat(testSiteUsers.getGoogle2faCode()).isEqualTo(DEFAULT_GOOGLE_2_FA_CODE);
        assertThat(testSiteUsers.getVerifiedGoogle()).isEqualTo(DEFAULT_VERIFIED_GOOGLE);
        assertThat(testSiteUsers.getLastLang()).isEqualTo(DEFAULT_LAST_LANG);
        assertThat(testSiteUsers.getNotifyWithdrawBtc()).isEqualTo(DEFAULT_NOTIFY_WITHDRAW_BTC);
        assertThat(testSiteUsers.getNotifyWithdrawBank()).isEqualTo(DEFAULT_NOTIFY_WITHDRAW_BANK);
        assertThat(testSiteUsers.getTrusted()).isEqualTo(DEFAULT_TRUSTED);
    }

    @Test
    @Transactional
    void createSiteUsersWithExistingId() throws Exception {
        // Create the SiteUsers with an existing ID
        siteUsers.setId(1L);
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        int databaseSizeBeforeCreate = siteUsersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPassIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setPass(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setFirstName(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setLastName(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setEmail(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setDate(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setTel(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setUser(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setCountryCode(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthyRequestedIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setAuthyRequested(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerifiedAuthyIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setVerifiedAuthy(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setAuthyId(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsingSmsIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setUsingSms(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDontAsk30DaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setDontAsk30Days(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDontAskDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setDontAskDate(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfirmWithdrawalEmailBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setConfirmWithdrawalEmailBtc(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfirmWithdrawal2faBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setConfirmWithdrawal2faBtc(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfirmWithdrawal2faBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setConfirmWithdrawal2faBank(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfirmWithdrawalEmailBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setConfirmWithdrawalEmailBank(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotifyDepositBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setNotifyDepositBtc(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotifyDepositBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setNotifyDepositBank(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setLastUpdate(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNoLoginsIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setNoLogins(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotifyLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setNotifyLogin(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeactivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setDeactivated(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLockedIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setLocked(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGoogle2faCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setGoogle2faCode(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerifiedGoogleIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setVerifiedGoogle(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastLangIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setLastLang(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotifyWithdrawBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setNotifyWithdrawBtc(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotifyWithdrawBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setNotifyWithdrawBank(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrustedIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUsersRepository.findAll().size();
        // set the field null
        siteUsers.setTrusted(null);

        // Create the SiteUsers, which fails.
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        restSiteUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSiteUsers() throws Exception {
        // Initialize the database
        siteUsersRepository.saveAndFlush(siteUsers);

        // Get all the siteUsersList
        restSiteUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].pass").value(hasItem(DEFAULT_PASS)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].authyRequested").value(hasItem(DEFAULT_AUTHY_REQUESTED.toString())))
            .andExpect(jsonPath("$.[*].verifiedAuthy").value(hasItem(DEFAULT_VERIFIED_AUTHY.toString())))
            .andExpect(jsonPath("$.[*].authyId").value(hasItem(DEFAULT_AUTHY_ID)))
            .andExpect(jsonPath("$.[*].usingSms").value(hasItem(DEFAULT_USING_SMS.toString())))
            .andExpect(jsonPath("$.[*].dontAsk30Days").value(hasItem(DEFAULT_DONT_ASK_30_DAYS.toString())))
            .andExpect(jsonPath("$.[*].dontAskDate").value(hasItem(DEFAULT_DONT_ASK_DATE.toString())))
            .andExpect(jsonPath("$.[*].confirmWithdrawalEmailBtc").value(hasItem(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BTC.toString())))
            .andExpect(jsonPath("$.[*].confirmWithdrawal2faBtc").value(hasItem(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BTC.toString())))
            .andExpect(jsonPath("$.[*].confirmWithdrawal2faBank").value(hasItem(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BANK.toString())))
            .andExpect(jsonPath("$.[*].confirmWithdrawalEmailBank").value(hasItem(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BANK.toString())))
            .andExpect(jsonPath("$.[*].notifyDepositBtc").value(hasItem(DEFAULT_NOTIFY_DEPOSIT_BTC.toString())))
            .andExpect(jsonPath("$.[*].notifyDepositBank").value(hasItem(DEFAULT_NOTIFY_DEPOSIT_BANK.toString())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].noLogins").value(hasItem(DEFAULT_NO_LOGINS.toString())))
            .andExpect(jsonPath("$.[*].notifyLogin").value(hasItem(DEFAULT_NOTIFY_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].deactivated").value(hasItem(DEFAULT_DEACTIVATED.toString())))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.toString())))
            .andExpect(jsonPath("$.[*].google2faCode").value(hasItem(DEFAULT_GOOGLE_2_FA_CODE)))
            .andExpect(jsonPath("$.[*].verifiedGoogle").value(hasItem(DEFAULT_VERIFIED_GOOGLE.toString())))
            .andExpect(jsonPath("$.[*].lastLang").value(hasItem(DEFAULT_LAST_LANG)))
            .andExpect(jsonPath("$.[*].notifyWithdrawBtc").value(hasItem(DEFAULT_NOTIFY_WITHDRAW_BTC.toString())))
            .andExpect(jsonPath("$.[*].notifyWithdrawBank").value(hasItem(DEFAULT_NOTIFY_WITHDRAW_BANK.toString())))
            .andExpect(jsonPath("$.[*].trusted").value(hasItem(DEFAULT_TRUSTED.toString())));
    }

    @Test
    @Transactional
    void getSiteUsers() throws Exception {
        // Initialize the database
        siteUsersRepository.saveAndFlush(siteUsers);

        // Get the siteUsers
        restSiteUsersMockMvc
            .perform(get(ENTITY_API_URL_ID, siteUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteUsers.getId().intValue()))
            .andExpect(jsonPath("$.pass").value(DEFAULT_PASS))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.authyRequested").value(DEFAULT_AUTHY_REQUESTED.toString()))
            .andExpect(jsonPath("$.verifiedAuthy").value(DEFAULT_VERIFIED_AUTHY.toString()))
            .andExpect(jsonPath("$.authyId").value(DEFAULT_AUTHY_ID))
            .andExpect(jsonPath("$.usingSms").value(DEFAULT_USING_SMS.toString()))
            .andExpect(jsonPath("$.dontAsk30Days").value(DEFAULT_DONT_ASK_30_DAYS.toString()))
            .andExpect(jsonPath("$.dontAskDate").value(DEFAULT_DONT_ASK_DATE.toString()))
            .andExpect(jsonPath("$.confirmWithdrawalEmailBtc").value(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BTC.toString()))
            .andExpect(jsonPath("$.confirmWithdrawal2faBtc").value(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BTC.toString()))
            .andExpect(jsonPath("$.confirmWithdrawal2faBank").value(DEFAULT_CONFIRM_WITHDRAWAL_2_FA_BANK.toString()))
            .andExpect(jsonPath("$.confirmWithdrawalEmailBank").value(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BANK.toString()))
            .andExpect(jsonPath("$.notifyDepositBtc").value(DEFAULT_NOTIFY_DEPOSIT_BTC.toString()))
            .andExpect(jsonPath("$.notifyDepositBank").value(DEFAULT_NOTIFY_DEPOSIT_BANK.toString()))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.noLogins").value(DEFAULT_NO_LOGINS.toString()))
            .andExpect(jsonPath("$.notifyLogin").value(DEFAULT_NOTIFY_LOGIN.toString()))
            .andExpect(jsonPath("$.deactivated").value(DEFAULT_DEACTIVATED.toString()))
            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.toString()))
            .andExpect(jsonPath("$.google2faCode").value(DEFAULT_GOOGLE_2_FA_CODE))
            .andExpect(jsonPath("$.verifiedGoogle").value(DEFAULT_VERIFIED_GOOGLE.toString()))
            .andExpect(jsonPath("$.lastLang").value(DEFAULT_LAST_LANG))
            .andExpect(jsonPath("$.notifyWithdrawBtc").value(DEFAULT_NOTIFY_WITHDRAW_BTC.toString()))
            .andExpect(jsonPath("$.notifyWithdrawBank").value(DEFAULT_NOTIFY_WITHDRAW_BANK.toString()))
            .andExpect(jsonPath("$.trusted").value(DEFAULT_TRUSTED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSiteUsers() throws Exception {
        // Get the siteUsers
        restSiteUsersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiteUsers() throws Exception {
        // Initialize the database
        siteUsersRepository.saveAndFlush(siteUsers);

        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();

        // Update the siteUsers
        SiteUsers updatedSiteUsers = siteUsersRepository.findById(siteUsers.getId()).get();
        // Disconnect from session so that the updates on updatedSiteUsers are not directly saved in db
        em.detach(updatedSiteUsers);
        updatedSiteUsers
            .pass(UPDATED_PASS)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .date(UPDATED_DATE)
            .tel(UPDATED_TEL)
            .user(UPDATED_USER)
            .countryCode(UPDATED_COUNTRY_CODE)
            .authyRequested(UPDATED_AUTHY_REQUESTED)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY)
            .authyId(UPDATED_AUTHY_ID)
            .usingSms(UPDATED_USING_SMS)
            .dontAsk30Days(UPDATED_DONT_ASK_30_DAYS)
            .dontAskDate(UPDATED_DONT_ASK_DATE)
            .confirmWithdrawalEmailBtc(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC)
            .confirmWithdrawal2faBtc(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC)
            .confirmWithdrawal2faBank(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK)
            .confirmWithdrawalEmailBank(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BANK)
            .notifyDepositBtc(UPDATED_NOTIFY_DEPOSIT_BTC)
            .notifyDepositBank(UPDATED_NOTIFY_DEPOSIT_BANK)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .noLogins(UPDATED_NO_LOGINS)
            .notifyLogin(UPDATED_NOTIFY_LOGIN)
            .deactivated(UPDATED_DEACTIVATED)
            .locked(UPDATED_LOCKED)
            .google2faCode(UPDATED_GOOGLE_2_FA_CODE)
            .verifiedGoogle(UPDATED_VERIFIED_GOOGLE)
            .lastLang(UPDATED_LAST_LANG)
            .notifyWithdrawBtc(UPDATED_NOTIFY_WITHDRAW_BTC)
            .notifyWithdrawBank(UPDATED_NOTIFY_WITHDRAW_BANK)
            .trusted(UPDATED_TRUSTED);
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(updatedSiteUsers);

        restSiteUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersDTO))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
        SiteUsers testSiteUsers = siteUsersList.get(siteUsersList.size() - 1);
        assertThat(testSiteUsers.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testSiteUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSiteUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSiteUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSiteUsers.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSiteUsers.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testSiteUsers.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testSiteUsers.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testSiteUsers.getAuthyRequested()).isEqualTo(UPDATED_AUTHY_REQUESTED);
        assertThat(testSiteUsers.getVerifiedAuthy()).isEqualTo(UPDATED_VERIFIED_AUTHY);
        assertThat(testSiteUsers.getAuthyId()).isEqualTo(UPDATED_AUTHY_ID);
        assertThat(testSiteUsers.getUsingSms()).isEqualTo(UPDATED_USING_SMS);
        assertThat(testSiteUsers.getDontAsk30Days()).isEqualTo(UPDATED_DONT_ASK_30_DAYS);
        assertThat(testSiteUsers.getDontAskDate()).isEqualTo(UPDATED_DONT_ASK_DATE);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBtc()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBtc()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBank()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBank()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BANK);
        assertThat(testSiteUsers.getNotifyDepositBtc()).isEqualTo(UPDATED_NOTIFY_DEPOSIT_BTC);
        assertThat(testSiteUsers.getNotifyDepositBank()).isEqualTo(UPDATED_NOTIFY_DEPOSIT_BANK);
        assertThat(testSiteUsers.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSiteUsers.getNoLogins()).isEqualTo(UPDATED_NO_LOGINS);
        assertThat(testSiteUsers.getNotifyLogin()).isEqualTo(UPDATED_NOTIFY_LOGIN);
        assertThat(testSiteUsers.getDeactivated()).isEqualTo(UPDATED_DEACTIVATED);
        assertThat(testSiteUsers.getLocked()).isEqualTo(UPDATED_LOCKED);
        assertThat(testSiteUsers.getGoogle2faCode()).isEqualTo(UPDATED_GOOGLE_2_FA_CODE);
        assertThat(testSiteUsers.getVerifiedGoogle()).isEqualTo(UPDATED_VERIFIED_GOOGLE);
        assertThat(testSiteUsers.getLastLang()).isEqualTo(UPDATED_LAST_LANG);
        assertThat(testSiteUsers.getNotifyWithdrawBtc()).isEqualTo(UPDATED_NOTIFY_WITHDRAW_BTC);
        assertThat(testSiteUsers.getNotifyWithdrawBank()).isEqualTo(UPDATED_NOTIFY_WITHDRAW_BANK);
        assertThat(testSiteUsers.getTrusted()).isEqualTo(UPDATED_TRUSTED);
    }

    @Test
    @Transactional
    void putNonExistingSiteUsers() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();
        siteUsers.setId(count.incrementAndGet());

        // Create the SiteUsers
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteUsers() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();
        siteUsers.setId(count.incrementAndGet());

        // Create the SiteUsers
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteUsers() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();
        siteUsers.setId(count.incrementAndGet());

        // Create the SiteUsers
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteUsersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteUsersWithPatch() throws Exception {
        // Initialize the database
        siteUsersRepository.saveAndFlush(siteUsers);

        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();

        // Update the siteUsers using partial update
        SiteUsers partialUpdatedSiteUsers = new SiteUsers();
        partialUpdatedSiteUsers.setId(siteUsers.getId());

        partialUpdatedSiteUsers
            .pass(UPDATED_PASS)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .user(UPDATED_USER)
            .countryCode(UPDATED_COUNTRY_CODE)
            .authyRequested(UPDATED_AUTHY_REQUESTED)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY)
            .usingSms(UPDATED_USING_SMS)
            .dontAskDate(UPDATED_DONT_ASK_DATE)
            .confirmWithdrawalEmailBtc(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC)
            .confirmWithdrawal2faBtc(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC)
            .confirmWithdrawal2faBank(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK)
            .notifyDepositBank(UPDATED_NOTIFY_DEPOSIT_BANK)
            .notifyLogin(UPDATED_NOTIFY_LOGIN)
            .notifyWithdrawBtc(UPDATED_NOTIFY_WITHDRAW_BTC)
            .notifyWithdrawBank(UPDATED_NOTIFY_WITHDRAW_BANK)
            .trusted(UPDATED_TRUSTED);

        restSiteUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsers))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
        SiteUsers testSiteUsers = siteUsersList.get(siteUsersList.size() - 1);
        assertThat(testSiteUsers.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testSiteUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSiteUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSiteUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSiteUsers.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSiteUsers.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testSiteUsers.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testSiteUsers.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testSiteUsers.getAuthyRequested()).isEqualTo(UPDATED_AUTHY_REQUESTED);
        assertThat(testSiteUsers.getVerifiedAuthy()).isEqualTo(UPDATED_VERIFIED_AUTHY);
        assertThat(testSiteUsers.getAuthyId()).isEqualTo(DEFAULT_AUTHY_ID);
        assertThat(testSiteUsers.getUsingSms()).isEqualTo(UPDATED_USING_SMS);
        assertThat(testSiteUsers.getDontAsk30Days()).isEqualTo(DEFAULT_DONT_ASK_30_DAYS);
        assertThat(testSiteUsers.getDontAskDate()).isEqualTo(UPDATED_DONT_ASK_DATE);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBtc()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBtc()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBank()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBank()).isEqualTo(DEFAULT_CONFIRM_WITHDRAWAL_EMAIL_BANK);
        assertThat(testSiteUsers.getNotifyDepositBtc()).isEqualTo(DEFAULT_NOTIFY_DEPOSIT_BTC);
        assertThat(testSiteUsers.getNotifyDepositBank()).isEqualTo(UPDATED_NOTIFY_DEPOSIT_BANK);
        assertThat(testSiteUsers.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testSiteUsers.getNoLogins()).isEqualTo(DEFAULT_NO_LOGINS);
        assertThat(testSiteUsers.getNotifyLogin()).isEqualTo(UPDATED_NOTIFY_LOGIN);
        assertThat(testSiteUsers.getDeactivated()).isEqualTo(DEFAULT_DEACTIVATED);
        assertThat(testSiteUsers.getLocked()).isEqualTo(DEFAULT_LOCKED);
        assertThat(testSiteUsers.getGoogle2faCode()).isEqualTo(DEFAULT_GOOGLE_2_FA_CODE);
        assertThat(testSiteUsers.getVerifiedGoogle()).isEqualTo(DEFAULT_VERIFIED_GOOGLE);
        assertThat(testSiteUsers.getLastLang()).isEqualTo(DEFAULT_LAST_LANG);
        assertThat(testSiteUsers.getNotifyWithdrawBtc()).isEqualTo(UPDATED_NOTIFY_WITHDRAW_BTC);
        assertThat(testSiteUsers.getNotifyWithdrawBank()).isEqualTo(UPDATED_NOTIFY_WITHDRAW_BANK);
        assertThat(testSiteUsers.getTrusted()).isEqualTo(UPDATED_TRUSTED);
    }

    @Test
    @Transactional
    void fullUpdateSiteUsersWithPatch() throws Exception {
        // Initialize the database
        siteUsersRepository.saveAndFlush(siteUsers);

        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();

        // Update the siteUsers using partial update
        SiteUsers partialUpdatedSiteUsers = new SiteUsers();
        partialUpdatedSiteUsers.setId(siteUsers.getId());

        partialUpdatedSiteUsers
            .pass(UPDATED_PASS)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .date(UPDATED_DATE)
            .tel(UPDATED_TEL)
            .user(UPDATED_USER)
            .countryCode(UPDATED_COUNTRY_CODE)
            .authyRequested(UPDATED_AUTHY_REQUESTED)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY)
            .authyId(UPDATED_AUTHY_ID)
            .usingSms(UPDATED_USING_SMS)
            .dontAsk30Days(UPDATED_DONT_ASK_30_DAYS)
            .dontAskDate(UPDATED_DONT_ASK_DATE)
            .confirmWithdrawalEmailBtc(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC)
            .confirmWithdrawal2faBtc(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC)
            .confirmWithdrawal2faBank(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK)
            .confirmWithdrawalEmailBank(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BANK)
            .notifyDepositBtc(UPDATED_NOTIFY_DEPOSIT_BTC)
            .notifyDepositBank(UPDATED_NOTIFY_DEPOSIT_BANK)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .noLogins(UPDATED_NO_LOGINS)
            .notifyLogin(UPDATED_NOTIFY_LOGIN)
            .deactivated(UPDATED_DEACTIVATED)
            .locked(UPDATED_LOCKED)
            .google2faCode(UPDATED_GOOGLE_2_FA_CODE)
            .verifiedGoogle(UPDATED_VERIFIED_GOOGLE)
            .lastLang(UPDATED_LAST_LANG)
            .notifyWithdrawBtc(UPDATED_NOTIFY_WITHDRAW_BTC)
            .notifyWithdrawBank(UPDATED_NOTIFY_WITHDRAW_BANK)
            .trusted(UPDATED_TRUSTED);

        restSiteUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteUsers))
            )
            .andExpect(status().isOk());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
        SiteUsers testSiteUsers = siteUsersList.get(siteUsersList.size() - 1);
        assertThat(testSiteUsers.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testSiteUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSiteUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSiteUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSiteUsers.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSiteUsers.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testSiteUsers.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testSiteUsers.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testSiteUsers.getAuthyRequested()).isEqualTo(UPDATED_AUTHY_REQUESTED);
        assertThat(testSiteUsers.getVerifiedAuthy()).isEqualTo(UPDATED_VERIFIED_AUTHY);
        assertThat(testSiteUsers.getAuthyId()).isEqualTo(UPDATED_AUTHY_ID);
        assertThat(testSiteUsers.getUsingSms()).isEqualTo(UPDATED_USING_SMS);
        assertThat(testSiteUsers.getDontAsk30Days()).isEqualTo(UPDATED_DONT_ASK_30_DAYS);
        assertThat(testSiteUsers.getDontAskDate()).isEqualTo(UPDATED_DONT_ASK_DATE);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBtc()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBtc()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BTC);
        assertThat(testSiteUsers.getConfirmWithdrawal2faBank()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_2_FA_BANK);
        assertThat(testSiteUsers.getConfirmWithdrawalEmailBank()).isEqualTo(UPDATED_CONFIRM_WITHDRAWAL_EMAIL_BANK);
        assertThat(testSiteUsers.getNotifyDepositBtc()).isEqualTo(UPDATED_NOTIFY_DEPOSIT_BTC);
        assertThat(testSiteUsers.getNotifyDepositBank()).isEqualTo(UPDATED_NOTIFY_DEPOSIT_BANK);
        assertThat(testSiteUsers.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testSiteUsers.getNoLogins()).isEqualTo(UPDATED_NO_LOGINS);
        assertThat(testSiteUsers.getNotifyLogin()).isEqualTo(UPDATED_NOTIFY_LOGIN);
        assertThat(testSiteUsers.getDeactivated()).isEqualTo(UPDATED_DEACTIVATED);
        assertThat(testSiteUsers.getLocked()).isEqualTo(UPDATED_LOCKED);
        assertThat(testSiteUsers.getGoogle2faCode()).isEqualTo(UPDATED_GOOGLE_2_FA_CODE);
        assertThat(testSiteUsers.getVerifiedGoogle()).isEqualTo(UPDATED_VERIFIED_GOOGLE);
        assertThat(testSiteUsers.getLastLang()).isEqualTo(UPDATED_LAST_LANG);
        assertThat(testSiteUsers.getNotifyWithdrawBtc()).isEqualTo(UPDATED_NOTIFY_WITHDRAW_BTC);
        assertThat(testSiteUsers.getNotifyWithdrawBank()).isEqualTo(UPDATED_NOTIFY_WITHDRAW_BANK);
        assertThat(testSiteUsers.getTrusted()).isEqualTo(UPDATED_TRUSTED);
    }

    @Test
    @Transactional
    void patchNonExistingSiteUsers() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();
        siteUsers.setId(count.incrementAndGet());

        // Create the SiteUsers
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteUsersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteUsers() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();
        siteUsers.setId(count.incrementAndGet());

        // Create the SiteUsers
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteUsers() throws Exception {
        int databaseSizeBeforeUpdate = siteUsersRepository.findAll().size();
        siteUsers.setId(count.incrementAndGet());

        // Create the SiteUsers
        SiteUsersDTO siteUsersDTO = siteUsersMapper.toDto(siteUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteUsersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(siteUsersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteUsers in the database
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteUsers() throws Exception {
        // Initialize the database
        siteUsersRepository.saveAndFlush(siteUsers);

        int databaseSizeBeforeDelete = siteUsersRepository.findAll().size();

        // Delete the siteUsers
        restSiteUsersMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteUsers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteUsers> siteUsersList = siteUsersRepository.findAll();
        assertThat(siteUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
