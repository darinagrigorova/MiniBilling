package mypackage.repository;

import mypackage.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    @Override
    Invoice saveAndFlush(Invoice invoice);
}
