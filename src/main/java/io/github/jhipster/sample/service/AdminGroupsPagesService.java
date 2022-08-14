package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminGroupsPages;
import io.github.jhipster.sample.repository.AdminGroupsPagesRepository;
import io.github.jhipster.sample.service.dto.AdminGroupsPagesDTO;
import io.github.jhipster.sample.service.mapper.AdminGroupsPagesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminGroupsPages}.
 */
@Service
@Transactional
public class AdminGroupsPagesService {

    private final Logger log = LoggerFactory.getLogger(AdminGroupsPagesService.class);

    private final AdminGroupsPagesRepository adminGroupsPagesRepository;

    private final AdminGroupsPagesMapper adminGroupsPagesMapper;

    public AdminGroupsPagesService(AdminGroupsPagesRepository adminGroupsPagesRepository, AdminGroupsPagesMapper adminGroupsPagesMapper) {
        this.adminGroupsPagesRepository = adminGroupsPagesRepository;
        this.adminGroupsPagesMapper = adminGroupsPagesMapper;
    }

    /**
     * Save a adminGroupsPages.
     *
     * @param adminGroupsPagesDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminGroupsPagesDTO save(AdminGroupsPagesDTO adminGroupsPagesDTO) {
        log.debug("Request to save AdminGroupsPages : {}", adminGroupsPagesDTO);
        AdminGroupsPages adminGroupsPages = adminGroupsPagesMapper.toEntity(adminGroupsPagesDTO);
        adminGroupsPages = adminGroupsPagesRepository.save(adminGroupsPages);
        return adminGroupsPagesMapper.toDto(adminGroupsPages);
    }

    /**
     * Update a adminGroupsPages.
     *
     * @param adminGroupsPagesDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminGroupsPagesDTO update(AdminGroupsPagesDTO adminGroupsPagesDTO) {
        log.debug("Request to save AdminGroupsPages : {}", adminGroupsPagesDTO);
        AdminGroupsPages adminGroupsPages = adminGroupsPagesMapper.toEntity(adminGroupsPagesDTO);
        adminGroupsPages = adminGroupsPagesRepository.save(adminGroupsPages);
        return adminGroupsPagesMapper.toDto(adminGroupsPages);
    }

    /**
     * Partially update a adminGroupsPages.
     *
     * @param adminGroupsPagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminGroupsPagesDTO> partialUpdate(AdminGroupsPagesDTO adminGroupsPagesDTO) {
        log.debug("Request to partially update AdminGroupsPages : {}", adminGroupsPagesDTO);

        return adminGroupsPagesRepository
            .findById(adminGroupsPagesDTO.getId())
            .map(existingAdminGroupsPages -> {
                adminGroupsPagesMapper.partialUpdate(existingAdminGroupsPages, adminGroupsPagesDTO);

                return existingAdminGroupsPages;
            })
            .map(adminGroupsPagesRepository::save)
            .map(adminGroupsPagesMapper::toDto);
    }

    /**
     * Get all the adminGroupsPages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminGroupsPagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminGroupsPages");
        return adminGroupsPagesRepository.findAll(pageable).map(adminGroupsPagesMapper::toDto);
    }

    /**
     * Get one adminGroupsPages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminGroupsPagesDTO> findOne(Long id) {
        log.debug("Request to get AdminGroupsPages : {}", id);
        return adminGroupsPagesRepository.findById(id).map(adminGroupsPagesMapper::toDto);
    }

    /**
     * Delete the adminGroupsPages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminGroupsPages : {}", id);
        adminGroupsPagesRepository.deleteById(id);
    }
}
