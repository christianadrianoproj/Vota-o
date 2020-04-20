package com.senac.appvotacao

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class InformacoesVotacaoActivity : AppCompatActivity() {

    private lateinit var spinnerEscolaridade: Spinner
    private lateinit var edIdade: EditText
    private lateinit var sCandidato: String
    private lateinit var rgGenero: RadioGroup
    private lateinit var rgCasaPropria: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes_votacao)

        sCandidato = intent.getStringExtra("candidatoSelecionado") ?: "Não encontrado"
        spinnerEscolaridade = findViewById<Spinner>(R.id.spEscolaridade)
        edIdade = findViewById<EditText>(R.id.et_idade)
        rgGenero = findViewById<RadioGroup>(R.id.rg_genero)
        rgCasaPropria = findViewById<RadioGroup>(R.id.rg_casapropria)

        ArrayAdapter.createFromResource(
            this,
            R.array.lista_Escolaridade,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEscolaridade.adapter = adapter
        }

        val btn = findViewById<Button>(R.id.bt_confirmar)
        btn.setOnClickListener {
            if (validate()) {
                Toast.makeText(
                    this,
                    "Obrigado por participar da entrevista. Seu voto foi no candidato ${sCandidato}",
                    Toast.LENGTH_LONG
                ).show()

                val intent = this.intent
                intent.putExtra("candidatoSelRetorno", "$sCandidato")
                this.setResult(Activity.RESULT_OK, intent)
                this.finish()
            }
        }
    }

    private fun validate(): Boolean {
        var result = true
        if (edIdade.text.trim().length == 0) {
            edIdade.setError("Campo é obrigatório")
            result = false
       }
        if ((result) && (edIdade.text.toString().toInt() < 16)) {
            edIdade.setError("Idade precisa ser superior a 15 anos!")
            result = false
        }
        if ((result) && (rgGenero.checkedRadioButtonId == -1)) {
            AlertDialog.Builder(this)
                .setTitle("Validação")
                .setMessage("Selecione o gênero (Sexo)!")
                .setPositiveButton("OK") { dialog, which -> dialog.dismiss()
                }.show()
            result = false
        }
        if ((result) && (spinnerEscolaridade.selectedItemId.toString().equals("-1"))) {
            AlertDialog.Builder(this)
                .setTitle("Validação")
                .setMessage("Selecione a escolaridade!")
                .setPositiveButton("OK") { dialog, which -> dialog.dismiss()
                }.show()
            result = false
        }
        if ((result) && (rgCasaPropria.checkedRadioButtonId == -1)) {
            AlertDialog.Builder(this)
                .setTitle("Validação")
                .setMessage("Selecione a opção se possui casa própria!")
                .setPositiveButton("OK") { dialog, which -> dialog.dismiss()
                }.show()
            result = false
        }
        return result
    }
}
