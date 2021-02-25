import static org.junit.jupiter.api.Assertions.*;

import java.io.Serializable;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class RPLSTest {
	Server server;

	@BeforeEach
	void init() {
		Consumer<Serializable> call1 = null,call2 = null;
		server = new Server(call1,call2,-1);
		
	}
	@Test
	void gameinfoIdTest() {
		Consumer<Serializable> call1 = null,call2 = null;
		server = new Server(call1,call2,-1);
		assertEquals(server.serverMessage.player,-1);
	}
	@Test
	void gameinfoArrayTest() {
		Consumer<Serializable> call1 = null,call2 = null;
		server = new Server(call1,call2,-1);
		assertEquals(0,server.serverMessage.onlinePlayers.get(0));
	}
}
	
//	@ParameterizedTest
//	@ValueSource(strings = { "rock","paper", "scissors", "lizard","spock" })
//	void testRsultRock(String play) {
//		server.result("rock", play);
//		if(play.equals("rock")) {
//			assertEquals(0, server.client1.roundinfo.winner);
//		}
//        else if (play.equals("paper")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("scissors")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("lizard")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("spock")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        }
//	}
//	
//	@ParameterizedTest
//	@ValueSource(strings = { "rock","paper", "scissors", "lizard","spock" })
//	void testRsultPaper(String play) {
//		server.result("paper", play);
//		if(play.equals("rock")) {
//			assertEquals(1, server.client1.roundinfo.winner);
//		}
//        else if (play.equals("paper")) 
//        {
//        	assertEquals(0, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("scissors")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("lizard")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("spock")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        }
//	}
//	
//	@ParameterizedTest
//	@ValueSource(strings = { "rock","paper", "scissors", "lizard","spock" })
//	void testRsultScissors(String play) {
//		server.result("scissors", play);
//		if(play.equals("rock")) {
//			assertEquals(2, server.client1.roundinfo.winner);
//		}
//        else if (play.equals("paper")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("scissors")) 
//        {
//        	assertEquals(0, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("lizard")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("spock")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        }
//	}
//	
//	@ParameterizedTest
//	@ValueSource(strings = { "rock","paper", "scissors", "lizard","spock" })
//	void testRsultLizard(String play) {
//		server.result("lizard", play);
//		if(play.equals("rock")) {
//			assertEquals(2, server.client1.roundinfo.winner);
//		}
//        else if (play.equals("paper")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("scissors")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("lizard")) 
//        {
//        	assertEquals(0, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("spock")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        }
//	}
//	
//	@ParameterizedTest
//	@ValueSource(strings = { "rock","paper", "scissors", "lizard","spock" })
//	void testRsultSpock(String play) {
//		server.result("spock", play);
//		if(play.equals("rock")) {
//			assertEquals(1, server.client1.roundinfo.winner);
//		}
//        else if (play.equals("paper")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("scissors")) 
//        {
//        	assertEquals(1, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("lizard")) 
//        {
//        	assertEquals(2, server.client1.roundinfo.winner);
//        } 
//        else if (play.equals("spock")) 
//        {
//        	assertEquals(0, server.client1.roundinfo.winner);
//        }
//	}
	

