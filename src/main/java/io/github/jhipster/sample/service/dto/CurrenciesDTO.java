package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Currencies} entity.
 */
public class CurrenciesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3)
    private String currency;

    @NotNull
    @Size(max = 255)
    private String faSymbol;

    @NotNull
    private Long accountNumber;

    @NotNull
    @Size(max = 255)
    private String accountName;

    @NotNull
    private YesNo isActive;

    @NotNull
    @Size(max = 255)
    private String usdBid;

    @NotNull
    @Size(max = 255)
    private String usdAsk;

    @NotNull
    @Size(max = 255)
    private String nameEn;

    @NotNull
    @Size(max = 255)
    private String nameEs;

    @NotNull
    @Size(max = 255)
    private String nameRu;

    @NotNull
    @Size(max = 255)
    private String nameZh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFaSymbol() {
        return faSymbol;
    }

    public void setFaSymbol(String faSymbol) {
        this.faSymbol = faSymbol;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public YesNo getIsActive() {
        return isActive;
    }

    public void setIsActive(YesNo isActive) {
        this.isActive = isActive;
    }

    public String getUsdBid() {
        return usdBid;
    }

    public void setUsdBid(String usdBid) {
        this.usdBid = usdBid;
    }

    public String getUsdAsk() {
        return usdAsk;
    }

    public void setUsdAsk(String usdAsk) {
        this.usdAsk = usdAsk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrenciesDTO)) {
            return false;
        }

        CurrenciesDTO currenciesDTO = (CurrenciesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, currenciesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrenciesDTO{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", faSymbol='" + getFaSymbol() + "'" +
            ", accountNumber=" + getAccountNumber() +
            ", accountName='" + getAccountName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", usdBid='" + getUsdBid() + "'" +
            ", usdAsk='" + getUsdAsk() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", nameEs='" + getNameEs() + "'" +
            ", nameRu='" + getNameRu() + "'" +
            ", nameZh='" + getNameZh() + "'" +
            "}";
    }
}
