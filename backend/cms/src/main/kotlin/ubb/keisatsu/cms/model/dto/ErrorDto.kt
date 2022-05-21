package ubb.keisatsu.cms.model.dto

data class ErrorDto(val executedWithoutErrors: Boolean, val errorMessage: String = "")
