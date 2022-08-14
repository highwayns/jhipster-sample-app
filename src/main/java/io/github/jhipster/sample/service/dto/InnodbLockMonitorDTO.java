package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.InnodbLockMonitor} entity.
 */
public class InnodbLockMonitorDTO implements Serializable {

    private Long id;

    private Integer a;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InnodbLockMonitorDTO)) {
            return false;
        }

        InnodbLockMonitorDTO innodbLockMonitorDTO = (InnodbLockMonitorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, innodbLockMonitorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InnodbLockMonitorDTO{" +
            "id=" + getId() +
            ", a=" + getA() +
            "}";
    }
}
