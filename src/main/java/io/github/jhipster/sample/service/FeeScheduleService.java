package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.FeeSchedule;
import io.github.jhipster.sample.repository.FeeScheduleRepository;
import io.github.jhipster.sample.service.dto.FeeScheduleDTO;
import io.github.jhipster.sample.service.mapper.FeeScheduleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeeSchedule}.
 */
@Service
@Transactional
public class FeeScheduleService {

    private final Logger log = LoggerFactory.getLogger(FeeScheduleService.class);

    private final FeeScheduleRepository feeScheduleRepository;

    private final FeeScheduleMapper feeScheduleMapper;

    public FeeScheduleService(FeeScheduleRepository feeScheduleRepository, FeeScheduleMapper feeScheduleMapper) {
        this.feeScheduleRepository = feeScheduleRepository;
        this.feeScheduleMapper = feeScheduleMapper;
    }

    /**
     * Save a feeSchedule.
     *
     * @param feeScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    public FeeScheduleDTO save(FeeScheduleDTO feeScheduleDTO) {
        log.debug("Request to save FeeSchedule : {}", feeScheduleDTO);
        FeeSchedule feeSchedule = feeScheduleMapper.toEntity(feeScheduleDTO);
        feeSchedule = feeScheduleRepository.save(feeSchedule);
        return feeScheduleMapper.toDto(feeSchedule);
    }

    /**
     * Update a feeSchedule.
     *
     * @param feeScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    public FeeScheduleDTO update(FeeScheduleDTO feeScheduleDTO) {
        log.debug("Request to save FeeSchedule : {}", feeScheduleDTO);
        FeeSchedule feeSchedule = feeScheduleMapper.toEntity(feeScheduleDTO);
        feeSchedule = feeScheduleRepository.save(feeSchedule);
        return feeScheduleMapper.toDto(feeSchedule);
    }

    /**
     * Partially update a feeSchedule.
     *
     * @param feeScheduleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeeScheduleDTO> partialUpdate(FeeScheduleDTO feeScheduleDTO) {
        log.debug("Request to partially update FeeSchedule : {}", feeScheduleDTO);

        return feeScheduleRepository
            .findById(feeScheduleDTO.getId())
            .map(existingFeeSchedule -> {
                feeScheduleMapper.partialUpdate(existingFeeSchedule, feeScheduleDTO);

                return existingFeeSchedule;
            })
            .map(feeScheduleRepository::save)
            .map(feeScheduleMapper::toDto);
    }

    /**
     * Get all the feeSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeeScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FeeSchedules");
        return feeScheduleRepository.findAll(pageable).map(feeScheduleMapper::toDto);
    }

    /**
     * Get one feeSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeeScheduleDTO> findOne(Long id) {
        log.debug("Request to get FeeSchedule : {}", id);
        return feeScheduleRepository.findById(id).map(feeScheduleMapper::toDto);
    }

    /**
     * Delete the feeSchedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeeSchedule : {}", id);
        feeScheduleRepository.deleteById(id);
    }
}
