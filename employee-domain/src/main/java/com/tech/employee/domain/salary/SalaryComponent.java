package com.tech.employee.domain.salary;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedSalaryComponent.class, name = "fixedSalaryComponent")
})
public sealed interface SalaryComponent permits FixedSalaryComponent {
    String label();
}
