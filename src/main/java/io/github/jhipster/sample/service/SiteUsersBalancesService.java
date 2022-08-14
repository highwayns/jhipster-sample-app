package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.SiteUsersBalances;
import io.github.jhipster.sample.repository.SiteUsersBalancesRepository;
import io.github.jhipster.sample.service.dto.SiteUsersBalancesDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersBalancesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SiteUsersBalances}.
 */
@Service
@Transactional
public class SiteUsersBalancesService {

    private final Logger log = LoggerFactory.getLogger(SiteUsersBalancesService.class);

    private final SiteUsersBalancesRepository siteUsersBalancesRepository;

    private final SiteUsersBalancesMapper siteUsersBalancesMapper;

    public SiteUsersBalancesService(
        SiteUsersBalancesRepository siteUsersBalancesRepository,
        SiteUsersBalancesMapper siteUsersBalancesMapper
    ) {
        this.siteUsersBalancesRepository = siteUsersBalancesRepository;
        this.siteUsersBalancesMapper = siteUsersBalancesMapper;
    }

    /**
     * Save a siteUsersBalances.
     *
     * @param siteUsersBalancesDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersBalancesDTO save(SiteUsersBalancesDTO siteUsersBalancesDTO) {
        log.debug("Request to save SiteUsersBalances : {}", siteUsersBalancesDTO);
        SiteUsersBalances siteUsersBalances = siteUsersBalancesMapper.toEntity(siteUsersBalancesDTO);
        siteUsersBalances = siteUsersBalancesRepository.save(siteUsersBalances);
        return siteUsersBalancesMapper.toDto(siteUsersBalances);
    }

    /**
     * Update a siteUsersBalances.
     *
     * @param siteUsersBalancesDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersBalancesDTO update(SiteUsersBalancesDTO siteUsersBalancesDTO) {
        log.debug("Request to save SiteUsersBalances : {}", siteUsersBalancesDTO);
        SiteUsersBalances siteUsersBalances = siteUsersBalancesMapper.toEntity(siteUsersBalancesDTO);
        siteUsersBalances = siteUsersBalancesRepository.save(siteUsersBalances);
        return siteUsersBalancesMapper.toDto(siteUsersBalances);
    }

    /**
     * Partially update a siteUsersBalances.
     *
     * @param siteUsersBalancesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiteUsersBalancesDTO> partialUpdate(SiteUsersBalancesDTO siteUsersBalancesDTO) {
        log.debug("Request to partially update SiteUsersBalances : {}", siteUsersBalancesDTO);

        return siteUsersBalancesRepository
            .findById(siteUsersBalancesDTO.getId())
            .map(existingSiteUsersBalances -> {
                siteUsersBalancesMapper.partialUpdate(existingSiteUsersBalances, siteUsersBalancesDTO);

                return existingSiteUsersBalances;
            })
            .map(siteUsersBalancesRepository::save)
            .map(siteUsersBalancesMapper::toDto);
    }

    /**
     * Get all the siteUsersBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteUsersBalancesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteUsersBalances");
        return siteUsersBalancesRepository.findAll(pageable).map(siteUsersBalancesMapper::toDto);
    }

    /**
     * Get one siteUsersBalances by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteUsersBalancesDTO> findOne(Long id) {
        log.debug("Request to get SiteUsersBalances : {}", id);
        return siteUsersBalancesRepository.findById(id).map(siteUsersBalancesMapper::toDto);
    }

    /**
     * Delete the siteUsersBalances by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteUsersBalances : {}", id);
        siteUsersBalancesRepository.deleteById(id);
    }
}
