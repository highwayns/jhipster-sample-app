package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.HistoricalData} entity.
 */
public class HistoricalDataDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private BigDecimal usd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoricalDataDTO)) {
            return false;
        }

        HistoricalDataDTO historicalDataDTO = (HistoricalDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, historicalDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoricalDataDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", usd=" + getUsd() +
            "}";
    }
}
