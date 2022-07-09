package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.RefundStep;
import io.github.jhipster.sample.repository.RefundStepRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.RefundStep}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RefundStepResource {

    private final Logger log = LoggerFactory.getLogger(RefundStepResource.class);

    private static final String ENTITY_NAME = "refundStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefundStepRepository refundStepRepository;

    public RefundStepResource(RefundStepRepository refundStepRepository) {
        this.refundStepRepository = refundStepRepository;
    }

    /**
     * {@code POST  /refund-steps} : Create a new refundStep.
     *
     * @param refundStep the refundStep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refundStep, or with status {@code 400 (Bad Request)} if the refundStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refund-steps")
    public ResponseEntity<RefundStep> createRefundStep(@RequestBody RefundStep refundStep) throws URISyntaxException {
        log.debug("REST request to save RefundStep : {}", refundStep);
        if (refundStep.getId() != null) {
            throw new BadRequestAlertException("A new refundStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefundStep result = refundStepRepository.save(refundStep);
        return ResponseEntity
            .created(new URI("/api/refund-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /refund-steps/:id} : Updates an existing refundStep.
     *
     * @param id the id of the refundStep to save.
     * @param refundStep the refundStep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refundStep,
     * or with status {@code 400 (Bad Request)} if the refundStep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refundStep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refund-steps/{id}")
    public ResponseEntity<RefundStep> updateRefundStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RefundStep refundStep
    ) throws URISyntaxException {
        log.debug("REST request to update RefundStep : {}, {}", id, refundStep);
        if (refundStep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refundStep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refundStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RefundStep result = refundStepRepository.save(refundStep);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refundStep.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /refund-steps/:id} : Partial updates given fields of an existing refundStep, field will ignore if it is null
     *
     * @param id the id of the refundStep to save.
     * @param refundStep the refundStep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refundStep,
     * or with status {@code 400 (Bad Request)} if the refundStep is not valid,
     * or with status {@code 404 (Not Found)} if the refundStep is not found,
     * or with status {@code 500 (Internal Server Error)} if the refundStep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/refund-steps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RefundStep> partialUpdateRefundStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RefundStep refundStep
    ) throws URISyntaxException {
        log.debug("REST request to partial update RefundStep partially : {}, {}", id, refundStep);
        if (refundStep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refundStep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refundStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RefundStep> result = refundStepRepository
            .findById(refundStep.getId())
            .map(existingRefundStep -> {
                if (refundStep.getReference() != null) {
                    existingRefundStep.setReference(refundStep.getReference());
                }
                if (refundStep.getCreateDateTimeUtc() != null) {
                    existingRefundStep.setCreateDateTimeUtc(refundStep.getCreateDateTimeUtc());
                }
                if (refundStep.getAction() != null) {
                    existingRefundStep.setAction(refundStep.getAction());
                }
                if (refundStep.getStatus() != null) {
                    existingRefundStep.setStatus(refundStep.getStatus());
                }
                if (refundStep.getDescription() != null) {
                    existingRefundStep.setDescription(refundStep.getDescription());
                }

                return existingRefundStep;
            })
            .map(refundStepRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refundStep.getId().toString())
        );
    }

    /**
     * {@code GET  /refund-steps} : get all the refundSteps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refundSteps in body.
     */
    @GetMapping("/refund-steps")
    public List<RefundStep> getAllRefundSteps() {
        log.debug("REST request to get all RefundSteps");
        return refundStepRepository.findAll();
    }

    /**
     * {@code GET  /refund-steps/:id} : get the "id" refundStep.
     *
     * @param id the id of the refundStep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refundStep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refund-steps/{id}")
    public ResponseEntity<RefundStep> getRefundStep(@PathVariable Long id) {
        log.debug("REST request to get RefundStep : {}", id);
        Optional<RefundStep> refundStep = refundStepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(refundStep);
    }

    /**
     * {@code DELETE  /refund-steps/:id} : delete the "id" refundStep.
     *
     * @param id the id of the refundStep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refund-steps/{id}")
    public ResponseEntity<Void> deleteRefundStep(@PathVariable Long id) {
        log.debug("REST request to delete RefundStep : {}", id);
        refundStepRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
