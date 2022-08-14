package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.BitcoinAddresses} entity.
 */
public class BitcoinAddressesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    private Instant date;

    @NotNull
    private YesNo systemAddress;

    @NotNull
    private YesNo hotWallet;

    @NotNull
    private YesNo warmWallet;

    private SiteUsersDTO siteUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public YesNo getSystemAddress() {
        return systemAddress;
    }

    public void setSystemAddress(YesNo systemAddress) {
        this.systemAddress = systemAddress;
    }

    public YesNo getHotWallet() {
        return hotWallet;
    }

    public void setHotWallet(YesNo hotWallet) {
        this.hotWallet = hotWallet;
    }

    public YesNo getWarmWallet() {
        return warmWallet;
    }

    public void setWarmWallet(YesNo warmWallet) {
        this.warmWallet = warmWallet;
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
        if (!(o instanceof BitcoinAddressesDTO)) {
            return false;
        }

        BitcoinAddressesDTO bitcoinAddressesDTO = (BitcoinAddressesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bitcoinAddressesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BitcoinAddressesDTO{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", date='" + getDate() + "'" +
            ", systemAddress='" + getSystemAddress() + "'" +
            ", hotWallet='" + getHotWallet() + "'" +
            ", warmWallet='" + getWarmWallet() + "'" +
            ", siteUser=" + getSiteUser() +
            "}";
    }
}
