package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.CurrentStats} entity.
 */
public class CurrentStatsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long totalBtc;

    @NotNull
    private Long marketCap;

    @NotNull
    private Long tradeVolume;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalBtc() {
        return totalBtc;
    }

    public void setTotalBtc(Long totalBtc) {
        this.totalBtc = totalBtc;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    public Long getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(Long tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrentStatsDTO)) {
            return false;
        }

        CurrentStatsDTO currentStatsDTO = (CurrentStatsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, currentStatsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrentStatsDTO{" +
            "id=" + getId() +
            ", totalBtc=" + getTotalBtc() +
            ", marketCap=" + getMarketCap() +
            ", tradeVolume=" + getTradeVolume() +
            "}";
    }
}
