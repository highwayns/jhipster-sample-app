package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.RequestTypes;
import io.github.jhipster.sample.repository.RequestTypesRepository;
import io.github.jhipster.sample.service.dto.RequestTypesDTO;
import io.github.jhipster.sample.service.mapper.RequestTypesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequestTypes}.
 */
@Service
@Transactional
public class RequestTypesService {

    private final Logger log = LoggerFactory.getLogger(RequestTypesService.class);

    private final RequestTypesRepository requestTypesRepository;

    private final RequestTypesMapper requestTypesMapper;

    public RequestTypesService(RequestTypesRepository requestTypesRepository, RequestTypesMapper requestTypesMapper) {
        this.requestTypesRepository = requestTypesRepository;
        this.requestTypesMapper = requestTypesMapper;
    }

    /**
     * Save a requestTypes.
     *
     * @param requestTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestTypesDTO save(RequestTypesDTO requestTypesDTO) {
        log.debug("Request to save RequestTypes : {}", requestTypesDTO);
        RequestTypes requestTypes = requestTypesMapper.toEntity(requestTypesDTO);
        requestTypes = requestTypesRepository.save(requestTypes);
        return requestTypesMapper.toDto(requestTypes);
    }

    /**
     * Update a requestTypes.
     *
     * @param requestTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestTypesDTO update(RequestTypesDTO requestTypesDTO) {
        log.debug("Request to save RequestTypes : {}", requestTypesDTO);
        RequestTypes requestTypes = requestTypesMapper.toEntity(requestTypesDTO);
        requestTypes = requestTypesRepository.save(requestTypes);
        return requestTypesMapper.toDto(requestTypes);
    }

    /**
     * Partially update a requestTypes.
     *
     * @param requestTypesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestTypesDTO> partialUpdate(RequestTypesDTO requestTypesDTO) {
        log.debug("Request to partially update RequestTypes : {}", requestTypesDTO);

        return requestTypesRepository
            .findById(requestTypesDTO.getId())
            .map(existingRequestTypes -> {
                requestTypesMapper.partialUpdate(existingRequestTypes, requestTypesDTO);

                return existingRequestTypes;
            })
            .map(requestTypesRepository::save)
            .map(requestTypesMapper::toDto);
    }

    /**
     * Get all the requestTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestTypesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestTypes");
        return requestTypesRepository.findAll(pageable).map(requestTypesMapper::toDto);
    }

    /**
     * Get one requestTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestTypesDTO> findOne(Long id) {
        log.debug("Request to get RequestTypes : {}", id);
        return requestTypesRepository.findById(id).map(requestTypesMapper::toDto);
    }

    /**
     * Delete the requestTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestTypes : {}", id);
        requestTypesRepository.deleteById(id);
    }
}
