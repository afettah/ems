package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;
import com.tech.timeoff.category.CategoryId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TimeOffServiceTest {
    @Mock
    TimeOffRepository timeOffRepository;
    TimeOffService timeOffService;

    @BeforeEach
    void setUp() {
        timeOffService = new TimeOffService(timeOffRepository);
    }

    @Test
    void request_should_create_requested_timeoff() {
        //given
        EmployeeId employeeId = EmployeeId.generate();
        CategoryId categoryId = CategoryId.generate();
        DateRange range = new DateRange(LocalDate.now(), LocalDate.now().plusDays(1));
        String comment = "Time off request comment";
        TimeOffRequest timeOffRequest = new TimeOffRequest(employeeId, categoryId, range, comment);

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

        //when
        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> timeOffService.request(timeOffRequest));
        assertThat(exception).hasMessageContaining(field);

    }

    public static Stream<Arguments> invalidTimeOffRequest() {
        return Stream.of(
                Arguments.of("employeeId", null, CategoryId.generate(), new DateRange(LocalDate.now(), LocalDate.now().plusDays(1)), "Time off request comment"),
                Arguments.of("categoryId", EmployeeId.generate(), null, new DateRange(LocalDate.now(), LocalDate.now().plusDays(1)), "Time off request comment"),
                Arguments.of("dateRange", EmployeeId.generate(), CategoryId.generate(), null, "Time off request comment"),
                Arguments.of("comment", EmployeeId.generate(), CategoryId.generate(), new DateRange(LocalDate.now(), LocalDate.now().plusDays(1)), null)
        );
    }
}
