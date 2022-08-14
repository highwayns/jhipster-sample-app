package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.AppConfiguration;
import io.github.jhipster.sample.repository.AppConfigurationRepository;
import io.github.jhipster.sample.service.dto.AppConfigurationDTO;
import io.github.jhipster.sample.service.mapper.AppConfigurationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppConfiguration}.
 */
@Service
@Transactional
public class AppConfigurationService {

    private final Logger log = LoggerFactory.getLogger(AppConfigurationService.class);

    private final AppConfigurationRepository appConfigurationRepository;

    private final AppConfigurationMapper appConfigurationMapper;

    public AppConfigurationService(AppConfigurationRepository appConfigurationRepository, AppConfigurationMapper appConfigurationMapper) {
        this.appConfigurationRepository = appConfigurationRepository;
        this.appConfigurationMapper = appConfigurationMapper;
    }

    /**
     * Save a appConfiguration.
     *
     * @param appConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public AppConfigurationDTO save(AppConfigurationDTO appConfigurationDTO) {
        log.debug("Request to save AppConfiguration : {}", appConfigurationDTO);
        AppConfiguration appConfiguration = appConfigurationMapper.toEntity(appConfigurationDTO);
        appConfiguration = appConfigurationRepository.save(appConfiguration);
        return appConfigurationMapper.toDto(appConfiguration);
    }

    /**
     * Update a appConfiguration.
     *
     * @param appConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public AppConfigurationDTO update(AppConfigurationDTO appConfigurationDTO) {
        log.debug("Request to save AppConfiguration : {}", appConfigurationDTO);
        AppConfiguration appConfiguration = appConfigurationMapper.toEntity(appConfigurationDTO);
        appConfiguration = appConfigurationRepository.save(appConfiguration);
        return appConfigurationMapper.toDto(appConfiguration);
    }

    /**
     * Partially update a appConfiguration.
     *
     * @param appConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppConfigurationDTO> partialUpdate(AppConfigurationDTO appConfigurationDTO) {
        log.debug("Request to partially update AppConfiguration : {}", appConfigurationDTO);

        return appConfigurationRepository
            .findById(appConfigurationDTO.getId())
            .map(existingAppConfiguration -> {
                appConfigurationMapper.partialUpdate(existingAppConfiguration, appConfigurationDTO);

                return existingAppConfiguration;
            })
            .map(appConfigurationRepository::save)
            .map(appConfigurationMapper::toDto);
    }

    /**
     * Get all the appConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppConfigurations");
        return appConfigurationRepository.findAll(pageable).map(appConfigurationMapper::toDto);
    }

    /**
     * Get one appConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppConfigurationDTO> findOne(Long id) {
        log.debug("Request to get AppConfiguration : {}", id);
        return appConfigurationRepository.findById(id).map(appConfigurationMapper::toDto);
    }

    /**
     * Delete the appConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppConfiguration : {}", id);
        appConfigurationRepository.deleteById(id);
    }
}
