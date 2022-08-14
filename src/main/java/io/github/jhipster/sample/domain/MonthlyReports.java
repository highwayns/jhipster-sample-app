package io.github.jhipster.sample.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MonthlyReports.
 */
@Entity
@Table(name = "monthly_reports")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MonthlyReports implements Serializable {

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
    @Column(name = "transactions_btc", nullable = false)
    private Double transactionsBtc;

    @NotNull
    @Column(name = "avg_transaction_size_btc", nullable = false)
    private Double avgTransactionSizeBtc;

    @NotNull
    @Column(name = "transaction_volume_per_user", nullable = false)
    private Double transactionVolumePerUser;

    @NotNull
    @Column(name = "total_fees_btc", nullable = false)
    private Double totalFeesBtc;

    @NotNull
    @Column(name = "fees_per_user_btc", nullable = false)
    private Double feesPerUserBtc;

    @NotNull
    @Column(name = "gross_profit_btc", nullable = false)
    private Double grossProfitBtc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MonthlyReports id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public MonthlyReports date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getTransactionsBtc() {
        return this.transactionsBtc;
    }

    public MonthlyReports transactionsBtc(Double transactionsBtc) {
        this.setTransactionsBtc(transactionsBtc);
        return this;
    }

    public void setTransactionsBtc(Double transactionsBtc) {
        this.transactionsBtc = transactionsBtc;
    }

    public Double getAvgTransactionSizeBtc() {
        return this.avgTransactionSizeBtc;
    }

    public MonthlyReports avgTransactionSizeBtc(Double avgTransactionSizeBtc) {
        this.setAvgTransactionSizeBtc(avgTransactionSizeBtc);
        return this;
    }

    public void setAvgTransactionSizeBtc(Double avgTransactionSizeBtc) {
        this.avgTransactionSizeBtc = avgTransactionSizeBtc;
    }

    public Double getTransactionVolumePerUser() {
        return this.transactionVolumePerUser;
    }

    public MonthlyReports transactionVolumePerUser(Double transactionVolumePerUser) {
        this.setTransactionVolumePerUser(transactionVolumePerUser);
        return this;
    }

    public void setTransactionVolumePerUser(Double transactionVolumePerUser) {
        this.transactionVolumePerUser = transactionVolumePerUser;
    }

    public Double getTotalFeesBtc() {
        return this.totalFeesBtc;
    }

    public MonthlyReports totalFeesBtc(Double totalFeesBtc) {
        this.setTotalFeesBtc(totalFeesBtc);
        return this;
    }

    public void setTotalFeesBtc(Double totalFeesBtc) {
        this.totalFeesBtc = totalFeesBtc;
    }

    public Double getFeesPerUserBtc() {
        return this.feesPerUserBtc;
    }

    public MonthlyReports feesPerUserBtc(Double feesPerUserBtc) {
        this.setFeesPerUserBtc(feesPerUserBtc);
        return this;
    }

    public void setFeesPerUserBtc(Double feesPerUserBtc) {
        this.feesPerUserBtc = feesPerUserBtc;
    }

    public Double getGrossProfitBtc() {
        return this.grossProfitBtc;
    }

    public MonthlyReports grossProfitBtc(Double grossProfitBtc) {
        this.setGrossProfitBtc(grossProfitBtc);
        return this;
    }

    public void setGrossProfitBtc(Double grossProfitBtc) {
        this.grossProfitBtc = grossProfitBtc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyReports)) {
            return false;
        }
        return id != null && id.equals(((MonthlyReports) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlyReports{" +
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
