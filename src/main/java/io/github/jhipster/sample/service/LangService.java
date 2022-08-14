package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.Lang;
import io.github.jhipster.sample.repository.LangRepository;
import io.github.jhipster.sample.service.dto.LangDTO;
import io.github.jhipster.sample.service.mapper.LangMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Lang}.
 */
@Service
@Transactional
public class LangService {

    private final Logger log = LoggerFactory.getLogger(LangService.class);

    private final LangRepository langRepository;

    private final LangMapper langMapper;

    public LangService(LangRepository langRepository, LangMapper langMapper) {
        this.langRepository = langRepository;
        this.langMapper = langMapper;
    }

    /**
     * Save a lang.
     *
     * @param langDTO the entity to save.
     * @return the persisted entity.
     */
    public LangDTO save(LangDTO langDTO) {
        log.debug("Request to save Lang : {}", langDTO);
        Lang lang = langMapper.toEntity(langDTO);
        lang = langRepository.save(lang);
        return langMapper.toDto(lang);
    }

    /**
     * Update a lang.
     *
     * @param langDTO the entity to save.
     * @return the persisted entity.
     */
    public LangDTO update(LangDTO langDTO) {
        log.debug("Request to save Lang : {}", langDTO);
        Lang lang = langMapper.toEntity(langDTO);
        lang = langRepository.save(lang);
        return langMapper.toDto(lang);
    }

    /**
     * Partially update a lang.
     *
     * @param langDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LangDTO> partialUpdate(LangDTO langDTO) {
        log.debug("Request to partially update Lang : {}", langDTO);

        return langRepository
            .findById(langDTO.getId())
            .map(existingLang -> {
                langMapper.partialUpdate(existingLang, langDTO);

                return existingLang;
            })
            .map(langRepository::save)
            .map(langMapper::toDto);
    }

    /**
     * Get all the langs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LangDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Langs");
        return langRepository.findAll(pageable).map(langMapper::toDto);
    }

    /**
     * Get one lang by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LangDTO> findOne(Long id) {
        log.debug("Request to get Lang : {}", id);
        return langRepository.findById(id).map(langMapper::toDto);
    }

    /**
     * Delete the lang by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lang : {}", id);
        langRepository.deleteById(id);
    }
}
