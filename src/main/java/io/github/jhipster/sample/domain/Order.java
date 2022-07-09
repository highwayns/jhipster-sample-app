package io.github.jhipster.sample.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "note")
    private String note;

    @Column(name = "create_date_time_utc")
    private Instant createDateTimeUtc;

    @Column(name = "customer_reference")
    private Long customerReference;

    @OneToOne
    @JoinColumn(unique = true)
    private Address billingAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private Identity billingIdentity;

    @OneToOne
    @JoinColumn(unique = true)
    private Address shippingAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public Order orderNumber(String orderNumber) {
        this.setOrderNumber(orderNumber);
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getNote() {
        return this.note;
    }

    public Order note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreateDateTimeUtc() {
        return this.createDateTimeUtc;
    }

    public Order createDateTimeUtc(Instant createDateTimeUtc) {
        this.setCreateDateTimeUtc(createDateTimeUtc);
        return this;
    }

    public void setCreateDateTimeUtc(Instant createDateTimeUtc) {
        this.createDateTimeUtc = createDateTimeUtc;
    }

    public Long getCustomerReference() {
        return this.customerReference;
    }

    public Order customerReference(Long customerReference) {
        this.setCustomerReference(customerReference);
        return this;
    }

    public void setCustomerReference(Long customerReference) {
        this.customerReference = customerReference;
    }

    public Address getBillingAddress() {
        return this.billingAddress;
    }

    public void setBillingAddress(Address address) {
        this.billingAddress = address;
    }

    public Order billingAddress(Address address) {
        this.setBillingAddress(address);
        return this;
    }

    public Identity getBillingIdentity() {
        return this.billingIdentity;
    }

    public void setBillingIdentity(Identity identity) {
        this.billingIdentity = identity;
    }

    public Order billingIdentity(Identity identity) {
        this.setBillingIdentity(identity);
        return this;
    }

    public Address getShippingAddress() {
        return this.shippingAddress;
    }

    public void setShippingAddress(Address address) {
        this.shippingAddress = address;
    }

    public Order shippingAddress(Address address) {
        this.setShippingAddress(address);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", note='" + getNote() + "'" +
            ", createDateTimeUtc='" + getCreateDateTimeUtc() + "'" +
            ", customerReference=" + getCustomerReference() +
            "}";
    }
}
