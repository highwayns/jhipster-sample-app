package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.AbuseReport;
import io.github.jhipster.sample.repository.AbuseReportRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AbuseReport}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AbuseReportResource {

    private final Logger log = LoggerFactory.getLogger(AbuseReportResource.class);

    private static final String ENTITY_NAME = "abuseReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbuseReportRepository abuseReportRepository;

    public AbuseReportResource(AbuseReportRepository abuseReportRepository) {
        this.abuseReportRepository = abuseReportRepository;
    }

    /**
     * {@code POST  /abuse-reports} : Create a new abuseReport.
     *
     * @param abuseReport the abuseReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new abuseReport, or with status {@code 400 (Bad Request)} if the abuseReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/abuse-reports")
    public ResponseEntity<AbuseReport> createAbuseReport(@RequestBody AbuseReport abuseReport) throws URISyntaxException {
        log.debug("REST request to save AbuseReport : {}", abuseReport);
        if (abuseReport.getId() != null) {
            throw new BadRequestAlertException("A new abuseReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbuseReport result = abuseReportRepository.save(abuseReport);
        return ResponseEntity
            .created(new URI("/api/abuse-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /abuse-reports/:id} : Updates an existing abuseReport.
     *
     * @param id the id of the abuseReport to save.
     * @param abuseReport the abuseReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abuseReport,
     * or with status {@code 400 (Bad Request)} if the abuseReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the abuseReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/abuse-reports/{id}")
    public ResponseEntity<AbuseReport> updateAbuseReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AbuseReport abuseReport
    ) throws URISyntaxException {
        log.debug("REST request to update AbuseReport : {}, {}", id, abuseReport);
        if (abuseReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abuseReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abuseReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AbuseReport result = abuseReportRepository.save(abuseReport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abuseReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /abuse-reports/:id} : Partial updates given fields of an existing abuseReport, field will ignore if it is null
     *
     * @param id the id of the abuseReport to save.
     * @param abuseReport the abuseReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abuseReport,
     * or with status {@code 400 (Bad Request)} if the abuseReport is not valid,
     * or with status {@code 404 (Not Found)} if the abuseReport is not found,
     * or with status {@code 500 (Internal Server Error)} if the abuseReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/abuse-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AbuseReport> partialUpdateAbuseReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AbuseReport abuseReport
    ) throws URISyntaxException {
        log.debug("REST request to partial update AbuseReport partially : {}, {}", id, abuseReport);
        if (abuseReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abuseReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abuseReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AbuseReport> result = abuseReportRepository
            .findById(abuseReport.getId())
            .map(existingAbuseReport -> {
                if (abuseReport.getScore() != null) {
                    existingAbuseReport.setScore(abuseReport.getScore());
                }
                if (abuseReport.getCreatedDateTimeUtc() != null) {
                    existingAbuseReport.setCreatedDateTimeUtc(abuseReport.getCreatedDateTimeUtc());
                }

                return existingAbuseReport;
            })
            .map(abuseReportRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abuseReport.getId().toString())
        );
    }

    /**
     * {@code GET  /abuse-reports} : get all the abuseReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abuseReports in body.
     */
    @GetMapping("/abuse-reports")
    public List<AbuseReport> getAllAbuseReports() {
        log.debug("REST request to get all AbuseReports");
        return abuseReportRepository.findAll();
    }

    /**
     * {@code GET  /abuse-reports/:id} : get the "id" abuseReport.
     *
     * @param id the id of the abuseReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the abuseReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/abuse-reports/{id}")
    public ResponseEntity<AbuseReport> getAbuseReport(@PathVariable Long id) {
        log.debug("REST request to get AbuseReport : {}", id);
        Optional<AbuseReport> abuseReport = abuseReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(abuseReport);
    }

    /**
     * {@code DELETE  /abuse-reports/:id} : delete the "id" abuseReport.
     *
     * @param id the id of the abuseReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/abuse-reports/{id}")
    public ResponseEntity<Void> deleteAbuseReport(@PathVariable Long id) {
        log.debug("REST request to delete AbuseReport : {}", id);
        abuseReportRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
