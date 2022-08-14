package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Requests.
 */
@Entity
@Table(name = "requests")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requests implements Serializable {

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
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @NotNull
    @Column(name = "account", nullable = false)
    private Long account;

    @NotNull
    @Size(max = 255)
    @Column(name = "send_address", length = 255, nullable = false)
    private String sendAddress;

    @NotNull
    @Size(max = 255)
    @Column(name = "transaction_id", length = 255, nullable = false)
    private String transactionId;

    @NotNull
    @Column(name = "increment", nullable = false)
    private Double increment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "done", nullable = false)
    private YesNo done;

    @NotNull
    @Column(name = "crypto_id", nullable = false)
    private Integer cryptoId;

    @NotNull
    @Column(name = "fee", nullable = false)
    private Double fee;

    @NotNull
    @Column(name = "net_amount", nullable = false)
    private Double netAmount;

    @NotNull
    @Column(name = "notified", nullable = false)
    private Integer notified;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies currency;

    @OneToOne
    @JoinColumn(unique = true)
    private RequestDescriptions description;

    @OneToOne
    @JoinColumn(unique = true)
    private RequestStatus requestStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private RequestTypes requestType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Requests id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Requests date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Requests amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAddressId() {
        return this.addressId;
    }

    public Requests addressId(Integer addressId) {
        this.setAddressId(addressId);
        return this;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Long getAccount() {
        return this.account;
    }

    public Requests account(Long account) {
        this.setAccount(account);
        return this;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getSendAddress() {
        return this.sendAddress;
    }

    public Requests sendAddress(String sendAddress) {
        this.setSendAddress(sendAddress);
        return this;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public Requests transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getIncrement() {
        return this.increment;
    }

    public Requests increment(Double increment) {
        this.setIncrement(increment);
        return this;
    }

    public void setIncrement(Double increment) {
        this.increment = increment;
    }

    public YesNo getDone() {
        return this.done;
    }

    public Requests done(YesNo done) {
        this.setDone(done);
        return this;
    }

    public void setDone(YesNo done) {
        this.done = done;
    }

    public Integer getCryptoId() {
        return this.cryptoId;
    }

    public Requests cryptoId(Integer cryptoId) {
        this.setCryptoId(cryptoId);
        return this;
    }

    public void setCryptoId(Integer cryptoId) {
        this.cryptoId = cryptoId;
    }

    public Double getFee() {
        return this.fee;
    }

    public Requests fee(Double fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getNetAmount() {
        return this.netAmount;
    }

    public Requests netAmount(Double netAmount) {
        this.setNetAmount(netAmount);
        return this;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getNotified() {
        return this.notified;
    }

    public Requests notified(Integer notified) {
        this.setNotified(notified);
        return this;
    }

    public void setNotified(Integer notified) {
        this.notified = notified;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public Requests siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    public Currencies getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currencies currencies) {
        this.currency = currencies;
    }

    public Requests currency(Currencies currencies) {
        this.setCurrency(currencies);
        return this;
    }

    public RequestDescriptions getDescription() {
        return this.description;
    }

    public void setDescription(RequestDescriptions requestDescriptions) {
        this.description = requestDescriptions;
    }

    public Requests description(RequestDescriptions requestDescriptions) {
        this.setDescription(requestDescriptions);
        return this;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Requests requestStatus(RequestStatus requestStatus) {
        this.setRequestStatus(requestStatus);
        return this;
    }

    public RequestTypes getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestTypes requestTypes) {
        this.requestType = requestTypes;
    }

    public Requests requestType(RequestTypes requestTypes) {
        this.setRequestType(requestTypes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requests)) {
            return false;
        }
        return id != null && id.equals(((Requests) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Requests{" +
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
            "}";
    }
}
