package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminPages} entity.
 */
public class AdminPagesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer fId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String url;

    @NotNull
    @Size(max = 255)
    private String icon;

    @NotNull
    private Integer order;

    @NotNull
    private Boolean pageMapReorders;

    @NotNull
    private YesNo oneRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getPageMapReorders() {
        return pageMapReorders;
    }

    public void setPageMapReorders(Boolean pageMapReorders) {
        this.pageMapReorders = pageMapReorders;
    }

    public YesNo getOneRecord() {
        return oneRecord;
    }

    public void setOneRecord(YesNo oneRecord) {
        this.oneRecord = oneRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminPagesDTO)) {
            return false;
        }

        AdminPagesDTO adminPagesDTO = (AdminPagesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminPagesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminPagesDTO{" +
            "id=" + getId() +
            ", fId=" + getfId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            ", icon='" + getIcon() + "'" +
            ", order=" + getOrder() +
            ", pageMapReorders='" + getPageMapReorders() + "'" +
            ", oneRecord='" + getOneRecord() + "'" +
            "}";
    }
}
