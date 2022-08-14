package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.DailyReports} entity.
 */
public class DailyReportsDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private Double totalBtc;

    @NotNull
    private Double totalFiatUsd;

    @NotNull
    private Double openOrdersBtc;

    @NotNull
    private Double btcPerUser;

    @NotNull
    private Double transactionsBtc;

    @NotNull
    private Double avgTransactionSizeBtc;

    @NotNull
    private Double transactionVolumePerUser;

    @NotNull
    private Double totalFeesBtc;

    @NotNull
    private Double feesPerUserBtc;

    @NotNull
    private Double usdPerUser;

    @NotNull
    private Double grossProfitBtc;

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

    public Double getTotalBtc() {
        return totalBtc;
    }

    public void setTotalBtc(Double totalBtc) {
        this.totalBtc = totalBtc;
    }

    public Double getTotalFiatUsd() {
        return totalFiatUsd;
    }

    public void setTotalFiatUsd(Double totalFiatUsd) {
        this.totalFiatUsd = totalFiatUsd;
    }

    public Double getOpenOrdersBtc() {
        return openOrdersBtc;
    }

    public void setOpenOrdersBtc(Double openOrdersBtc) {
        this.openOrdersBtc = openOrdersBtc;
    }

    public Double getBtcPerUser() {
        return btcPerUser;
    }

    public void setBtcPerUser(Double btcPerUser) {
        this.btcPerUser = btcPerUser;
    }

    public Double getTransactionsBtc() {
        return transactionsBtc;
    }

    public void setTransactionsBtc(Double transactionsBtc) {
        this.transactionsBtc = transactionsBtc;
    }

    public Double getAvgTransactionSizeBtc() {
        return avgTransactionSizeBtc;
    }

    public void setAvgTransactionSizeBtc(Double avgTransactionSizeBtc) {
        this.avgTransactionSizeBtc = avgTransactionSizeBtc;
    }

    public Double getTransactionVolumePerUser() {
        return transactionVolumePerUser;
    }

    public void setTransactionVolumePerUser(Double transactionVolumePerUser) {
        this.transactionVolumePerUser = transactionVolumePerUser;
    }

    public Double getTotalFeesBtc() {
        return totalFeesBtc;
    }

    public void setTotalFeesBtc(Double totalFeesBtc) {
        this.totalFeesBtc = totalFeesBtc;
    }

    public Double getFeesPerUserBtc() {
        return feesPerUserBtc;
    }

    public void setFeesPerUserBtc(Double feesPerUserBtc) {
        this.feesPerUserBtc = feesPerUserBtc;
    }

    public Double getUsdPerUser() {
        return usdPerUser;
    }

    public void setUsdPerUser(Double usdPerUser) {
        this.usdPerUser = usdPerUser;
    }

    public Double getGrossProfitBtc() {
        return grossProfitBtc;
    }

    public void setGrossProfitBtc(Double grossProfitBtc) {
        this.grossProfitBtc = grossProfitBtc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyReportsDTO)) {
            return false;
        }

        DailyReportsDTO dailyReportsDTO = (DailyReportsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dailyReportsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyReportsDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", totalBtc=" + getTotalBtc() +
            ", totalFiatUsd=" + getTotalFiatUsd() +
            ", openOrdersBtc=" + getOpenOrdersBtc() +
            ", btcPerUser=" + getBtcPerUser() +
            ", transactionsBtc=" + getTransactionsBtc() +
            ", avgTransactionSizeBtc=" + getAvgTransactionSizeBtc() +
            ", transactionVolumePerUser=" + getTransactionVolumePerUser() +
            ", totalFeesBtc=" + getTotalFeesBtc() +
            ", feesPerUserBtc=" + getFeesPerUserBtc() +
            ", usdPerUser=" + getUsdPerUser() +
            ", grossProfitBtc=" + getGrossProfitBtc() +
            "}";
    }
}
