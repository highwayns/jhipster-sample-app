package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminCron} entity.
 */
public class AdminCronDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String day;

    @NotNull
    @Size(max = 30)
    private String month;

    @NotNull
    @Size(max = 30)
    private String year;

    @NotNull
    @Size(max = 255)
    private String sendCondition;

    private AdminControlsDTO controlId;

    private AdminControlsMethodsDTO methodId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSendCondition() {
        return sendCondition;
    }

    public void setSendCondition(String sendCondition) {
        this.sendCondition = sendCondition;
    }

    public AdminControlsDTO getControlId() {
        return controlId;
    }

    public void setControlId(AdminControlsDTO controlId) {
        this.controlId = controlId;
    }

    public AdminControlsMethodsDTO getMethodId() {
        return methodId;
    }

    public void setMethodId(AdminControlsMethodsDTO methodId) {
        this.methodId = methodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminCronDTO)) {
            return false;
        }

        AdminCronDTO adminCronDTO = (AdminCronDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminCronDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminCronDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            ", sendCondition='" + getSendCondition() + "'" +
            ", controlId=" + getControlId() +
            ", methodId=" + getMethodId() +
            "}";
    }
}
