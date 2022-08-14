package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.DailyReportsRepository;
import io.github.jhipster.sample.service.DailyReportsService;
import io.github.jhipster.sample.service.dto.DailyReportsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.DailyReports}.
 */
@RestController
@RequestMapping("/api")
public class DailyReportsResource {

    private final Logger log = LoggerFactory.getLogger(DailyReportsResource.class);

    private static final String ENTITY_NAME = "dailyReports";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyReportsService dailyReportsService;

    private final DailyReportsRepository dailyReportsRepository;

    public DailyReportsResource(DailyReportsService dailyReportsService, DailyReportsRepository dailyReportsRepository) {
        this.dailyReportsService = dailyReportsService;
        this.dailyReportsRepository = dailyReportsRepository;
    }

    /**
     * {@code POST  /daily-reports} : Create a new dailyReports.
     *
     * @param dailyReportsDTO the dailyReportsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyReportsDTO, or with status {@code 400 (Bad Request)} if the dailyReports has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-reports")
    public ResponseEntity<DailyReportsDTO> createDailyReports(@Valid @RequestBody DailyReportsDTO dailyReportsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DailyReports : {}", dailyReportsDTO);
        if (dailyReportsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dailyReports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyReportsDTO result = dailyReportsService.save(dailyReportsDTO);
        return ResponseEntity
            .created(new URI("/api/daily-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-reports/:id} : Updates an existing dailyReports.
     *
     * @param id the id of the dailyReportsDTO to save.
     * @param dailyReportsDTO the dailyReportsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyReportsDTO,
     * or with status {@code 400 (Bad Request)} if the dailyReportsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyReportsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-reports/{id}")
    public ResponseEntity<DailyReportsDTO> updateDailyReports(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DailyReportsDTO dailyReportsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DailyReports : {}, {}", id, dailyReportsDTO);
        if (dailyReportsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyReportsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DailyReportsDTO result = dailyReportsService.update(dailyReportsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyReportsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /daily-reports/:id} : Partial updates given fields of an existing dailyReports, field will ignore if it is null
     *
     * @param id the id of the dailyReportsDTO to save.
     * @param dailyReportsDTO the dailyReportsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyReportsDTO,
     * or with status {@code 400 (Bad Request)} if the dailyReportsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dailyReportsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dailyReportsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/daily-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DailyReportsDTO> partialUpdateDailyReports(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DailyReportsDTO dailyReportsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DailyReports partially : {}, {}", id, dailyReportsDTO);
        if (dailyReportsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyReportsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyReportsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DailyReportsDTO> result = dailyReportsService.partialUpdate(dailyReportsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyReportsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /daily-reports} : get all the dailyReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyReports in body.
     */
    @GetMapping("/daily-reports")
    public ResponseEntity<List<DailyReportsDTO>> getAllDailyReports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DailyReports");
        Page<DailyReportsDTO> page = dailyReportsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /daily-reports/:id} : get the "id" dailyReports.
     *
     * @param id the id of the dailyReportsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyReportsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-reports/{id}")
    public ResponseEntity<DailyReportsDTO> getDailyReports(@PathVariable Long id) {
        log.debug("REST request to get DailyReports : {}", id);
        Optional<DailyReportsDTO> dailyReportsDTO = dailyReportsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyReportsDTO);
    }

    /**
     * {@code DELETE  /daily-reports/:id} : delete the "id" dailyReports.
     *
     * @param id the id of the dailyReportsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-reports/{id}")
    public ResponseEntity<Void> deleteDailyReports(@PathVariable Long id) {
        log.debug("REST request to delete DailyReports : {}", id);
        dailyReportsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
