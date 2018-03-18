package server.core.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlayerTest {
	private Player player;

	@Before
	public void init() {
		player = new Player("name");
	}

	@Test
	public void setAdmine() {
		player.setAdmine(true);
		assertTrue(player.isAdmine());
	}

	@Test
	public void setName() {
		player.setName("test");
		assertTrue(player.getName().equals("test"));
	}

	@Test
	public void moveHorse() {
		player.moveHorse(0, 2);
		assertTrue(player.getHorse(0) == 2);
	}
}