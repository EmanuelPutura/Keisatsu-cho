package ubb.keisatsu.cms.model.dto

import java.io.Serializable

data class PaperDto(val id: Int? , val title: String? = null, val keywords: String? = null, val format: String? = null, val file: ByteArray? = null) : Serializable
