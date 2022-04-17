package ubb.keisatsu.cms.model

import org.ktorm.schema.Table
import org.ktorm.schema.bytes
import org.ktorm.schema.int
import org.ktorm.schema.varchar


object PaperTable : Table<Nothing>("Paper") {
    val id = int("PaperID").primaryKey()
    val title = varchar("Title")
    val keywords=varchar("KeyWords")
    val topic=int("TopicID")
    val format=varchar("Format")
    val file=bytes("File")
}

data class Paper(val title:String, val keywords:String, val format:String,val topic:Int)