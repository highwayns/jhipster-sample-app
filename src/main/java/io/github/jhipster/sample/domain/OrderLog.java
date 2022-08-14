package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.Status;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderLog.
 */
@Entity
@Table(name = "order_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderLog implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "market_price", nullable = false)
    private YesNo marketPrice;

    @NotNull
    @Column(name = "btc_price", nullable = false)
    private Double btcPrice;

    @NotNull
    @Column(name = "fiat", nullable = false)
    private Double fiat;

    @NotNull
    @Column(name = "p_id", nullable = false)
    private Integer pId;

    @NotNull
    @Size(max = 255)
    @Column(name = "stop_price", length = 255, nullable = false)
    private String stopPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @NotNull
    @Column(name = "btc_remaining", nullable = false)
    private Double btcRemaining;

    @JsonIgnoreProperties(value = { "country", "feeSchedule", "defaultCurrency" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteUsers siteUser;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies currency;

    @OneToOne
    @JoinColumn(unique = true)
    private OrderTypes orderType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public OrderLog date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getBtc() {
        return this.btc;
    }

    public OrderLog btc(Double btc) {
        this.setBtc(btc);
        return this;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public YesNo getMarketPrice() {
        return this.marketPrice;
    }

    public OrderLog marketPrice(YesNo marketPrice) {
        this.setMarketPrice(marketPrice);
        return this;
    }

    public void setMarketPrice(YesNo marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getBtcPrice() {
        return this.btcPrice;
    }

    public OrderLog btcPrice(Double btcPrice) {
        this.setBtcPrice(btcPrice);
        return this;
    }

    public void setBtcPrice(Double btcPrice) {
        this.btcPrice = btcPrice;
    }

    public Double getFiat() {
        return this.fiat;
    }

    public OrderLog fiat(Double fiat) {
        this.setFiat(fiat);
        return this;
    }

    public void setFiat(Double fiat) {
        this.fiat = fiat;
    }

    public Integer getpId() {
        return this.pId;
    }

    public OrderLog pId(Integer pId) {
        this.setpId(pId);
        return this;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getStopPrice() {
        return this.stopPrice;
    }

    public OrderLog stopPrice(String stopPrice) {
        this.setStopPrice(stopPrice);
        return this;
    }

    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
    }

    public Status getStatus() {
        return this.status;
    }

    public OrderLog status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getBtcRemaining() {
        return this.btcRemaining;
    }

    public OrderLog btcRemaining(Double btcRemaining) {
        this.setBtcRemaining(btcRemaining);
        return this;
    }

    public void setBtcRemaining(Double btcRemaining) {
        this.btcRemaining = btcRemaining;
    }

    public SiteUsers getSiteUser() {
        return this.siteUser;
    }

    public void setSiteUser(SiteUsers siteUsers) {
        this.siteUser = siteUsers;
    }

    public OrderLog siteUser(SiteUsers siteUsers) {
        this.setSiteUser(siteUsers);
        return this;
    }

    public Currencies getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currencies currencies) {
        this.currency = currencies;
    }

    public OrderLog currency(Currencies currencies) {
        this.setCurrency(currencies);
        return this;
    }

    public OrderTypes getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderTypes orderTypes) {
        this.orderType = orderTypes;
    }

    public OrderLog orderType(OrderTypes orderTypes) {
        this.setOrderType(orderTypes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLog)) {
            return false;
        }
        return id != null && id.equals(((OrderLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderLog{" +
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
            "}";
    }
}
