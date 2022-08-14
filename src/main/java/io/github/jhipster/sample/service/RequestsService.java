package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.Requests;
import io.github.jhipster.sample.repository.RequestsRepository;
import io.github.jhipster.sample.service.dto.RequestsDTO;
import io.github.jhipster.sample.service.mapper.RequestsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Requests}.
 */
@Service
@Transactional
public class RequestsService {

    private final Logger log = LoggerFactory.getLogger(RequestsService.class);

    private final RequestsRepository requestsRepository;

    private final RequestsMapper requestsMapper;

    public RequestsService(RequestsRepository requestsRepository, RequestsMapper requestsMapper) {
        this.requestsRepository = requestsRepository;
        this.requestsMapper = requestsMapper;
    }

    /**
     * Save a requests.
     *
     * @param requestsDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestsDTO save(RequestsDTO requestsDTO) {
        log.debug("Request to save Requests : {}", requestsDTO);
        Requests requests = requestsMapper.toEntity(requestsDTO);
        requests = requestsRepository.save(requests);
        return requestsMapper.toDto(requests);
    }

    /**
     * Update a requests.
     *
     * @param requestsDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestsDTO update(RequestsDTO requestsDTO) {
        log.debug("Request to save Requests : {}", requestsDTO);
        Requests requests = requestsMapper.toEntity(requestsDTO);
        requests = requestsRepository.save(requests);
        return requestsMapper.toDto(requests);
    }

    /**
     * Partially update a requests.
     *
     * @param requestsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestsDTO> partialUpdate(RequestsDTO requestsDTO) {
        log.debug("Request to partially update Requests : {}", requestsDTO);

        return requestsRepository
            .findById(requestsDTO.getId())
            .map(existingRequests -> {
                requestsMapper.partialUpdate(existingRequests, requestsDTO);

                return existingRequests;
            })
            .map(requestsRepository::save)
            .map(requestsMapper::toDto);
    }

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestsRepository.findAll(pageable).map(requestsMapper::toDto);
    }

    /**
     * Get one requests by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestsDTO> findOne(Long id) {
        log.debug("Request to get Requests : {}", id);
        return requestsRepository.findById(id).map(requestsMapper::toDto);
    }

    /**
     * Delete the requests by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Requests : {}", id);
        requestsRepository.deleteById(id);
    }
}
