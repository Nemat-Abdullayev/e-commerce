package com.ecommerce.model;
import com.ecommerce.model.base.AbstractBaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
public class RefreshToken extends AbstractBaseEntity {

    private Long userId;
    private String refreshToken;
    @Column(name = "access_token",length = 4000)
    private String accessToken;
    private LocalDateTime expiredDateTime;
    private boolean active;
}
