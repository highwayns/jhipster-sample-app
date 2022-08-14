package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.ApiKeys;
import io.github.jhipster.sample.repository.ApiKeysRepository;
import io.github.jhipster.sample.service.dto.ApiKeysDTO;
import io.github.jhipster.sample.service.mapper.ApiKeysMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApiKeys}.
 */
@Service
@Transactional
public class ApiKeysService {

    private final Logger log = LoggerFactory.getLogger(ApiKeysService.class);

    private final ApiKeysRepository apiKeysRepository;

    private final ApiKeysMapper apiKeysMapper;

    public ApiKeysService(ApiKeysRepository apiKeysRepository, ApiKeysMapper apiKeysMapper) {
        this.apiKeysRepository = apiKeysRepository;
        this.apiKeysMapper = apiKeysMapper;
    }

    /**
     * Save a apiKeys.
     *
     * @param apiKeysDTO the entity to save.
     * @return the persisted entity.
     */
    public ApiKeysDTO save(ApiKeysDTO apiKeysDTO) {
        log.debug("Request to save ApiKeys : {}", apiKeysDTO);
        ApiKeys apiKeys = apiKeysMapper.toEntity(apiKeysDTO);
        apiKeys = apiKeysRepository.save(apiKeys);
        return apiKeysMapper.toDto(apiKeys);
    }

    /**
     * Update a apiKeys.
     *
     * @param apiKeysDTO the entity to save.
     * @return the persisted entity.
     */
    public ApiKeysDTO update(ApiKeysDTO apiKeysDTO) {
        log.debug("Request to save ApiKeys : {}", apiKeysDTO);
        ApiKeys apiKeys = apiKeysMapper.toEntity(apiKeysDTO);
        apiKeys = apiKeysRepository.save(apiKeys);
        return apiKeysMapper.toDto(apiKeys);
    }

    /**
     * Partially update a apiKeys.
     *
     * @param apiKeysDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApiKeysDTO> partialUpdate(ApiKeysDTO apiKeysDTO) {
        log.debug("Request to partially update ApiKeys : {}", apiKeysDTO);

        return apiKeysRepository
            .findById(apiKeysDTO.getId())
            .map(existingApiKeys -> {
                apiKeysMapper.partialUpdate(existingApiKeys, apiKeysDTO);

                return existingApiKeys;
            })
            .map(apiKeysRepository::save)
            .map(apiKeysMapper::toDto);
    }

    /**
     * Get all the apiKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApiKeysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiKeys");
        return apiKeysRepository.findAll(pageable).map(apiKeysMapper::toDto);
    }

    /**
     * Get one apiKeys by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApiKeysDTO> findOne(Long id) {
        log.debug("Request to get ApiKeys : {}", id);
        return apiKeysRepository.findById(id).map(apiKeysMapper::toDto);
    }

    /**
     * Delete the apiKeys by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiKeys : {}", id);
        apiKeysRepository.deleteById(id);
    }
}
