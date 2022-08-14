package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.OrderTypes;
import io.github.jhipster.sample.repository.OrderTypesRepository;
import io.github.jhipster.sample.service.dto.OrderTypesDTO;
import io.github.jhipster.sample.service.mapper.OrderTypesMapper;
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
 * Integration tests for the {@link OrderTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderTypesResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ES = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ZH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ZH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderTypesRepository orderTypesRepository;

    @Autowired
    private OrderTypesMapper orderTypesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTypesMockMvc;

    private OrderTypes orderTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTypes createEntity(EntityManager em) {
        OrderTypes orderTypes = new OrderTypes()
            .nameEn(DEFAULT_NAME_EN)
            .nameEs(DEFAULT_NAME_ES)
            .nameRu(DEFAULT_NAME_RU)
            .nameZh(DEFAULT_NAME_ZH);
        return orderTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTypes createUpdatedEntity(EntityManager em) {
        OrderTypes orderTypes = new OrderTypes()
            .nameEn(UPDATED_NAME_EN)
            .nameEs(UPDATED_NAME_ES)
            .nameRu(UPDATED_NAME_RU)
            .nameZh(UPDATED_NAME_ZH);
        return orderTypes;
    }

    @BeforeEach
    public void initTest() {
        orderTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderTypes() throws Exception {
        int databaseSizeBeforeCreate = orderTypesRepository.findAll().size();
        // Create the OrderTypes
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);
        restOrderTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTypes testOrderTypes = orderTypesList.get(orderTypesList.size() - 1);
        assertThat(testOrderTypes.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testOrderTypes.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testOrderTypes.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testOrderTypes.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void createOrderTypesWithExistingId() throws Exception {
        // Create the OrderTypes with an existing ID
        orderTypes.setId(1L);
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        int databaseSizeBeforeCreate = orderTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderTypesRepository.findAll().size();
        // set the field null
        orderTypes.setNameEn(null);

        // Create the OrderTypes, which fails.
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        restOrderTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderTypesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEsIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderTypesRepository.findAll().size();
        // set the field null
        orderTypes.setNameEs(null);

        // Create the OrderTypes, which fails.
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        restOrderTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderTypesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderTypesRepository.findAll().size();
        // set the field null
        orderTypes.setNameRu(null);

        // Create the OrderTypes, which fails.
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        restOrderTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderTypesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameZhIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderTypesRepository.findAll().size();
        // set the field null
        orderTypes.setNameZh(null);

        // Create the OrderTypes, which fails.
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        restOrderTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderTypesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderTypes() throws Exception {
        // Initialize the database
        orderTypesRepository.saveAndFlush(orderTypes);

        // Get all the orderTypesList
        restOrderTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameEs").value(hasItem(DEFAULT_NAME_ES)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameZh").value(hasItem(DEFAULT_NAME_ZH)));
    }

    @Test
    @Transactional
    void getOrderTypes() throws Exception {
        // Initialize the database
        orderTypesRepository.saveAndFlush(orderTypes);

        // Get the orderTypes
        restOrderTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, orderTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderTypes.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameEs").value(DEFAULT_NAME_ES))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameZh").value(DEFAULT_NAME_ZH));
    }

    @Test
    @Transactional
    void getNonExistingOrderTypes() throws Exception {
        // Get the orderTypes
        restOrderTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderTypes() throws Exception {
        // Initialize the database
        orderTypesRepository.saveAndFlush(orderTypes);

        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();

        // Update the orderTypes
        OrderTypes updatedOrderTypes = orderTypesRepository.findById(orderTypes.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTypes are not directly saved in db
        em.detach(updatedOrderTypes);
        updatedOrderTypes.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(updatedOrderTypes);

        restOrderTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderTypesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderTypesDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
        OrderTypes testOrderTypes = orderTypesList.get(orderTypesList.size() - 1);
        assertThat(testOrderTypes.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testOrderTypes.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testOrderTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testOrderTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void putNonExistingOrderTypes() throws Exception {
        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();
        orderTypes.setId(count.incrementAndGet());

        // Create the OrderTypes
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderTypesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderTypes() throws Exception {
        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();
        orderTypes.setId(count.incrementAndGet());

        // Create the OrderTypes
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderTypes() throws Exception {
        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();
        orderTypes.setId(count.incrementAndGet());

        // Create the OrderTypes
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderTypesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderTypesWithPatch() throws Exception {
        // Initialize the database
        orderTypesRepository.saveAndFlush(orderTypes);

        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();

        // Update the orderTypes using partial update
        OrderTypes partialUpdatedOrderTypes = new OrderTypes();
        partialUpdatedOrderTypes.setId(orderTypes.getId());

        partialUpdatedOrderTypes.nameEn(UPDATED_NAME_EN).nameRu(UPDATED_NAME_RU);

        restOrderTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderTypes))
            )
            .andExpect(status().isOk());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
        OrderTypes testOrderTypes = orderTypesList.get(orderTypesList.size() - 1);
        assertThat(testOrderTypes.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testOrderTypes.getNameEs()).isEqualTo(DEFAULT_NAME_ES);
        assertThat(testOrderTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testOrderTypes.getNameZh()).isEqualTo(DEFAULT_NAME_ZH);
    }

    @Test
    @Transactional
    void fullUpdateOrderTypesWithPatch() throws Exception {
        // Initialize the database
        orderTypesRepository.saveAndFlush(orderTypes);

        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();

        // Update the orderTypes using partial update
        OrderTypes partialUpdatedOrderTypes = new OrderTypes();
        partialUpdatedOrderTypes.setId(orderTypes.getId());

        partialUpdatedOrderTypes.nameEn(UPDATED_NAME_EN).nameEs(UPDATED_NAME_ES).nameRu(UPDATED_NAME_RU).nameZh(UPDATED_NAME_ZH);

        restOrderTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderTypes))
            )
            .andExpect(status().isOk());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
        OrderTypes testOrderTypes = orderTypesList.get(orderTypesList.size() - 1);
        assertThat(testOrderTypes.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testOrderTypes.getNameEs()).isEqualTo(UPDATED_NAME_ES);
        assertThat(testOrderTypes.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testOrderTypes.getNameZh()).isEqualTo(UPDATED_NAME_ZH);
    }

    @Test
    @Transactional
    void patchNonExistingOrderTypes() throws Exception {
        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();
        orderTypes.setId(count.incrementAndGet());

        // Create the OrderTypes
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderTypesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderTypes() throws Exception {
        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();
        orderTypes.setId(count.incrementAndGet());

        // Create the OrderTypes
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderTypesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderTypes() throws Exception {
        int databaseSizeBeforeUpdate = orderTypesRepository.findAll().size();
        orderTypes.setId(count.incrementAndGet());

        // Create the OrderTypes
        OrderTypesDTO orderTypesDTO = orderTypesMapper.toDto(orderTypes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderTypesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderTypes in the database
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderTypes() throws Exception {
        // Initialize the database
        orderTypesRepository.saveAndFlush(orderTypes);

        int databaseSizeBeforeDelete = orderTypesRepository.findAll().size();

        // Delete the orderTypes
        restOrderTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderTypes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderTypes> orderTypesList = orderTypesRepository.findAll();
        assertThat(orderTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
