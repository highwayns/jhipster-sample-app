package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.HistoricalData;
import io.github.jhipster.sample.repository.HistoricalDataRepository;
import io.github.jhipster.sample.service.dto.HistoricalDataDTO;
import io.github.jhipster.sample.service.mapper.HistoricalDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HistoricalData}.
 */
@Service
@Transactional
public class HistoricalDataService {

    private final Logger log = LoggerFactory.getLogger(HistoricalDataService.class);

    private final HistoricalDataRepository historicalDataRepository;

    private final HistoricalDataMapper historicalDataMapper;

    public HistoricalDataService(HistoricalDataRepository historicalDataRepository, HistoricalDataMapper historicalDataMapper) {
        this.historicalDataRepository = historicalDataRepository;
        this.historicalDataMapper = historicalDataMapper;
    }

    /**
     * Save a historicalData.
     *
     * @param historicalDataDTO the entity to save.
     * @return the persisted entity.
     */
    public HistoricalDataDTO save(HistoricalDataDTO historicalDataDTO) {
        log.debug("Request to save HistoricalData : {}", historicalDataDTO);
        HistoricalData historicalData = historicalDataMapper.toEntity(historicalDataDTO);
        historicalData = historicalDataRepository.save(historicalData);
        return historicalDataMapper.toDto(historicalData);
    }

    /**
     * Update a historicalData.
     *
     * @param historicalDataDTO the entity to save.
     * @return the persisted entity.
     */
    public HistoricalDataDTO update(HistoricalDataDTO historicalDataDTO) {
        log.debug("Request to save HistoricalData : {}", historicalDataDTO);
        HistoricalData historicalData = historicalDataMapper.toEntity(historicalDataDTO);
        historicalData = historicalDataRepository.save(historicalData);
        return historicalDataMapper.toDto(historicalData);
    }

    /**
     * Partially update a historicalData.
     *
     * @param historicalDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HistoricalDataDTO> partialUpdate(HistoricalDataDTO historicalDataDTO) {
        log.debug("Request to partially update HistoricalData : {}", historicalDataDTO);

        return historicalDataRepository
            .findById(historicalDataDTO.getId())
            .map(existingHistoricalData -> {
                historicalDataMapper.partialUpdate(existingHistoricalData, historicalDataDTO);

                return existingHistoricalData;
            })
            .map(historicalDataRepository::save)
            .map(historicalDataMapper::toDto);
    }

    /**
     * Get all the historicalData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HistoricalDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HistoricalData");
        return historicalDataRepository.findAll(pageable).map(historicalDataMapper::toDto);
    }

    /**
     * Get one historicalData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HistoricalDataDTO> findOne(Long id) {
        log.debug("Request to get HistoricalData : {}", id);
        return historicalDataRepository.findById(id).map(historicalDataMapper::toDto);
    }

    /**
     * Delete the historicalData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HistoricalData : {}", id);
        historicalDataRepository.deleteById(id);
    }
}
