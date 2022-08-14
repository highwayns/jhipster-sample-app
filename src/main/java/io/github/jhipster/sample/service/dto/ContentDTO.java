package io.github.jhipster.sample.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.Content} entity.
 */
public class ContentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String url;

    @NotNull
    @Size(max = 255)
    private String titleEn;

    @NotNull
    @Size(max = 255)
    private String titleEs;

    @Lob
    private byte[] contentEn;

    private String contentEnContentType;

    @Lob
    private byte[] contentEs;

    private String contentEsContentType;

    @NotNull
    @Size(max = 255)
    private String titleRu;

    @NotNull
    @Size(max = 255)
    private String titleZh;

    @Lob
    private byte[] contentRu;

    private String contentRuContentType;

    @Lob
    private byte[] contentZh;

    private String contentZhContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs;
    }

    public byte[] getContentEn() {
        return contentEn;
    }

    public void setContentEn(byte[] contentEn) {
        this.contentEn = contentEn;
    }

    public String getContentEnContentType() {
        return contentEnContentType;
    }

    public void setContentEnContentType(String contentEnContentType) {
        this.contentEnContentType = contentEnContentType;
    }

    public byte[] getContentEs() {
        return contentEs;
    }

    public void setContentEs(byte[] contentEs) {
        this.contentEs = contentEs;
    }

    public String getContentEsContentType() {
        return contentEsContentType;
    }

    public void setContentEsContentType(String contentEsContentType) {
        this.contentEsContentType = contentEsContentType;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitleZh() {
        return titleZh;
    }

    public void setTitleZh(String titleZh) {
        this.titleZh = titleZh;
    }

    public byte[] getContentRu() {
        return contentRu;
    }

    public void setContentRu(byte[] contentRu) {
        this.contentRu = contentRu;
    }

    public String getContentRuContentType() {
        return contentRuContentType;
    }

    public void setContentRuContentType(String contentRuContentType) {
        this.contentRuContentType = contentRuContentType;
    }

    public byte[] getContentZh() {
        return contentZh;
    }

    public void setContentZh(byte[] contentZh) {
        this.contentZh = contentZh;
    }

    public String getContentZhContentType() {
        return contentZhContentType;
    }

    public void setContentZhContentType(String contentZhContentType) {
        this.contentZhContentType = contentZhContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDTO)) {
            return false;
        }

        ContentDTO contentDTO = (ContentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", titleEn='" + getTitleEn() + "'" +
            ", titleEs='" + getTitleEs() + "'" +
            ", contentEn='" + getContentEn() + "'" +
            ", contentEs='" + getContentEs() + "'" +
            ", titleRu='" + getTitleRu() + "'" +
            ", titleZh='" + getTitleZh() + "'" +
            ", contentRu='" + getContentRu() + "'" +
            ", contentZh='" + getContentZh() + "'" +
            "}";
    }
}
