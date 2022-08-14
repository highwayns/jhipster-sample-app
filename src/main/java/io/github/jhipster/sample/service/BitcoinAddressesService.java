package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.BitcoinAddresses;
import io.github.jhipster.sample.repository.BitcoinAddressesRepository;
import io.github.jhipster.sample.service.dto.BitcoinAddressesDTO;
import io.github.jhipster.sample.service.mapper.BitcoinAddressesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BitcoinAddresses}.
 */
@Service
@Transactional
public class BitcoinAddressesService {

    private final Logger log = LoggerFactory.getLogger(BitcoinAddressesService.class);

    private final BitcoinAddressesRepository bitcoinAddressesRepository;

    private final BitcoinAddressesMapper bitcoinAddressesMapper;

    public BitcoinAddressesService(BitcoinAddressesRepository bitcoinAddressesRepository, BitcoinAddressesMapper bitcoinAddressesMapper) {
        this.bitcoinAddressesRepository = bitcoinAddressesRepository;
        this.bitcoinAddressesMapper = bitcoinAddressesMapper;
    }

    /**
     * Save a bitcoinAddresses.
     *
     * @param bitcoinAddressesDTO the entity to save.
     * @return the persisted entity.
     */
    public BitcoinAddressesDTO save(BitcoinAddressesDTO bitcoinAddressesDTO) {
        log.debug("Request to save BitcoinAddresses : {}", bitcoinAddressesDTO);
        BitcoinAddresses bitcoinAddresses = bitcoinAddressesMapper.toEntity(bitcoinAddressesDTO);
        bitcoinAddresses = bitcoinAddressesRepository.save(bitcoinAddresses);
        return bitcoinAddressesMapper.toDto(bitcoinAddresses);
    }

    /**
     * Update a bitcoinAddresses.
     *
     * @param bitcoinAddressesDTO the entity to save.
     * @return the persisted entity.
     */
    public BitcoinAddressesDTO update(BitcoinAddressesDTO bitcoinAddressesDTO) {
        log.debug("Request to save BitcoinAddresses : {}", bitcoinAddressesDTO);
        BitcoinAddresses bitcoinAddresses = bitcoinAddressesMapper.toEntity(bitcoinAddressesDTO);
        bitcoinAddresses = bitcoinAddressesRepository.save(bitcoinAddresses);
        return bitcoinAddressesMapper.toDto(bitcoinAddresses);
    }

    /**
     * Partially update a bitcoinAddresses.
     *
     * @param bitcoinAddressesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BitcoinAddressesDTO> partialUpdate(BitcoinAddressesDTO bitcoinAddressesDTO) {
        log.debug("Request to partially update BitcoinAddresses : {}", bitcoinAddressesDTO);

        return bitcoinAddressesRepository
            .findById(bitcoinAddressesDTO.getId())
            .map(existingBitcoinAddresses -> {
                bitcoinAddressesMapper.partialUpdate(existingBitcoinAddresses, bitcoinAddressesDTO);

                return existingBitcoinAddresses;
            })
            .map(bitcoinAddressesRepository::save)
            .map(bitcoinAddressesMapper::toDto);
    }

    /**
     * Get all the bitcoinAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BitcoinAddressesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BitcoinAddresses");
        return bitcoinAddressesRepository.findAll(pageable).map(bitcoinAddressesMapper::toDto);
    }

    /**
     * Get one bitcoinAddresses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BitcoinAddressesDTO> findOne(Long id) {
        log.debug("Request to get BitcoinAddresses : {}", id);
        return bitcoinAddressesRepository.findById(id).map(bitcoinAddressesMapper::toDto);
    }

    /**
     * Delete the bitcoinAddresses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BitcoinAddresses : {}", id);
        bitcoinAddressesRepository.deleteById(id);
    }
}
