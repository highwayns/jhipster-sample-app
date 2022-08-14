package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminImageSizes} entity.
 */
public class AdminImageSizesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String fieldName;

    @NotNull
    @Size(max = 255)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminImageSizesDTO)) {
            return false;
        }

        AdminImageSizesDTO adminImageSizesDTO = (AdminImageSizesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminImageSizesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminImageSizesDTO{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
