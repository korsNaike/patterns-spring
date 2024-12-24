package com.korsnaike.patternsspringstudent.service

import com.korsnaike.patternsspringstudent.model.Student
import com.korsnaike.patternsspringstudent.repository.StudentRepository
import com.korsnaike.patternsspringstudent.schema.UpdateStudentSchema
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class StudentService(@Autowired private val studentRepository: StudentRepository) {

    fun findAll(): List<Student> = studentRepository.findAll()

    fun findById(id: Long): Optional<Student> = studentRepository.findById(id)

    fun save(student: Student): Student = studentRepository.save(student)

    fun update(id: Long, schema: UpdateStudentSchema): Student {
        val student = studentRepository.findById(id).orElseThrow { NoSuchElementException("Student not found") }

        return studentRepository.save(student.updateFromDto(schema))
    }

    fun deleteById(id: Long) {
        if (!studentRepository.existsById(id)) {
            throw NoSuchElementException("Student with id $id not found")
        }
        studentRepository.deleteById(id)
    }

    fun findWithFilters(
        firstName: String?,
        lastName: String?,
        middleName: String?,
        email: String?,
        telegram: String?,
        phone: String?,
        git: String?,
        pageable: Pageable
    ): Page<Student> {
        val spec = Specification.where<Student>(null)
            .and(firstName?.let {
                Specification<Student> { root, _, cb ->
                    cb.like(cb.lower(root.get("firstName")), "%${it.lowercase()}%")
                }
            })
            .and(lastName?.let {
                Specification<Student> { root, _, cb ->
                    cb.like(cb.lower(root.get("lastName")), "%${it.lowercase()}%")
                }
            })
            .and(middleName?.let {
                Specification<Student> { root, _, cb ->
                    cb.like(cb.lower(root.get("middleName")), "%${it.lowercase()}%")
                }
            })
            .and(email?.let {
                Specification<Student> { root, _, cb ->
                    cb.like(cb.lower(root.get("email")), "%${it.lowercase()}%")
                }
            })
            .and(telegram?.let {
                Specification<Student> { root, _, cb ->
                    cb.like(cb.lower(root.get("telegram")), "%${it.lowercase()}%")
                }
            })
            .and(phone?.let {
                Specification<Student> { root, _, cb ->
                    cb.like(cb.lower(root.get("phone")), "%${it.lowercase()}%")
                }
            })
            .and(git?.let {
                Specification<Student> { root, _, cb ->
                    cb.like(cb.lower(root.get("git")), "%${it.lowercase()}%")
                }
            })

        return studentRepository.findAll(spec, pageable)
    }

}