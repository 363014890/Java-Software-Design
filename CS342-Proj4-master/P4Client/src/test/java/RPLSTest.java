import static org.junit.jupiter.api.Assertions.*;

import java.io.Serializable;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RPLSTest {

	Client c;
	@BeforeEach
	void init() {
		c = new Client(null, null, 0);
	}
	@Test
	void testInitClient() {
		assertEquals("Client", c.getClass().getName(), "did not initialize proper object");
	}
	@Test
	void ClientIpInitTest() {
		Consumer<Serializable> data = null;
		Client s = new Client(data,"127.0.0.1", -1);
		assertEquals("127.0.0.1",s.ipAddress);
	}
	@Test
	void ClientPortTest() {
		Consumer<Serializable> data = null;
		Client s = new Client(data,"127.0.0.1", 5555);
		assertEquals(5555,s.portnum);
	}
	@Test
	void consumerTest() {
		Consumer<Serializable> data = null;
		Client s = new Client(data,"127.0.0.1", 5555);
		assertNull(data);
	}
	@Test
	void nameTest() {
		Consumer<Serializable> data = null;
		Client s = new Client(data,"127.0.0.1", 5555);
		assertEquals(s.getClass().getName(),"Client");
	}

}
