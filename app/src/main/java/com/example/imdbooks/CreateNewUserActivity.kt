package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityCreateNewUserBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils
import com.example.imdbooks.sharedPreferencesUtils.Utils.Companion.getAllUsers


// Atividade responsável por criar um novo usuário no sistema.
class CreateNewUserActivity : AppCompatActivity() {

    // Binding é usado para acessar diretamente os elementos do layout sem precisar de `findViewById`.
    private lateinit var binding: ActivityCreateNewUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewUserBinding.inflate(layoutInflater) // Infla o layout da atividade usando View Binding.
        val createUserButton = binding.createUserBtn           // Botão para criar um novo usuário.
        val returnToLoginButton = binding.returnToLoginBtn     // Botão para voltar à tela de login.
        val usernameInput = binding.newUsernameInput           // Campo para inserir o nome de usuário.
        val userPassword = binding.newPasswordInput            // Campo para inserir a senha do usuário.
        setContentView(binding.root)                           // Associa o layout ao conteúdo da atividade.

        // Recupera a lista de usuários existentes através de SharedPreferences.
        val currentUsers = getAllUsers(this)

        // Configura o comportamento do botão "Criar Usuário".
        createUserButton.setOnClickListener{
            // Verifica se já existe um usuário com o nome fornecido.
            when {
                currentUsers.any {
                    it.getPassword() == usernameInput.text.toString()
                } -> {
                    Toast.makeText(
                        this,
                        "A user with this username already exists. Please use a different username.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {       // Caso o nome de usuário seja único, cria um novo usuário.

                    val user = User(
                        usernameInput.text.toString(),  // Nome de usuário inserido.
                        userPassword.text.toString(),   // Senha inserida.
                    )
                    currentUsers.add(user)       // Adiciona o novo usuário à lista existente.
                    Utils.saveUsersMutableList(this, currentUsers)  // Salva a lista de usuários atualizada em SharedPreferences.

                    Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show()       // Exibe uma mensagem confirmando a criação do usuário.
                    startActivity(Intent(this, MainActivity::class.java))       // Finaliza a atividade atual para que ela seja removida da pilha de atividades.

                    finish()
                }
            }

        }

        // Configura o comportamento do botão "Voltar para Login".
        returnToLoginButton.setOnClickListener {
            startActivity(Intent(this@CreateNewUserActivity,MainActivity::class.java))  // Redireciona o usuário para a tela de login (MainActivity).
        }

    }


}