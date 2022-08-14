package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminControlsMethods.
 */
@Entity
@Table(name = "admin_controls_methods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminControlsMethods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "method", length = 100)
    private String method;

    @NotNull
    @Size(max = 255)
    @Column(name = "argument", length = 255, nullable = false)
    private String argument;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Column(name = "p_id", nullable = false)
    private Integer pId;

    @OneToMany(mappedBy = "methodId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "controlId", "methodId" }, allowSetters = true)
    private Set<AdminCron> adminCrons = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "pageId", "tabId", "adminControlsMethods", "adminCrons", "adminOrders" }, allowSetters = true)
    private AdminControls controlId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminControlsMethods id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return this.method;
    }

    public AdminControlsMethods method(String method) {
        this.setMethod(method);
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getArgument() {
        return this.argument;
    }

    public AdminControlsMethods argument(String argument) {
        this.setArgument(argument);
        return this;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Integer getOrder() {
        return this.order;
    }

    public AdminControlsMethods order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getpId() {
        return this.pId;
    }

    public AdminControlsMethods pId(Integer pId) {
        this.setpId(pId);
        return this;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Set<AdminCron> getAdminCrons() {
        return this.adminCrons;
    }

    public void setAdminCrons(Set<AdminCron> adminCrons) {
        if (this.adminCrons != null) {
            this.adminCrons.forEach(i -> i.setMethodId(null));
        }
        if (adminCrons != null) {
            adminCrons.forEach(i -> i.setMethodId(this));
        }
        this.adminCrons = adminCrons;
    }

    public AdminControlsMethods adminCrons(Set<AdminCron> adminCrons) {
        this.setAdminCrons(adminCrons);
        return this;
    }

    public AdminControlsMethods addAdminCron(AdminCron adminCron) {
        this.adminCrons.add(adminCron);
        adminCron.setMethodId(this);
        return this;
    }

    public AdminControlsMethods removeAdminCron(AdminCron adminCron) {
        this.adminCrons.remove(adminCron);
        adminCron.setMethodId(null);
        return this;
    }

    public AdminControls getControlId() {
        return this.controlId;
    }

    public void setControlId(AdminControls adminControls) {
        this.controlId = adminControls;
    }

    public AdminControlsMethods controlId(AdminControls adminControls) {
        this.setControlId(adminControls);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminControlsMethods)) {
            return false;
        }
        return id != null && id.equals(((AdminControlsMethods) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminControlsMethods{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            ", argument='" + getArgument() + "'" +
            ", order=" + getOrder() +
            ", pId=" + getpId() +
            "}";
    }
}
