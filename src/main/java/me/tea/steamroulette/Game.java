package main.java.me.tea.steamroulette;

public class Game {

    private String appid;
    private String name;
    private String playtime_forever;

    public Game(String appid, String name, String playtime_forever){
        this.appid = appid;
        this.name = name;
        this.playtime_forever = playtime_forever;
    }

    public int getRawPlaytime() {
        return Integer.parseInt(playtime_forever);
    }

    public int getPlaytimeInHours(){
        if(Integer.parseInt(playtime_forever) > 0)
            return (Integer.parseInt(playtime_forever)/60);

        return 0;
    }

    public int getAppID() {
        return Integer.parseInt(appid);
    }

    public String getName() {
        return name;
    }
}
