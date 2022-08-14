package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Fees} entity.
 */
public class FeesDTO implements Serializable {

    private Long id;

    @NotNull
    private Double fee;

    @NotNull
    private Instant date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeesDTO)) {
            return false;
        }

        FeesDTO feesDTO = (FeesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, feesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeesDTO{" +
            "id=" + getId() +
            ", fee=" + getFee() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
