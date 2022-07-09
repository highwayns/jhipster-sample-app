package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.Currency;
import io.github.jhipster.sample.domain.enumeration.Locale;
import io.github.jhipster.sample.domain.enumeration.PaymentJobType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentJob.
 */
@Entity
@Table(name = "payment_job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentJob implements Serializable {

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
    @Column(name = "type")
    private PaymentJobType type;

    @Column(name = "trace_reference")
    private Long traceReference;

    @Column(name = "configuration_id")
    private String configurationId;

    @Column(name = "domain")
    private String domain;

    @Enumerated(EnumType.STRING)
    @Column(name = "locale")
    private Locale locale;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "parent_payment_job_reference")
    private Long parentPaymentJobReference;

    @Column(name = "display_url")
    private String displayUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "amount_to_collect")
    private Double amountToCollect;

    @Column(name = "amount_collected")
    private Double amountCollected;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "paid_date_time_utc")
    private Instant paidDateTimeUtc;

    @Column(name = "expiration_date_time_utc")
    private Instant expirationDateTimeUtc;

    @Column(name = "due_date_time_utc")
    private Instant dueDateTimeUtc;

    @Column(name = "last_update_time_utc")
    private Instant lastUpdateTimeUtc;

    @Column(name = "last_processed_time_utc")
    private Instant lastProcessedTimeUtc;

    @JsonIgnoreProperties(value = { "billingAddress", "billingIdentity", "shippingAddress", "orderLines" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Order order;

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentJobAttributes attributes;

    @OneToOne
    @JoinColumn(unique = true)
    private RecurrenceCriteria recurrenceCriteria;

    @ManyToOne
    @JsonIgnoreProperties(value = { "billingAddress", "billingIdentity", "shippingAddress", "orderLines" }, allowSetters = true)
    private Order orderHistory;

    @ManyToOne
    @JsonIgnoreProperties(value = { "payments", "paymentSteps", "paymentJobs" }, allowSetters = true)
    private PaymentMethods paymentMethodsToUse;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "lastErrorReport", "abuseReport", "attributes", "paymentJobs", "paymentMethods", "steps", "refunds", "captures" },
        allowSetters = true
    )
    private Payment payments;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentJob id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return this.reference;
    }

    public PaymentJob reference(Long reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Instant getCreateDateTimeUtc() {
        return this.createDateTimeUtc;
    }

    public PaymentJob createDateTimeUtc(Instant createDateTimeUtc) {
        this.setCreateDateTimeUtc(createDateTimeUtc);
        return this;
    }

    public void setCreateDateTimeUtc(Instant createDateTimeUtc) {
        this.createDateTimeUtc = createDateTimeUtc;
    }

    public PaymentJobType getType() {
        return this.type;
    }

    public PaymentJob type(PaymentJobType type) {
        this.setType(type);
        return this;
    }

    public void setType(PaymentJobType type) {
        this.type = type;
    }

    public Long getTraceReference() {
        return this.traceReference;
    }

    public PaymentJob traceReference(Long traceReference) {
        this.setTraceReference(traceReference);
        return this;
    }

    public void setTraceReference(Long traceReference) {
        this.traceReference = traceReference;
    }

    public String getConfigurationId() {
        return this.configurationId;
    }

    public PaymentJob configurationId(String configurationId) {
        this.setConfigurationId(configurationId);
        return this;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public String getDomain() {
        return this.domain;
    }

    public PaymentJob domain(String domain) {
        this.setDomain(domain);
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public PaymentJob locale(Locale locale) {
        this.setLocale(locale);
        return this;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public PaymentJob timeZone(String timeZone) {
        this.setTimeZone(timeZone);
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Long getParentPaymentJobReference() {
        return this.parentPaymentJobReference;
    }

    public PaymentJob parentPaymentJobReference(Long parentPaymentJobReference) {
        this.setParentPaymentJobReference(parentPaymentJobReference);
        return this;
    }

    public void setParentPaymentJobReference(Long parentPaymentJobReference) {
        this.parentPaymentJobReference = parentPaymentJobReference;
    }

    public String getDisplayUrl() {
        return this.displayUrl;
    }

    public PaymentJob displayUrl(String displayUrl) {
        this.setDisplayUrl(displayUrl);
        return this;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public PaymentJob currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAmountToCollect() {
        return this.amountToCollect;
    }

    public PaymentJob amountToCollect(Double amountToCollect) {
        this.setAmountToCollect(amountToCollect);
        return this;
    }

    public void setAmountToCollect(Double amountToCollect) {
        this.amountToCollect = amountToCollect;
    }

    public Double getAmountCollected() {
        return this.amountCollected;
    }

    public PaymentJob amountCollected(Double amountCollected) {
        this.setAmountCollected(amountCollected);
        return this;
    }

    public void setAmountCollected(Double amountCollected) {
        this.amountCollected = amountCollected;
    }

    public Double getPaidAmount() {
        return this.paidAmount;
    }

    public PaymentJob paidAmount(Double paidAmount) {
        this.setPaidAmount(paidAmount);
        return this;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Instant getPaidDateTimeUtc() {
        return this.paidDateTimeUtc;
    }

    public PaymentJob paidDateTimeUtc(Instant paidDateTimeUtc) {
        this.setPaidDateTimeUtc(paidDateTimeUtc);
        return this;
    }

    public void setPaidDateTimeUtc(Instant paidDateTimeUtc) {
        this.paidDateTimeUtc = paidDateTimeUtc;
    }

    public Instant getExpirationDateTimeUtc() {
        return this.expirationDateTimeUtc;
    }

    public PaymentJob expirationDateTimeUtc(Instant expirationDateTimeUtc) {
        this.setExpirationDateTimeUtc(expirationDateTimeUtc);
        return this;
    }

    public void setExpirationDateTimeUtc(Instant expirationDateTimeUtc) {
        this.expirationDateTimeUtc = expirationDateTimeUtc;
    }

    public Instant getDueDateTimeUtc() {
        return this.dueDateTimeUtc;
    }

    public PaymentJob dueDateTimeUtc(Instant dueDateTimeUtc) {
        this.setDueDateTimeUtc(dueDateTimeUtc);
        return this;
    }

    public void setDueDateTimeUtc(Instant dueDateTimeUtc) {
        this.dueDateTimeUtc = dueDateTimeUtc;
    }

    public Instant getLastUpdateTimeUtc() {
        return this.lastUpdateTimeUtc;
    }

    public PaymentJob lastUpdateTimeUtc(Instant lastUpdateTimeUtc) {
        this.setLastUpdateTimeUtc(lastUpdateTimeUtc);
        return this;
    }

    public void setLastUpdateTimeUtc(Instant lastUpdateTimeUtc) {
        this.lastUpdateTimeUtc = lastUpdateTimeUtc;
    }

    public Instant getLastProcessedTimeUtc() {
        return this.lastProcessedTimeUtc;
    }

    public PaymentJob lastProcessedTimeUtc(Instant lastProcessedTimeUtc) {
        this.setLastProcessedTimeUtc(lastProcessedTimeUtc);
        return this;
    }

    public void setLastProcessedTimeUtc(Instant lastProcessedTimeUtc) {
        this.lastProcessedTimeUtc = lastProcessedTimeUtc;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentJob order(Order order) {
        this.setOrder(order);
        return this;
    }

    public PaymentJobAttributes getAttributes() {
        return this.attributes;
    }

    public void setAttributes(PaymentJobAttributes paymentJobAttributes) {
        this.attributes = paymentJobAttributes;
    }

    public PaymentJob attributes(PaymentJobAttributes paymentJobAttributes) {
        this.setAttributes(paymentJobAttributes);
        return this;
    }

    public RecurrenceCriteria getRecurrenceCriteria() {
        return this.recurrenceCriteria;
    }

    public void setRecurrenceCriteria(RecurrenceCriteria recurrenceCriteria) {
        this.recurrenceCriteria = recurrenceCriteria;
    }

    public PaymentJob recurrenceCriteria(RecurrenceCriteria recurrenceCriteria) {
        this.setRecurrenceCriteria(recurrenceCriteria);
        return this;
    }

    public Order getOrderHistory() {
        return this.orderHistory;
    }

    public void setOrderHistory(Order order) {
        this.orderHistory = order;
    }

    public PaymentJob orderHistory(Order order) {
        this.setOrderHistory(order);
        return this;
    }

    public PaymentMethods getPaymentMethodsToUse() {
        return this.paymentMethodsToUse;
    }

    public void setPaymentMethodsToUse(PaymentMethods paymentMethods) {
        this.paymentMethodsToUse = paymentMethods;
    }

    public PaymentJob paymentMethodsToUse(PaymentMethods paymentMethods) {
        this.setPaymentMethodsToUse(paymentMethods);
        return this;
    }

    public Payment getPayments() {
        return this.payments;
    }

    public void setPayments(Payment payment) {
        this.payments = payment;
    }

    public PaymentJob payments(Payment payment) {
        this.setPayments(payment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentJob)) {
            return false;
        }
        return id != null && id.equals(((PaymentJob) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentJob{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            ", createDateTimeUtc='" + getCreateDateTimeUtc() + "'" +
            ", type='" + getType() + "'" +
            ", traceReference=" + getTraceReference() +
            ", configurationId='" + getConfigurationId() + "'" +
            ", domain='" + getDomain() + "'" +
            ", locale='" + getLocale() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", parentPaymentJobReference=" + getParentPaymentJobReference() +
            ", displayUrl='" + getDisplayUrl() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", amountToCollect=" + getAmountToCollect() +
            ", amountCollected=" + getAmountCollected() +
            ", paidAmount=" + getPaidAmount() +
            ", paidDateTimeUtc='" + getPaidDateTimeUtc() + "'" +
            ", expirationDateTimeUtc='" + getExpirationDateTimeUtc() + "'" +
            ", dueDateTimeUtc='" + getDueDateTimeUtc() + "'" +
            ", lastUpdateTimeUtc='" + getLastUpdateTimeUtc() + "'" +
            ", lastProcessedTimeUtc='" + getLastProcessedTimeUtc() + "'" +
            "}";
    }
}
