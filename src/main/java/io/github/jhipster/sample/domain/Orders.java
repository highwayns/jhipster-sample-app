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
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Orders implements Serializable {

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
    @Column(name = "btc", nullable = false)
    private Double btc;

    @NotNull
    @Column(name = "fiat", nullable = false)
    private Double fiat;

    @NotNull
    @Column(name = "btc_price", nullable = false)
    private Double btcPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "market_price", nullable = false)
    private YesNo marketPrice;

    @NotNull
    @Column(name = "stop_price", nullable = false)
    private Double stopPrice;

    @OneToOne
    @JoinColumn(unique = true)
    private OrderTypes orderType;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies currency;

    @JsonIgnoreProperties(value = { "siteUser", "currency", "orderType" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrderLog logId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Orders id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Orders date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getBtc() {
        return this.btc;
    }

    public Orders btc(Double btc) {
        this.setBtc(btc);
        return this;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public Double getFiat() {
        return this.fiat;
    }

    public Orders fiat(Double fiat) {
        this.setFiat(fiat);
        return this;
    }

    public void setFiat(Double fiat) {
        this.fiat = fiat;
    }

    public Double getBtcPrice() {
        return this.btcPrice;
    }

    public Orders btcPrice(Double btcPrice) {
        this.setBtcPrice(btcPrice);
        return this;
    }

    public void setBtcPrice(Double btcPrice) {
        this.btcPrice = btcPrice;
    }

    public YesNo getMarketPrice() {
        return this.marketPrice;
    }

    public Orders marketPrice(YesNo marketPrice) {
        this.setMarketPrice(marketPrice);
        return this;
    }

    public void setMarketPrice(YesNo marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getStopPrice() {
        return this.stopPrice;
    }

    public Orders stopPrice(Double stopPrice) {
        this.setStopPrice(stopPrice);
        return this;
    }

    public void setStopPrice(Double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public OrderTypes getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderTypes orderTypes) {
        this.orderType = orderTypes;
    }

    public Orders orderType(OrderTypes orderTypes) {
        this.setOrderType(orderTypes);
        return this;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public Orders siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    public Currencies getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currencies currencies) {
        this.currency = currencies;
    }

    public Orders currency(Currencies currencies) {
        this.setCurrency(currencies);
        return this;
    }

    public OrderLog getLogId() {
        return this.logId;
    }

    public void setLogId(OrderLog orderLog) {
        this.logId = orderLog;
    }

    public Orders logId(OrderLog orderLog) {
        this.setLogId(orderLog);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", btc=" + getBtc() +
            ", fiat=" + getFiat() +
            ", btcPrice=" + getBtcPrice() +
            ", marketPrice='" + getMarketPrice() + "'" +
            ", stopPrice=" + getStopPrice() +
            "}";
    }
}
