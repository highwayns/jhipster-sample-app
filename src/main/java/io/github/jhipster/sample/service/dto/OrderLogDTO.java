package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.Status;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.OrderLog} entity.
 */
public class OrderLogDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private Double btc;

    @NotNull
    private YesNo marketPrice;

    @NotNull
    private Double btcPrice;

    @NotNull
    private Double fiat;

    @NotNull
    private Integer pId;

    @NotNull
    @Size(max = 255)
    private String stopPrice;

    @NotNull
    private Status status;

    @NotNull
    private Double btcRemaining;

    private SiteUsersDTO siteUser;

    private CurrenciesDTO currency;

    private OrderTypesDTO orderType;

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

    public YesNo getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(YesNo marketPrice) {
        this.marketPrice = marketPrice;
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

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getBtcRemaining() {
        return btcRemaining;
    }

    public void setBtcRemaining(Double btcRemaining) {
        this.btcRemaining = btcRemaining;
    }

    public SiteUsersDTO getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUsersDTO siteUser) {
        this.siteUser = siteUser;
    }

    public CurrenciesDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrenciesDTO currency) {
        this.currency = currency;
    }

    public OrderTypesDTO getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypesDTO orderType) {
        this.orderType = orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLogDTO)) {
            return false;
        }

        OrderLogDTO orderLogDTO = (OrderLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderLogDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", btc=" + getBtc() +
            ", marketPrice='" + getMarketPrice() + "'" +
            ", btcPrice=" + getBtcPrice() +
            ", fiat=" + getFiat() +
            ", pId=" + getpId() +
            ", stopPrice='" + getStopPrice() + "'" +
            ", status='" + getStatus() + "'" +
            ", btcRemaining=" + getBtcRemaining() +
            ", siteUser=" + getSiteUser() +
            ", currency=" + getCurrency() +
            ", orderType=" + getOrderType() +
            "}";
    }
}
