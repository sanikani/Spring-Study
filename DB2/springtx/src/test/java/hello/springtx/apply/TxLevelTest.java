package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class TxLevelTest {

    @Autowired LevelService service;

    @Test
    void orderTest() {
        service.write();
        service.read();
    }

    //LevelService Bean 등록
    @TestConfiguration
    static class TxApplyLevelConfig {
        @Bean
        LevelService levelService() {
            return new LevelService();
        }
    }

    @Slf4j
    //Class단에 Transactional 설정(읽기전용)
    @Transactional(readOnly = true)
    static class LevelService {

        @Transactional
        //method단에 Transactional 설정하지만 Transactional의 default는 readOnly = false이므로 우선적용
        public void write() {
            log.info("write");
            printTxInfo();
        }

        public void read() {
            log.info("read");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive: {}", txActive);

            //현재 트랜잭션에 적용된 readOnly 옵션의 값을 반환한다
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("readOnly: {}", readOnly);
        }
    }
}
