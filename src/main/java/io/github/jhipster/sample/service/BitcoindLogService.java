package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.BitcoindLog;
import io.github.jhipster.sample.repository.BitcoindLogRepository;
import io.github.jhipster.sample.service.dto.BitcoindLogDTO;
import io.github.jhipster.sample.service.mapper.BitcoindLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BitcoindLog}.
 */
@Service
@Transactional
public class BitcoindLogService {

    private final Logger log = LoggerFactory.getLogger(BitcoindLogService.class);

    private final BitcoindLogRepository bitcoindLogRepository;

    private final BitcoindLogMapper bitcoindLogMapper;

    public BitcoindLogService(BitcoindLogRepository bitcoindLogRepository, BitcoindLogMapper bitcoindLogMapper) {
        this.bitcoindLogRepository = bitcoindLogRepository;
        this.bitcoindLogMapper = bitcoindLogMapper;
    }

    /**
     * Save a bitcoindLog.
     *
     * @param bitcoindLogDTO the entity to save.
     * @return the persisted entity.
     */
    public BitcoindLogDTO save(BitcoindLogDTO bitcoindLogDTO) {
        log.debug("Request to save BitcoindLog : {}", bitcoindLogDTO);
        BitcoindLog bitcoindLog = bitcoindLogMapper.toEntity(bitcoindLogDTO);
        bitcoindLog = bitcoindLogRepository.save(bitcoindLog);
        return bitcoindLogMapper.toDto(bitcoindLog);
    }

    /**
     * Update a bitcoindLog.
     *
     * @param bitcoindLogDTO the entity to save.
     * @return the persisted entity.
     */
    public BitcoindLogDTO update(BitcoindLogDTO bitcoindLogDTO) {
        log.debug("Request to save BitcoindLog : {}", bitcoindLogDTO);
        BitcoindLog bitcoindLog = bitcoindLogMapper.toEntity(bitcoindLogDTO);
        bitcoindLog = bitcoindLogRepository.save(bitcoindLog);
        return bitcoindLogMapper.toDto(bitcoindLog);
    }

    /**
     * Partially update a bitcoindLog.
     *
     * @param bitcoindLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BitcoindLogDTO> partialUpdate(BitcoindLogDTO bitcoindLogDTO) {
        log.debug("Request to partially update BitcoindLog : {}", bitcoindLogDTO);

        return bitcoindLogRepository
            .findById(bitcoindLogDTO.getId())
            .map(existingBitcoindLog -> {
                bitcoindLogMapper.partialUpdate(existingBitcoindLog, bitcoindLogDTO);

                return existingBitcoindLog;
            })
            .map(bitcoindLogRepository::save)
            .map(bitcoindLogMapper::toDto);
    }

    /**
     * Get all the bitcoindLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BitcoindLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BitcoindLogs");
        return bitcoindLogRepository.findAll(pageable).map(bitcoindLogMapper::toDto);
    }

    /**
     * Get one bitcoindLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BitcoindLogDTO> findOne(Long id) {
        log.debug("Request to get BitcoindLog : {}", id);
        return bitcoindLogRepository.findById(id).map(bitcoindLogMapper::toDto);
    }

    /**
     * Delete the bitcoindLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BitcoindLog : {}", id);
        bitcoindLogRepository.deleteById(id);
    }
}
