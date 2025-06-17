package empresa.models;

public class StockViewModel {
    private Long productId;
    private String productName;
    private String warehouseName;
    private int stock;

    public StockViewModel(Long productId, String productName, String warehouseName, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.warehouseName = warehouseName;
        this.stock = stock;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public int getStock() {
        return stock;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

