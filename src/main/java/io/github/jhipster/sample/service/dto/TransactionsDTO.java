package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Transactions} entity.
 */
public class TransactionsDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private Double btc;

    @NotNull
    private Double btcPrice;

    @NotNull
    private Double fiat;

    @NotNull
    private Double fee;

    @NotNull
    private Double fee1;

    @NotNull
    private Double btcNet;

    @NotNull
    private Double btcNet1;

    @NotNull
    private Double btcBefore1;

    @NotNull
    private Double btcAfter1;

    @NotNull
    private Double fiatBefore1;

    @NotNull
    private Double fiatAfter1;

    @NotNull
    private Double btcBefore;

    @NotNull
    private Double btcAfter;

    @NotNull
    private Double fiatBefore;

    @NotNull
    private Double fiatAfter;

    @NotNull
    private Double feeLevel;

    @NotNull
    private Double feeLevel1;

    @NotNull
    private Double origBtcPrice;

    @NotNull
    private Double conversionFee;

    @NotNull
    private Double convertAmount;

    @NotNull
    private Double convertRateGiven;

    @NotNull
    private Double convertSystemRate;

    @NotNull
    private YesNo conversion;

    @NotNull
    private Double bidAtTransaction;

    @NotNull
    private Double askAtTransaction;

    @NotNull
    private YesNo factored;

    private SiteUsersDTO siteUser;

    private SiteUsersDTO siteUser1;

    private TransactionTypesDTO transactionType;

    private TransactionTypesDTO transactionType1;

    private CurrenciesDTO currency1;

    private CurrenciesDTO convertFromCurrency;

    private CurrenciesDTO convertToCurrency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getBtc() {
        return btc;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public Double getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice(Double btcPrice) {
        this.btcPrice = btcPrice;
    }

    public Double getFiat() {
        return fiat;
    }

    public void setFiat(Double fiat) {
        this.fiat = fiat;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFee1() {
        return fee1;
    }

    public void setFee1(Double fee1) {
        this.fee1 = fee1;
    }

    public Double getBtcNet() {
        return btcNet;
    }

    public void setBtcNet(Double btcNet) {
        this.btcNet = btcNet;
    }

    public Double getBtcNet1() {
        return btcNet1;
    }

    public void setBtcNet1(Double btcNet1) {
        this.btcNet1 = btcNet1;
    }

    public Double getBtcBefore1() {
        return btcBefore1;
    }

    public void setBtcBefore1(Double btcBefore1) {
        this.btcBefore1 = btcBefore1;
    }

    public Double getBtcAfter1() {
        return btcAfter1;
    }

    public void setBtcAfter1(Double btcAfter1) {
        this.btcAfter1 = btcAfter1;
    }

    public Double getFiatBefore1() {
        return fiatBefore1;
    }

    public void setFiatBefore1(Double fiatBefore1) {
        this.fiatBefore1 = fiatBefore1;
    }

    public Double getFiatAfter1() {
        return fiatAfter1;
    }

    public void setFiatAfter1(Double fiatAfter1) {
        this.fiatAfter1 = fiatAfter1;
    }

    public Double getBtcBefore() {
        return btcBefore;
    }

    public void setBtcBefore(Double btcBefore) {
        this.btcBefore = btcBefore;
    }

    public Double getBtcAfter() {
        return btcAfter;
    }

    public void setBtcAfter(Double btcAfter) {
        this.btcAfter = btcAfter;
    }

    public Double getFiatBefore() {
        return fiatBefore;
    }

    public void setFiatBefore(Double fiatBefore) {
        this.fiatBefore = fiatBefore;
    }

    public Double getFiatAfter() {
        return fiatAfter;
    }

    public void setFiatAfter(Double fiatAfter) {
        this.fiatAfter = fiatAfter;
    }

    public Double getFeeLevel() {
        return feeLevel;
    }

    public void setFeeLevel(Double feeLevel) {
        this.feeLevel = feeLevel;
    }

    public Double getFeeLevel1() {
        return feeLevel1;
    }

    public void setFeeLevel1(Double feeLevel1) {
        this.feeLevel1 = feeLevel1;
    }

    public Double getOrigBtcPrice() {
        return origBtcPrice;
    }

    public void setOrigBtcPrice(Double origBtcPrice) {
        this.origBtcPrice = origBtcPrice;
    }

    public Double getConversionFee() {
        return conversionFee;
    }

    public void setConversionFee(Double conversionFee) {
        this.conversionFee = conversionFee;
    }

    public Double getConvertAmount() {
        return convertAmount;
    }

    public void setConvertAmount(Double convertAmount) {
        this.convertAmount = convertAmount;
    }

    public Double getConvertRateGiven() {
        return convertRateGiven;
    }

    public void setConvertRateGiven(Double convertRateGiven) {
        this.convertRateGiven = convertRateGiven;
    }

    public Double getConvertSystemRate() {
        return convertSystemRate;
    }

    public void setConvertSystemRate(Double convertSystemRate) {
        this.convertSystemRate = convertSystemRate;
    }

    public YesNo getConversion() {
        return conversion;
    }

    public void setConversion(YesNo conversion) {
        this.conversion = conversion;
    }

    public Double getBidAtTransaction() {
        return bidAtTransaction;
    }

    public void setBidAtTransaction(Double bidAtTransaction) {
        this.bidAtTransaction = bidAtTransaction;
    }

    public Double getAskAtTransaction() {
        return askAtTransaction;
    }

    public void setAskAtTransaction(Double askAtTransaction) {
        this.askAtTransaction = askAtTransaction;
    }

    public YesNo getFactored() {
        return factored;
    }

    public void setFactored(YesNo factored) {
        this.factored = factored;
    }

    public SiteUsersDTO getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUsersDTO siteUser) {
        this.siteUser = siteUser;
    }

    public SiteUsersDTO getSiteUser1() {
        return siteUser1;
    }

    public void setSiteUser1(SiteUsersDTO siteUser1) {
        this.siteUser1 = siteUser1;
    }

    public TransactionTypesDTO getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypesDTO transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionTypesDTO getTransactionType1() {
        return transactionType1;
    }

    public void setTransactionType1(TransactionTypesDTO transactionType1) {
        this.transactionType1 = transactionType1;
    }

    public CurrenciesDTO getCurrency1() {
        return currency1;
    }

    public void setCurrency1(CurrenciesDTO currency1) {
        this.currency1 = currency1;
    }

    public CurrenciesDTO getConvertFromCurrency() {
        return convertFromCurrency;
    }

    public void setConvertFromCurrency(CurrenciesDTO convertFromCurrency) {
        this.convertFromCurrency = convertFromCurrency;
    }

    public CurrenciesDTO getConvertToCurrency() {
        return convertToCurrency;
    }

    public void setConvertToCurrency(CurrenciesDTO convertToCurrency) {
        this.convertToCurrency = convertToCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionsDTO)) {
            return false;
        }

        TransactionsDTO transactionsDTO = (TransactionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionsDTO{" +
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
            ", siteUser=" + getSiteUser() +
            ", siteUser1=" + getSiteUser1() +
            ", transactionType=" + getTransactionType() +
            ", transactionType1=" + getTransactionType1() +
            ", currency1=" + getCurrency1() +
            ", convertFromCurrency=" + getConvertFromCurrency() +
            ", convertToCurrency=" + getConvertToCurrency() +
            "}";
    }
}
