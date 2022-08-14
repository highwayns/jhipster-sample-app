package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminSessions;
import io.github.jhipster.sample.repository.AdminSessionsRepository;
import io.github.jhipster.sample.service.dto.AdminSessionsDTO;
import io.github.jhipster.sample.service.mapper.AdminSessionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminSessions}.
 */
@Service
@Transactional
public class AdminSessionsService {

    private final Logger log = LoggerFactory.getLogger(AdminSessionsService.class);

    private final AdminSessionsRepository adminSessionsRepository;

    private final AdminSessionsMapper adminSessionsMapper;

    public AdminSessionsService(AdminSessionsRepository adminSessionsRepository, AdminSessionsMapper adminSessionsMapper) {
        this.adminSessionsRepository = adminSessionsRepository;
        this.adminSessionsMapper = adminSessionsMapper;
    }

    /**
     * Save a adminSessions.
     *
     * @param adminSessionsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminSessionsDTO save(AdminSessionsDTO adminSessionsDTO) {
        log.debug("Request to save AdminSessions : {}", adminSessionsDTO);
        AdminSessions adminSessions = adminSessionsMapper.toEntity(adminSessionsDTO);
        adminSessions = adminSessionsRepository.save(adminSessions);
        return adminSessionsMapper.toDto(adminSessions);
    }

    /**
     * Update a adminSessions.
     *
     * @param adminSessionsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminSessionsDTO update(AdminSessionsDTO adminSessionsDTO) {
        log.debug("Request to save AdminSessions : {}", adminSessionsDTO);
        AdminSessions adminSessions = adminSessionsMapper.toEntity(adminSessionsDTO);
        adminSessions = adminSessionsRepository.save(adminSessions);
        return adminSessionsMapper.toDto(adminSessions);
    }

    /**
     * Partially update a adminSessions.
     *
     * @param adminSessionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminSessionsDTO> partialUpdate(AdminSessionsDTO adminSessionsDTO) {
        log.debug("Request to partially update AdminSessions : {}", adminSessionsDTO);

        return adminSessionsRepository
            .findById(adminSessionsDTO.getId())
            .map(existingAdminSessions -> {
                adminSessionsMapper.partialUpdate(existingAdminSessions, adminSessionsDTO);

                return existingAdminSessions;
            })
            .map(adminSessionsRepository::save)
            .map(adminSessionsMapper::toDto);
    }

    /**
     * Get all the adminSessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminSessionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminSessions");
        return adminSessionsRepository.findAll(pageable).map(adminSessionsMapper::toDto);
    }

    /**
     * Get one adminSessions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminSessionsDTO> findOne(Long id) {
        log.debug("Request to get AdminSessions : {}", id);
        return adminSessionsRepository.findById(id).map(adminSessionsMapper::toDto);
    }

    /**
     * Delete the adminSessions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminSessions : {}", id);
        adminSessionsRepository.deleteById(id);
    }
}
