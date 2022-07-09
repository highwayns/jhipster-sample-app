package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.PaymentJobAttributes;
import io.github.jhipster.sample.repository.PaymentJobAttributesRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.PaymentJobAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentJobAttributesResource {

    private final Logger log = LoggerFactory.getLogger(PaymentJobAttributesResource.class);

    private static final String ENTITY_NAME = "paymentJobAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentJobAttributesRepository paymentJobAttributesRepository;

    public PaymentJobAttributesResource(PaymentJobAttributesRepository paymentJobAttributesRepository) {
        this.paymentJobAttributesRepository = paymentJobAttributesRepository;
    }

    /**
     * {@code POST  /payment-job-attributes} : Create a new paymentJobAttributes.
     *
     * @param paymentJobAttributes the paymentJobAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentJobAttributes, or with status {@code 400 (Bad Request)} if the paymentJobAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-job-attributes")
    public ResponseEntity<PaymentJobAttributes> createPaymentJobAttributes(@RequestBody PaymentJobAttributes paymentJobAttributes)
        throws URISyntaxException {
        log.debug("REST request to save PaymentJobAttributes : {}", paymentJobAttributes);
        if (paymentJobAttributes.getId() != null) {
            throw new BadRequestAlertException("A new paymentJobAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentJobAttributes result = paymentJobAttributesRepository.save(paymentJobAttributes);
        return ResponseEntity
            .created(new URI("/api/payment-job-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-job-attributes/:id} : Updates an existing paymentJobAttributes.
     *
     * @param id the id of the paymentJobAttributes to save.
     * @param paymentJobAttributes the paymentJobAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentJobAttributes,
     * or with status {@code 400 (Bad Request)} if the paymentJobAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentJobAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-job-attributes/{id}")
    public ResponseEntity<PaymentJobAttributes> updatePaymentJobAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentJobAttributes paymentJobAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentJobAttributes : {}, {}", id, paymentJobAttributes);
        if (paymentJobAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentJobAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentJobAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentJobAttributes result = paymentJobAttributesRepository.save(paymentJobAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentJobAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-job-attributes/:id} : Partial updates given fields of an existing paymentJobAttributes, field will ignore if it is null
     *
     * @param id the id of the paymentJobAttributes to save.
     * @param paymentJobAttributes the paymentJobAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentJobAttributes,
     * or with status {@code 400 (Bad Request)} if the paymentJobAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the paymentJobAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentJobAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-job-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentJobAttributes> partialUpdatePaymentJobAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentJobAttributes paymentJobAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentJobAttributes partially : {}, {}", id, paymentJobAttributes);
        if (paymentJobAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentJobAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentJobAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentJobAttributes> result = paymentJobAttributesRepository
            .findById(paymentJobAttributes.getId())
            .map(existingPaymentJobAttributes -> {
                if (paymentJobAttributes.getWebhookUrl() != null) {
                    existingPaymentJobAttributes.setWebhookUrl(paymentJobAttributes.getWebhookUrl());
                }
                if (paymentJobAttributes.getGoogleAnalyticsClientId() != null) {
                    existingPaymentJobAttributes.setGoogleAnalyticsClientId(paymentJobAttributes.getGoogleAnalyticsClientId());
                }
                if (paymentJobAttributes.getAllowedParentFrameDomains() != null) {
                    existingPaymentJobAttributes.setAllowedParentFrameDomains(paymentJobAttributes.getAllowedParentFrameDomains());
                }
                if (paymentJobAttributes.getPaymentPageReference() != null) {
                    existingPaymentJobAttributes.setPaymentPageReference(paymentJobAttributes.getPaymentPageReference());
                }

                return existingPaymentJobAttributes;
            })
            .map(paymentJobAttributesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentJobAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-job-attributes} : get all the paymentJobAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentJobAttributes in body.
     */
    @GetMapping("/payment-job-attributes")
    public List<PaymentJobAttributes> getAllPaymentJobAttributes() {
        log.debug("REST request to get all PaymentJobAttributes");
        return paymentJobAttributesRepository.findAll();
    }

    /**
     * {@code GET  /payment-job-attributes/:id} : get the "id" paymentJobAttributes.
     *
     * @param id the id of the paymentJobAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentJobAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-job-attributes/{id}")
    public ResponseEntity<PaymentJobAttributes> getPaymentJobAttributes(@PathVariable Long id) {
        log.debug("REST request to get PaymentJobAttributes : {}", id);
        Optional<PaymentJobAttributes> paymentJobAttributes = paymentJobAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentJobAttributes);
    }

    /**
     * {@code DELETE  /payment-job-attributes/:id} : delete the "id" paymentJobAttributes.
     *
     * @param id the id of the paymentJobAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-job-attributes/{id}")
    public ResponseEntity<Void> deletePaymentJobAttributes(@PathVariable Long id) {
        log.debug("REST request to delete PaymentJobAttributes : {}", id);
        paymentJobAttributesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
