package com.acme.modres;

import java.io.IOException;


import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;

@WebServlet({ "/resorts/weather" })
public class WeatherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  //private ModResortsCustomerInformation customerInfo;

  // local OS environment variable key name. The key value should provide an API
  // key that will be used to
  // get weather information from site: http://www.wunderground.com

  private static final Logger logger = Logger.getLogger(WeatherServlet.class.getName());


  @Override
  public void init() {
    
  }

  @Override
  public void destroy() {
    
  }

  @Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    String methodName = "doGet";
    logger.entering(WeatherServlet.class.getName(), methodName);

    String city = request.getParameter("selectedCity");
    logger.log(Level.FINE, "requested city is " + city);

    getDefaultWeatherData(city, response);
  }

  private void getDefaultWeatherData(String city, HttpServletResponse response)
      throws IOException {
    DefaultWeatherData defaultWeatherData = null;

    try {
      defaultWeatherData = new DefaultWeatherData(city);
    } catch (UnsupportedOperationException e) {
      throw e;
    }

    ServletOutputStream out = null;

    try {
      String responseStr = defaultWeatherData.getDefaultWeatherData();
      response.setContentType("application/json");
      out = response.getOutputStream();
      out.print(responseStr.toString());
      logger.log(Level.FINEST, "responseStr: " + responseStr);
    } catch (Exception e) {
      throw e;
    } finally {

      if (out != null) {
        out.close();
      }

      out = null;
    }
  }

  /**
   * Returns the weather information for a given city
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    doGet(request, response);
  }

  private static String mockKey(String toBeMocked) {
    if (toBeMocked == null) {
      return null;
    }
    String lastToKeep = toBeMocked.substring(toBeMocked.length() - 3);
    return "*********" + lastToKeep;
  }

  private String configureEnvDiscovery() {

    String serverEnv = "";

    serverEnv += System.getProperty("wlp.server.name");
    serverEnv += System.getProperty("wlp.server.name");

    return serverEnv;
  }

}
