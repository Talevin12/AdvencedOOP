package Controller;

import Model.Model;
import ModelCommands.ShowProductCommand;
import View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class Controller {
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		EventHandler<ActionEvent> EventHandlerToShowAllProducts = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ShowProductCommand command = model.getStore().getShowProdCommand();
				command.setListView(view.getProductsListView());
				command.execute();
				
				view.setMainVBox(view.showAllProducts());
			}
		};
		view.EventHandlerToShowAllProducts(EventHandlerToShowAllProducts);
	}
}
