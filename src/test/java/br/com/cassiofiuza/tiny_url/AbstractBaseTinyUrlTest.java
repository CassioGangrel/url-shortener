package br.com.cassiofiuza.tiny_url;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class AbstractBaseTinyUrlTest {
    static final String ORIGINAL_URL = "http://www.google.com";
    static final LocalDateTime _2daysFromNow = LocalDateTime.now().plus(2, ChronoUnit.DAYS);
}