package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentJobAttributes.
 */
@Entity
@Table(name = "payment_job_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentJobAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "webhook_url")
    private String webhookUrl;

    @Column(name = "google_analytics_client_id")
    private String googleAnalyticsClientId;

    @Column(name = "allowed_parent_frame_domains")
    private String allowedParentFrameDomains;

    @Column(name = "payment_page_reference")
    private String paymentPageReference;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentJobAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebhookUrl() {
        return this.webhookUrl;
    }

    public PaymentJobAttributes webhookUrl(String webhookUrl) {
        this.setWebhookUrl(webhookUrl);
        return this;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getGoogleAnalyticsClientId() {
        return this.googleAnalyticsClientId;
    }

    public PaymentJobAttributes googleAnalyticsClientId(String googleAnalyticsClientId) {
        this.setGoogleAnalyticsClientId(googleAnalyticsClientId);
        return this;
    }

    public void setGoogleAnalyticsClientId(String googleAnalyticsClientId) {
        this.googleAnalyticsClientId = googleAnalyticsClientId;
    }

    public String getAllowedParentFrameDomains() {
        return this.allowedParentFrameDomains;
    }

    public PaymentJobAttributes allowedParentFrameDomains(String allowedParentFrameDomains) {
        this.setAllowedParentFrameDomains(allowedParentFrameDomains);
        return this;
    }

    public void setAllowedParentFrameDomains(String allowedParentFrameDomains) {
        this.allowedParentFrameDomains = allowedParentFrameDomains;
    }

    public String getPaymentPageReference() {
        return this.paymentPageReference;
    }

    public PaymentJobAttributes paymentPageReference(String paymentPageReference) {
        this.setPaymentPageReference(paymentPageReference);
        return this;
    }

    public void setPaymentPageReference(String paymentPageReference) {
        this.paymentPageReference = paymentPageReference;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentJobAttributes)) {
            return false;
        }
        return id != null && id.equals(((PaymentJobAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentJobAttributes{" +
            "id=" + getId() +
            ", webhookUrl='" + getWebhookUrl() + "'" +
            ", googleAnalyticsClientId='" + getGoogleAnalyticsClientId() + "'" +
            ", allowedParentFrameDomains='" + getAllowedParentFrameDomains() + "'" +
            ", paymentPageReference='" + getPaymentPageReference() + "'" +
            "}";
    }
}
