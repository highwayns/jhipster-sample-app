package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.IpAccessLog;
import io.github.jhipster.sample.repository.IpAccessLogRepository;
import io.github.jhipster.sample.service.dto.IpAccessLogDTO;
import io.github.jhipster.sample.service.mapper.IpAccessLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IpAccessLog}.
 */
@Service
@Transactional
public class IpAccessLogService {

    private final Logger log = LoggerFactory.getLogger(IpAccessLogService.class);

    private final IpAccessLogRepository ipAccessLogRepository;

    private final IpAccessLogMapper ipAccessLogMapper;

    public IpAccessLogService(IpAccessLogRepository ipAccessLogRepository, IpAccessLogMapper ipAccessLogMapper) {
        this.ipAccessLogRepository = ipAccessLogRepository;
        this.ipAccessLogMapper = ipAccessLogMapper;
    }

    /**
     * Save a ipAccessLog.
     *
     * @param ipAccessLogDTO the entity to save.
     * @return the persisted entity.
     */
    public IpAccessLogDTO save(IpAccessLogDTO ipAccessLogDTO) {
        log.debug("Request to save IpAccessLog : {}", ipAccessLogDTO);
        IpAccessLog ipAccessLog = ipAccessLogMapper.toEntity(ipAccessLogDTO);
        ipAccessLog = ipAccessLogRepository.save(ipAccessLog);
        return ipAccessLogMapper.toDto(ipAccessLog);
    }

    /**
     * Update a ipAccessLog.
     *
     * @param ipAccessLogDTO the entity to save.
     * @return the persisted entity.
     */
    public IpAccessLogDTO update(IpAccessLogDTO ipAccessLogDTO) {
        log.debug("Request to save IpAccessLog : {}", ipAccessLogDTO);
        IpAccessLog ipAccessLog = ipAccessLogMapper.toEntity(ipAccessLogDTO);
        ipAccessLog = ipAccessLogRepository.save(ipAccessLog);
        return ipAccessLogMapper.toDto(ipAccessLog);
    }

    /**
     * Partially update a ipAccessLog.
     *
     * @param ipAccessLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IpAccessLogDTO> partialUpdate(IpAccessLogDTO ipAccessLogDTO) {
        log.debug("Request to partially update IpAccessLog : {}", ipAccessLogDTO);

        return ipAccessLogRepository
            .findById(ipAccessLogDTO.getId())
            .map(existingIpAccessLog -> {
                ipAccessLogMapper.partialUpdate(existingIpAccessLog, ipAccessLogDTO);

                return existingIpAccessLog;
            })
            .map(ipAccessLogRepository::save)
            .map(ipAccessLogMapper::toDto);
    }

    /**
     * Get all the ipAccessLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IpAccessLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IpAccessLogs");
        return ipAccessLogRepository.findAll(pageable).map(ipAccessLogMapper::toDto);
    }

    /**
     * Get one ipAccessLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IpAccessLogDTO> findOne(Long id) {
        log.debug("Request to get IpAccessLog : {}", id);
        return ipAccessLogRepository.findById(id).map(ipAccessLogMapper::toDto);
    }

    /**
     * Delete the ipAccessLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IpAccessLog : {}", id);
        ipAccessLogRepository.deleteById(id);
    }
}
