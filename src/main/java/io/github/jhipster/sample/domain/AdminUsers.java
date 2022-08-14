package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminUsers.
 */
@Entity
@Table(name = "admin_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "jhi_user", length = 50, nullable = false)
    private String user;

    @NotNull
    @Size(max = 50)
    @Column(name = "pass", length = 50, nullable = false)
    private String pass;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @Column(name = "company", length = 255, nullable = false)
    private String company;

    @NotNull
    @Size(max = 255)
    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @NotNull
    @Size(max = 50)
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @NotNull
    @Size(max = 50)
    @Column(name = "phone", length = 50, nullable = false)
    private String phone;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @NotNull
    @Size(max = 255)
    @Column(name = "website", length = 255, nullable = false)
    private String website;

    @NotNull
    @Column(name = "f_id", nullable = false)
    private Integer fId;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_admin", nullable = false)
    private YesNo isAdmin;

    @NotNull
    @Column(name = "country_code", nullable = false)
    private Integer countryCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "verified_authy", nullable = false)
    private YesNo verifiedAuthy;

    @NotNull
    @Size(max = 255)
    @Column(name = "authy_id", length = 255, nullable = false)
    private String authyId;

    @OneToOne
    @JoinColumn(unique = true)
    private IsoCountries countryId;

    @OneToMany(mappedBy = "userId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "controlId", "userId" }, allowSetters = true)
    private Set<AdminOrder> adminOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminUsers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public AdminUsers user(String user) {
        this.setUser(user);
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return this.pass;
    }

    public AdminUsers pass(String pass) {
        this.setPass(pass);
        return this;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public AdminUsers firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AdminUsers lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return this.company;
    }

    public AdminUsers company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return this.address;
    }

    public AdminUsers address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public AdminUsers city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return this.phone;
    }

    public AdminUsers phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public AdminUsers email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return this.website;
    }

    public AdminUsers website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getfId() {
        return this.fId;
    }

    public AdminUsers fId(Integer fId) {
        this.setfId(fId);
        return this;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public Integer getOrder() {
        return this.order;
    }

    public AdminUsers order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public YesNo getIsAdmin() {
        return this.isAdmin;
    }

    public AdminUsers isAdmin(YesNo isAdmin) {
        this.setIsAdmin(isAdmin);
        return this;
    }

    public void setIsAdmin(YesNo isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getCountryCode() {
        return this.countryCode;
    }

    public AdminUsers countryCode(Integer countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public YesNo getVerifiedAuthy() {
        return this.verifiedAuthy;
    }

    public AdminUsers verifiedAuthy(YesNo verifiedAuthy) {
        this.setVerifiedAuthy(verifiedAuthy);
        return this;
    }

    public void setVerifiedAuthy(YesNo verifiedAuthy) {
        this.verifiedAuthy = verifiedAuthy;
    }

    public String getAuthyId() {
        return this.authyId;
    }

    public AdminUsers authyId(String authyId) {
        this.setAuthyId(authyId);
        return this;
    }

    public void setAuthyId(String authyId) {
        this.authyId = authyId;
    }

    public IsoCountries getCountryId() {
        return this.countryId;
    }

    public void setCountryId(IsoCountries isoCountries) {
        this.countryId = isoCountries;
    }

    public AdminUsers countryId(IsoCountries isoCountries) {
        this.setCountryId(isoCountries);
        return this;
    }

    public Set<AdminOrder> getAdminOrders() {
        return this.adminOrders;
    }

    public void setAdminOrders(Set<AdminOrder> adminOrders) {
        if (this.adminOrders != null) {
            this.adminOrders.forEach(i -> i.setUserId(null));
        }
        if (adminOrders != null) {
            adminOrders.forEach(i -> i.setUserId(this));
        }
        this.adminOrders = adminOrders;
    }

    public AdminUsers adminOrders(Set<AdminOrder> adminOrders) {
        this.setAdminOrders(adminOrders);
        return this;
    }

    public AdminUsers addAdminOrder(AdminOrder adminOrder) {
        this.adminOrders.add(adminOrder);
        adminOrder.setUserId(this);
        return this;
    }

    public AdminUsers removeAdminOrder(AdminOrder adminOrder) {
        this.adminOrders.remove(adminOrder);
        adminOrder.setUserId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminUsers)) {
            return false;
        }
        return id != null && id.equals(((AdminUsers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminUsers{" +
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
            "}";
    }
}
