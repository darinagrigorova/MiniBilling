package mypackage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private ZonedDateTime time;
    @Column(name = "pokazanie")
    private BigDecimal value;

    @Column(name = "product")
    private String product;

    @ManyToOne
    @JoinColumn(name = "user_ref",
            insertable = false,
            updatable = false)
    private User user;

    public Reading() {
    }

    public Reading(final ZonedDateTime time, final BigDecimal value, final User user, final String product) {
        this.time = time;
        this.value = value;
        this.user = user;
        this.product = product;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(final ZonedDateTime time) {
        this.time = time;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(final String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Reading{" +
                "time=" + time +
                ", value=" + value +
                ", user=" + user +
                ", product='" + product + '\'' +
                ", userRef=" +
                '}';
    }
}
