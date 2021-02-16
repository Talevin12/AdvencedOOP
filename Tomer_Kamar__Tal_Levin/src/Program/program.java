package Program;

import Controller.Controller;
import Model.Customer;
import Model.Model;
import Model.Product;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class program extends Application{

	public static void main(String[] args) /*throws InvalidInputException*/ {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Model theModel = new Model();

		View theView = new View(primaryStage);
		Controller TheController = new Controller(theModel, theView);
	}

}
