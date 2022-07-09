package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.Country;
import io.github.jhipster.sample.domain.enumeration.PhoneNumberType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "phone_number_1")
    private String phoneNumber1;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_number_1_type")
    private PhoneNumberType phoneNumber1Type;

    @Column(name = "phone_number_2")
    private String phoneNumber2;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_number_2_type")
    private PhoneNumberType phoneNumber2Type;

    @Column(name = "organisation")
    private String organisation;

    @Column(name = "department")
    private String department;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Address title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Address firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Address middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Address lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getCountry() {
        return this.country;
    }

    public Address country(Country country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public Address addressLine1(String addressLine1) {
        this.setAddressLine1(addressLine1);
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public Address addressLine2(String addressLine2) {
        this.setAddressLine2(addressLine2);
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Address zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return this.stateProvince;
    }

    public Address stateProvince(String stateProvince) {
        this.setStateProvince(stateProvince);
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPhoneNumber1() {
        return this.phoneNumber1;
    }

    public Address phoneNumber1(String phoneNumber1) {
        this.setPhoneNumber1(phoneNumber1);
        return this;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public PhoneNumberType getPhoneNumber1Type() {
        return this.phoneNumber1Type;
    }

    public Address phoneNumber1Type(PhoneNumberType phoneNumber1Type) {
        this.setPhoneNumber1Type(phoneNumber1Type);
        return this;
    }

    public void setPhoneNumber1Type(PhoneNumberType phoneNumber1Type) {
        this.phoneNumber1Type = phoneNumber1Type;
    }

    public String getPhoneNumber2() {
        return this.phoneNumber2;
    }

    public Address phoneNumber2(String phoneNumber2) {
        this.setPhoneNumber2(phoneNumber2);
        return this;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public PhoneNumberType getPhoneNumber2Type() {
        return this.phoneNumber2Type;
    }

    public Address phoneNumber2Type(PhoneNumberType phoneNumber2Type) {
        this.setPhoneNumber2Type(phoneNumber2Type);
        return this;
    }

    public void setPhoneNumber2Type(PhoneNumberType phoneNumber2Type) {
        this.phoneNumber2Type = phoneNumber2Type;
    }

    public String getOrganisation() {
        return this.organisation;
    }

    public Address organisation(String organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getDepartment() {
        return this.department;
    }

    public Address department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", country='" + getCountry() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", phoneNumber1='" + getPhoneNumber1() + "'" +
            ", phoneNumber1Type='" + getPhoneNumber1Type() + "'" +
            ", phoneNumber2='" + getPhoneNumber2() + "'" +
            ", phoneNumber2Type='" + getPhoneNumber2Type() + "'" +
            ", organisation='" + getOrganisation() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }
}
