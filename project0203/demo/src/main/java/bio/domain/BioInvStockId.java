package bio.domain;

import java.io.Serializable;
import java.util.Objects;

public class BioInvStockId implements Serializable {

    private String productCode;
    private String warehouseLocation;

    // 기본 생성자
    public BioInvStockId() {}

    // 생성자
    public BioInvStockId(String productCode, String warehouseLocation) {
        this.productCode = productCode;
        this.warehouseLocation = warehouseLocation;
    }

    // equals와 hashCode 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BioInvStockId that = (BioInvStockId) o;
        return Objects.equals(productCode, that.productCode) &&
                Objects.equals(warehouseLocation, that.warehouseLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, warehouseLocation);
    }
}
