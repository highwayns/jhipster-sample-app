package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.ChangeSettings;
import io.github.jhipster.sample.repository.ChangeSettingsRepository;
import io.github.jhipster.sample.service.dto.ChangeSettingsDTO;
import io.github.jhipster.sample.service.mapper.ChangeSettingsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChangeSettings}.
 */
@Service
@Transactional
public class ChangeSettingsService {

    private final Logger log = LoggerFactory.getLogger(ChangeSettingsService.class);

    private final ChangeSettingsRepository changeSettingsRepository;

    private final ChangeSettingsMapper changeSettingsMapper;

    public ChangeSettingsService(ChangeSettingsRepository changeSettingsRepository, ChangeSettingsMapper changeSettingsMapper) {
        this.changeSettingsRepository = changeSettingsRepository;
        this.changeSettingsMapper = changeSettingsMapper;
    }

    /**
     * Save a changeSettings.
     *
     * @param changeSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    public ChangeSettingsDTO save(ChangeSettingsDTO changeSettingsDTO) {
        log.debug("Request to save ChangeSettings : {}", changeSettingsDTO);
        ChangeSettings changeSettings = changeSettingsMapper.toEntity(changeSettingsDTO);
        changeSettings = changeSettingsRepository.save(changeSettings);
        return changeSettingsMapper.toDto(changeSettings);
    }

    /**
     * Update a changeSettings.
     *
     * @param changeSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    public ChangeSettingsDTO update(ChangeSettingsDTO changeSettingsDTO) {
        log.debug("Request to save ChangeSettings : {}", changeSettingsDTO);
        ChangeSettings changeSettings = changeSettingsMapper.toEntity(changeSettingsDTO);
        changeSettings = changeSettingsRepository.save(changeSettings);
        return changeSettingsMapper.toDto(changeSettings);
    }

    /**
     * Partially update a changeSettings.
     *
     * @param changeSettingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChangeSettingsDTO> partialUpdate(ChangeSettingsDTO changeSettingsDTO) {
        log.debug("Request to partially update ChangeSettings : {}", changeSettingsDTO);

        return changeSettingsRepository
            .findById(changeSettingsDTO.getId())
            .map(existingChangeSettings -> {
                changeSettingsMapper.partialUpdate(existingChangeSettings, changeSettingsDTO);

                return existingChangeSettings;
            })
            .map(changeSettingsRepository::save)
            .map(changeSettingsMapper::toDto);
    }

    /**
     * Get all the changeSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChangeSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChangeSettings");
        return changeSettingsRepository.findAll(pageable).map(changeSettingsMapper::toDto);
    }

    /**
     * Get one changeSettings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChangeSettingsDTO> findOne(Long id) {
        log.debug("Request to get ChangeSettings : {}", id);
        return changeSettingsRepository.findById(id).map(changeSettingsMapper::toDto);
    }

    /**
     * Delete the changeSettings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChangeSettings : {}", id);
        changeSettingsRepository.deleteById(id);
    }
}
