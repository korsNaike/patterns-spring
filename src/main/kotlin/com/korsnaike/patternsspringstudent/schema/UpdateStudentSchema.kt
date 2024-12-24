package com.korsnaike.patternsspringstudent.schema

data class UpdateStudentSchema (
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null
) {
}