package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.Currencies;
import io.github.jhipster.sample.repository.CurrenciesRepository;
import io.github.jhipster.sample.service.dto.CurrenciesDTO;
import io.github.jhipster.sample.service.mapper.CurrenciesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Currencies}.
 */
@Service
@Transactional
public class CurrenciesService {

    private final Logger log = LoggerFactory.getLogger(CurrenciesService.class);

    private final CurrenciesRepository currenciesRepository;

    private final CurrenciesMapper currenciesMapper;

    public CurrenciesService(CurrenciesRepository currenciesRepository, CurrenciesMapper currenciesMapper) {
        this.currenciesRepository = currenciesRepository;
        this.currenciesMapper = currenciesMapper;
    }

    /**
     * Save a currencies.
     *
     * @param currenciesDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrenciesDTO save(CurrenciesDTO currenciesDTO) {
        log.debug("Request to save Currencies : {}", currenciesDTO);
        Currencies currencies = currenciesMapper.toEntity(currenciesDTO);
        currencies = currenciesRepository.save(currencies);
        return currenciesMapper.toDto(currencies);
    }

    /**
     * Update a currencies.
     *
     * @param currenciesDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrenciesDTO update(CurrenciesDTO currenciesDTO) {
        log.debug("Request to save Currencies : {}", currenciesDTO);
        Currencies currencies = currenciesMapper.toEntity(currenciesDTO);
        currencies = currenciesRepository.save(currencies);
        return currenciesMapper.toDto(currencies);
    }

    /**
     * Partially update a currencies.
     *
     * @param currenciesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CurrenciesDTO> partialUpdate(CurrenciesDTO currenciesDTO) {
        log.debug("Request to partially update Currencies : {}", currenciesDTO);

        return currenciesRepository
            .findById(currenciesDTO.getId())
            .map(existingCurrencies -> {
                currenciesMapper.partialUpdate(existingCurrencies, currenciesDTO);

                return existingCurrencies;
            })
            .map(currenciesRepository::save)
            .map(currenciesMapper::toDto);
    }

    /**
     * Get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrenciesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Currencies");
        return currenciesRepository.findAll(pageable).map(currenciesMapper::toDto);
    }

    /**
     * Get one currencies by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CurrenciesDTO> findOne(Long id) {
        log.debug("Request to get Currencies : {}", id);
        return currenciesRepository.findById(id).map(currenciesMapper::toDto);
    }

    /**
     * Delete the currencies by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Currencies : {}", id);
        currenciesRepository.deleteById(id);
    }
}
