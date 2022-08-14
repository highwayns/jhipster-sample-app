package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminOrder} entity.
 */
public class AdminOrderDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String orderBy;

    @NotNull
    private Integer orderAsc;

    private AdminControlsDTO controlId;

    private AdminUsersDTO userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getOrderAsc() {
        return orderAsc;
    }

    public void setOrderAsc(Integer orderAsc) {
        this.orderAsc = orderAsc;
    }

    public AdminControlsDTO getControlId() {
        return controlId;
    }

    public void setControlId(AdminControlsDTO controlId) {
        this.controlId = controlId;
    }

    public AdminUsersDTO getUserId() {
        return userId;
    }

    public void setUserId(AdminUsersDTO userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminOrderDTO)) {
            return false;
        }

        AdminOrderDTO adminOrderDTO = (AdminOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminOrderDTO{" +
            "id=" + getId() +
            ", orderBy='" + getOrderBy() + "'" +
            ", orderAsc=" + getOrderAsc() +
            ", controlId=" + getControlId() +
            ", userId=" + getUserId() +
            "}";
    }
}
