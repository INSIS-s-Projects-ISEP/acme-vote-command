package com.isep.acme.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String string) {
        super(string);
    }

    public ResourceNotFoundException(final Class<?> clazz, UUID id) {
        super(String.format("Entity %s with id %d not found", clazz.getSimpleName(), id));
    }
}
