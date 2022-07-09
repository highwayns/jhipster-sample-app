package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.PaymentAttributes;
import io.github.jhipster.sample.repository.PaymentAttributesRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.PaymentAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentAttributesResource {

    private final Logger log = LoggerFactory.getLogger(PaymentAttributesResource.class);

    private static final String ENTITY_NAME = "paymentAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentAttributesRepository paymentAttributesRepository;

    public PaymentAttributesResource(PaymentAttributesRepository paymentAttributesRepository) {
        this.paymentAttributesRepository = paymentAttributesRepository;
    }

    /**
     * {@code POST  /payment-attributes} : Create a new paymentAttributes.
     *
     * @param paymentAttributes the paymentAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentAttributes, or with status {@code 400 (Bad Request)} if the paymentAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-attributes")
    public ResponseEntity<PaymentAttributes> createPaymentAttributes(@RequestBody PaymentAttributes paymentAttributes)
        throws URISyntaxException {
        log.debug("REST request to save PaymentAttributes : {}", paymentAttributes);
        if (paymentAttributes.getId() != null) {
            throw new BadRequestAlertException("A new paymentAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentAttributes result = paymentAttributesRepository.save(paymentAttributes);
        return ResponseEntity
            .created(new URI("/api/payment-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-attributes/:id} : Updates an existing paymentAttributes.
     *
     * @param id the id of the paymentAttributes to save.
     * @param paymentAttributes the paymentAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAttributes,
     * or with status {@code 400 (Bad Request)} if the paymentAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-attributes/{id}")
    public ResponseEntity<PaymentAttributes> updatePaymentAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentAttributes paymentAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentAttributes : {}, {}", id, paymentAttributes);
        if (paymentAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentAttributes result = paymentAttributesRepository.save(paymentAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-attributes/:id} : Partial updates given fields of an existing paymentAttributes, field will ignore if it is null
     *
     * @param id the id of the paymentAttributes to save.
     * @param paymentAttributes the paymentAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAttributes,
     * or with status {@code 400 (Bad Request)} if the paymentAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the paymentAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentAttributes> partialUpdatePaymentAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentAttributes paymentAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentAttributes partially : {}, {}", id, paymentAttributes);
        if (paymentAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentAttributes> result = paymentAttributesRepository
            .findById(paymentAttributes.getId())
            .map(existingPaymentAttributes -> {
                if (paymentAttributes.getOriginatingIpAddress() != null) {
                    existingPaymentAttributes.setOriginatingIpAddress(paymentAttributes.getOriginatingIpAddress());
                }
                if (paymentAttributes.getOriginHeader() != null) {
                    existingPaymentAttributes.setOriginHeader(paymentAttributes.getOriginHeader());
                }
                if (paymentAttributes.getUserAgent() != null) {
                    existingPaymentAttributes.setUserAgent(paymentAttributes.getUserAgent());
                }
                if (paymentAttributes.getReturnUrlSuccess() != null) {
                    existingPaymentAttributes.setReturnUrlSuccess(paymentAttributes.getReturnUrlSuccess());
                }
                if (paymentAttributes.getReturnUrlFailed() != null) {
                    existingPaymentAttributes.setReturnUrlFailed(paymentAttributes.getReturnUrlFailed());
                }
                if (paymentAttributes.getReturnUrlCancelled() != null) {
                    existingPaymentAttributes.setReturnUrlCancelled(paymentAttributes.getReturnUrlCancelled());
                }
                if (paymentAttributes.getSimulatedStatus() != null) {
                    existingPaymentAttributes.setSimulatedStatus(paymentAttributes.getSimulatedStatus());
                }
                if (paymentAttributes.getIdealBic() != null) {
                    existingPaymentAttributes.setIdealBic(paymentAttributes.getIdealBic());
                }
                if (paymentAttributes.getPaymentMethodTransactionId() != null) {
                    existingPaymentAttributes.setPaymentMethodTransactionId(paymentAttributes.getPaymentMethodTransactionId());
                }
                if (paymentAttributes.getPaymentMethodVoidTransactionId() != null) {
                    existingPaymentAttributes.setPaymentMethodVoidTransactionId(paymentAttributes.getPaymentMethodVoidTransactionId());
                }
                if (paymentAttributes.getToken() != null) {
                    existingPaymentAttributes.setToken(paymentAttributes.getToken());
                }
                if (paymentAttributes.getCashFlowsAcquiringDetails() != null) {
                    existingPaymentAttributes.setCashFlowsAcquiringDetails(paymentAttributes.getCashFlowsAcquiringDetails());
                }
                if (paymentAttributes.getDescriptor() != null) {
                    existingPaymentAttributes.setDescriptor(paymentAttributes.getDescriptor());
                }
                if (paymentAttributes.getEwalletType() != null) {
                    existingPaymentAttributes.setEwalletType(paymentAttributes.getEwalletType());
                }
                if (paymentAttributes.getPaymentStatus() != null) {
                    existingPaymentAttributes.setPaymentStatus(paymentAttributes.getPaymentStatus());
                }

                return existingPaymentAttributes;
            })
            .map(paymentAttributesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-attributes} : get all the paymentAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentAttributes in body.
     */
    @GetMapping("/payment-attributes")
    public List<PaymentAttributes> getAllPaymentAttributes() {
        log.debug("REST request to get all PaymentAttributes");
        return paymentAttributesRepository.findAll();
    }

    /**
     * {@code GET  /payment-attributes/:id} : get the "id" paymentAttributes.
     *
     * @param id the id of the paymentAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-attributes/{id}")
    public ResponseEntity<PaymentAttributes> getPaymentAttributes(@PathVariable Long id) {
        log.debug("REST request to get PaymentAttributes : {}", id);
        Optional<PaymentAttributes> paymentAttributes = paymentAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentAttributes);
    }

    /**
     * {@code DELETE  /payment-attributes/:id} : delete the "id" paymentAttributes.
     *
     * @param id the id of the paymentAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-attributes/{id}")
    public ResponseEntity<Void> deletePaymentAttributes(@PathVariable Long id) {
        log.debug("REST request to delete PaymentAttributes : {}", id);
        paymentAttributesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
