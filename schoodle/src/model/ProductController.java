package model;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductController {
    private List<Products> products;
    private CSVHandler csvHandler;

    public ProductController(CSVHandler csvHandler) throws IOException, ParseException {
        this.csvHandler = csvHandler;
        List<String[]> data = csvHandler.readAll();
        this.products = new ArrayList<>();
        for (String[] productData : data) {
            int productId = Integer.parseInt(productData[0]);
            String productName = productData[1];
            double price = Double.parseDouble(productData[2]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date warrantyDate = dateFormat.parse(productData[3]);
            Products product = new Products(productId, productName, price, warrantyDate);
            products.add(product);
        }
    }

    public void addProduct(String productName, double price, Date warrantyDate) throws IOException {
        int maxId = 0;
        for (Products product : products) {
            if (product.getProductId() > maxId) {
                maxId = product.getProductId();
            }
        }

        Products newProduct = new Products(maxId + 1, productName, price, warrantyDate);
        products.add(newProduct);

        List<String[]> data = new ArrayList<>();
        for (Products product : products) {
            String[] productData = { Integer.toString(product.getProductId()), product.getProductName(),
                    Double.toString(product.getPrice()), product.getWarrantyDate().toString() };
            data.add(productData);
        }

        csvHandler.writeAll(data);
    }

    public void updateProduct(int productId, String productName, double price, Date warrantyDate) throws IOException {
        for (Products product : products) {
            if (product.getProductId() == productId) {
                product.setProductName(productName);
                product.setPrice(price);
                product.setWarrantyDate(warrantyDate);
                break;
            }
        }

        List<String[]> data = new ArrayList<>();
        for (Products product : products) {
            String[] productData = { Integer.toString(product.getProductId()), product.getProductName(),
                    Double.toString(product.getPrice()), product.getWarrantyDate().toString() };
            data.add(productData);
        }

        csvHandler.writeAll(data);
    }

    public void deleteProduct(int productId) throws IOException {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == productId) {
                products.remove(i);
                break;
            }
        }

        List<String[]> data = new ArrayList<>();
        for (Products product : products) {
            String[] productData = { Integer.toString(product.getProductId()), product.getProductName(),
                    Double.toString(product.getPrice()), product.getWarrantyDate().toString() };
            data.add(productData);
        }

        csvHandler.writeAll(data);
    }
}
