package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.OrderLog;
import io.github.jhipster.sample.repository.OrderLogRepository;
import io.github.jhipster.sample.service.dto.OrderLogDTO;
import io.github.jhipster.sample.service.mapper.OrderLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderLog}.
 */
@Service
@Transactional
public class OrderLogService {

    private final Logger log = LoggerFactory.getLogger(OrderLogService.class);

    private final OrderLogRepository orderLogRepository;

    private final OrderLogMapper orderLogMapper;

    public OrderLogService(OrderLogRepository orderLogRepository, OrderLogMapper orderLogMapper) {
        this.orderLogRepository = orderLogRepository;
        this.orderLogMapper = orderLogMapper;
    }

    /**
     * Save a orderLog.
     *
     * @param orderLogDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderLogDTO save(OrderLogDTO orderLogDTO) {
        log.debug("Request to save OrderLog : {}", orderLogDTO);
        OrderLog orderLog = orderLogMapper.toEntity(orderLogDTO);
        orderLog = orderLogRepository.save(orderLog);
        return orderLogMapper.toDto(orderLog);
    }

    /**
     * Update a orderLog.
     *
     * @param orderLogDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderLogDTO update(OrderLogDTO orderLogDTO) {
        log.debug("Request to save OrderLog : {}", orderLogDTO);
        OrderLog orderLog = orderLogMapper.toEntity(orderLogDTO);
        orderLog = orderLogRepository.save(orderLog);
        return orderLogMapper.toDto(orderLog);
    }

    /**
     * Partially update a orderLog.
     *
     * @param orderLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderLogDTO> partialUpdate(OrderLogDTO orderLogDTO) {
        log.debug("Request to partially update OrderLog : {}", orderLogDTO);

        return orderLogRepository
            .findById(orderLogDTO.getId())
            .map(existingOrderLog -> {
                orderLogMapper.partialUpdate(existingOrderLog, orderLogDTO);

                return existingOrderLog;
            })
            .map(orderLogRepository::save)
            .map(orderLogMapper::toDto);
    }

    /**
     * Get all the orderLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderLogs");
        return orderLogRepository.findAll(pageable).map(orderLogMapper::toDto);
    }

    /**
     * Get one orderLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderLogDTO> findOne(Long id) {
        log.debug("Request to get OrderLog : {}", id);
        return orderLogRepository.findById(id).map(orderLogMapper::toDto);
    }

    /**
     * Delete the orderLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderLog : {}", id);
        orderLogRepository.deleteById(id);
    }
}
