package com.example.demo.modules.share;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;


public class Stock {



    @Getter
    @Setter
    private Date date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    final static String API_KEY = "C5R0KD15LKFP929I";

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public Stock(String open, String high, String low, String close, String volume) {

        this.date = new Date();
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;

    }

    public static String getShareByName(String shareName, String func) throws IOException {
        Date d = new Date();

        URL url = buildUrl(shareName, func);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        //


//
        StringBuilder responseBuilder = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            responseBuilder.append(line);
            line = br.readLine();
        }
        br.close();
        return responseBuilder.toString();
    }


    public static URL buildUrl(String shareName, String func) throws MalformedURLException {
        String shareAdress = "https://www.alphavantage.co/query?function=";
        shareAdress += func + "&symbol=" + shareName + "&interval=60min&" + "&apikey=" + API_KEY;

        return new URL(shareAdress);

    }

    public static Stock parseStringToJson(String stock) {
        Gson gson = new Gson();

        Stock s = gson.fromJson(stock, Stock.class);

        return s;
    }

    public static Stock getStock(String shareName, String func) throws IOException {


        String result = getShareByName(shareName, func);
        // JSONObject rootObject = new JSONObject(result);
        System.out.println(result);

        if (func.equals("TIME_SERIES_INTRADAY")) {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(result, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = new JsonArray();
            for (String key : jsonObject.keySet()) {
                jsonArray.add(jsonObject.get(key));
            }

            JsonArray timeSeriesArray = new JsonArray();
            JsonObject timeSeriesObject = jsonArray.get(1).getAsJsonObject();
            for (String key : timeSeriesObject.keySet()) {
                timeSeriesArray.add(timeSeriesObject.get(key));
            }

            String currentDate = "";
            String currentOpen = timeSeriesArray.get(0).getAsJsonObject().get("1. open").toString();
            String currentHigh = timeSeriesArray.get(0).getAsJsonObject().get("2. high").toString();
            String currentLow = timeSeriesArray.get(0).getAsJsonObject().get("3. low").toString();
            String currentClose = timeSeriesArray.get(0).getAsJsonObject().get("4. close").toString();
            String currentVolume = timeSeriesArray.get(0).getAsJsonObject().get("5. volume").toString();
            System.out.println("Open: " + currentOpen);
            System.out.println("High: " + currentHigh);
            System.out.println("low: " + currentLow);
            System.out.println("close: " + currentClose);
            System.out.println("volume: " + currentVolume);
            return new Stock(currentOpen, currentHigh, currentLow, currentClose, currentVolume);

        }else if(func.equals("TIME_SERIES_DAILY_ADJUSTED")) {

            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(result, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = new JsonArray();
            for (String key : jsonObject.keySet()) {
                jsonArray.add(jsonObject.get(key));
            }

            JsonArray timeSeriesArray = new JsonArray();
            return null;

            // JsonObject timeSeriesObject = jsonArray.get

            //.get(1).getAsJsonObject();



 /*           for (String key : timeSeriesObject.keySet()) {
                timeSeriesArray.add(timeSeriesObject.get(key));
            }*/
            //in timeSeriesObject das Datum finden und den close wert zurücklierfern
        }
            return null;/*new Stock(new Date(System.currentTimeMillis()), currentOpen, currentHigh, currentLow, currentClose, currentVolume);*/
    }

}