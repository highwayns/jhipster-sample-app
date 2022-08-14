package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.BitcoindLog} entity.
 */
public class BitcoindLogDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String transactionId;

    @NotNull
    private Double amount;

    @NotNull
    private Instant date;

    private SiteUsersDTO siteUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public SiteUsersDTO getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUsersDTO siteUser) {
        this.siteUser = siteUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BitcoindLogDTO)) {
            return false;
        }

        BitcoindLogDTO bitcoindLogDTO = (BitcoindLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bitcoindLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BitcoindLogDTO{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", amount=" + getAmount() +
            ", date='" + getDate() + "'" +
            ", siteUser=" + getSiteUser() +
            "}";
    }
}
