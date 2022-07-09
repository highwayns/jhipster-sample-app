package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.Gender;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Identity.
 */
@Entity
@Table(name = "identity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Identity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "debtor_id")
    private String debtorId;

    @Column(name = "email_address")
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "chamber_of_commerce_number")
    private String chamberOfCommerceNumber;

    @Column(name = "vat_number")
    private String vatNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Identity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebtorId() {
        return this.debtorId;
    }

    public Identity debtorId(String debtorId) {
        this.setDebtorId(debtorId);
        return this;
    }

    public void setDebtorId(String debtorId) {
        this.debtorId = debtorId;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Identity emailAddress(String emailAddress) {
        this.setEmailAddress(emailAddress);
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Identity gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Identity dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }

    public Identity socialSecurityNumber(String socialSecurityNumber) {
        this.setSocialSecurityNumber(socialSecurityNumber);
        return this;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getChamberOfCommerceNumber() {
        return this.chamberOfCommerceNumber;
    }

    public Identity chamberOfCommerceNumber(String chamberOfCommerceNumber) {
        this.setChamberOfCommerceNumber(chamberOfCommerceNumber);
        return this;
    }

    public void setChamberOfCommerceNumber(String chamberOfCommerceNumber) {
        this.chamberOfCommerceNumber = chamberOfCommerceNumber;
    }

    public String getVatNumber() {
        return this.vatNumber;
    }

    public Identity vatNumber(String vatNumber) {
        this.setVatNumber(vatNumber);
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Identity)) {
            return false;
        }
        return id != null && id.equals(((Identity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Identity{" +
            "id=" + getId() +
            ", debtorId='" + getDebtorId() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", socialSecurityNumber='" + getSocialSecurityNumber() + "'" +
            ", chamberOfCommerceNumber='" + getChamberOfCommerceNumber() + "'" +
            ", vatNumber='" + getVatNumber() + "'" +
            "}";
    }
}
