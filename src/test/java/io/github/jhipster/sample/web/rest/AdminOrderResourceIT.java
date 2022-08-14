package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AdminOrder;
import io.github.jhipster.sample.repository.AdminOrderRepository;
import io.github.jhipster.sample.service.dto.AdminOrderDTO;
import io.github.jhipster.sample.service.mapper.AdminOrderMapper;
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
 * Integration tests for the {@link AdminOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminOrderResourceIT {

    private static final String DEFAULT_ORDER_BY = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_BY = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_ASC = 1;
    private static final Integer UPDATED_ORDER_ASC = 2;

    private static final String ENTITY_API_URL = "/api/admin-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminOrderRepository adminOrderRepository;

    @Autowired
    private AdminOrderMapper adminOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminOrderMockMvc;

    private AdminOrder adminOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminOrder createEntity(EntityManager em) {
        AdminOrder adminOrder = new AdminOrder().orderBy(DEFAULT_ORDER_BY).orderAsc(DEFAULT_ORDER_ASC);
        return adminOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminOrder createUpdatedEntity(EntityManager em) {
        AdminOrder adminOrder = new AdminOrder().orderBy(UPDATED_ORDER_BY).orderAsc(UPDATED_ORDER_ASC);
        return adminOrder;
    }

    @BeforeEach
    public void initTest() {
        adminOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminOrder() throws Exception {
        int databaseSizeBeforeCreate = adminOrderRepository.findAll().size();
        // Create the AdminOrder
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);
        restAdminOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeCreate + 1);
        AdminOrder testAdminOrder = adminOrderList.get(adminOrderList.size() - 1);
        assertThat(testAdminOrder.getOrderBy()).isEqualTo(DEFAULT_ORDER_BY);
        assertThat(testAdminOrder.getOrderAsc()).isEqualTo(DEFAULT_ORDER_ASC);
    }

    @Test
    @Transactional
    void createAdminOrderWithExistingId() throws Exception {
        // Create the AdminOrder with an existing ID
        adminOrder.setId(1L);
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        int databaseSizeBeforeCreate = adminOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOrderByIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminOrderRepository.findAll().size();
        // set the field null
        adminOrder.setOrderBy(null);

        // Create the AdminOrder, which fails.
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        restAdminOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminOrderDTO)))
            .andExpect(status().isBadRequest());

        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderAscIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminOrderRepository.findAll().size();
        // set the field null
        adminOrder.setOrderAsc(null);

        // Create the AdminOrder, which fails.
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        restAdminOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminOrderDTO)))
            .andExpect(status().isBadRequest());

        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminOrders() throws Exception {
        // Initialize the database
        adminOrderRepository.saveAndFlush(adminOrder);

        // Get all the adminOrderList
        restAdminOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderBy").value(hasItem(DEFAULT_ORDER_BY)))
            .andExpect(jsonPath("$.[*].orderAsc").value(hasItem(DEFAULT_ORDER_ASC)));
    }

    @Test
    @Transactional
    void getAdminOrder() throws Exception {
        // Initialize the database
        adminOrderRepository.saveAndFlush(adminOrder);

        // Get the adminOrder
        restAdminOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, adminOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderBy").value(DEFAULT_ORDER_BY))
            .andExpect(jsonPath("$.orderAsc").value(DEFAULT_ORDER_ASC));
    }

    @Test
    @Transactional
    void getNonExistingAdminOrder() throws Exception {
        // Get the adminOrder
        restAdminOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminOrder() throws Exception {
        // Initialize the database
        adminOrderRepository.saveAndFlush(adminOrder);

        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();

        // Update the adminOrder
        AdminOrder updatedAdminOrder = adminOrderRepository.findById(adminOrder.getId()).get();
        // Disconnect from session so that the updates on updatedAdminOrder are not directly saved in db
        em.detach(updatedAdminOrder);
        updatedAdminOrder.orderBy(UPDATED_ORDER_BY).orderAsc(UPDATED_ORDER_ASC);
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(updatedAdminOrder);

        restAdminOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
        AdminOrder testAdminOrder = adminOrderList.get(adminOrderList.size() - 1);
        assertThat(testAdminOrder.getOrderBy()).isEqualTo(UPDATED_ORDER_BY);
        assertThat(testAdminOrder.getOrderAsc()).isEqualTo(UPDATED_ORDER_ASC);
    }

    @Test
    @Transactional
    void putNonExistingAdminOrder() throws Exception {
        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();
        adminOrder.setId(count.incrementAndGet());

        // Create the AdminOrder
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminOrder() throws Exception {
        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();
        adminOrder.setId(count.incrementAndGet());

        // Create the AdminOrder
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminOrder() throws Exception {
        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();
        adminOrder.setId(count.incrementAndGet());

        // Create the AdminOrder
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminOrderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminOrderWithPatch() throws Exception {
        // Initialize the database
        adminOrderRepository.saveAndFlush(adminOrder);

        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();

        // Update the adminOrder using partial update
        AdminOrder partialUpdatedAdminOrder = new AdminOrder();
        partialUpdatedAdminOrder.setId(adminOrder.getId());

        partialUpdatedAdminOrder.orderBy(UPDATED_ORDER_BY).orderAsc(UPDATED_ORDER_ASC);

        restAdminOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminOrder))
            )
            .andExpect(status().isOk());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
        AdminOrder testAdminOrder = adminOrderList.get(adminOrderList.size() - 1);
        assertThat(testAdminOrder.getOrderBy()).isEqualTo(UPDATED_ORDER_BY);
        assertThat(testAdminOrder.getOrderAsc()).isEqualTo(UPDATED_ORDER_ASC);
    }

    @Test
    @Transactional
    void fullUpdateAdminOrderWithPatch() throws Exception {
        // Initialize the database
        adminOrderRepository.saveAndFlush(adminOrder);

        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();

        // Update the adminOrder using partial update
        AdminOrder partialUpdatedAdminOrder = new AdminOrder();
        partialUpdatedAdminOrder.setId(adminOrder.getId());

        partialUpdatedAdminOrder.orderBy(UPDATED_ORDER_BY).orderAsc(UPDATED_ORDER_ASC);

        restAdminOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminOrder))
            )
            .andExpect(status().isOk());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
        AdminOrder testAdminOrder = adminOrderList.get(adminOrderList.size() - 1);
        assertThat(testAdminOrder.getOrderBy()).isEqualTo(UPDATED_ORDER_BY);
        assertThat(testAdminOrder.getOrderAsc()).isEqualTo(UPDATED_ORDER_ASC);
    }

    @Test
    @Transactional
    void patchNonExistingAdminOrder() throws Exception {
        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();
        adminOrder.setId(count.incrementAndGet());

        // Create the AdminOrder
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminOrderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminOrder() throws Exception {
        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();
        adminOrder.setId(count.incrementAndGet());

        // Create the AdminOrder
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminOrder() throws Exception {
        int databaseSizeBeforeUpdate = adminOrderRepository.findAll().size();
        adminOrder.setId(count.incrementAndGet());

        // Create the AdminOrder
        AdminOrderDTO adminOrderDTO = adminOrderMapper.toDto(adminOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminOrderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adminOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminOrder in the database
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminOrder() throws Exception {
        // Initialize the database
        adminOrderRepository.saveAndFlush(adminOrder);

        int databaseSizeBeforeDelete = adminOrderRepository.findAll().size();

        // Delete the adminOrder
        restAdminOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminOrder> adminOrderList = adminOrderRepository.findAll();
        assertThat(adminOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
