package com.example.apelsintask.entity;

import com.example.apelsintask.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Customer extends AbsNameEntity {

    @Length(max = 3)
    @Column(nullable = false)
    private String country;

    private String address;

    @Length(max = 50)
    private String phone;
}
