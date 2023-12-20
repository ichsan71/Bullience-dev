package com.marqumil.bullience_dev.ui.screen.signin

import com.marqumil.bullience_dev.ui.common.FormHandler

data class SignInUiState(
    val loginId: String = "",
    val password: String = "",
    val inputEmail: FormHandler = FormHandler(true, ""),
    val inputPassword: FormHandler = FormHandler(true, ""),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)