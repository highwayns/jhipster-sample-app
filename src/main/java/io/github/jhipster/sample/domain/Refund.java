package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.domain.enumeration.RefundStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Refund.
 */
@Entity
@Table(name = "refund")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Refund implements Serializable {

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

    @Column(name = "refund_number")
    private String refundNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RefundStatus status;

    @Column(name = "amount_to_refund")
    private Double amountToRefund;

    @Column(name = "converted_amount_to_refund")
    private Double convertedAmountToRefund;

    @Enumerated(EnumType.STRING)
    @Column(name = "converted_currency")
    private Currency convertedCurrency;

    @Column(name = "conversion_rate")
    private Double conversionRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Refund id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return this.reference;
    }

    public Refund reference(Long reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Instant getCreateDateTimeUtc() {
        return this.createDateTimeUtc;
    }

    public Refund createDateTimeUtc(Instant createDateTimeUtc) {
        this.setCreateDateTimeUtc(createDateTimeUtc);
        return this;
    }

    public void setCreateDateTimeUtc(Instant createDateTimeUtc) {
        this.createDateTimeUtc = createDateTimeUtc;
    }

    public String getRefundNumber() {
        return this.refundNumber;
    }

    public Refund refundNumber(String refundNumber) {
        this.setRefundNumber(refundNumber);
        return this;
    }

    public void setRefundNumber(String refundNumber) {
        this.refundNumber = refundNumber;
    }

    public RefundStatus getStatus() {
        return this.status;
    }

    public Refund status(RefundStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RefundStatus status) {
        this.status = status;
    }

    public Double getAmountToRefund() {
        return this.amountToRefund;
    }

    public Refund amountToRefund(Double amountToRefund) {
        this.setAmountToRefund(amountToRefund);
        return this;
    }

    public void setAmountToRefund(Double amountToRefund) {
        this.amountToRefund = amountToRefund;
    }

    public Double getConvertedAmountToRefund() {
        return this.convertedAmountToRefund;
    }

    public Refund convertedAmountToRefund(Double convertedAmountToRefund) {
        this.setConvertedAmountToRefund(convertedAmountToRefund);
        return this;
    }

    public void setConvertedAmountToRefund(Double convertedAmountToRefund) {
        this.convertedAmountToRefund = convertedAmountToRefund;
    }

    public Currency getConvertedCurrency() {
        return this.convertedCurrency;
    }

    public Refund convertedCurrency(Currency convertedCurrency) {
        this.setConvertedCurrency(convertedCurrency);
        return this;
    }

    public void setConvertedCurrency(Currency convertedCurrency) {
        this.convertedCurrency = convertedCurrency;
    }

    public Double getConversionRate() {
        return this.conversionRate;
    }

    public Refund conversionRate(Double conversionRate) {
        this.setConversionRate(conversionRate);
        return this;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Refund)) {
            return false;
        }
        return id != null && id.equals(((Refund) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Refund{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            ", createDateTimeUtc='" + getCreateDateTimeUtc() + "'" +
            ", refundNumber='" + getRefundNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", amountToRefund=" + getAmountToRefund() +
            ", convertedAmountToRefund=" + getConvertedAmountToRefund() +
            ", convertedCurrency='" + getConvertedCurrency() + "'" +
            ", conversionRate=" + getConversionRate() +
            "}";
    }
}
