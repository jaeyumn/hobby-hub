package noul.hobbyhub.user.service

import noul.hobbyhub.user.dto.request.UserSignUpRequest
import noul.hobbyhub.user.entity.Role
import noul.hobbyhub.user.repository.UserRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val sut: UserService,
    private val userRepository: UserRepository
) {
    @Test
    @DisplayName("회원가입 시, 정상적으로 회원 데이터가 생성된다.")
    fun test1000() {
        // given
        val request = UserSignUpRequest(
            username = "tester",
            password = "test123",
            email = "test@test.com",
            role = Role.ADMIN,
        )

        // when
        sut.signUp(request)

        // then
        val savedUser = userRepository.findByUsername("tester").get()
        assertThat(savedUser).isNotNull
        assertThat(savedUser.username).isEqualTo("tester")
        assertThat(savedUser.email).isEqualTo("test@test.com")
        assertThat(savedUser.password).isNotEqualTo("test123")
        assertThat(savedUser.role).isEqualTo(Role.ADMIN)
    }
}