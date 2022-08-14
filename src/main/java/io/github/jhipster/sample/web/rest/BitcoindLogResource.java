package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.BitcoindLogRepository;
import io.github.jhipster.sample.service.BitcoindLogService;
import io.github.jhipster.sample.service.dto.BitcoindLogDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.BitcoindLog}.
 */
@RestController
@RequestMapping("/api")
public class BitcoindLogResource {

    private final Logger log = LoggerFactory.getLogger(BitcoindLogResource.class);

    private static final String ENTITY_NAME = "bitcoindLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BitcoindLogService bitcoindLogService;

    private final BitcoindLogRepository bitcoindLogRepository;

    public BitcoindLogResource(BitcoindLogService bitcoindLogService, BitcoindLogRepository bitcoindLogRepository) {
        this.bitcoindLogService = bitcoindLogService;
        this.bitcoindLogRepository = bitcoindLogRepository;
    }

    /**
     * {@code POST  /bitcoind-logs} : Create a new bitcoindLog.
     *
     * @param bitcoindLogDTO the bitcoindLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bitcoindLogDTO, or with status {@code 400 (Bad Request)} if the bitcoindLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bitcoind-logs")
    public ResponseEntity<BitcoindLogDTO> createBitcoindLog(@Valid @RequestBody BitcoindLogDTO bitcoindLogDTO) throws URISyntaxException {
        log.debug("REST request to save BitcoindLog : {}", bitcoindLogDTO);
        if (bitcoindLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new bitcoindLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BitcoindLogDTO result = bitcoindLogService.save(bitcoindLogDTO);
        return ResponseEntity
            .created(new URI("/api/bitcoind-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bitcoind-logs/:id} : Updates an existing bitcoindLog.
     *
     * @param id the id of the bitcoindLogDTO to save.
     * @param bitcoindLogDTO the bitcoindLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bitcoindLogDTO,
     * or with status {@code 400 (Bad Request)} if the bitcoindLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bitcoindLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bitcoind-logs/{id}")
    public ResponseEntity<BitcoindLogDTO> updateBitcoindLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BitcoindLogDTO bitcoindLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BitcoindLog : {}, {}", id, bitcoindLogDTO);
        if (bitcoindLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bitcoindLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bitcoindLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BitcoindLogDTO result = bitcoindLogService.update(bitcoindLogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bitcoindLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bitcoind-logs/:id} : Partial updates given fields of an existing bitcoindLog, field will ignore if it is null
     *
     * @param id the id of the bitcoindLogDTO to save.
     * @param bitcoindLogDTO the bitcoindLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bitcoindLogDTO,
     * or with status {@code 400 (Bad Request)} if the bitcoindLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bitcoindLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bitcoindLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bitcoind-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BitcoindLogDTO> partialUpdateBitcoindLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BitcoindLogDTO bitcoindLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BitcoindLog partially : {}, {}", id, bitcoindLogDTO);
        if (bitcoindLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bitcoindLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bitcoindLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BitcoindLogDTO> result = bitcoindLogService.partialUpdate(bitcoindLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bitcoindLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bitcoind-logs} : get all the bitcoindLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bitcoindLogs in body.
     */
    @GetMapping("/bitcoind-logs")
    public ResponseEntity<List<BitcoindLogDTO>> getAllBitcoindLogs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BitcoindLogs");
        Page<BitcoindLogDTO> page = bitcoindLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bitcoind-logs/:id} : get the "id" bitcoindLog.
     *
     * @param id the id of the bitcoindLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bitcoindLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bitcoind-logs/{id}")
    public ResponseEntity<BitcoindLogDTO> getBitcoindLog(@PathVariable Long id) {
        log.debug("REST request to get BitcoindLog : {}", id);
        Optional<BitcoindLogDTO> bitcoindLogDTO = bitcoindLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bitcoindLogDTO);
    }

    /**
     * {@code DELETE  /bitcoind-logs/:id} : delete the "id" bitcoindLog.
     *
     * @param id the id of the bitcoindLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bitcoind-logs/{id}")
    public ResponseEntity<Void> deleteBitcoindLog(@PathVariable Long id) {
        log.debug("REST request to delete BitcoindLog : {}", id);
        bitcoindLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
