package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.MonthlyReportsRepository;
import io.github.jhipster.sample.service.MonthlyReportsService;
import io.github.jhipster.sample.service.dto.MonthlyReportsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.MonthlyReports}.
 */
@RestController
@RequestMapping("/api")
public class MonthlyReportsResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyReportsResource.class);

    private static final String ENTITY_NAME = "monthlyReports";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonthlyReportsService monthlyReportsService;

    private final MonthlyReportsRepository monthlyReportsRepository;

    public MonthlyReportsResource(MonthlyReportsService monthlyReportsService, MonthlyReportsRepository monthlyReportsRepository) {
        this.monthlyReportsService = monthlyReportsService;
        this.monthlyReportsRepository = monthlyReportsRepository;
    }

    /**
     * {@code POST  /monthly-reports} : Create a new monthlyReports.
     *
     * @param monthlyReportsDTO the monthlyReportsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monthlyReportsDTO, or with status {@code 400 (Bad Request)} if the monthlyReports has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monthly-reports")
    public ResponseEntity<MonthlyReportsDTO> createMonthlyReports(@Valid @RequestBody MonthlyReportsDTO monthlyReportsDTO)
        throws URISyntaxException {
        log.debug("REST request to save MonthlyReports : {}", monthlyReportsDTO);
        if (monthlyReportsDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlyReports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlyReportsDTO result = monthlyReportsService.save(monthlyReportsDTO);
        return ResponseEntity
            .created(new URI("/api/monthly-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monthly-reports/:id} : Updates an existing monthlyReports.
     *
     * @param id the id of the monthlyReportsDTO to save.
     * @param monthlyReportsDTO the monthlyReportsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyReportsDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyReportsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monthlyReportsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monthly-reports/{id}")
    public ResponseEntity<MonthlyReportsDTO> updateMonthlyReports(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MonthlyReportsDTO monthlyReportsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MonthlyReports : {}, {}", id, monthlyReportsDTO);
        if (monthlyReportsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyReportsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MonthlyReportsDTO result = monthlyReportsService.update(monthlyReportsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monthlyReportsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /monthly-reports/:id} : Partial updates given fields of an existing monthlyReports, field will ignore if it is null
     *
     * @param id the id of the monthlyReportsDTO to save.
     * @param monthlyReportsDTO the monthlyReportsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyReportsDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyReportsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the monthlyReportsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the monthlyReportsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/monthly-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MonthlyReportsDTO> partialUpdateMonthlyReports(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MonthlyReportsDTO monthlyReportsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MonthlyReports partially : {}, {}", id, monthlyReportsDTO);
        if (monthlyReportsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyReportsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MonthlyReportsDTO> result = monthlyReportsService.partialUpdate(monthlyReportsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monthlyReportsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /monthly-reports} : get all the monthlyReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monthlyReports in body.
     */
    @GetMapping("/monthly-reports")
    public ResponseEntity<List<MonthlyReportsDTO>> getAllMonthlyReports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MonthlyReports");
        Page<MonthlyReportsDTO> page = monthlyReportsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /monthly-reports/:id} : get the "id" monthlyReports.
     *
     * @param id the id of the monthlyReportsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlyReportsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-reports/{id}")
    public ResponseEntity<MonthlyReportsDTO> getMonthlyReports(@PathVariable Long id) {
        log.debug("REST request to get MonthlyReports : {}", id);
        Optional<MonthlyReportsDTO> monthlyReportsDTO = monthlyReportsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlyReportsDTO);
    }

    /**
     * {@code DELETE  /monthly-reports/:id} : delete the "id" monthlyReports.
     *
     * @param id the id of the monthlyReportsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monthly-reports/{id}")
    public ResponseEntity<Void> deleteMonthlyReports(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyReports : {}", id);
        monthlyReportsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
