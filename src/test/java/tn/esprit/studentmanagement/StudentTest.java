package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentTest {

    @Test
    void testStudentEntity() {
        // --- TEST 1 : Constructeur vide et Setters ---
        Student s = new Student();

        Department dept = new Department();
        List<Enrollment> enrollments = new ArrayList<>();
        LocalDate date = LocalDate.of(2000, 1, 1);

        s.setIdStudent(1L);
        s.setFirstName("Mohamed");
        s.setLastName("Zahi");
        s.setEmail("mohamed.zahi@esprit.tn");
        s.setPhone("56464733");
        s.setDateOfBirth(date);
        s.setAddress("Tunis");
        s.setDepartment(dept);
        s.setEnrollments(enrollments);

        assertEquals(1L, s.getIdStudent());
        assertEquals("Mohamed", s.getFirstName());
        assertEquals("Zahi", s.getLastName());
        assertEquals("Tunis", s.getAddress());
        assertEquals(dept, s.getDepartment());


        Student s2 = new Student(2L, "Yassine", "Chakroun", "Djappa@test.com", "0000", date, "Kram", dept, enrollments);

        assertEquals(2L, s2.getIdStudent());
        assertEquals("Yassine", s2.getFirstName());

        // --- TEST 3 : toString (@ToString) ---
        // Cela permet de couvrir aussi la méthode toString générée par Lombok
        String result = s2.toString();
        assertNotNull(result);
    }
}