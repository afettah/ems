package com.tech.ems.timeoff;

import com.tech.ems.employee.Employee;
import com.tech.ems.employee.EmployeeFixtures;
import com.tech.ems.employee.EmployeeId;
import com.tech.ems.employee.EmployeeRepository;
import com.tech.ems.timeoff.category.CategoryId;
import com.tech.ems.timeoff.category.TimeOffCategory;
import com.tech.ems.timeoff.category.TimeOffCategoryRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        TimeOffRequest timeOffRequest = TimeOffFixtures.timeOffRequest();
        TimeOffCategory category = mockCategory(timeOffRequest.categoryId());
        mockEmployee(timeOffRequest.employeeId());
        when(timeOffRepository.findOverlappingDateRange(timeOffRequest.employeeId(), timeOffRequest.dateRange())).thenReturn(Collections.emptyList());

        //when
        TimeOff timeOff = timeOffService.request(timeOffRequest);

        //then
        assertThat(timeOff.getId()).isNotNull();
        assertThat(timeOff)
                .extracting(TimeOff::getEmployeeId, TimeOff::getCategory, TimeOff::getDateRange, TimeOff::getComment, TimeOff::getStatus)
                .containsExactly(timeOffRequest.employeeId(), category, timeOffRequest.dateRange(), timeOffRequest.comment(), TimeOff.TimeOffStatus.REQUESTED);
        verify(timeOffRepository).create(timeOff);
        verifyNoMoreInteractions(timeOffRepository);
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
        TimeOffRequest timeOffRequest = new TimeOffRequest(EmployeeId.generate(), CategoryId.generate(), TimeOffFixtures.oneDayRange(), null);

        //when
        //then
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> timeOffService.request(timeOffRequest));
        assertThat(exception).hasMessageContaining("Category not found");
    }

    @Test
    void request_should_throw_EmployeeNotFoundException_when_employee_does_not_exist() {
        //given
        TimeOffRequest timeOffRequest = new TimeOffRequest(EmployeeId.generate(), CategoryId.generate(), TimeOffFixtures.oneDayRange(), null);
        mockCategory(timeOffRequest.categoryId());

        //when
        //then
        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> timeOffService.request(timeOffRequest));
        assertThat(exception).hasMessageContaining("Employee not found");
    }

    @Test
    @Description("Two categories cannot be requested for the same period")
    void request_should_throw_OverlappingTimeOffException_when_other_timeoff_exists_for_the_same_date_range() {
        //given
        TimeOffRequest timeOffRequest = TimeOffFixtures.timeOffRequest();
        mockCategory(timeOffRequest.categoryId());
        mockEmployee(timeOffRequest.employeeId());
        List<TimeOff> timeOffs = List.of(TimeOffFixtures.timeOff(timeOffRequest.dateRange()));
        mockExistingTimeOff(timeOffRequest.employeeId(), timeOffRequest.dateRange(), timeOffs);

        //when
        //then
        OverlappingTimeOffException exception = assertThrows(OverlappingTimeOffException.class, () -> timeOffService.request(timeOffRequest));
        assertThat(exception).extracting(OverlappingTimeOffException::getDateRange, OverlappingTimeOffException::getTimeOffList)
                .containsExactly(timeOffRequest.dateRange(), timeOffs);
    }

    @Test
    void request_should_success_when_existing_timeoff_are_auto_cancellable() {
        //given
        TimeOffRequest timeOffRequest = TimeOffFixtures.timeOffRequest();
        mockCategory(timeOffRequest.categoryId());
        mockEmployee(timeOffRequest.employeeId());
        List<TimeOff> timeOffs = List.of(TimeOffFixtures.autoCancellable(timeOffRequest.dateRange()), TimeOffFixtures.autoCancellable(timeOffRequest.dateRange()));
        mockExistingTimeOff(timeOffRequest.employeeId(), timeOffRequest.dateRange(), timeOffs);

        //when
        TimeOff timeOff = timeOffService.request(timeOffRequest);

        //then
        assertThat(timeOff.getId()).isNotNull();
        verify(timeOffRepository).create(timeOff);
        for (TimeOff off : timeOffs) {
            verify(timeOffRepository).cancel(off.getId());
        }
        verifyNoMoreInteractions(timeOffRepository);
    }

    private void mockExistingTimeOff(EmployeeId employeeId, DateRange dateRange, List<TimeOff> timeOffs) {
        when(timeOffRepository.findOverlappingDateRange(employeeId, dateRange)).thenReturn(timeOffs);
    }

    private TimeOffCategory mockCategory(CategoryId categoryId) {
        TimeOffCategory category = TimeOffFixtures.category(false);
        when(timeOffCategoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        return category;
    }

    private void mockEmployee(EmployeeId employeeId) {
        Employee employee = EmployeeFixtures.generate();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
    }


    public static Stream<Arguments> invalidTimeOffRequest() {
        return Stream.of(
                Arguments.of("employeeId", null, CategoryId.generate(), new DateRange(LocalDate.now(), LocalDate.now().plusDays(1)), "Time off request comment"),
                Arguments.of("categoryId", EmployeeId.generate(), null, new DateRange(LocalDate.now(), LocalDate.now().plusDays(1)), "Time off request comment"),
                Arguments.of("dateRange", EmployeeId.generate(), CategoryId.generate(), null, "Time off request comment")
        );
    }
}
