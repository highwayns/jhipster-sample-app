package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApiKeys.
 */
@Entity
@Table(name = "api_keys")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApiKeys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "key", length = 255, nullable = false, unique = true)
    private String key;

    @NotNull
    @Size(max = 255)
    @Column(name = "secret", length = 255, nullable = false)
    private String secret;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "view", nullable = false)
    private YesNo view;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "orders", nullable = false)
    private YesNo orders;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "withdraw", nullable = false)
    private YesNo withdraw;

    @NotNull
    @Column(name = "nonce", nullable = false)
    private Long nonce;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApiKeys id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public ApiKeys key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return this.secret;
    }

    public ApiKeys secret(String secret) {
        this.setSecret(secret);
        return this;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public YesNo getView() {
        return this.view;
    }

    public ApiKeys view(YesNo view) {
        this.setView(view);
        return this;
    }

    public void setView(YesNo view) {
        this.view = view;
    }

    public YesNo getOrders() {
        return this.orders;
    }

    public ApiKeys orders(YesNo orders) {
        this.setOrders(orders);
        return this;
    }

    public void setOrders(YesNo orders) {
        this.orders = orders;
    }

    public YesNo getWithdraw() {
        return this.withdraw;
    }

    public ApiKeys withdraw(YesNo withdraw) {
        this.setWithdraw(withdraw);
        return this;
    }

    public void setWithdraw(YesNo withdraw) {
        this.withdraw = withdraw;
    }

    public Long getNonce() {
        return this.nonce;
    }

    public ApiKeys nonce(Long nonce) {
        this.setNonce(nonce);
        return this;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public ApiKeys siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiKeys)) {
            return false;
        }
        return id != null && id.equals(((ApiKeys) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiKeys{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", secret='" + getSecret() + "'" +
            ", view='" + getView() + "'" +
            ", orders='" + getOrders() + "'" +
            ", withdraw='" + getWithdraw() + "'" +
            ", nonce=" + getNonce() +
            "}";
    }
}
