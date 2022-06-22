package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

public class DataStorage implements Serializable{

	//all product stock here
	private ArrayList<Product> stock = new ArrayList<Product>();
	//all sales report here
	private ArrayList<Report> report = new ArrayList<Report>();

	////////////////////// ALL STOCK RELATED FUNCTION BELOW////////////////////////

	public DataStorage() {
		//hardcoding some data for testing only
		/*
		 * stock.add(new Product("apple", "apple is apple", 20, 10, 50)); stock.add(new
		 * Product("banana", "banananananana is all rounder healthy food", 20, 10, 20));
		 * stock.add(new Product("orange", "orange good vitamin c", 20, 10, 10));
		 *
		 * report.add(new Report("car", 3, 10000)); report.add(new Report("mobile", 2,
		 * 4000)); report.add(new Report("apple", 1, 10));
		 */
	}

	public ArrayList<Product> getStock() {
		return stock;
	}

	public Product getProductFromStock(int index) {
		return stock.get(index);
	}

	public String getProductNameFromStock(int index) {
		return stock.get(index).getProductName();
	}

	public String getProductDescription(String productName) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(productName) == 0) {
				return stock.get(i).getProductDescription();
			}
		}
		return "";
	}

	public double getProductPrice(String productName) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(productName) == 0) {
				return stock.get(i).getProductPrice();
			}
		}
		return -1.0;
	}

	public double getProductCost(String productName) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(productName) == 0) {
				return stock.get(i).getProductCost();
			}
		}
		return -1.0;
	}

	public int getTotalItemsOfProduct(String productName) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(productName) == 0) {
				return stock.get(i).getProductItemsInStock();
			}
		}
		return -1;
	}

	public boolean checkIfStockAvailaible(String productName, int requiredAmount) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(productName) == 0 && stock.get(i).getProductItemsInStock() >= requiredAmount)
			{
				return true;
			}
		}

		return false;
	}

	public void addProductToStock(String productName, String productDescription, int noOfItems, double cost, double price) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(productName) == 0)
			{
				System.out.println("Error: Product already exists");
				return;
			}
		}

		Product newProduct = new Product(productName, productDescription, price, cost, noOfItems);
		stock.add(newProduct);
	}

	public void reduceProductItemsAvailaible(String productName, int reduceByNum) {
		for (int i = 0; i < stock.size(); i++) {
			//check name and check if we can reduce, so number of items don't go to negative
			if (stock.get(i).getProductName().compareTo(productName) == 0 && stock.get(i).getProductItemsInStock() >= reduceByNum)
			{
				stock.get(i).setProductItemsInStock(stock.get(i).getProductItemsInStock() - reduceByNum);

				//if product have no stock after sell, then delete it automatically
				if (stock.get(i).getProductItemsInStock() <= 0) {
					stock.remove(i);
				}
				return;
			}
		}
	}

	public void updateStockProduct(String oldProductName, String updatedProductName, String productDescription,int noOfItems, double cost, double price) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(oldProductName) == 0)
			{
				stock.get(i).setProductName(updatedProductName);
				stock.get(i).setProductDescription(productDescription);
				stock.get(i).setProductItemsInStock(noOfItems);
				stock.get(i).setProductCost(cost);
				stock.get(i).setProductPrice(price);

				return;
			}
		}
	}

	public void deleteProductFromStock(String productName) {
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getProductName().compareTo(productName) == 0)
			{
				stock.remove(i);
				return;
			}
		}
	}

	/////////////////////ALL REPORT RELATED FUNCTIONS BELOW/////////////////////////////

	public void addToReport(String productName, int itemsSold, double profit) {

		GregorianCalendar gcalendar = new GregorianCalendar();
		int currentDay = gcalendar.get(Calendar.DATE);
		int currentMonth = gcalendar.get(Calendar.MONTH) + 1;
		int currentYear = gcalendar.get(Calendar.YEAR);

		for (int i = 0; i < report.size(); i++) {
			if (report.get(i).getReportMonth() == currentMonth && report.get(i).getReportYear() == currentYear && report.get(i).getProductName().compareTo(productName) == 0) {
				report.get(i).addItemsSold(itemsSold);
				report.get(i).addProfit(profit);
				return;
			}
		}

		Report newReport = new Report(productName, itemsSold, profit);
		report.add(newReport);

		//System.out.println(report.toString());
	}

	public LinkedHashMap<String, Double> getReportByMonthProfitCurrentYear() {
		LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();

		GregorianCalendar gcalendar = new GregorianCalendar();
		int currentYear = gcalendar.get(Calendar.YEAR);

		for (int i = 0; i < report.size(); i++) {
			if (report.get(i).getReportYear() == currentYear) {

				double pr = 0;
				String s = report.get(i).getReportMonth() + "-" + report.get(i).getReportYear(); //key string format

				if (result.containsKey(s)) {
					pr += result.get(s) + report.get(i).getProfit();
				} else {
					pr += report.get(i).getProfit();
				}

				result.put(s, pr);
			}
		}

		return result;
	}

	public LinkedHashMap<String, Double> getReportByDayProfitCurrentMonth() {
		LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();

		GregorianCalendar gcalendar = new GregorianCalendar();
		int currentYear = gcalendar.get(Calendar.YEAR);
		int currentMonth = gcalendar.get(Calendar.MONTH) + 1;
		int currentDay = gcalendar.get(Calendar.DATE);

		for (int i = 0; i < report.size(); i++) {
			if (report.get(i).getReportYear() == currentYear && report.get(i).getReportMonth() == currentMonth) {

				double pr = 0;
				String s = report.get(i).getReportDate();  //key string format

				if (result.containsKey(s)) {
					pr += result.get(s) + report.get(i).getProfit();
				} else {
					pr += report.get(i).getProfit();
				}

				result.put(s, pr);
			}
		}

		return result;
	}

	public LinkedHashMap<String, Double> getReportByYearProfit() {
		LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();

		GregorianCalendar gcalendar = new GregorianCalendar();
		int currentYear = gcalendar.get(Calendar.YEAR);

		for (int i = 0; i < report.size(); i++) {
			if (report.get(i).getReportYear() == currentYear) {

				double pr = 0;
				String s = String.valueOf(report.get(i).getReportYear());  //key string format

				if (result.containsKey(s)) {
					pr += result.get(s) + report.get(i).getProfit();
				} else {
					pr += report.get(i).getProfit();
				}

				result.put(s, pr);
			}
		}
		return result;
	}

	public ArrayList<Report> getReport() {
		return report;
	}
}
