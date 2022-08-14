package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminControlsMethods;
import io.github.jhipster.sample.repository.AdminControlsMethodsRepository;
import io.github.jhipster.sample.service.dto.AdminControlsMethodsDTO;
import io.github.jhipster.sample.service.mapper.AdminControlsMethodsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminControlsMethods}.
 */
@Service
@Transactional
public class AdminControlsMethodsService {

    private final Logger log = LoggerFactory.getLogger(AdminControlsMethodsService.class);

    private final AdminControlsMethodsRepository adminControlsMethodsRepository;

    private final AdminControlsMethodsMapper adminControlsMethodsMapper;

    public AdminControlsMethodsService(
        AdminControlsMethodsRepository adminControlsMethodsRepository,
        AdminControlsMethodsMapper adminControlsMethodsMapper
    ) {
        this.adminControlsMethodsRepository = adminControlsMethodsRepository;
        this.adminControlsMethodsMapper = adminControlsMethodsMapper;
    }

    /**
     * Save a adminControlsMethods.
     *
     * @param adminControlsMethodsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminControlsMethodsDTO save(AdminControlsMethodsDTO adminControlsMethodsDTO) {
        log.debug("Request to save AdminControlsMethods : {}", adminControlsMethodsDTO);
        AdminControlsMethods adminControlsMethods = adminControlsMethodsMapper.toEntity(adminControlsMethodsDTO);
        adminControlsMethods = adminControlsMethodsRepository.save(adminControlsMethods);
        return adminControlsMethodsMapper.toDto(adminControlsMethods);
    }

    /**
     * Update a adminControlsMethods.
     *
     * @param adminControlsMethodsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminControlsMethodsDTO update(AdminControlsMethodsDTO adminControlsMethodsDTO) {
        log.debug("Request to save AdminControlsMethods : {}", adminControlsMethodsDTO);
        AdminControlsMethods adminControlsMethods = adminControlsMethodsMapper.toEntity(adminControlsMethodsDTO);
        adminControlsMethods = adminControlsMethodsRepository.save(adminControlsMethods);
        return adminControlsMethodsMapper.toDto(adminControlsMethods);
    }

    /**
     * Partially update a adminControlsMethods.
     *
     * @param adminControlsMethodsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminControlsMethodsDTO> partialUpdate(AdminControlsMethodsDTO adminControlsMethodsDTO) {
        log.debug("Request to partially update AdminControlsMethods : {}", adminControlsMethodsDTO);

        return adminControlsMethodsRepository
            .findById(adminControlsMethodsDTO.getId())
            .map(existingAdminControlsMethods -> {
                adminControlsMethodsMapper.partialUpdate(existingAdminControlsMethods, adminControlsMethodsDTO);

                return existingAdminControlsMethods;
            })
            .map(adminControlsMethodsRepository::save)
            .map(adminControlsMethodsMapper::toDto);
    }

    /**
     * Get all the adminControlsMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminControlsMethodsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminControlsMethods");
        return adminControlsMethodsRepository.findAll(pageable).map(adminControlsMethodsMapper::toDto);
    }

    /**
     * Get one adminControlsMethods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminControlsMethodsDTO> findOne(Long id) {
        log.debug("Request to get AdminControlsMethods : {}", id);
        return adminControlsMethodsRepository.findById(id).map(adminControlsMethodsMapper::toDto);
    }

    /**
     * Delete the adminControlsMethods by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminControlsMethods : {}", id);
        adminControlsMethodsRepository.deleteById(id);
    }
}
