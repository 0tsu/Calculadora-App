package com.example.calculadoraapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView
    private var resultJustShown = false // controla se o resultado acabou de ser mostrado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply,
            R.id.btnDivide, R.id.btnDot, R.id.btnOpen, R.id.btnClose
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                val button = it as Button
                var text = button.text.toString()

                // Corrige símbolos para o cálculo
                if (text == "×") text = "*"
                if (text == "÷") text = "/"

                // Se acabou de mostrar o resultado, "sobe" ele pra expressão
                if (resultJustShown) {
                    tvExpression.text = tvResult.text.toString()
                    tvResult.text = "0"
                    resultJustShown = false
                }

                tvExpression.append(text)
            }
        }

        // Botão C (limpar tudo)
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            tvExpression.text = ""
            tvResult.text = "0"
            resultJustShown = false
        }

        // Botão =
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            try {
                val expression = ExpressionBuilder(tvExpression.text.toString()).build()
                val result = expression.evaluate()

                // Mostra o resultado embaixo, mantém expressão em cima
                tvResult.text = result.toString()
                resultJustShown = true

            } catch (e: Exception) {
                tvResult.text = "Erro"
                resultJustShown = true
            }
        }
    }
}
