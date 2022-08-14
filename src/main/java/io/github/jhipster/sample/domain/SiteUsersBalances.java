package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteUsersBalances.
 */
@Entity
@Table(name = "site_users_balances")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteUsersBalances implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Double balance;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SiteUsersBalances id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return this.balance;
    }

    public SiteUsersBalances balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public SiteUsersBalances siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    public Currencies getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currencies currencies) {
        this.currency = currencies;
    }

    public SiteUsersBalances currency(Currencies currencies) {
        this.setCurrency(currencies);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteUsersBalances)) {
            return false;
        }
        return id != null && id.equals(((SiteUsersBalances) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUsersBalances{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            "}";
    }
}
