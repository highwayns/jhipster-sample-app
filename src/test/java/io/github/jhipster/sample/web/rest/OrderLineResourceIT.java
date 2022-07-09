package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.OrderLine;
import io.github.jhipster.sample.domain.enumeration.OrderLineType;
import io.github.jhipster.sample.repository.OrderLineRepository;
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
 * Integration tests for the {@link OrderLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderLineResourceIT {

    private static final Long DEFAULT_LINE_NUMBER = 1L;
    private static final Long UPDATED_LINE_NUMBER = 2L;

    private static final OrderLineType DEFAULT_TYPE = OrderLineType.PHYSICALITEM;
    private static final OrderLineType UPDATED_TYPE = OrderLineType.DIGITALITEM;

    private static final String DEFAULT_SKU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SKU_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Double DEFAULT_UNIT_PRICE_EXCL_VAT = 1D;
    private static final Double UPDATED_UNIT_PRICE_EXCL_VAT = 2D;

    private static final Double DEFAULT_UNIT_PRICE_INCL_VAT = 1D;
    private static final Double UPDATED_UNIT_PRICE_INCL_VAT = 2D;

    private static final Double DEFAULT_VAT_PERCENTAGE = 1D;
    private static final Double UPDATED_VAT_PERCENTAGE = 2D;

    private static final String DEFAULT_VAT_PERCENTAGE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_VAT_PERCENTAGE_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_PERCENTAGE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_PERCENTAGE_LABEL = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_LINE_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_LINE_AMOUNT = 2D;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderLineMockMvc;

    private OrderLine orderLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLine createEntity(EntityManager em) {
        OrderLine orderLine = new OrderLine()
            .lineNumber(DEFAULT_LINE_NUMBER)
            .type(DEFAULT_TYPE)
            .skuCode(DEFAULT_SKU_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .quantity(DEFAULT_QUANTITY)
            .unitPriceExclVat(DEFAULT_UNIT_PRICE_EXCL_VAT)
            .unitPriceInclVat(DEFAULT_UNIT_PRICE_INCL_VAT)
            .vatPercentage(DEFAULT_VAT_PERCENTAGE)
            .vatPercentageLabel(DEFAULT_VAT_PERCENTAGE_LABEL)
            .discountPercentageLabel(DEFAULT_DISCOUNT_PERCENTAGE_LABEL)
            .totalLineAmount(DEFAULT_TOTAL_LINE_AMOUNT)
            .url(DEFAULT_URL);
        return orderLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLine createUpdatedEntity(EntityManager em) {
        OrderLine orderLine = new OrderLine()
            .lineNumber(UPDATED_LINE_NUMBER)
            .type(UPDATED_TYPE)
            .skuCode(UPDATED_SKU_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPriceExclVat(UPDATED_UNIT_PRICE_EXCL_VAT)
            .unitPriceInclVat(UPDATED_UNIT_PRICE_INCL_VAT)
            .vatPercentage(UPDATED_VAT_PERCENTAGE)
            .vatPercentageLabel(UPDATED_VAT_PERCENTAGE_LABEL)
            .discountPercentageLabel(UPDATED_DISCOUNT_PERCENTAGE_LABEL)
            .totalLineAmount(UPDATED_TOTAL_LINE_AMOUNT)
            .url(UPDATED_URL);
        return orderLine;
    }

    @BeforeEach
    public void initTest() {
        orderLine = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderLine() throws Exception {
        int databaseSizeBeforeCreate = orderLineRepository.findAll().size();
        // Create the OrderLine
        restOrderLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLine)))
            .andExpect(status().isCreated());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeCreate + 1);
        OrderLine testOrderLine = orderLineList.get(orderLineList.size() - 1);
        assertThat(testOrderLine.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testOrderLine.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrderLine.getSkuCode()).isEqualTo(DEFAULT_SKU_CODE);
        assertThat(testOrderLine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrderLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderLine.getUnitPriceExclVat()).isEqualTo(DEFAULT_UNIT_PRICE_EXCL_VAT);
        assertThat(testOrderLine.getUnitPriceInclVat()).isEqualTo(DEFAULT_UNIT_PRICE_INCL_VAT);
        assertThat(testOrderLine.getVatPercentage()).isEqualTo(DEFAULT_VAT_PERCENTAGE);
        assertThat(testOrderLine.getVatPercentageLabel()).isEqualTo(DEFAULT_VAT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getDiscountPercentageLabel()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getTotalLineAmount()).isEqualTo(DEFAULT_TOTAL_LINE_AMOUNT);
        assertThat(testOrderLine.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    void createOrderLineWithExistingId() throws Exception {
        // Create the OrderLine with an existing ID
        orderLine.setId(1L);

        int databaseSizeBeforeCreate = orderLineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLine)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrderLines() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        // Get all the orderLineList
        restOrderLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].skuCode").value(hasItem(DEFAULT_SKU_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPriceExclVat").value(hasItem(DEFAULT_UNIT_PRICE_EXCL_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPriceInclVat").value(hasItem(DEFAULT_UNIT_PRICE_INCL_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].vatPercentage").value(hasItem(DEFAULT_VAT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].vatPercentageLabel").value(hasItem(DEFAULT_VAT_PERCENTAGE_LABEL)))
            .andExpect(jsonPath("$.[*].discountPercentageLabel").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE_LABEL)))
            .andExpect(jsonPath("$.[*].totalLineAmount").value(hasItem(DEFAULT_TOTAL_LINE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }

    @Test
    @Transactional
    void getOrderLine() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        // Get the orderLine
        restOrderLineMockMvc
            .perform(get(ENTITY_API_URL_ID, orderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderLine.getId().intValue()))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.skuCode").value(DEFAULT_SKU_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unitPriceExclVat").value(DEFAULT_UNIT_PRICE_EXCL_VAT.doubleValue()))
            .andExpect(jsonPath("$.unitPriceInclVat").value(DEFAULT_UNIT_PRICE_INCL_VAT.doubleValue()))
            .andExpect(jsonPath("$.vatPercentage").value(DEFAULT_VAT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.vatPercentageLabel").value(DEFAULT_VAT_PERCENTAGE_LABEL))
            .andExpect(jsonPath("$.discountPercentageLabel").value(DEFAULT_DISCOUNT_PERCENTAGE_LABEL))
            .andExpect(jsonPath("$.totalLineAmount").value(DEFAULT_TOTAL_LINE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    void getNonExistingOrderLine() throws Exception {
        // Get the orderLine
        restOrderLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderLine() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();

        // Update the orderLine
        OrderLine updatedOrderLine = orderLineRepository.findById(orderLine.getId()).get();
        // Disconnect from session so that the updates on updatedOrderLine are not directly saved in db
        em.detach(updatedOrderLine);
        updatedOrderLine
            .lineNumber(UPDATED_LINE_NUMBER)
            .type(UPDATED_TYPE)
            .skuCode(UPDATED_SKU_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPriceExclVat(UPDATED_UNIT_PRICE_EXCL_VAT)
            .unitPriceInclVat(UPDATED_UNIT_PRICE_INCL_VAT)
            .vatPercentage(UPDATED_VAT_PERCENTAGE)
            .vatPercentageLabel(UPDATED_VAT_PERCENTAGE_LABEL)
            .discountPercentageLabel(UPDATED_DISCOUNT_PERCENTAGE_LABEL)
            .totalLineAmount(UPDATED_TOTAL_LINE_AMOUNT)
            .url(UPDATED_URL);

        restOrderLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderLine))
            )
            .andExpect(status().isOk());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
        OrderLine testOrderLine = orderLineList.get(orderLineList.size() - 1);
        assertThat(testOrderLine.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testOrderLine.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrderLine.getSkuCode()).isEqualTo(UPDATED_SKU_CODE);
        assertThat(testOrderLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrderLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderLine.getUnitPriceExclVat()).isEqualTo(UPDATED_UNIT_PRICE_EXCL_VAT);
        assertThat(testOrderLine.getUnitPriceInclVat()).isEqualTo(UPDATED_UNIT_PRICE_INCL_VAT);
        assertThat(testOrderLine.getVatPercentage()).isEqualTo(UPDATED_VAT_PERCENTAGE);
        assertThat(testOrderLine.getVatPercentageLabel()).isEqualTo(UPDATED_VAT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getDiscountPercentageLabel()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getTotalLineAmount()).isEqualTo(UPDATED_TOTAL_LINE_AMOUNT);
        assertThat(testOrderLine.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void putNonExistingOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();
        orderLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();
        orderLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();
        orderLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderLineWithPatch() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();

        // Update the orderLine using partial update
        OrderLine partialUpdatedOrderLine = new OrderLine();
        partialUpdatedOrderLine.setId(orderLine.getId());

        partialUpdatedOrderLine
            .lineNumber(UPDATED_LINE_NUMBER)
            .skuCode(UPDATED_SKU_CODE)
            .name(UPDATED_NAME)
            .unitPriceExclVat(UPDATED_UNIT_PRICE_EXCL_VAT)
            .unitPriceInclVat(UPDATED_UNIT_PRICE_INCL_VAT)
            .vatPercentage(UPDATED_VAT_PERCENTAGE)
            .vatPercentageLabel(UPDATED_VAT_PERCENTAGE_LABEL)
            .url(UPDATED_URL);

        restOrderLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderLine))
            )
            .andExpect(status().isOk());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
        OrderLine testOrderLine = orderLineList.get(orderLineList.size() - 1);
        assertThat(testOrderLine.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testOrderLine.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrderLine.getSkuCode()).isEqualTo(UPDATED_SKU_CODE);
        assertThat(testOrderLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrderLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderLine.getUnitPriceExclVat()).isEqualTo(UPDATED_UNIT_PRICE_EXCL_VAT);
        assertThat(testOrderLine.getUnitPriceInclVat()).isEqualTo(UPDATED_UNIT_PRICE_INCL_VAT);
        assertThat(testOrderLine.getVatPercentage()).isEqualTo(UPDATED_VAT_PERCENTAGE);
        assertThat(testOrderLine.getVatPercentageLabel()).isEqualTo(UPDATED_VAT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getDiscountPercentageLabel()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getTotalLineAmount()).isEqualTo(DEFAULT_TOTAL_LINE_AMOUNT);
        assertThat(testOrderLine.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void fullUpdateOrderLineWithPatch() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();

        // Update the orderLine using partial update
        OrderLine partialUpdatedOrderLine = new OrderLine();
        partialUpdatedOrderLine.setId(orderLine.getId());

        partialUpdatedOrderLine
            .lineNumber(UPDATED_LINE_NUMBER)
            .type(UPDATED_TYPE)
            .skuCode(UPDATED_SKU_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPriceExclVat(UPDATED_UNIT_PRICE_EXCL_VAT)
            .unitPriceInclVat(UPDATED_UNIT_PRICE_INCL_VAT)
            .vatPercentage(UPDATED_VAT_PERCENTAGE)
            .vatPercentageLabel(UPDATED_VAT_PERCENTAGE_LABEL)
            .discountPercentageLabel(UPDATED_DISCOUNT_PERCENTAGE_LABEL)
            .totalLineAmount(UPDATED_TOTAL_LINE_AMOUNT)
            .url(UPDATED_URL);

        restOrderLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderLine))
            )
            .andExpect(status().isOk());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
        OrderLine testOrderLine = orderLineList.get(orderLineList.size() - 1);
        assertThat(testOrderLine.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testOrderLine.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrderLine.getSkuCode()).isEqualTo(UPDATED_SKU_CODE);
        assertThat(testOrderLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrderLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderLine.getUnitPriceExclVat()).isEqualTo(UPDATED_UNIT_PRICE_EXCL_VAT);
        assertThat(testOrderLine.getUnitPriceInclVat()).isEqualTo(UPDATED_UNIT_PRICE_INCL_VAT);
        assertThat(testOrderLine.getVatPercentage()).isEqualTo(UPDATED_VAT_PERCENTAGE);
        assertThat(testOrderLine.getVatPercentageLabel()).isEqualTo(UPDATED_VAT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getDiscountPercentageLabel()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE_LABEL);
        assertThat(testOrderLine.getTotalLineAmount()).isEqualTo(UPDATED_TOTAL_LINE_AMOUNT);
        assertThat(testOrderLine.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void patchNonExistingOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();
        orderLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();
        orderLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();
        orderLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderLineMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderLine in the database
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderLine() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        int databaseSizeBeforeDelete = orderLineRepository.findAll().size();

        // Delete the orderLine
        restOrderLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderLine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        assertThat(orderLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
