package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.TransactionTypes;
import io.github.jhipster.sample.repository.TransactionTypesRepository;
import io.github.jhipster.sample.service.dto.TransactionTypesDTO;
import io.github.jhipster.sample.service.mapper.TransactionTypesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransactionTypes}.
 */
@Service
@Transactional
public class TransactionTypesService {

    private final Logger log = LoggerFactory.getLogger(TransactionTypesService.class);

    private final TransactionTypesRepository transactionTypesRepository;

    private final TransactionTypesMapper transactionTypesMapper;

    public TransactionTypesService(TransactionTypesRepository transactionTypesRepository, TransactionTypesMapper transactionTypesMapper) {
        this.transactionTypesRepository = transactionTypesRepository;
        this.transactionTypesMapper = transactionTypesMapper;
    }

    /**
     * Save a transactionTypes.
     *
     * @param transactionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public TransactionTypesDTO save(TransactionTypesDTO transactionTypesDTO) {
        log.debug("Request to save TransactionTypes : {}", transactionTypesDTO);
        TransactionTypes transactionTypes = transactionTypesMapper.toEntity(transactionTypesDTO);
        transactionTypes = transactionTypesRepository.save(transactionTypes);
        return transactionTypesMapper.toDto(transactionTypes);
    }

    /**
     * Update a transactionTypes.
     *
     * @param transactionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public TransactionTypesDTO update(TransactionTypesDTO transactionTypesDTO) {
        log.debug("Request to save TransactionTypes : {}", transactionTypesDTO);
        TransactionTypes transactionTypes = transactionTypesMapper.toEntity(transactionTypesDTO);
        transactionTypes = transactionTypesRepository.save(transactionTypes);
        return transactionTypesMapper.toDto(transactionTypes);
    }

    /**
     * Partially update a transactionTypes.
     *
     * @param transactionTypesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransactionTypesDTO> partialUpdate(TransactionTypesDTO transactionTypesDTO) {
        log.debug("Request to partially update TransactionTypes : {}", transactionTypesDTO);

        return transactionTypesRepository
            .findById(transactionTypesDTO.getId())
            .map(existingTransactionTypes -> {
                transactionTypesMapper.partialUpdate(existingTransactionTypes, transactionTypesDTO);

                return existingTransactionTypes;
            })
            .map(transactionTypesRepository::save)
            .map(transactionTypesMapper::toDto);
    }

    /**
     * Get all the transactionTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionTypesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionTypes");
        return transactionTypesRepository.findAll(pageable).map(transactionTypesMapper::toDto);
    }

    /**
     * Get one transactionTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionTypesDTO> findOne(Long id) {
        log.debug("Request to get TransactionTypes : {}", id);
        return transactionTypesRepository.findById(id).map(transactionTypesMapper::toDto);
    }

    /**
     * Delete the transactionTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransactionTypes : {}", id);
        transactionTypesRepository.deleteById(id);
    }
}
