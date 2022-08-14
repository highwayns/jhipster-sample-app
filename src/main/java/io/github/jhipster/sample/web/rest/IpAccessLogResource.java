package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.IpAccessLogRepository;
import io.github.jhipster.sample.service.IpAccessLogService;
import io.github.jhipster.sample.service.dto.IpAccessLogDTO;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.IpAccessLog}.
 */
@RestController
@RequestMapping("/api")
public class IpAccessLogResource {

    private final Logger log = LoggerFactory.getLogger(IpAccessLogResource.class);

    private static final String ENTITY_NAME = "ipAccessLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IpAccessLogService ipAccessLogService;

    private final IpAccessLogRepository ipAccessLogRepository;

    public IpAccessLogResource(IpAccessLogService ipAccessLogService, IpAccessLogRepository ipAccessLogRepository) {
        this.ipAccessLogService = ipAccessLogService;
        this.ipAccessLogRepository = ipAccessLogRepository;
    }

    /**
     * {@code POST  /ip-access-logs} : Create a new ipAccessLog.
     *
     * @param ipAccessLogDTO the ipAccessLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ipAccessLogDTO, or with status {@code 400 (Bad Request)} if the ipAccessLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ip-access-logs")
    public ResponseEntity<IpAccessLogDTO> createIpAccessLog(@Valid @RequestBody IpAccessLogDTO ipAccessLogDTO) throws URISyntaxException {
        log.debug("REST request to save IpAccessLog : {}", ipAccessLogDTO);
        if (ipAccessLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new ipAccessLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IpAccessLogDTO result = ipAccessLogService.save(ipAccessLogDTO);
        return ResponseEntity
            .created(new URI("/api/ip-access-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ip-access-logs/:id} : Updates an existing ipAccessLog.
     *
     * @param id the id of the ipAccessLogDTO to save.
     * @param ipAccessLogDTO the ipAccessLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ipAccessLogDTO,
     * or with status {@code 400 (Bad Request)} if the ipAccessLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ipAccessLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ip-access-logs/{id}")
    public ResponseEntity<IpAccessLogDTO> updateIpAccessLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IpAccessLogDTO ipAccessLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IpAccessLog : {}, {}", id, ipAccessLogDTO);
        if (ipAccessLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ipAccessLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ipAccessLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IpAccessLogDTO result = ipAccessLogService.update(ipAccessLogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ipAccessLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ip-access-logs/:id} : Partial updates given fields of an existing ipAccessLog, field will ignore if it is null
     *
     * @param id the id of the ipAccessLogDTO to save.
     * @param ipAccessLogDTO the ipAccessLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ipAccessLogDTO,
     * or with status {@code 400 (Bad Request)} if the ipAccessLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ipAccessLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ipAccessLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ip-access-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IpAccessLogDTO> partialUpdateIpAccessLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IpAccessLogDTO ipAccessLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IpAccessLog partially : {}, {}", id, ipAccessLogDTO);
        if (ipAccessLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ipAccessLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ipAccessLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IpAccessLogDTO> result = ipAccessLogService.partialUpdate(ipAccessLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ipAccessLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ip-access-logs} : get all the ipAccessLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ipAccessLogs in body.
     */
    @GetMapping("/ip-access-logs")
    public ResponseEntity<List<IpAccessLogDTO>> getAllIpAccessLogs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of IpAccessLogs");
        Page<IpAccessLogDTO> page = ipAccessLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ip-access-logs/:id} : get the "id" ipAccessLog.
     *
     * @param id the id of the ipAccessLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ipAccessLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ip-access-logs/{id}")
    public ResponseEntity<IpAccessLogDTO> getIpAccessLog(@PathVariable Long id) {
        log.debug("REST request to get IpAccessLog : {}", id);
        Optional<IpAccessLogDTO> ipAccessLogDTO = ipAccessLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ipAccessLogDTO);
    }

    /**
     * {@code DELETE  /ip-access-logs/:id} : delete the "id" ipAccessLog.
     *
     * @param id the id of the ipAccessLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ip-access-logs/{id}")
    public ResponseEntity<Void> deleteIpAccessLog(@PathVariable Long id) {
        log.debug("REST request to delete IpAccessLog : {}", id);
        ipAccessLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
