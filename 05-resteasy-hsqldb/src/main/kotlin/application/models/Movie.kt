package application.models

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import javax.validation.constraints.Size

@DataObject(generateConverter = true)
data class Movie (

    var id : Int? = null,

    @get:Size(min = 2, max = 30, message = "Name must have between 2 and 30 caracters")
    var name : String = ""

) {
    constructor (jsonObject: JsonObject) : this() {
        //Método gerado automaticamente
        MovieConverter.fromJson(jsonObject, this)
    }

    fun toJson(): JsonObject {
        //Método gerado automaticamente
        val jsonObject = JsonObject()
        MovieConverter.toJson(this, jsonObject)
        return jsonObject
    }
}
