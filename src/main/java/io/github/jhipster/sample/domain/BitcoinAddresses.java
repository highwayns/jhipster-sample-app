package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BitcoinAddresses.
 */
@Entity
@Table(name = "bitcoin_addresses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BitcoinAddresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "system_address", nullable = false)
    private YesNo systemAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hot_wallet", nullable = false)
    private YesNo hotWallet;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "warm_wallet", nullable = false)
    private YesNo warmWallet;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BitcoinAddresses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public BitcoinAddresses address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getDate() {
        return this.date;
    }

    public BitcoinAddresses date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public YesNo getSystemAddress() {
        return this.systemAddress;
    }

    public BitcoinAddresses systemAddress(YesNo systemAddress) {
        this.setSystemAddress(systemAddress);
        return this;
    }

    public void setSystemAddress(YesNo systemAddress) {
        this.systemAddress = systemAddress;
    }

    public YesNo getHotWallet() {
        return this.hotWallet;
    }

    public BitcoinAddresses hotWallet(YesNo hotWallet) {
        this.setHotWallet(hotWallet);
        return this;
    }

    public void setHotWallet(YesNo hotWallet) {
        this.hotWallet = hotWallet;
    }

    public YesNo getWarmWallet() {
        return this.warmWallet;
    }

    public BitcoinAddresses warmWallet(YesNo warmWallet) {
        this.setWarmWallet(warmWallet);
        return this;
    }

    public void setWarmWallet(YesNo warmWallet) {
        this.warmWallet = warmWallet;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public BitcoinAddresses siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BitcoinAddresses)) {
            return false;
        }
        return id != null && id.equals(((BitcoinAddresses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BitcoinAddresses{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", date='" + getDate() + "'" +
            ", systemAddress='" + getSystemAddress() + "'" +
            ", hotWallet='" + getHotWallet() + "'" +
            ", warmWallet='" + getWarmWallet() + "'" +
            "}";
    }
}
