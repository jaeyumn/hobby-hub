package noul.hobbyhub.base.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: LocalDateTime
        protected set

    @UpdateTimestamp
    @Column(nullable = false)
    lateinit var modifiedAt: LocalDateTime
        protected set
}