package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.OrderTypesRepository;
import io.github.jhipster.sample.service.OrderTypesService;
import io.github.jhipster.sample.service.dto.OrderTypesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.OrderTypes}.
 */
@RestController
@RequestMapping("/api")
public class OrderTypesResource {

    private final Logger log = LoggerFactory.getLogger(OrderTypesResource.class);

    private static final String ENTITY_NAME = "orderTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTypesService orderTypesService;

    private final OrderTypesRepository orderTypesRepository;

    public OrderTypesResource(OrderTypesService orderTypesService, OrderTypesRepository orderTypesRepository) {
        this.orderTypesService = orderTypesService;
        this.orderTypesRepository = orderTypesRepository;
    }

    /**
     * {@code POST  /order-types} : Create a new orderTypes.
     *
     * @param orderTypesDTO the orderTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTypesDTO, or with status {@code 400 (Bad Request)} if the orderTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-types")
    public ResponseEntity<OrderTypesDTO> createOrderTypes(@Valid @RequestBody OrderTypesDTO orderTypesDTO) throws URISyntaxException {
        log.debug("REST request to save OrderTypes : {}", orderTypesDTO);
        if (orderTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTypesDTO result = orderTypesService.save(orderTypesDTO);
        return ResponseEntity
            .created(new URI("/api/order-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-types/:id} : Updates an existing orderTypes.
     *
     * @param id the id of the orderTypesDTO to save.
     * @param orderTypesDTO the orderTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTypesDTO,
     * or with status {@code 400 (Bad Request)} if the orderTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-types/{id}")
    public ResponseEntity<OrderTypesDTO> updateOrderTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderTypesDTO orderTypesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderTypes : {}, {}", id, orderTypesDTO);
        if (orderTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderTypesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderTypesDTO result = orderTypesService.update(orderTypesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-types/:id} : Partial updates given fields of an existing orderTypes, field will ignore if it is null
     *
     * @param id the id of the orderTypesDTO to save.
     * @param orderTypesDTO the orderTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTypesDTO,
     * or with status {@code 400 (Bad Request)} if the orderTypesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderTypesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderTypesDTO> partialUpdateOrderTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderTypesDTO orderTypesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderTypes partially : {}, {}", id, orderTypesDTO);
        if (orderTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderTypesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderTypesDTO> result = orderTypesService.partialUpdate(orderTypesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTypesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-types} : get all the orderTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTypes in body.
     */
    @GetMapping("/order-types")
    public ResponseEntity<List<OrderTypesDTO>> getAllOrderTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderTypes");
        Page<OrderTypesDTO> page = orderTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-types/:id} : get the "id" orderTypes.
     *
     * @param id the id of the orderTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-types/{id}")
    public ResponseEntity<OrderTypesDTO> getOrderTypes(@PathVariable Long id) {
        log.debug("REST request to get OrderTypes : {}", id);
        Optional<OrderTypesDTO> orderTypesDTO = orderTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderTypesDTO);
    }

    /**
     * {@code DELETE  /order-types/:id} : delete the "id" orderTypes.
     *
     * @param id the id of the orderTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-types/{id}")
    public ResponseEntity<Void> deleteOrderTypes(@PathVariable Long id) {
        log.debug("REST request to delete OrderTypes : {}", id);
        orderTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
