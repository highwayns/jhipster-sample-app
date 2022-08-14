package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeeSchedule.
 */
@Entity
@Table(name = "fee_schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FeeSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fee", nullable = false)
    private Double fee;

    @NotNull
    @Column(name = "from_usd", nullable = false)
    private Double fromUsd;

    @NotNull
    @Column(name = "to_usd", nullable = false)
    private Double toUsd;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Column(name = "fee_1", nullable = false)
    private Double fee1;

    @NotNull
    @Column(name = "global_btc", nullable = false)
    private Double globalBtc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeeSchedule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFee() {
        return this.fee;
    }

    public FeeSchedule fee(Double fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFromUsd() {
        return this.fromUsd;
    }

    public FeeSchedule fromUsd(Double fromUsd) {
        this.setFromUsd(fromUsd);
        return this;
    }

    public void setFromUsd(Double fromUsd) {
        this.fromUsd = fromUsd;
    }

    public Double getToUsd() {
        return this.toUsd;
    }

    public FeeSchedule toUsd(Double toUsd) {
        this.setToUsd(toUsd);
        return this;
    }

    public void setToUsd(Double toUsd) {
        this.toUsd = toUsd;
    }

    public Integer getOrder() {
        return this.order;
    }

    public FeeSchedule order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Double getFee1() {
        return this.fee1;
    }

    public FeeSchedule fee1(Double fee1) {
        this.setFee1(fee1);
        return this;
    }

    public void setFee1(Double fee1) {
        this.fee1 = fee1;
    }

    public Double getGlobalBtc() {
        return this.globalBtc;
    }

    public FeeSchedule globalBtc(Double globalBtc) {
        this.setGlobalBtc(globalBtc);
        return this;
    }

    public void setGlobalBtc(Double globalBtc) {
        this.globalBtc = globalBtc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeeSchedule)) {
            return false;
        }
        return id != null && id.equals(((FeeSchedule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeeSchedule{" +
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
