package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Lang} entity.
 */
public class LangDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String key;

    @NotNull
    @Size(max = 255)
    private String esp;

    @NotNull
    @Size(max = 255)
    private String eng;

    @NotNull
    @Size(max = 255)
    private String order;

    @NotNull
    private Integer pId;

    @NotNull
    @Size(max = 255)
    private String zh;

    @NotNull
    @Size(max = 255)
    private String ru;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LangDTO)) {
            return false;
        }

        LangDTO langDTO = (LangDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, langDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LangDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", esp='" + getEsp() + "'" +
            ", eng='" + getEng() + "'" +
            ", order='" + getOrder() + "'" +
            ", pId=" + getpId() +
            ", zh='" + getZh() + "'" +
            ", ru='" + getRu() + "'" +
            "}";
    }
}
