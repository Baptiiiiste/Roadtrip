package uca.baptistearthur.geocaching.network

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import uca.baptistearthur.geocaching.model.Address
import java.lang.reflect.Type

class AddressDeserializer : JsonDeserializer<Address> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Address = json?.asJsonObject!!.let{
                    Address(
                        it.get("address").asJsonObject.get("country").asString,
                        it.get("display_name").asString
                    )
                }
}
