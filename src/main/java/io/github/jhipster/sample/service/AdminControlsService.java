package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminControls;
import io.github.jhipster.sample.repository.AdminControlsRepository;
import io.github.jhipster.sample.service.dto.AdminControlsDTO;
import io.github.jhipster.sample.service.mapper.AdminControlsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminControls}.
 */
@Service
@Transactional
public class AdminControlsService {

    private final Logger log = LoggerFactory.getLogger(AdminControlsService.class);

    private final AdminControlsRepository adminControlsRepository;

    private final AdminControlsMapper adminControlsMapper;

    public AdminControlsService(AdminControlsRepository adminControlsRepository, AdminControlsMapper adminControlsMapper) {
        this.adminControlsRepository = adminControlsRepository;
        this.adminControlsMapper = adminControlsMapper;
    }

    /**
     * Save a adminControls.
     *
     * @param adminControlsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminControlsDTO save(AdminControlsDTO adminControlsDTO) {
        log.debug("Request to save AdminControls : {}", adminControlsDTO);
        AdminControls adminControls = adminControlsMapper.toEntity(adminControlsDTO);
        adminControls = adminControlsRepository.save(adminControls);
        return adminControlsMapper.toDto(adminControls);
    }

    /**
     * Update a adminControls.
     *
     * @param adminControlsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminControlsDTO update(AdminControlsDTO adminControlsDTO) {
        log.debug("Request to save AdminControls : {}", adminControlsDTO);
        AdminControls adminControls = adminControlsMapper.toEntity(adminControlsDTO);
        adminControls = adminControlsRepository.save(adminControls);
        return adminControlsMapper.toDto(adminControls);
    }

    /**
     * Partially update a adminControls.
     *
     * @param adminControlsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminControlsDTO> partialUpdate(AdminControlsDTO adminControlsDTO) {
        log.debug("Request to partially update AdminControls : {}", adminControlsDTO);

        return adminControlsRepository
            .findById(adminControlsDTO.getId())
            .map(existingAdminControls -> {
                adminControlsMapper.partialUpdate(existingAdminControls, adminControlsDTO);

                return existingAdminControls;
            })
            .map(adminControlsRepository::save)
            .map(adminControlsMapper::toDto);
    }

    /**
     * Get all the adminControls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminControlsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminControls");
        return adminControlsRepository.findAll(pageable).map(adminControlsMapper::toDto);
    }

    /**
     * Get one adminControls by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminControlsDTO> findOne(Long id) {
        log.debug("Request to get AdminControls : {}", id);
        return adminControlsRepository.findById(id).map(adminControlsMapper::toDto);
    }

    /**
     * Delete the adminControls by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminControls : {}", id);
        adminControlsRepository.deleteById(id);
    }
}
