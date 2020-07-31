package com.globomantics.globomed

class Employee(
    val id: String,
    val name: String,
    val dob: Long,
    val designation: String,
    val isSurgeon: Int
 ) {
    override fun toString(): String {
        return "Employee(id='$id', name='$name', dob=$dob, designation='$designation')"
    }
}