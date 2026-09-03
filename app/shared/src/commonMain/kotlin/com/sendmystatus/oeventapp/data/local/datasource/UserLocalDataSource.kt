package com.sendmystatus.oeventapp.data.local.datasource

import com.sendmystatus.oeventapp.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getCurrentUser(): Flow<User?>
    suspend fun saveUser(user: User)
    suspend fun deleteUser()
}
