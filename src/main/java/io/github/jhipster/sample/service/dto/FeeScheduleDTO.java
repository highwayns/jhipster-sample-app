package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.FeeSchedule} entity.
 */
public class FeeScheduleDTO implements Serializable {

    private Long id;

    @NotNull
    private Double fee;

    @NotNull
    private Double fromUsd;

    @NotNull
    private Double toUsd;

    @NotNull
    private Integer order;

    @NotNull
    private Double fee1;

    @NotNull
    private Double globalBtc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFromUsd() {
        return fromUsd;
    }

    public void setFromUsd(Double fromUsd) {
        this.fromUsd = fromUsd;
    }

    public Double getToUsd() {
        return toUsd;
    }

    public void setToUsd(Double toUsd) {
        this.toUsd = toUsd;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Double getFee1() {
        return fee1;
    }

    public void setFee1(Double fee1) {
        this.fee1 = fee1;
    }

    public Double getGlobalBtc() {
        return globalBtc;
    }

    public void setGlobalBtc(Double globalBtc) {
        this.globalBtc = globalBtc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeeScheduleDTO)) {
            return false;
        }

        FeeScheduleDTO feeScheduleDTO = (FeeScheduleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, feeScheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeeScheduleDTO{" +
            "id=" + getId() +
            ", fee=" + getFee() +
            ", fromUsd=" + getFromUsd() +
            ", toUsd=" + getToUsd() +
            ", order=" + getOrder() +
            ", fee1=" + getFee1() +
            ", globalBtc=" + getGlobalBtc() +
            "}";
    }
}
