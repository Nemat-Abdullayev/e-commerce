package com.ecommerce.model;

import com.ecommerce.enums.RoleName;
import com.ecommerce.model.base.AbstractBaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends AbstractBaseEntity {

    @Enumerated(EnumType.STRING)
    private RoleName name;

    private boolean active = true;
}
