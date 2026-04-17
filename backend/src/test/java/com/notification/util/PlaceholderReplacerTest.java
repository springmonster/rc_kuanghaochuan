package com.notification.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlaceholderReplacerTest {

    @Test
    void shouldReplacePlaceholderWithValue() {
        String template = "Hello {{name}}";
        Map<String, String> values = new HashMap<>();
        values.put("name", "World");

        String result = PlaceholderReplacer.replace(template, values);

        assertEquals("Hello World", result);
    }

    @Test
    void shouldReplaceMultiplePlaceholders() {
        String template = "User {{user_id}} purchased {{product}}";
        Map<String, String> values = new HashMap<>();
        values.put("user_id", "123");
        values.put("product", "Laptop");

        String result = PlaceholderReplacer.replace(template, values);

        assertEquals("User 123 purchased Laptop", result);
    }

    @Test
    void shouldKeepUnknownPlaceholderAsIs() {
        String template = "Hello {{unknown}}";
        Map<String, String> values = new HashMap<>();
        values.put("name", "World");

        String result = PlaceholderReplacer.replace(template, values);

        assertEquals("Hello {{unknown}}", result);
    }

    @Test
    void shouldHandleNullTemplate() {
        String result = PlaceholderReplacer.replace(null, new HashMap<>());
        assertNull(result);
    }

    @Test
    void shouldHandleNullValues() {
        String template = "Hello {{name}}";
        String result = PlaceholderReplacer.replace(template, null);
        assertEquals("Hello {{name}}", result);
    }

    @Test
    void shouldHandleEmptyTemplate() {
        String result = PlaceholderReplacer.replace("", new HashMap<>());
        assertEquals("", result);
    }

    @Test
    void shouldHandleEmptyValues() {
        String template = "Hello {{name}}";
        String result = PlaceholderReplacer.replace(template, new HashMap<>());
        assertEquals("Hello {{name}}", result);
    }
}
