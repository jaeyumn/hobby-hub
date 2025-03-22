package noul.hobbyhub.user.dto.request

import noul.hobbyhub.user.entity.Role

data class UserSignUpRequest(
    val username: String,
    val password: String,
    val email: String,
    val role: Role? = Role.USER
)
