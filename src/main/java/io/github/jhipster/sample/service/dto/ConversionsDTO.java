package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Conversions} entity.
 */
public class ConversionsDTO implements Serializable {

    private Long id;

    @NotNull
    private Double amount;

    @NotNull
    private Instant date;

    @NotNull
    private YesNo isActive;

    @NotNull
    private Double totalWithdrawals;

    @NotNull
    private Double profitToFactor;

    @NotNull
    private YesNo factored;

    @NotNull
    private Instant date1;

    private CurrenciesDTO currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public YesNo getIsActive() {
        return isActive;
    }

    public void setIsActive(YesNo isActive) {
        this.isActive = isActive;
    }

    public Double getTotalWithdrawals() {
        return totalWithdrawals;
    }

    public void setTotalWithdrawals(Double totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }

    public Double getProfitToFactor() {
        return profitToFactor;
    }

    public void setProfitToFactor(Double profitToFactor) {
        this.profitToFactor = profitToFactor;
    }

    public YesNo getFactored() {
        return factored;
    }

    public void setFactored(YesNo factored) {
        this.factored = factored;
    }

    public Instant getDate1() {
        return date1;
    }

    public void setDate1(Instant date1) {
        this.date1 = date1;
    }

    public CurrenciesDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrenciesDTO currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConversionsDTO)) {
            return false;
        }

        ConversionsDTO conversionsDTO = (ConversionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conversionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConversionsDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", date='" + getDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", totalWithdrawals=" + getTotalWithdrawals() +
            ", profitToFactor=" + getProfitToFactor() +
            ", factored='" + getFactored() + "'" +
            ", date1='" + getDate1() + "'" +
            ", currency=" + getCurrency() +
            "}";
    }
}
