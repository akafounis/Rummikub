package game;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

  @Test
  public void initTest(){
    Player player1 = new Player("Hyunsung", 23);

    assertEquals(player1.getAge(), 23);
    assertEquals(player1.getName(),  "Hyunsung");
    assertEquals(player1.getHandHeight(),  3);
    assertEquals(player1.getHandWidth(),  20);

    player1.pushStone(new Stone(Stone.Color.RED, 1));
    player1.pushStone(new Stone(Stone.Color.YELLOW, 2));
    player1.pushStone(new Stone());

    //A Joker counts as 20 negative points, extra rule!
    assertEquals(23, player1.points());

    assertTrue(player1.getStones().size() == 3);
    player1.clearHand();
    assertEquals(player1.getHand().size(), 0);
  }

  @Test
  public void moveTest(){
    Player player2 = new Player("Cedrik", 22);

    player2.pushStone(new Stone(Stone.Color.YELLOW, 2));
    player2.pushStone(new Stone(Stone.Color.JOKER, 3));

    player2.moveStone(new Coordinate(0,0), new Coordinate(3,0));
    Stone stone1 = player2.getStones().get(new Coordinate(3,0));

    assertTrue(stone1.getColor() == Stone.Color.YELLOW);
    assertTrue(stone1.getNumber() == 2);

  }

  @Test
  public void fillHandTest(){
    Player player3 = new Player("Ella", 19);

    for (int i = 0; i < player3.getHand().getWidth(); i++){
      for (int j = 0; j < player3.getHand().getHeight(); j++){
        player3.getHand().setStone(new Coordinate(i,j), new Stone(Stone.Color.JOKER, 1));
      }
    }

    try {
      player3.pushStone(new Stone(Stone.Color.RED, 1));
    } catch (Exception e){
      assertEquals("Hand is full.", e.getMessage());
    }

    assertTrue(player3.getHandSize() == player3.getHandHeight() * player3.getHandWidth());
    player3.popStone(new Coordinate(0,0));
    assertTrue(player3.getHandSize() == player3.getHandHeight() * player3.getHandWidth() - 1);

    player3.notifyEndOfFirstMove();

    assertTrue(player3.hasPlayedFirstMove() == true);

  }

  @Test
  public void sortHandByGroupAndRun() {
    Player player = new Player("name1", 0);
    RummiBag bag = new RummiBag();
    for (int i = 0; i < 20; i++) {
      player.pushStone(bag.removeStone());
    }
    player.moveStone(new Coordinate(0, 0), new Coordinate(19, 1));
    player.moveStone(new Coordinate(3, 0), new Coordinate(3, 1));
    System.out.println("Normal: \n" + player.getHand());
    player.sortHandByGroup();
    System.out.println("Group: \n" + player.getHand());
    player.sortHandByRun();
    System.out.println("Run: \n" + player.getHand());
    player.sortHandByGroup();
    System.out.println("Group: \n" + player.getHand());
  }


  @Test
  public void pointsTest() {
    Player player = new Player("name1", 0);
    RummiBag bag = new RummiBag();
    for (int i = 0; i < 20; i++) {
      player.pushStone(bag.removeStone());
    }
    player.moveStone(new Coordinate(0, 0), new Coordinate(19, 1));
    player.moveStone(new Coordinate(3, 0), new Coordinate(3, 1));



    System.out.println(player.points());
  }

}