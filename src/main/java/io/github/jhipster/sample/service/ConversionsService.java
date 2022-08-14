package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.Conversions;
import io.github.jhipster.sample.repository.ConversionsRepository;
import io.github.jhipster.sample.service.dto.ConversionsDTO;
import io.github.jhipster.sample.service.mapper.ConversionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Conversions}.
 */
@Service
@Transactional
public class ConversionsService {

    private final Logger log = LoggerFactory.getLogger(ConversionsService.class);

    private final ConversionsRepository conversionsRepository;

    private final ConversionsMapper conversionsMapper;

    public ConversionsService(ConversionsRepository conversionsRepository, ConversionsMapper conversionsMapper) {
        this.conversionsRepository = conversionsRepository;
        this.conversionsMapper = conversionsMapper;
    }

    /**
     * Save a conversions.
     *
     * @param conversionsDTO the entity to save.
     * @return the persisted entity.
     */
    public ConversionsDTO save(ConversionsDTO conversionsDTO) {
        log.debug("Request to save Conversions : {}", conversionsDTO);
        Conversions conversions = conversionsMapper.toEntity(conversionsDTO);
        conversions = conversionsRepository.save(conversions);
        return conversionsMapper.toDto(conversions);
    }

    /**
     * Update a conversions.
     *
     * @param conversionsDTO the entity to save.
     * @return the persisted entity.
     */
    public ConversionsDTO update(ConversionsDTO conversionsDTO) {
        log.debug("Request to save Conversions : {}", conversionsDTO);
        Conversions conversions = conversionsMapper.toEntity(conversionsDTO);
        conversions = conversionsRepository.save(conversions);
        return conversionsMapper.toDto(conversions);
    }

    /**
     * Partially update a conversions.
     *
     * @param conversionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConversionsDTO> partialUpdate(ConversionsDTO conversionsDTO) {
        log.debug("Request to partially update Conversions : {}", conversionsDTO);

        return conversionsRepository
            .findById(conversionsDTO.getId())
            .map(existingConversions -> {
                conversionsMapper.partialUpdate(existingConversions, conversionsDTO);

                return existingConversions;
            })
            .map(conversionsRepository::save)
            .map(conversionsMapper::toDto);
    }

    /**
     * Get all the conversions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConversionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Conversions");
        return conversionsRepository.findAll(pageable).map(conversionsMapper::toDto);
    }

    /**
     * Get one conversions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConversionsDTO> findOne(Long id) {
        log.debug("Request to get Conversions : {}", id);
        return conversionsRepository.findById(id).map(conversionsMapper::toDto);
    }

    /**
     * Delete the conversions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conversions : {}", id);
        conversionsRepository.deleteById(id);
    }
}
