package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.SettingsFiles;
import io.github.jhipster.sample.repository.SettingsFilesRepository;
import io.github.jhipster.sample.service.dto.SettingsFilesDTO;
import io.github.jhipster.sample.service.mapper.SettingsFilesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SettingsFiles}.
 */
@Service
@Transactional
public class SettingsFilesService {

    private final Logger log = LoggerFactory.getLogger(SettingsFilesService.class);

    private final SettingsFilesRepository settingsFilesRepository;

    private final SettingsFilesMapper settingsFilesMapper;

    public SettingsFilesService(SettingsFilesRepository settingsFilesRepository, SettingsFilesMapper settingsFilesMapper) {
        this.settingsFilesRepository = settingsFilesRepository;
        this.settingsFilesMapper = settingsFilesMapper;
    }

    /**
     * Save a settingsFiles.
     *
     * @param settingsFilesDTO the entity to save.
     * @return the persisted entity.
     */
    public SettingsFilesDTO save(SettingsFilesDTO settingsFilesDTO) {
        log.debug("Request to save SettingsFiles : {}", settingsFilesDTO);
        SettingsFiles settingsFiles = settingsFilesMapper.toEntity(settingsFilesDTO);
        settingsFiles = settingsFilesRepository.save(settingsFiles);
        return settingsFilesMapper.toDto(settingsFiles);
    }

    /**
     * Update a settingsFiles.
     *
     * @param settingsFilesDTO the entity to save.
     * @return the persisted entity.
     */
    public SettingsFilesDTO update(SettingsFilesDTO settingsFilesDTO) {
        log.debug("Request to save SettingsFiles : {}", settingsFilesDTO);
        SettingsFiles settingsFiles = settingsFilesMapper.toEntity(settingsFilesDTO);
        settingsFiles = settingsFilesRepository.save(settingsFiles);
        return settingsFilesMapper.toDto(settingsFiles);
    }

    /**
     * Partially update a settingsFiles.
     *
     * @param settingsFilesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SettingsFilesDTO> partialUpdate(SettingsFilesDTO settingsFilesDTO) {
        log.debug("Request to partially update SettingsFiles : {}", settingsFilesDTO);

        return settingsFilesRepository
            .findById(settingsFilesDTO.getId())
            .map(existingSettingsFiles -> {
                settingsFilesMapper.partialUpdate(existingSettingsFiles, settingsFilesDTO);

                return existingSettingsFiles;
            })
            .map(settingsFilesRepository::save)
            .map(settingsFilesMapper::toDto);
    }

    /**
     * Get all the settingsFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SettingsFilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SettingsFiles");
        return settingsFilesRepository.findAll(pageable).map(settingsFilesMapper::toDto);
    }

    /**
     * Get one settingsFiles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SettingsFilesDTO> findOne(Long id) {
        log.debug("Request to get SettingsFiles : {}", id);
        return settingsFilesRepository.findById(id).map(settingsFilesMapper::toDto);
    }

    /**
     * Delete the settingsFiles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SettingsFiles : {}", id);
        settingsFilesRepository.deleteById(id);
    }
}
