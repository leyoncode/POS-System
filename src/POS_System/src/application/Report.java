package application;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Report implements Serializable{

	//this class should be used to store reports of sales of one product in whole day

	private String productName;
	private int itemsSold;
	private double profit;
	private String reportDate; //date format = dd-mm-yyyy


	public Report(String productName, int itemsSold, double profit) {
		this.productName = productName;
		this.itemsSold = itemsSold;
		this.profit = profit;

		GregorianCalendar gcalendar = new GregorianCalendar();
		reportDate = String.valueOf(gcalendar.get(Calendar.DATE)) + "-" +
	            	String.valueOf(gcalendar.get(Calendar.MONTH)+1) + "-" +
	            	String.valueOf(gcalendar.get(Calendar.YEAR)
	            );
	}

	@Override
	public String toString() {
		return productName + ">> sold=" + itemsSold + " - profit=" + profit + " - date=" + reportDate;
	}
	public String getReportDate() {
		return reportDate;
	}

	public int getReportDay() {
		return Integer.parseInt(reportDate.split("-")[0]);
	}

	public int getReportMonth() {
		return Integer.parseInt(reportDate.split("-")[1]);
	}

	public int getReportYear() {
		return Integer.parseInt(reportDate.split("-")[2]);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductNameString(String productName) {
		this.productName = productName;
	}

	public int getItemsSold() {
		return itemsSold;
	}

	public void addItemsSold(int itemsSold) {
		this.itemsSold += itemsSold;
	}

	public double getProfit() {
		return profit;
	}

	public void addProfit(double profit) {
		this.profit += profit;
	}

}
