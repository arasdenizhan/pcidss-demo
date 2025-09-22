package io.github.arasdenizhan.payment.demo.repository;

import io.github.arasdenizhan.payment.demo.datamodel.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<CardPayment, Long> {
}
