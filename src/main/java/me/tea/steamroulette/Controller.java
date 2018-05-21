package main.java.me.tea.steamroulette;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import main.java.org.json.simple.JSONArray;
import main.java.org.json.simple.JSONObject;
import main.java.org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Random;

public class Controller {

    @FXML
    JFXTextField steamid64input;

    @FXML
    JFXButton applybutton;

    @FXML
    Label info;

    @FXML
    Label game;

    @FXML
    Label appid;

    @FXML
    Label playtime;

    public void onApply(){
        if(applybutton.getText().equalsIgnoreCase("Roulette")){
            roulette();
            return;
        }

        if(steamid64input.getText().isEmpty()){
            info.setText("Please input a SteamID64 to search.");
            info.setTextFill(Paint.valueOf("red"));
            info.setVisible(true);
            return;
        }

        try{
            URL url = new URL("http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=762E4C6E055A0E1F835C82C1174503A1&steamid=" + steamid64input.getText() + "&format=json&include_appinfo=1");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", "");

            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            StringBuilder builder = new StringBuilder();
            for (int line; (line = in.read()) >= 0;)
                builder.append((char)line);

            if(builder.toString().contains("<h1>Internal Server Error</h1>")){
                info.setText("That SteamID64 could not be found.");
                info.setTextFill(Paint.valueOf("red"));
                info.setVisible(true);
                return;
            }

            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(builder.toString());
            JSONObject response = (JSONObject) jsonObject.get("response");

            JSONArray nextGame = (JSONArray) response.get("games");
            Iterator i = nextGame.iterator();

            while(i.hasNext()){
                JSONObject item = (JSONObject) i.next();

                Main.addGame(String.valueOf(item.get("appid")), String.valueOf(item.get("name")), String.valueOf(item.get("playtime_forever")));
                System.out.println("Game added: " + item.get("name") + " (" + item.get("appid") + ") Playtime: " + item.get("playtime_forever"));
            }

            System.out.println("Game HashMap size: " + Main.getGame().size());

            info.setText(Main.getGame().size() + " games loaded!");
            info.setTextFill(Paint.valueOf("green"));
            info.setVisible(true);
            applybutton.setText("Roulette");
            steamid64input.setDisable(true);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void roulette(){
        Random rand = new Random();
        int n = 0;

        for(int i = 0; i <= 100; i++)
            n = rand.nextInt(Main.getGame().size());

        Game g = Main.getGame().get(n);

        //info.setText("Game picked: " + g.getName() + " (" + g.getAppID() + ") - Playtime: " + g.getPlaytimeInHours() + " hours (" + g.getRawPlaytime() + " minutes)");
        //info.setTextFill(Paint.valueOf("green"));
        //info.setVisible(false);

        game.setText("Game: " + g.getName());
        appid.setText("AppID: " + g.getAppID());
        playtime.setText("Playtime: " + g.getPlaytimeInHours() + "h (" + g.getRawPlaytime() + " minutes)");

        System.out.println("Game picked: " + g.getName() + " (" + g.getAppID() + ") - Playtime: " + g.getPlaytimeInHours() + " hours (" + g.getRawPlaytime() + " minutes)");
    }


    // http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=762E4C6E055A0E1F835C82C1174503A1&steamid=76561198097674281&format=json&include_appinfo=1
}
