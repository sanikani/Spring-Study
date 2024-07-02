package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    CallService callService;

    @Test
    void externalCall() {
        callService.external();
    }

    @Test
    void internalCall() {
        callService.internal();
    }

    @Test
    void printProxy() {
        log.info("callService class={}", callService.getClass());
    }

    @TestConfiguration
    static class InternalCallV1Config {
        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService{

        public void external() {
            log.info("external call");
            printTxInfo();
            internal();
        }

        @Transactional
        //@Transactional이 하나라도 적용되었으므로 CallService는 프록시 객체가 생성됨
        public void internal() {
            log.info("internal call");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive: {}", txActive);
        }


    }
}
