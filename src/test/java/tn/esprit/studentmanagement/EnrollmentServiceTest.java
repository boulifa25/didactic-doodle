package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;
import tn.esprit.studentmanagement.services.EnrollmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void testSaveEnrollment() {
        Enrollment e = new Enrollment();
        when(enrollmentRepository.save(e)).thenReturn(e);

        Enrollment saved = enrollmentService.saveEnrollment(e);
        assertNotNull(saved);
        verify(enrollmentRepository, times(1)).save(e);
    }

    @Test
    void testGetAllEnrollments() {
        List<Enrollment> list = new ArrayList<>();
        list.add(new Enrollment());

        when(enrollmentRepository.findAll()).thenReturn(list);

        List<Enrollment> result = enrollmentService.getAllEnrollments();
        assertEquals(1, result.size());
    }

    @Test
    void testGetEnrollmentById() {
        Enrollment e = new Enrollment();
        e.setIdEnrollment(1L);
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(e));

        Enrollment result = enrollmentService.getEnrollmentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getIdEnrollment());
    }

    @Test
    void testDeleteEnrollment() {
        enrollmentService.deleteEnrollment(1L);
        verify(enrollmentRepository, times(1)).deleteById(1L);
    }
}