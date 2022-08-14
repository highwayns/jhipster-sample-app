package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminOrder;
import io.github.jhipster.sample.repository.AdminOrderRepository;
import io.github.jhipster.sample.service.dto.AdminOrderDTO;
import io.github.jhipster.sample.service.mapper.AdminOrderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminOrder}.
 */
@Service
@Transactional
public class AdminOrderService {

    private final Logger log = LoggerFactory.getLogger(AdminOrderService.class);

    private final AdminOrderRepository adminOrderRepository;

    private final AdminOrderMapper adminOrderMapper;

    public AdminOrderService(AdminOrderRepository adminOrderRepository, AdminOrderMapper adminOrderMapper) {
        this.adminOrderRepository = adminOrderRepository;
        this.adminOrderMapper = adminOrderMapper;
    }

    /**
     * Save a adminOrder.
     *
     * @param adminOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminOrderDTO save(AdminOrderDTO adminOrderDTO) {
        log.debug("Request to save AdminOrder : {}", adminOrderDTO);
        AdminOrder adminOrder = adminOrderMapper.toEntity(adminOrderDTO);
        adminOrder = adminOrderRepository.save(adminOrder);
        return adminOrderMapper.toDto(adminOrder);
    }

    /**
     * Update a adminOrder.
     *
     * @param adminOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminOrderDTO update(AdminOrderDTO adminOrderDTO) {
        log.debug("Request to save AdminOrder : {}", adminOrderDTO);
        AdminOrder adminOrder = adminOrderMapper.toEntity(adminOrderDTO);
        adminOrder = adminOrderRepository.save(adminOrder);
        return adminOrderMapper.toDto(adminOrder);
    }

    /**
     * Partially update a adminOrder.
     *
     * @param adminOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminOrderDTO> partialUpdate(AdminOrderDTO adminOrderDTO) {
        log.debug("Request to partially update AdminOrder : {}", adminOrderDTO);

        return adminOrderRepository
            .findById(adminOrderDTO.getId())
            .map(existingAdminOrder -> {
                adminOrderMapper.partialUpdate(existingAdminOrder, adminOrderDTO);

                return existingAdminOrder;
            })
            .map(adminOrderRepository::save)
            .map(adminOrderMapper::toDto);
    }

    /**
     * Get all the adminOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminOrders");
        return adminOrderRepository.findAll(pageable).map(adminOrderMapper::toDto);
    }

    /**
     * Get one adminOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminOrderDTO> findOne(Long id) {
        log.debug("Request to get AdminOrder : {}", id);
        return adminOrderRepository.findById(id).map(adminOrderMapper::toDto);
    }

    /**
     * Delete the adminOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminOrder : {}", id);
        adminOrderRepository.deleteById(id);
    }
}
