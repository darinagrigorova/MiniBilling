package mypackage;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuantityPricePeriod {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final BigDecimal price;
    private final BigDecimal quantity;

    private final String product;

    public QuantityPricePeriod(final LocalDateTime start, final LocalDateTime end, final BigDecimal price,
                               final String product,
                               final BigDecimal quantity) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getProduct() {
        return product;
    }
}

