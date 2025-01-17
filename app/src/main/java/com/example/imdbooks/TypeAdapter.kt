package com.example.imdbooks

import android.net.Uri
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

// Classe personalizada que estende TypeAdapter para permitir a serialização e desserialização de URIs com Gson
class TypeAdapter : TypeAdapter<Uri>() {
    // Metodo chamado ao serializar (converter objeto em JSON)
    override fun write(out: JsonWriter, value: Uri?) {
        // Escreve o valor da URI como string no JSON
        out.value(value.toString()) // Se value for null, será gravado "null" no JSON
    }

    // Metodo chamado ao desserializar (converter JSON em objeto)
    override fun read(`in`: JsonReader): Uri {
        // Lê a próxima string no JSON e converte para um objeto Uri
        return Uri.parse(`in`.nextString())
    }
}
