package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.Currency;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentMethodInfo.
 */
@Entity
@Table(name = "payment_method_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentMethodInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "logo")
    private String logo;

    @Column(name = "supports_tokenisation")
    private Boolean supportsTokenisation;

    @Enumerated(EnumType.STRING)
    @Column(name = "currencies")
    private Currency currencies;

    @Column(name = "surcharge_amount")
    private Double surchargeAmount;

    @Column(name = "surcharge_amount_excl_vat")
    private Double surchargeAmountExclVat;

    @Column(name = "surcharge_amount_vat")
    private Double surchargeAmountVat;

    @Column(name = "surcharge_vat_percentage")
    private Double surchargeVatPercentage;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentMethodInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public PaymentMethodInfo paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getLogo() {
        return this.logo;
    }

    public PaymentMethodInfo logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getSupportsTokenisation() {
        return this.supportsTokenisation;
    }

    public PaymentMethodInfo supportsTokenisation(Boolean supportsTokenisation) {
        this.setSupportsTokenisation(supportsTokenisation);
        return this;
    }

    public void setSupportsTokenisation(Boolean supportsTokenisation) {
        this.supportsTokenisation = supportsTokenisation;
    }

    public Currency getCurrencies() {
        return this.currencies;
    }

    public PaymentMethodInfo currencies(Currency currencies) {
        this.setCurrencies(currencies);
        return this;
    }

    public void setCurrencies(Currency currencies) {
        this.currencies = currencies;
    }

    public Double getSurchargeAmount() {
        return this.surchargeAmount;
    }

    public PaymentMethodInfo surchargeAmount(Double surchargeAmount) {
        this.setSurchargeAmount(surchargeAmount);
        return this;
    }

    public void setSurchargeAmount(Double surchargeAmount) {
        this.surchargeAmount = surchargeAmount;
    }

    public Double getSurchargeAmountExclVat() {
        return this.surchargeAmountExclVat;
    }

    public PaymentMethodInfo surchargeAmountExclVat(Double surchargeAmountExclVat) {
        this.setSurchargeAmountExclVat(surchargeAmountExclVat);
        return this;
    }

    public void setSurchargeAmountExclVat(Double surchargeAmountExclVat) {
        this.surchargeAmountExclVat = surchargeAmountExclVat;
    }

    public Double getSurchargeAmountVat() {
        return this.surchargeAmountVat;
    }

    public PaymentMethodInfo surchargeAmountVat(Double surchargeAmountVat) {
        this.setSurchargeAmountVat(surchargeAmountVat);
        return this;
    }

    public void setSurchargeAmountVat(Double surchargeAmountVat) {
        this.surchargeAmountVat = surchargeAmountVat;
    }

    public Double getSurchargeVatPercentage() {
        return this.surchargeVatPercentage;
    }

    public PaymentMethodInfo surchargeVatPercentage(Double surchargeVatPercentage) {
        this.setSurchargeVatPercentage(surchargeVatPercentage);
        return this;
    }

    public void setSurchargeVatPercentage(Double surchargeVatPercentage) {
        this.surchargeVatPercentage = surchargeVatPercentage;
    }

    public String getDescription() {
        return this.description;
    }

    public PaymentMethodInfo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodInfo)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethodInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodInfo{" +
            "id=" + getId() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", logo='" + getLogo() + "'" +
            ", supportsTokenisation='" + getSupportsTokenisation() + "'" +
            ", currencies='" + getCurrencies() + "'" +
            ", surchargeAmount=" + getSurchargeAmount() +
            ", surchargeAmountExclVat=" + getSurchargeAmountExclVat() +
            ", surchargeAmountVat=" + getSurchargeAmountVat() +
            ", surchargeVatPercentage=" + getSurchargeVatPercentage() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
