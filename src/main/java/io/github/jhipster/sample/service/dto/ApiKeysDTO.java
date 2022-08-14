package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.ApiKeys} entity.
 */
public class ApiKeysDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String key;

    @NotNull
    @Size(max = 255)
    private String secret;

    @NotNull
    private YesNo view;

    @NotNull
    private YesNo orders;

    @NotNull
    private YesNo withdraw;

    @NotNull
    private Long nonce;

    private SiteUsersDTO siteUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public YesNo getView() {
        return view;
    }

    public void setView(YesNo view) {
        this.view = view;
    }

    public YesNo getOrders() {
        return orders;
    }

    public void setOrders(YesNo orders) {
        this.orders = orders;
    }

    public YesNo getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(YesNo withdraw) {
        this.withdraw = withdraw;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public SiteUsersDTO getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUsersDTO siteUser) {
        this.siteUser = siteUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiKeysDTO)) {
            return false;
        }

        ApiKeysDTO apiKeysDTO = (ApiKeysDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apiKeysDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiKeysDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", secret='" + getSecret() + "'" +
            ", view='" + getView() + "'" +
            ", orders='" + getOrders() + "'" +
            ", withdraw='" + getWithdraw() + "'" +
            ", nonce=" + getNonce() +
            ", siteUser=" + getSiteUser() +
            "}";
    }
}
