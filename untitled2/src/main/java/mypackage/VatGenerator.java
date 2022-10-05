package mypackage;

import mypackage.model.Vat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VatGenerator {

    int index;

    BigDecimal amountInVat;

    BigDecimal amountInTax;

    public VatGenerator(final int index, final BigDecimal amountInVat, final BigDecimal amountInTax) {
        this.index = index;
        this.amountInVat = amountInVat;
        this.amountInTax = amountInTax;
    }

    public List<Vat> generateVats() {
        final List<Vat> vatLines = new ArrayList<>();
        int indexInVat = 1;
        final VatPercentage firstVatPercentage = new VatPercentage(60, 20);
        final VatPercentage secondVatPercentage = new VatPercentage(40, 10);
        final VatPercentage thirdVatPercentage = new VatPercentage(100, 20);
        BigDecimal amountInVat1 = amountInVat.multiply(
                new BigDecimal(firstVatPercentage.taxedAmountPercentage).divide(new BigDecimal(100), 2,
                        RoundingMode.HALF_UP));
        amountInVat1 = amountInVat1.multiply(new BigDecimal(firstVatPercentage.getPercentage()))
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        BigDecimal amountInVat2 = amountInVat.multiply(
                new BigDecimal(secondVatPercentage.getTaxedAmountPercentage()).divide(new BigDecimal(100), 2,
                        RoundingMode.HALF_UP));
        amountInVat2 = amountInVat2.multiply(new BigDecimal(secondVatPercentage.getPercentage()))
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        final BigDecimal amountInVat3 = amountInTax.multiply(new BigDecimal(thirdVatPercentage.getPercentage()))
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        vatLines.add(new Vat(indexInVat, index, 0, firstVatPercentage.getTaxedAmountPercentage(),
                firstVatPercentage.getPercentage(), amountInVat1));
        vatLines.add(
                new Vat(indexInVat++ + 1, index, 0, secondVatPercentage.getTaxedAmountPercentage(),
                        secondVatPercentage.getPercentage(), amountInVat2));
        vatLines.add(new Vat(indexInVat++ + 1, 0, index, thirdVatPercentage.getTaxedAmountPercentage(),
                thirdVatPercentage.getPercentage(),
                amountInVat3));
        return vatLines;
    }

}

