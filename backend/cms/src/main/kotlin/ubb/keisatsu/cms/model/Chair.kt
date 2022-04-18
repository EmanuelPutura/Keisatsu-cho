package ubb.keisatsu.cms.model

import org.ktorm.schema.Table
import org.ktorm.schema.int


object ChairTable : Table<Nothing>("Chair") {
    val id = int("ChairID").primaryKey()
}

data class Chair(val id: Int)
