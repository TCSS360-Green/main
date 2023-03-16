package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.CSVHandler;
import model.ProductController;
import model.Products;

public class ProductControllerTest {

    private ProductController controller;
    private CSVHandler csvHandler;
    private static final String TEST_CSV_FILE = "test-products.csv";
    @Before
    public void setUp() {
        csvHandler = new CSVHandler(TEST_CSV_FILE);
        controller = new ProductController(csvHandler);
    }
    @After
    public void tearDown() {
        File testCsvFile = new File(TEST_CSV_FILE);
        if (testCsvFile.exists()) {
            testCsvFile.delete();
        }
    }
    @Test
    public void testAddProduct() throws IOException {
        Products product = new Products(1, 1, "Product A", 100.0, "2023-03-16");
        controller.addProduct(product);

        List<String[]> data = csvHandler.readAll();
        String[] lastRow = data.get(data.size() - 1);

        assertEquals("1", lastRow[0]);
        assertEquals("1", lastRow[1]);
        assertEquals("Product A", lastRow[2]);
        assertEquals("100.0", lastRow[3]);
        assertEquals("2023-03-16", lastRow[4]);
    }

    @Test
    public void testDeleteProduct() throws IOException {
        Products product = new Products(2, 1, "Product B", 200.0, "2023-03-16");
        controller.addProduct(product);

        controller.deleteProduct(2);

        List<String[]> data = csvHandler.readAll();
        for (String[] row : data) {
            assertFalse(row[0].equals("2"));
        }
    }

    @Test
    public void testGetAllProducts() throws IOException {
        Products product1 = new Products(3, 1, "Product C", 300.0, "2023-03-16");
        Products product2 = new Products(4, 2, "Product D", 400.0, "2023-03-16");
        controller.addProduct(product1);
        controller.addProduct(product2);

        List<Products> products = controller.getAllProducts(2);
        assertEquals(1, products.size());
        assertTrue(products.get(0).getProductName().equals("Product D"));
        assertEquals(4, products.get(0).getProductId());
        assertEquals(2, products.get(0).getUserId());
        assertEquals(400.0, products.get(0).getPrice(), 0.0);
        assertEquals("2023-03-16", products.get(0).getWarrantyDate());
    }
}

