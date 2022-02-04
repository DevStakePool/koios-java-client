package com.reina.koios.client.backend.api.epoch;

import com.reina.koios.client.backend.api.base.exception.ApiException;
import com.reina.koios.client.backend.api.epoch.model.EpochInfo;
import com.reina.koios.client.backend.api.epoch.model.EpochParams;
import com.reina.koios.client.backend.factory.BackendFactory;
import com.reina.koios.client.backend.factory.options.Limit;
import com.reina.koios.client.backend.factory.options.Options;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EpochServiceIntegrationTest {

    private EpochService epochService;

    @BeforeAll
    public void setup() {
        epochService = BackendFactory.getKoiosTestnetService().getEpochService();
    }

    @Test
    void getEpochInformationTest() throws ApiException {
        Long epochNo = 180L;
        EpochInfo[] epochInformation = epochService.getEpochInformation(epochNo);
        log.info(Arrays.toString(epochInformation));
        Assertions.assertNotNull(epochInformation);
        Assertions.assertEquals(epochNo, epochInformation[0].getEpochNo());
    }

    @Test
    void getEpochInformationLimitTest() throws ApiException {
        Options options = Options.builder().option(Limit.of(10)).build();
        EpochInfo[] epochInformation = epochService.getEpochInformation(options);
        log.info(Arrays.toString(epochInformation));
        Assertions.assertNotNull(epochInformation);
        Assertions.assertEquals(10,epochInformation.length);
    }

    @Test
    void getEpochParametersTest() throws ApiException {
        Long epochNo = 180L;
        EpochParams[] epochParameters = epochService.getEpochParameters(epochNo);
        log.info(Arrays.toString(epochParameters));
        Assertions.assertNotNull(epochParameters);
        Assertions.assertEquals(epochNo, epochParameters[0].getEpochNo());
    }

    @Test
    void getEpochParametersLimitTest() throws ApiException {
        Options options = Options.builder().option(Limit.of(10)).build();
        EpochParams[] epochParameters = epochService.getEpochParameters(options);
        log.info(Arrays.toString(epochParameters));
        Assertions.assertNotNull(epochParameters);
        Assertions.assertEquals(10,epochParameters.length);
    }
}
