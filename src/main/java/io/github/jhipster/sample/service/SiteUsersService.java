package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.SiteUsers;
import io.github.jhipster.sample.repository.SiteUsersRepository;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
import io.github.jhipster.sample.service.mapper.SiteUsersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SiteUsers}.
 */
@Service
@Transactional
public class SiteUsersService {

    private final Logger log = LoggerFactory.getLogger(SiteUsersService.class);

    private final SiteUsersRepository siteUsersRepository;

    private final SiteUsersMapper siteUsersMapper;

    public SiteUsersService(SiteUsersRepository siteUsersRepository, SiteUsersMapper siteUsersMapper) {
        this.siteUsersRepository = siteUsersRepository;
        this.siteUsersMapper = siteUsersMapper;
    }

    /**
     * Save a siteUsers.
     *
     * @param siteUsersDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersDTO save(SiteUsersDTO siteUsersDTO) {
        log.debug("Request to save SiteUsers : {}", siteUsersDTO);
        SiteUsers siteUsers = siteUsersMapper.toEntity(siteUsersDTO);
        siteUsers = siteUsersRepository.save(siteUsers);
        return siteUsersMapper.toDto(siteUsers);
    }

    /**
     * Update a siteUsers.
     *
     * @param siteUsersDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUsersDTO update(SiteUsersDTO siteUsersDTO) {
        log.debug("Request to save SiteUsers : {}", siteUsersDTO);
        SiteUsers siteUsers = siteUsersMapper.toEntity(siteUsersDTO);
        siteUsers = siteUsersRepository.save(siteUsers);
        return siteUsersMapper.toDto(siteUsers);
    }

    /**
     * Partially update a siteUsers.
     *
     * @param siteUsersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiteUsersDTO> partialUpdate(SiteUsersDTO siteUsersDTO) {
        log.debug("Request to partially update SiteUsers : {}", siteUsersDTO);

        return siteUsersRepository
            .findById(siteUsersDTO.getId())
            .map(existingSiteUsers -> {
                siteUsersMapper.partialUpdate(existingSiteUsers, siteUsersDTO);

                return existingSiteUsers;
            })
            .map(siteUsersRepository::save)
            .map(siteUsersMapper::toDto);
    }

    /**
     * Get all the siteUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteUsers");
        return siteUsersRepository.findAll(pageable).map(siteUsersMapper::toDto);
    }

    /**
     * Get one siteUsers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteUsersDTO> findOne(Long id) {
        log.debug("Request to get SiteUsers : {}", id);
        return siteUsersRepository.findById(id).map(siteUsersMapper::toDto);
    }

    /**
     * Delete the siteUsers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteUsers : {}", id);
        siteUsersRepository.deleteById(id);
    }
}
