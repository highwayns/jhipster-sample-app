package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminUsers} entity.
 */
public class AdminUsersDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String user;

    @NotNull
    @Size(max = 50)
    private String pass;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 255)
    private String company;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 50)
    private String city;

    @NotNull
    @Size(max = 50)
    private String phone;

    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(max = 255)
    private String website;

    @NotNull
    private Integer fId;

    @NotNull
    private Integer order;

    @NotNull
    private YesNo isAdmin;

    @NotNull
    private Integer countryCode;

    @NotNull
    private YesNo verifiedAuthy;

    @NotNull
    @Size(max = 255)
    private String authyId;

    private IsoCountriesDTO countryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public YesNo getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(YesNo isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public YesNo getVerifiedAuthy() {
        return verifiedAuthy;
    }

    public void setVerifiedAuthy(YesNo verifiedAuthy) {
        this.verifiedAuthy = verifiedAuthy;
    }

    public String getAuthyId() {
        return authyId;
    }

    public void setAuthyId(String authyId) {
        this.authyId = authyId;
    }

    public IsoCountriesDTO getCountryId() {
        return countryId;
    }

    public void setCountryId(IsoCountriesDTO countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminUsersDTO)) {
            return false;
        }

        AdminUsersDTO adminUsersDTO = (AdminUsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminUsersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminUsersDTO{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", pass='" + getPass() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", company='" + getCompany() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", fId=" + getfId() +
            ", order=" + getOrder() +
            ", isAdmin='" + getIsAdmin() + "'" +
            ", countryCode=" + getCountryCode() +
            ", verifiedAuthy='" + getVerifiedAuthy() + "'" +
            ", authyId='" + getAuthyId() + "'" +
            ", countryId=" + getCountryId() +
            "}";
    }
}
