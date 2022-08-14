package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.BankAccounts} entity.
 */
public class BankAccountsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long accountNumber;

    @NotNull
    @Size(max = 255)
    private String description;

    private SiteUsersDTO siteUser;

    private CurrenciesDTO currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof BankAccountsDTO)) {
            return false;
        }

        BankAccountsDTO bankAccountsDTO = (BankAccountsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankAccountsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccountsDTO{" +
            "id=" + getId() +
            ", accountNumber=" + getAccountNumber() +
            ", description='" + getDescription() + "'" +
            ", siteUser=" + getSiteUser() +
            ", currency=" + getCurrency() +
            "}";
    }
}
