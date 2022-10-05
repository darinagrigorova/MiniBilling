package mypackage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_line")
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @Column(name = "product")
    private String product;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "price_list")
    private int priceList;
    @Column(name = "amount")
    private BigDecimal amount;

    public InvoiceLine() {
    }

    public InvoiceLine(final int index, final BigDecimal quantity, final LocalDateTime start, final LocalDateTime end,
                       final String product,
                       final BigDecimal price, final int priceList, final BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        this.start = start;
        this.end = end;
        this.product = product;
        this.price = price;
        this.priceList = priceList;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getPriceList() {
        return priceList;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

