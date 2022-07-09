package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payment implements Serializable {

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
    private PaymentStatus status;

    @Column(name = "amount_to_collect")
    private Double amountToCollect;

    @Column(name = "surcharge_amount")
    private Double surchargeAmount;

    @Column(name = "converted_total_amount")
    private Double convertedTotalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "converted_currency")
    private Currency convertedCurrency;

    @Column(name = "conversion_rate")
    private Double conversionRate;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @OneToOne
    @JoinColumn(unique = true)
    private ErrorReport lastErrorReport;

    @OneToOne
    @JoinColumn(unique = true)
    private AbuseReport abuseReport;

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentAttributes attributes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return this.reference;
    }

    public Payment reference(Long reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Instant getCreateDateTimeUtc() {
        return this.createDateTimeUtc;
    }

    public Payment createDateTimeUtc(Instant createDateTimeUtc) {
        this.setCreateDateTimeUtc(createDateTimeUtc);
        return this;
    }

    public void setCreateDateTimeUtc(Instant createDateTimeUtc) {
        this.createDateTimeUtc = createDateTimeUtc;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public Payment status(PaymentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Double getAmountToCollect() {
        return this.amountToCollect;
    }

    public Payment amountToCollect(Double amountToCollect) {
        this.setAmountToCollect(amountToCollect);
        return this;
    }

    public void setAmountToCollect(Double amountToCollect) {
        this.amountToCollect = amountToCollect;
    }

    public Double getSurchargeAmount() {
        return this.surchargeAmount;
    }

    public Payment surchargeAmount(Double surchargeAmount) {
        this.setSurchargeAmount(surchargeAmount);
        return this;
    }

    public void setSurchargeAmount(Double surchargeAmount) {
        this.surchargeAmount = surchargeAmount;
    }

    public Double getConvertedTotalAmount() {
        return this.convertedTotalAmount;
    }

    public Payment convertedTotalAmount(Double convertedTotalAmount) {
        this.setConvertedTotalAmount(convertedTotalAmount);
        return this;
    }

    public void setConvertedTotalAmount(Double convertedTotalAmount) {
        this.convertedTotalAmount = convertedTotalAmount;
    }

    public Currency getConvertedCurrency() {
        return this.convertedCurrency;
    }

    public Payment convertedCurrency(Currency convertedCurrency) {
        this.setConvertedCurrency(convertedCurrency);
        return this;
    }

    public void setConvertedCurrency(Currency convertedCurrency) {
        this.convertedCurrency = convertedCurrency;
    }

    public Double getConversionRate() {
        return this.conversionRate;
    }

    public Payment conversionRate(Double conversionRate) {
        this.setConversionRate(conversionRate);
        return this;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Double getPaidAmount() {
        return this.paidAmount;
    }

    public Payment paidAmount(Double paidAmount) {
        this.setPaidAmount(paidAmount);
        return this;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public ErrorReport getLastErrorReport() {
        return this.lastErrorReport;
    }

    public void setLastErrorReport(ErrorReport errorReport) {
        this.lastErrorReport = errorReport;
    }

    public Payment lastErrorReport(ErrorReport errorReport) {
        this.setLastErrorReport(errorReport);
        return this;
    }

    public AbuseReport getAbuseReport() {
        return this.abuseReport;
    }

    public void setAbuseReport(AbuseReport abuseReport) {
        this.abuseReport = abuseReport;
    }

    public Payment abuseReport(AbuseReport abuseReport) {
        this.setAbuseReport(abuseReport);
        return this;
    }

    public PaymentAttributes getAttributes() {
        return this.attributes;
    }

    public void setAttributes(PaymentAttributes paymentAttributes) {
        this.attributes = paymentAttributes;
    }

    public Payment attributes(PaymentAttributes paymentAttributes) {
        this.setAttributes(paymentAttributes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            ", createDateTimeUtc='" + getCreateDateTimeUtc() + "'" +
            ", status='" + getStatus() + "'" +
            ", amountToCollect=" + getAmountToCollect() +
            ", surchargeAmount=" + getSurchargeAmount() +
            ", convertedTotalAmount=" + getConvertedTotalAmount() +
            ", convertedCurrency='" + getConvertedCurrency() + "'" +
            ", conversionRate=" + getConversionRate() +
            ", paidAmount=" + getPaidAmount() +
            "}";
    }
}
