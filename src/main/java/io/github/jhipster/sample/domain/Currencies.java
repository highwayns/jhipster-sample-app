package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Currencies.
 */
@Entity
@Table(name = "currencies")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Currencies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @NotNull
    @Size(max = 255)
    @Column(name = "fa_symbol", length = 255, nullable = false)
    private String faSymbol;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @NotNull
    @Size(max = 255)
    @Column(name = "account_name", length = 255, nullable = false)
    private String accountName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_active", nullable = false)
    private YesNo isActive;

    @NotNull
    @Size(max = 255)
    @Column(name = "usd_bid", length = 255, nullable = false)
    private String usdBid;

    @NotNull
    @Size(max = 255)
    @Column(name = "usd_ask", length = 255, nullable = false)
    private String usdAsk;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_en", length = 255, nullable = false)
    private String nameEn;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_es", length = 255, nullable = false)
    private String nameEs;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_ru", length = 255, nullable = false)
    private String nameRu;

    @NotNull
    @Size(max = 255)
    @Column(name = "name_zh", length = 255, nullable = false)
    private String nameZh;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Currencies id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Currencies currency(String currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFaSymbol() {
        return this.faSymbol;
    }

    public Currencies faSymbol(String faSymbol) {
        this.setFaSymbol(faSymbol);
        return this;
    }

    public void setFaSymbol(String faSymbol) {
        this.faSymbol = faSymbol;
    }

    public Long getAccountNumber() {
        return this.accountNumber;
    }

    public Currencies accountNumber(Long accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public Currencies accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public YesNo getIsActive() {
        return this.isActive;
    }

    public Currencies isActive(YesNo isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(YesNo isActive) {
        this.isActive = isActive;
    }

    public String getUsdBid() {
        return this.usdBid;
    }

    public Currencies usdBid(String usdBid) {
        this.setUsdBid(usdBid);
        return this;
    }

    public void setUsdBid(String usdBid) {
        this.usdBid = usdBid;
    }

    public String getUsdAsk() {
        return this.usdAsk;
    }

    public Currencies usdAsk(String usdAsk) {
        this.setUsdAsk(usdAsk);
        return this;
    }

    public void setUsdAsk(String usdAsk) {
        this.usdAsk = usdAsk;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Currencies nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEs() {
        return this.nameEs;
    }

    public Currencies nameEs(String nameEs) {
        this.setNameEs(nameEs);
        return this;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    public String getNameRu() {
        return this.nameRu;
    }

    public Currencies nameRu(String nameRu) {
        this.setNameRu(nameRu);
        return this;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameZh() {
        return this.nameZh;
    }

    public Currencies nameZh(String nameZh) {
        this.setNameZh(nameZh);
        return this;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currencies)) {
            return false;
        }
        return id != null && id.equals(((Currencies) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currencies{" +
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
