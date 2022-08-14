package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.MonthlyReports;
import io.github.jhipster.sample.repository.MonthlyReportsRepository;
import io.github.jhipster.sample.service.dto.MonthlyReportsDTO;
import io.github.jhipster.sample.service.mapper.MonthlyReportsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MonthlyReports}.
 */
@Service
@Transactional
public class MonthlyReportsService {

    private final Logger log = LoggerFactory.getLogger(MonthlyReportsService.class);

    private final MonthlyReportsRepository monthlyReportsRepository;

    private final MonthlyReportsMapper monthlyReportsMapper;

    public MonthlyReportsService(MonthlyReportsRepository monthlyReportsRepository, MonthlyReportsMapper monthlyReportsMapper) {
        this.monthlyReportsRepository = monthlyReportsRepository;
        this.monthlyReportsMapper = monthlyReportsMapper;
    }

    /**
     * Save a monthlyReports.
     *
     * @param monthlyReportsDTO the entity to save.
     * @return the persisted entity.
     */
    public MonthlyReportsDTO save(MonthlyReportsDTO monthlyReportsDTO) {
        log.debug("Request to save MonthlyReports : {}", monthlyReportsDTO);
        MonthlyReports monthlyReports = monthlyReportsMapper.toEntity(monthlyReportsDTO);
        monthlyReports = monthlyReportsRepository.save(monthlyReports);
        return monthlyReportsMapper.toDto(monthlyReports);
    }

    /**
     * Update a monthlyReports.
     *
     * @param monthlyReportsDTO the entity to save.
     * @return the persisted entity.
     */
    public MonthlyReportsDTO update(MonthlyReportsDTO monthlyReportsDTO) {
        log.debug("Request to save MonthlyReports : {}", monthlyReportsDTO);
        MonthlyReports monthlyReports = monthlyReportsMapper.toEntity(monthlyReportsDTO);
        monthlyReports = monthlyReportsRepository.save(monthlyReports);
        return monthlyReportsMapper.toDto(monthlyReports);
    }

    /**
     * Partially update a monthlyReports.
     *
     * @param monthlyReportsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MonthlyReportsDTO> partialUpdate(MonthlyReportsDTO monthlyReportsDTO) {
        log.debug("Request to partially update MonthlyReports : {}", monthlyReportsDTO);

        return monthlyReportsRepository
            .findById(monthlyReportsDTO.getId())
            .map(existingMonthlyReports -> {
                monthlyReportsMapper.partialUpdate(existingMonthlyReports, monthlyReportsDTO);

                return existingMonthlyReports;
            })
            .map(monthlyReportsRepository::save)
            .map(monthlyReportsMapper::toDto);
    }

    /**
     * Get all the monthlyReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlyReportsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MonthlyReports");
        return monthlyReportsRepository.findAll(pageable).map(monthlyReportsMapper::toDto);
    }

    /**
     * Get one monthlyReports by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MonthlyReportsDTO> findOne(Long id) {
        log.debug("Request to get MonthlyReports : {}", id);
        return monthlyReportsRepository.findById(id).map(monthlyReportsMapper::toDto);
    }

    /**
     * Delete the monthlyReports by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlyReports : {}", id);
        monthlyReportsRepository.deleteById(id);
    }
}
