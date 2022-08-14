package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.ContentFiles;
import io.github.jhipster.sample.repository.ContentFilesRepository;
import io.github.jhipster.sample.service.dto.ContentFilesDTO;
import io.github.jhipster.sample.service.mapper.ContentFilesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContentFiles}.
 */
@Service
@Transactional
public class ContentFilesService {

    private final Logger log = LoggerFactory.getLogger(ContentFilesService.class);

    private final ContentFilesRepository contentFilesRepository;

    private final ContentFilesMapper contentFilesMapper;

    public ContentFilesService(ContentFilesRepository contentFilesRepository, ContentFilesMapper contentFilesMapper) {
        this.contentFilesRepository = contentFilesRepository;
        this.contentFilesMapper = contentFilesMapper;
    }

    /**
     * Save a contentFiles.
     *
     * @param contentFilesDTO the entity to save.
     * @return the persisted entity.
     */
    public ContentFilesDTO save(ContentFilesDTO contentFilesDTO) {
        log.debug("Request to save ContentFiles : {}", contentFilesDTO);
        ContentFiles contentFiles = contentFilesMapper.toEntity(contentFilesDTO);
        contentFiles = contentFilesRepository.save(contentFiles);
        return contentFilesMapper.toDto(contentFiles);
    }

    /**
     * Update a contentFiles.
     *
     * @param contentFilesDTO the entity to save.
     * @return the persisted entity.
     */
    public ContentFilesDTO update(ContentFilesDTO contentFilesDTO) {
        log.debug("Request to save ContentFiles : {}", contentFilesDTO);
        ContentFiles contentFiles = contentFilesMapper.toEntity(contentFilesDTO);
        contentFiles = contentFilesRepository.save(contentFiles);
        return contentFilesMapper.toDto(contentFiles);
    }

    /**
     * Partially update a contentFiles.
     *
     * @param contentFilesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContentFilesDTO> partialUpdate(ContentFilesDTO contentFilesDTO) {
        log.debug("Request to partially update ContentFiles : {}", contentFilesDTO);

        return contentFilesRepository
            .findById(contentFilesDTO.getId())
            .map(existingContentFiles -> {
                contentFilesMapper.partialUpdate(existingContentFiles, contentFilesDTO);

                return existingContentFiles;
            })
            .map(contentFilesRepository::save)
            .map(contentFilesMapper::toDto);
    }

    /**
     * Get all the contentFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContentFilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContentFiles");
        return contentFilesRepository.findAll(pageable).map(contentFilesMapper::toDto);
    }

    /**
     * Get one contentFiles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContentFilesDTO> findOne(Long id) {
        log.debug("Request to get ContentFiles : {}", id);
        return contentFilesRepository.findById(id).map(contentFilesMapper::toDto);
    }

    /**
     * Delete the contentFiles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContentFiles : {}", id);
        contentFilesRepository.deleteById(id);
    }
}
