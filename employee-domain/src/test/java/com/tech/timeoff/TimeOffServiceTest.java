package com.tech.timeoff;

import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeFixtures;
import com.tech.employee.domain.EmployeeId;
import com.tech.employee.domain.EmployeeRepository;
import com.tech.timeoff.category.CategoryId;
import com.tech.timeoff.category.TimeOffCategory;
import com.tech.timeoff.category.TimeOffCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeOffServiceTest {
    @Mock
    TimeOffRepository timeOffRepository;
    @Mock
    TimeOffCategoryRepository timeOffCategoryRepository;
    @Mock
    EmployeeRepository employeeRepository;
    TimeOffService timeOffService;

    @BeforeEach
    void setUp() {
        timeOffService = new TimeOffService(timeOffRepository, timeOffCategoryRepository, employeeRepository);
    }

    @Test
    void request_should_create_requested_timeoff() {
        //given
        EmployeeId employeeId = EmployeeId.generate();
        CategoryId categoryId = CategoryId.generate();
        DateRange range = new DateRange(LocalDate.now(), LocalDate.now().plusDays(1));
        String comment = "Time off request comment";
        TimeOffRequest timeOffRequest = new TimeOffRequest(employeeId, categoryId, range, comment);
        mockCategory(categoryId);
        mockEmployee(employeeId);

        //when
        TimeOff timeOff = timeOffService.request(timeOffRequest);

        //then
        assertThat(timeOff.getId()).isNotNull();
        assertThat(timeOff)
                .extracting(TimeOff::getEmployeeId, TimeOff::getCategoryId, TimeOff::getDateRange, TimeOff::getComment, TimeOff::getStatus)
                .containsExactly(employeeId, categoryId, range, comment, TimeOff.TimeOffStatus.REQUESTED);
        verify(timeOffRepository).create(timeOff);
    }

    @ParameterizedTest
    @MethodSource("invalidTimeOffRequest")
    void request_should_throw_exception_when_required_fields_are_missing(String field, EmployeeId employeeId, CategoryId categoryId, DateRange range, String comment) {
        //given
        TimeOffRequest timeOffRequest = new TimeOffRequest(employeeId, categoryId, range, comment);
        mockCategory(categoryId);
        mockEmployee(employeeId);

        //when
        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> timeOffService.request(timeOffRequest));
        assertThat(exception).hasMessageContaining(field);
    }

    @Test
    void request_should_throw_CategoryNotFoundException_when_category_does_not_exist() {
        //given
        TimeOffRequest timeOffRequest = new TimeOffRequest(EmployeeId.generate(), CategoryId.generate(), oneDayRange(), null);

        //when
        //then
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> timeOffService.request(timeOffRequest));
        assertThat(exception).hasMessageContaining("Category not found");
    }

    @Test
    void request_should_throw_EmployeeNotFoundException_when_employee_does_not_exist() {
        //given
        TimeOffRequest timeOffRequest = new TimeOffRequest(EmployeeId.generate(), CategoryId.generate(), oneDayRange(), null);
        mockCategory(timeOffRequest.categoryId());

        //when
        //then
        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> timeOffService.request(timeOffRequest));
        assertThat(exception).hasMessageContaining("Employee not found");
    }

    private void mockCategory(CategoryId categoryId) {
        TimeOffCategory category = new TimeOffCategory(categoryId, "Category name", "Category description", true);
        when(timeOffCategoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
    }

    private void mockEmployee(EmployeeId employeeId) {
        Employee employee = EmployeeFixtures.generate();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
    }


    private static DateRange oneDayRange() {
        return new DateRange(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    public static Stream<Arguments> invalidTimeOffRequest() {
        return Stream.of(
                Arguments.of("employeeId", null, CategoryId.generate(), new DateRange(LocalDate.now(), LocalDate.now().plusDays(1)), "Time off request comment"),
                Arguments.of("categoryId", EmployeeId.generate(), null, new DateRange(LocalDate.now(), LocalDate.now().plusDays(1)), "Time off request comment"),
                Arguments.of("dateRange", EmployeeId.generate(), CategoryId.generate(), null, "Time off request comment")
        );
    }
}
