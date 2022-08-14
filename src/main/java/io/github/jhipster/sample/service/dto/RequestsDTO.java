package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Requests} entity.
 */
public class RequestsDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private Double amount;

    @NotNull
    private Integer addressId;

    @NotNull
    private Long account;

    @NotNull
    @Size(max = 255)
    private String sendAddress;

    @NotNull
    @Size(max = 255)
    private String transactionId;

    @NotNull
    private Double increment;

    @NotNull
    private YesNo done;

    @NotNull
    private Integer cryptoId;

    @NotNull
    private Double fee;

    @NotNull
    private Double netAmount;

    @NotNull
    private Integer notified;

    private SiteUsersDTO siteUser;

    private CurrenciesDTO currency;

    private RequestDescriptionsDTO description;

    private RequestStatusDTO requestStatus;

    private RequestTypesDTO requestType;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getIncrement() {
        return increment;
    }

    public void setIncrement(Double increment) {
        this.increment = increment;
    }

    public YesNo getDone() {
        return done;
    }

    public void setDone(YesNo done) {
        this.done = done;
    }

    public Integer getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Integer cryptoId) {
        this.cryptoId = cryptoId;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getNotified() {
        return notified;
    }

    public void setNotified(Integer notified) {
        this.notified = notified;
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

    public RequestDescriptionsDTO getDescription() {
        return description;
    }

    public void setDescription(RequestDescriptionsDTO description) {
        this.description = description;
    }

    public RequestStatusDTO getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatusDTO requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestTypesDTO getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypesDTO requestType) {
        this.requestType = requestType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestsDTO)) {
            return false;
        }

        RequestsDTO requestsDTO = (RequestsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestsDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", addressId=" + getAddressId() +
            ", account=" + getAccount() +
            ", sendAddress='" + getSendAddress() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", increment=" + getIncrement() +
            ", done='" + getDone() + "'" +
            ", cryptoId=" + getCryptoId() +
            ", fee=" + getFee() +
            ", netAmount=" + getNetAmount() +
            ", notified=" + getNotified() +
            ", siteUser=" + getSiteUser() +
            ", currency=" + getCurrency() +
            ", description=" + getDescription() +
            ", requestStatus=" + getRequestStatus() +
            ", requestType=" + getRequestType() +
            "}";
    }
}
