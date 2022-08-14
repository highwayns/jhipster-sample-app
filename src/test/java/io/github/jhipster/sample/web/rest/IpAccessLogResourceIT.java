package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.IpAccessLog;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.IpAccessLogRepository;
import io.github.jhipster.sample.service.dto.IpAccessLogDTO;
import io.github.jhipster.sample.service.mapper.IpAccessLogMapper;
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
 * Integration tests for the {@link IpAccessLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IpAccessLogResourceIT {

    private static final Long DEFAULT_IP = 1L;
    private static final Long UPDATED_IP = 2L;

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final YesNo DEFAULT_LOGIN = YesNo.Y;
    private static final YesNo UPDATED_LOGIN = YesNo.N;

    private static final String ENTITY_API_URL = "/api/ip-access-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IpAccessLogRepository ipAccessLogRepository;

    @Autowired
    private IpAccessLogMapper ipAccessLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIpAccessLogMockMvc;

    private IpAccessLog ipAccessLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IpAccessLog createEntity(EntityManager em) {
        IpAccessLog ipAccessLog = new IpAccessLog().ip(DEFAULT_IP).timestamp(DEFAULT_TIMESTAMP).login(DEFAULT_LOGIN);
        return ipAccessLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IpAccessLog createUpdatedEntity(EntityManager em) {
        IpAccessLog ipAccessLog = new IpAccessLog().ip(UPDATED_IP).timestamp(UPDATED_TIMESTAMP).login(UPDATED_LOGIN);
        return ipAccessLog;
    }

    @BeforeEach
    public void initTest() {
        ipAccessLog = createEntity(em);
    }

    @Test
    @Transactional
    void createIpAccessLog() throws Exception {
        int databaseSizeBeforeCreate = ipAccessLogRepository.findAll().size();
        // Create the IpAccessLog
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);
        restIpAccessLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeCreate + 1);
        IpAccessLog testIpAccessLog = ipAccessLogList.get(ipAccessLogList.size() - 1);
        assertThat(testIpAccessLog.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testIpAccessLog.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testIpAccessLog.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }

    @Test
    @Transactional
    void createIpAccessLogWithExistingId() throws Exception {
        // Create the IpAccessLog with an existing ID
        ipAccessLog.setId(1L);
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        int databaseSizeBeforeCreate = ipAccessLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIpAccessLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = ipAccessLogRepository.findAll().size();
        // set the field null
        ipAccessLog.setIp(null);

        // Create the IpAccessLog, which fails.
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        restIpAccessLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = ipAccessLogRepository.findAll().size();
        // set the field null
        ipAccessLog.setTimestamp(null);

        // Create the IpAccessLog, which fails.
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        restIpAccessLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = ipAccessLogRepository.findAll().size();
        // set the field null
        ipAccessLog.setLogin(null);

        // Create the IpAccessLog, which fails.
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        restIpAccessLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIpAccessLogs() throws Exception {
        // Initialize the database
        ipAccessLogRepository.saveAndFlush(ipAccessLog);

        // Get all the ipAccessLogList
        restIpAccessLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ipAccessLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())));
    }

    @Test
    @Transactional
    void getIpAccessLog() throws Exception {
        // Initialize the database
        ipAccessLogRepository.saveAndFlush(ipAccessLog);

        // Get the ipAccessLog
        restIpAccessLogMockMvc
            .perform(get(ENTITY_API_URL_ID, ipAccessLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ipAccessLog.getId().intValue()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.intValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIpAccessLog() throws Exception {
        // Get the ipAccessLog
        restIpAccessLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIpAccessLog() throws Exception {
        // Initialize the database
        ipAccessLogRepository.saveAndFlush(ipAccessLog);

        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();

        // Update the ipAccessLog
        IpAccessLog updatedIpAccessLog = ipAccessLogRepository.findById(ipAccessLog.getId()).get();
        // Disconnect from session so that the updates on updatedIpAccessLog are not directly saved in db
        em.detach(updatedIpAccessLog);
        updatedIpAccessLog.ip(UPDATED_IP).timestamp(UPDATED_TIMESTAMP).login(UPDATED_LOGIN);
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(updatedIpAccessLog);

        restIpAccessLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ipAccessLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
        IpAccessLog testIpAccessLog = ipAccessLogList.get(ipAccessLogList.size() - 1);
        assertThat(testIpAccessLog.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testIpAccessLog.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testIpAccessLog.getLogin()).isEqualTo(UPDATED_LOGIN);
    }

    @Test
    @Transactional
    void putNonExistingIpAccessLog() throws Exception {
        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();
        ipAccessLog.setId(count.incrementAndGet());

        // Create the IpAccessLog
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIpAccessLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ipAccessLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIpAccessLog() throws Exception {
        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();
        ipAccessLog.setId(count.incrementAndGet());

        // Create the IpAccessLog
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpAccessLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIpAccessLog() throws Exception {
        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();
        ipAccessLog.setId(count.incrementAndGet());

        // Create the IpAccessLog
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpAccessLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIpAccessLogWithPatch() throws Exception {
        // Initialize the database
        ipAccessLogRepository.saveAndFlush(ipAccessLog);

        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();

        // Update the ipAccessLog using partial update
        IpAccessLog partialUpdatedIpAccessLog = new IpAccessLog();
        partialUpdatedIpAccessLog.setId(ipAccessLog.getId());

        restIpAccessLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIpAccessLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIpAccessLog))
            )
            .andExpect(status().isOk());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
        IpAccessLog testIpAccessLog = ipAccessLogList.get(ipAccessLogList.size() - 1);
        assertThat(testIpAccessLog.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testIpAccessLog.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testIpAccessLog.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }

    @Test
    @Transactional
    void fullUpdateIpAccessLogWithPatch() throws Exception {
        // Initialize the database
        ipAccessLogRepository.saveAndFlush(ipAccessLog);

        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();

        // Update the ipAccessLog using partial update
        IpAccessLog partialUpdatedIpAccessLog = new IpAccessLog();
        partialUpdatedIpAccessLog.setId(ipAccessLog.getId());

        partialUpdatedIpAccessLog.ip(UPDATED_IP).timestamp(UPDATED_TIMESTAMP).login(UPDATED_LOGIN);

        restIpAccessLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIpAccessLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIpAccessLog))
            )
            .andExpect(status().isOk());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
        IpAccessLog testIpAccessLog = ipAccessLogList.get(ipAccessLogList.size() - 1);
        assertThat(testIpAccessLog.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testIpAccessLog.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testIpAccessLog.getLogin()).isEqualTo(UPDATED_LOGIN);
    }

    @Test
    @Transactional
    void patchNonExistingIpAccessLog() throws Exception {
        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();
        ipAccessLog.setId(count.incrementAndGet());

        // Create the IpAccessLog
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIpAccessLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ipAccessLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIpAccessLog() throws Exception {
        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();
        ipAccessLog.setId(count.incrementAndGet());

        // Create the IpAccessLog
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpAccessLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIpAccessLog() throws Exception {
        int databaseSizeBeforeUpdate = ipAccessLogRepository.findAll().size();
        ipAccessLog.setId(count.incrementAndGet());

        // Create the IpAccessLog
        IpAccessLogDTO ipAccessLogDTO = ipAccessLogMapper.toDto(ipAccessLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIpAccessLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ipAccessLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IpAccessLog in the database
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIpAccessLog() throws Exception {
        // Initialize the database
        ipAccessLogRepository.saveAndFlush(ipAccessLog);

        int databaseSizeBeforeDelete = ipAccessLogRepository.findAll().size();

        // Delete the ipAccessLog
        restIpAccessLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, ipAccessLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IpAccessLog> ipAccessLogList = ipAccessLogRepository.findAll();
        assertThat(ipAccessLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
