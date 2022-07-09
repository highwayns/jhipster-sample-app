package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentAttributes.
 */
@Entity
@Table(name = "payment_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "originating_ip_address")
    private String originatingIpAddress;

    @Column(name = "origin_header")
    private String originHeader;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "return_url_success")
    private String returnUrlSuccess;

    @Column(name = "return_url_failed")
    private String returnUrlFailed;

    @Column(name = "return_url_cancelled")
    private String returnUrlCancelled;

    @Column(name = "simulated_status")
    private String simulatedStatus;

    @Column(name = "ideal_bic")
    private String idealBic;

    @Column(name = "payment_method_transaction_id")
    private String paymentMethodTransactionId;

    @Column(name = "payment_method_void_transaction_id")
    private String paymentMethodVoidTransactionId;

    @Column(name = "token")
    private String token;

    @Column(name = "cash_flows_acquiring_details")
    private String cashFlowsAcquiringDetails;

    @Column(name = "descriptor")
    private String descriptor;

    @Column(name = "ewallet_type")
    private String ewalletType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginatingIpAddress() {
        return this.originatingIpAddress;
    }

    public PaymentAttributes originatingIpAddress(String originatingIpAddress) {
        this.setOriginatingIpAddress(originatingIpAddress);
        return this;
    }

    public void setOriginatingIpAddress(String originatingIpAddress) {
        this.originatingIpAddress = originatingIpAddress;
    }

    public String getOriginHeader() {
        return this.originHeader;
    }

    public PaymentAttributes originHeader(String originHeader) {
        this.setOriginHeader(originHeader);
        return this;
    }

    public void setOriginHeader(String originHeader) {
        this.originHeader = originHeader;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public PaymentAttributes userAgent(String userAgent) {
        this.setUserAgent(userAgent);
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReturnUrlSuccess() {
        return this.returnUrlSuccess;
    }

    public PaymentAttributes returnUrlSuccess(String returnUrlSuccess) {
        this.setReturnUrlSuccess(returnUrlSuccess);
        return this;
    }

    public void setReturnUrlSuccess(String returnUrlSuccess) {
        this.returnUrlSuccess = returnUrlSuccess;
    }

    public String getReturnUrlFailed() {
        return this.returnUrlFailed;
    }

    public PaymentAttributes returnUrlFailed(String returnUrlFailed) {
        this.setReturnUrlFailed(returnUrlFailed);
        return this;
    }

    public void setReturnUrlFailed(String returnUrlFailed) {
        this.returnUrlFailed = returnUrlFailed;
    }

    public String getReturnUrlCancelled() {
        return this.returnUrlCancelled;
    }

    public PaymentAttributes returnUrlCancelled(String returnUrlCancelled) {
        this.setReturnUrlCancelled(returnUrlCancelled);
        return this;
    }

    public void setReturnUrlCancelled(String returnUrlCancelled) {
        this.returnUrlCancelled = returnUrlCancelled;
    }

    public String getSimulatedStatus() {
        return this.simulatedStatus;
    }

    public PaymentAttributes simulatedStatus(String simulatedStatus) {
        this.setSimulatedStatus(simulatedStatus);
        return this;
    }

    public void setSimulatedStatus(String simulatedStatus) {
        this.simulatedStatus = simulatedStatus;
    }

    public String getIdealBic() {
        return this.idealBic;
    }

    public PaymentAttributes idealBic(String idealBic) {
        this.setIdealBic(idealBic);
        return this;
    }

    public void setIdealBic(String idealBic) {
        this.idealBic = idealBic;
    }

    public String getPaymentMethodTransactionId() {
        return this.paymentMethodTransactionId;
    }

    public PaymentAttributes paymentMethodTransactionId(String paymentMethodTransactionId) {
        this.setPaymentMethodTransactionId(paymentMethodTransactionId);
        return this;
    }

    public void setPaymentMethodTransactionId(String paymentMethodTransactionId) {
        this.paymentMethodTransactionId = paymentMethodTransactionId;
    }

    public String getPaymentMethodVoidTransactionId() {
        return this.paymentMethodVoidTransactionId;
    }

    public PaymentAttributes paymentMethodVoidTransactionId(String paymentMethodVoidTransactionId) {
        this.setPaymentMethodVoidTransactionId(paymentMethodVoidTransactionId);
        return this;
    }

    public void setPaymentMethodVoidTransactionId(String paymentMethodVoidTransactionId) {
        this.paymentMethodVoidTransactionId = paymentMethodVoidTransactionId;
    }

    public String getToken() {
        return this.token;
    }

    public PaymentAttributes token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCashFlowsAcquiringDetails() {
        return this.cashFlowsAcquiringDetails;
    }

    public PaymentAttributes cashFlowsAcquiringDetails(String cashFlowsAcquiringDetails) {
        this.setCashFlowsAcquiringDetails(cashFlowsAcquiringDetails);
        return this;
    }

    public void setCashFlowsAcquiringDetails(String cashFlowsAcquiringDetails) {
        this.cashFlowsAcquiringDetails = cashFlowsAcquiringDetails;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public PaymentAttributes descriptor(String descriptor) {
        this.setDescriptor(descriptor);
        return this;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getEwalletType() {
        return this.ewalletType;
    }

    public PaymentAttributes ewalletType(String ewalletType) {
        this.setEwalletType(ewalletType);
        return this;
    }

    public void setEwalletType(String ewalletType) {
        this.ewalletType = ewalletType;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public PaymentAttributes paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAttributes)) {
            return false;
        }
        return id != null && id.equals(((PaymentAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAttributes{" +
            "id=" + getId() +
            ", originatingIpAddress='" + getOriginatingIpAddress() + "'" +
            ", originHeader='" + getOriginHeader() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", returnUrlSuccess='" + getReturnUrlSuccess() + "'" +
            ", returnUrlFailed='" + getReturnUrlFailed() + "'" +
            ", returnUrlCancelled='" + getReturnUrlCancelled() + "'" +
            ", simulatedStatus='" + getSimulatedStatus() + "'" +
            ", idealBic='" + getIdealBic() + "'" +
            ", paymentMethodTransactionId='" + getPaymentMethodTransactionId() + "'" +
            ", paymentMethodVoidTransactionId='" + getPaymentMethodVoidTransactionId() + "'" +
            ", token='" + getToken() + "'" +
            ", cashFlowsAcquiringDetails='" + getCashFlowsAcquiringDetails() + "'" +
            ", descriptor='" + getDescriptor() + "'" +
            ", ewalletType='" + getEwalletType() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
