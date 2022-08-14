package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.HistoryActions;
import io.github.jhipster.sample.repository.HistoryActionsRepository;
import io.github.jhipster.sample.service.dto.HistoryActionsDTO;
import io.github.jhipster.sample.service.mapper.HistoryActionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HistoryActions}.
 */
@Service
@Transactional
public class HistoryActionsService {

    private final Logger log = LoggerFactory.getLogger(HistoryActionsService.class);

    private final HistoryActionsRepository historyActionsRepository;

    private final HistoryActionsMapper historyActionsMapper;

    public HistoryActionsService(HistoryActionsRepository historyActionsRepository, HistoryActionsMapper historyActionsMapper) {
        this.historyActionsRepository = historyActionsRepository;
        this.historyActionsMapper = historyActionsMapper;
    }

    /**
     * Save a historyActions.
     *
     * @param historyActionsDTO the entity to save.
     * @return the persisted entity.
     */
    public HistoryActionsDTO save(HistoryActionsDTO historyActionsDTO) {
        log.debug("Request to save HistoryActions : {}", historyActionsDTO);
        HistoryActions historyActions = historyActionsMapper.toEntity(historyActionsDTO);
        historyActions = historyActionsRepository.save(historyActions);
        return historyActionsMapper.toDto(historyActions);
    }

    /**
     * Update a historyActions.
     *
     * @param historyActionsDTO the entity to save.
     * @return the persisted entity.
     */
    public HistoryActionsDTO update(HistoryActionsDTO historyActionsDTO) {
        log.debug("Request to save HistoryActions : {}", historyActionsDTO);
        HistoryActions historyActions = historyActionsMapper.toEntity(historyActionsDTO);
        historyActions = historyActionsRepository.save(historyActions);
        return historyActionsMapper.toDto(historyActions);
    }

    /**
     * Partially update a historyActions.
     *
     * @param historyActionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HistoryActionsDTO> partialUpdate(HistoryActionsDTO historyActionsDTO) {
        log.debug("Request to partially update HistoryActions : {}", historyActionsDTO);

        return historyActionsRepository
            .findById(historyActionsDTO.getId())
            .map(existingHistoryActions -> {
                historyActionsMapper.partialUpdate(existingHistoryActions, historyActionsDTO);

                return existingHistoryActions;
            })
            .map(historyActionsRepository::save)
            .map(historyActionsMapper::toDto);
    }

    /**
     * Get all the historyActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HistoryActionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HistoryActions");
        return historyActionsRepository.findAll(pageable).map(historyActionsMapper::toDto);
    }

    /**
     * Get one historyActions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HistoryActionsDTO> findOne(Long id) {
        log.debug("Request to get HistoryActions : {}", id);
        return historyActionsRepository.findById(id).map(historyActionsMapper::toDto);
    }

    /**
     * Delete the historyActions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HistoryActions : {}", id);
        historyActionsRepository.deleteById(id);
    }
}
