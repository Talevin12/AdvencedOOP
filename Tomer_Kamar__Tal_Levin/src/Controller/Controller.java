package Controller;

import java.util.ArrayList;

import Model.Model;
import ModelCommands.ModelCommands;
import View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import Model.InvalidInputException;

public class Controller {
	private ModelCommands modelCommands;
	private View view;
	
	public Controller(Model model, View view) {
		this.modelCommands = model.getModelCommands();
		this.view = view;
		
		EventHandler<ActionEvent> EventHandlerToAddProductBtn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> fields = view.getAddProductFields();
				try {
					String catalog = ((TextField)fields.get(0)).getText(); 
					if(catalog.isEmpty())
						throw new InvalidInputException("No catalog number was entered");
					
					String prodName = ((TextField)fields.get(1)).getText();
					
					int storePrice = 0;
					if(!((TextField)fields.get(2)).getText().isEmpty()) {
						storePrice = Integer.parseInt(((TextField)fields.get(2)).getText());
						if(storePrice < 0)
							throw new InvalidInputException("Price must be a positive value");
					}
					
					int customerPrice = 0;
					if(!((TextField)fields.get(3)).getText().isEmpty()) {
						customerPrice = Integer.parseInt(((TextField)fields.get(3)).getText());
						if(customerPrice < 0)
							throw new InvalidInputException("Price must be a positive value");
					}
				
					String customerName = ((TextField)fields.get(4)).getText();
					if(!customerName.isEmpty())
						if(!customerName.matches("[a-zA-Z]+"))
							throw new InvalidInputException("Customer name cannot contain non alphabetic letters");
					
					String phoneNumber = ((TextField)fields.get(5)).getText();
					if(!phoneNumber.isEmpty())
						if(!phoneNumber.matches("[0-9]+") || phoneNumber.length() != 10)
							throw new InvalidInputException("Phone number must be 10 digits");
					
					boolean promotions = ((RadioButton)fields.get(6)).isPressed();
					
					modelCommands.addProductCommand(catalog, prodName, storePrice, customerPrice, customerName, phoneNumber, promotions);
					
					for(Node n : fields) {
						if(n.getClass() == TextField.class)
							((TextField)n).clear();
						else
							((RadioButton)n).setSelected(false);
					}
				}
				catch(InvalidInputException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText(e.getMsg());
					alert.show();
				}
				catch(NumberFormatException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Price cannot contain non digit characters");
					alert.show();
				}
			}
		};
		view.EventHandlerToAddProductBtn(EventHandlerToAddProductBtn);
		
		EventHandler<ActionEvent> EventHandlerToShowAllProducts = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String[] productsStr = modelCommands.showAllProducts();
				ListView<String> listView = new ListView<String>();
				for(String str : productsStr)
					listView.getItems().add(str);
				
				view.setProductsListView(listView);
				view.setMainVBox(view.showAllProducts());
			}
		};
		view.EventHandlerToShowAllProducts(EventHandlerToShowAllProducts);
		
		EventHandler<ActionEvent> EventHandlerToFindProductBtn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> fields = view.getSearchProductFields();
				
				try {
					String catalog = ((TextField)fields.get(0)).getText(); 
					if(catalog.isEmpty())
						throw new InvalidInputException("No catalog number was entered");
					String product = modelCommands.searchProduct(catalog);
					((Text)fields.get(1)).setText(product);
				}catch(InvalidInputException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText(e.getMsg());
					alert.show();
				}
			}
		};
		view.EventHandlerToFindProductBtn(EventHandlerToFindProductBtn);
		
		EventHandler<ActionEvent> EventHandlerToShowProfitBtn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String[] profitStr = modelCommands.showProfit();
				ListView<String> listView = new ListView<String>();
				for(String str : profitStr)
					listView.getItems().add(str);
				
				view.setProfitListView(listView);
				view.setMainVBox(view.showProfit());
			}
		};
		view.EventHandlerToShowProfitBtn(EventHandlerToShowProfitBtn);
	}
}
