package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminOrderRepository;
import io.github.jhipster.sample.service.AdminOrderService;
import io.github.jhipster.sample.service.dto.AdminOrderDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminOrder}.
 */
@RestController
@RequestMapping("/api")
public class AdminOrderResource {

    private final Logger log = LoggerFactory.getLogger(AdminOrderResource.class);

    private static final String ENTITY_NAME = "adminOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminOrderService adminOrderService;

    private final AdminOrderRepository adminOrderRepository;

    public AdminOrderResource(AdminOrderService adminOrderService, AdminOrderRepository adminOrderRepository) {
        this.adminOrderService = adminOrderService;
        this.adminOrderRepository = adminOrderRepository;
    }

    /**
     * {@code POST  /admin-orders} : Create a new adminOrder.
     *
     * @param adminOrderDTO the adminOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminOrderDTO, or with status {@code 400 (Bad Request)} if the adminOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-orders")
    public ResponseEntity<AdminOrderDTO> createAdminOrder(@Valid @RequestBody AdminOrderDTO adminOrderDTO) throws URISyntaxException {
        log.debug("REST request to save AdminOrder : {}", adminOrderDTO);
        if (adminOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminOrderDTO result = adminOrderService.save(adminOrderDTO);
        return ResponseEntity
            .created(new URI("/api/admin-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-orders/:id} : Updates an existing adminOrder.
     *
     * @param id the id of the adminOrderDTO to save.
     * @param adminOrderDTO the adminOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminOrderDTO,
     * or with status {@code 400 (Bad Request)} if the adminOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-orders/{id}")
    public ResponseEntity<AdminOrderDTO> updateAdminOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminOrderDTO adminOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminOrder : {}, {}", id, adminOrderDTO);
        if (adminOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminOrderDTO result = adminOrderService.update(adminOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-orders/:id} : Partial updates given fields of an existing adminOrder, field will ignore if it is null
     *
     * @param id the id of the adminOrderDTO to save.
     * @param adminOrderDTO the adminOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminOrderDTO,
     * or with status {@code 400 (Bad Request)} if the adminOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminOrderDTO> partialUpdateAdminOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminOrderDTO adminOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminOrder partially : {}, {}", id, adminOrderDTO);
        if (adminOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminOrderDTO> result = adminOrderService.partialUpdate(adminOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-orders} : get all the adminOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminOrders in body.
     */
    @GetMapping("/admin-orders")
    public ResponseEntity<List<AdminOrderDTO>> getAllAdminOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminOrders");
        Page<AdminOrderDTO> page = adminOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-orders/:id} : get the "id" adminOrder.
     *
     * @param id the id of the adminOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-orders/{id}")
    public ResponseEntity<AdminOrderDTO> getAdminOrder(@PathVariable Long id) {
        log.debug("REST request to get AdminOrder : {}", id);
        Optional<AdminOrderDTO> adminOrderDTO = adminOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminOrderDTO);
    }

    /**
     * {@code DELETE  /admin-orders/:id} : delete the "id" adminOrder.
     *
     * @param id the id of the adminOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-orders/{id}")
    public ResponseEntity<Void> deleteAdminOrder(@PathVariable Long id) {
        log.debug("REST request to delete AdminOrder : {}", id);
        adminOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
