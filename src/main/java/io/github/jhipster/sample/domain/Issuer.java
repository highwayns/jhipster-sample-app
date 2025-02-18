package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Issuer.
 */
@Entity
@Table(name = "issuer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Issuer implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "issuerList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currencies", "issuerList", "tokenizedCards" }, allowSetters = true)
    private Set<PaymentMethodInfo> paymentMethodInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Issuer id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Issuer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Issuer setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<PaymentMethodInfo> getPaymentMethodInfos() {
        return this.paymentMethodInfos;
    }

    public void setPaymentMethodInfos(Set<PaymentMethodInfo> paymentMethodInfos) {
        if (this.paymentMethodInfos != null) {
            this.paymentMethodInfos.forEach(i -> i.setIssuerList(null));
        }
        if (paymentMethodInfos != null) {
            paymentMethodInfos.forEach(i -> i.setIssuerList(this));
        }
        this.paymentMethodInfos = paymentMethodInfos;
    }

    public Issuer paymentMethodInfos(Set<PaymentMethodInfo> paymentMethodInfos) {
        this.setPaymentMethodInfos(paymentMethodInfos);
        return this;
    }

    public Issuer addPaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfos.add(paymentMethodInfo);
        paymentMethodInfo.setIssuerList(this);
        return this;
    }

    public Issuer removePaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfos.remove(paymentMethodInfo);
        paymentMethodInfo.setIssuerList(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Issuer)) {
            return false;
        }
        return id != null && id.equals(((Issuer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Issuer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
