package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.Currency;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Currencys.
 */
@Entity
@Table(name = "currencys")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Currencys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @OneToMany(mappedBy = "currencies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currencies", "issuerList", "tokenizedCards" }, allowSetters = true)
    private Set<PaymentMethodInfo> paymentMethodInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Currencys id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Currencys currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Set<PaymentMethodInfo> getPaymentMethodInfos() {
        return this.paymentMethodInfos;
    }

    public void setPaymentMethodInfos(Set<PaymentMethodInfo> paymentMethodInfos) {
        if (this.paymentMethodInfos != null) {
            this.paymentMethodInfos.forEach(i -> i.setCurrencies(null));
        }
        if (paymentMethodInfos != null) {
            paymentMethodInfos.forEach(i -> i.setCurrencies(this));
        }
        this.paymentMethodInfos = paymentMethodInfos;
    }

    public Currencys paymentMethodInfos(Set<PaymentMethodInfo> paymentMethodInfos) {
        this.setPaymentMethodInfos(paymentMethodInfos);
        return this;
    }

    public Currencys addPaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfos.add(paymentMethodInfo);
        paymentMethodInfo.setCurrencies(this);
        return this;
    }

    public Currencys removePaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfos.remove(paymentMethodInfo);
        paymentMethodInfo.setCurrencies(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currencys)) {
            return false;
        }
        return id != null && id.equals(((Currencys) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currencys{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
