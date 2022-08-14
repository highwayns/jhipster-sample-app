package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminPages;
import io.github.jhipster.sample.repository.AdminPagesRepository;
import io.github.jhipster.sample.service.dto.AdminPagesDTO;
import io.github.jhipster.sample.service.mapper.AdminPagesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminPages}.
 */
@Service
@Transactional
public class AdminPagesService {

    private final Logger log = LoggerFactory.getLogger(AdminPagesService.class);

    private final AdminPagesRepository adminPagesRepository;

    private final AdminPagesMapper adminPagesMapper;

    public AdminPagesService(AdminPagesRepository adminPagesRepository, AdminPagesMapper adminPagesMapper) {
        this.adminPagesRepository = adminPagesRepository;
        this.adminPagesMapper = adminPagesMapper;
    }

    /**
     * Save a adminPages.
     *
     * @param adminPagesDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminPagesDTO save(AdminPagesDTO adminPagesDTO) {
        log.debug("Request to save AdminPages : {}", adminPagesDTO);
        AdminPages adminPages = adminPagesMapper.toEntity(adminPagesDTO);
        adminPages = adminPagesRepository.save(adminPages);
        return adminPagesMapper.toDto(adminPages);
    }

    /**
     * Update a adminPages.
     *
     * @param adminPagesDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminPagesDTO update(AdminPagesDTO adminPagesDTO) {
        log.debug("Request to save AdminPages : {}", adminPagesDTO);
        AdminPages adminPages = adminPagesMapper.toEntity(adminPagesDTO);
        adminPages = adminPagesRepository.save(adminPages);
        return adminPagesMapper.toDto(adminPages);
    }

    /**
     * Partially update a adminPages.
     *
     * @param adminPagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminPagesDTO> partialUpdate(AdminPagesDTO adminPagesDTO) {
        log.debug("Request to partially update AdminPages : {}", adminPagesDTO);

        return adminPagesRepository
            .findById(adminPagesDTO.getId())
            .map(existingAdminPages -> {
                adminPagesMapper.partialUpdate(existingAdminPages, adminPagesDTO);

                return existingAdminPages;
            })
            .map(adminPagesRepository::save)
            .map(adminPagesMapper::toDto);
    }

    /**
     * Get all the adminPages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminPagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminPages");
        return adminPagesRepository.findAll(pageable).map(adminPagesMapper::toDto);
    }

    /**
     * Get one adminPages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminPagesDTO> findOne(Long id) {
        log.debug("Request to get AdminPages : {}", id);
        return adminPagesRepository.findById(id).map(adminPagesMapper::toDto);
    }

    /**
     * Delete the adminPages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminPages : {}", id);
        adminPagesRepository.deleteById(id);
    }
}
