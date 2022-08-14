package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.RequestStatus;
import io.github.jhipster.sample.repository.RequestStatusRepository;
import io.github.jhipster.sample.service.dto.RequestStatusDTO;
import io.github.jhipster.sample.service.mapper.RequestStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequestStatus}.
 */
@Service
@Transactional
public class RequestStatusService {

    private final Logger log = LoggerFactory.getLogger(RequestStatusService.class);

    private final RequestStatusRepository requestStatusRepository;

    private final RequestStatusMapper requestStatusMapper;

    public RequestStatusService(RequestStatusRepository requestStatusRepository, RequestStatusMapper requestStatusMapper) {
        this.requestStatusRepository = requestStatusRepository;
        this.requestStatusMapper = requestStatusMapper;
    }

    /**
     * Save a requestStatus.
     *
     * @param requestStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestStatusDTO save(RequestStatusDTO requestStatusDTO) {
        log.debug("Request to save RequestStatus : {}", requestStatusDTO);
        RequestStatus requestStatus = requestStatusMapper.toEntity(requestStatusDTO);
        requestStatus = requestStatusRepository.save(requestStatus);
        return requestStatusMapper.toDto(requestStatus);
    }

    /**
     * Update a requestStatus.
     *
     * @param requestStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestStatusDTO update(RequestStatusDTO requestStatusDTO) {
        log.debug("Request to save RequestStatus : {}", requestStatusDTO);
        RequestStatus requestStatus = requestStatusMapper.toEntity(requestStatusDTO);
        requestStatus = requestStatusRepository.save(requestStatus);
        return requestStatusMapper.toDto(requestStatus);
    }

    /**
     * Partially update a requestStatus.
     *
     * @param requestStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestStatusDTO> partialUpdate(RequestStatusDTO requestStatusDTO) {
        log.debug("Request to partially update RequestStatus : {}", requestStatusDTO);

        return requestStatusRepository
            .findById(requestStatusDTO.getId())
            .map(existingRequestStatus -> {
                requestStatusMapper.partialUpdate(existingRequestStatus, requestStatusDTO);

                return existingRequestStatus;
            })
            .map(requestStatusRepository::save)
            .map(requestStatusMapper::toDto);
    }

    /**
     * Get all the requestStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestStatuses");
        return requestStatusRepository.findAll(pageable).map(requestStatusMapper::toDto);
    }

    /**
     * Get one requestStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestStatusDTO> findOne(Long id) {
        log.debug("Request to get RequestStatus : {}", id);
        return requestStatusRepository.findById(id).map(requestStatusMapper::toDto);
    }

    /**
     * Delete the requestStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestStatus : {}", id);
        requestStatusRepository.deleteById(id);
    }
}
