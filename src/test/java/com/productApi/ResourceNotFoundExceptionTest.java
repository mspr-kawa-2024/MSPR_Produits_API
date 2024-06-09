package com.productApi;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.productApi.config.ResourceNotFoundException;

class ResourceNotFoundExceptionTest {

    @Test
    void testConstructor() {
        String errorMessage = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
