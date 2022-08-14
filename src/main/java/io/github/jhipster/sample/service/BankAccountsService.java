package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.BankAccounts;
import io.github.jhipster.sample.repository.BankAccountsRepository;
import io.github.jhipster.sample.service.dto.BankAccountsDTO;
import io.github.jhipster.sample.service.mapper.BankAccountsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankAccounts}.
 */
@Service
@Transactional
public class BankAccountsService {

    private final Logger log = LoggerFactory.getLogger(BankAccountsService.class);

    private final BankAccountsRepository bankAccountsRepository;

    private final BankAccountsMapper bankAccountsMapper;

    public BankAccountsService(BankAccountsRepository bankAccountsRepository, BankAccountsMapper bankAccountsMapper) {
        this.bankAccountsRepository = bankAccountsRepository;
        this.bankAccountsMapper = bankAccountsMapper;
    }

    /**
     * Save a bankAccounts.
     *
     * @param bankAccountsDTO the entity to save.
     * @return the persisted entity.
     */
    public BankAccountsDTO save(BankAccountsDTO bankAccountsDTO) {
        log.debug("Request to save BankAccounts : {}", bankAccountsDTO);
        BankAccounts bankAccounts = bankAccountsMapper.toEntity(bankAccountsDTO);
        bankAccounts = bankAccountsRepository.save(bankAccounts);
        return bankAccountsMapper.toDto(bankAccounts);
    }

    /**
     * Update a bankAccounts.
     *
     * @param bankAccountsDTO the entity to save.
     * @return the persisted entity.
     */
    public BankAccountsDTO update(BankAccountsDTO bankAccountsDTO) {
        log.debug("Request to save BankAccounts : {}", bankAccountsDTO);
        BankAccounts bankAccounts = bankAccountsMapper.toEntity(bankAccountsDTO);
        bankAccounts = bankAccountsRepository.save(bankAccounts);
        return bankAccountsMapper.toDto(bankAccounts);
    }

    /**
     * Partially update a bankAccounts.
     *
     * @param bankAccountsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankAccountsDTO> partialUpdate(BankAccountsDTO bankAccountsDTO) {
        log.debug("Request to partially update BankAccounts : {}", bankAccountsDTO);

        return bankAccountsRepository
            .findById(bankAccountsDTO.getId())
            .map(existingBankAccounts -> {
                bankAccountsMapper.partialUpdate(existingBankAccounts, bankAccountsDTO);

                return existingBankAccounts;
            })
            .map(bankAccountsRepository::save)
            .map(bankAccountsMapper::toDto);
    }

    /**
     * Get all the bankAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankAccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankAccounts");
        return bankAccountsRepository.findAll(pageable).map(bankAccountsMapper::toDto);
    }

    /**
     * Get one bankAccounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankAccountsDTO> findOne(Long id) {
        log.debug("Request to get BankAccounts : {}", id);
        return bankAccountsRepository.findById(id).map(bankAccountsMapper::toDto);
    }

    /**
     * Delete the bankAccounts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankAccounts : {}", id);
        bankAccountsRepository.deleteById(id);
    }
}
