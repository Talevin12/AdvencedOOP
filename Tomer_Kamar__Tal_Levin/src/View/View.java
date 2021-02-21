package View;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {
	private Group root;
	private Scene sortOptionScene;
	private Scene mainScene;

	private Text sortOptionTxt;
	private RadioButton ascOrderRB;
	private RadioButton descOrderRB;
	private RadioButton insertOrderRB;
	private Button sortSubmit;
	
	private SplitPane sp;
	private VBox mainVB;
	
	private Button addProductBtn = new Button("Add product");
	private Button undoBtn = new Button("Undo last insert");
	private Button showAllProductsBtn = new Button("Show all products");
	private Button searchProductBtn = new Button("Search/Delete product");
	private Button showProfit = new Button("Show profit");
	private Button deleteAllBtn = new Button("Delete all products");
	private Button sendPromotionBtn = new Button("Send promotions");
	private Button showAcceptedCustomers = new Button("Customers confirmed");
	private Button exitBtn = new Button("Exit");
	
	private TextField catalogNumTF = new TextField();
	private TextField prodNameTF = new TextField();
	private TextField storePriceTF = new TextField();
	private TextField clientPriceTF = new TextField();
	private TextField clientNameTF = new TextField();
	private TextField phoneTF = new TextField();
	private Text promotionTxt = new Text("Would you like us to send you promotion updates?\t");
	private RadioButton promotionYesRB = new RadioButton("Yes ");
	private RadioButton promotionNoRB = new RadioButton("No ");
	private Button addProdSubmitBtn = new Button("Submit");
	private Text addFeedback = new Text("The product was added!");
	
	private ListView<String> listViewProducts;
	
	private TextField findCatalogNumTF = new TextField();
	private Button findCatalogBtn = new Button("Search");
	private Button deleteCatalogBtn = new Button("Delete");
	private Text foundProductTxt;
	
	private ListView<String> profitListView;
	
	private ListView<String> AcceptedPromotionListView;
	
	public View(Stage stage) {
		this.root = new Group();
		stage.setTitle("Product Management System");
		
		this.sortOptionTxt = new Text("Which insert order type would you like:");
		this.sortOptionTxt.setStyle("-fx-font: 24 arial;");
		ToggleGroup tgOrder = new ToggleGroup();
		this.ascOrderRB = new RadioButton("Alphabetical ascending order ");
		this.ascOrderRB.setToggleGroup(tgOrder);
		this.descOrderRB = new RadioButton("Alphabetical descending order ");
		this.descOrderRB.setToggleGroup(tgOrder);
		this.insertOrderRB = new RadioButton("Insert Order ");
		this.insertOrderRB.setToggleGroup(tgOrder);
		
		this.sortSubmit = new Button("Submit");
		
		VBox vBoxOrderMenu = new VBox();
		vBoxOrderMenu.getChildren().addAll(this.sortOptionTxt, this.ascOrderRB, this.descOrderRB, this.insertOrderRB, this.sortSubmit);
		vBoxOrderMenu.setAlignment(Pos.CENTER);
		vBoxOrderMenu.setSpacing(20);
	
		this.sortOptionScene = new Scene(vBoxOrderMenu, 1000, 700);

		//////////////////////////////////////////////////////////////////
		
		VBox naviMenuFunctions = new VBox(addProductBtn, undoBtn, showAllProductsBtn, searchProductBtn, showProfit, deleteAllBtn, sendPromotionBtn, showAcceptedCustomers/*, exitBtn*/);
		
		naviMenuFunctions.setSpacing(20);
		
		this.exitBtn.setMinSize(60, 30);
//		this.exitBtn.
		BorderPane naviMenu = new BorderPane();
		naviMenu.setTop(naviMenuFunctions);
		naviMenu.setBottom(this.exitBtn);
		
		naviMenu.setPadding(new Insets(10));
		
		naviMenu.setMaxWidth(160);
		naviMenu.setMinWidth(160);
		
		addProductBtn.setOnAction(e->{this.mainVB.getChildren().clear(); this.mainVB.getChildren().add(addProductView());});
		searchProductBtn.setOnAction(e->{this.mainVB.getChildren().clear(); this.mainVB.getChildren().add(searchProduct());});
		exitBtn.setOnAction(e->{Stage s = (Stage) exitBtn.getParent().getScene().getWindow(); s.close();});
		
		this.mainVB = new VBox();

		this.sp = new SplitPane(naviMenu, this.mainVB);
		this.mainScene = new Scene(sp, 1000, 700);
		
		//////////////////////////////////////////////////////////////////

		stage.setScene(this.sortOptionScene);
		stage.show();
	}
	
	public void setMainScene() {
		Stage stage = new Stage();
		stage.setMinWidth(1000);
		stage.setMinHeight(700);
		stage.setTitle("Product Management System");
		stage.setScene(this.mainScene);
		stage.show();
		Stage startStage = (Stage)this.sortOptionScene.getWindow();
		startStage.close();
	}
	
	private Pane addProductView() {
		this.catalogNumTF.setPromptText("Catalog number (*Required field* | length: 3)");
		this.catalogNumTF.setMinSize(325, 30);
		this.catalogNumTF.setMaxSize(325, 30);
		this.prodNameTF.setPromptText("Product name");
		this.prodNameTF.setMinSize(325, 30);
		this.prodNameTF.setMaxSize(325, 30);
		this.storePriceTF.setPromptText("Store price");
		this.storePriceTF.setMinSize(325, 30);
		this.storePriceTF.setMaxSize(325, 30);
		this.clientPriceTF.setPromptText("Client Price");
		this.clientPriceTF.setMinSize(325, 30);
		this.clientPriceTF.setMaxSize(325, 30);
		this.clientNameTF.setPromptText("Client name");
		this.clientNameTF.setMinSize(325, 30);
		this.clientNameTF.setMaxSize(325, 30);
		this.phoneTF.setPromptText("Client phone number");
		this.phoneTF.setMinSize(325, 30);
		this.phoneTF.setMaxSize(325, 30);
		
		ToggleGroup tg = new ToggleGroup();
		this.promotionYesRB.setToggleGroup(tg);
		this.promotionNoRB.setToggleGroup(tg);
		
		HBox promHB = new HBox(promotionTxt, promotionYesRB, promotionNoRB);
		promHB.setAlignment(Pos.CENTER);
		promHB.setSpacing(10);
		
		addProdSubmitBtn.setMinSize(100, 50);
		
		this.addFeedback.setVisible(false);
		this.addFeedback.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		this.addFeedback.setFill(Color.LIMEGREEN);
		HBox feedbackHBox = new HBox(this.addFeedback);
		feedbackHBox.setAlignment(Pos.CENTER);
		
		
		VBox addInputs = new VBox(catalogNumTF, prodNameTF, storePriceTF, clientPriceTF, clientNameTF, phoneTF, promHB, addProdSubmitBtn, addFeedback);
		addInputs.setAlignment(Pos.CENTER);
		addInputs.setPadding(new Insets(100));
		addInputs.setSpacing(30);
		
		BorderPane root = new BorderPane();
		root.setCenter(addInputs);
		root.setBottom(feedbackHBox);
		
		return root;
	}
	
	public VBox showAllProducts() {
		this.listViewProducts.setPrefHeight(this.mainScene.getHeight());
		VBox root = new VBox(this.listViewProducts);
		return root;
	}
	
	public VBox searchProduct() {
		this.findCatalogNumTF.setPromptText("Product catalog number (*Required field*)");		
		this.findCatalogNumTF.setMaxSize(250, 80);
		
		this.findCatalogBtn.setMinSize(100, 50);
		this.deleteCatalogBtn.setMinSize(100, 50);
		HBox buttons = new HBox(findCatalogBtn, deleteCatalogBtn);
		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER);
		
		this.foundProductTxt = new Text();
		
		VBox root = new VBox(findCatalogNumTF, buttons, foundProductTxt);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(150));
		root.setSpacing(25);
		
		return root;
	}
	
	public void setFoundProductInfo(String product) {
		this.foundProductTxt.setText(product);
	}
	
	public VBox showProfit() {
		this.profitListView.setPrefHeight(this.mainScene.getHeight());
		VBox root = new VBox(this.profitListView);
		return root;
	}
	
	public VBox showAcceptedCustomersListView() {
		this.AcceptedPromotionListView.setPrefHeight(this.mainScene.getHeight());
		VBox root = new VBox(this.AcceptedPromotionListView);
		return root;
	}
	
	public void setMainVBox(Pane root) {
		this.mainVB.getChildren().clear(); 
		this.mainVB.getChildren().add(root);
	}
	
	public ArrayList<RadioButton> getSortRadioBtn(){
		ArrayList<RadioButton> rbArray = new ArrayList<>();
		
		rbArray.add(this.ascOrderRB);
		rbArray.add(this.descOrderRB);
		rbArray.add(this.insertOrderRB);
		
		return rbArray;
	}
	
	public void setProductsListView(ListView<String> listView) {
		this.listViewProducts = listView;
	}
	
	public void setProfitListView(ListView<String> listView) {
		this.profitListView = listView;
	}
	
	public void setAcceptedCustomersListView(ListView<String> listView) {
		this.AcceptedPromotionListView = listView;
	}
	
	public ArrayList<Node> getAddProductFields(){
		ArrayList<Node> fields = new ArrayList<>();
		
		fields.add(this.catalogNumTF);
		fields.add(this.prodNameTF);
		fields.add(this.storePriceTF);
		fields.add(this.clientPriceTF);
		fields.add(this.clientNameTF);
		fields.add(this.phoneTF);
		fields.add(this.promotionYesRB);
		fields.add(this.promotionNoRB);
		
		return fields;
	}
	
	public void changeAddFeedbackVisibility() {
		this.addFeedback.setVisible(true);
	}
	
	public ArrayList<Node> getSearchProductFields(){
		ArrayList<Node> fields = new ArrayList<>();
		
		fields.add(this.findCatalogNumTF);
		fields.add(this.foundProductTxt);
		
		return fields;
	}
	
	public Button getExitBtn() {
		return this.exitBtn;
	}
	
	public void EventHandlerToSortSubmitBtn(EventHandler<ActionEvent> event) {
		this.sortSubmit.setOnAction(event);
	}
	
	public void EventHandlerToShowAllProducts(EventHandler<ActionEvent> event) {
		this.showAllProductsBtn.setOnAction(event);
	}
	
	public void EventHandlerToUndoBtn(EventHandler<ActionEvent> event) {
		this.undoBtn.setOnAction(event);
	}
	
	public void EventHandlerToAddProductBtn(EventHandler<ActionEvent> event) {
		this.addProdSubmitBtn.setOnAction(event);
	}
	
	public void EventHandlerToFindProductBtn(EventHandler<ActionEvent> event) {
		this.findCatalogBtn.setOnAction(event);
	}
	
	public void EventHandlerToDeleteProductBtn(EventHandler<ActionEvent> event) {
		this.deleteCatalogBtn.setOnAction(event);
	}
	
	public void EventHandlerToDeleteAllBtn(EventHandler<ActionEvent> event) {
		this.deleteAllBtn.setOnAction(event);
	}
	
	public void EventHandlerToShowProfitBtn(EventHandler<ActionEvent> event) {
		this.showProfit.setOnAction(event);
	}
	
	public void EventHandlerToSendPromotions(EventHandler<ActionEvent> event) {
		this.sendPromotionBtn.setOnAction(event);
	}
	
	public void EventHandlerToShowAcceptedCustomers(EventHandler<ActionEvent> event) {
		this.showAcceptedCustomers.setOnAction(event);
	}
	
	public void EventhandlerExitBtn(EventHandler<ActionEvent> event) {
		this.exitBtn.setOnAction(event);
	}
}
