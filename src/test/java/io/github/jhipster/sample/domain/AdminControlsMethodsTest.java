package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminControlsMethodsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminControlsMethods.class);
        AdminControlsMethods adminControlsMethods1 = new AdminControlsMethods();
        adminControlsMethods1.setId(1L);
        AdminControlsMethods adminControlsMethods2 = new AdminControlsMethods();
        adminControlsMethods2.setId(adminControlsMethods1.getId());
        assertThat(adminControlsMethods1).isEqualTo(adminControlsMethods2);
        adminControlsMethods2.setId(2L);
        assertThat(adminControlsMethods1).isNotEqualTo(adminControlsMethods2);
        adminControlsMethods1.setId(null);
        assertThat(adminControlsMethods1).isNotEqualTo(adminControlsMethods2);
    }
}
