package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.PaymentStatus;
import io.github.jhipster.sample.domain.enumeration.PaymentStepAction;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentStep.
 */
@Entity
@Table(name = "payment_step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "reference")
    private Long reference;

    @Column(name = "create_date_time_utc")
    private Instant createDateTimeUtc;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private PaymentStepAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "amount_to_collect")
    private Double amountToCollect;

    @OneToMany(mappedBy = "steps")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "lastErrorReport", "abuseReport", "attributes", "paymentJobs", "paymentMethods", "steps", "refunds", "captures" },
        allowSetters = true
    )
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "payments", "paymentSteps", "paymentJobs" }, allowSetters = true)
    private PaymentMethods paymentMethods;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentStep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return this.reference;
    }

    public PaymentStep reference(Long reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Instant getCreateDateTimeUtc() {
        return this.createDateTimeUtc;
    }

    public PaymentStep createDateTimeUtc(Instant createDateTimeUtc) {
        this.setCreateDateTimeUtc(createDateTimeUtc);
        return this;
    }

    public void setCreateDateTimeUtc(Instant createDateTimeUtc) {
        this.createDateTimeUtc = createDateTimeUtc;
    }

    public PaymentStepAction getAction() {
        return this.action;
    }

    public PaymentStep action(PaymentStepAction action) {
        this.setAction(action);
        return this;
    }

    public void setAction(PaymentStepAction action) {
        this.action = action;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public PaymentStep status(PaymentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Double getAmountToCollect() {
        return this.amountToCollect;
    }

    public PaymentStep amountToCollect(Double amountToCollect) {
        this.setAmountToCollect(amountToCollect);
        return this;
    }

    public void setAmountToCollect(Double amountToCollect) {
        this.amountToCollect = amountToCollect;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setSteps(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setSteps(this));
        }
        this.payments = payments;
    }

    public PaymentStep payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public PaymentStep addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setSteps(this);
        return this;
    }

    public PaymentStep removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setSteps(null);
        return this;
    }

    public PaymentMethods getPaymentMethods() {
        return this.paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public PaymentStep paymentMethods(PaymentMethods paymentMethods) {
        this.setPaymentMethods(paymentMethods);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentStep)) {
            return false;
        }
        return id != null && id.equals(((PaymentStep) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentStep{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            ", createDateTimeUtc='" + getCreateDateTimeUtc() + "'" +
            ", action='" + getAction() + "'" +
            ", status='" + getStatus() + "'" +
            ", amountToCollect=" + getAmountToCollect() +
            "}";
    }
}
