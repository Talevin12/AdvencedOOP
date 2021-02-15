package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class View {
	private Group root;
	private Scene sortOptionScene;
	private Scene mainScene;
	public Popup popup = new Popup();

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
	private Button searchProductBtn = new Button("Search product");
	private Button showProfit = new Button("Show profit");
	private Button deleteAllBtn = new Button("Delete all products");
	private Button sendPromotionBtn = new Button("Send promotions");
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
	
	private ListView<String> listViewProducts;
	
	private TextField findCatalogNumTF = new TextField();
	private Button findCatalogBtn = new Button("Search");
	private Text foundProductTxt;
	
//	private TableView<Product> profitTV;
	
	private ListView<String> approvedPromotionListView = new ListView<>();
	
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
		this.sortSubmit.setOnAction(e->{stage.setScene(mainScene);});
		
		VBox vBoxOrderMenu = new VBox();
		vBoxOrderMenu.getChildren().addAll(this.sortOptionTxt, this.ascOrderRB, this.descOrderRB, this.insertOrderRB, this.sortSubmit);
		vBoxOrderMenu.setAlignment(Pos.CENTER);
		vBoxOrderMenu.setSpacing(20);
	
		this.sortOptionScene = new Scene(vBoxOrderMenu, 1000, 600);

		//////////////////////////////////////////////////////////////////
		
		VBox naviMenu = new VBox(addProductBtn, undoBtn, showAllProductsBtn, searchProductBtn, showProfit, deleteAllBtn, sendPromotionBtn, exitBtn);
		addProductBtn.setOnAction(e->{this.mainVB.getChildren().clear(); this.mainVB.getChildren().add(addProductView());});
		searchProductBtn.setOnAction(e->{this.mainVB.getChildren().clear(); this.mainVB.getChildren().add(searchProduct());});
		naviMenu.setSpacing(40);
		naviMenu.setMaxWidth(150);
		naviMenu.setMinWidth(150);
		
		this.mainVB = new VBox();

		this.sp = new SplitPane(naviMenu, this.mainVB);
		this.mainScene = new Scene(sp, 1000, 600);

		//////////////////////////////////////////////////////////////////
		
		stage.setScene(this.sortOptionScene);
		stage.show();
	}
	
	private VBox addProductView() {
		this.catalogNumTF.setPromptText("Product catalog number (*Required field*)");
		this.catalogNumTF.setMaxSize(250, 80);
		this.prodNameTF.setPromptText("Product name");
		this.prodNameTF.setMaxSize(250, 80);
		this.storePriceTF.setPromptText("Store price");
		this.storePriceTF.setMaxSize(250, 80);
		this.clientPriceTF.setPromptText("Client Price");
		this.clientPriceTF.setMaxSize(250, 80);
		this.clientNameTF.setPromptText("Client name");
		this.clientNameTF.setMaxSize(250, 80);
		this.phoneTF.setPromptText("Client phone number");
		this.phoneTF.setMaxSize(250, 80);
		
		ToggleGroup tg = new ToggleGroup();
		this.promotionYesRB.setToggleGroup(tg);
		this.promotionNoRB.setToggleGroup(tg);
		
		HBox promHB = new HBox(promotionTxt, promotionYesRB, promotionNoRB);
		promHB.setAlignment(Pos.CENTER);
		promHB.setSpacing(10);
		
		addProdSubmitBtn.setMinSize(100, 50);
		
		VBox root = new VBox(catalogNumTF, prodNameTF, storePriceTF, clientPriceTF, clientNameTF, phoneTF, promHB, addProdSubmitBtn);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(100));
		root.setSpacing(30);
		return root;
	}
	
	public VBox showAllProducts() {
		VBox root = new VBox(this.listViewProducts);
		return root;
	}
	
	public VBox searchProduct() {
		this.findCatalogNumTF.setPromptText("Product catalog number (*Required field*)");		
		this.findCatalogNumTF.setMaxSize(250, 80);
		
		this.findCatalogBtn.setMinSize(100, 50);
		
		this.foundProductTxt = new Text();
		
		VBox root = new VBox(findCatalogNumTF, findCatalogBtn, foundProductTxt);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(150));
		root.setSpacing(40);
		
		return root;
	}
	
	public void setFoundProductInfo(String product) {
		this.foundProductTxt.setText(product);
	}
	
	public VBox showProfit() {
		return null;
	}
	
	public VBox showApprovedList() {
		VBox root = new VBox(this.approvedPromotionListView);
		return root;
	}
	
	public void setMainVBox(VBox root) {
		this.mainVB.getChildren().clear(); 
		this.mainVB.getChildren().add(root);
	}
	
	public ListView getProductsListView() {
		return this.listViewProducts;
	}
	
	
	public void EventHandlerToShowAllProducts(EventHandler<ActionEvent> event) {
		this.showAllProductsBtn.setOnAction(event);
	}
}
