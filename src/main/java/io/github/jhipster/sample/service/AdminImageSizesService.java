package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminImageSizes;
import io.github.jhipster.sample.repository.AdminImageSizesRepository;
import io.github.jhipster.sample.service.dto.AdminImageSizesDTO;
import io.github.jhipster.sample.service.mapper.AdminImageSizesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminImageSizes}.
 */
@Service
@Transactional
public class AdminImageSizesService {

    private final Logger log = LoggerFactory.getLogger(AdminImageSizesService.class);

    private final AdminImageSizesRepository adminImageSizesRepository;

    private final AdminImageSizesMapper adminImageSizesMapper;

    public AdminImageSizesService(AdminImageSizesRepository adminImageSizesRepository, AdminImageSizesMapper adminImageSizesMapper) {
        this.adminImageSizesRepository = adminImageSizesRepository;
        this.adminImageSizesMapper = adminImageSizesMapper;
    }

    /**
     * Save a adminImageSizes.
     *
     * @param adminImageSizesDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminImageSizesDTO save(AdminImageSizesDTO adminImageSizesDTO) {
        log.debug("Request to save AdminImageSizes : {}", adminImageSizesDTO);
        AdminImageSizes adminImageSizes = adminImageSizesMapper.toEntity(adminImageSizesDTO);
        adminImageSizes = adminImageSizesRepository.save(adminImageSizes);
        return adminImageSizesMapper.toDto(adminImageSizes);
    }

    /**
     * Update a adminImageSizes.
     *
     * @param adminImageSizesDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminImageSizesDTO update(AdminImageSizesDTO adminImageSizesDTO) {
        log.debug("Request to save AdminImageSizes : {}", adminImageSizesDTO);
        AdminImageSizes adminImageSizes = adminImageSizesMapper.toEntity(adminImageSizesDTO);
        adminImageSizes = adminImageSizesRepository.save(adminImageSizes);
        return adminImageSizesMapper.toDto(adminImageSizes);
    }

    /**
     * Partially update a adminImageSizes.
     *
     * @param adminImageSizesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminImageSizesDTO> partialUpdate(AdminImageSizesDTO adminImageSizesDTO) {
        log.debug("Request to partially update AdminImageSizes : {}", adminImageSizesDTO);

        return adminImageSizesRepository
            .findById(adminImageSizesDTO.getId())
            .map(existingAdminImageSizes -> {
                adminImageSizesMapper.partialUpdate(existingAdminImageSizes, adminImageSizesDTO);

                return existingAdminImageSizes;
            })
            .map(adminImageSizesRepository::save)
            .map(adminImageSizesMapper::toDto);
    }

    /**
     * Get all the adminImageSizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminImageSizesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminImageSizes");
        return adminImageSizesRepository.findAll(pageable).map(adminImageSizesMapper::toDto);
    }

    /**
     * Get one adminImageSizes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminImageSizesDTO> findOne(Long id) {
        log.debug("Request to get AdminImageSizes : {}", id);
        return adminImageSizesRepository.findById(id).map(adminImageSizesMapper::toDto);
    }

    /**
     * Delete the adminImageSizes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminImageSizes : {}", id);
        adminImageSizesRepository.deleteById(id);
    }
}
