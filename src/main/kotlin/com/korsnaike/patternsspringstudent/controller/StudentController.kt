package com.korsnaike.patternsspringstudent.controller

import com.korsnaike.patternsspringstudent.entity.Student
import com.korsnaike.patternsspringstudent.service.StudentService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(@Autowired private val studentService: StudentService) {

    @GetMapping
    fun getAllStudents(): List<Student> = studentService.findAll()

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): Student =
        studentService.findById(id).orElseThrow { NoSuchElementException("Student with id $id not found") }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createStudent(@RequestBody @Valid student: Student): Student = studentService.save(student)

    @PutMapping("/{id}")
    fun updateStudent(@PathVariable id: Long, @RequestBody @Valid student: Student): Student =
        studentService.update(id, student)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudent(@PathVariable id: Long) = studentService.deleteById(id)

    @GetMapping("/search")
    fun searchStudents(
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
        @RequestParam(required = false) middleName: String?,
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) telegram: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) git: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "ASC") direction: String
    ): Page<Student> {
        val pageable = PageRequest.of(page, size, if (direction.uppercase() == "ASC") Sort.by(sortBy).ascending() else Sort.by(sortBy).descending())
        return studentService.findWithFilters(firstName, lastName, middleName, email, telegram, phone, git, pageable)
    }
}