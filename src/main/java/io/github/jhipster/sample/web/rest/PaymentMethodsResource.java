package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.PaymentMethods;
import io.github.jhipster.sample.repository.PaymentMethodsRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.PaymentMethods}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentMethodsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodsResource.class);

    private static final String ENTITY_NAME = "paymentMethods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentMethodsRepository paymentMethodsRepository;

    public PaymentMethodsResource(PaymentMethodsRepository paymentMethodsRepository) {
        this.paymentMethodsRepository = paymentMethodsRepository;
    }

    /**
     * {@code POST  /payment-methods} : Create a new paymentMethods.
     *
     * @param paymentMethods the paymentMethods to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentMethods, or with status {@code 400 (Bad Request)} if the paymentMethods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-methods")
    public ResponseEntity<PaymentMethods> createPaymentMethods(@RequestBody PaymentMethods paymentMethods) throws URISyntaxException {
        log.debug("REST request to save PaymentMethods : {}", paymentMethods);
        if (paymentMethods.getId() != null) {
            throw new BadRequestAlertException("A new paymentMethods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentMethods result = paymentMethodsRepository.save(paymentMethods);
        return ResponseEntity
            .created(new URI("/api/payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-methods/:id} : Updates an existing paymentMethods.
     *
     * @param id the id of the paymentMethods to save.
     * @param paymentMethods the paymentMethods to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethods,
     * or with status {@code 400 (Bad Request)} if the paymentMethods is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethods couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-methods/{id}")
    public ResponseEntity<PaymentMethods> updatePaymentMethods(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentMethods paymentMethods
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentMethods : {}, {}", id, paymentMethods);
        if (paymentMethods.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethods.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentMethods result = paymentMethodsRepository.save(paymentMethods);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentMethods.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-methods/:id} : Partial updates given fields of an existing paymentMethods, field will ignore if it is null
     *
     * @param id the id of the paymentMethods to save.
     * @param paymentMethods the paymentMethods to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethods,
     * or with status {@code 400 (Bad Request)} if the paymentMethods is not valid,
     * or with status {@code 404 (Not Found)} if the paymentMethods is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethods couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-methods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentMethods> partialUpdatePaymentMethods(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentMethods paymentMethods
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentMethods partially : {}, {}", id, paymentMethods);
        if (paymentMethods.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethods.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentMethods> result = paymentMethodsRepository
            .findById(paymentMethods.getId())
            .map(existingPaymentMethods -> {
                if (paymentMethods.getPaymentMethod() != null) {
                    existingPaymentMethods.setPaymentMethod(paymentMethods.getPaymentMethod());
                }

                return existingPaymentMethods;
            })
            .map(paymentMethodsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentMethods.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-methods} : get all the paymentMethods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentMethods in body.
     */
    @GetMapping("/payment-methods")
    public List<PaymentMethods> getAllPaymentMethods() {
        log.debug("REST request to get all PaymentMethods");
        return paymentMethodsRepository.findAll();
    }

    /**
     * {@code GET  /payment-methods/:id} : get the "id" paymentMethods.
     *
     * @param id the id of the paymentMethods to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentMethods, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-methods/{id}")
    public ResponseEntity<PaymentMethods> getPaymentMethods(@PathVariable Long id) {
        log.debug("REST request to get PaymentMethods : {}", id);
        Optional<PaymentMethods> paymentMethods = paymentMethodsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentMethods);
    }

    /**
     * {@code DELETE  /payment-methods/:id} : delete the "id" paymentMethods.
     *
     * @param id the id of the paymentMethods to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-methods/{id}")
    public ResponseEntity<Void> deletePaymentMethods(@PathVariable Long id) {
        log.debug("REST request to delete PaymentMethods : {}", id);
        paymentMethodsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
