package com.example.sales.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilTests {

    private Util util;

    @BeforeEach
    void setUp() {
        util = new Util(new ObjectMapper());
    }

    @Test
    void parsesJsonToRequestedType() {
        ParsedValue result = util.parseJsonToType("{\"value\":\"sales\"}", ParsedValue.class);

        assertThat(result).isNotNull();
        assertThat(result.value()).isEqualTo("sales");
    }

    @Test
    void removesUtf8BomBeforeParsing() {
        ParsedValue result = util.parseJsonToType("\uFEFF{\"value\":\"sales\"}", ParsedValue.class);

        assertThat(result).isEqualTo(new ParsedValue("sales"));
    }

    @Test
    void returnsNullForNullOrInvalidJson() {
        assertThat(util.parseJsonToType(null, ParsedValue.class)).isNull();
        assertThat(util.parseJsonToType("not-json", ParsedValue.class)).isNull();
    }

    private record ParsedValue(String value) {
    }
}
