package com.lucasmoraist.luflix;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LuflixApplicationTests {

	@Test
	void contextLoads() {
        LuflixApplication.main(new String[] {"--spring.profiles.active=test"});
	}

}
