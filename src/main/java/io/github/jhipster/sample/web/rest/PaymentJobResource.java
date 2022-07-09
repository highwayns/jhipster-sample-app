package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.PaymentJob;
import io.github.jhipster.sample.repository.PaymentJobRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.PaymentJob}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentJobResource {

    private final Logger log = LoggerFactory.getLogger(PaymentJobResource.class);

    private static final String ENTITY_NAME = "paymentJob";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentJobRepository paymentJobRepository;

    public PaymentJobResource(PaymentJobRepository paymentJobRepository) {
        this.paymentJobRepository = paymentJobRepository;
    }

    /**
     * {@code POST  /payment-jobs} : Create a new paymentJob.
     *
     * @param paymentJob the paymentJob to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentJob, or with status {@code 400 (Bad Request)} if the paymentJob has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-jobs")
    public ResponseEntity<PaymentJob> createPaymentJob(@RequestBody PaymentJob paymentJob) throws URISyntaxException {
        log.debug("REST request to save PaymentJob : {}", paymentJob);
        if (paymentJob.getId() != null) {
            throw new BadRequestAlertException("A new paymentJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentJob result = paymentJobRepository.save(paymentJob);
        return ResponseEntity
            .created(new URI("/api/payment-jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-jobs/:id} : Updates an existing paymentJob.
     *
     * @param id the id of the paymentJob to save.
     * @param paymentJob the paymentJob to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentJob,
     * or with status {@code 400 (Bad Request)} if the paymentJob is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentJob couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-jobs/{id}")
    public ResponseEntity<PaymentJob> updatePaymentJob(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentJob paymentJob
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentJob : {}, {}", id, paymentJob);
        if (paymentJob.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentJob.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentJob result = paymentJobRepository.save(paymentJob);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentJob.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-jobs/:id} : Partial updates given fields of an existing paymentJob, field will ignore if it is null
     *
     * @param id the id of the paymentJob to save.
     * @param paymentJob the paymentJob to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentJob,
     * or with status {@code 400 (Bad Request)} if the paymentJob is not valid,
     * or with status {@code 404 (Not Found)} if the paymentJob is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentJob couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-jobs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentJob> partialUpdatePaymentJob(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentJob paymentJob
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentJob partially : {}, {}", id, paymentJob);
        if (paymentJob.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentJob.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentJob> result = paymentJobRepository
            .findById(paymentJob.getId())
            .map(existingPaymentJob -> {
                if (paymentJob.getReference() != null) {
                    existingPaymentJob.setReference(paymentJob.getReference());
                }
                if (paymentJob.getCreateDateTimeUtc() != null) {
                    existingPaymentJob.setCreateDateTimeUtc(paymentJob.getCreateDateTimeUtc());
                }
                if (paymentJob.getType() != null) {
                    existingPaymentJob.setType(paymentJob.getType());
                }
                if (paymentJob.getTraceReference() != null) {
                    existingPaymentJob.setTraceReference(paymentJob.getTraceReference());
                }
                if (paymentJob.getConfigurationId() != null) {
                    existingPaymentJob.setConfigurationId(paymentJob.getConfigurationId());
                }
                if (paymentJob.getDomain() != null) {
                    existingPaymentJob.setDomain(paymentJob.getDomain());
                }
                if (paymentJob.getLocale() != null) {
                    existingPaymentJob.setLocale(paymentJob.getLocale());
                }
                if (paymentJob.getTimeZone() != null) {
                    existingPaymentJob.setTimeZone(paymentJob.getTimeZone());
                }
                if (paymentJob.getParentPaymentJobReference() != null) {
                    existingPaymentJob.setParentPaymentJobReference(paymentJob.getParentPaymentJobReference());
                }
                if (paymentJob.getDisplayUrl() != null) {
                    existingPaymentJob.setDisplayUrl(paymentJob.getDisplayUrl());
                }
                if (paymentJob.getCurrency() != null) {
                    existingPaymentJob.setCurrency(paymentJob.getCurrency());
                }
                if (paymentJob.getAmountToCollect() != null) {
                    existingPaymentJob.setAmountToCollect(paymentJob.getAmountToCollect());
                }
                if (paymentJob.getAmountCollected() != null) {
                    existingPaymentJob.setAmountCollected(paymentJob.getAmountCollected());
                }
                if (paymentJob.getPaidAmount() != null) {
                    existingPaymentJob.setPaidAmount(paymentJob.getPaidAmount());
                }
                if (paymentJob.getPaidDateTimeUtc() != null) {
                    existingPaymentJob.setPaidDateTimeUtc(paymentJob.getPaidDateTimeUtc());
                }
                if (paymentJob.getExpirationDateTimeUtc() != null) {
                    existingPaymentJob.setExpirationDateTimeUtc(paymentJob.getExpirationDateTimeUtc());
                }
                if (paymentJob.getDueDateTimeUtc() != null) {
                    existingPaymentJob.setDueDateTimeUtc(paymentJob.getDueDateTimeUtc());
                }
                if (paymentJob.getLastUpdateTimeUtc() != null) {
                    existingPaymentJob.setLastUpdateTimeUtc(paymentJob.getLastUpdateTimeUtc());
                }
                if (paymentJob.getLastProcessedTimeUtc() != null) {
                    existingPaymentJob.setLastProcessedTimeUtc(paymentJob.getLastProcessedTimeUtc());
                }

                return existingPaymentJob;
            })
            .map(paymentJobRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentJob.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-jobs} : get all the paymentJobs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentJobs in body.
     */
    @GetMapping("/payment-jobs")
    public List<PaymentJob> getAllPaymentJobs() {
        log.debug("REST request to get all PaymentJobs");
        return paymentJobRepository.findAll();
    }

    /**
     * {@code GET  /payment-jobs/:id} : get the "id" paymentJob.
     *
     * @param id the id of the paymentJob to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentJob, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-jobs/{id}")
    public ResponseEntity<PaymentJob> getPaymentJob(@PathVariable Long id) {
        log.debug("REST request to get PaymentJob : {}", id);
        Optional<PaymentJob> paymentJob = paymentJobRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentJob);
    }

    /**
     * {@code DELETE  /payment-jobs/:id} : delete the "id" paymentJob.
     *
     * @param id the id of the paymentJob to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-jobs/{id}")
    public ResponseEntity<Void> deletePaymentJob(@PathVariable Long id) {
        log.debug("REST request to delete PaymentJob : {}", id);
        paymentJobRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
