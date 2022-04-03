package brc.studybuddy.backend.users.model

import brc.studybuddy.model.User

data class UserInput(
    val email: String? = null,
    val loginType: User.LoginType? = null,
    val loginValue: String? = null
) {
    fun toUser(): User = User(
        email = this.email!!,
        loginType = this.loginType!!,
        loginValue = this.loginValue!!
    )

    fun updateUser(user: User): User = User(
        user.id,
        this.email ?: user.email,
        this.loginType ?: user.loginType,
        this.loginValue ?: user.loginValue
    )
}
