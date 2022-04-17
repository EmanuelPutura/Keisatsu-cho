package ubb.keisatsu.cms.model

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import org.ktorm.schema.boolean

object ChairPaperTable : Table<Nothing>("ChairPaperEvaluation") {
    val chairID = int("ChairID")
    val paperID = int("PaperID")
    val isAccepted = boolean("IsAccepted")
}