package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.DailyReports;
import io.github.jhipster.sample.repository.DailyReportsRepository;
import io.github.jhipster.sample.service.dto.DailyReportsDTO;
import io.github.jhipster.sample.service.mapper.DailyReportsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DailyReports}.
 */
@Service
@Transactional
public class DailyReportsService {

    private final Logger log = LoggerFactory.getLogger(DailyReportsService.class);

    private final DailyReportsRepository dailyReportsRepository;

    private final DailyReportsMapper dailyReportsMapper;

    public DailyReportsService(DailyReportsRepository dailyReportsRepository, DailyReportsMapper dailyReportsMapper) {
        this.dailyReportsRepository = dailyReportsRepository;
        this.dailyReportsMapper = dailyReportsMapper;
    }

    /**
     * Save a dailyReports.
     *
     * @param dailyReportsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyReportsDTO save(DailyReportsDTO dailyReportsDTO) {
        log.debug("Request to save DailyReports : {}", dailyReportsDTO);
        DailyReports dailyReports = dailyReportsMapper.toEntity(dailyReportsDTO);
        dailyReports = dailyReportsRepository.save(dailyReports);
        return dailyReportsMapper.toDto(dailyReports);
    }

    /**
     * Update a dailyReports.
     *
     * @param dailyReportsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyReportsDTO update(DailyReportsDTO dailyReportsDTO) {
        log.debug("Request to save DailyReports : {}", dailyReportsDTO);
        DailyReports dailyReports = dailyReportsMapper.toEntity(dailyReportsDTO);
        dailyReports = dailyReportsRepository.save(dailyReports);
        return dailyReportsMapper.toDto(dailyReports);
    }

    /**
     * Partially update a dailyReports.
     *
     * @param dailyReportsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DailyReportsDTO> partialUpdate(DailyReportsDTO dailyReportsDTO) {
        log.debug("Request to partially update DailyReports : {}", dailyReportsDTO);

        return dailyReportsRepository
            .findById(dailyReportsDTO.getId())
            .map(existingDailyReports -> {
                dailyReportsMapper.partialUpdate(existingDailyReports, dailyReportsDTO);

                return existingDailyReports;
            })
            .map(dailyReportsRepository::save)
            .map(dailyReportsMapper::toDto);
    }

    /**
     * Get all the dailyReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyReportsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DailyReports");
        return dailyReportsRepository.findAll(pageable).map(dailyReportsMapper::toDto);
    }

    /**
     * Get one dailyReports by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DailyReportsDTO> findOne(Long id) {
        log.debug("Request to get DailyReports : {}", id);
        return dailyReportsRepository.findById(id).map(dailyReportsMapper::toDto);
    }

    /**
     * Delete the dailyReports by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyReports : {}", id);
        dailyReportsRepository.deleteById(id);
    }
}
