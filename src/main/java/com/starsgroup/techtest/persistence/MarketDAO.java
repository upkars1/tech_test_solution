package com.starsgroup.techtest.persistence;

import com.starsgroup.techtest.domain.Market;

import java.io.IOException;

public interface MarketDAO {

    void save( Market market ) throws IOException;
}
