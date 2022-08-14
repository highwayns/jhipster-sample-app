package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A History.
 */
@Entity
@Table(name = "history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class History implements Serializable {

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
    @Size(max = 255)
    @Column(name = "ip", length = 255, nullable = false)
    private String ip;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_address", length = 255, nullable = false)
    private String bitcoinAddress;

    @NotNull
    @Column(name = "balance_before", nullable = false)
    private Double balanceBefore;

    @NotNull
    @Column(name = "balance_after", nullable = false)
    private Double balanceAfter;

    @OneToOne
    @JoinColumn(unique = true)
    private HistoryActions historyAction;

    @JsonIgnoreProperties(value = { "orderType", "siteUser", "currency", "logId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Orders orderId;

    @JsonIgnoreProperties(value = { "siteUser", "currency", "description", "requestStatus", "requestType" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Requests requestId;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public History id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public History date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getIp() {
        return this.ip;
    }

    public History ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBitcoinAddress() {
        return this.bitcoinAddress;
    }

    public History bitcoinAddress(String bitcoinAddress) {
        this.setBitcoinAddress(bitcoinAddress);
        return this;
    }

    public void setBitcoinAddress(String bitcoinAddress) {
        this.bitcoinAddress = bitcoinAddress;
    }

    public Double getBalanceBefore() {
        return this.balanceBefore;
    }

    public History balanceBefore(Double balanceBefore) {
        this.setBalanceBefore(balanceBefore);
        return this;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Double getBalanceAfter() {
        return this.balanceAfter;
    }

    public History balanceAfter(Double balanceAfter) {
        this.setBalanceAfter(balanceAfter);
        return this;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public HistoryActions getHistoryAction() {
        return this.historyAction;
    }

    public void setHistoryAction(HistoryActions historyActions) {
        this.historyAction = historyActions;
    }

    public History historyAction(HistoryActions historyActions) {
        this.setHistoryAction(historyActions);
        return this;
    }

    public Orders getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Orders orders) {
        this.orderId = orders;
    }

    public History orderId(Orders orders) {
        this.setOrderId(orders);
        return this;
    }

    public Requests getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Requests requests) {
        this.requestId = requests;
    }

    public History requestId(Requests requests) {
        this.setRequestId(requests);
        return this;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public History siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof History)) {
            return false;
        }
        return id != null && id.equals(((History) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "History{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", ip='" + getIp() + "'" +
            ", bitcoinAddress='" + getBitcoinAddress() + "'" +
            ", balanceBefore=" + getBalanceBefore() +
            ", balanceAfter=" + getBalanceAfter() +
            "}";
    }
}
