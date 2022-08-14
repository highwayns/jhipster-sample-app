package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Status} entity.
 */
public class StatusDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant lastSweep;

    @NotNull
    private Double deficitBtc;

    @NotNull
    private Double hotWalletBtc;

    @NotNull
    private Double warmWalletBtc;

    @NotNull
    private Double totalBtc;

    @NotNull
    private Double receivedBtcPending;

    @NotNull
    private Double pendingWithdrawals;

    @NotNull
    @Size(max = 255)
    private String tradingStatus;

    @NotNull
    @Size(max = 255)
    private String withdrawalsStatus;

    @NotNull
    private Double dbVersion;

    @NotNull
    private Instant cronDailyStats;

    @NotNull
    private Instant cronGetStats;

    @NotNull
    private Instant cronMaintenance;

    @NotNull
    private Instant cronMonthlyStats;

    @NotNull
    private Instant cronReceiveBitcoin;

    @NotNull
    private Instant cronSendBitcoin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLastSweep() {
        return lastSweep;
    }

    public void setLastSweep(Instant lastSweep) {
        this.lastSweep = lastSweep;
    }

    public Double getDeficitBtc() {
        return deficitBtc;
    }

    public void setDeficitBtc(Double deficitBtc) {
        this.deficitBtc = deficitBtc;
    }

    public Double getHotWalletBtc() {
        return hotWalletBtc;
    }

    public void setHotWalletBtc(Double hotWalletBtc) {
        this.hotWalletBtc = hotWalletBtc;
    }

    public Double getWarmWalletBtc() {
        return warmWalletBtc;
    }

    public void setWarmWalletBtc(Double warmWalletBtc) {
        this.warmWalletBtc = warmWalletBtc;
    }

    public Double getTotalBtc() {
        return totalBtc;
    }

    public void setTotalBtc(Double totalBtc) {
        this.totalBtc = totalBtc;
    }

    public Double getReceivedBtcPending() {
        return receivedBtcPending;
    }

    public void setReceivedBtcPending(Double receivedBtcPending) {
        this.receivedBtcPending = receivedBtcPending;
    }

    public Double getPendingWithdrawals() {
        return pendingWithdrawals;
    }

    public void setPendingWithdrawals(Double pendingWithdrawals) {
        this.pendingWithdrawals = pendingWithdrawals;
    }

    public String getTradingStatus() {
        return tradingStatus;
    }

    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public String getWithdrawalsStatus() {
        return withdrawalsStatus;
    }

    public void setWithdrawalsStatus(String withdrawalsStatus) {
        this.withdrawalsStatus = withdrawalsStatus;
    }

    public Double getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(Double dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Instant getCronDailyStats() {
        return cronDailyStats;
    }

    public void setCronDailyStats(Instant cronDailyStats) {
        this.cronDailyStats = cronDailyStats;
    }

    public Instant getCronGetStats() {
        return cronGetStats;
    }

    public void setCronGetStats(Instant cronGetStats) {
        this.cronGetStats = cronGetStats;
    }

    public Instant getCronMaintenance() {
        return cronMaintenance;
    }

    public void setCronMaintenance(Instant cronMaintenance) {
        this.cronMaintenance = cronMaintenance;
    }

    public Instant getCronMonthlyStats() {
        return cronMonthlyStats;
    }

    public void setCronMonthlyStats(Instant cronMonthlyStats) {
        this.cronMonthlyStats = cronMonthlyStats;
    }

    public Instant getCronReceiveBitcoin() {
        return cronReceiveBitcoin;
    }

    public void setCronReceiveBitcoin(Instant cronReceiveBitcoin) {
        this.cronReceiveBitcoin = cronReceiveBitcoin;
    }

    public Instant getCronSendBitcoin() {
        return cronSendBitcoin;
    }

    public void setCronSendBitcoin(Instant cronSendBitcoin) {
        this.cronSendBitcoin = cronSendBitcoin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusDTO)) {
            return false;
        }

        StatusDTO statusDTO = (StatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusDTO{" +
            "id=" + getId() +
            ", lastSweep='" + getLastSweep() + "'" +
            ", deficitBtc=" + getDeficitBtc() +
            ", hotWalletBtc=" + getHotWalletBtc() +
            ", warmWalletBtc=" + getWarmWalletBtc() +
            ", totalBtc=" + getTotalBtc() +
            ", receivedBtcPending=" + getReceivedBtcPending() +
            ", pendingWithdrawals=" + getPendingWithdrawals() +
            ", tradingStatus='" + getTradingStatus() + "'" +
            ", withdrawalsStatus='" + getWithdrawalsStatus() + "'" +
            ", dbVersion=" + getDbVersion() +
            ", cronDailyStats='" + getCronDailyStats() + "'" +
            ", cronGetStats='" + getCronGetStats() + "'" +
            ", cronMaintenance='" + getCronMaintenance() + "'" +
            ", cronMonthlyStats='" + getCronMonthlyStats() + "'" +
            ", cronReceiveBitcoin='" + getCronReceiveBitcoin() + "'" +
            ", cronSendBitcoin='" + getCronSendBitcoin() + "'" +
            "}";
    }
}
