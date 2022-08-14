package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminOrder.
 */
@Entity
@Table(name = "admin_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "order_by", length = 50, nullable = false)
    private String orderBy;

    @NotNull
    @Column(name = "order_asc", nullable = false)
    private Integer orderAsc;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pageId", "tabId", "adminControlsMethods", "adminCrons", "adminOrders" }, allowSetters = true)
    private AdminControls controlId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "countryId", "adminOrders" }, allowSetters = true)
    private AdminUsers userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public AdminOrder orderBy(String orderBy) {
        this.setOrderBy(orderBy);
        return this;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getOrderAsc() {
        return this.orderAsc;
    }

    public AdminOrder orderAsc(Integer orderAsc) {
        this.setOrderAsc(orderAsc);
        return this;
    }

    public void setOrderAsc(Integer orderAsc) {
        this.orderAsc = orderAsc;
    }

    public AdminControls getControlId() {
        return this.controlId;
    }

    public void setControlId(AdminControls adminControls) {
        this.controlId = adminControls;
    }

    public AdminOrder controlId(AdminControls adminControls) {
        this.setControlId(adminControls);
        return this;
    }

    public AdminUsers getUserId() {
        return this.userId;
    }

    public void setUserId(AdminUsers adminUsers) {
        this.userId = adminUsers;
    }

    public AdminOrder userId(AdminUsers adminUsers) {
        this.setUserId(adminUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminOrder)) {
            return false;
        }
        return id != null && id.equals(((AdminOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminOrder{" +
            "id=" + getId() +
            ", orderBy='" + getOrderBy() + "'" +
            ", orderAsc=" + getOrderAsc() +
            "}";
    }
}
