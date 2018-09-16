package GUIView.ComponentCreation;

import GUIView.GameGUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;

public class MenuBarGameReferee extends MenuBar {


    public MenuBarGameReferee(GameGUI parent) { //erstellt den obersten Menü-Balken; fügt diesem außerdem chronologisch
        // alle
        // MenutItems hinzu

        Menu gameMenu = new Menu("_Game");
        gameMenu.setMnemonicParsing(true);

        MenuItem newGame = createMenuItem("_Neu", "SHORTCUT + N", "/icons/open-menu-icon.png", null);
        newGame.setOnAction(event -> parent.createCloneGui());
        MenuItem printGame = createMenuItem("_Print", "SHORTCUT + P", "/icons/print-menu-icon.png", null);
        MenuItem startGame = createMenuItem("St_art", "SHORTCUT + A", null, null);
        MenuItem stopGame = createMenuItem("_Stop", "SHORTCUT + S", null, null);
        stopGame.setDisable(true);

        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public synchronized void handle(ActionEvent event) {
                if (parent.getGame().getDummyDummyGame() == null) {
                    parent.getGame().startDummyDummyGame(parent.getGame());
                } else {
                    if (parent.getGame().getDummyDummyGame().isInterrupted()) {
                        parent.getGame().getDummyDummyGame().notify();
                    }
                }
                startGame.setDisable(true);
                stopGame.setDisable(false);
            }
        });

        stopGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public synchronized void handle(ActionEvent event) {
                try {
                    parent.getGame().setDummyDummyGameOnWait();
                } catch (Exception e) {

                }
                stopGame.setDisable(true);
                startGame.setDisable(false);
            }
        });

        /*DisableButtonProperty disableStartBean = new DisableButtonProperty();
        disableStartBean.valueProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                startGame.setDisable(newValue);
            }
        });

        DisableButtonProperty disableStopBean = new DisableButtonProperty();
        disableStopBean.valueProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                stopGame.setDisable(newValue);

            }
        });*/

        MenuItem exitGame = createMenuItem("_Exit", "SHORTCUT + Q", null, null);
        exitGame.setOnAction(event -> parent.exitAllGUIs());

        gameMenu.getItems().addAll(newGame, printGame, new SeparatorMenuItem(), startGame, stopGame, new SeparatorMenuItem(), exitGame);
        //Separator können nicht benutzt werden; addAll() erwartet MenuItems. Dazu gibt es die Klasse SeparatorMenuItem.

        this.getMenus().addAll(gameMenu, createPlayerAMenu(), createPlayerBMenu());

    }

    public MenuItem createMenuItem(String text, String shortcut, String graphicPath, EventHandler<ActionEvent> eventHandler) {
        MenuItem menuItem = new MenuItem();
        if (text != null) {
            menuItem.setText(text);
        }
        if (shortcut != null) {
            menuItem.setAccelerator(KeyCombination.keyCombination(shortcut));
        }
        if (graphicPath != null) {
            Image image = new Image(getClass().getResource(graphicPath).toString());
            menuItem.setGraphic(new ImageView(image));
        }
        if (eventHandler != null) {
            menuItem.setOnAction(eventHandler);
        }
        return menuItem;

    }

    private Menu createPlayerBMenu() {
        Menu playerBMenu = new Menu("Player _B");
        playerBMenu.setMnemonicParsing(true);

        RadioMenuItem playerBButton = new RadioMenuItem("Player");
        playerBButton.setAccelerator(KeyCombination.valueOf("SHORTCUT + S"));
        playerBButton.setSelected(true);
        playerBMenu.getItems().add(playerBButton);

        return playerBMenu;
    }

    private Menu createPlayerAMenu() {
        Menu playerAMenu = new Menu("Player _A");
        playerAMenu.setMnemonicParsing(true);

        RadioMenuItem playerAButton = new RadioMenuItem("Player");
        playerAButton.setSelected(true);
        playerAButton.setAccelerator(KeyCombination.valueOf("SHORTCUT + S"));
        playerAMenu.getItems().add(playerAButton);

        return playerAMenu;
    }
}
