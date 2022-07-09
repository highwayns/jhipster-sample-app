package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.ErrorReport;
import io.github.jhipster.sample.repository.ErrorReportRepository;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.ErrorReport}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ErrorReportResource {

    private final Logger log = LoggerFactory.getLogger(ErrorReportResource.class);

    private static final String ENTITY_NAME = "errorReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ErrorReportRepository errorReportRepository;

    public ErrorReportResource(ErrorReportRepository errorReportRepository) {
        this.errorReportRepository = errorReportRepository;
    }

    /**
     * {@code POST  /error-reports} : Create a new errorReport.
     *
     * @param errorReport the errorReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new errorReport, or with status {@code 400 (Bad Request)} if the errorReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/error-reports")
    public ResponseEntity<ErrorReport> createErrorReport(@RequestBody ErrorReport errorReport) throws URISyntaxException {
        log.debug("REST request to save ErrorReport : {}", errorReport);
        if (errorReport.getId() != null) {
            throw new BadRequestAlertException("A new errorReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ErrorReport result = errorReportRepository.save(errorReport);
        return ResponseEntity
            .created(new URI("/api/error-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /error-reports/:id} : Updates an existing errorReport.
     *
     * @param id the id of the errorReport to save.
     * @param errorReport the errorReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errorReport,
     * or with status {@code 400 (Bad Request)} if the errorReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the errorReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/error-reports/{id}")
    public ResponseEntity<ErrorReport> updateErrorReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ErrorReport errorReport
    ) throws URISyntaxException {
        log.debug("REST request to update ErrorReport : {}, {}", id, errorReport);
        if (errorReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, errorReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!errorReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ErrorReport result = errorReportRepository.save(errorReport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, errorReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /error-reports/:id} : Partial updates given fields of an existing errorReport, field will ignore if it is null
     *
     * @param id the id of the errorReport to save.
     * @param errorReport the errorReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errorReport,
     * or with status {@code 400 (Bad Request)} if the errorReport is not valid,
     * or with status {@code 404 (Not Found)} if the errorReport is not found,
     * or with status {@code 500 (Internal Server Error)} if the errorReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/error-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ErrorReport> partialUpdateErrorReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ErrorReport errorReport
    ) throws URISyntaxException {
        log.debug("REST request to partial update ErrorReport partially : {}, {}", id, errorReport);
        if (errorReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, errorReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!errorReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ErrorReport> result = errorReportRepository
            .findById(errorReport.getId())
            .map(existingErrorReport -> {
                if (errorReport.getLanguage() != null) {
                    existingErrorReport.setLanguage(errorReport.getLanguage());
                }
                if (errorReport.getIsFatalError() != null) {
                    existingErrorReport.setIsFatalError(errorReport.getIsFatalError());
                }

                return existingErrorReport;
            })
            .map(errorReportRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, errorReport.getId().toString())
        );
    }

    /**
     * {@code GET  /error-reports} : get all the errorReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of errorReports in body.
     */
    @GetMapping("/error-reports")
    public List<ErrorReport> getAllErrorReports() {
        log.debug("REST request to get all ErrorReports");
        return errorReportRepository.findAll();
    }

    /**
     * {@code GET  /error-reports/:id} : get the "id" errorReport.
     *
     * @param id the id of the errorReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the errorReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/error-reports/{id}")
    public ResponseEntity<ErrorReport> getErrorReport(@PathVariable Long id) {
        log.debug("REST request to get ErrorReport : {}", id);
        Optional<ErrorReport> errorReport = errorReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(errorReport);
    }

    /**
     * {@code DELETE  /error-reports/:id} : delete the "id" errorReport.
     *
     * @param id the id of the errorReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/error-reports/{id}")
    public ResponseEntity<Void> deleteErrorReport(@PathVariable Long id) {
        log.debug("REST request to delete ErrorReport : {}", id);
        errorReportRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
