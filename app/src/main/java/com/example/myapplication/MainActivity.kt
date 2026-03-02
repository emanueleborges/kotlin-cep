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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "MainActivity"
        private const val BASE_URL = "https://brasilapi.com.br/api/cep/v2"
    }

    private lateinit var editTextCEP: EditText
    private lateinit var textViewResultado: TextView
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Log.d(TAG, "onCreate: Iniciando atividade")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        inicializarComponentes()
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        Log.i(TAG, "onCreate: Configuração concluída")
    }
    
    private fun inicializarComponentes() {
        Log.d(TAG, "inicializarComponentes: Iniciando")
        
        try {
            editTextCEP = findViewById(R.id.editTextCEP)
            textViewResultado = findViewById(R.id.textViewResultado)
            
            Log.i(TAG, "Componentes inicializados com sucesso")
            
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao inicializar componentes: ${e.message}")
        }
    }
    
    fun buscarCEP(view: View) {
        Log.d(TAG, "buscarCEP: Botão clicado")
        
        val cep = editTextCEP.text.toString().trim()
        Log.v(TAG, "CEP digitado: '$cep'")
        
        when {
            cep.isBlank() -> {
                Log.w(TAG, "CEP está em branco")
                textViewResultado.text = "Por favor, digite um CEP!"
                return
            }
            cep.length != 8 -> {
                Log.w(TAG, "CEP inválido - comprimento: ${cep.length}")
                textViewResultado.text = "CEP deve ter 8 dígitos!"
                return
            }
        }
        
        textViewResultado.text = "Aguardando resposta..."
        Log.i(TAG, "Iniciando requisição para CEP: $cep")
        
        fazerRequisicaoCEP(cep)
    }
    
    private fun fazerRequisicaoCEP(cep: String) {
        val url = "$BASE_URL/$cep"
        Log.d(TAG, "URL da requisição: $url")
        
        val request = Request.Builder()
            .url(url)
            .build()
        
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Erro na requisição: ${e.message}", e)
                runOnUiThread {
                    textViewResultado.text = "Erro na requisição: ${e.message}"
                }
            }
            
            override fun onResponse(call: Call, response: Response) {
                val resposta = response.body.string()
                Log.d(TAG, "Resposta recebida: $resposta")
                
                runOnUiThread {
                    if (response.isSuccessful) {
                        Log.i(TAG, "Requisição bem-sucedida")
                        textViewResultado.text = resposta
                    } else {
                        Log.w(TAG, "Requisição retornou código: ${response.code}")
                        textViewResultado.text = "CEP não encontrado ou erro na API\n\nCódigo: ${response.code}"
                    }
                }
            }
        })
    }
}