package br.com.cassiofiuza.tiny_url;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

public class NanoIdGeneratorTest {
    Logger logger = Logger.getLogger(NanoIdGeneratorTest.class);
    
        @Test
        public void shouldGenerate1000IdsWithoutColision() {
            var length = 1000;
            Set<String> ids = new HashSet<>(length);

            for (int i = 0; i < length; i++) {
                String id = NanoIdGenerator.generate();
                ids.add(id);
                logger.info(id + " - " + ids.size());
            }
            
            assertTrue(ids.size() == length);
        }
}
