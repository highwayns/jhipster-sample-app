package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.OrderTypes;
import io.github.jhipster.sample.repository.OrderTypesRepository;
import io.github.jhipster.sample.service.dto.OrderTypesDTO;
import io.github.jhipster.sample.service.mapper.OrderTypesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderTypes}.
 */
@Service
@Transactional
public class OrderTypesService {

    private final Logger log = LoggerFactory.getLogger(OrderTypesService.class);

    private final OrderTypesRepository orderTypesRepository;

    private final OrderTypesMapper orderTypesMapper;

    public OrderTypesService(OrderTypesRepository orderTypesRepository, OrderTypesMapper orderTypesMapper) {
        this.orderTypesRepository = orderTypesRepository;
        this.orderTypesMapper = orderTypesMapper;
    }

    /**
     * Save a orderTypes.
     *
     * @param orderTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderTypesDTO save(OrderTypesDTO orderTypesDTO) {
        log.debug("Request to save OrderTypes : {}", orderTypesDTO);
        OrderTypes orderTypes = orderTypesMapper.toEntity(orderTypesDTO);
        orderTypes = orderTypesRepository.save(orderTypes);
        return orderTypesMapper.toDto(orderTypes);
    }

    /**
     * Update a orderTypes.
     *
     * @param orderTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderTypesDTO update(OrderTypesDTO orderTypesDTO) {
        log.debug("Request to save OrderTypes : {}", orderTypesDTO);
        OrderTypes orderTypes = orderTypesMapper.toEntity(orderTypesDTO);
        orderTypes = orderTypesRepository.save(orderTypes);
        return orderTypesMapper.toDto(orderTypes);
    }

    /**
     * Partially update a orderTypes.
     *
     * @param orderTypesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderTypesDTO> partialUpdate(OrderTypesDTO orderTypesDTO) {
        log.debug("Request to partially update OrderTypes : {}", orderTypesDTO);

        return orderTypesRepository
            .findById(orderTypesDTO.getId())
            .map(existingOrderTypes -> {
                orderTypesMapper.partialUpdate(existingOrderTypes, orderTypesDTO);

                return existingOrderTypes;
            })
            .map(orderTypesRepository::save)
            .map(orderTypesMapper::toDto);
    }

    /**
     * Get all the orderTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderTypesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderTypes");
        return orderTypesRepository.findAll(pageable).map(orderTypesMapper::toDto);
    }

    /**
     * Get one orderTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderTypesDTO> findOne(Long id) {
        log.debug("Request to get OrderTypes : {}", id);
        return orderTypesRepository.findById(id).map(orderTypesMapper::toDto);
    }

    /**
     * Delete the orderTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderTypes : {}", id);
        orderTypesRepository.deleteById(id);
    }
}
