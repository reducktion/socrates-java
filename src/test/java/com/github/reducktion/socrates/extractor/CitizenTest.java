package com.github.reducktion.socrates.extractor;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CitizenTest {

    @Test
    void equalsHashCode_shouldComplyWithContract() {
        EqualsVerifier.forClass(Citizen.class).verify();
    }
}
