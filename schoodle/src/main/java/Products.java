package classes;
import java.util.Date;

public class Products {
    private int productId;
    private String productName;
    private double price;
    private Date warrantyDate;

    public Products(int productId, String productName, double price, Date warrantyDate) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.warrantyDate = warrantyDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(Date warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price
                + ", warrantyDate=" + warrantyDate + "]";
    }
}
