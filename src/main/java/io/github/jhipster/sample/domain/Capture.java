package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.CaptureStatus;
import io.github.jhipster.sample.domain.enumeration.Currency;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Capture.
 */
@Entity
@Table(name = "capture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Capture implements Serializable {

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
    @Column(name = "status")
    private CaptureStatus status;

    @Column(name = "amount_to_capture")
    private Double amountToCapture;

    @Column(name = "converted_amount_to_capture")
    private Double convertedAmountToCapture;

    @Enumerated(EnumType.STRING)
    @Column(name = "converted_currency")
    private Currency convertedCurrency;

    @Column(name = "conversion_rate")
    private Double conversionRate;

    @Column(name = "is_final_capture")
    private Boolean isFinalCapture;

    @OneToMany(mappedBy = "captures")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "lastErrorReport", "abuseReport", "attributes", "paymentJobs", "paymentMethods", "steps", "refunds", "captures" },
        allowSetters = true
    )
    private Set<Payment> payments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Capture id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return this.reference;
    }

    public Capture reference(Long reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Instant getCreateDateTimeUtc() {
        return this.createDateTimeUtc;
    }

    public Capture createDateTimeUtc(Instant createDateTimeUtc) {
        this.setCreateDateTimeUtc(createDateTimeUtc);
        return this;
    }

    public void setCreateDateTimeUtc(Instant createDateTimeUtc) {
        this.createDateTimeUtc = createDateTimeUtc;
    }

    public CaptureStatus getStatus() {
        return this.status;
    }

    public Capture status(CaptureStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CaptureStatus status) {
        this.status = status;
    }

    public Double getAmountToCapture() {
        return this.amountToCapture;
    }

    public Capture amountToCapture(Double amountToCapture) {
        this.setAmountToCapture(amountToCapture);
        return this;
    }

    public void setAmountToCapture(Double amountToCapture) {
        this.amountToCapture = amountToCapture;
    }

    public Double getConvertedAmountToCapture() {
        return this.convertedAmountToCapture;
    }

    public Capture convertedAmountToCapture(Double convertedAmountToCapture) {
        this.setConvertedAmountToCapture(convertedAmountToCapture);
        return this;
    }

    public void setConvertedAmountToCapture(Double convertedAmountToCapture) {
        this.convertedAmountToCapture = convertedAmountToCapture;
    }

    public Currency getConvertedCurrency() {
        return this.convertedCurrency;
    }

    public Capture convertedCurrency(Currency convertedCurrency) {
        this.setConvertedCurrency(convertedCurrency);
        return this;
    }

    public void setConvertedCurrency(Currency convertedCurrency) {
        this.convertedCurrency = convertedCurrency;
    }

    public Double getConversionRate() {
        return this.conversionRate;
    }

    public Capture conversionRate(Double conversionRate) {
        this.setConversionRate(conversionRate);
        return this;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Boolean getIsFinalCapture() {
        return this.isFinalCapture;
    }

    public Capture isFinalCapture(Boolean isFinalCapture) {
        this.setIsFinalCapture(isFinalCapture);
        return this;
    }

    public void setIsFinalCapture(Boolean isFinalCapture) {
        this.isFinalCapture = isFinalCapture;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setCaptures(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setCaptures(this));
        }
        this.payments = payments;
    }

    public Capture payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public Capture addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setCaptures(this);
        return this;
    }

    public Capture removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setCaptures(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Capture)) {
            return false;
        }
        return id != null && id.equals(((Capture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Capture{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            ", createDateTimeUtc='" + getCreateDateTimeUtc() + "'" +
            ", status='" + getStatus() + "'" +
            ", amountToCapture=" + getAmountToCapture() +
            ", convertedAmountToCapture=" + getConvertedAmountToCapture() +
            ", convertedCurrency='" + getConvertedCurrency() + "'" +
            ", conversionRate=" + getConversionRate() +
            ", isFinalCapture='" + getIsFinalCapture() + "'" +
            "}";
    }
}
