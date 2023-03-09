package model;
import java.util.Date;

public class Products {
    private int userID;
    private int productId;
    private String productName;
    private double price;
    private String warrantyDate;

    public Products(int productId,int userID, String productName, double price, String warrantyDate) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.warrantyDate = warrantyDate;
        this.userID = userID;
    }
    public int getUserId() {
        return userID;
    }

    public void setUserId(int userID) {
        this.userID = userID;
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

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price
                + ", warrantyDate=" + warrantyDate + "]";
    }
}
