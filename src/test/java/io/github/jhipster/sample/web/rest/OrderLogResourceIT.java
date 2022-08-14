package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.OrderLog;
import io.github.jhipster.sample.domain.enumeration.Status;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.OrderLogRepository;
import io.github.jhipster.sample.service.dto.OrderLogDTO;
import io.github.jhipster.sample.service.mapper.OrderLogMapper;
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
 * Integration tests for the {@link OrderLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderLogResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_BTC = 1D;
    private static final Double UPDATED_BTC = 2D;

    private static final YesNo DEFAULT_MARKET_PRICE = YesNo.Y;
    private static final YesNo UPDATED_MARKET_PRICE = YesNo.N;

    private static final Double DEFAULT_BTC_PRICE = 1D;
    private static final Double UPDATED_BTC_PRICE = 2D;

    private static final Double DEFAULT_FIAT = 1D;
    private static final Double UPDATED_FIAT = 2D;

    private static final Integer DEFAULT_P_ID = 1;
    private static final Integer UPDATED_P_ID = 2;

    private static final String DEFAULT_STOP_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_STOP_PRICE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.FILLED;

    private static final Double DEFAULT_BTC_REMAINING = 1D;
    private static final Double UPDATED_BTC_REMAINING = 2D;

    private static final String ENTITY_API_URL = "/api/order-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderLogRepository orderLogRepository;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderLogMockMvc;

    private OrderLog orderLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLog createEntity(EntityManager em) {
        OrderLog orderLog = new OrderLog()
            .date(DEFAULT_DATE)
            .btc(DEFAULT_BTC)
            .marketPrice(DEFAULT_MARKET_PRICE)
            .btcPrice(DEFAULT_BTC_PRICE)
            .fiat(DEFAULT_FIAT)
            .pId(DEFAULT_P_ID)
            .stopPrice(DEFAULT_STOP_PRICE)
            .status(DEFAULT_STATUS)
            .btcRemaining(DEFAULT_BTC_REMAINING);
        return orderLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLog createUpdatedEntity(EntityManager em) {
        OrderLog orderLog = new OrderLog()
            .date(UPDATED_DATE)
            .btc(UPDATED_BTC)
            .marketPrice(UPDATED_MARKET_PRICE)
            .btcPrice(UPDATED_BTC_PRICE)
            .fiat(UPDATED_FIAT)
            .pId(UPDATED_P_ID)
            .stopPrice(UPDATED_STOP_PRICE)
            .status(UPDATED_STATUS)
            .btcRemaining(UPDATED_BTC_REMAINING);
        return orderLog;
    }

    @BeforeEach
    public void initTest() {
        orderLog = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderLog() throws Exception {
        int databaseSizeBeforeCreate = orderLogRepository.findAll().size();
        // Create the OrderLog
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);
        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeCreate + 1);
        OrderLog testOrderLog = orderLogList.get(orderLogList.size() - 1);
        assertThat(testOrderLog.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOrderLog.getBtc()).isEqualTo(DEFAULT_BTC);
        assertThat(testOrderLog.getMarketPrice()).isEqualTo(DEFAULT_MARKET_PRICE);
        assertThat(testOrderLog.getBtcPrice()).isEqualTo(DEFAULT_BTC_PRICE);
        assertThat(testOrderLog.getFiat()).isEqualTo(DEFAULT_FIAT);
        assertThat(testOrderLog.getpId()).isEqualTo(DEFAULT_P_ID);
        assertThat(testOrderLog.getStopPrice()).isEqualTo(DEFAULT_STOP_PRICE);
        assertThat(testOrderLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderLog.getBtcRemaining()).isEqualTo(DEFAULT_BTC_REMAINING);
    }

    @Test
    @Transactional
    void createOrderLogWithExistingId() throws Exception {
        // Create the OrderLog with an existing ID
        orderLog.setId(1L);
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        int databaseSizeBeforeCreate = orderLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setDate(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setBtc(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarketPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setMarketPrice(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setBtcPrice(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiatIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setFiat(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setpId(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStopPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setStopPrice(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setStatus(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcRemainingIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLogRepository.findAll().size();
        // set the field null
        orderLog.setBtcRemaining(null);

        // Create the OrderLog, which fails.
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        restOrderLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderLogs() throws Exception {
        // Initialize the database
        orderLogRepository.saveAndFlush(orderLog);

        // Get all the orderLogList
        restOrderLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].btc").value(hasItem(DEFAULT_BTC.doubleValue())))
            .andExpect(jsonPath("$.[*].marketPrice").value(hasItem(DEFAULT_MARKET_PRICE.toString())))
            .andExpect(jsonPath("$.[*].btcPrice").value(hasItem(DEFAULT_BTC_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].fiat").value(hasItem(DEFAULT_FIAT.doubleValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID)))
            .andExpect(jsonPath("$.[*].stopPrice").value(hasItem(DEFAULT_STOP_PRICE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].btcRemaining").value(hasItem(DEFAULT_BTC_REMAINING.doubleValue())));
    }

    @Test
    @Transactional
    void getOrderLog() throws Exception {
        // Initialize the database
        orderLogRepository.saveAndFlush(orderLog);

        // Get the orderLog
        restOrderLogMockMvc
            .perform(get(ENTITY_API_URL_ID, orderLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderLog.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.btc").value(DEFAULT_BTC.doubleValue()))
            .andExpect(jsonPath("$.marketPrice").value(DEFAULT_MARKET_PRICE.toString()))
            .andExpect(jsonPath("$.btcPrice").value(DEFAULT_BTC_PRICE.doubleValue()))
            .andExpect(jsonPath("$.fiat").value(DEFAULT_FIAT.doubleValue()))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID))
            .andExpect(jsonPath("$.stopPrice").value(DEFAULT_STOP_PRICE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.btcRemaining").value(DEFAULT_BTC_REMAINING.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrderLog() throws Exception {
        // Get the orderLog
        restOrderLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderLog() throws Exception {
        // Initialize the database
        orderLogRepository.saveAndFlush(orderLog);

        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();

        // Update the orderLog
        OrderLog updatedOrderLog = orderLogRepository.findById(orderLog.getId()).get();
        // Disconnect from session so that the updates on updatedOrderLog are not directly saved in db
        em.detach(updatedOrderLog);
        updatedOrderLog
            .date(UPDATED_DATE)
            .btc(UPDATED_BTC)
            .marketPrice(UPDATED_MARKET_PRICE)
            .btcPrice(UPDATED_BTC_PRICE)
            .fiat(UPDATED_FIAT)
            .pId(UPDATED_P_ID)
            .stopPrice(UPDATED_STOP_PRICE)
            .status(UPDATED_STATUS)
            .btcRemaining(UPDATED_BTC_REMAINING);
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(updatedOrderLog);

        restOrderLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
        OrderLog testOrderLog = orderLogList.get(orderLogList.size() - 1);
        assertThat(testOrderLog.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOrderLog.getBtc()).isEqualTo(UPDATED_BTC);
        assertThat(testOrderLog.getMarketPrice()).isEqualTo(UPDATED_MARKET_PRICE);
        assertThat(testOrderLog.getBtcPrice()).isEqualTo(UPDATED_BTC_PRICE);
        assertThat(testOrderLog.getFiat()).isEqualTo(UPDATED_FIAT);
        assertThat(testOrderLog.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testOrderLog.getStopPrice()).isEqualTo(UPDATED_STOP_PRICE);
        assertThat(testOrderLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderLog.getBtcRemaining()).isEqualTo(UPDATED_BTC_REMAINING);
    }

    @Test
    @Transactional
    void putNonExistingOrderLog() throws Exception {
        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();
        orderLog.setId(count.incrementAndGet());

        // Create the OrderLog
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderLog() throws Exception {
        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();
        orderLog.setId(count.incrementAndGet());

        // Create the OrderLog
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderLog() throws Exception {
        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();
        orderLog.setId(count.incrementAndGet());

        // Create the OrderLog
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderLogWithPatch() throws Exception {
        // Initialize the database
        orderLogRepository.saveAndFlush(orderLog);

        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();

        // Update the orderLog using partial update
        OrderLog partialUpdatedOrderLog = new OrderLog();
        partialUpdatedOrderLog.setId(orderLog.getId());

        partialUpdatedOrderLog.btcPrice(UPDATED_BTC_PRICE).fiat(UPDATED_FIAT).pId(UPDATED_P_ID).stopPrice(UPDATED_STOP_PRICE);

        restOrderLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderLog))
            )
            .andExpect(status().isOk());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
        OrderLog testOrderLog = orderLogList.get(orderLogList.size() - 1);
        assertThat(testOrderLog.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOrderLog.getBtc()).isEqualTo(DEFAULT_BTC);
        assertThat(testOrderLog.getMarketPrice()).isEqualTo(DEFAULT_MARKET_PRICE);
        assertThat(testOrderLog.getBtcPrice()).isEqualTo(UPDATED_BTC_PRICE);
        assertThat(testOrderLog.getFiat()).isEqualTo(UPDATED_FIAT);
        assertThat(testOrderLog.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testOrderLog.getStopPrice()).isEqualTo(UPDATED_STOP_PRICE);
        assertThat(testOrderLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderLog.getBtcRemaining()).isEqualTo(DEFAULT_BTC_REMAINING);
    }

    @Test
    @Transactional
    void fullUpdateOrderLogWithPatch() throws Exception {
        // Initialize the database
        orderLogRepository.saveAndFlush(orderLog);

        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();

        // Update the orderLog using partial update
        OrderLog partialUpdatedOrderLog = new OrderLog();
        partialUpdatedOrderLog.setId(orderLog.getId());

        partialUpdatedOrderLog
            .date(UPDATED_DATE)
            .btc(UPDATED_BTC)
            .marketPrice(UPDATED_MARKET_PRICE)
            .btcPrice(UPDATED_BTC_PRICE)
            .fiat(UPDATED_FIAT)
            .pId(UPDATED_P_ID)
            .stopPrice(UPDATED_STOP_PRICE)
            .status(UPDATED_STATUS)
            .btcRemaining(UPDATED_BTC_REMAINING);

        restOrderLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderLog))
            )
            .andExpect(status().isOk());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
        OrderLog testOrderLog = orderLogList.get(orderLogList.size() - 1);
        assertThat(testOrderLog.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOrderLog.getBtc()).isEqualTo(UPDATED_BTC);
        assertThat(testOrderLog.getMarketPrice()).isEqualTo(UPDATED_MARKET_PRICE);
        assertThat(testOrderLog.getBtcPrice()).isEqualTo(UPDATED_BTC_PRICE);
        assertThat(testOrderLog.getFiat()).isEqualTo(UPDATED_FIAT);
        assertThat(testOrderLog.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testOrderLog.getStopPrice()).isEqualTo(UPDATED_STOP_PRICE);
        assertThat(testOrderLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderLog.getBtcRemaining()).isEqualTo(UPDATED_BTC_REMAINING);
    }

    @Test
    @Transactional
    void patchNonExistingOrderLog() throws Exception {
        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();
        orderLog.setId(count.incrementAndGet());

        // Create the OrderLog
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderLog() throws Exception {
        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();
        orderLog.setId(count.incrementAndGet());

        // Create the OrderLog
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderLog() throws Exception {
        int databaseSizeBeforeUpdate = orderLogRepository.findAll().size();
        orderLog.setId(count.incrementAndGet());

        // Create the OrderLog
        OrderLogDTO orderLogDTO = orderLogMapper.toDto(orderLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderLog in the database
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderLog() throws Exception {
        // Initialize the database
        orderLogRepository.saveAndFlush(orderLog);

        int databaseSizeBeforeDelete = orderLogRepository.findAll().size();

        // Delete the orderLog
        restOrderLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderLog> orderLogList = orderLogRepository.findAll();
        assertThat(orderLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
