package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CurrentStats.
 */
@Entity
@Table(name = "current_stats")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CurrentStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "total_btc", nullable = false)
    private Long totalBtc;

    @NotNull
    @Column(name = "market_cap", nullable = false)
    private Long marketCap;

    @NotNull
    @Column(name = "trade_volume", nullable = false)
    private Long tradeVolume;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CurrentStats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalBtc() {
        return this.totalBtc;
    }

    public CurrentStats totalBtc(Long totalBtc) {
        this.setTotalBtc(totalBtc);
        return this;
    }

    public void setTotalBtc(Long totalBtc) {
        this.totalBtc = totalBtc;
    }

    public Long getMarketCap() {
        return this.marketCap;
    }

    public CurrentStats marketCap(Long marketCap) {
        this.setMarketCap(marketCap);
        return this;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    public Long getTradeVolume() {
        return this.tradeVolume;
    }

    public CurrentStats tradeVolume(Long tradeVolume) {
        this.setTradeVolume(tradeVolume);
        return this;
    }

    public void setTradeVolume(Long tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrentStats)) {
            return false;
        }
        return id != null && id.equals(((CurrentStats) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrentStats{" +
            "id=" + getId() +
            ", totalBtc=" + getTotalBtc() +
            ", marketCap=" + getMarketCap() +
            ", tradeVolume=" + getTradeVolume() +
            "}";
    }
}
