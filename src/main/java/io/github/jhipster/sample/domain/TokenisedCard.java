package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TokenisedCard.
 */
@Entity
@Table(name = "tokenised_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TokenisedCard implements Serializable {

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

    @Column(name = "truncated_card_number")
    private String truncatedCardNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TokenisedCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public TokenisedCard token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCardExpiryMonth() {
        return this.cardExpiryMonth;
    }

    public TokenisedCard cardExpiryMonth(String cardExpiryMonth) {
        this.setCardExpiryMonth(cardExpiryMonth);
        return this;
    }

    public void setCardExpiryMonth(String cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public String getCardExpiryYear() {
        return this.cardExpiryYear;
    }

    public TokenisedCard cardExpiryYear(String cardExpiryYear) {
        this.setCardExpiryYear(cardExpiryYear);
        return this;
    }

    public void setCardExpiryYear(String cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public String getTruncatedCardNumber() {
        return this.truncatedCardNumber;
    }

    public TokenisedCard truncatedCardNumber(String truncatedCardNumber) {
        this.setTruncatedCardNumber(truncatedCardNumber);
        return this;
    }

    public void setTruncatedCardNumber(String truncatedCardNumber) {
        this.truncatedCardNumber = truncatedCardNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenisedCard)) {
            return false;
        }
        return id != null && id.equals(((TokenisedCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TokenisedCard{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", cardExpiryMonth='" + getCardExpiryMonth() + "'" +
            ", cardExpiryYear='" + getCardExpiryYear() + "'" +
            ", truncatedCardNumber='" + getTruncatedCardNumber() + "'" +
            "}";
    }
}
