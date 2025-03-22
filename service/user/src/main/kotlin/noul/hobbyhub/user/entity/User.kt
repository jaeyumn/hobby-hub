package noul.hobbyhub.user.entity

import entity.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role? = Role.USER
) : BaseEntity() {

    protected constructor() : this(UUID.randomUUID(), "", "", "", Role.USER)
}