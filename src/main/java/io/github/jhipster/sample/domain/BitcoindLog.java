package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BitcoindLog.
 */
@Entity
@Table(name = "bitcoind_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BitcoindLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "transaction_id", length = 255, nullable = false)
    private String transactionId;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BitcoindLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public BitcoindLog transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public BitcoindLog amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return this.date;
    }

    public BitcoindLog date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public BitcoindLog siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BitcoindLog)) {
            return false;
        }
        return id != null && id.equals(((BitcoindLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BitcoindLog{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", amount=" + getAmount() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
