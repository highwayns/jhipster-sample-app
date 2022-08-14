package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.MonthlyReports} entity.
 */
public class MonthlyReportsDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

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
        if (!(o instanceof MonthlyReportsDTO)) {
            return false;
        }

        MonthlyReportsDTO monthlyReportsDTO = (MonthlyReportsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, monthlyReportsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlyReportsDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", transactionsBtc=" + getTransactionsBtc() +
            ", avgTransactionSizeBtc=" + getAvgTransactionSizeBtc() +
            ", transactionVolumePerUser=" + getTransactionVolumePerUser() +
            ", totalFeesBtc=" + getTotalFeesBtc() +
            ", feesPerUserBtc=" + getFeesPerUserBtc() +
            ", grossProfitBtc=" + getGrossProfitBtc() +
            "}";
    }
}
