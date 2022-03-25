package com.example.apelsintask.entity;

import com.example.apelsintask.entity.template.AbsEntity;
import com.example.apelsintask.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    //    @CreatedDate = new Date(LocalDate.now().get(ChronoField.EPOCH_DAY))
//    @Column(nullable = false)
    private Date date ;

}
