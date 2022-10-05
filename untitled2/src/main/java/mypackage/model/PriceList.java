package mypackage.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "price_list")
public class PriceList {

    @Id
    @Column(name = "num")
    private int num;

    @OneToMany(mappedBy = "priceList")
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Price> prices;

    public PriceList() {
    }

    public PriceList(final int num, final List<Price> prices) {
        this.num = num;
        this.prices = prices;
    }

    public int getNum() {
        return num;
    }

    public void setNum(final int num) {
        this.num = num;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(final List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "num=" + num +
                '}';
    }
}

