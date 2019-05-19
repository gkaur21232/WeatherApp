import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.*;
import java.text.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.scene.image.Image;

public class WxModel {
  private JsonElement jse;
  
  public boolean getWx(String zip)
  {
    try
    {
      URL wuURL = new URL("http://api.openweathermap.org/data/2.5/weather?zip="
					+ zip
					+ "&units=imperial&appid=8ce2b173377f00c76ee0a43cccf99aeb");

      // Open connection
      InputStream is = wuURL.openStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      // Read the results into a JSON Element
      jse = new JsonParser().parse(br);

      // Close connection
      is.close();
      br.close();
    }
    catch (java.io.FileNotFoundException fne)
	 {			
      System.out.println("Error: no cities match your search query");
      return false;
	 }
    catch (java.io.UnsupportedEncodingException uee)
    {
      uee.printStackTrace();
    }
    catch (java.net.MalformedURLException mue)
    {
      mue.printStackTrace();
    }
    catch (java.io.IOException ioe)
    {
      ioe.printStackTrace();
    }
    catch (java.lang.NullPointerException npe)
    {
      npe.printStackTrace();
    }
    catch (java.lang.RuntimeException rne)
    {
      rne.printStackTrace();
    }

    // Check to see if the zip code was valid.
    return isValid();
  }

  public boolean isValid()
  {
    // If the zip is not valid we will get an error field in the JSON
    try {
      String error = jse.getAsJsonObject().get("response").getAsJsonObject().get("error").getAsJsonObject().get("description").getAsString();
      return false;
    }
    catch (java.lang.NullPointerException npe)
    {
      // We did not see error so this is a valid zip
      return true;
    }  
    
  }
  
     public String getLocation()
     {
       return jse.getAsJsonObject().get("name").getAsString();
     }
   
     public String getTime()
     {
       String time = jse.getAsJsonObject().get("dt").getAsString();
       java.util.Date tt = new java.util.Date(Long.parseLong(time)*1000);
       return tt.toString();
     }
   
     public String getWeather()
     {
     return jse.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
     }
      
     public Double getTemperature()
     {  
       Double t = Double.parseDouble(jse.getAsJsonObject().get("main").getAsJsonObject().getAsJsonObject().get("temp").getAsString());
       DecimalFormat df = new DecimalFormat("#0.0");
       return Double.parseDouble(df.format(t));
     }
   
     public String getWind()
     {     
       Double s = Double.parseDouble(jse.getAsJsonObject().get("wind").getAsJsonObject().getAsJsonObject().get("speed").getAsString());
       DecimalFormat df = new DecimalFormat("#0.0");
       return df.format(s);
     }
     
     public String getWindDir()
     {    
       Double deg = Double.parseDouble(jse.getAsJsonObject().get("wind").getAsJsonObject().getAsJsonObject().get("deg").getAsString());
       if(deg< 90 && deg> 0)
       return " NE";
       else if(deg< 180 && deg> 90)
       return " SE";
       else if(deg < 270 && deg> 180)
       return " SW";    
       else if(deg < 360 && deg> 270)
      
      
      
      
       return " NW";
       else if(deg == 90)
       return " E";
       else if(deg == 180)
       return " S";
       else if(deg == 270)
       return " W";
       else 
       return "N";
     }
     public String getPressure()
     {
         Double pressure = Double.parseDouble(jse.getAsJsonObject().get("main").getAsJsonObject().getAsJsonObject().get("pressure").getAsString());
         DecimalFormat df = new DecimalFormat("#0.00");
         return df.format(pressure*3386.389);
     }
   
     public String getHumidity()
     {
       Double hum = Double.parseDouble(jse.getAsJsonObject().get("main").getAsJsonObject().getAsJsonObject().get("humidity").getAsString());
       DecimalFormat df = new DecimalFormat("#%");
       return df.format(hum/100);
     }
     public Image getImage()
     {
       String icon = jse.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
       return new Image( "http://openweathermap.org/img/w/" + icon + ".png");         
     }  
}
