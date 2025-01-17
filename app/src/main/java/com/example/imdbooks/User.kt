package com.example.imdbooks

// Classe User que encapsula as informações de um usuário: username e password
class User(private val username: String, private val password: String) {

    // Metodo para obter a senha do usuário
    fun getPassword(): String {
        return password // Retorna a senha armazenada
    }
    // Metodo para obter o nome de usuário
    fun getUsername(): String {
        return username // Retorna o nome de usuário armazenado
    }

    // Sobrescreve o metodo toString para retornar uma representação amigável do objeto User
    override fun toString(): String {
        // Retorna apenas o username (não inclui a senha por razões de segurança)
        return "User(username='$username')"
    }
}