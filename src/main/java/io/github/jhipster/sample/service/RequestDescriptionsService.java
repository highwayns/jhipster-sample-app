package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.RequestDescriptions;
import io.github.jhipster.sample.repository.RequestDescriptionsRepository;
import io.github.jhipster.sample.service.dto.RequestDescriptionsDTO;
import io.github.jhipster.sample.service.mapper.RequestDescriptionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequestDescriptions}.
 */
@Service
@Transactional
public class RequestDescriptionsService {

    private final Logger log = LoggerFactory.getLogger(RequestDescriptionsService.class);

    private final RequestDescriptionsRepository requestDescriptionsRepository;

    private final RequestDescriptionsMapper requestDescriptionsMapper;

    public RequestDescriptionsService(
        RequestDescriptionsRepository requestDescriptionsRepository,
        RequestDescriptionsMapper requestDescriptionsMapper
    ) {
        this.requestDescriptionsRepository = requestDescriptionsRepository;
        this.requestDescriptionsMapper = requestDescriptionsMapper;
    }

    /**
     * Save a requestDescriptions.
     *
     * @param requestDescriptionsDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestDescriptionsDTO save(RequestDescriptionsDTO requestDescriptionsDTO) {
        log.debug("Request to save RequestDescriptions : {}", requestDescriptionsDTO);
        RequestDescriptions requestDescriptions = requestDescriptionsMapper.toEntity(requestDescriptionsDTO);
        requestDescriptions = requestDescriptionsRepository.save(requestDescriptions);
        return requestDescriptionsMapper.toDto(requestDescriptions);
    }

    /**
     * Update a requestDescriptions.
     *
     * @param requestDescriptionsDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestDescriptionsDTO update(RequestDescriptionsDTO requestDescriptionsDTO) {
        log.debug("Request to save RequestDescriptions : {}", requestDescriptionsDTO);
        RequestDescriptions requestDescriptions = requestDescriptionsMapper.toEntity(requestDescriptionsDTO);
        requestDescriptions = requestDescriptionsRepository.save(requestDescriptions);
        return requestDescriptionsMapper.toDto(requestDescriptions);
    }

    /**
     * Partially update a requestDescriptions.
     *
     * @param requestDescriptionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestDescriptionsDTO> partialUpdate(RequestDescriptionsDTO requestDescriptionsDTO) {
        log.debug("Request to partially update RequestDescriptions : {}", requestDescriptionsDTO);

        return requestDescriptionsRepository
            .findById(requestDescriptionsDTO.getId())
            .map(existingRequestDescriptions -> {
                requestDescriptionsMapper.partialUpdate(existingRequestDescriptions, requestDescriptionsDTO);

                return existingRequestDescriptions;
            })
            .map(requestDescriptionsRepository::save)
            .map(requestDescriptionsMapper::toDto);
    }

    /**
     * Get all the requestDescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestDescriptionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestDescriptions");
        return requestDescriptionsRepository.findAll(pageable).map(requestDescriptionsMapper::toDto);
    }

    /**
     * Get one requestDescriptions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestDescriptionsDTO> findOne(Long id) {
        log.debug("Request to get RequestDescriptions : {}", id);
        return requestDescriptionsRepository.findById(id).map(requestDescriptionsMapper::toDto);
    }

    /**
     * Delete the requestDescriptions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestDescriptions : {}", id);
        requestDescriptionsRepository.deleteById(id);
    }
}
