import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.ProductFinderService;
import org.example.Product;
import org.example.ISimpleHttpClient;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductFinderServiceTest {

    @Mock
    private ISimpleHttpClient httpClient;

    @InjectMocks
    private ProductFinderService productFinderService;

    @Test
    public void testFindProductDetails_ValidProduct() throws IOException {
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(
                "{\"id\":3, \"title\":\"Mens Cotton Jacket\"}"
        );

        Optional<Product> result = productFinderService.findProductDetails(3);

        assertTrue(result.isPresent());
        assertEquals(3, result.get().getId());
        assertEquals("Mens Cotton Jacket", result.get().getTitle());

        verify(httpClient).doHttpGet("https://fakestoreapi.com/products/3");
    }

    @Test
    public void testFindProductDetails_InvalidProduct() throws IOException {
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn("");
        Optional<Product> result = productFinderService.findProductDetails(300);
        assertFalse(result.isPresent());
        verify(httpClient).doHttpGet("https://fakestoreapi.com/products/300");
    }
}