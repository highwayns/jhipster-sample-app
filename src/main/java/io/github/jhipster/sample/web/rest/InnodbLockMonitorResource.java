package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.InnodbLockMonitorRepository;
import io.github.jhipster.sample.service.InnodbLockMonitorService;
import io.github.jhipster.sample.service.dto.InnodbLockMonitorDTO;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.InnodbLockMonitor}.
 */
@RestController
@RequestMapping("/api")
public class InnodbLockMonitorResource {

    private final Logger log = LoggerFactory.getLogger(InnodbLockMonitorResource.class);

    private static final String ENTITY_NAME = "innodbLockMonitor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InnodbLockMonitorService innodbLockMonitorService;

    private final InnodbLockMonitorRepository innodbLockMonitorRepository;

    public InnodbLockMonitorResource(
        InnodbLockMonitorService innodbLockMonitorService,
        InnodbLockMonitorRepository innodbLockMonitorRepository
    ) {
        this.innodbLockMonitorService = innodbLockMonitorService;
        this.innodbLockMonitorRepository = innodbLockMonitorRepository;
    }

    /**
     * {@code POST  /innodb-lock-monitors} : Create a new innodbLockMonitor.
     *
     * @param innodbLockMonitorDTO the innodbLockMonitorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new innodbLockMonitorDTO, or with status {@code 400 (Bad Request)} if the innodbLockMonitor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/innodb-lock-monitors")
    public ResponseEntity<InnodbLockMonitorDTO> createInnodbLockMonitor(@RequestBody InnodbLockMonitorDTO innodbLockMonitorDTO)
        throws URISyntaxException {
        log.debug("REST request to save InnodbLockMonitor : {}", innodbLockMonitorDTO);
        if (innodbLockMonitorDTO.getId() != null) {
            throw new BadRequestAlertException("A new innodbLockMonitor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InnodbLockMonitorDTO result = innodbLockMonitorService.save(innodbLockMonitorDTO);
        return ResponseEntity
            .created(new URI("/api/innodb-lock-monitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /innodb-lock-monitors/:id} : Updates an existing innodbLockMonitor.
     *
     * @param id the id of the innodbLockMonitorDTO to save.
     * @param innodbLockMonitorDTO the innodbLockMonitorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated innodbLockMonitorDTO,
     * or with status {@code 400 (Bad Request)} if the innodbLockMonitorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the innodbLockMonitorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/innodb-lock-monitors/{id}")
    public ResponseEntity<InnodbLockMonitorDTO> updateInnodbLockMonitor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InnodbLockMonitorDTO innodbLockMonitorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InnodbLockMonitor : {}, {}", id, innodbLockMonitorDTO);
        if (innodbLockMonitorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, innodbLockMonitorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!innodbLockMonitorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InnodbLockMonitorDTO result = innodbLockMonitorService.update(innodbLockMonitorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, innodbLockMonitorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /innodb-lock-monitors/:id} : Partial updates given fields of an existing innodbLockMonitor, field will ignore if it is null
     *
     * @param id the id of the innodbLockMonitorDTO to save.
     * @param innodbLockMonitorDTO the innodbLockMonitorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated innodbLockMonitorDTO,
     * or with status {@code 400 (Bad Request)} if the innodbLockMonitorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the innodbLockMonitorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the innodbLockMonitorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/innodb-lock-monitors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InnodbLockMonitorDTO> partialUpdateInnodbLockMonitor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InnodbLockMonitorDTO innodbLockMonitorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InnodbLockMonitor partially : {}, {}", id, innodbLockMonitorDTO);
        if (innodbLockMonitorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, innodbLockMonitorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!innodbLockMonitorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InnodbLockMonitorDTO> result = innodbLockMonitorService.partialUpdate(innodbLockMonitorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, innodbLockMonitorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /innodb-lock-monitors} : get all the innodbLockMonitors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of innodbLockMonitors in body.
     */
    @GetMapping("/innodb-lock-monitors")
    public ResponseEntity<List<InnodbLockMonitorDTO>> getAllInnodbLockMonitors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of InnodbLockMonitors");
        Page<InnodbLockMonitorDTO> page = innodbLockMonitorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /innodb-lock-monitors/:id} : get the "id" innodbLockMonitor.
     *
     * @param id the id of the innodbLockMonitorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the innodbLockMonitorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/innodb-lock-monitors/{id}")
    public ResponseEntity<InnodbLockMonitorDTO> getInnodbLockMonitor(@PathVariable Long id) {
        log.debug("REST request to get InnodbLockMonitor : {}", id);
        Optional<InnodbLockMonitorDTO> innodbLockMonitorDTO = innodbLockMonitorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(innodbLockMonitorDTO);
    }

    /**
     * {@code DELETE  /innodb-lock-monitors/:id} : delete the "id" innodbLockMonitor.
     *
     * @param id the id of the innodbLockMonitorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/innodb-lock-monitors/{id}")
    public ResponseEntity<Void> deleteInnodbLockMonitor(@PathVariable Long id) {
        log.debug("REST request to delete InnodbLockMonitor : {}", id);
        innodbLockMonitorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
