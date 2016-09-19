package ru.belov.example.service;

import com.google.gson.Gson;
import ru.belov.pubsub.model.JPack;

/**
 * Created by john on 9/19/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class JParser {

    private  static Gson gson = new Gson();

    public static void parse(String message) {
        JPack pack = gson.fromJson(message, JPack.class);

    }
}
