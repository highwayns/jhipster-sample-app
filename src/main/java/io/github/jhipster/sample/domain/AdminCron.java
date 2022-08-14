package io.github.jhipster.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdminCron.
 */
@Entity
@Table(name = "admin_cron")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminCron implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "day", length = 30, nullable = false)
    private String day;

    @NotNull
    @Size(max = 30)
    @Column(name = "month", length = 30, nullable = false)
    private String month;

    @NotNull
    @Size(max = 30)
    @Column(name = "year", length = 30, nullable = false)
    private String year;

    @NotNull
    @Size(max = 255)
    @Column(name = "send_condition", length = 255, nullable = false)
    private String sendCondition;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pageId", "tabId", "adminControlsMethods", "adminCrons", "adminOrders" }, allowSetters = true)
    private AdminControls controlId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "adminCrons", "controlId" }, allowSetters = true)
    private AdminControlsMethods methodId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminCron id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return this.day;
    }

    public AdminCron day(String day) {
        this.setDay(day);
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return this.month;
    }

    public AdminCron month(String month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return this.year;
    }

    public AdminCron year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSendCondition() {
        return this.sendCondition;
    }

    public AdminCron sendCondition(String sendCondition) {
        this.setSendCondition(sendCondition);
        return this;
    }

    public void setSendCondition(String sendCondition) {
        this.sendCondition = sendCondition;
    }

    public AdminControls getControlId() {
        return this.controlId;
    }

    public void setControlId(AdminControls adminControls) {
        this.controlId = adminControls;
    }

    public AdminCron controlId(AdminControls adminControls) {
        this.setControlId(adminControls);
        return this;
    }

    public AdminControlsMethods getMethodId() {
        return this.methodId;
    }

    public void setMethodId(AdminControlsMethods adminControlsMethods) {
        this.methodId = adminControlsMethods;
    }

    public AdminCron methodId(AdminControlsMethods adminControlsMethods) {
        this.setMethodId(adminControlsMethods);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminCron)) {
            return false;
        }
        return id != null && id.equals(((AdminCron) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminCron{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            ", sendCondition='" + getSendCondition() + "'" +
            "}";
    }
}
