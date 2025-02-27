package org.example.coding.urlshortener;

import org.example.coding.urlshortener.keygeneratorstrategy.KeyGeneratorStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {

    @Mock
    private KeyGeneratorStrategy keyGeneratorStrategy;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @Test
    void shouldShortenUrl() {
        String url = "http://verylong.com/verylongverylongverylongverylong?a=1&b=2&c=3";

        when(keyGeneratorStrategy.generate()).thenReturn("a");

        String shortenedUrl = urlShortenerService.shortenUrl(url);

        assertEquals("https://short.ly/a", shortenedUrl);
    }

    @Test
    void shouldGetUrl() {
        String url = "http://verylong.com/verylongverylongverylongverylong?a=1&b=2&c=3";

        when(keyGeneratorStrategy.generate()).thenReturn("a");

        String shortenedUrl = urlShortenerService.shortenUrl(url);

        assertEquals(url, urlShortenerService.getUrl(shortenedUrl));
    }

    @Test
    void shouldGetSameShortUrlWhileShorteningSameLongUrlSeveralTimes() {
        String url = "http://verylong.com/verylongverylongverylongverylong?a=1&b=2&c=3";

        when(keyGeneratorStrategy.generate()).thenReturn("a");

        assertEquals(urlShortenerService.shortenUrl(url), urlShortenerService.shortenUrl(url));
    }

}
