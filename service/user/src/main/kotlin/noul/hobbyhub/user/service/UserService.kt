package noul.hobbyhub.user.service

import noul.hobbyhub.user.dto.request.UserSignUpRequest
import noul.hobbyhub.user.entity.User
import noul.hobbyhub.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun signUp(request: UserSignUpRequest) {
        val encodedPassword = passwordEncoder.encode(request.password)
        val user = User(username = request.username, password = encodedPassword, email = request.email, role = request.role)
        userRepository.save(user)
    }
}