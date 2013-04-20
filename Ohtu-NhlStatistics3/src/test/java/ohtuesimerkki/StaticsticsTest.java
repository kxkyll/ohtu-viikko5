package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StaticsticsTest {

    private Statistics stats;
    private List<Player> playerList;
//    PlayerReader palayerReader = new PlayerReader() {
//
//        public List<Player> getPlayers() {
//            ArrayList<Player> players = new ArrayList<Player>();
//
//            players.add(new Player("Semenko", "EDM", 4, 12));
//            players.add(new Player("Lemieux", "PIT", 45, 54));
//            players.add(new Player("Kurri", "EDM", 37, 53));
//            players.add(new Player("Yzerman", "DET", 42, 56));
//            players.add(new Player("Gretzky", "EDM", 35, 89));
//
//            return players;
//        }
//    };

    @Before
    public void setUp() {
//        stats = new Statistics(palayerReader);
        stats = mock(Statistics.class);
        playerList = new ArrayList<Player>();
    }

    @Test
    public void playerFound() {
////        public Player(String name, String team, int goals, int assists) {
        when(stats.search("Lemieux")).thenReturn(new Player("Lemieux", "PIT", 45, 54));
        Player p = stats.search("Lemieux");
        assertEquals("PIT", p.getTeam());
        assertEquals(45, p.getGoals());
        assertEquals(54, p.getAssists());
        assertEquals(45 + 54, p.getPoints());

    }

    @Test
    public void teamMembersFound() {
        playerList.add(new Player("Semenko", "EDM", 4, 12));
        playerList.add(new Player("Kurri", "EDM", 37, 53));
        playerList.add(new Player("Gretzky", "EDM", 35, 89));
        when(stats.team("EDM")).thenReturn(playerList);
        List<Player> players = stats.team("EDM");
        assertEquals(3, players.size());
        for (Player player : players) {
            assertEquals("EDM", player.getTeam());
        }
    }

    @Test
    public void topScorersFound() {
        playerList.add(new Player("Gretzky", "EDM", 35, 89));
        playerList.add(new Player("Lemieux", "PIT", 45, 54));
        playerList.add(new Player("Kurri", "EDM", 37, 53));
        
        when(stats.topScorers(2)).thenReturn(playerList);
        List<Player> players = stats.topScorers(2);
        assertEquals(3, players.size());
        assertEquals("Gretzky", players.get(0).getName());
        assertEquals("Lemieux", players.get(1).getName());
    }
}
