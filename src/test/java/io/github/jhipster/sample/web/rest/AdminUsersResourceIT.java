package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminUsers;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.AdminUsersRepository;
import io.github.jhipster.sample.service.dto.AdminUsersDTO;
import io.github.jhipster.sample.service.mapper.AdminUsersMapper;
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
 * Integration tests for the {@link AdminUsersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminUsersResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_PASS = "AAAAAAAAAA";
    private static final String UPDATED_PASS = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Integer DEFAULT_F_ID = 1;
    private static final Integer UPDATED_F_ID = 2;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final YesNo DEFAULT_IS_ADMIN = YesNo.Y;
    private static final YesNo UPDATED_IS_ADMIN = YesNo.N;

    private static final Integer DEFAULT_COUNTRY_CODE = 1;
    private static final Integer UPDATED_COUNTRY_CODE = 2;

    private static final YesNo DEFAULT_VERIFIED_AUTHY = YesNo.Y;
    private static final YesNo UPDATED_VERIFIED_AUTHY = YesNo.N;

    private static final String DEFAULT_AUTHY_ID = "AAAAAAAAAA";
    private static final String UPDATED_AUTHY_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/admin-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminUsersRepository adminUsersRepository;

    @Autowired
    private AdminUsersMapper adminUsersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminUsersMockMvc;

    private AdminUsers adminUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminUsers createEntity(EntityManager em) {
        AdminUsers adminUsers = new AdminUsers()
            .user(DEFAULT_USER)
            .pass(DEFAULT_PASS)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .company(DEFAULT_COMPANY)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .fId(DEFAULT_F_ID)
            .order(DEFAULT_ORDER)
            .isAdmin(DEFAULT_IS_ADMIN)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .verifiedAuthy(DEFAULT_VERIFIED_AUTHY)
            .authyId(DEFAULT_AUTHY_ID);
        return adminUsers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminUsers createUpdatedEntity(EntityManager em) {
        AdminUsers adminUsers = new AdminUsers()
            .user(UPDATED_USER)
            .pass(UPDATED_PASS)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .company(UPDATED_COMPANY)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .fId(UPDATED_F_ID)
            .order(UPDATED_ORDER)
            .isAdmin(UPDATED_IS_ADMIN)
            .countryCode(UPDATED_COUNTRY_CODE)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY)
            .authyId(UPDATED_AUTHY_ID);
        return adminUsers;
    }

    @BeforeEach
    public void initTest() {
        adminUsers = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminUsers() throws Exception {
        int databaseSizeBeforeCreate = adminUsersRepository.findAll().size();
        // Create the AdminUsers
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);
        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeCreate + 1);
        AdminUsers testAdminUsers = adminUsersList.get(adminUsersList.size() - 1);
        assertThat(testAdminUsers.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testAdminUsers.getPass()).isEqualTo(DEFAULT_PASS);
        assertThat(testAdminUsers.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAdminUsers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAdminUsers.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testAdminUsers.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAdminUsers.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAdminUsers.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAdminUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAdminUsers.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testAdminUsers.getfId()).isEqualTo(DEFAULT_F_ID);
        assertThat(testAdminUsers.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAdminUsers.getIsAdmin()).isEqualTo(DEFAULT_IS_ADMIN);
        assertThat(testAdminUsers.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testAdminUsers.getVerifiedAuthy()).isEqualTo(DEFAULT_VERIFIED_AUTHY);
        assertThat(testAdminUsers.getAuthyId()).isEqualTo(DEFAULT_AUTHY_ID);
    }

    @Test
    @Transactional
    void createAdminUsersWithExistingId() throws Exception {
        // Create the AdminUsers with an existing ID
        adminUsers.setId(1L);
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        int databaseSizeBeforeCreate = adminUsersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setUser(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setPass(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setFirstName(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setLastName(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setCompany(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setAddress(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setCity(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setPhone(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setEmail(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWebsiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setWebsite(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkfIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setfId(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setOrder(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsAdminIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setIsAdmin(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setCountryCode(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerifiedAuthyIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setVerifiedAuthy(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUsersRepository.findAll().size();
        // set the field null
        adminUsers.setAuthyId(null);

        // Create the AdminUsers, which fails.
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        restAdminUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminUsers() throws Exception {
        // Initialize the database
        adminUsersRepository.saveAndFlush(adminUsers);

        // Get all the adminUsersList
        restAdminUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].pass").value(hasItem(DEFAULT_PASS)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].fId").value(hasItem(DEFAULT_F_ID)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].isAdmin").value(hasItem(DEFAULT_IS_ADMIN.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].verifiedAuthy").value(hasItem(DEFAULT_VERIFIED_AUTHY.toString())))
            .andExpect(jsonPath("$.[*].authyId").value(hasItem(DEFAULT_AUTHY_ID)));
    }

    @Test
    @Transactional
    void getAdminUsers() throws Exception {
        // Initialize the database
        adminUsersRepository.saveAndFlush(adminUsers);

        // Get the adminUsers
        restAdminUsersMockMvc
            .perform(get(ENTITY_API_URL_ID, adminUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminUsers.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.pass").value(DEFAULT_PASS))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.fId").value(DEFAULT_F_ID))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.isAdmin").value(DEFAULT_IS_ADMIN.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.verifiedAuthy").value(DEFAULT_VERIFIED_AUTHY.toString()))
            .andExpect(jsonPath("$.authyId").value(DEFAULT_AUTHY_ID));
    }

    @Test
    @Transactional
    void getNonExistingAdminUsers() throws Exception {
        // Get the adminUsers
        restAdminUsersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminUsers() throws Exception {
        // Initialize the database
        adminUsersRepository.saveAndFlush(adminUsers);

        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();

        // Update the adminUsers
        AdminUsers updatedAdminUsers = adminUsersRepository.findById(adminUsers.getId()).get();
        // Disconnect from session so that the updates on updatedAdminUsers are not directly saved in db
        em.detach(updatedAdminUsers);
        updatedAdminUsers
            .user(UPDATED_USER)
            .pass(UPDATED_PASS)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .company(UPDATED_COMPANY)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .fId(UPDATED_F_ID)
            .order(UPDATED_ORDER)
            .isAdmin(UPDATED_IS_ADMIN)
            .countryCode(UPDATED_COUNTRY_CODE)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY)
            .authyId(UPDATED_AUTHY_ID);
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(updatedAdminUsers);

        restAdminUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminUsersDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
        AdminUsers testAdminUsers = adminUsersList.get(adminUsersList.size() - 1);
        assertThat(testAdminUsers.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testAdminUsers.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testAdminUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAdminUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAdminUsers.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAdminUsers.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAdminUsers.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAdminUsers.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAdminUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAdminUsers.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testAdminUsers.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testAdminUsers.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminUsers.getIsAdmin()).isEqualTo(UPDATED_IS_ADMIN);
        assertThat(testAdminUsers.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAdminUsers.getVerifiedAuthy()).isEqualTo(UPDATED_VERIFIED_AUTHY);
        assertThat(testAdminUsers.getAuthyId()).isEqualTo(UPDATED_AUTHY_ID);
    }

    @Test
    @Transactional
    void putNonExistingAdminUsers() throws Exception {
        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();
        adminUsers.setId(count.incrementAndGet());

        // Create the AdminUsers
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminUsers() throws Exception {
        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();
        adminUsers.setId(count.incrementAndGet());

        // Create the AdminUsers
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminUsers() throws Exception {
        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();
        adminUsers.setId(count.incrementAndGet());

        // Create the AdminUsers
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminUsersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminUsersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminUsersWithPatch() throws Exception {
        // Initialize the database
        adminUsersRepository.saveAndFlush(adminUsers);

        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();

        // Update the adminUsers using partial update
        AdminUsers partialUpdatedAdminUsers = new AdminUsers();
        partialUpdatedAdminUsers.setId(adminUsers.getId());

        partialUpdatedAdminUsers
            .firstName(UPDATED_FIRST_NAME)
            .company(UPDATED_COMPANY)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .order(UPDATED_ORDER)
            .isAdmin(UPDATED_IS_ADMIN)
            .countryCode(UPDATED_COUNTRY_CODE)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY);

        restAdminUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminUsers))
            )
            .andExpect(status().isOk());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
        AdminUsers testAdminUsers = adminUsersList.get(adminUsersList.size() - 1);
        assertThat(testAdminUsers.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testAdminUsers.getPass()).isEqualTo(DEFAULT_PASS);
        assertThat(testAdminUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAdminUsers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAdminUsers.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAdminUsers.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAdminUsers.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAdminUsers.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAdminUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAdminUsers.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testAdminUsers.getfId()).isEqualTo(DEFAULT_F_ID);
        assertThat(testAdminUsers.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminUsers.getIsAdmin()).isEqualTo(UPDATED_IS_ADMIN);
        assertThat(testAdminUsers.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAdminUsers.getVerifiedAuthy()).isEqualTo(UPDATED_VERIFIED_AUTHY);
        assertThat(testAdminUsers.getAuthyId()).isEqualTo(DEFAULT_AUTHY_ID);
    }

    @Test
    @Transactional
    void fullUpdateAdminUsersWithPatch() throws Exception {
        // Initialize the database
        adminUsersRepository.saveAndFlush(adminUsers);

        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();

        // Update the adminUsers using partial update
        AdminUsers partialUpdatedAdminUsers = new AdminUsers();
        partialUpdatedAdminUsers.setId(adminUsers.getId());

        partialUpdatedAdminUsers
            .user(UPDATED_USER)
            .pass(UPDATED_PASS)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .company(UPDATED_COMPANY)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .fId(UPDATED_F_ID)
            .order(UPDATED_ORDER)
            .isAdmin(UPDATED_IS_ADMIN)
            .countryCode(UPDATED_COUNTRY_CODE)
            .verifiedAuthy(UPDATED_VERIFIED_AUTHY)
            .authyId(UPDATED_AUTHY_ID);

        restAdminUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminUsers))
            )
            .andExpect(status().isOk());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
        AdminUsers testAdminUsers = adminUsersList.get(adminUsersList.size() - 1);
        assertThat(testAdminUsers.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testAdminUsers.getPass()).isEqualTo(UPDATED_PASS);
        assertThat(testAdminUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAdminUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAdminUsers.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAdminUsers.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAdminUsers.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAdminUsers.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAdminUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAdminUsers.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testAdminUsers.getfId()).isEqualTo(UPDATED_F_ID);
        assertThat(testAdminUsers.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAdminUsers.getIsAdmin()).isEqualTo(UPDATED_IS_ADMIN);
        assertThat(testAdminUsers.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAdminUsers.getVerifiedAuthy()).isEqualTo(UPDATED_VERIFIED_AUTHY);
        assertThat(testAdminUsers.getAuthyId()).isEqualTo(UPDATED_AUTHY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAdminUsers() throws Exception {
        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();
        adminUsers.setId(count.incrementAndGet());

        // Create the AdminUsers
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminUsersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminUsers() throws Exception {
        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();
        adminUsers.setId(count.incrementAndGet());

        // Create the AdminUsers
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminUsers() throws Exception {
        int databaseSizeBeforeUpdate = adminUsersRepository.findAll().size();
        adminUsers.setId(count.incrementAndGet());

        // Create the AdminUsers
        AdminUsersDTO adminUsersDTO = adminUsersMapper.toDto(adminUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminUsersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adminUsersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminUsers in the database
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminUsers() throws Exception {
        // Initialize the database
        adminUsersRepository.saveAndFlush(adminUsers);

        int databaseSizeBeforeDelete = adminUsersRepository.findAll().size();

        // Delete the adminUsers
        restAdminUsersMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminUsers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminUsers> adminUsersList = adminUsersRepository.findAll();
        assertThat(adminUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
