package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Run command", key = {"r", "run", "start"})
    public void runApplication() {
        testRunnerService.run();
    }
}