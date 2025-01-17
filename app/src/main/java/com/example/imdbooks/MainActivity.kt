package com.example.imdbooks

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityMainBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils


// Atividade principal que exibe a tela de login e permite acessar ou criar um novo usuário.
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding         // Binding para acessar diretamente os elementos do layout.
    private lateinit var userPrefs: SharedPreferences         // SharedPreferences para armazenar ou recuperar dados de usuário.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)       // Infla o layout da atividade usando View Binding.
        setContentView(binding.root)

        // Inicializa o SharedPreferences com o nome "userCredentials".
        userPrefs = getSharedPreferences("userCredentials", MODE_PRIVATE)


        // Recupera a lista de usuários armazenados em SharedPreferences.
        val allUsers = Utils.getAllUsers(this)

        // Referências aos componentes da interface gráfica.
        val loginBtn = binding.loginBtn           // Botão de login.
        val passwordEdt = binding.loginPassword   // Campo para a senha
        val emailEdt = binding.loginEmail         // Campo para o e-mail ou nome de usuário.
        val createNewUseBtn = binding.registerBtn // Botão para criar um novo usuário.

        // Configura o comportamento do botão de login.
        loginBtn.setOnClickListener {
            val inputEmail = emailEdt.text.toString()           // Obtém o e-mail inserido pelo usuário.
            val inputPassword = passwordEdt.text.toString()     // Obtém a senha inserida pelo usuário.

            // Verifica se os campos estão vazios.
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter a username and a matching password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se o e-mail e a senha correspondem a algum usuário registrado.
            when {
                allUsers.any {
                    it.getUsername() == inputEmail && it.getPassword() == inputPassword
                } -> {
                    // Login bem-sucedido.
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Redireciona para o menu principal.
                    startActivity(Intent(this@MainActivity,MenuActivity::class.java))
                }
                else -> {
                    // Login falhou devido a credenciais inválidas.
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Configura o comportamento do botão "Criar Novo Usuário".
        createNewUseBtn.setOnClickListener {
            // Redireciona o usuário para a atividade de criação de um novo usuário.
            startActivity(Intent(this@MainActivity,CreateNewUserActivity::class.java))
        }
    }


}
