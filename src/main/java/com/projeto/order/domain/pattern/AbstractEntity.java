package com.projeto.order.domain.pattern;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements IEntity {

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdIn;

    @LastModifiedDate
    private LocalDateTime updatedIn;

    @Version
    private Integer version;

    public LocalDateTime getCreatedIn() {
        return createdIn;
    }

    public LocalDateTime getUpdatedIn() {
        return updatedIn;
    }

    public Integer getVersion() {
        return version;
    }
}
