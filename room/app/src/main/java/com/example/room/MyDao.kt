package com.example.room

import androidx.lifecycle.LiveData
import androidx.room.*

data class StudentWithEnrollments(  // 1:N 관계
    @Embedded val student: Student,
    @Relation(
        parentColumn = "student_id",
        entityColumn = "sid"
    )
    val enrollments: List<Enrollment>
)

@Dao
interface MyDao {
    //학생을 추가하고 삭제하는 인터페이스
    @Insert(onConflict = OnConflictStrategy.REPLACE) // INSERT, key 충돌이 나면 새 데이터로 교체
    suspend fun insertStudent(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(classInfo: ClassInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnrollment(enrollment: Enrollment)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * from student_table")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM student_table WHERE name = :sname")   // 메소드 인자를 SQL문에서 :을 붙여 사용
    suspend fun getStudentByName(sname: String): List<Student>

    @Transaction
    @Query("SELECT * FROM student_table WHERE student_id = :id")
    suspend fun getStudentsWithEnrollment(id: Int): List<StudentWithEnrollments>

    @Query("SELECT * FROM class_table WHERE id = :id")
    suspend fun getClassInfo(id: Int): List<ClassInfo>
}