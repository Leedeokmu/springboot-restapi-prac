package com.freeefly.restapiprac.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceUtils {
    private static MessageSource messageSource;

    public MessageSourceUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // code정보에 해당하는 메시지를 조회합니다.
    public static String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    public static String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
