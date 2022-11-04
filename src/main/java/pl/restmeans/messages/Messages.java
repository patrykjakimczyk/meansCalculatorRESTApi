package pl.restmeans.messages;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Messages {

    private final ApplicationContext context;

    public String getMessage(String message) {
        return context.getMessage(message, null, Locale.getDefault());
    }

}
