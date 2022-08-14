package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Conversions.
 */
@Entity
@Table(name = "conversions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conversions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_active", nullable = false)
    private YesNo isActive;

    @NotNull
    @Column(name = "total_withdrawals", nullable = false)
    private Double totalWithdrawals;

    @NotNull
    @Column(name = "profit_to_factor", nullable = false)
    private Double profitToFactor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "factored", nullable = false)
    private YesNo factored;

    @NotNull
    @Column(name = "date_1", nullable = false)
    private Instant date1;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Conversions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Conversions amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return this.date;
    }

    public Conversions date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public YesNo getIsActive() {
        return this.isActive;
    }

    public Conversions isActive(YesNo isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(YesNo isActive) {
        this.isActive = isActive;
    }

    public Double getTotalWithdrawals() {
        return this.totalWithdrawals;
    }

    public Conversions totalWithdrawals(Double totalWithdrawals) {
        this.setTotalWithdrawals(totalWithdrawals);
        return this;
    }

    public void setTotalWithdrawals(Double totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }

    public Double getProfitToFactor() {
        return this.profitToFactor;
    }

    public Conversions profitToFactor(Double profitToFactor) {
        this.setProfitToFactor(profitToFactor);
        return this;
    }

    public void setProfitToFactor(Double profitToFactor) {
        this.profitToFactor = profitToFactor;
    }

    public YesNo getFactored() {
        return this.factored;
    }

    public Conversions factored(YesNo factored) {
        this.setFactored(factored);
        return this;
    }

    public void setFactored(YesNo factored) {
        this.factored = factored;
    }

    public Instant getDate1() {
        return this.date1;
    }

    public Conversions date1(Instant date1) {
        this.setDate1(date1);
        return this;
    }

    public void setDate1(Instant date1) {
        this.date1 = date1;
    }

    public Currencies getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currencies currencies) {
        this.currency = currencies;
    }

    public Conversions currency(Currencies currencies) {
        this.setCurrency(currencies);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conversions)) {
            return false;
        }
        return id != null && id.equals(((Conversions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conversions{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", date='" + getDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", totalWithdrawals=" + getTotalWithdrawals() +
            ", profitToFactor=" + getProfitToFactor() +
            ", factored='" + getFactored() + "'" +
            ", date1='" + getDate1() + "'" +
            "}";
    }
}
