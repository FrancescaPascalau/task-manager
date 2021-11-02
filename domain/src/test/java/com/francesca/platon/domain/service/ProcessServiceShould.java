package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Priority;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProcessServiceShould {

    @ParameterizedTest
    @EnumSource(value = Priority.class)
    void create_a_new_process_with_a_specific_priority(Priority priority){
        var process = ProcessService.createProcess(priority);

        assertThat(process.getPid()).isNotZero();
        assertThat(process.getCreatedAt()).isNotNull();
        assertThat(process.getPriority()).isEqualTo(priority);
    }
}
