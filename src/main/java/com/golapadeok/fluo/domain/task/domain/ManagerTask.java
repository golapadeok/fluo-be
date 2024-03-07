package com.golapadeok.fluo.domain.task.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.customer.domain.Customer;
import jakarta.persistence.*;

@Entity
public class ManagerTask extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "MANAGER_TASK_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

}
