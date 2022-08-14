package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StatusEscrows.
 */
@Entity
@Table(name = "status_escrows")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StatusEscrows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Double balance;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies currency;

    @OneToOne
    @JoinColumn(unique = true)
    private Status statusId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StatusEscrows id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return this.balance;
    }

    public StatusEscrows balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currencies getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currencies currencies) {
        this.currency = currencies;
    }

    public StatusEscrows currency(Currencies currencies) {
        this.setCurrency(currencies);
        return this;
    }

    public Status getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Status status) {
        this.statusId = status;
    }

    public StatusEscrows statusId(Status status) {
        this.setStatusId(status);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusEscrows)) {
            return false;
        }
        return id != null && id.equals(((StatusEscrows) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusEscrows{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            "}";
    }
}
