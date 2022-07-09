package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.Capture;
import io.github.jhipster.sample.repository.CaptureRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.Capture}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CaptureResource {

    private final Logger log = LoggerFactory.getLogger(CaptureResource.class);

    private static final String ENTITY_NAME = "capture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaptureRepository captureRepository;

    public CaptureResource(CaptureRepository captureRepository) {
        this.captureRepository = captureRepository;
    }

    /**
     * {@code POST  /captures} : Create a new capture.
     *
     * @param capture the capture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new capture, or with status {@code 400 (Bad Request)} if the capture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/captures")
    public ResponseEntity<Capture> createCapture(@RequestBody Capture capture) throws URISyntaxException {
        log.debug("REST request to save Capture : {}", capture);
        if (capture.getId() != null) {
            throw new BadRequestAlertException("A new capture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Capture result = captureRepository.save(capture);
        return ResponseEntity
            .created(new URI("/api/captures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /captures/:id} : Updates an existing capture.
     *
     * @param id the id of the capture to save.
     * @param capture the capture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capture,
     * or with status {@code 400 (Bad Request)} if the capture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the capture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/captures/{id}")
    public ResponseEntity<Capture> updateCapture(@PathVariable(value = "id", required = false) final Long id, @RequestBody Capture capture)
        throws URISyntaxException {
        log.debug("REST request to update Capture : {}, {}", id, capture);
        if (capture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, capture.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!captureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Capture result = captureRepository.save(capture);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, capture.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /captures/:id} : Partial updates given fields of an existing capture, field will ignore if it is null
     *
     * @param id the id of the capture to save.
     * @param capture the capture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capture,
     * or with status {@code 400 (Bad Request)} if the capture is not valid,
     * or with status {@code 404 (Not Found)} if the capture is not found,
     * or with status {@code 500 (Internal Server Error)} if the capture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/captures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Capture> partialUpdateCapture(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Capture capture
    ) throws URISyntaxException {
        log.debug("REST request to partial update Capture partially : {}, {}", id, capture);
        if (capture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, capture.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!captureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Capture> result = captureRepository
            .findById(capture.getId())
            .map(existingCapture -> {
                if (capture.getReference() != null) {
                    existingCapture.setReference(capture.getReference());
                }
                if (capture.getCreateDateTimeUtc() != null) {
                    existingCapture.setCreateDateTimeUtc(capture.getCreateDateTimeUtc());
                }
                if (capture.getStatus() != null) {
                    existingCapture.setStatus(capture.getStatus());
                }
                if (capture.getAmountToCapture() != null) {
                    existingCapture.setAmountToCapture(capture.getAmountToCapture());
                }
                if (capture.getConvertedAmountToCapture() != null) {
                    existingCapture.setConvertedAmountToCapture(capture.getConvertedAmountToCapture());
                }
                if (capture.getConvertedCurrency() != null) {
                    existingCapture.setConvertedCurrency(capture.getConvertedCurrency());
                }
                if (capture.getConversionRate() != null) {
                    existingCapture.setConversionRate(capture.getConversionRate());
                }
                if (capture.getIsFinalCapture() != null) {
                    existingCapture.setIsFinalCapture(capture.getIsFinalCapture());
                }

                return existingCapture;
            })
            .map(captureRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, capture.getId().toString())
        );
    }

    /**
     * {@code GET  /captures} : get all the captures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of captures in body.
     */
    @GetMapping("/captures")
    public List<Capture> getAllCaptures() {
        log.debug("REST request to get all Captures");
        return captureRepository.findAll();
    }

    /**
     * {@code GET  /captures/:id} : get the "id" capture.
     *
     * @param id the id of the capture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the capture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/captures/{id}")
    public ResponseEntity<Capture> getCapture(@PathVariable Long id) {
        log.debug("REST request to get Capture : {}", id);
        Optional<Capture> capture = captureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(capture);
    }

    /**
     * {@code DELETE  /captures/:id} : delete the "id" capture.
     *
     * @param id the id of the capture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/captures/{id}")
    public ResponseEntity<Void> deleteCapture(@PathVariable Long id) {
        log.debug("REST request to delete Capture : {}", id);
        captureRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
