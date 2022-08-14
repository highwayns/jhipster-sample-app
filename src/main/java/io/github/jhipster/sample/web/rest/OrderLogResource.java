package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.OrderLogRepository;
import io.github.jhipster.sample.service.OrderLogService;
import io.github.jhipster.sample.service.dto.OrderLogDTO;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.OrderLog}.
 */
@RestController
@RequestMapping("/api")
public class OrderLogResource {

    private final Logger log = LoggerFactory.getLogger(OrderLogResource.class);

    private static final String ENTITY_NAME = "orderLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderLogService orderLogService;

    private final OrderLogRepository orderLogRepository;

    public OrderLogResource(OrderLogService orderLogService, OrderLogRepository orderLogRepository) {
        this.orderLogService = orderLogService;
        this.orderLogRepository = orderLogRepository;
    }

    /**
     * {@code POST  /order-logs} : Create a new orderLog.
     *
     * @param orderLogDTO the orderLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderLogDTO, or with status {@code 400 (Bad Request)} if the orderLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-logs")
    public ResponseEntity<OrderLogDTO> createOrderLog(@Valid @RequestBody OrderLogDTO orderLogDTO) throws URISyntaxException {
        log.debug("REST request to save OrderLog : {}", orderLogDTO);
        if (orderLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderLogDTO result = orderLogService.save(orderLogDTO);
        return ResponseEntity
            .created(new URI("/api/order-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-logs/:id} : Updates an existing orderLog.
     *
     * @param id the id of the orderLogDTO to save.
     * @param orderLogDTO the orderLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderLogDTO,
     * or with status {@code 400 (Bad Request)} if the orderLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-logs/{id}")
    public ResponseEntity<OrderLogDTO> updateOrderLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderLogDTO orderLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderLog : {}, {}", id, orderLogDTO);
        if (orderLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderLogDTO result = orderLogService.update(orderLogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-logs/:id} : Partial updates given fields of an existing orderLog, field will ignore if it is null
     *
     * @param id the id of the orderLogDTO to save.
     * @param orderLogDTO the orderLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderLogDTO,
     * or with status {@code 400 (Bad Request)} if the orderLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderLogDTO> partialUpdateOrderLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderLogDTO orderLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderLog partially : {}, {}", id, orderLogDTO);
        if (orderLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderLogDTO> result = orderLogService.partialUpdate(orderLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-logs} : get all the orderLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderLogs in body.
     */
    @GetMapping("/order-logs")
    public ResponseEntity<List<OrderLogDTO>> getAllOrderLogs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderLogs");
        Page<OrderLogDTO> page = orderLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-logs/:id} : get the "id" orderLog.
     *
     * @param id the id of the orderLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-logs/{id}")
    public ResponseEntity<OrderLogDTO> getOrderLog(@PathVariable Long id) {
        log.debug("REST request to get OrderLog : {}", id);
        Optional<OrderLogDTO> orderLogDTO = orderLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderLogDTO);
    }

    /**
     * {@code DELETE  /order-logs/:id} : delete the "id" orderLog.
     *
     * @param id the id of the orderLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-logs/{id}")
    public ResponseEntity<Void> deleteOrderLog(@PathVariable Long id) {
        log.debug("REST request to delete OrderLog : {}", id);
        orderLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
