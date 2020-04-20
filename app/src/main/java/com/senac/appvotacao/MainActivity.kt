package com.senac.appvotacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var candidatos: RadioGroup
    val RETORNO = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        candidatos = findViewById<RadioGroup>(R.id.rg_candidatos)

        val btn = findViewById<Button>(R.id.bt_votar)
        btn.setOnClickListener {
            if (validate()) {
                val candidatoSel =
                    if (candidatos.checkedRadioButtonId == R.id.rb_Branco)
                        getString(R.string.radioBranco)
                   else if (candidatos.checkedRadioButtonId == R.id.rb_Joao)
                        getString(R.string.radioJoao)
                    else if (candidatos.checkedRadioButtonId == R.id.rb_Jose)
                        getString(R.string.radioJose)
                    else if (candidatos.checkedRadioButtonId == R.id.rb_Luiz)
                        getString(R.string.radioLuiz)
                    else if (candidatos.checkedRadioButtonId == R.id.rb_Maria)
                        getString(R.string.radioMaria)
                    else if (candidatos.checkedRadioButtonId == R.id.rb_Nulo)
                        getString(R.string.radioNulo)
                    else
                        "Não encontrado"
                val i = Intent(this, InformacoesVotacaoActivity::class.java)
                i.putExtra("candidatoSelecionado", "$candidatoSel")
                startActivityForResult(i, RETORNO)
            }
        }
    }

    private fun validate(): Boolean {
        var result = true
        if (candidatos.checkedRadioButtonId == -1) {
            AlertDialog.Builder(this)
                .setTitle("Validação")
                .setMessage("Selecione um candidato.")
                .setPositiveButton("OK") { dialog, which -> dialog.dismiss()
                }.show()
            result = false
        }
        return result
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RETORNO && resultCode == RESULT_OK) {
            candidatos.clearCheck()
        }
    }
}
