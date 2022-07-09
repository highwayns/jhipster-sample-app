package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.OrderLineType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderLine.
 */
@Entity
@Table(name = "order_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "line_number")
    private Long lineNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrderLineType type;

    @Column(name = "sku_code")
    private String skuCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit_price_excl_vat")
    private Double unitPriceExclVat;

    @Column(name = "unit_price_incl_vat")
    private Double unitPriceInclVat;

    @Column(name = "vat_percentage")
    private Double vatPercentage;

    @Column(name = "vat_percentage_label")
    private String vatPercentageLabel;

    @Column(name = "discount_percentage_label")
    private String discountPercentageLabel;

    @Column(name = "total_line_amount")
    private Double totalLineAmount;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "orderLines")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "billingAddress", "billingIdentity", "shippingAddress", "orderLines" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderLine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineNumber() {
        return this.lineNumber;
    }

    public OrderLine lineNumber(Long lineNumber) {
        this.setLineNumber(lineNumber);
        return this;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public OrderLineType getType() {
        return this.type;
    }

    public OrderLine type(OrderLineType type) {
        this.setType(type);
        return this;
    }

    public void setType(OrderLineType type) {
        this.type = type;
    }

    public String getSkuCode() {
        return this.skuCode;
    }

    public OrderLine skuCode(String skuCode) {
        this.setSkuCode(skuCode);
        return this;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getName() {
        return this.name;
    }

    public OrderLine name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public OrderLine description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public OrderLine quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPriceExclVat() {
        return this.unitPriceExclVat;
    }

    public OrderLine unitPriceExclVat(Double unitPriceExclVat) {
        this.setUnitPriceExclVat(unitPriceExclVat);
        return this;
    }

    public void setUnitPriceExclVat(Double unitPriceExclVat) {
        this.unitPriceExclVat = unitPriceExclVat;
    }

    public Double getUnitPriceInclVat() {
        return this.unitPriceInclVat;
    }

    public OrderLine unitPriceInclVat(Double unitPriceInclVat) {
        this.setUnitPriceInclVat(unitPriceInclVat);
        return this;
    }

    public void setUnitPriceInclVat(Double unitPriceInclVat) {
        this.unitPriceInclVat = unitPriceInclVat;
    }

    public Double getVatPercentage() {
        return this.vatPercentage;
    }

    public OrderLine vatPercentage(Double vatPercentage) {
        this.setVatPercentage(vatPercentage);
        return this;
    }

    public void setVatPercentage(Double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public String getVatPercentageLabel() {
        return this.vatPercentageLabel;
    }

    public OrderLine vatPercentageLabel(String vatPercentageLabel) {
        this.setVatPercentageLabel(vatPercentageLabel);
        return this;
    }

    public void setVatPercentageLabel(String vatPercentageLabel) {
        this.vatPercentageLabel = vatPercentageLabel;
    }

    public String getDiscountPercentageLabel() {
        return this.discountPercentageLabel;
    }

    public OrderLine discountPercentageLabel(String discountPercentageLabel) {
        this.setDiscountPercentageLabel(discountPercentageLabel);
        return this;
    }

    public void setDiscountPercentageLabel(String discountPercentageLabel) {
        this.discountPercentageLabel = discountPercentageLabel;
    }

    public Double getTotalLineAmount() {
        return this.totalLineAmount;
    }

    public OrderLine totalLineAmount(Double totalLineAmount) {
        this.setTotalLineAmount(totalLineAmount);
        return this;
    }

    public void setTotalLineAmount(Double totalLineAmount) {
        this.totalLineAmount = totalLineAmount;
    }

    public String getUrl() {
        return this.url;
    }

    public OrderLine url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setOrderLines(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setOrderLines(this));
        }
        this.orders = orders;
    }

    public OrderLine orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public OrderLine addOrder(Order order) {
        this.orders.add(order);
        order.setOrderLines(this);
        return this;
    }

    public OrderLine removeOrder(Order order) {
        this.orders.remove(order);
        order.setOrderLines(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLine)) {
            return false;
        }
        return id != null && id.equals(((OrderLine) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderLine{" +
            "id=" + getId() +
            ", lineNumber=" + getLineNumber() +
            ", type='" + getType() + "'" +
            ", skuCode='" + getSkuCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPriceExclVat=" + getUnitPriceExclVat() +
            ", unitPriceInclVat=" + getUnitPriceInclVat() +
            ", vatPercentage=" + getVatPercentage() +
            ", vatPercentageLabel='" + getVatPercentageLabel() + "'" +
            ", discountPercentageLabel='" + getDiscountPercentageLabel() + "'" +
            ", totalLineAmount=" + getTotalLineAmount() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
