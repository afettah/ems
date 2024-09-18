package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;
import com.tech.timeoff.category.CategoryId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
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
}
