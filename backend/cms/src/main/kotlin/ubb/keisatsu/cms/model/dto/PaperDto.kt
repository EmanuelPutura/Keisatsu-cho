package ubb.keisatsu.cms.model.dto

import java.io.Serializable



data class PaperDetailsDto(val id: Int,val title: String, val keywords: String, val topic: String, val decided:Boolean=false)