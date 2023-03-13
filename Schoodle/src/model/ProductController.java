package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private CSVHandler csvHandler;

    public ProductController(CSVHandler csvHandler) {
        this.csvHandler = csvHandler;
    }
    public int getNextProductID() throws IOException {
        List<String[]> data = csvHandler.readAll();
        int maxProductID = 9999; // start projectID at 1000
        for (String[] row : data) {
            int currentId = Integer.parseInt(row[1]);
            if (currentId > maxProductID) {
                maxProductID = currentId;
            }
        }
        return maxProductID + 1;
    }

    public void addProduct(Products addProduct) throws IOException {
        
        List<String[]> data = csvHandler.readAll();
        String[] newProduct = { Integer.toString(addProduct.getProductId()),Integer.toString(addProduct.getUserId()), addProduct.getProductName(),
            Double.toString(addProduct.getPrice()), addProduct.getWarrantyDate().toString() };
        data.add(newProduct);
        csvHandler.writeAll(data);

    }

    public void deleteProduct(int productId) throws IOException {
        List<String[]> data = csvHandler.readAll();

        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            int currentId = Integer.parseInt(row[0]);

            if (currentId == productId) {
                data.remove(i);
                csvHandler.writeAll(data);
                return;
            }
        } 
         
    }

    public List<Products> getAllProducts(int userId) throws IOException {
        List<String[]> productData = null;
        try {
            productData = csvHandler.readAll();
        } catch (IOException e) {
            // Handle IO exception
            e.printStackTrace();
        }
    
        List<Products> products = new ArrayList<>();
    
        if (productData != null) {
            for (String[] productFields : productData) {
                int productID = Integer.parseInt(productFields[0]);
                int userID = Integer.parseInt(productFields[1]);
                String name = productFields[2];
                double price = Double.parseDouble(productFields[3]);
                String warrantyDate = productFields[4];
                
    
                if (userID == userId) {
                    Products product = new Products(productID, userID, name, price,warrantyDate);
                    products.add(product);
                }
            }
        }
    
        return products;
    }
}
