package io.github.arasdenizhan.payment.demo.datamodel;

import io.github.arasdenizhan.payment.demo.datamodel.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Audited
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
public class CardPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @LastModifiedDate
    private long modifiedDate;

    private String token;

    private BigDecimal amount;

    private String providerId;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
