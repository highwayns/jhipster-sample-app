package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminTabs;
import io.github.jhipster.sample.repository.AdminTabsRepository;
import io.github.jhipster.sample.service.dto.AdminTabsDTO;
import io.github.jhipster.sample.service.mapper.AdminTabsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminTabs}.
 */
@Service
@Transactional
public class AdminTabsService {

    private final Logger log = LoggerFactory.getLogger(AdminTabsService.class);

    private final AdminTabsRepository adminTabsRepository;

    private final AdminTabsMapper adminTabsMapper;

    public AdminTabsService(AdminTabsRepository adminTabsRepository, AdminTabsMapper adminTabsMapper) {
        this.adminTabsRepository = adminTabsRepository;
        this.adminTabsMapper = adminTabsMapper;
    }

    /**
     * Save a adminTabs.
     *
     * @param adminTabsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminTabsDTO save(AdminTabsDTO adminTabsDTO) {
        log.debug("Request to save AdminTabs : {}", adminTabsDTO);
        AdminTabs adminTabs = adminTabsMapper.toEntity(adminTabsDTO);
        adminTabs = adminTabsRepository.save(adminTabs);
        return adminTabsMapper.toDto(adminTabs);
    }

    /**
     * Update a adminTabs.
     *
     * @param adminTabsDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminTabsDTO update(AdminTabsDTO adminTabsDTO) {
        log.debug("Request to save AdminTabs : {}", adminTabsDTO);
        AdminTabs adminTabs = adminTabsMapper.toEntity(adminTabsDTO);
        adminTabs = adminTabsRepository.save(adminTabs);
        return adminTabsMapper.toDto(adminTabs);
    }

    /**
     * Partially update a adminTabs.
     *
     * @param adminTabsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminTabsDTO> partialUpdate(AdminTabsDTO adminTabsDTO) {
        log.debug("Request to partially update AdminTabs : {}", adminTabsDTO);

        return adminTabsRepository
            .findById(adminTabsDTO.getId())
            .map(existingAdminTabs -> {
                adminTabsMapper.partialUpdate(existingAdminTabs, adminTabsDTO);

                return existingAdminTabs;
            })
            .map(adminTabsRepository::save)
            .map(adminTabsMapper::toDto);
    }

    /**
     * Get all the adminTabs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminTabsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminTabs");
        return adminTabsRepository.findAll(pageable).map(adminTabsMapper::toDto);
    }

    /**
     * Get one adminTabs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminTabsDTO> findOne(Long id) {
        log.debug("Request to get AdminTabs : {}", id);
        return adminTabsRepository.findById(id).map(adminTabsMapper::toDto);
    }

    /**
     * Delete the adminTabs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminTabs : {}", id);
        adminTabsRepository.deleteById(id);
    }
}
