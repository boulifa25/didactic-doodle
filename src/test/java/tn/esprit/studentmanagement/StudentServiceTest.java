package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;
import tn.esprit.studentmanagement.services.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testSaveStudent() {
        Student s = new Student();
        s.setFirstName("Mohamed");

        when(studentRepository.save(s)).thenReturn(s);

        Student saved = studentService.saveStudent(s);

        assertNotNull(saved);
        assertEquals("Mohamed", saved.getFirstName());
        verify(studentRepository, times(1)).save(s);
    }

    @Test
    void testGetAllStudents() {
        List<Student> list = new ArrayList<>();
        list.add(new Student());
        list.add(new Student());

        when(studentRepository.findAll()).thenReturn(list);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
    }

    @Test
    void testGetStudentById() {
        Student s = new Student();
        s.setIdStudent(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        Student result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdStudent());
    }

    @Test
    void testDeleteStudent() {
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}