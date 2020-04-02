package pt.wtest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class PostalCodeEntity (
    @PrimaryKey var id: Int?,
    var nome_localidade: String,
    var nome_localidade_ascii: String,
    var num_cod_postal: String,
    var ext_cod_postal: String
) : Serializable