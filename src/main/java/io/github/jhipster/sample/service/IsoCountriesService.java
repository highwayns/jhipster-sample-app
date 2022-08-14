package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.IsoCountries;
import io.github.jhipster.sample.repository.IsoCountriesRepository;
import io.github.jhipster.sample.service.dto.IsoCountriesDTO;
import io.github.jhipster.sample.service.mapper.IsoCountriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IsoCountries}.
 */
@Service
@Transactional
public class IsoCountriesService {

    private final Logger log = LoggerFactory.getLogger(IsoCountriesService.class);

    private final IsoCountriesRepository isoCountriesRepository;

    private final IsoCountriesMapper isoCountriesMapper;

    public IsoCountriesService(IsoCountriesRepository isoCountriesRepository, IsoCountriesMapper isoCountriesMapper) {
        this.isoCountriesRepository = isoCountriesRepository;
        this.isoCountriesMapper = isoCountriesMapper;
    }

    /**
     * Save a isoCountries.
     *
     * @param isoCountriesDTO the entity to save.
     * @return the persisted entity.
     */
    public IsoCountriesDTO save(IsoCountriesDTO isoCountriesDTO) {
        log.debug("Request to save IsoCountries : {}", isoCountriesDTO);
        IsoCountries isoCountries = isoCountriesMapper.toEntity(isoCountriesDTO);
        isoCountries = isoCountriesRepository.save(isoCountries);
        return isoCountriesMapper.toDto(isoCountries);
    }

    /**
     * Update a isoCountries.
     *
     * @param isoCountriesDTO the entity to save.
     * @return the persisted entity.
     */
    public IsoCountriesDTO update(IsoCountriesDTO isoCountriesDTO) {
        log.debug("Request to save IsoCountries : {}", isoCountriesDTO);
        IsoCountries isoCountries = isoCountriesMapper.toEntity(isoCountriesDTO);
        isoCountries = isoCountriesRepository.save(isoCountries);
        return isoCountriesMapper.toDto(isoCountries);
    }

    /**
     * Partially update a isoCountries.
     *
     * @param isoCountriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IsoCountriesDTO> partialUpdate(IsoCountriesDTO isoCountriesDTO) {
        log.debug("Request to partially update IsoCountries : {}", isoCountriesDTO);

        return isoCountriesRepository
            .findById(isoCountriesDTO.getId())
            .map(existingIsoCountries -> {
                isoCountriesMapper.partialUpdate(existingIsoCountries, isoCountriesDTO);

                return existingIsoCountries;
            })
            .map(isoCountriesRepository::save)
            .map(isoCountriesMapper::toDto);
    }

    /**
     * Get all the isoCountries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IsoCountriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IsoCountries");
        return isoCountriesRepository.findAll(pageable).map(isoCountriesMapper::toDto);
    }

    /**
     * Get one isoCountries by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IsoCountriesDTO> findOne(Long id) {
        log.debug("Request to get IsoCountries : {}", id);
        return isoCountriesRepository.findById(id).map(isoCountriesMapper::toDto);
    }

    /**
     * Delete the isoCountries by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IsoCountries : {}", id);
        isoCountriesRepository.deleteById(id);
    }
}
