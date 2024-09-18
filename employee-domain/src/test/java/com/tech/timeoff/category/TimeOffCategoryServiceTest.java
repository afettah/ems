package com.tech.timeoff.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TimeOffCategoryServiceTest {
    @Mock
    private TimeOffCategoryRepository timeOffCategoryRepository;
    private TimeOffCategoryService timeOffCategoryService;

    @BeforeEach
    void setUp() {
        timeOffCategoryService = new TimeOffCategoryService(timeOffCategoryRepository);
    }

    @Test
    void createCategory_should_create_category() {
        //given
        String name = "Vacation";
        String description = "Paid time off";
        boolean paid = true;

        //when
        TimeOffCategory category = timeOffCategoryService.createCategory(name, description, paid);

        //then
        assertThat(category.id()).isNotNull();
        assertThat(category)
                .extracting(TimeOffCategory::name, TimeOffCategory::description, TimeOffCategory::paid)
                .containsExactly(name, description, paid);
        verify(timeOffCategoryRepository).create(category);
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "name, , description",
                    "name, '', description"
            }
    )
    void createCategory_should_fail_if_required_fields_are_missing(String field, String name, String description) {
        //given
        boolean paid = false;

        //when
        //then
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> timeOffCategoryService.createCategory(name, description, paid));
        assertThat(exception).hasMessageContaining(field);
    }
}
