package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminCron;
import io.github.jhipster.sample.repository.AdminCronRepository;
import io.github.jhipster.sample.service.dto.AdminCronDTO;
import io.github.jhipster.sample.service.mapper.AdminCronMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminCron}.
 */
@Service
@Transactional
public class AdminCronService {

    private final Logger log = LoggerFactory.getLogger(AdminCronService.class);

    private final AdminCronRepository adminCronRepository;

    private final AdminCronMapper adminCronMapper;

    public AdminCronService(AdminCronRepository adminCronRepository, AdminCronMapper adminCronMapper) {
        this.adminCronRepository = adminCronRepository;
        this.adminCronMapper = adminCronMapper;
    }

    /**
     * Save a adminCron.
     *
     * @param adminCronDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminCronDTO save(AdminCronDTO adminCronDTO) {
        log.debug("Request to save AdminCron : {}", adminCronDTO);
        AdminCron adminCron = adminCronMapper.toEntity(adminCronDTO);
        adminCron = adminCronRepository.save(adminCron);
        return adminCronMapper.toDto(adminCron);
    }

    /**
     * Update a adminCron.
     *
     * @param adminCronDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminCronDTO update(AdminCronDTO adminCronDTO) {
        log.debug("Request to save AdminCron : {}", adminCronDTO);
        AdminCron adminCron = adminCronMapper.toEntity(adminCronDTO);
        adminCron = adminCronRepository.save(adminCron);
        return adminCronMapper.toDto(adminCron);
    }

    /**
     * Partially update a adminCron.
     *
     * @param adminCronDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminCronDTO> partialUpdate(AdminCronDTO adminCronDTO) {
        log.debug("Request to partially update AdminCron : {}", adminCronDTO);

        return adminCronRepository
            .findById(adminCronDTO.getId())
            .map(existingAdminCron -> {
                adminCronMapper.partialUpdate(existingAdminCron, adminCronDTO);

                return existingAdminCron;
            })
            .map(adminCronRepository::save)
            .map(adminCronMapper::toDto);
    }

    /**
     * Get all the adminCrons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminCronDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminCrons");
        return adminCronRepository.findAll(pageable).map(adminCronMapper::toDto);
    }

    /**
     * Get one adminCron by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminCronDTO> findOne(Long id) {
        log.debug("Request to get AdminCron : {}", id);
        return adminCronRepository.findById(id).map(adminCronMapper::toDto);
    }

    /**
     * Delete the adminCron by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminCron : {}", id);
        adminCronRepository.deleteById(id);
    }
}
