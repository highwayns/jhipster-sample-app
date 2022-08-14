package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LangTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lang.class);
        Lang lang1 = new Lang();
        lang1.setId(1L);
        Lang lang2 = new Lang();
        lang2.setId(lang1.getId());
        assertThat(lang1).isEqualTo(lang2);
        lang2.setId(2L);
        assertThat(lang1).isNotEqualTo(lang2);
        lang1.setId(null);
        assertThat(lang1).isNotEqualTo(lang2);
    }
}
