package com.reina.koios.client.backend.api.network;

import com.reina.koios.client.backend.api.base.exception.ApiException;
import com.reina.koios.client.backend.api.network.model.Genesis;
import com.reina.koios.client.backend.api.network.model.Tip;
import com.reina.koios.client.backend.api.network.model.Totals;
import com.reina.koios.client.backend.factory.BackendFactory;
import com.reina.koios.client.backend.factory.options.Limit;
import com.reina.koios.client.backend.factory.options.Options;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NetworkServiceIntegrationTest {

    private NetworkService networkService;

    @BeforeAll
    public void setup() {
        networkService = BackendFactory.getKoiosTestnetService().getNetworkService();
    }

    @Test
    void getChainTipTest() throws ApiException {
        Tip[] tip = networkService.getChainTip();
        log.info(Arrays.toString(tip));
        Assertions.assertNotNull(tip);
    }

    @Test
    void getGenesisInfoTest() throws ApiException {
        Genesis[] genesis = networkService.getGenesisInfo();
        log.info(Arrays.toString(genesis));
        Assertions.assertNotNull(genesis);
        Assertions.assertEquals("45000000000000000", genesis[0].getMaxlovelacesupply());
    }

    @Test
    void getHistoricalTokenomicStatsTest() throws ApiException {
        Long epochNo = 180L;
        Totals[] historicalTokenomicStats = networkService.getHistoricalTokenomicStats(180L);
        log.info(Arrays.toString(historicalTokenomicStats));
        Assertions.assertNotNull(historicalTokenomicStats);
        Assertions.assertEquals(epochNo,historicalTokenomicStats[0].getEpochNo());
    }

    @Test
    void getHistoricalTokenomicStatsLimitTest() throws ApiException {
        Options options = Options.builder().option(Limit.of(10)).build();
        Totals[] historicalTokenomicStats = networkService.getHistoricalTokenomicStats(options);
        log.info(Arrays.toString(historicalTokenomicStats));
        Assertions.assertNotNull(historicalTokenomicStats);
        Assertions.assertEquals(10,historicalTokenomicStats.length);
    }

    @Test
    void getHistoricalTokenomicStatsBadRequestTest() {
        ApiException exception = assertThrows(ApiException.class, () -> networkService.getHistoricalTokenomicStats(-5L));
        assertInstanceOf(ApiException.class, exception);
        assertEquals(exception.getCode(), HttpStatus.BAD_REQUEST.value());
    }
}
