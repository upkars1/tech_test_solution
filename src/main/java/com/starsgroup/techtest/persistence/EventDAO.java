package com.starsgroup.techtest.persistence;

import com.starsgroup.techtest.domain.Event;

import java.io.IOException;

public interface EventDAO {

    void save ( Event unsaved ) throws IOException;
}
