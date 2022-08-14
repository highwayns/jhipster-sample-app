package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.FeeScheduleRepository;
import io.github.jhipster.sample.service.FeeScheduleService;
import io.github.jhipster.sample.service.dto.FeeScheduleDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.FeeSchedule}.
 */
@RestController
@RequestMapping("/api")
public class FeeScheduleResource {

    private final Logger log = LoggerFactory.getLogger(FeeScheduleResource.class);

    private static final String ENTITY_NAME = "feeSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeeScheduleService feeScheduleService;

    private final FeeScheduleRepository feeScheduleRepository;

    public FeeScheduleResource(FeeScheduleService feeScheduleService, FeeScheduleRepository feeScheduleRepository) {
        this.feeScheduleService = feeScheduleService;
        this.feeScheduleRepository = feeScheduleRepository;
    }

    /**
     * {@code POST  /fee-schedules} : Create a new feeSchedule.
     *
     * @param feeScheduleDTO the feeScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feeScheduleDTO, or with status {@code 400 (Bad Request)} if the feeSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fee-schedules")
    public ResponseEntity<FeeScheduleDTO> createFeeSchedule(@Valid @RequestBody FeeScheduleDTO feeScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save FeeSchedule : {}", feeScheduleDTO);
        if (feeScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new feeSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeeScheduleDTO result = feeScheduleService.save(feeScheduleDTO);
        return ResponseEntity
            .created(new URI("/api/fee-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fee-schedules/:id} : Updates an existing feeSchedule.
     *
     * @param id the id of the feeScheduleDTO to save.
     * @param feeScheduleDTO the feeScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feeScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the feeScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feeScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fee-schedules/{id}")
    public ResponseEntity<FeeScheduleDTO> updateFeeSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeeScheduleDTO feeScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FeeSchedule : {}, {}", id, feeScheduleDTO);
        if (feeScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feeScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feeScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeeScheduleDTO result = feeScheduleService.update(feeScheduleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feeScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fee-schedules/:id} : Partial updates given fields of an existing feeSchedule, field will ignore if it is null
     *
     * @param id the id of the feeScheduleDTO to save.
     * @param feeScheduleDTO the feeScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feeScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the feeScheduleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feeScheduleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feeScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fee-schedules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeeScheduleDTO> partialUpdateFeeSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeeScheduleDTO feeScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeeSchedule partially : {}, {}", id, feeScheduleDTO);
        if (feeScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feeScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feeScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeeScheduleDTO> result = feeScheduleService.partialUpdate(feeScheduleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feeScheduleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fee-schedules} : get all the feeSchedules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feeSchedules in body.
     */
    @GetMapping("/fee-schedules")
    public ResponseEntity<List<FeeScheduleDTO>> getAllFeeSchedules(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FeeSchedules");
        Page<FeeScheduleDTO> page = feeScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fee-schedules/:id} : get the "id" feeSchedule.
     *
     * @param id the id of the feeScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feeScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fee-schedules/{id}")
    public ResponseEntity<FeeScheduleDTO> getFeeSchedule(@PathVariable Long id) {
        log.debug("REST request to get FeeSchedule : {}", id);
        Optional<FeeScheduleDTO> feeScheduleDTO = feeScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feeScheduleDTO);
    }

    /**
     * {@code DELETE  /fee-schedules/:id} : delete the "id" feeSchedule.
     *
     * @param id the id of the feeScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fee-schedules/{id}")
    public ResponseEntity<Void> deleteFeeSchedule(@PathVariable Long id) {
        log.debug("REST request to delete FeeSchedule : {}", id);
        feeScheduleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
