package com.example.imdbooks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbooks.databinding.ActivityDeleteBinding
import com.example.imdbooks.sharedPreferencesUtils.Utils.Companion.deleteProductById
import com.google.android.material.snackbar.Snackbar

// Atividade responsável por excluir um produto (livro) com base no ID fornecido pelo usuário.
class DeleteActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDeleteBinding     // Binding é usado para acessar diretamente os elementos do layout sem a necessidade de findViewById.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)     // Infla o layout da atividade usando o View Binding.
        val view = binding.root
        setContentView(view)        // Associa o layout ao conteúdo da atividade.

        val deleteBtn = binding.deleteBtn   // Botão para confirmar a exclusão.
        val cancelBtn = binding.cancelBtn   // Botão para cancelar a exclusão.

        // Configura o comportamento do botão "Deletar".
        deleteBtn.setOnClickListener {

            val bookIdInput = binding.bookIdInput.text.toString().toIntOrNull()     // Obtém o ID do livro inserido no campo de entrada de texto.

            // Verifica se o ID inserido é válido (não nulo e numérico)
            if (bookIdInput == null) {
                // Exibe uma mensagem informando que o ID é inválido.
                Toast.makeText(this, "Please insert a valid book ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Exclui o produto com o ID fornecido.
            if(!deleteProductById(this,bookIdInput)){
                // Caso o ID do produto não seja encontrado, exibe uma mensagem de erro.
                Toast.makeText(this, "Product with Id not found!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                // Caso o produto seja encontrado e excluído com sucesso:
                Toast.makeText(this, "Product deleted with success", Toast.LENGTH_SHORT).show()

                // Redireciona o usuário para o menu principal
                startActivity(Intent(this, MenuActivity::class.java))
                finish() // Finaliza a atividade atual.
                return@setOnClickListener
            }
        }

        // Configura o comportamento do botão "Cancelar".
        cancelBtn.setOnClickListener {
            // Redireciona o usuário para o menu principal sem realizar nenhuma ação.
            startActivity(Intent(this@DeleteActivity, MenuActivity::class.java))
            finish() // Finaliza a atividade atual.
            return@setOnClickListener
        }
    }
}
