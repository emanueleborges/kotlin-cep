package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    
    // Constante TAG para identificação nos logs
    companion object {
        private const val TAG = "MainActivity"
    }

    // Declaração das variáveis de UI
    private lateinit var editTextNome: EditText
    private lateinit var textViewSaida: TextView
    private lateinit var editTextNumero1: EditText
    private lateinit var editTextNumero2: EditText
    private lateinit var textViewResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Log para verificar o ciclo de vida
        Log.d(TAG, "onCreate: Iniciando atividade")
        Log.v(TAG, "onCreate: savedInstanceState é ${if (savedInstanceState == null) "nulo" else "não nulo"}")
        
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Inicializar componentes
        inicializarComponentes()
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            Log.v(TAG, "onApplyWindowInsets: Padding aplicado")
            insets
        }
        
        Log.i(TAG, "onCreate: Configuração concluída")
    }
    
    private fun inicializarComponentes() {
        Log.d(TAG, "inicializarComponentes: Iniciando")
        
        try {
            editTextNome = findViewById(R.id.editTextNome)
            textViewSaida = findViewById(R.id.textViewSaida)
            editTextNumero1 = findViewById(R.id.editTextNumero1)
            editTextNumero2 = findViewById(R.id.editTextNumero2)
            textViewResultado = findViewById(R.id.textViewResultado)
            
            // Verificar se todos os componentes foram encontrados
            Log.i(TAG, "Componentes inicializados com sucesso")
            Log.v(TAG, "Componentes: editTextNome=$editTextNome, textViewSaida=$textViewSaida")
            
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao inicializar componentes: ${e.message}")
        }
    }
    
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Atividade ficando visível")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Atividade pronta para interação")
    }
    
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Atividade pausada")
    }
    
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Atividade não está mais visível")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Atividade sendo destruída")
    }

    fun onButtonClick(view: View) {
        Log.d(TAG, "onButtonClick: Botão clicado")
        
        // Log para identificar qual botão foi clicado
        when (view.id) {
            R.id.buttonSaudacao -> {
                Log.i(TAG, "Botão de saudação clicado")
                handleSaudacao()
            }
            R.id.buttonCalcular -> {
                Log.i(TAG, "Botão de calcular clicado")
                handleCalculo()
            }
            else -> {
                Log.w(TAG, "Botão desconhecido clicado: ${view.id}")
            }
        }
    }
    
    private fun handleSaudacao() {
        Log.d(TAG, "handleSaudacao: Iniciando")
        
        // Verificar se o EditText foi inicializado
        if (!::editTextNome.isInitialized) {
            Log.e(TAG, "editTextNome não foi inicializado")
            return
        }
        
        val nome = editTextNome.text.toString()
        Log.v(TAG, "Nome digitado: '$nome'")
        
        // Coloque um breakpoint AQUI para verificar o valor da variável 'nome'
        
        when {
            nome.isBlank() -> {
                Log.w(TAG, "Nome está em branco")
                textViewSaida.text = "Por favor, digite um nome!"
            }
            else -> {
                val mensagem = "Olá, $nome!"
                Log.i(TAG, "Mensagem gerada: $mensagem")
                textViewSaida.text = mensagem
            }
        }
        
        Log.d(TAG, "handleSaudacao: Finalizado")
    }
    
    private fun handleCalculo() {
        Log.d(TAG, "handleCalculo: Iniciando cálculo")
        
        // Verificar componentes
        if (!::editTextNumero1.isInitialized || !::editTextNumero2.isInitialized) {
            Log.e(TAG, "Componentes de cálculo não inicializados")
            return
        }
        
        val valor1Str = editTextNumero1.text.toString()
        val valor2Str = editTextNumero2.text.toString()
        
        Log.v(TAG, "Valores recebidos: valor1='$valor1Str', valor2='$valor2Str'")
        
        // Coloque um breakpoint AQUI para verificar os valores das strings
        
        when {
            valor1Str.isBlank() || valor2Str.isBlank() -> {
                Log.w(TAG, "Campos de cálculo vazios")
                textViewResultado.text = "Preencha ambos os números"
                return
            }
        }
        
        try {
            val numero1 = valor1Str.toInt()
            val numero2 = valor2Str.toInt()
            
            Log.d(TAG, "Conversão realizada: numero1=$numero1, numero2=$numero2")
            
            // Coloque um breakpoint AQUI para verificar os valores convertidos
            
            val resultado = numero1 + numero2
            Log.i(TAG, "Resultado do cálculo: $resultado")
            
            textViewResultado.text = "Resultado: $resultado"
            
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Erro de conversão: ${e.message}")
            textViewResultado.text = "Erro: Digite números válidos"
        } catch (e: Exception) {
            Log.e(TAG, "Erro inesperado: ${e.message}")
            textViewResultado.text = "Erro inesperado"
        }
        
        Log.d(TAG, "handleCalculo: Finalizado")
    }
}