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
//		theModel.getStore().getAllProducts().put("a", new Product("a", 1, 2, new Customer("a", "a", false)));
//		theModel.getStore().getAllProducts().put("b", new Product("a", 6, 3, new Customer("a", "a", false)));
//		theModel.getStore().getAllProducts().put("c", new Product("a", 1, 16, new Customer("a", "a", false)));

		View theView = new View(primaryStage);
		Controller TheController = new Controller(theModel, theView);
	}

}
