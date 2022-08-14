package io.github.jhipster.sample.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @NotNull
    @Size(max = 255)
    @Column(name = "title_en", length = 255, nullable = false)
    private String titleEn;

    @NotNull
    @Size(max = 255)
    @Column(name = "title_es", length = 255, nullable = false)
    private String titleEs;

    @Lob
    @Column(name = "content_en", nullable = false)
    private byte[] contentEn;

    @NotNull
    @Column(name = "content_en_content_type", nullable = false)
    private String contentEnContentType;

    @Lob
    @Column(name = "content_es", nullable = false)
    private byte[] contentEs;

    @NotNull
    @Column(name = "content_es_content_type", nullable = false)
    private String contentEsContentType;

    @NotNull
    @Size(max = 255)
    @Column(name = "title_ru", length = 255, nullable = false)
    private String titleRu;

    @NotNull
    @Size(max = 255)
    @Column(name = "title_zh", length = 255, nullable = false)
    private String titleZh;

    @Lob
    @Column(name = "content_ru", nullable = false)
    private byte[] contentRu;

    @NotNull
    @Column(name = "content_ru_content_type", nullable = false)
    private String contentRuContentType;

    @Lob
    @Column(name = "content_zh", nullable = false)
    private byte[] contentZh;

    @NotNull
    @Column(name = "content_zh_content_type", nullable = false)
    private String contentZhContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Content id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public Content url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitleEn() {
        return this.titleEn;
    }

    public Content titleEn(String titleEn) {
        this.setTitleEn(titleEn);
        return this;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleEs() {
        return this.titleEs;
    }

    public Content titleEs(String titleEs) {
        this.setTitleEs(titleEs);
        return this;
    }

    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs;
    }

    public byte[] getContentEn() {
        return this.contentEn;
    }

    public Content contentEn(byte[] contentEn) {
        this.setContentEn(contentEn);
        return this;
    }

    public void setContentEn(byte[] contentEn) {
        this.contentEn = contentEn;
    }

    public String getContentEnContentType() {
        return this.contentEnContentType;
    }

    public Content contentEnContentType(String contentEnContentType) {
        this.contentEnContentType = contentEnContentType;
        return this;
    }

    public void setContentEnContentType(String contentEnContentType) {
        this.contentEnContentType = contentEnContentType;
    }

    public byte[] getContentEs() {
        return this.contentEs;
    }

    public Content contentEs(byte[] contentEs) {
        this.setContentEs(contentEs);
        return this;
    }

    public void setContentEs(byte[] contentEs) {
        this.contentEs = contentEs;
    }

    public String getContentEsContentType() {
        return this.contentEsContentType;
    }

    public Content contentEsContentType(String contentEsContentType) {
        this.contentEsContentType = contentEsContentType;
        return this;
    }

    public void setContentEsContentType(String contentEsContentType) {
        this.contentEsContentType = contentEsContentType;
    }

    public String getTitleRu() {
        return this.titleRu;
    }

    public Content titleRu(String titleRu) {
        this.setTitleRu(titleRu);
        return this;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitleZh() {
        return this.titleZh;
    }

    public Content titleZh(String titleZh) {
        this.setTitleZh(titleZh);
        return this;
    }

    public void setTitleZh(String titleZh) {
        this.titleZh = titleZh;
    }

    public byte[] getContentRu() {
        return this.contentRu;
    }

    public Content contentRu(byte[] contentRu) {
        this.setContentRu(contentRu);
        return this;
    }

    public void setContentRu(byte[] contentRu) {
        this.contentRu = contentRu;
    }

    public String getContentRuContentType() {
        return this.contentRuContentType;
    }

    public Content contentRuContentType(String contentRuContentType) {
        this.contentRuContentType = contentRuContentType;
        return this;
    }

    public void setContentRuContentType(String contentRuContentType) {
        this.contentRuContentType = contentRuContentType;
    }

    public byte[] getContentZh() {
        return this.contentZh;
    }

    public Content contentZh(byte[] contentZh) {
        this.setContentZh(contentZh);
        return this;
    }

    public void setContentZh(byte[] contentZh) {
        this.contentZh = contentZh;
    }

    public String getContentZhContentType() {
        return this.contentZhContentType;
    }

    public Content contentZhContentType(String contentZhContentType) {
        this.contentZhContentType = contentZhContentType;
        return this;
    }

    public void setContentZhContentType(String contentZhContentType) {
        this.contentZhContentType = contentZhContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        return id != null && id.equals(((Content) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", titleEn='" + getTitleEn() + "'" +
            ", titleEs='" + getTitleEs() + "'" +
            ", contentEn='" + getContentEn() + "'" +
            ", contentEnContentType='" + getContentEnContentType() + "'" +
            ", contentEs='" + getContentEs() + "'" +
            ", contentEsContentType='" + getContentEsContentType() + "'" +
            ", titleRu='" + getTitleRu() + "'" +
            ", titleZh='" + getTitleZh() + "'" +
            ", contentRu='" + getContentRu() + "'" +
            ", contentRuContentType='" + getContentRuContentType() + "'" +
            ", contentZh='" + getContentZh() + "'" +
            ", contentZhContentType='" + getContentZhContentType() + "'" +
            "}";
    }
}
