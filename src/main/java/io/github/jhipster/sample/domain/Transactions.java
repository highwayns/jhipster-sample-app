package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transactions.
 */
@Entity
@Table(name = "transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "btc", nullable = false)
    private Double btc;

    @NotNull
    @Column(name = "btc_price", nullable = false)
    private Double btcPrice;

    @NotNull
    @Column(name = "fiat", nullable = false)
    private Double fiat;

    @NotNull
    @Column(name = "fee", nullable = false)
    private Double fee;

    @NotNull
    @Column(name = "fee_1", nullable = false)
    private Double fee1;

    @NotNull
    @Column(name = "btc_net", nullable = false)
    private Double btcNet;

    @NotNull
    @Column(name = "btc_net_1", nullable = false)
    private Double btcNet1;

    @NotNull
    @Column(name = "btc_before_1", nullable = false)
    private Double btcBefore1;

    @NotNull
    @Column(name = "btc_after_1", nullable = false)
    private Double btcAfter1;

    @NotNull
    @Column(name = "fiat_before_1", nullable = false)
    private Double fiatBefore1;

    @NotNull
    @Column(name = "fiat_after_1", nullable = false)
    private Double fiatAfter1;

    @NotNull
    @Column(name = "btc_before", nullable = false)
    private Double btcBefore;

    @NotNull
    @Column(name = "btc_after", nullable = false)
    private Double btcAfter;

    @NotNull
    @Column(name = "fiat_before", nullable = false)
    private Double fiatBefore;

    @NotNull
    @Column(name = "fiat_after", nullable = false)
    private Double fiatAfter;

    @NotNull
    @Column(name = "fee_level", nullable = false)
    private Double feeLevel;

    @NotNull
    @Column(name = "fee_level_1", nullable = false)
    private Double feeLevel1;

    @NotNull
    @Column(name = "orig_btc_price", nullable = false)
    private Double origBtcPrice;

    @NotNull
    @Column(name = "conversion_fee", nullable = false)
    private Double conversionFee;

    @NotNull
    @Column(name = "convert_amount", nullable = false)
    private Double convertAmount;

    @NotNull
    @Column(name = "convert_rate_given", nullable = false)
    private Double convertRateGiven;

    @NotNull
    @Column(name = "convert_system_rate", nullable = false)
    private Double convertSystemRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "conversion", nullable = false)
    private YesNo conversion;

    @NotNull
    @Column(name = "bid_at_transaction", nullable = false)
    private Double bidAtTransaction;

    @NotNull
    @Column(name = "ask_at_transaction", nullable = false)
    private Double askAtTransaction;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "factored", nullable = false)
    private YesNo factored;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser1;

    @OneToOne
    @JoinColumn(unique = true)
    private TransactionTypes transactionType;

    @OneToOne
    @JoinColumn(unique = true)
    private TransactionTypes transactionType1;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies currency1;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies convertFromCurrency;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies convertToCurrency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transactions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Transactions date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getBtc() {
        return this.btc;
    }

    public Transactions btc(Double btc) {
        this.setBtc(btc);
        return this;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public Double getBtcPrice() {
        return this.btcPrice;
    }

    public Transactions btcPrice(Double btcPrice) {
        this.setBtcPrice(btcPrice);
        return this;
    }

    public void setBtcPrice(Double btcPrice) {
        this.btcPrice = btcPrice;
    }

    public Double getFiat() {
        return this.fiat;
    }

    public Transactions fiat(Double fiat) {
        this.setFiat(fiat);
        return this;
    }

    public void setFiat(Double fiat) {
        this.fiat = fiat;
    }

    public Double getFee() {
        return this.fee;
    }

    public Transactions fee(Double fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFee1() {
        return this.fee1;
    }

    public Transactions fee1(Double fee1) {
        this.setFee1(fee1);
        return this;
    }

    public void setFee1(Double fee1) {
        this.fee1 = fee1;
    }

    public Double getBtcNet() {
        return this.btcNet;
    }

    public Transactions btcNet(Double btcNet) {
        this.setBtcNet(btcNet);
        return this;
    }

    public void setBtcNet(Double btcNet) {
        this.btcNet = btcNet;
    }

    public Double getBtcNet1() {
        return this.btcNet1;
    }

    public Transactions btcNet1(Double btcNet1) {
        this.setBtcNet1(btcNet1);
        return this;
    }

    public void setBtcNet1(Double btcNet1) {
        this.btcNet1 = btcNet1;
    }

    public Double getBtcBefore1() {
        return this.btcBefore1;
    }

    public Transactions btcBefore1(Double btcBefore1) {
        this.setBtcBefore1(btcBefore1);
        return this;
    }

    public void setBtcBefore1(Double btcBefore1) {
        this.btcBefore1 = btcBefore1;
    }

    public Double getBtcAfter1() {
        return this.btcAfter1;
    }

    public Transactions btcAfter1(Double btcAfter1) {
        this.setBtcAfter1(btcAfter1);
        return this;
    }

    public void setBtcAfter1(Double btcAfter1) {
        this.btcAfter1 = btcAfter1;
    }

    public Double getFiatBefore1() {
        return this.fiatBefore1;
    }

    public Transactions fiatBefore1(Double fiatBefore1) {
        this.setFiatBefore1(fiatBefore1);
        return this;
    }

    public void setFiatBefore1(Double fiatBefore1) {
        this.fiatBefore1 = fiatBefore1;
    }

    public Double getFiatAfter1() {
        return this.fiatAfter1;
    }

    public Transactions fiatAfter1(Double fiatAfter1) {
        this.setFiatAfter1(fiatAfter1);
        return this;
    }

    public void setFiatAfter1(Double fiatAfter1) {
        this.fiatAfter1 = fiatAfter1;
    }

    public Double getBtcBefore() {
        return this.btcBefore;
    }

    public Transactions btcBefore(Double btcBefore) {
        this.setBtcBefore(btcBefore);
        return this;
    }

    public void setBtcBefore(Double btcBefore) {
        this.btcBefore = btcBefore;
    }

    public Double getBtcAfter() {
        return this.btcAfter;
    }

    public Transactions btcAfter(Double btcAfter) {
        this.setBtcAfter(btcAfter);
        return this;
    }

    public void setBtcAfter(Double btcAfter) {
        this.btcAfter = btcAfter;
    }

    public Double getFiatBefore() {
        return this.fiatBefore;
    }

    public Transactions fiatBefore(Double fiatBefore) {
        this.setFiatBefore(fiatBefore);
        return this;
    }

    public void setFiatBefore(Double fiatBefore) {
        this.fiatBefore = fiatBefore;
    }

    public Double getFiatAfter() {
        return this.fiatAfter;
    }

    public Transactions fiatAfter(Double fiatAfter) {
        this.setFiatAfter(fiatAfter);
        return this;
    }

    public void setFiatAfter(Double fiatAfter) {
        this.fiatAfter = fiatAfter;
    }

    public Double getFeeLevel() {
        return this.feeLevel;
    }

    public Transactions feeLevel(Double feeLevel) {
        this.setFeeLevel(feeLevel);
        return this;
    }

    public void setFeeLevel(Double feeLevel) {
        this.feeLevel = feeLevel;
    }

    public Double getFeeLevel1() {
        return this.feeLevel1;
    }

    public Transactions feeLevel1(Double feeLevel1) {
        this.setFeeLevel1(feeLevel1);
        return this;
    }

    public void setFeeLevel1(Double feeLevel1) {
        this.feeLevel1 = feeLevel1;
    }

    public Double getOrigBtcPrice() {
        return this.origBtcPrice;
    }

    public Transactions origBtcPrice(Double origBtcPrice) {
        this.setOrigBtcPrice(origBtcPrice);
        return this;
    }

    public void setOrigBtcPrice(Double origBtcPrice) {
        this.origBtcPrice = origBtcPrice;
    }

    public Double getConversionFee() {
        return this.conversionFee;
    }

    public Transactions conversionFee(Double conversionFee) {
        this.setConversionFee(conversionFee);
        return this;
    }

    public void setConversionFee(Double conversionFee) {
        this.conversionFee = conversionFee;
    }

    public Double getConvertAmount() {
        return this.convertAmount;
    }

    public Transactions convertAmount(Double convertAmount) {
        this.setConvertAmount(convertAmount);
        return this;
    }

    public void setConvertAmount(Double convertAmount) {
        this.convertAmount = convertAmount;
    }

    public Double getConvertRateGiven() {
        return this.convertRateGiven;
    }

    public Transactions convertRateGiven(Double convertRateGiven) {
        this.setConvertRateGiven(convertRateGiven);
        return this;
    }

    public void setConvertRateGiven(Double convertRateGiven) {
        this.convertRateGiven = convertRateGiven;
    }

    public Double getConvertSystemRate() {
        return this.convertSystemRate;
    }

    public Transactions convertSystemRate(Double convertSystemRate) {
        this.setConvertSystemRate(convertSystemRate);
        return this;
    }

    public void setConvertSystemRate(Double convertSystemRate) {
        this.convertSystemRate = convertSystemRate;
    }

    public YesNo getConversion() {
        return this.conversion;
    }

    public Transactions conversion(YesNo conversion) {
        this.setConversion(conversion);
        return this;
    }

    public void setConversion(YesNo conversion) {
        this.conversion = conversion;
    }

    public Double getBidAtTransaction() {
        return this.bidAtTransaction;
    }

    public Transactions bidAtTransaction(Double bidAtTransaction) {
        this.setBidAtTransaction(bidAtTransaction);
        return this;
    }

    public void setBidAtTransaction(Double bidAtTransaction) {
        this.bidAtTransaction = bidAtTransaction;
    }

    public Double getAskAtTransaction() {
        return this.askAtTransaction;
    }

    public Transactions askAtTransaction(Double askAtTransaction) {
        this.setAskAtTransaction(askAtTransaction);
        return this;
    }

    public void setAskAtTransaction(Double askAtTransaction) {
        this.askAtTransaction = askAtTransaction;
    }

    public YesNo getFactored() {
        return this.factored;
    }

    public Transactions factored(YesNo factored) {
        this.setFactored(factored);
        return this;
    }

    public void setFactored(YesNo factored) {
        this.factored = factored;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public Transactions siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    public SiteUsers getSiteUser1() {
        return this.siteUser1;
    }

    public void setSiteUser1(SiteUsers siteUsers) {
        this.siteUser1 = siteUsers;
    }

    public Transactions siteUser1(SiteUsers siteUsers) {
        this.setSiteUser1(siteUsers);
        return this;
    }

    public TransactionTypes getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(TransactionTypes transactionTypes) {
        this.transactionType = transactionTypes;
    }

    public Transactions transactionType(TransactionTypes transactionTypes) {
        this.setTransactionType(transactionTypes);
        return this;
    }

    public TransactionTypes getTransactionType1() {
        return this.transactionType1;
    }

    public void setTransactionType1(TransactionTypes transactionTypes) {
        this.transactionType1 = transactionTypes;
    }

    public Transactions transactionType1(TransactionTypes transactionTypes) {
        this.setTransactionType1(transactionTypes);
        return this;
    }

    public Currencies getCurrency1() {
        return this.currency1;
    }

    public void setCurrency1(Currencies currencies) {
        this.currency1 = currencies;
    }

    public Transactions currency1(Currencies currencies) {
        this.setCurrency1(currencies);
        return this;
    }

    public Currencies getConvertFromCurrency() {
        return this.convertFromCurrency;
    }

    public void setConvertFromCurrency(Currencies currencies) {
        this.convertFromCurrency = currencies;
    }

    public Transactions convertFromCurrency(Currencies currencies) {
        this.setConvertFromCurrency(currencies);
        return this;
    }

    public Currencies getConvertToCurrency() {
        return this.convertToCurrency;
    }

    public void setConvertToCurrency(Currencies currencies) {
        this.convertToCurrency = currencies;
    }

    public Transactions convertToCurrency(Currencies currencies) {
        this.setConvertToCurrency(currencies);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transactions)) {
            return false;
        }
        return id != null && id.equals(((Transactions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transactions{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", btc=" + getBtc() +
            ", btcPrice=" + getBtcPrice() +
            ", fiat=" + getFiat() +
            ", fee=" + getFee() +
            ", fee1=" + getFee1() +
            ", btcNet=" + getBtcNet() +
            ", btcNet1=" + getBtcNet1() +
            ", btcBefore1=" + getBtcBefore1() +
            ", btcAfter1=" + getBtcAfter1() +
            ", fiatBefore1=" + getFiatBefore1() +
            ", fiatAfter1=" + getFiatAfter1() +
            ", btcBefore=" + getBtcBefore() +
            ", btcAfter=" + getBtcAfter() +
            ", fiatBefore=" + getFiatBefore() +
            ", fiatAfter=" + getFiatAfter() +
            ", feeLevel=" + getFeeLevel() +
            ", feeLevel1=" + getFeeLevel1() +
            ", origBtcPrice=" + getOrigBtcPrice() +
            ", conversionFee=" + getConversionFee() +
            ", convertAmount=" + getConvertAmount() +
            ", convertRateGiven=" + getConvertRateGiven() +
            ", convertSystemRate=" + getConvertSystemRate() +
            ", conversion='" + getConversion() + "'" +
            ", bidAtTransaction=" + getBidAtTransaction() +
            ", askAtTransaction=" + getAskAtTransaction() +
            ", factored='" + getFactored() + "'" +
            "}";
    }
}
