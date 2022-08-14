package io.github.jhipster.sample.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "last_sweep", nullable = false)
    private Instant lastSweep;

    @NotNull
    @Column(name = "deficit_btc", nullable = false)
    private Double deficitBtc;

    @NotNull
    @Column(name = "hot_wallet_btc", nullable = false)
    private Double hotWalletBtc;

    @NotNull
    @Column(name = "warm_wallet_btc", nullable = false)
    private Double warmWalletBtc;

    @NotNull
    @Column(name = "total_btc", nullable = false)
    private Double totalBtc;

    @NotNull
    @Column(name = "received_btc_pending", nullable = false)
    private Double receivedBtcPending;

    @NotNull
    @Column(name = "pending_withdrawals", nullable = false)
    private Double pendingWithdrawals;

    @NotNull
    @Size(max = 255)
    @Column(name = "trading_status", length = 255, nullable = false)
    private String tradingStatus;

    @NotNull
    @Size(max = 255)
    @Column(name = "withdrawals_status", length = 255, nullable = false)
    private String withdrawalsStatus;

    @NotNull
    @Column(name = "db_version", nullable = false)
    private Double dbVersion;

    @NotNull
    @Column(name = "cron_daily_stats", nullable = false)
    private Instant cronDailyStats;

    @NotNull
    @Column(name = "cron_get_stats", nullable = false)
    private Instant cronGetStats;

    @NotNull
    @Column(name = "cron_maintenance", nullable = false)
    private Instant cronMaintenance;

    @NotNull
    @Column(name = "cron_monthly_stats", nullable = false)
    private Instant cronMonthlyStats;

    @NotNull
    @Column(name = "cron_receive_bitcoin", nullable = false)
    private Instant cronReceiveBitcoin;

    @NotNull
    @Column(name = "cron_send_bitcoin", nullable = false)
    private Instant cronSendBitcoin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Status id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLastSweep() {
        return this.lastSweep;
    }

    public Status lastSweep(Instant lastSweep) {
        this.setLastSweep(lastSweep);
        return this;
    }

    public void setLastSweep(Instant lastSweep) {
        this.lastSweep = lastSweep;
    }

    public Double getDeficitBtc() {
        return this.deficitBtc;
    }

    public Status deficitBtc(Double deficitBtc) {
        this.setDeficitBtc(deficitBtc);
        return this;
    }

    public void setDeficitBtc(Double deficitBtc) {
        this.deficitBtc = deficitBtc;
    }

    public Double getHotWalletBtc() {
        return this.hotWalletBtc;
    }

    public Status hotWalletBtc(Double hotWalletBtc) {
        this.setHotWalletBtc(hotWalletBtc);
        return this;
    }

    public void setHotWalletBtc(Double hotWalletBtc) {
        this.hotWalletBtc = hotWalletBtc;
    }

    public Double getWarmWalletBtc() {
        return this.warmWalletBtc;
    }

    public Status warmWalletBtc(Double warmWalletBtc) {
        this.setWarmWalletBtc(warmWalletBtc);
        return this;
    }

    public void setWarmWalletBtc(Double warmWalletBtc) {
        this.warmWalletBtc = warmWalletBtc;
    }

    public Double getTotalBtc() {
        return this.totalBtc;
    }

    public Status totalBtc(Double totalBtc) {
        this.setTotalBtc(totalBtc);
        return this;
    }

    public void setTotalBtc(Double totalBtc) {
        this.totalBtc = totalBtc;
    }

    public Double getReceivedBtcPending() {
        return this.receivedBtcPending;
    }

    public Status receivedBtcPending(Double receivedBtcPending) {
        this.setReceivedBtcPending(receivedBtcPending);
        return this;
    }

    public void setReceivedBtcPending(Double receivedBtcPending) {
        this.receivedBtcPending = receivedBtcPending;
    }

    public Double getPendingWithdrawals() {
        return this.pendingWithdrawals;
    }

    public Status pendingWithdrawals(Double pendingWithdrawals) {
        this.setPendingWithdrawals(pendingWithdrawals);
        return this;
    }

    public void setPendingWithdrawals(Double pendingWithdrawals) {
        this.pendingWithdrawals = pendingWithdrawals;
    }

    public String getTradingStatus() {
        return this.tradingStatus;
    }

    public Status tradingStatus(String tradingStatus) {
        this.setTradingStatus(tradingStatus);
        return this;
    }

    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public String getWithdrawalsStatus() {
        return this.withdrawalsStatus;
    }

    public Status withdrawalsStatus(String withdrawalsStatus) {
        this.setWithdrawalsStatus(withdrawalsStatus);
        return this;
    }

    public void setWithdrawalsStatus(String withdrawalsStatus) {
        this.withdrawalsStatus = withdrawalsStatus;
    }

    public Double getDbVersion() {
        return this.dbVersion;
    }

    public Status dbVersion(Double dbVersion) {
        this.setDbVersion(dbVersion);
        return this;
    }

    public void setDbVersion(Double dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Instant getCronDailyStats() {
        return this.cronDailyStats;
    }

    public Status cronDailyStats(Instant cronDailyStats) {
        this.setCronDailyStats(cronDailyStats);
        return this;
    }

    public void setCronDailyStats(Instant cronDailyStats) {
        this.cronDailyStats = cronDailyStats;
    }

    public Instant getCronGetStats() {
        return this.cronGetStats;
    }

    public Status cronGetStats(Instant cronGetStats) {
        this.setCronGetStats(cronGetStats);
        return this;
    }

    public void setCronGetStats(Instant cronGetStats) {
        this.cronGetStats = cronGetStats;
    }

    public Instant getCronMaintenance() {
        return this.cronMaintenance;
    }

    public Status cronMaintenance(Instant cronMaintenance) {
        this.setCronMaintenance(cronMaintenance);
        return this;
    }

    public void setCronMaintenance(Instant cronMaintenance) {
        this.cronMaintenance = cronMaintenance;
    }

    public Instant getCronMonthlyStats() {
        return this.cronMonthlyStats;
    }

    public Status cronMonthlyStats(Instant cronMonthlyStats) {
        this.setCronMonthlyStats(cronMonthlyStats);
        return this;
    }

    public void setCronMonthlyStats(Instant cronMonthlyStats) {
        this.cronMonthlyStats = cronMonthlyStats;
    }

    public Instant getCronReceiveBitcoin() {
        return this.cronReceiveBitcoin;
    }

    public Status cronReceiveBitcoin(Instant cronReceiveBitcoin) {
        this.setCronReceiveBitcoin(cronReceiveBitcoin);
        return this;
    }

    public void setCronReceiveBitcoin(Instant cronReceiveBitcoin) {
        this.cronReceiveBitcoin = cronReceiveBitcoin;
    }

    public Instant getCronSendBitcoin() {
        return this.cronSendBitcoin;
    }

    public Status cronSendBitcoin(Instant cronSendBitcoin) {
        this.setCronSendBitcoin(cronSendBitcoin);
        return this;
    }

    public void setCronSendBitcoin(Instant cronSendBitcoin) {
        this.cronSendBitcoin = cronSendBitcoin;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Status{" +
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
