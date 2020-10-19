package view.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import view.ViewConstants;

import java.net.URISyntaxException;

/**
 * Class handling all audio-related capabilities of the application.
 */
public class Audio {

  /**
   * Enum for the different kinds of music.
   */
  public enum Music {
    START,
    WAIT,
    GAME
  }

  /**
   * Enum for the different types of sounds.
   */
  public enum Sound {
    PICK_UP_STONE,
    DROP_STONE,
    DRAW_STONE
  }

  // GAME VIEW MEDIA PLAYERS
  private static MediaPlayer play_pickupStone;
  private static MediaPlayer play_dropStone;
  private static MediaPlayer play_drawStone;

  // PLAYING NOW: FOR START AND WAIT VIEW
  private static MediaPlayer playing_now;


  public static void selectMusic(Music music) {
    stopAllMusic();
    try {
      switch (music) {
        case START:
          Media startView_music = new Media(Audio.class.getResource(ViewConstants.START_MUSIC_MP3).toURI().toString());
          playing_now = new MediaPlayer(startView_music);
          return;
        case WAIT:
          Media waitView_music = new Media(Audio.class.getResource(ViewConstants.WAITING_MUSIC_MP3).toURI().toString());
          playing_now = new MediaPlayer(waitView_music);
          return;
        case GAME:
          Media sound_pickupStone = new Media(Audio.class.getResource(
                  ViewConstants.PICK_UP_STONE_MP3).toURI().toString());

          Media sound_dropStone = new Media(Audio.class.getResource(
                  ViewConstants.DROP_STONE_MP3).toURI().toString());

          Media sound_drawStone = new Media(Audio.class.getResource(
                  ViewConstants.DRAW_STONE_MP3).toURI().toString());

          play_pickupStone = new MediaPlayer(sound_pickupStone);
          play_dropStone = new MediaPlayer(sound_dropStone);
          play_drawStone = new MediaPlayer(sound_drawStone);
      }
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  private static void stopAllMusic() {
    if (playing_now != null) {
      playing_now.stop();
    }
    if (play_pickupStone != null) {
      play_pickupStone.stop();
    }
    if (play_dropStone != null) {
      play_dropStone.stop();
    }
    if (play_drawStone != null) {
      play_drawStone.stop();
    }
  }

  // FOR MUSIC IN START OR WAIT VIEW
  public static void playMusicNow() {
    playing_now.play();
  }

  public static void playSoundOf(Sound sound) {
    switch (sound) {
      case PICK_UP_STONE:
        play_pickupStone.stop();
        play_pickupStone.play();
        break;
      case DROP_STONE:
        play_dropStone.stop();
        play_dropStone.play();
        break;
      case DRAW_STONE:
        play_dropStone.stop();
        play_drawStone.play();
    }
  }

  public static void muteSoundOfWait() {
    playing_now.pause();
  }

}
