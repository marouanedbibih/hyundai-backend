package org.hyundai.backend.utils;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MyDTO {
    private Long id;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
