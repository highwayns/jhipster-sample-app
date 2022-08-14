package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.StatusEscrows} entity.
 */
public class StatusEscrowsDTO implements Serializable {

    private Long id;

    @NotNull
    private Double balance;

    private CurrenciesDTO currency;

    private StatusDTO statusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public CurrenciesDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrenciesDTO currency) {
        this.currency = currency;
    }

    public StatusDTO getStatusId() {
        return statusId;
    }

    public void setStatusId(StatusDTO statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusEscrowsDTO)) {
            return false;
        }

        StatusEscrowsDTO statusEscrowsDTO = (StatusEscrowsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statusEscrowsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusEscrowsDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", currency=" + getCurrency() +
            ", statusId=" + getStatusId() +
            "}";
    }
}
