package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminControls.
 */
@Entity
@Table(name = "admin_controls")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminControls implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "action", length = 50, nullable = false)
    private String action;

    @Size(max = 50)
    @Column(name = "control_class", length = 50)
    private String controlClass;

    @NotNull
    @Size(max = 255)
    @Column(name = "argument", length = 255, nullable = false)
    private String argument;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_static", nullable = false)
    private YesNo isStatic;

    @OneToOne
    @JoinColumn(unique = true)
    private AdminPages pageId;

    @OneToOne
    @JoinColumn(unique = true)
    private AdminTabs tabId;

    @OneToMany(mappedBy = "controlId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "adminCrons", "controlId" }, allowSetters = true)
    private Set<AdminControlsMethods> adminControlsMethods = new HashSet<>();

    @OneToMany(mappedBy = "controlId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "controlId", "methodId" }, allowSetters = true)
    private Set<AdminCron> adminCrons = new HashSet<>();

    @OneToMany(mappedBy = "controlId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "controlId", "userId" }, allowSetters = true)
    private Set<AdminOrder> adminOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminControls id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return this.action;
    }

    public AdminControls action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getControlClass() {
        return this.controlClass;
    }

    public AdminControls controlClass(String controlClass) {
        this.setControlClass(controlClass);
        return this;
    }

    public void setControlClass(String controlClass) {
        this.controlClass = controlClass;
    }

    public String getArgument() {
        return this.argument;
    }

    public AdminControls argument(String argument) {
        this.setArgument(argument);
        return this;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Integer getOrder() {
        return this.order;
    }

    public AdminControls order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public YesNo getIsStatic() {
        return this.isStatic;
    }

    public AdminControls isStatic(YesNo isStatic) {
        this.setIsStatic(isStatic);
        return this;
    }

    public void setIsStatic(YesNo isStatic) {
        this.isStatic = isStatic;
    }

    public AdminPages getPageId() {
        return this.pageId;
    }

    public void setPageId(AdminPages adminPages) {
        this.pageId = adminPages;
    }

    public AdminControls pageId(AdminPages adminPages) {
        this.setPageId(adminPages);
        return this;
    }

    public AdminTabs getTabId() {
        return this.tabId;
    }

    public void setTabId(AdminTabs adminTabs) {
        this.tabId = adminTabs;
    }

    public AdminControls tabId(AdminTabs adminTabs) {
        this.setTabId(adminTabs);
        return this;
    }

    public Set<AdminControlsMethods> getAdminControlsMethods() {
        return this.adminControlsMethods;
    }

    public void setAdminControlsMethods(Set<AdminControlsMethods> adminControlsMethods) {
        if (this.adminControlsMethods != null) {
            this.adminControlsMethods.forEach(i -> i.setControlId(null));
        }
        if (adminControlsMethods != null) {
            adminControlsMethods.forEach(i -> i.setControlId(this));
        }
        this.adminControlsMethods = adminControlsMethods;
    }

    public AdminControls adminControlsMethods(Set<AdminControlsMethods> adminControlsMethods) {
        this.setAdminControlsMethods(adminControlsMethods);
        return this;
    }

    public AdminControls addAdminControlsMethods(AdminControlsMethods adminControlsMethods) {
        this.adminControlsMethods.add(adminControlsMethods);
        adminControlsMethods.setControlId(this);
        return this;
    }

    public AdminControls removeAdminControlsMethods(AdminControlsMethods adminControlsMethods) {
        this.adminControlsMethods.remove(adminControlsMethods);
        adminControlsMethods.setControlId(null);
        return this;
    }

    public Set<AdminCron> getAdminCrons() {
        return this.adminCrons;
    }

    public void setAdminCrons(Set<AdminCron> adminCrons) {
        if (this.adminCrons != null) {
            this.adminCrons.forEach(i -> i.setControlId(null));
        }
        if (adminCrons != null) {
            adminCrons.forEach(i -> i.setControlId(this));
        }
        this.adminCrons = adminCrons;
    }

    public AdminControls adminCrons(Set<AdminCron> adminCrons) {
        this.setAdminCrons(adminCrons);
        return this;
    }

    public AdminControls addAdminCron(AdminCron adminCron) {
        this.adminCrons.add(adminCron);
        adminCron.setControlId(this);
        return this;
    }

    public AdminControls removeAdminCron(AdminCron adminCron) {
        this.adminCrons.remove(adminCron);
        adminCron.setControlId(null);
        return this;
    }

    public Set<AdminOrder> getAdminOrders() {
        return this.adminOrders;
    }

    public void setAdminOrders(Set<AdminOrder> adminOrders) {
        if (this.adminOrders != null) {
            this.adminOrders.forEach(i -> i.setControlId(null));
        }
        if (adminOrders != null) {
            adminOrders.forEach(i -> i.setControlId(this));
        }
        this.adminOrders = adminOrders;
    }

    public AdminControls adminOrders(Set<AdminOrder> adminOrders) {
        this.setAdminOrders(adminOrders);
        return this;
    }

    public AdminControls addAdminOrder(AdminOrder adminOrder) {
        this.adminOrders.add(adminOrder);
        adminOrder.setControlId(this);
        return this;
    }

    public AdminControls removeAdminOrder(AdminOrder adminOrder) {
        this.adminOrders.remove(adminOrder);
        adminOrder.setControlId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminControls)) {
            return false;
        }
        return id != null && id.equals(((AdminControls) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminControls{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", controlClass='" + getControlClass() + "'" +
            ", argument='" + getArgument() + "'" +
            ", order=" + getOrder() +
            ", isStatic='" + getIsStatic() + "'" +
            "}";
    }
}
