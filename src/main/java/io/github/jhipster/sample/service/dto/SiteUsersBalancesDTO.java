package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.SiteUsersBalances} entity.
 */
public class SiteUsersBalancesDTO implements Serializable {

    private Long id;

    @NotNull
    private Double balance;

    private SiteUsersDTO siteUser;

    private CurrenciesDTO currency;

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

    public SiteUsersDTO getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUsersDTO siteUser) {
        this.siteUser = siteUser;
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
        if (!(o instanceof SiteUsersBalancesDTO)) {
            return false;
        }

        SiteUsersBalancesDTO siteUsersBalancesDTO = (SiteUsersBalancesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, siteUsersBalancesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUsersBalancesDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", siteUser=" + getSiteUser() +
            ", currency=" + getCurrency() +
            "}";
    }
}
