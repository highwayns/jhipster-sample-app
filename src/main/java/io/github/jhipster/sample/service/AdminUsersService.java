package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AdminUsers;
import io.github.jhipster.sample.repository.AdminUsersRepository;
import io.github.jhipster.sample.service.dto.AdminUsersDTO;
import io.github.jhipster.sample.service.mapper.AdminUsersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminUsers}.
 */
@Service
@Transactional
public class AdminUsersService {

    private final Logger log = LoggerFactory.getLogger(AdminUsersService.class);

    private final AdminUsersRepository adminUsersRepository;

    private final AdminUsersMapper adminUsersMapper;

    public AdminUsersService(AdminUsersRepository adminUsersRepository, AdminUsersMapper adminUsersMapper) {
        this.adminUsersRepository = adminUsersRepository;
        this.adminUsersMapper = adminUsersMapper;
    }

    /**
     * Save a adminUsers.
     *
     * @param adminUsersDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminUsersDTO save(AdminUsersDTO adminUsersDTO) {
        log.debug("Request to save AdminUsers : {}", adminUsersDTO);
        AdminUsers adminUsers = adminUsersMapper.toEntity(adminUsersDTO);
        adminUsers = adminUsersRepository.save(adminUsers);
        return adminUsersMapper.toDto(adminUsers);
    }

    /**
     * Update a adminUsers.
     *
     * @param adminUsersDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminUsersDTO update(AdminUsersDTO adminUsersDTO) {
        log.debug("Request to save AdminUsers : {}", adminUsersDTO);
        AdminUsers adminUsers = adminUsersMapper.toEntity(adminUsersDTO);
        adminUsers = adminUsersRepository.save(adminUsers);
        return adminUsersMapper.toDto(adminUsers);
    }

    /**
     * Partially update a adminUsers.
     *
     * @param adminUsersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdminUsersDTO> partialUpdate(AdminUsersDTO adminUsersDTO) {
        log.debug("Request to partially update AdminUsers : {}", adminUsersDTO);

        return adminUsersRepository
            .findById(adminUsersDTO.getId())
            .map(existingAdminUsers -> {
                adminUsersMapper.partialUpdate(existingAdminUsers, adminUsersDTO);

                return existingAdminUsers;
            })
            .map(adminUsersRepository::save)
            .map(adminUsersMapper::toDto);
    }

    /**
     * Get all the adminUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminUsers");
        return adminUsersRepository.findAll(pageable).map(adminUsersMapper::toDto);
    }

    /**
     * Get one adminUsers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminUsersDTO> findOne(Long id) {
        log.debug("Request to get AdminUsers : {}", id);
        return adminUsersRepository.findById(id).map(adminUsersMapper::toDto);
    }

    /**
     * Delete the adminUsers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminUsers : {}", id);
        adminUsersRepository.deleteById(id);
    }
}
