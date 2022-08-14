package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AdminTabs} entity.
 */
public class AdminTabsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Integer order;

    @NotNull
    @Size(max = 255)
    private String icon;

    @NotNull
    @Size(max = 255)
    private String url;

    @NotNull
    private YesNo hidden;

    @NotNull
    private YesNo isCtrlPanel;

    @NotNull
    private Integer forGroup;

    @NotNull
    private YesNo oneRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public YesNo getHidden() {
        return hidden;
    }

    public void setHidden(YesNo hidden) {
        this.hidden = hidden;
    }

    public YesNo getIsCtrlPanel() {
        return isCtrlPanel;
    }

    public void setIsCtrlPanel(YesNo isCtrlPanel) {
        this.isCtrlPanel = isCtrlPanel;
    }

    public Integer getForGroup() {
        return forGroup;
    }

    public void setForGroup(Integer forGroup) {
        this.forGroup = forGroup;
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
        if (!(o instanceof AdminTabsDTO)) {
            return false;
        }

        AdminTabsDTO adminTabsDTO = (AdminTabsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminTabsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminTabsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", icon='" + getIcon() + "'" +
            ", url='" + getUrl() + "'" +
            ", hidden='" + getHidden() + "'" +
            ", isCtrlPanel='" + getIsCtrlPanel() + "'" +
            ", forGroup=" + getForGroup() +
            ", oneRecord='" + getOneRecord() + "'" +
            "}";
    }
}
