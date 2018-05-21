package main.java.me.tea.steamroulette;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {

    private static HashMap<Integer, Game> game = new HashMap<>();

    public static void addGame(String appid, String name, String playtime){
        Game g = new Game(appid, name, playtime);

        if(!(game.containsValue(g)))
            game.put(game.size(), g);
    }

    public static HashMap<Integer, Game> getGame(){
        return game;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("SteamRoulette");
        primaryStage.setScene(new Scene(root, 445, 191));
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
