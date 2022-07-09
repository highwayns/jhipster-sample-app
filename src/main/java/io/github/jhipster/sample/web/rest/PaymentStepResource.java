package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.PaymentStep;
import io.github.jhipster.sample.repository.PaymentStepRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.PaymentStep}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentStepResource {

    private final Logger log = LoggerFactory.getLogger(PaymentStepResource.class);

    private static final String ENTITY_NAME = "paymentStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentStepRepository paymentStepRepository;

    public PaymentStepResource(PaymentStepRepository paymentStepRepository) {
        this.paymentStepRepository = paymentStepRepository;
    }

    /**
     * {@code POST  /payment-steps} : Create a new paymentStep.
     *
     * @param paymentStep the paymentStep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentStep, or with status {@code 400 (Bad Request)} if the paymentStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-steps")
    public ResponseEntity<PaymentStep> createPaymentStep(@RequestBody PaymentStep paymentStep) throws URISyntaxException {
        log.debug("REST request to save PaymentStep : {}", paymentStep);
        if (paymentStep.getId() != null) {
            throw new BadRequestAlertException("A new paymentStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentStep result = paymentStepRepository.save(paymentStep);
        return ResponseEntity
            .created(new URI("/api/payment-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-steps/:id} : Updates an existing paymentStep.
     *
     * @param id the id of the paymentStep to save.
     * @param paymentStep the paymentStep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentStep,
     * or with status {@code 400 (Bad Request)} if the paymentStep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentStep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-steps/{id}")
    public ResponseEntity<PaymentStep> updatePaymentStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentStep paymentStep
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentStep : {}, {}", id, paymentStep);
        if (paymentStep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentStep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentStep result = paymentStepRepository.save(paymentStep);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentStep.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-steps/:id} : Partial updates given fields of an existing paymentStep, field will ignore if it is null
     *
     * @param id the id of the paymentStep to save.
     * @param paymentStep the paymentStep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentStep,
     * or with status {@code 400 (Bad Request)} if the paymentStep is not valid,
     * or with status {@code 404 (Not Found)} if the paymentStep is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentStep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-steps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentStep> partialUpdatePaymentStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentStep paymentStep
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentStep partially : {}, {}", id, paymentStep);
        if (paymentStep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentStep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentStep> result = paymentStepRepository
            .findById(paymentStep.getId())
            .map(existingPaymentStep -> {
                if (paymentStep.getReference() != null) {
                    existingPaymentStep.setReference(paymentStep.getReference());
                }
                if (paymentStep.getCreateDateTimeUtc() != null) {
                    existingPaymentStep.setCreateDateTimeUtc(paymentStep.getCreateDateTimeUtc());
                }
                if (paymentStep.getAction() != null) {
                    existingPaymentStep.setAction(paymentStep.getAction());
                }
                if (paymentStep.getStatus() != null) {
                    existingPaymentStep.setStatus(paymentStep.getStatus());
                }
                if (paymentStep.getAmountToCollect() != null) {
                    existingPaymentStep.setAmountToCollect(paymentStep.getAmountToCollect());
                }

                return existingPaymentStep;
            })
            .map(paymentStepRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentStep.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-steps} : get all the paymentSteps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentSteps in body.
     */
    @GetMapping("/payment-steps")
    public List<PaymentStep> getAllPaymentSteps() {
        log.debug("REST request to get all PaymentSteps");
        return paymentStepRepository.findAll();
    }

    /**
     * {@code GET  /payment-steps/:id} : get the "id" paymentStep.
     *
     * @param id the id of the paymentStep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentStep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-steps/{id}")
    public ResponseEntity<PaymentStep> getPaymentStep(@PathVariable Long id) {
        log.debug("REST request to get PaymentStep : {}", id);
        Optional<PaymentStep> paymentStep = paymentStepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentStep);
    }

    /**
     * {@code DELETE  /payment-steps/:id} : delete the "id" paymentStep.
     *
     * @param id the id of the paymentStep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-steps/{id}")
    public ResponseEntity<Void> deletePaymentStep(@PathVariable Long id) {
        log.debug("REST request to delete PaymentStep : {}", id);
        paymentStepRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
