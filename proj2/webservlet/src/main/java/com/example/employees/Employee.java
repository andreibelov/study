package com.example.employees;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author luperalt
 */
@Getter
@Setter
public class Employee {

    private long id;
    private String name;
    private String lastName;
    private String birthDate;
    private String role;
    private String department;
    private String email;
    private static final AtomicLong counter = new AtomicLong(100);

    public Employee(String name, String lastName, String birthDate, String role, String department, String email, long id) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.department = department;
        this.email = email;
        this.id = id;
    }

    public Employee(String name, String lastName, String birthDate, String role, String department, String email) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.department = department;
        this.email = email;
        this.id = counter.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name +
                ", lastName=" + lastName + ", birthDate=" + birthDate +
                ", role=" + role + ", department=" + department +
                ", email=" + email + '}';
    }
}
