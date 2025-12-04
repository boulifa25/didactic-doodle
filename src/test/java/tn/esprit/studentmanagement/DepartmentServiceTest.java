package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;
import tn.esprit.studentmanagement.services.DepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void testSaveDepartment() {
        Department dept = new Department();
        dept.setName("IT");

        when(departmentRepository.save(dept)).thenReturn(dept);

        Department saved = departmentService.saveDepartment(dept);

        assertNotNull(saved);
        assertEquals("IT", saved.getName());
        verify(departmentRepository, times(1)).save(dept);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> list = new ArrayList<>();
        list.add(new Department());

        when(departmentRepository.findAll()).thenReturn(list);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(1, result.size());
    }

    @Test
    void testGetDepartmentById() {
        Department dept = new Department();
        dept.setIdDepartment(1L);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(dept));

        Department result = departmentService.getDepartmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdDepartment());
    }

    @Test
    void testDeleteDepartment() {
        departmentService.deleteDepartment(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}