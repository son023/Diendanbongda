/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
/**
 *
 * @author pc
 */
public class JSONHelper {
    public JSONHelper() {

    }  

    public static String readJSON(HttpServletRequest request) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonFromRequest = "";
        String tmp = buffer.readLine();
        while (tmp != null) {
            jsonFromRequest += tmp;
            tmp = buffer.readLine();
        }
        return jsonFromRequest;
    }

    public static void sendJsonAsResponse(HttpServletResponse response, int statusCode, Object payload) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(payload);
        response.getWriter().write(json);
        response.setStatus(statusCode);
    }
}
