package mypackage.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "invoice")
public class Invoice {

    @Column(name = "document_date")
    private LocalDateTime documentDate;
    @Id
    @Column(name = "document_number")
    private String documentNumber;
    @Column(name = "consumer")
    private String consumer;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "total_amount_with_vat")
    private BigDecimal totalAmountWithVat;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_number",
            nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<InvoiceLine> lines;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_number",
            nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vat> vatsLines;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_number",
            nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Taxes> taxesLines;
    private static long counter = 10000;

    public Invoice() {
    }

    public Invoice(final LocalDateTime documentDate, final String documentNumber, final String consumer,
                   final BigDecimal totalAmount, final BigDecimal totalAmountWithVat,
                   final List<InvoiceLine> lines, final List<Vat> vatsLines, final List<Taxes> taxesLines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.taxesLines = taxesLines;
        this.vatsLines = vatsLines;
        counter++;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(counter);
    }

    public String getConsumer() {
        return consumer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalAmountWithVat() {
        return totalAmountWithVat;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public List<Taxes> getTaxesLines() {
        return taxesLines;
    }

    public List<Vat> getVatsLines() {
        return vatsLines;
    }

}

