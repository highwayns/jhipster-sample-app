package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Identity;
import io.github.jhipster.sample.domain.enumeration.Gender;
import io.github.jhipster.sample.repository.IdentityRepository;
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
 * Integration tests for the {@link IdentityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IdentityResourceIT {

    private static final String DEFAULT_DEBTOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEBTOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.FEMALE;
    private static final Gender UPDATED_GENDER = Gender.MALE;

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOCIAL_SECURITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_SECURITY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CHAMBER_OF_COMMERCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHAMBER_OF_COMMERCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/identities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdentityMockMvc;

    private Identity identity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identity createEntity(EntityManager em) {
        Identity identity = new Identity()
            .debtorId(DEFAULT_DEBTOR_ID)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .gender(DEFAULT_GENDER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .socialSecurityNumber(DEFAULT_SOCIAL_SECURITY_NUMBER)
            .chamberOfCommerceNumber(DEFAULT_CHAMBER_OF_COMMERCE_NUMBER)
            .vatNumber(DEFAULT_VAT_NUMBER);
        return identity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identity createUpdatedEntity(EntityManager em) {
        Identity identity = new Identity()
            .debtorId(UPDATED_DEBTOR_ID)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .socialSecurityNumber(UPDATED_SOCIAL_SECURITY_NUMBER)
            .chamberOfCommerceNumber(UPDATED_CHAMBER_OF_COMMERCE_NUMBER)
            .vatNumber(UPDATED_VAT_NUMBER);
        return identity;
    }

    @BeforeEach
    public void initTest() {
        identity = createEntity(em);
    }

    @Test
    @Transactional
    void createIdentity() throws Exception {
        int databaseSizeBeforeCreate = identityRepository.findAll().size();
        // Create the Identity
        restIdentityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isCreated());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeCreate + 1);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getDebtorId()).isEqualTo(DEFAULT_DEBTOR_ID);
        assertThat(testIdentity.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testIdentity.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIdentity.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testIdentity.getSocialSecurityNumber()).isEqualTo(DEFAULT_SOCIAL_SECURITY_NUMBER);
        assertThat(testIdentity.getChamberOfCommerceNumber()).isEqualTo(DEFAULT_CHAMBER_OF_COMMERCE_NUMBER);
        assertThat(testIdentity.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
    }

    @Test
    @Transactional
    void createIdentityWithExistingId() throws Exception {
        // Create the Identity with an existing ID
        identity.setId(1L);

        int databaseSizeBeforeCreate = identityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIdentities() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        // Get all the identityList
        restIdentityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identity.getId().intValue())))
            .andExpect(jsonPath("$.[*].debtorId").value(hasItem(DEFAULT_DEBTOR_ID)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].socialSecurityNumber").value(hasItem(DEFAULT_SOCIAL_SECURITY_NUMBER)))
            .andExpect(jsonPath("$.[*].chamberOfCommerceNumber").value(hasItem(DEFAULT_CHAMBER_OF_COMMERCE_NUMBER)))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER)));
    }

    @Test
    @Transactional
    void getIdentity() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        // Get the identity
        restIdentityMockMvc
            .perform(get(ENTITY_API_URL_ID, identity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identity.getId().intValue()))
            .andExpect(jsonPath("$.debtorId").value(DEFAULT_DEBTOR_ID))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.socialSecurityNumber").value(DEFAULT_SOCIAL_SECURITY_NUMBER))
            .andExpect(jsonPath("$.chamberOfCommerceNumber").value(DEFAULT_CHAMBER_OF_COMMERCE_NUMBER))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingIdentity() throws Exception {
        // Get the identity
        restIdentityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIdentity() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // Update the identity
        Identity updatedIdentity = identityRepository.findById(identity.getId()).get();
        // Disconnect from session so that the updates on updatedIdentity are not directly saved in db
        em.detach(updatedIdentity);
        updatedIdentity
            .debtorId(UPDATED_DEBTOR_ID)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .socialSecurityNumber(UPDATED_SOCIAL_SECURITY_NUMBER)
            .chamberOfCommerceNumber(UPDATED_CHAMBER_OF_COMMERCE_NUMBER)
            .vatNumber(UPDATED_VAT_NUMBER);

        restIdentityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIdentity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIdentity))
            )
            .andExpect(status().isOk());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getDebtorId()).isEqualTo(UPDATED_DEBTOR_ID);
        assertThat(testIdentity.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIdentity.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIdentity.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIdentity.getSocialSecurityNumber()).isEqualTo(UPDATED_SOCIAL_SECURITY_NUMBER);
        assertThat(testIdentity.getChamberOfCommerceNumber()).isEqualTo(UPDATED_CHAMBER_OF_COMMERCE_NUMBER);
        assertThat(testIdentity.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();
        identity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, identity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(identity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();
        identity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(identity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();
        identity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdentityWithPatch() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // Update the identity using partial update
        Identity partialUpdatedIdentity = new Identity();
        partialUpdatedIdentity.setId(identity.getId());

        partialUpdatedIdentity
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .chamberOfCommerceNumber(UPDATED_CHAMBER_OF_COMMERCE_NUMBER)
            .vatNumber(UPDATED_VAT_NUMBER);

        restIdentityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdentity))
            )
            .andExpect(status().isOk());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getDebtorId()).isEqualTo(DEFAULT_DEBTOR_ID);
        assertThat(testIdentity.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testIdentity.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIdentity.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIdentity.getSocialSecurityNumber()).isEqualTo(DEFAULT_SOCIAL_SECURITY_NUMBER);
        assertThat(testIdentity.getChamberOfCommerceNumber()).isEqualTo(UPDATED_CHAMBER_OF_COMMERCE_NUMBER);
        assertThat(testIdentity.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateIdentityWithPatch() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // Update the identity using partial update
        Identity partialUpdatedIdentity = new Identity();
        partialUpdatedIdentity.setId(identity.getId());

        partialUpdatedIdentity
            .debtorId(UPDATED_DEBTOR_ID)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .socialSecurityNumber(UPDATED_SOCIAL_SECURITY_NUMBER)
            .chamberOfCommerceNumber(UPDATED_CHAMBER_OF_COMMERCE_NUMBER)
            .vatNumber(UPDATED_VAT_NUMBER);

        restIdentityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdentity))
            )
            .andExpect(status().isOk());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getDebtorId()).isEqualTo(UPDATED_DEBTOR_ID);
        assertThat(testIdentity.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIdentity.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIdentity.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIdentity.getSocialSecurityNumber()).isEqualTo(UPDATED_SOCIAL_SECURITY_NUMBER);
        assertThat(testIdentity.getChamberOfCommerceNumber()).isEqualTo(UPDATED_CHAMBER_OF_COMMERCE_NUMBER);
        assertThat(testIdentity.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();
        identity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, identity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(identity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();
        identity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(identity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();
        identity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdentity() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        int databaseSizeBeforeDelete = identityRepository.findAll().size();

        // Delete the identity
        restIdentityMockMvc
            .perform(delete(ENTITY_API_URL_ID, identity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
