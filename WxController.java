import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WxController implements Initializable {

  @FXML
  private Button btnGetWx;

  @FXML
  private TextField txtZipcode;

  @FXML
  private Label lblCityState;

  @FXML
  private Label lblTime;

  @FXML
  private Label lblWeather;

  @FXML
  private Label lblTemperature;

  @FXML
  private Label lblWind;
  
  @FXML
  private Label lblWindDir;

  @FXML
  private Label lblPressure;

  @FXML
  private Label lblHumidity;

  @FXML
  private ImageView iconWx;

  @FXML
  private void handleButtonAction(ActionEvent e) {
    // Create object to access the Model
    WxModel weather = new WxModel();

    // Get zipcode
    String zipcode = txtZipcode.getText();

    // Use the model to get the weather information
    if (weather.getWx(zipcode))
    {
      lblCityState.setText(weather.getLocation());
      lblTime.setText(weather.getTime());
      lblWeather.setText(weather.getWeather());
      lblTemperature.setText(String.valueOf(weather.getTemperature()));
      lblWind.setText(weather.getWind());
      lblWindDir.setText(weather.getWindDir());
      lblPressure.setText(weather.getPressure());
      lblHumidity.setText(weather.getHumidity());
      iconWx.setImage(weather.getImage());
    }
    else
    {
      lblCityState.setText("Invalid Zipcode");
      lblTime.setText("");
      lblWeather.setText("");
      lblTemperature.setText("");
      lblWind.setText("");
      lblWindDir.setText("");
      lblPressure.setText("");
      lblHumidity.setText("");
      iconWx.setImage(new Image("badzipcode.png"));
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }    

}
