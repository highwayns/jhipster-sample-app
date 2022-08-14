package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.StatusEscrows;
import io.github.jhipster.sample.repository.StatusEscrowsRepository;
import io.github.jhipster.sample.service.dto.StatusEscrowsDTO;
import io.github.jhipster.sample.service.mapper.StatusEscrowsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatusEscrows}.
 */
@Service
@Transactional
public class StatusEscrowsService {

    private final Logger log = LoggerFactory.getLogger(StatusEscrowsService.class);

    private final StatusEscrowsRepository statusEscrowsRepository;

    private final StatusEscrowsMapper statusEscrowsMapper;

    public StatusEscrowsService(StatusEscrowsRepository statusEscrowsRepository, StatusEscrowsMapper statusEscrowsMapper) {
        this.statusEscrowsRepository = statusEscrowsRepository;
        this.statusEscrowsMapper = statusEscrowsMapper;
    }

    /**
     * Save a statusEscrows.
     *
     * @param statusEscrowsDTO the entity to save.
     * @return the persisted entity.
     */
    public StatusEscrowsDTO save(StatusEscrowsDTO statusEscrowsDTO) {
        log.debug("Request to save StatusEscrows : {}", statusEscrowsDTO);
        StatusEscrows statusEscrows = statusEscrowsMapper.toEntity(statusEscrowsDTO);
        statusEscrows = statusEscrowsRepository.save(statusEscrows);
        return statusEscrowsMapper.toDto(statusEscrows);
    }

    /**
     * Update a statusEscrows.
     *
     * @param statusEscrowsDTO the entity to save.
     * @return the persisted entity.
     */
    public StatusEscrowsDTO update(StatusEscrowsDTO statusEscrowsDTO) {
        log.debug("Request to save StatusEscrows : {}", statusEscrowsDTO);
        StatusEscrows statusEscrows = statusEscrowsMapper.toEntity(statusEscrowsDTO);
        statusEscrows = statusEscrowsRepository.save(statusEscrows);
        return statusEscrowsMapper.toDto(statusEscrows);
    }

    /**
     * Partially update a statusEscrows.
     *
     * @param statusEscrowsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StatusEscrowsDTO> partialUpdate(StatusEscrowsDTO statusEscrowsDTO) {
        log.debug("Request to partially update StatusEscrows : {}", statusEscrowsDTO);

        return statusEscrowsRepository
            .findById(statusEscrowsDTO.getId())
            .map(existingStatusEscrows -> {
                statusEscrowsMapper.partialUpdate(existingStatusEscrows, statusEscrowsDTO);

                return existingStatusEscrows;
            })
            .map(statusEscrowsRepository::save)
            .map(statusEscrowsMapper::toDto);
    }

    /**
     * Get all the statusEscrows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusEscrowsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusEscrows");
        return statusEscrowsRepository.findAll(pageable).map(statusEscrowsMapper::toDto);
    }

    /**
     * Get one statusEscrows by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatusEscrowsDTO> findOne(Long id) {
        log.debug("Request to get StatusEscrows : {}", id);
        return statusEscrowsRepository.findById(id).map(statusEscrowsMapper::toDto);
    }

    /**
     * Delete the statusEscrows by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StatusEscrows : {}", id);
        statusEscrowsRepository.deleteById(id);
    }
}
