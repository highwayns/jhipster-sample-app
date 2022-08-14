package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Orders} entity.
 */
public class OrdersDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private Double btc;

    @NotNull
    private Double fiat;

    @NotNull
    private Double btcPrice;

    @NotNull
    private YesNo marketPrice;

    @NotNull
    private Double stopPrice;

    private OrderTypesDTO orderType;

    private SiteUsersDTO siteUser;

    private CurrenciesDTO currency;

    private OrderLogDTO logId;

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

    public Double getFiat() {
        return fiat;
    }

    public void setFiat(Double fiat) {
        this.fiat = fiat;
    }

    public Double getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice(Double btcPrice) {
        this.btcPrice = btcPrice;
    }

    public YesNo getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(YesNo marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(Double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public OrderTypesDTO getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypesDTO orderType) {
        this.orderType = orderType;
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

    public OrderLogDTO getLogId() {
        return logId;
    }

    public void setLogId(OrderLogDTO logId) {
        this.logId = logId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdersDTO)) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", btc=" + getBtc() +
            ", fiat=" + getFiat() +
            ", btcPrice=" + getBtcPrice() +
            ", marketPrice='" + getMarketPrice() + "'" +
            ", stopPrice=" + getStopPrice() +
            ", orderType=" + getOrderType() +
            ", siteUser=" + getSiteUser() +
            ", currency=" + getCurrency() +
            ", logId=" + getLogId() +
            "}";
    }
}
