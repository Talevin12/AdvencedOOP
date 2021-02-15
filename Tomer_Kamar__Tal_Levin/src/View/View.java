package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
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
	private Button showCustomersBtn = new Button("Show all customers");
	private Button showProfit = new Button("Show profit");
	private Button deleteAllBtn = new Button("Delete all products");
	private Button sendPromotionBtn = new Button("Send promotions");
	private Button exitBtn = new Button("Exit");
	
	public View(Stage stage) {
		this.root = new Group();
		stage.setTitle("Product management system");
		
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
		
		VBox naviMenu = new VBox(addProductBtn, undoBtn, showAllProductsBtn, searchProductBtn, showCustomersBtn, showProfit, deleteAllBtn, sendPromotionBtn, exitBtn);
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
}
