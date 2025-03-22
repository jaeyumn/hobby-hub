package noul.hobbyhub.user.controller

import noul.hobbyhub.user.dto.request.UserSignUpRequest
import noul.hobbyhub.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody request: UserSignUpRequest) {
        userService.signUp(request)
    }
}