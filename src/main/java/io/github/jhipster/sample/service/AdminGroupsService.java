package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminGroups;
import io.github.jhipster.sample.repository.AdminGroupsRepository;
import io.github.jhipster.sample.service.dto.AdminGroupsDTO;
import io.github.jhipster.sample.service.mapper.AdminGroupsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminGroups}.
 */
@Service
@Transactional
public class AdminGroupsService {

    private final Logger log = LoggerFactory.getLogger(AdminGroupsService.class);

    private final AdminGroupsRepository adminGroupsRepository;

    private final AdminGroupsMapper adminGroupsMapper;

    public AdminGroupsService(AdminGroupsRepository adminGroupsRepository, AdminGroupsMapper adminGroupsMapper) {
        this.adminGroupsRepository = adminGroupsRepository;
        this.adminGroupsMapper = adminGroupsMapper;
    }

    /**
     * Save a adminGroups.
     *
     * @param adminGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminGroupsDTO save(AdminGroupsDTO adminGroupsDTO) {
        log.debug("Request to save AdminGroups : {}", adminGroupsDTO);
        AdminGroups adminGroups = adminGroupsMapper.toEntity(adminGroupsDTO);
        adminGroups = adminGroupsRepository.save(adminGroups);
        return adminGroupsMapper.toDto(adminGroups);
    }

    /**
     * Update a adminGroups.
     *
     * @param adminGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminGroupsDTO update(AdminGroupsDTO adminGroupsDTO) {
        log.debug("Request to save AdminGroups : {}", adminGroupsDTO);
        AdminGroups adminGroups = adminGroupsMapper.toEntity(adminGroupsDTO);
        adminGroups = adminGroupsRepository.save(adminGroups);
        return adminGroupsMapper.toDto(adminGroups);
    }

    /**
     * Partially update a adminGroups.
     *
     * @param adminGroupsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminGroupsDTO> partialUpdate(AdminGroupsDTO adminGroupsDTO) {
        log.debug("Request to partially update AdminGroups : {}", adminGroupsDTO);

        return adminGroupsRepository
            .findById(adminGroupsDTO.getId())
            .map(existingAdminGroups -> {
                adminGroupsMapper.partialUpdate(existingAdminGroups, adminGroupsDTO);

                return existingAdminGroups;
            })
            .map(adminGroupsRepository::save)
            .map(adminGroupsMapper::toDto);
    }

    /**
     * Get all the adminGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminGroups");
        return adminGroupsRepository.findAll(pageable).map(adminGroupsMapper::toDto);
    }

    /**
     * Get one adminGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminGroupsDTO> findOne(Long id) {
        log.debug("Request to get AdminGroups : {}", id);
        return adminGroupsRepository.findById(id).map(adminGroupsMapper::toDto);
    }

    /**
     * Delete the adminGroups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminGroups : {}", id);
        adminGroupsRepository.deleteById(id);
    }
}
