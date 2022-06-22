package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStoreAndRetrieve {

	//all data stored in this object
	private DataStorage savedData = new DataStorage();

	public DataStoreAndRetrieve() {
		loadData();
	}

	public void loadData(){
        File f = new File("." + File.separator + "posstorage.dat");

        if (f.exists()) {
            try {

                FileInputStream fis = new FileInputStream("." + File.separator + "posstorage.dat");
                ObjectInputStream in = new ObjectInputStream(fis);
                savedData = (DataStorage) in.readObject();

                in.close();
            } catch (Exception e) {
				System.out.println(e);
			}
        }

        if (savedData==null) {
        	savedData = new DataStorage();
        }
    }

	void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream("." + File.separator + "posstorage.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(savedData);
            out.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


	public Product getProductFromStock(int index) {
		return savedData.getProductFromStock(index);
	}

	public String getProductNameFromStock(int index) {
		return savedData.getProductNameFromStock(index);
	}

	public double getProductPrice(String productName) {
		return savedData.getProductPrice(productName);
	}

	public double getProductCost(String productName) {
		return savedData.getProductCost(productName);
	}

	public String getProductDescription(String productDescription) {
		return savedData.getProductDescription(productDescription);
	}

	//used to fill listview
	public ObservableList<String> getStockInfo() {
		ObservableList<String> stockInfo = FXCollections.observableArrayList();

		ArrayList<Product> tmp1 = savedData.getStock();

		for (int i = 0; i < tmp1.size(); i++) {
			//this string will be displayed in the listview
			String tmp2 = tmp1.get(i).getProductName() + " (x" + tmp1.get(i).getProductItemsInStock() + " in stock) --- price = " + tmp1.get(i).getProductPrice();

			stockInfo.add(tmp2);
		}

		return stockInfo;
	}

	public boolean checkIfStockAvailaible(String productName, int requiredAmount) {
		return savedData.checkIfStockAvailaible(productName, requiredAmount);
	}

	public double sellProduct(String productName, int noOfItems) {
		//reduce stock availaible of the product
		savedData.reduceProductItemsAvailaible(productName, noOfItems);

		//add to report
		double profit = (savedData.getProductPrice(productName) * noOfItems) - (savedData.getProductCost(productName) * noOfItems);
		savedData.addToReport(productName, noOfItems, profit);
		return 0.0;
	}

	public void updateStockProduct(String productName, String updatedProductName, String productDescription,int noOfItems, double cost, double price) {
		savedData.updateStockProduct(productName, updatedProductName, productDescription, noOfItems, cost, price);
	}

	public void deleteProductFromStock(String productName) {
		savedData.deleteProductFromStock(productName);
	}

	public void addProductToStock(String productName, String productDescription, int noOfItems, double cost, double price) {
		savedData.addProductToStock(productName, productDescription, noOfItems, cost, price);
	}

	public LinkedHashMap<String, Double> getReportByMonthProfitCurrentYear() {
		return savedData.getReportByMonthProfitCurrentYear();
	}

	public LinkedHashMap<String, Double> getReportByDayProfitCurrentMonth() {
		return savedData.getReportByDayProfitCurrentMonth();
	}

	public LinkedHashMap<String, Double> getReportByYearProfit() {
		return savedData.getReportByYearProfit();
	}

	public ArrayList<Report> getReport() {
		return savedData.getReport();
	}
}
