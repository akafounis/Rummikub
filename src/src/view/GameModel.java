package view;

import java.util.Observable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Local model storing data of the players.
 */
public class GameModel extends Observable {

  private String[] name;
  private int[] age;
  private int index;

  /**
   * Add a name to the data.
   *
   * @param name of one of the players
   */
  public void setName(String name) {
    if (index < 3) {
      this.name[index] = name;
    }
    index++;
  }

  /**
   * Add an age to the data.
   *
   * @param age of one of the players.
   */
  public void setAge(int age) {
    if (index < 3) {
      this.age[index] = age;
    }
    index++;
  }
}
