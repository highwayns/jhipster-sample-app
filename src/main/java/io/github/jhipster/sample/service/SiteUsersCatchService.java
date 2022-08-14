package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.SiteUsersCatch;
import io.github.jhipster.sample.repository.SiteUsersCatchRepository;
import io.github.jhipster.sample.service.dto.SiteUsersCatchDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersCatchMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SiteUsersCatch}.
 */
@Service
@Transactional
public class SiteUsersCatchService {

    private final Logger log = LoggerFactory.getLogger(SiteUsersCatchService.class);

    private final SiteUsersCatchRepository siteUsersCatchRepository;

    private final SiteUsersCatchMapper siteUsersCatchMapper;

    public SiteUsersCatchService(SiteUsersCatchRepository siteUsersCatchRepository, SiteUsersCatchMapper siteUsersCatchMapper) {
        this.siteUsersCatchRepository = siteUsersCatchRepository;
        this.siteUsersCatchMapper = siteUsersCatchMapper;
    }

    /**
     * Save a siteUsersCatch.
     *
     * @param siteUsersCatchDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersCatchDTO save(SiteUsersCatchDTO siteUsersCatchDTO) {
        log.debug("Request to save SiteUsersCatch : {}", siteUsersCatchDTO);
        SiteUsersCatch siteUsersCatch = siteUsersCatchMapper.toEntity(siteUsersCatchDTO);
        siteUsersCatch = siteUsersCatchRepository.save(siteUsersCatch);
        return siteUsersCatchMapper.toDto(siteUsersCatch);
    }

    /**
     * Update a siteUsersCatch.
     *
     * @param siteUsersCatchDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersCatchDTO update(SiteUsersCatchDTO siteUsersCatchDTO) {
        log.debug("Request to save SiteUsersCatch : {}", siteUsersCatchDTO);
        SiteUsersCatch siteUsersCatch = siteUsersCatchMapper.toEntity(siteUsersCatchDTO);
        siteUsersCatch = siteUsersCatchRepository.save(siteUsersCatch);
        return siteUsersCatchMapper.toDto(siteUsersCatch);
    }

    /**
     * Partially update a siteUsersCatch.
     *
     * @param siteUsersCatchDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiteUsersCatchDTO> partialUpdate(SiteUsersCatchDTO siteUsersCatchDTO) {
        log.debug("Request to partially update SiteUsersCatch : {}", siteUsersCatchDTO);

        return siteUsersCatchRepository
            .findById(siteUsersCatchDTO.getId())
            .map(existingSiteUsersCatch -> {
                siteUsersCatchMapper.partialUpdate(existingSiteUsersCatch, siteUsersCatchDTO);

                return existingSiteUsersCatch;
            })
            .map(siteUsersCatchRepository::save)
            .map(siteUsersCatchMapper::toDto);
    }

    /**
     * Get all the siteUsersCatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteUsersCatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteUsersCatches");
        return siteUsersCatchRepository.findAll(pageable).map(siteUsersCatchMapper::toDto);
    }

    /**
     * Get one siteUsersCatch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteUsersCatchDTO> findOne(Long id) {
        log.debug("Request to get SiteUsersCatch : {}", id);
        return siteUsersCatchRepository.findById(id).map(siteUsersCatchMapper::toDto);
    }

    /**
     * Delete the siteUsersCatch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteUsersCatch : {}", id);
        siteUsersCatchRepository.deleteById(id);
    }
}
