package com.txt1stparkuor.Ecommerce.domain.entity.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class FullAuditing extends DateAuditing {

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
