package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {

	//all data stored here
	private DataStoreAndRetrieve storedData = new DataStoreAndRetrieve();

	/////////////////////////START POS CODE///////////////////////////////////////////////////////////

    @FXML
    private ListView<String> productsInStock;
    private int selectedIndexOfProductsInStock = 0;

    @FXML
    private TextField noOfItemsToSell;

    @FXML
    private ListView<String> productsInCart;

    @FXML
    private Label priceLabel;

    //key=name, value=amount
    private LinkedHashMap<String, Integer> cart = new LinkedHashMap<String, Integer>();

    @FXML
	private void setPOSStockSelectedIndex() {
    	selectedIndexOfProductsInStock = productsInStock.getSelectionModel().getSelectedIndex();
	}

    @FXML
    private void addToCart() {
    	//try {
    		String selectedProductName = storedData.getProductFromStock(selectedIndexOfProductsInStock).getProductName();

        	int noOfItems = Integer.parseInt(noOfItemsToSell.getText());

        	if (storedData.checkIfStockAvailaible(selectedProductName, noOfItems)) {
        		//boolean checkStock = storedData.checkIfStockAvailaible(selectedProductName, (cart.get(selectedProductName)+noOfItems));
        		if (cart.containsKey(selectedProductName))
        		{
        			if (!storedData.checkIfStockAvailaible(selectedProductName, (cart.get(selectedProductName)+noOfItems))) {
        				System.out.println("Error: Don't have that many in stock");
        				return;
        			}
        			double removed = cart.get(selectedProductName) * storedData.getProductPrice(selectedProductName);

        			cart.put(selectedProductName, cart.get(selectedProductName) + noOfItems);
        			double price = cart.get(selectedProductName) * storedData.getProductPrice(selectedProductName);
        			//productsInCart.getItems().remove(selectedIndexOfProductsInStock);
        			//productsInCart.getItems().add(selectedProductName + " --- x" + cart.get(selectedProductName) + " = " + price);
        			refreshCart();

        			//update priceLabel
            		double priceToAdd = Double.parseDouble(priceLabel.getText()) - removed + price;
            		priceLabel.setText(String.valueOf(priceToAdd));

        		} else {
        			cart.put(selectedProductName, noOfItems);

            		double price = storedData.getProductPrice(selectedProductName) * noOfItems;
            		productsInCart.getItems().add(selectedProductName + " --- x" + noOfItems + " = " + price);

            		//update priceLabel
            		double priceToAdd = Double.parseDouble(priceLabel.getText()) + price;
            		priceLabel.setText(String.valueOf(priceToAdd));
        		}

    		} else {
    			getThingsReady(); //to refresh the listview to get updated stock
    			System.out.println("Product out of stock");
        	}
		//} catch (Exception e) {
		//	System.out.println("Add to cart error>> " + e);
		//}
    }

    @FXML
    private void removeFromCart() {
    	int selectedIndex = productsInCart.getSelectionModel().getSelectedIndex();
    	productsInCart.getItems().remove(selectedIndex);

    	int i = 0;

        for (String key : cart.keySet()) {
            if (i == selectedIndex) {
                System.out.println("Removed " + key + " from cart.");
                cart.remove(key);
                break;
            }
            i++;
        }
    }

    @FXML
    private void checkout() {
    	double totalPrice = 0;

		//Check if products exist in stock
		//which ever is not in stock remove that from cart
    	int i = 0;
    	double priceOfProductBringChecked = 0;
    	int noOfItemsOfProductBringChecked = 0;
    	for (String key : cart.keySet()) {
    		if (!storedData.checkIfStockAvailaible(key, cart.get(key))) {
    			cart.remove(key);
    			productsInCart.getItems().remove(i);

        		//update priceLabel
        		double priceToReduce = Double.parseDouble(priceLabel.getText()) - priceOfProductBringChecked*noOfItemsOfProductBringChecked;
        		priceLabel.setText(String.valueOf(priceToReduce));
    		}

    		i++;
    	}

    	//calculate total price of products in cart
    	for (String key : cart.keySet()) {
    		double tmp = cart.get(key) * storedData.getProductPrice(key);
    		totalPrice += tmp;
    	}

		priceLabel.setText(Double.toString(totalPrice));

		//make changes to stock
		for (String key : cart.keySet()) {
			storedData.sellProduct(key, cart.get(key));
		}

		//Show final result separately
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Checkout");
		alert.setHeaderText("Checkout");
		//String s = productsInCart.getItems().toString() + "\n\nTotal = " + priceLabel.getText();
		String s = "\n";
		for (int x = 0; x < productsInCart.getItems().size(); x++) {
			s = s.concat(productsInCart.getItems().get(x));
			s = s.concat("\n");
		}
		s = s.concat("\n\nTotal = " + priceLabel.getText());
		alert.setContentText(s);
		alert.show();

		//refresh stock in gui
		productsInCart.getItems().clear();
		cart.clear();
		priceLabel.setText("0");
		getThingsReady();
    }

    private void refreshCart() {
    	//this function will sync cart linkedhashmap with the listview that shows what's in the cart in gui
    	productsInCart.getItems().clear();

    	for (String key : cart.keySet()) {
    		String p = key + " --- x" + cart.get(key) + " = " + storedData.getProductPrice(key);

    		productsInCart.getItems().add(p);
    	}
    }

	/////////////////////////END POS CODE///////////////////////////////////////////////////////////

	/////////////////////////START STOCK CODE///////////////////////////////////////////////////////////


    @FXML
    private ListView<String> productsInStock1;

    private int selectedIndexOfProductsInStock1 = 0;

    @FXML
    private TextField productName;

    @FXML
    private TextArea productDescription;

    @FXML
    private TextField noOfItemsInStock;

    @FXML
    private TextField productCost;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField newProductName;

    @FXML
    private TextArea newProductDescription;

    @FXML
    private TextField noOfNewItemsToAddToStock;

    @FXML
    private TextField newProductCost;

    @FXML
    private TextField newProductPrice;

    @FXML
    private void populateDataForProduct() {
    	selectedIndexOfProductsInStock1 = productsInStock1.getSelectionModel().getSelectedIndex();

    	Product selectedProduct = storedData.getProductFromStock(selectedIndexOfProductsInStock1);

    	productName.setText(selectedProduct.getProductName());
    	productDescription.setText(selectedProduct.getProductDescription());
    	productCost.setText(String.valueOf(selectedProduct.getProductCost()));
    	productPrice.setText(String.valueOf(selectedProduct.getProductPrice()));
    	noOfItemsInStock.setText(String.valueOf(selectedProduct.getProductItemsInStock()));
    }

    @FXML
    private void updateChangesToStorage() {
    	storedData.updateStockProduct(storedData.getProductNameFromStock(selectedIndexOfProductsInStock1), productName.getText(), productDescription.getText(), Integer.parseInt(noOfItemsInStock.getText()), Double.parseDouble(productCost.getText()), Double.parseDouble(productPrice.getText()));
    	getThingsReady();
	}

    @FXML
    private void deleteProduct() {
    	storedData.deleteProductFromStock(storedData.getProductNameFromStock(selectedIndexOfProductsInStock1));
    	productsInStock1.getSelectionModel().selectFirst();
    	populateDataForProduct();
    	getThingsReady();
    }

    @FXML
    private void addToStock() {
    	storedData.addProductToStock(newProductName.getText(), newProductDescription.getText(), Integer.parseInt(noOfNewItemsToAddToStock.getText()), Double.parseDouble(newProductCost.getText()), Double.parseDouble(newProductPrice.getText()));
    	getThingsReady();
    }

	/////////////////////////END STOCK CODE///////////////////////////////////////////////////////////

	/////////////////////////START SALES REPORT CODE///////////////////////////////////////////////////////////

    @FXML
    BarChart yearEarningBarChart;

    @FXML
    BarChart monthEarningBarChart;

    @FXML
    BarChart prevYearsEarningBarChart;

    @FXML
    TableView<Report> allReports;
    @FXML
    TableColumn allReportsNameCol;
    @FXML
    TableColumn allReportsItemSoldCol;
    @FXML
    TableColumn allReportsProfitCol;
    @FXML
    TableColumn allReportsDateCol;

    private void getCurrentYearProfitChartReady() {
		yearEarningBarChart.getData().clear();

        XYChart.Series dataSeries = new XYChart.Series<String, Double>();
        dataSeries.setName("One month's profit");

		//dataSeries.getData().add(new XYChart.Data("Desktop", 178));

        LinkedHashMap<String, Double> temp = storedData.getReportByMonthProfitCurrentYear();

        for (String key: temp.keySet()) {
        	dataSeries.getData().add(new XYChart.Data(key, temp.get(key)));
        }


		yearEarningBarChart.getData().add(dataSeries);
    }

    private void getCurrentMonthProfitChartReady() {
		monthEarningBarChart.getData().clear();

        XYChart.Series dataSeries = new XYChart.Series<String, Double>();
        dataSeries.setName("One day's profit");

        LinkedHashMap<String, Double> temp = storedData.getReportByDayProfitCurrentMonth();

        for (String key: temp.keySet()) {
        	dataSeries.getData().add(new XYChart.Data(key, temp.get(key)));
        }


        monthEarningBarChart.getData().add(dataSeries);
    }

    private void getYearProfitChartReady() {
    	prevYearsEarningBarChart.getData().clear();

        XYChart.Series dataSeries = new XYChart.Series<String, Double>();
        dataSeries.setName("One years's profit");

        LinkedHashMap<String, Double> temp = storedData.getReportByYearProfit();

        for (String key: temp.keySet()) {
        	dataSeries.getData().add(new XYChart.Data(key, temp.get(key)));
        }


        prevYearsEarningBarChart.getData().add(dataSeries);
    }

    private void getReportsReady() {
    	allReports.getItems().clear();

    	ArrayList<Report> temp = storedData.getReport();

    	allReportsNameCol.setCellValueFactory(new PropertyValueFactory("productName"));
    	allReportsItemSoldCol.setCellValueFactory(new PropertyValueFactory("itemsSold"));
    	allReportsProfitCol.setCellValueFactory(new PropertyValueFactory("profit"));
    	allReportsDateCol.setCellValueFactory(new PropertyValueFactory("reportDate"));

    	for (int i = 0; i < temp.size(); i++) {
    		allReports.getItems().add(temp.get(i));
    	}
    }

	/////////////////////////END SALES REPORT CODE///////////////////////////////////////////////////////////

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Initialize values of all listviews, charts and tableview when program starts
		getThingsReady();
	}

	private void getThingsReady() {
		productsInStock.setItems(storedData.getStockInfo());
		productsInStock1.setItems(storedData.getStockInfo());

		getCurrentYearProfitChartReady();
		getCurrentMonthProfitChartReady();
		getYearProfitChartReady();

		getReportsReady();

		storedData.saveData();
	}
}
