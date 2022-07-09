package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.PaymentMethodInfo;
import io.github.jhipster.sample.repository.PaymentMethodInfoRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.PaymentMethodInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentMethodInfoResource {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodInfoResource.class);

    private static final String ENTITY_NAME = "paymentMethodInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentMethodInfoRepository paymentMethodInfoRepository;

    public PaymentMethodInfoResource(PaymentMethodInfoRepository paymentMethodInfoRepository) {
        this.paymentMethodInfoRepository = paymentMethodInfoRepository;
    }

    /**
     * {@code POST  /payment-method-infos} : Create a new paymentMethodInfo.
     *
     * @param paymentMethodInfo the paymentMethodInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentMethodInfo, or with status {@code 400 (Bad Request)} if the paymentMethodInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-method-infos")
    public ResponseEntity<PaymentMethodInfo> createPaymentMethodInfo(@RequestBody PaymentMethodInfo paymentMethodInfo)
        throws URISyntaxException {
        log.debug("REST request to save PaymentMethodInfo : {}", paymentMethodInfo);
        if (paymentMethodInfo.getId() != null) {
            throw new BadRequestAlertException("A new paymentMethodInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentMethodInfo result = paymentMethodInfoRepository.save(paymentMethodInfo);
        return ResponseEntity
            .created(new URI("/api/payment-method-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-method-infos/:id} : Updates an existing paymentMethodInfo.
     *
     * @param id the id of the paymentMethodInfo to save.
     * @param paymentMethodInfo the paymentMethodInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethodInfo,
     * or with status {@code 400 (Bad Request)} if the paymentMethodInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethodInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-method-infos/{id}")
    public ResponseEntity<PaymentMethodInfo> updatePaymentMethodInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentMethodInfo paymentMethodInfo
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentMethodInfo : {}, {}", id, paymentMethodInfo);
        if (paymentMethodInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethodInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentMethodInfo result = paymentMethodInfoRepository.save(paymentMethodInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentMethodInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-method-infos/:id} : Partial updates given fields of an existing paymentMethodInfo, field will ignore if it is null
     *
     * @param id the id of the paymentMethodInfo to save.
     * @param paymentMethodInfo the paymentMethodInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethodInfo,
     * or with status {@code 400 (Bad Request)} if the paymentMethodInfo is not valid,
     * or with status {@code 404 (Not Found)} if the paymentMethodInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethodInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-method-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentMethodInfo> partialUpdatePaymentMethodInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentMethodInfo paymentMethodInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentMethodInfo partially : {}, {}", id, paymentMethodInfo);
        if (paymentMethodInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethodInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentMethodInfo> result = paymentMethodInfoRepository
            .findById(paymentMethodInfo.getId())
            .map(existingPaymentMethodInfo -> {
                if (paymentMethodInfo.getPaymentMethod() != null) {
                    existingPaymentMethodInfo.setPaymentMethod(paymentMethodInfo.getPaymentMethod());
                }
                if (paymentMethodInfo.getLogo() != null) {
                    existingPaymentMethodInfo.setLogo(paymentMethodInfo.getLogo());
                }
                if (paymentMethodInfo.getSupportsTokenisation() != null) {
                    existingPaymentMethodInfo.setSupportsTokenisation(paymentMethodInfo.getSupportsTokenisation());
                }
                if (paymentMethodInfo.getCurrencies() != null) {
                    existingPaymentMethodInfo.setCurrencies(paymentMethodInfo.getCurrencies());
                }
                if (paymentMethodInfo.getSurchargeAmount() != null) {
                    existingPaymentMethodInfo.setSurchargeAmount(paymentMethodInfo.getSurchargeAmount());
                }
                if (paymentMethodInfo.getSurchargeAmountExclVat() != null) {
                    existingPaymentMethodInfo.setSurchargeAmountExclVat(paymentMethodInfo.getSurchargeAmountExclVat());
                }
                if (paymentMethodInfo.getSurchargeAmountVat() != null) {
                    existingPaymentMethodInfo.setSurchargeAmountVat(paymentMethodInfo.getSurchargeAmountVat());
                }
                if (paymentMethodInfo.getSurchargeVatPercentage() != null) {
                    existingPaymentMethodInfo.setSurchargeVatPercentage(paymentMethodInfo.getSurchargeVatPercentage());
                }
                if (paymentMethodInfo.getDescription() != null) {
                    existingPaymentMethodInfo.setDescription(paymentMethodInfo.getDescription());
                }

                return existingPaymentMethodInfo;
            })
            .map(paymentMethodInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentMethodInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-method-infos} : get all the paymentMethodInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentMethodInfos in body.
     */
    @GetMapping("/payment-method-infos")
    public List<PaymentMethodInfo> getAllPaymentMethodInfos() {
        log.debug("REST request to get all PaymentMethodInfos");
        return paymentMethodInfoRepository.findAll();
    }

    /**
     * {@code GET  /payment-method-infos/:id} : get the "id" paymentMethodInfo.
     *
     * @param id the id of the paymentMethodInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentMethodInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-method-infos/{id}")
    public ResponseEntity<PaymentMethodInfo> getPaymentMethodInfo(@PathVariable Long id) {
        log.debug("REST request to get PaymentMethodInfo : {}", id);
        Optional<PaymentMethodInfo> paymentMethodInfo = paymentMethodInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentMethodInfo);
    }

    /**
     * {@code DELETE  /payment-method-infos/:id} : delete the "id" paymentMethodInfo.
     *
     * @param id the id of the paymentMethodInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-method-infos/{id}")
    public ResponseEntity<Void> deletePaymentMethodInfo(@PathVariable Long id) {
        log.debug("REST request to delete PaymentMethodInfo : {}", id);
        paymentMethodInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
