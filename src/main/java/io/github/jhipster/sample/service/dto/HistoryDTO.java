package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.History} entity.
 */
public class HistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    @Size(max = 255)
    private String ip;

    @NotNull
    @Size(max = 255)
    private String bitcoinAddress;

    @NotNull
    private Double balanceBefore;

    @NotNull
    private Double balanceAfter;

    private HistoryActionsDTO historyAction;

    private OrdersDTO orderId;

    private RequestsDTO requestId;

    private SiteUsersDTO siteUser;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBitcoinAddress() {
        return bitcoinAddress;
    }

    public void setBitcoinAddress(String bitcoinAddress) {
        this.bitcoinAddress = bitcoinAddress;
    }

    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public HistoryActionsDTO getHistoryAction() {
        return historyAction;
    }

    public void setHistoryAction(HistoryActionsDTO historyAction) {
        this.historyAction = historyAction;
    }

    public OrdersDTO getOrderId() {
        return orderId;
    }

    public void setOrderId(OrdersDTO orderId) {
        this.orderId = orderId;
    }

    public RequestsDTO getRequestId() {
        return requestId;
    }

    public void setRequestId(RequestsDTO requestId) {
        this.requestId = requestId;
    }

    public SiteUsersDTO getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUsersDTO siteUser) {
        this.siteUser = siteUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoryDTO)) {
            return false;
        }

        HistoryDTO historyDTO = (HistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, historyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", ip='" + getIp() + "'" +
            ", bitcoinAddress='" + getBitcoinAddress() + "'" +
            ", balanceBefore=" + getBalanceBefore() +
            ", balanceAfter=" + getBalanceAfter() +
            ", historyAction=" + getHistoryAction() +
            ", orderId=" + getOrderId() +
            ", requestId=" + getRequestId() +
            ", siteUser=" + getSiteUser() +
            "}";
    }
}
