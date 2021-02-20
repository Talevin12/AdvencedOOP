package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import Model.Model;
import Model.Product;
import Model.Store;
import ModelCommands.ModelCommands;
import View.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Model.FileIterator.ascendingComparator;
import Model.FileIterator.descendingComparator;
import Model.FileIterator.insertionOrderComparator;
import Model.InvalidInputException;

public class Controller {
	private ModelCommands modelCommands; 
	private Model model;
	private View view;

	public Controller(Model model, View view) {
		this.model = model;
		this.modelCommands = this.model.getModelCommands();
		this.view = view;

		EventHandler<MouseEvent> Eventhandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					if(model.updateMapFromFile()) {
						view.setMainScene();
					}
				} catch (ClassNotFoundException e) {
					System.out.println(e.getStackTrace());
				} catch	(IOException e) {
					System.out.println(e.getStackTrace());
				}
			}
		};
		view.Eventhandler(Eventhandler);

		EventHandler<ActionEvent> EventHandlerToSortSubmitBtn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<RadioButton> rbArray = view.getSortRadioBtn();

				if(rbArray.get(0).isSelected())
					Store.getInstance().setAllProducts(new TreeMap<>(new ascendingComparator()));
				else if(rbArray.get(1).isSelected())
					Store.getInstance().setAllProducts(new TreeMap<>(new descendingComparator()));
				else if(rbArray.get(2).isSelected())
					Store.getInstance().setAllProducts(new TreeMap<>(new insertionOrderComparator()));
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("No sort type was selected");
					alert.show();
					return;
				}

				try {
					model.getIterator().writeProducts(Store.getInstance().getAllProducts());;
				} catch (FileNotFoundException e) {	}
				catch (IOException e) 		  {	}

				view.setMainScene();
			}
		};
		view.EventHandlerToSortSubmitBtn(EventHandlerToSortSubmitBtn);

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

					boolean promotions = ((RadioButton)fields.get(6)).isSelected();

					modelCommands.addProductCommand(catalog, prodName, storePrice, customerPrice, customerName, phoneNumber, promotions);

					for(Node n : fields) {
						if(n.getClass() == TextField.class)
							((TextField)n).clear();
						else
							((RadioButton)n).setSelected(false);
					}
					view.changeAddFeedbackVisibility();
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
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		view.EventHandlerToAddProductBtn(EventHandlerToAddProductBtn);

		EventHandler<ActionEvent> EventHandlerToUndoBtn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean b = modelCommands.undoInsert();

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				if(b)
					alert.setContentText("Action Performed!");
				else
					alert.setContentText("Nothing to undo");
				alert.show();

				view.setMainVBox(new VBox());
			}
		};
		view.EventHandlerToUndoBtn(EventHandlerToUndoBtn);

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
					Product product = modelCommands.searchProduct(catalog);
					if(product == null)
						((Text)fields.get(1)).setText("No such product was found :(");
					else
						((Text)fields.get(1)).setText(product.toString());
				}catch(InvalidInputException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText(e.getMsg());
					alert.show();
				}
			}
		};
		view.EventHandlerToFindProductBtn(EventHandlerToFindProductBtn);

		EventHandler<ActionEvent> EventHandlerToDeleteProductBtn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> fields = view.getSearchProductFields();

				try {
					String catalog = ((TextField)fields.get(0)).getText(); 
					if(catalog.isEmpty())
						throw new InvalidInputException("No catalog number was entered");
					
					if(modelCommands.deleteProduct(catalog)) {
						((TextField)fields.get(0)).clear();
						((Text)fields.get(1)).setText("Product Deleted!");
					}
					else
						((Text)fields.get(1)).setText("No such product was found :(");
				}catch(InvalidInputException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText(e.getMsg());
					alert.show();
				}
			}
		};
		view.EventHandlerToDeleteProductBtn(EventHandlerToDeleteProductBtn);

		EventHandler<ActionEvent> EventHandlerToDeleteAllBtn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				modelCommands.deleteAll();
				view.setMainVBox(new VBox());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Action Performed!");
				alert.show();
			}
		};
		view.EventHandlerToDeleteAllBtn(EventHandlerToDeleteAllBtn);

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

		EventHandler<ActionEvent> EventHandlerToSendPromotions = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				modelCommands.sendPromotion();

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("The Promotions Have Been Sent");
				alert.show();
			}
		};
		view.EventHandlerToSendPromotions(EventHandlerToSendPromotions);

		EventHandler<ActionEvent> EventHandlerToShowAcceptedCustomers = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Thread t = new Thread(() -> {
					try {
						ListView<String> listView = new ListView<String>();
						String name = "";		
						while((name = modelCommands.showAcceptedCustomer()) != null) {
							Thread.sleep(2000);
							String temp = name;
							Platform.runLater(() -> {
								listView.getItems().add(temp);
								view.setAcceptedCustomersListView(listView);
								view.setMainVBox(view.showAcceptedCustomersListView());
							});
						}
					} catch (InterruptedException e1) {

					}
				});
				t.start();
			}
		};
		view.EventHandlerToShowAcceptedCustomers(EventHandlerToShowAcceptedCustomers);

		//		EventHandler<ActionEvent> EventhandlerExitBtn = new EventHandler<ActionEvent>() {
		//			@Override
		//			public void handle(ActionEvent event) {
		//				try {
		//					model.getIterator().closeOutputStreams();
		//					Stage s = (Stage) view.getExitBtn().getParent().getScene().getWindow(); 
		//					s.close();
		//				} catch (IOException e) {
		//					System.out.println(e.getMessage());
		//				}
		//			}
		//		};
		//		view.EventhandlerExitBtn(EventhandlerExitBtn);
	}
}
