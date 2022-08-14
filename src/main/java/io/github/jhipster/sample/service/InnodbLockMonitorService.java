package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.InnodbLockMonitor;
import io.github.jhipster.sample.repository.InnodbLockMonitorRepository;
import io.github.jhipster.sample.service.dto.InnodbLockMonitorDTO;
import io.github.jhipster.sample.service.mapper.InnodbLockMonitorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InnodbLockMonitor}.
 */
@Service
@Transactional
public class InnodbLockMonitorService {

    private final Logger log = LoggerFactory.getLogger(InnodbLockMonitorService.class);

    private final InnodbLockMonitorRepository innodbLockMonitorRepository;

    private final InnodbLockMonitorMapper innodbLockMonitorMapper;

    public InnodbLockMonitorService(
        InnodbLockMonitorRepository innodbLockMonitorRepository,
        InnodbLockMonitorMapper innodbLockMonitorMapper
    ) {
        this.innodbLockMonitorRepository = innodbLockMonitorRepository;
        this.innodbLockMonitorMapper = innodbLockMonitorMapper;
    }

    /**
     * Save a innodbLockMonitor.
     *
     * @param innodbLockMonitorDTO the entity to save.
     * @return the persisted entity.
     */
    public InnodbLockMonitorDTO save(InnodbLockMonitorDTO innodbLockMonitorDTO) {
        log.debug("Request to save InnodbLockMonitor : {}", innodbLockMonitorDTO);
        InnodbLockMonitor innodbLockMonitor = innodbLockMonitorMapper.toEntity(innodbLockMonitorDTO);
        innodbLockMonitor = innodbLockMonitorRepository.save(innodbLockMonitor);
        return innodbLockMonitorMapper.toDto(innodbLockMonitor);
    }

    /**
     * Update a innodbLockMonitor.
     *
     * @param innodbLockMonitorDTO the entity to save.
     * @return the persisted entity.
     */
    public InnodbLockMonitorDTO update(InnodbLockMonitorDTO innodbLockMonitorDTO) {
        log.debug("Request to save InnodbLockMonitor : {}", innodbLockMonitorDTO);
        InnodbLockMonitor innodbLockMonitor = innodbLockMonitorMapper.toEntity(innodbLockMonitorDTO);
        innodbLockMonitor = innodbLockMonitorRepository.save(innodbLockMonitor);
        return innodbLockMonitorMapper.toDto(innodbLockMonitor);
    }

    /**
     * Partially update a innodbLockMonitor.
     *
     * @param innodbLockMonitorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InnodbLockMonitorDTO> partialUpdate(InnodbLockMonitorDTO innodbLockMonitorDTO) {
        log.debug("Request to partially update InnodbLockMonitor : {}", innodbLockMonitorDTO);

        return innodbLockMonitorRepository
            .findById(innodbLockMonitorDTO.getId())
            .map(existingInnodbLockMonitor -> {
                innodbLockMonitorMapper.partialUpdate(existingInnodbLockMonitor, innodbLockMonitorDTO);

                return existingInnodbLockMonitor;
            })
            .map(innodbLockMonitorRepository::save)
            .map(innodbLockMonitorMapper::toDto);
    }

    /**
     * Get all the innodbLockMonitors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InnodbLockMonitorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InnodbLockMonitors");
        return innodbLockMonitorRepository.findAll(pageable).map(innodbLockMonitorMapper::toDto);
    }

    /**
     * Get one innodbLockMonitor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InnodbLockMonitorDTO> findOne(Long id) {
        log.debug("Request to get InnodbLockMonitor : {}", id);
        return innodbLockMonitorRepository.findById(id).map(innodbLockMonitorMapper::toDto);
    }

    /**
     * Delete the innodbLockMonitor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InnodbLockMonitor : {}", id);
        innodbLockMonitorRepository.deleteById(id);
    }
}
