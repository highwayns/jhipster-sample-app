package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CardTokenData.
 */
@Entity
@Table(name = "card_token_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CardTokenData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "card_expiry_month")
    private String cardExpiryMonth;

    @Column(name = "card_expiry_year")
    private String cardExpiryYear;

    @Column(name = "issuer_return_code")
    private String issuerReturnCode;

    @Column(name = "truncated_card_number")
    private String truncatedCardNumber;

    @OneToMany(mappedBy = "tokenizedCards")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currencies", "issuerList", "tokenizedCards" }, allowSetters = true)
    private Set<PaymentMethodInfo> paymentMethodInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CardTokenData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public CardTokenData token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCardExpiryMonth() {
        return this.cardExpiryMonth;
    }

    public CardTokenData cardExpiryMonth(String cardExpiryMonth) {
        this.setCardExpiryMonth(cardExpiryMonth);
        return this;
    }

    public void setCardExpiryMonth(String cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public String getCardExpiryYear() {
        return this.cardExpiryYear;
    }

    public CardTokenData cardExpiryYear(String cardExpiryYear) {
        this.setCardExpiryYear(cardExpiryYear);
        return this;
    }

    public void setCardExpiryYear(String cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public String getIssuerReturnCode() {
        return this.issuerReturnCode;
    }

    public CardTokenData issuerReturnCode(String issuerReturnCode) {
        this.setIssuerReturnCode(issuerReturnCode);
        return this;
    }

    public void setIssuerReturnCode(String issuerReturnCode) {
        this.issuerReturnCode = issuerReturnCode;
    }

    public String getTruncatedCardNumber() {
        return this.truncatedCardNumber;
    }

    public CardTokenData truncatedCardNumber(String truncatedCardNumber) {
        this.setTruncatedCardNumber(truncatedCardNumber);
        return this;
    }

    public void setTruncatedCardNumber(String truncatedCardNumber) {
        this.truncatedCardNumber = truncatedCardNumber;
    }

    public Set<PaymentMethodInfo> getPaymentMethodInfos() {
        return this.paymentMethodInfos;
    }

    public void setPaymentMethodInfos(Set<PaymentMethodInfo> paymentMethodInfos) {
        if (this.paymentMethodInfos != null) {
            this.paymentMethodInfos.forEach(i -> i.setTokenizedCards(null));
        }
        if (paymentMethodInfos != null) {
            paymentMethodInfos.forEach(i -> i.setTokenizedCards(this));
        }
        this.paymentMethodInfos = paymentMethodInfos;
    }

    public CardTokenData paymentMethodInfos(Set<PaymentMethodInfo> paymentMethodInfos) {
        this.setPaymentMethodInfos(paymentMethodInfos);
        return this;
    }

    public CardTokenData addPaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfos.add(paymentMethodInfo);
        paymentMethodInfo.setTokenizedCards(this);
        return this;
    }

    public CardTokenData removePaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfos.remove(paymentMethodInfo);
        paymentMethodInfo.setTokenizedCards(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardTokenData)) {
            return false;
        }
        return id != null && id.equals(((CardTokenData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardTokenData{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", cardExpiryMonth='" + getCardExpiryMonth() + "'" +
            ", cardExpiryYear='" + getCardExpiryYear() + "'" +
            ", issuerReturnCode='" + getIssuerReturnCode() + "'" +
            ", truncatedCardNumber='" + getTruncatedCardNumber() + "'" +
            "}";
    }
}
