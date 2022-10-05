package mypackage.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Column(name = "name")
    private String name;
    @Id
    @Column(name = "ref")
    private String ref;

    @Column(name = "price_list_num")
    private int priceListNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "price_list_num",
            insertable = false,
            updatable = false)
    private PriceList priceList;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Reading> readingList;

    public User() {
    }

    public User(final String name, final String ref, final int priceListNumber) {
        this.name = name;
        this.ref = ref;
        this.priceListNumber = priceListNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(final String ref) {
        this.ref = ref;
    }

    public int getPriceListNumber() {
        return priceListNumber;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public List<Reading> getReadingList() {
        return readingList;
    }

    @Override
    public String toString() {
        return "mypackage.User{" +
                "name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", priceListNumber=" +
                '}';
    }
}
