package noul.hobbyhub.user.repository

import noul.hobbyhub.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): Optional<User>
}