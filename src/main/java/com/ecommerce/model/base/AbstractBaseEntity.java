package com.ecommerce.model.base;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
public abstract class AbstractBaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 270383254869437136L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "updated")
    private LocalDateTime updateDate;


    @PrePersist
    public void toCreate() {
        setCreateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void toUpdate() {
        updateDate = LocalDateTime.now();
    }


}
