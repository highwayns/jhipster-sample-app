package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminGroupsTabs;
import io.github.jhipster.sample.repository.AdminGroupsTabsRepository;
import io.github.jhipster.sample.service.dto.AdminGroupsTabsDTO;
import io.github.jhipster.sample.service.mapper.AdminGroupsTabsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminGroupsTabs}.
 */
@Service
@Transactional
public class AdminGroupsTabsService {

    private final Logger log = LoggerFactory.getLogger(AdminGroupsTabsService.class);

    private final AdminGroupsTabsRepository adminGroupsTabsRepository;

    private final AdminGroupsTabsMapper adminGroupsTabsMapper;

    public AdminGroupsTabsService(AdminGroupsTabsRepository adminGroupsTabsRepository, AdminGroupsTabsMapper adminGroupsTabsMapper) {
        this.adminGroupsTabsRepository = adminGroupsTabsRepository;
        this.adminGroupsTabsMapper = adminGroupsTabsMapper;
    }

    /**
     * Save a adminGroupsTabs.
     *
     * @param adminGroupsTabsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminGroupsTabsDTO save(AdminGroupsTabsDTO adminGroupsTabsDTO) {
        log.debug("Request to save AdminGroupsTabs : {}", adminGroupsTabsDTO);
        AdminGroupsTabs adminGroupsTabs = adminGroupsTabsMapper.toEntity(adminGroupsTabsDTO);
        adminGroupsTabs = adminGroupsTabsRepository.save(adminGroupsTabs);
        return adminGroupsTabsMapper.toDto(adminGroupsTabs);
    }

    /**
     * Update a adminGroupsTabs.
     *
     * @param adminGroupsTabsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminGroupsTabsDTO update(AdminGroupsTabsDTO adminGroupsTabsDTO) {
        log.debug("Request to save AdminGroupsTabs : {}", adminGroupsTabsDTO);
        AdminGroupsTabs adminGroupsTabs = adminGroupsTabsMapper.toEntity(adminGroupsTabsDTO);
        adminGroupsTabs = adminGroupsTabsRepository.save(adminGroupsTabs);
        return adminGroupsTabsMapper.toDto(adminGroupsTabs);
    }

    /**
     * Partially update a adminGroupsTabs.
     *
     * @param adminGroupsTabsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminGroupsTabsDTO> partialUpdate(AdminGroupsTabsDTO adminGroupsTabsDTO) {
        log.debug("Request to partially update AdminGroupsTabs : {}", adminGroupsTabsDTO);

        return adminGroupsTabsRepository
            .findById(adminGroupsTabsDTO.getId())
            .map(existingAdminGroupsTabs -> {
                adminGroupsTabsMapper.partialUpdate(existingAdminGroupsTabs, adminGroupsTabsDTO);

                return existingAdminGroupsTabs;
            })
            .map(adminGroupsTabsRepository::save)
            .map(adminGroupsTabsMapper::toDto);
    }

    /**
     * Get all the adminGroupsTabs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminGroupsTabsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminGroupsTabs");
        return adminGroupsTabsRepository.findAll(pageable).map(adminGroupsTabsMapper::toDto);
    }

    /**
     * Get one adminGroupsTabs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminGroupsTabsDTO> findOne(Long id) {
        log.debug("Request to get AdminGroupsTabs : {}", id);
        return adminGroupsTabsRepository.findById(id).map(adminGroupsTabsMapper::toDto);
    }

    /**
     * Delete the adminGroupsTabs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminGroupsTabs : {}", id);
        adminGroupsTabsRepository.deleteById(id);
    }
}
