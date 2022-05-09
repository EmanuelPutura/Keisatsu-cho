package ubb.keisatsu.cms.model.dto

import java.io.Serializable

data class PaperEvaluationDto(val chairID: Int, val paperID: Int, val isAccepted:Boolean)

data class PaperDetailsDto(val id: Int,val title: String, val keywords: String, val topic: String,val abstract: String, val decided:Boolean=false)