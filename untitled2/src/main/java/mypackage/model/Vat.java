package mypackage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "vat")
public class Vat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name = "lines")
    private int lines;
    @Column(name = "taxes")
    private int taxes;
    @Column(name = "taxed_amount_percentage")
    private int taxedAmountPercentage;
    @Column(name = "percentage")
    private int percentage;
    @Column(name = "amount")
    private BigDecimal amount;

    public Vat() {
    }

    public Vat(final int index, final int lines, final int taxes, final int taxedAmountPercentage, final int percentage,
               final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.taxes = taxes;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = percentage;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public int getLines() {
        return lines;
    }

    public int getTaxes() {
        return taxes;
    }

    public int getTaxedAmountPercentage() {
        return taxedAmountPercentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

