package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.PaymentMethod;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentMethods.
 */
@Entity
@Table(name = "payment_methods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentMethods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "paymentMethods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "lastErrorReport", "abuseReport", "attributes", "paymentJobs", "paymentMethods", "steps", "refunds", "captures" },
        allowSetters = true
    )
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "paymentMethods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "payments", "paymentMethods" }, allowSetters = true)
    private Set<PaymentStep> paymentSteps = new HashSet<>();

    @OneToMany(mappedBy = "paymentMethodsToUse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "order", "attributes", "recurrenceCriteria", "orderHistory", "paymentMethodsToUse", "payments" },
        allowSetters = true
    )
    private Set<PaymentJob> paymentJobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentMethods id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public PaymentMethods paymentMethod(PaymentMethod paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setPaymentMethods(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setPaymentMethods(this));
        }
        this.payments = payments;
    }

    public PaymentMethods payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public PaymentMethods addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setPaymentMethods(this);
        return this;
    }

    public PaymentMethods removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setPaymentMethods(null);
        return this;
    }

    public Set<PaymentStep> getPaymentSteps() {
        return this.paymentSteps;
    }

    public void setPaymentSteps(Set<PaymentStep> paymentSteps) {
        if (this.paymentSteps != null) {
            this.paymentSteps.forEach(i -> i.setPaymentMethods(null));
        }
        if (paymentSteps != null) {
            paymentSteps.forEach(i -> i.setPaymentMethods(this));
        }
        this.paymentSteps = paymentSteps;
    }

    public PaymentMethods paymentSteps(Set<PaymentStep> paymentSteps) {
        this.setPaymentSteps(paymentSteps);
        return this;
    }

    public PaymentMethods addPaymentStep(PaymentStep paymentStep) {
        this.paymentSteps.add(paymentStep);
        paymentStep.setPaymentMethods(this);
        return this;
    }

    public PaymentMethods removePaymentStep(PaymentStep paymentStep) {
        this.paymentSteps.remove(paymentStep);
        paymentStep.setPaymentMethods(null);
        return this;
    }

    public Set<PaymentJob> getPaymentJobs() {
        return this.paymentJobs;
    }

    public void setPaymentJobs(Set<PaymentJob> paymentJobs) {
        if (this.paymentJobs != null) {
            this.paymentJobs.forEach(i -> i.setPaymentMethodsToUse(null));
        }
        if (paymentJobs != null) {
            paymentJobs.forEach(i -> i.setPaymentMethodsToUse(this));
        }
        this.paymentJobs = paymentJobs;
    }

    public PaymentMethods paymentJobs(Set<PaymentJob> paymentJobs) {
        this.setPaymentJobs(paymentJobs);
        return this;
    }

    public PaymentMethods addPaymentJob(PaymentJob paymentJob) {
        this.paymentJobs.add(paymentJob);
        paymentJob.setPaymentMethodsToUse(this);
        return this;
    }

    public PaymentMethods removePaymentJob(PaymentJob paymentJob) {
        this.paymentJobs.remove(paymentJob);
        paymentJob.setPaymentMethodsToUse(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethods)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethods) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethods{" +
            "id=" + getId() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            "}";
    }
}
