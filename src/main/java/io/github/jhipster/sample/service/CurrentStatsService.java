package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.CurrentStats;
import io.github.jhipster.sample.repository.CurrentStatsRepository;
import io.github.jhipster.sample.service.dto.CurrentStatsDTO;
import io.github.jhipster.sample.service.mapper.CurrentStatsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CurrentStats}.
 */
@Service
@Transactional
public class CurrentStatsService {

    private final Logger log = LoggerFactory.getLogger(CurrentStatsService.class);

    private final CurrentStatsRepository currentStatsRepository;

    private final CurrentStatsMapper currentStatsMapper;

    public CurrentStatsService(CurrentStatsRepository currentStatsRepository, CurrentStatsMapper currentStatsMapper) {
        this.currentStatsRepository = currentStatsRepository;
        this.currentStatsMapper = currentStatsMapper;
    }

    /**
     * Save a currentStats.
     *
     * @param currentStatsDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrentStatsDTO save(CurrentStatsDTO currentStatsDTO) {
        log.debug("Request to save CurrentStats : {}", currentStatsDTO);
        CurrentStats currentStats = currentStatsMapper.toEntity(currentStatsDTO);
        currentStats = currentStatsRepository.save(currentStats);
        return currentStatsMapper.toDto(currentStats);
    }

    /**
     * Update a currentStats.
     *
     * @param currentStatsDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrentStatsDTO update(CurrentStatsDTO currentStatsDTO) {
        log.debug("Request to save CurrentStats : {}", currentStatsDTO);
        CurrentStats currentStats = currentStatsMapper.toEntity(currentStatsDTO);
        currentStats = currentStatsRepository.save(currentStats);
        return currentStatsMapper.toDto(currentStats);
    }

    /**
     * Partially update a currentStats.
     *
     * @param currentStatsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CurrentStatsDTO> partialUpdate(CurrentStatsDTO currentStatsDTO) {
        log.debug("Request to partially update CurrentStats : {}", currentStatsDTO);

        return currentStatsRepository
            .findById(currentStatsDTO.getId())
            .map(existingCurrentStats -> {
                currentStatsMapper.partialUpdate(existingCurrentStats, currentStatsDTO);

                return existingCurrentStats;
            })
            .map(currentStatsRepository::save)
            .map(currentStatsMapper::toDto);
    }

    /**
     * Get all the currentStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrentStatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CurrentStats");
        return currentStatsRepository.findAll(pageable).map(currentStatsMapper::toDto);
    }

    /**
     * Get one currentStats by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CurrentStatsDTO> findOne(Long id) {
        log.debug("Request to get CurrentStats : {}", id);
        return currentStatsRepository.findById(id).map(currentStatsMapper::toDto);
    }

    /**
     * Delete the currentStats by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CurrentStats : {}", id);
        currentStatsRepository.deleteById(id);
    }
}
