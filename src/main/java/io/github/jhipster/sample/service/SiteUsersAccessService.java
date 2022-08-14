package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.SiteUsersAccess;
import io.github.jhipster.sample.repository.SiteUsersAccessRepository;
import io.github.jhipster.sample.service.dto.SiteUsersAccessDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersAccessMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SiteUsersAccess}.
 */
@Service
@Transactional
public class SiteUsersAccessService {

    private final Logger log = LoggerFactory.getLogger(SiteUsersAccessService.class);

    private final SiteUsersAccessRepository siteUsersAccessRepository;

    private final SiteUsersAccessMapper siteUsersAccessMapper;

    public SiteUsersAccessService(SiteUsersAccessRepository siteUsersAccessRepository, SiteUsersAccessMapper siteUsersAccessMapper) {
        this.siteUsersAccessRepository = siteUsersAccessRepository;
        this.siteUsersAccessMapper = siteUsersAccessMapper;
    }

    /**
     * Save a siteUsersAccess.
     *
     * @param siteUsersAccessDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersAccessDTO save(SiteUsersAccessDTO siteUsersAccessDTO) {
        log.debug("Request to save SiteUsersAccess : {}", siteUsersAccessDTO);
        SiteUsersAccess siteUsersAccess = siteUsersAccessMapper.toEntity(siteUsersAccessDTO);
        siteUsersAccess = siteUsersAccessRepository.save(siteUsersAccess);
        return siteUsersAccessMapper.toDto(siteUsersAccess);
    }

    /**
     * Update a siteUsersAccess.
     *
     * @param siteUsersAccessDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersAccessDTO update(SiteUsersAccessDTO siteUsersAccessDTO) {
        log.debug("Request to save SiteUsersAccess : {}", siteUsersAccessDTO);
        SiteUsersAccess siteUsersAccess = siteUsersAccessMapper.toEntity(siteUsersAccessDTO);
        siteUsersAccess = siteUsersAccessRepository.save(siteUsersAccess);
        return siteUsersAccessMapper.toDto(siteUsersAccess);
    }

    /**
     * Partially update a siteUsersAccess.
     *
     * @param siteUsersAccessDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiteUsersAccessDTO> partialUpdate(SiteUsersAccessDTO siteUsersAccessDTO) {
        log.debug("Request to partially update SiteUsersAccess : {}", siteUsersAccessDTO);

        return siteUsersAccessRepository
            .findById(siteUsersAccessDTO.getId())
            .map(existingSiteUsersAccess -> {
                siteUsersAccessMapper.partialUpdate(existingSiteUsersAccess, siteUsersAccessDTO);

                return existingSiteUsersAccess;
            })
            .map(siteUsersAccessRepository::save)
            .map(siteUsersAccessMapper::toDto);
    }

    /**
     * Get all the siteUsersAccesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteUsersAccessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteUsersAccesses");
        return siteUsersAccessRepository.findAll(pageable).map(siteUsersAccessMapper::toDto);
    }

    /**
     * Get one siteUsersAccess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteUsersAccessDTO> findOne(Long id) {
        log.debug("Request to get SiteUsersAccess : {}", id);
        return siteUsersAccessRepository.findById(id).map(siteUsersAccessMapper::toDto);
    }

    /**
     * Delete the siteUsersAccess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteUsersAccess : {}", id);
        siteUsersAccessRepository.deleteById(id);
    }
}
