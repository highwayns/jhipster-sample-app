package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminControlsMethods} entity.
 */
public class AdminControlsMethodsDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String method;

    @NotNull
    @Size(max = 255)
    private String argument;

    @NotNull
    private Integer order;

    @NotNull
    private Integer pId;

    private AdminControlsDTO controlId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public AdminControlsDTO getControlId() {
        return controlId;
    }

    public void setControlId(AdminControlsDTO controlId) {
        this.controlId = controlId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminControlsMethodsDTO)) {
            return false;
        }

        AdminControlsMethodsDTO adminControlsMethodsDTO = (AdminControlsMethodsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminControlsMethodsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminControlsMethodsDTO{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            ", argument='" + getArgument() + "'" +
            ", order=" + getOrder() +
            ", pId=" + getpId() +
            ", controlId=" + getControlId() +
            "}";
    }
}
