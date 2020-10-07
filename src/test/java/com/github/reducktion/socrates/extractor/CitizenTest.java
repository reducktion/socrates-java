package com.github.reducktion.socrates.extractor;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class CitizenTest {

    @Test
    void equalsHashCode_shouldComplyWithContract() {
        EqualsVerifier.forClass(Citizen.class).verify();
    }
}
