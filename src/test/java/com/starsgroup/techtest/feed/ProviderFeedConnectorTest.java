package com.starsgroup.techtest.feed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProviderFeedConnectorTest {

    @InjectMocks
    private ProviderFeedConnector testSubject;

    @Mock
    private FeedContentReader reader;

    @Mock
    private FeedContentReaderContext expectedContext;

    @Test
    public void shouldGetFeed() throws IOException {
        when( reader.connect() ).thenReturn( expectedContext );

        FeedContentReaderContext feed = testSubject.getFeed();

        verify( reader ).connect();
        assertEquals( expectedContext, feed );
    }
}