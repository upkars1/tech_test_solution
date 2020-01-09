package com.starsgroup.techtest.persistence;

import com.starsgroup.techtest.domain.Outcome;

import java.io.IOException;

public interface OutcomeDAO {

    void save (Outcome outcome) throws IOException;
}
