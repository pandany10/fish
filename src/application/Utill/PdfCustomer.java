package application.Utill;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import application.Model.OrderModel;
import application.Model.SalesModel;

public class PdfCustomer {
	private String font = FontFactory.COURIER;
	private List<OrderModel> lstOrder = new ArrayList<>();
	private String todayDate ="";
	String title = "EXOTIC REEF IMPORTS, INC.  AGING REPORT";
	boolean isLedger = false;
	public class HeaderFooterPageEvent extends PdfPageEventHelper {
		PdfTemplate total;
	   public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }
	    public void onStartPage(PdfWriter writer,Document document) {
	    	try {
				getHeader(writer,document);
				//getHeaderCus(document);
				//getHeader1(document);
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    public void onEndPage(PdfWriter writer,Document document) {
	    	try {
				getFooter(writer,document);
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	} 
	public void Print(List<OrderModel> lstOrder,String todayDate)
			throws MalformedURLException, IOException {
		this.lstOrder = lstOrder;
		this.todayDate = todayDate;
		String replacedString = todayDate.replace("/", "");
		replacedString = replacedString.replace("/", "");
		String file ="Aging-Report-"+replacedString+".pdf";
		File files = new File(file);
		deleteFile(file);
		/*	ver++;
			file ="pdf/sales/Invoice-"+type+"-"+fromDate+toDate+"-"+ver+".pdf";
		}*/
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			//getHeader(document);
			//getHeader1(document);
			getBody(document);
			document.close();
			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(file);
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void PrintE(List<OrderModel> lstOrder,String todayDate)
			throws MalformedURLException, IOException {
		this.lstOrder = lstOrder;
		this.todayDate = todayDate;
		String replacedString = todayDate.replace("/", "");
		replacedString = replacedString.replace("/", "");
		String file ="Aging-Report-"+replacedString+".pdf";
		File files = new File(file);
		deleteFile(file);
		/*	ver++;
			file ="pdf/sales/Invoice-"+type+"-"+fromDate+toDate+"-"+ver+".pdf";
		}*/
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			//getHeader(document);
			//getHeader1(document);
			getBody(document);
			document.close();
			/*if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(file);
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}*/

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Prints(List<OrderModel> lstOrder,String todayDate)
			throws MalformedURLException, IOException {
		title = "EXOTIC REEF IMPORTS, INC. ACCOUNT STATEMENT";
		this.lstOrder = lstOrder;
		this.todayDate = todayDate;
		String replacedString = todayDate.replace("/", "");
		replacedString = replacedString.replace("/", "");
		String cusid = "";
		if(lstOrder.size()>0){
			cusid = lstOrder.get(0).getCustomerId();
		}
		String file ="Account Statement #"+cusid+".pdf";
		File files = new File(file);
		deleteFile(file);
		/*	ver++;
			file ="pdf/sales/Invoice-"+type+"-"+fromDate+toDate+"-"+ver+".pdf";
		}*/
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			//getHeader(document);
			//getHeader1(document);
			getBody(document);
			document.close();
			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(file);
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void PrintsE(List<OrderModel> lstOrder,String todayDate)
			throws MalformedURLException, IOException {
		title = "EXOTIC REEF IMPORTS, INC. ACCOUNT STATEMENT";
		this.lstOrder = lstOrder;
		this.todayDate = todayDate;
		String replacedString = todayDate.replace("/", "");
		replacedString = replacedString.replace("/", "");
		String cusid = "";
		if(lstOrder.size()>0){
			cusid = lstOrder.get(0).getCustomerId();
		}
		String file ="Account Statement #"+cusid+".pdf";
		File files = new File(file);
		deleteFile(file);
		/*	ver++;
			file ="pdf/sales/Invoice-"+type+"-"+fromDate+toDate+"-"+ver+".pdf";
		}*/
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			//getHeader(document);
			//getHeader1(document);
			getBody(document);
			document.close();
	/*		if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(file);
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}*/

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Prints1(List<OrderModel> lstOrder,String todayDate)
			throws MalformedURLException, IOException {
		isLedger = true;
		title = "EXOTIC REEF IMPORTS, INC. CUSTOMER LEDGER REPORT";
		this.lstOrder = lstOrder;
		this.todayDate = todayDate;
		String replacedString = todayDate.replace("/", "");
		replacedString = replacedString.replace("/", "");
		String cusid = "";
		if(lstOrder.size()>0){
			cusid = lstOrder.get(0).getCustomerId();
		}
		String file ="Customer Ledger #"+cusid+".pdf";	
		File files = new File(file);
		deleteFile(file);
		/*	ver++;
			file ="pdf/sales/Invoice-"+type+"-"+fromDate+toDate+"-"+ver+".pdf";
		}*/
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			//getHeader(document);
			//getHeader1(document);
			getBody(document);
			document.close();
			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(file);
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Prints1E(List<OrderModel> lstOrder,String todayDate)
			throws MalformedURLException, IOException {
		title = "EXOTIC REEF IMPORTS, INC. CUSTOMER LEDGER REPORT";
		this.lstOrder = lstOrder;
		this.todayDate = todayDate;
		String replacedString = todayDate.replace("/", "");
		replacedString = replacedString.replace("/", "");
		String cusid = "";
		if(lstOrder.size()>0){
			cusid = lstOrder.get(0).getCustomerId();
		}
		String file ="Customer Ledger #"+cusid+".pdf";	
		File files = new File(file);
		deleteFile(file);
		/*	ver++;
			file ="pdf/sales/Invoice-"+type+"-"+fromDate+toDate+"-"+ver+".pdf";
		}*/
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			//getHeader(document);
			//getHeader1(document);
			getBody(document);
			document.close();
/*			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(file);
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}*/

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean deleteFile(String file){
		File files = new File(file);
		System.out.println(files.getName() + " is deleted!");
		return files.delete();
	}
	public Paragraph getText(String content, String font, Integer size, Integer style) {
		Paragraph paragraph = new Paragraph(content,
				FontFactory.getFont(font, size, style, new CMYKColor(0, 0, 0, 255)));
		return paragraph;
	}

	public Paragraph getTextC(String content, String font, Integer size, Integer style) {
		Paragraph paragraph = new Paragraph(content,
				FontFactory.getFont(font, size, style, new CMYKColor(0, 0, 0, 255)));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		return paragraph;
	}

	public Paragraph getTextR(String content, String font, Integer size, Integer style) {
		Paragraph paragraph = new Paragraph(content,
				FontFactory.getFont(font, size, style, new CMYKColor(0, 0, 0, 255)));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		return paragraph;
	}
	public Paragraph getTextRR(String content, String font, Integer size, Integer style) {
		Paragraph paragraph = new Paragraph(content,
				FontFactory.getFont(font, size, style, BaseColor.RED));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		return paragraph;
	}
	public Paragraph getRText(String content, String font, Integer size, Integer style) {
		Paragraph paragraph = new Paragraph(content,
				FontFactory.getFont(font, size, style, BaseColor.RED));
		//paragraph.setAlignment(Element.ALIGN_RIGHT);
		return paragraph;
	}
	public Paragraph getRCText(String content, String font, Integer size, Integer style) {
		Paragraph paragraph = new Paragraph(content,
				FontFactory.getFont(font, size, style, BaseColor.RED));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		return paragraph;
	}
	public void getBody(Document document) throws MalformedURLException, IOException, DocumentException {
		//if(type.equals("sumary")){
			String customerId = "";
			String customerIds = "";
			int count = 0;
			int counts = 0;
			Float total = 0.f;
			Float totalP = 0.f;
			Float totalUP = 0.f;
			Float totalBF = 0.f;
			Float totalB30 = 0.f;
			Float totalB60 = 0.f;
			Float totalB90 = 0.f;
			Float totalB120 = 0.f;
			int col = 9;
			if(isLedger == true){
				col = 7;
			}
			PdfPTable tblProducto = new PdfPTable(col);
			if(isLedger == false){
				tblProducto.setWidthPercentage(100);
				tblProducto.setWidths(new float[] { 10,10,10,10,10,10,10,15,15 });
			
			for (OrderModel order : lstOrder) {
				count++;
			}
			for (OrderModel order : lstOrder) {
				counts++;
				if(counts == 1){
					customerIds = order.getCustomerId();
				}else{
					if(((!customerIds.equals(order.getCustomerId()))  || (count == counts))){
						customerIds = order.getCustomerId();
						PdfPCell date = getRCell("TOTAL:", font,7, 0);
						date.setBorderWidthLeft(1);
						date.setBorderColorLeft(BaseColor.DARK_GRAY);
						date.setBorderColorBottom(BaseColor.DARK_GRAY);
						date.setBorderWidthBottom(1);
						tblProducto.addCell(date);
						
						PdfPCell day = getCellCN("", font, 7, 0);
						day.setBorderWidthBottom(1);
						day.setBorderColorBottom(BaseColor.DARK_GRAY);
						tblProducto.addCell(day);			
						
					/*	PdfPCell tp = getCellRR("$"+String.format ("%,.2f",totalP), font, 10, 0);
						tp.setBorderWidthBottom(1);
						tp.setBorderColorBottom(BaseColor.DARK_GRAY);
						tblProduct.addCell(tp);*/
						
						//PdfPCell ts = getCellRR("$"+String.format ("%,.2f",total), font, 7, 0);
						PdfPCell ts = getCellRR("", font, 7, 0);

						ts.setBorderWidthBottom(1);
						ts.setBorderColorBottom(BaseColor.DARK_GRAY);
						tblProducto.addCell(ts);
						
						PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalB30), font, 7, 0);
//						PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalP), font, 10, 0);

						totalc.setBorderWidthBottom(1);
						tblProducto.addCell(totalc);
						
						PdfPCell totalc1 = getCellRR("$"+String.format ("%,.2f",totalB60), font, 7, 0);
//						PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalP), font, 10, 0);
						totalc1.setBorderWidthBottom(1);
						tblProducto.addCell(totalc1);
						PdfPCell totalc2 = getCellRR("$"+String.format ("%,.2f",totalB90), font, 7, 0);
//						PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalP), font, 10, 0);
						totalc2.setBorderWidthBottom(1);
						tblProducto.addCell(totalc2);
						PdfPCell totalc3 = getCellRR("$"+String.format ("%,.2f",totalB120), font, 7, 0);
//						PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalP), font, 10, 0);
						totalc3.setBorderWidthBottom(1);
						tblProducto.addCell(totalc3);
						PdfPCell totalc4 = getCellRR("$"+String.format ("%,.2f",totalUP), font, 7, 0);
//						PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalP), font, 10, 0);
						totalc4.setBorderWidthBottom(1);
						tblProducto.addCell(totalc4);
						
						
						PdfPCell totaln = getCellRR("<================", font, 7, 0);
//						PdfPCell totaln = getCellRR("$"+String.format ("%,.2f",totalUP), font, 10, 0);

						totaln.setBorderWidthBottom(1);
						totaln.setBorderColorBottom(BaseColor.DARK_GRAY);
						totaln.setBorderWidthRight(1);
						totaln.setBorderColorRight(BaseColor.DARK_GRAY);
						tblProducto.addCell(totaln);
						
						if(title.equals("EXOTIC REEF IMPORTS, INC. ACCOUNT STATEMENT")){
							Float totalOB =0.00f;
							totalOB = totalB30 + totalB60 +totalB90 + totalB120;
							PdfPCell bottom0 = getCellCN("", font, 7, 0);
							bottom0.setColspan(9);
							tblProducto.addCell(bottom0);
							PdfPCell bottomDefault = getCellCN("", font, 7, 0);
							bottomDefault.setColspan(6);
							PdfPCell bottomDefaultBlank = getCellCN("", font, 7, 0); 
							bottomDefaultBlank.setColspan(1);
							PdfPCell bottom1 = getCellCN("*****  OVERALL RECEIVABLES TOTAL  *****", font, 7, 0);
							bottom1.setColspan(3);
							tblProducto.addCell(bottom1);
							tblProducto.addCell(bottomDefault);
							
							PdfPCell bottom21 = getCell("CURRENT", font, 7, 0);
							PdfPCell bottom22 = getCellR("$"+String.format ("%,.2f",totalOB), font, 7, 0);
							tblProducto.addCell(bottom21);
							tblProducto.addCell(bottomDefaultBlank);
							tblProducto.addCell(bottom22);
							tblProducto.addCell(bottomDefault);
							
							PdfPCell bottom31 = getCell("30 DAYS", font, 7, 0);
							PdfPCell bottom32 = getCellR("$"+String.format ("%,.2f",totalB30), font, 7, 0);
							tblProducto.addCell(bottom31);
							tblProducto.addCell(bottomDefaultBlank);
							tblProducto.addCell(bottom32);
							tblProducto.addCell(bottomDefault);
							
							PdfPCell bottom41 = getCell("60 DAYS", font, 7, 0);
							PdfPCell bottom42 = getCellR("$"+String.format ("%,.2f",totalB60), font, 7, 0);
							tblProducto.addCell(bottom41);
							tblProducto.addCell(bottomDefaultBlank);
							tblProducto.addCell(bottom42);
							tblProducto.addCell(bottomDefault);

							PdfPCell bottom51 = getCell("90 DAYS", font, 7, 0);
							PdfPCell bottom52 = getCellR("$"+String.format ("%,.2f",totalB90), font, 7, 0);
							tblProducto.addCell(bottom51);
							tblProducto.addCell(bottomDefaultBlank);
							tblProducto.addCell(bottom52);
							tblProducto.addCell(bottomDefault);
							
							PdfPCell bottom61 = getCell("120 DAYS", font, 7, 0);
							PdfPCell bottom62 = getCellR("$"+String.format ("%,.2f",totalB120), font, 7, 0);
							tblProducto.addCell(bottom61);
							tblProducto.addCell(bottomDefaultBlank);
							tblProducto.addCell(bottom62);
							tblProducto.addCell(bottomDefault);
							
							PdfPCell bottom7 = getCellCN("****************************************", font, 7, 0);
							bottom7.setColspan(3);
							tblProducto.addCell(bottom7);
							tblProducto.addCell(bottomDefault);
							
							PdfPCell bottom81 = getCell("OVERALL BALANCE", font, 7, 0);
							bottom81.setColspan(2);
							PdfPCell bottom82 = getCellR("$"+String.format ("%,.2f",totalOB), font, 7, 0);
							tblProducto.addCell(bottom81);
							tblProducto.addCell(bottom82);
							tblProducto.addCell(bottomDefault);
						}
						document.add(tblProducto);
						
						total = 0.f;
						totalP = 0.f;
						totalUP = 0.f;
						totalB30 = 0.f;
						totalB60 = 0.f;
						totalB90 = 0.f;
						totalB120 = 0.f;
						tblProducto = new PdfPTable(9);
						tblProducto.setWidthPercentage(100);
						tblProducto.setWidths(new float[] { 10,10,10,10,10,10,10,15,15 });
					}
				}
				if(customerId.equals("") || (!customerId.equals(order.getCustomerId())) ){

					customerId = order.getCustomerId();
					getHeaderCus(document);
					PdfPTable tblProduct = new PdfPTable(6);
					tblProduct.setWidthPercentage(100);
					tblProduct.setWidths(new float[] { 7,30,15,7,15,26 });
					
					PdfPCell cusid = getCellCN(order.getCustomerId(), font, 7, 0);
					cusid.setBorderWidthLeft(1);
					cusid.setBorderWidthBottom(1);
					cusid.setBorderColorLeft(BaseColor.DARK_GRAY);
					tblProduct.addCell(cusid);
					
					PdfPCell cusName = getCellCN(order.getCustomerName(), font, 7, 0);
					cusName.setBorderWidthBottom(1);
					cusName.setBorderColorLeft(BaseColor.DARK_GRAY);
					tblProduct.addCell(cusName);	
					
					
					PdfPCell cusSale = getCellCN(order.getCustomerTerms(), font, 7, 0);
					cusSale.setBorderWidthBottom(1);
					cusSale.setBorderColorLeft(BaseColor.DARK_GRAY);
					tblProduct.addCell(cusSale);	
					
					PdfPCell totalc = getCellCN(order.getCustomerSalesperson(), font, 7, 0);
					totalc.setBorderWidthBottom(1);
					totalc.setBorderColorLeft(BaseColor.DARK_GRAY);
					tblProduct.addCell(totalc);
					
					PdfPCell totald = getCellCN(order.getCustomerPhone(), font, 7, 0);
					totald.setBorderWidthBottom(1);
					totald.setBorderColorLeft(BaseColor.DARK_GRAY);
					tblProduct.addCell(totald);					
					
					PdfPCell totaln = getCellCN(order.getCustomerContact(), font, 7, 0);
					totaln.setBorderWidthRight(1);
					totaln.setBorderWidthBottom(1);
					totaln.setBorderColorRight(BaseColor.DARK_GRAY);
					tblProduct.addCell(totaln);
					
					document.add(tblProduct);
					getHeader1(document);

				}
				
				
				PdfPCell cusid = getCellCN(order.getOrder_id()+"", font,7, 0);
				PdfPCell cusName = getCellCN(order.getCustomer_date(), font,7, 0);

				cusName.setBorderWidthLeft(1);
				cusName.setBorderColorLeft(BaseColor.DARK_GRAY);
				tblProducto.addCell(cusName);
				
				tblProducto.addCell(cusid);					
				
				PdfPCell cusSale = getCellR("$"+order.getAll_Total(), font, 7, 0);
				tblProducto.addCell(cusSale);	
				if(!order.getBlance30().equals("")){
					PdfPCell totalc = getCellR("$"+order.getBlance30(), font, 7, 0);
					tblProducto.addCell(totalc);
				}else{
					PdfPCell totalc = getCellR(order.getBlance30(), font, 7, 0);
					tblProducto.addCell(totalc);
				}

				if(!order.getBlance60().equals("")){
					PdfPCell totalc1 = getCellR("$"+order.getBlance60(), font, 7, 0);
					tblProducto.addCell(totalc1);
				}else{
					PdfPCell totalc1 = getCellR(order.getBlance60(), font, 7, 0);
					tblProducto.addCell(totalc1);
				}
				
				if(!order.getBlance90().equals("")){
					PdfPCell totalc2 = getCellR("$"+order.getBlance90(), font, 7, 0);
					tblProducto.addCell(totalc2);
				}else{
					PdfPCell totalc2 = getCellR(order.getBlance90(), font, 7, 0);
					tblProducto.addCell(totalc2);
				}
				
				if(!order.getBlance120().equals("")){
					PdfPCell totalc3 = getCellR("$"+order.getBlance120(), font, 7, 0);
					tblProducto.addCell(totalc3);
				}else{
					PdfPCell totalc3 = getCellR(order.getBlance120(), font, 7, 0);
					tblProducto.addCell(totalc3);
				}

				PdfPCell totalc4 = getCellR("$"+order.getAmoutUnPaid(), font, 7, 0);
				tblProducto.addCell(totalc4);
				PdfPCell totaln = getCellR("", font, 7, 0);
				totaln.setBorderWidthRight(1);
				totaln.setBorderColorRight(BaseColor.DARK_GRAY);
				tblProducto.addCell(totaln);
				if(!order.getBlance30().equals("")){
					totalB30 = totalB30 + Float.parseFloat(order.getBlance30().replace(",", ""));
				}
				if(!order.getBlance60().equals("")){
					totalB60 = totalB60 + Float.parseFloat(order.getBlance60().replace(",", ""));
				}
				if(!order.getBlance90().equals("")){
					totalB90 = totalB90 + Float.parseFloat(order.getBlance90().replace(",", ""));
				}
				if(!order.getBlance120().equals("")){
					totalB120 = totalB120 + Float.parseFloat(order.getBlance120().replace(",", ""));
				}
				total = total + Float.parseFloat(order.getAll_Total().replace(",", ""));
				totalP = totalP + Float.parseFloat(order.getAmoutPaid().replace(",", ""));
				totalUP = totalUP + Float.parseFloat(order.getAmoutUnPaid().replace(",", ""));
				

			}

			}else{
				tblProducto.setWidthPercentage(100);
				tblProducto.setWidths(new float[] { 13,13,17,13,13,13,18 });
			
				for (OrderModel order : lstOrder) {
					count++;
				}
				for (OrderModel order : lstOrder) {
					counts++;
					if(counts == 1){
						customerIds = order.getCustomerId();
					}else{
						if(((!customerIds.equals(order.getCustomerId()))  || (count == counts))){
							customerIds = order.getCustomerId();
							PdfPCell date = getRCCell("TOTAL:", font,7, 0);
							date.setBorderWidthLeft(1);
							date.setBorderColorLeft(BaseColor.DARK_GRAY);
							date.setBorderColorBottom(BaseColor.DARK_GRAY);
							date.setBorderWidthBottom(1);
							tblProducto.addCell(date);
							
							PdfPCell day = getCellCN("", font, 7, 0);
							day.setBorderWidthBottom(1);
							day.setBorderColorBottom(BaseColor.DARK_GRAY);
							tblProducto.addCell(day);			
							tblProducto.addCell(day);			

							PdfPCell ts = getCellRR("$"+String.format ("%,.2f",total), font, 7, 0);
							ts.setBorderWidthBottom(1);
							ts.setBorderColorBottom(BaseColor.DARK_GRAY);
							tblProducto.addCell(ts);
							
							PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalP), font, 7, 0);
							totalc.setBorderColorBottom(BaseColor.DARK_GRAY);
							totalc.setBorderWidthBottom(1);
							tblProducto.addCell(totalc);
							
							PdfPCell totalDis = getCellRR("$"+String.format ("%,.2f",totalUP), font, 7, 0);
							totalDis.setBorderWidthBottom(1);
							totalDis.setBorderColorBottom(BaseColor.DARK_GRAY);
							tblProducto.addCell(totalDis);
							

							if(title.equals("EXOTIC REEF IMPORTS, INC. CUSTOMER LEDGER REPORT")){
								PdfPCell totaln = getCellRR("", font, 7, 0);
								totaln.setBorderWidthBottom(1);
								totaln.setBorderColorBottom(BaseColor.DARK_GRAY);
								totaln.setBorderWidthRight(1);
								totaln.setBorderColorRight(BaseColor.DARK_GRAY);
								tblProducto.addCell(totaln);
							}else{
								PdfPCell totaln = getCellRR("$"+String.format ("%,.2f",totalBF), font, 7, 0);
								totaln.setBorderWidthBottom(1);
								totaln.setBorderColorBottom(BaseColor.DARK_GRAY);
								totaln.setBorderWidthRight(1);
								totaln.setBorderColorRight(BaseColor.DARK_GRAY);
								tblProducto.addCell(totaln);
							}
							document.add(tblProducto);
							
							total = 0.f;
							totalP = 0.f;
							totalUP = 0.f;
							totalB30 = 0.f;
							totalB60 = 0.f;
							totalB90 = 0.f;
							totalB120 = 0.f;
							tblProducto = new PdfPTable(7);
							tblProducto.setWidthPercentage(100);
							tblProducto.setWidths(new float[] { 13,13,17,13,13,13,18 });

						}
					}
					if(customerId.equals("") || (!customerId.equals(order.getCustomerId())) ){

						customerId = order.getCustomerId();
						getHeaderCus(document);
						PdfPTable tblProduct = new PdfPTable(6);
						tblProduct.setWidthPercentage(100);
						tblProduct.setWidths(new float[] { 7,30,15,7,15,26 });
						
						PdfPCell cusid = getCellCN(order.getCustomerId(), font, 7, 0);
						cusid.setBorderWidthLeft(1);
						cusid.setBorderWidthBottom(1);
						cusid.setBorderColorLeft(BaseColor.DARK_GRAY);
						tblProduct.addCell(cusid);
						
						PdfPCell cusName = getCellCN(order.getCustomerName(), font, 7, 0);
						cusName.setBorderWidthBottom(1);
						cusName.setBorderColorLeft(BaseColor.DARK_GRAY);
						tblProduct.addCell(cusName);	
						
						
						PdfPCell cusSale = getCellCN(order.getCustomerTerms(), font, 7, 0);
						cusSale.setBorderWidthBottom(1);
						cusSale.setBorderColorLeft(BaseColor.DARK_GRAY);
						tblProduct.addCell(cusSale);	
						
						PdfPCell totalc = getCellCN(order.getCustomerSalesperson(), font, 7, 0);
						totalc.setBorderWidthBottom(1);
						totalc.setBorderColorLeft(BaseColor.DARK_GRAY);
						tblProduct.addCell(totalc);
						
						PdfPCell totald = getCellCN(order.getCustomerPhone(), font, 7, 0);
						totald.setBorderWidthBottom(1);
						totald.setBorderColorLeft(BaseColor.DARK_GRAY);
						tblProduct.addCell(totald);					
						
						PdfPCell totaln = getCellCN(order.getCustomerContact(), font, 7, 0);
						totaln.setBorderWidthRight(1);
						totaln.setBorderWidthBottom(1);
						totaln.setBorderColorRight(BaseColor.DARK_GRAY);
						tblProduct.addCell(totaln);
						
						document.add(tblProduct);
						getHeader1(document);

					}
					
					
					PdfPCell invoice = getCellCN(order.getOrder_id()+"", font,7, 0);
					PdfPCell date = getCellCN(order.getCustomer_date(), font,7, 0);

					invoice.setBorderWidthLeft(1);
					invoice.setBorderColorLeft(BaseColor.DARK_GRAY);
					
					tblProducto.addCell(invoice);
					tblProducto.addCell(date);	
					
					PdfPCell chkref = getCellR(order.getPaymentMethod(), font, 7, 0);
					tblProducto.addCell(chkref);	
					
					PdfPCell invoices = getCellR("$"+order.getAll_Total(), font, 7, 0);
					tblProducto.addCell(invoices);	
					
					PdfPCell payment = getCellR("$"+order.getAmoutPaid(), font, 7, 0);
					tblProducto.addCell(payment);
					
					PdfPCell discount = getCellR("$"+order.getDisc(), font, 7, 0);
					tblProducto.addCell(discount);
			
						
					PdfPCell blanceFWD = getCellR("$"+order.getBalance(), font, 7, 0);
					blanceFWD.setBorderWidthRight(1);
					blanceFWD.setBorderColorRight(BaseColor.DARK_GRAY);
					tblProducto.addCell(blanceFWD);
					
					total = total + Float.parseFloat(order.getAll_Total().replace(",", ""));
					totalP = totalP + Float.parseFloat(order.getAmoutPaid().replace(",", ""));
					totalUP = totalUP + Float.parseFloat(order.getDisc().replace(",", ""));
					totalBF = totalBF + Float.parseFloat(order.getBalance().replace(",", ""));


				}
			}
	//	}
	}
	public void  addRow(SalesModel sale,Document document){
		PdfPTable tblProduct = new PdfPTable(5);
		tblProduct.setWidthPercentage(100);
		PdfPCell date = getCell(sale.getInv(), font, 10, 0);
		date.setBorderWidthLeft(1);
		date.setBorderColorLeft(BaseColor.DARK_GRAY);
		tblProduct.addCell(date);
		
		tblProduct.addCell(getCellCN(sale.getIntDate(), font, 10, 0));
		tblProduct.addCell(getCell(sale.getInvCusName(), font, 10, 0));
		
		tblProduct.addCell(getCellR(sale.getInvTotal(), font, 10, 0));
		
		PdfPCell totalc = getCellR(sale.getInvNon(), font, 10, 0);
		totalc.setBorderWidthRight(1);
		totalc.setBorderColorRight(BaseColor.DARK_GRAY);
		tblProduct.addCell(totalc);

		try {
			tblProduct.setWidths(new float[] { 15, 15,40,15,15 });
			document.add(tblProduct);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void  addTotal(SalesModel sale,Document document){
		PdfPTable tblProduct = new PdfPTable(5);
		tblProduct.setWidthPercentage(100);
		PdfPCell date = getCell("", font, 10, 0);
		date.setBorderWidthLeft(1);
		date.setBorderColorLeft(BaseColor.DARK_GRAY);
		date.setBorderColorBottom(BaseColor.DARK_GRAY);
		date.setBorderWidthBottom(1);
		tblProduct.addCell(date);
		
		PdfPCell day = getCellCN("", font, 10, 0);
		day.setBorderWidthBottom(1);
		day.setBorderColorBottom(BaseColor.DARK_GRAY);
		tblProduct.addCell(day);
		tblProduct.addCell(day);
		
		PdfPCell tp = getCellRR(sale.getInvTotal(), font, 10, 0);
		tp.setBorderWidthBottom(1);
		tp.setBorderColorBottom(BaseColor.DARK_GRAY);
		tblProduct.addCell(tp);
		
		PdfPCell totalc = getCellRR(sale.getInvNon(), font, 10, 0);
		totalc.setBorderWidthRight(1);
		totalc.setBorderColorRight(BaseColor.DARK_GRAY);
		totalc.setBorderWidthBottom(1);
		totalc.setBorderColorBottom(BaseColor.DARK_GRAY);
		tblProduct.addCell(totalc);
		try {
			tblProduct.setWidths(new float[] { 15, 15,40,15,15 });
			document.add(tblProduct);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void  addTitleInvoice(Document document){

		PdfPTable tblProduct = new PdfPTable(5);
		tblProduct.setWidthPercentage(100);
		tblProduct.addCell(getCellC("INVOICE#", font, 12, 1,1,1,1,1));
		tblProduct.addCell(getCellC("DATE", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("CUSTOMER CODE-NAME", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("TOTAL", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("NON-TOTAL", font, 12, 1,0,1,1,1));
		try {
			Paragraph paragraph1s = new Paragraph();
			paragraph1s.setSpacingBefore(1);
			document.add(paragraph1s);
			tblProduct.setWidths(new float[] { 15, 15,40,15,15 });
			document.add(tblProduct);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void  addTitle(String text,Document document){

		PdfPTable header = new PdfPTable(1);
		header.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		header.setWidthPercentage(100);
		header.setSpacingAfter(10);
		PdfPTable tblProduct = new PdfPTable(1);
		Paragraph paragraph = getText(text, font, 12, 1);
		try {
			header.addCell(paragraph);
			document.add(paragraph);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public PdfPCell getCellC(String text, String font, Integer size, Integer style,Integer left ,Integer top ,Integer right ,Integer botom) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getTextC(text, font, size, style));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(2);
		cell.setBorderColor(BaseColor.DARK_GRAY);
		cell.setBorderWidthRight(right);
		cell.setBorderWidthTop(top);
		cell.setBorderWidthBottom(botom);
		cell.setBorderWidthLeft(left);
		return cell;
	}

	public PdfPCell getCellCN(String text, String font, Integer size, Integer style) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getTextC(text, font, size, style));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(2);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}

	public PdfPCell getCellR(String text, String font, Integer size, Integer style) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getTextR(text, font, size, style));
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(2);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	public PdfPCell getCellRR(String text, String font, Integer size, Integer style) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getTextRR(text, font, size, style));
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(2);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	public PdfPCell getCellRB(String text, String font, Integer size, Integer style ,Integer left ,Integer top ,Integer right ,Integer botom) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getTextR(text, font, size, style));
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(3);
		//cell.setBorder(Rectangle.NO_BORDER);
		cell.setBorderColor(BaseColor.DARK_GRAY);
		cell.setBorderWidthRight(right);
		cell.setBorderWidthTop(top);
		cell.setBorderWidthBottom(botom);
		cell.setBorderWidthLeft(left);
		return cell;
	}

	public PdfPCell getCell(String text, String font, Integer size, Integer style ) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getText(text, font, size, style));
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(2);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	public PdfPCell getRCell(String text, String font, Integer size, Integer style ) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getRText(text, font, size, style));
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(2);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	public PdfPCell getRCCell(String text, String font, Integer size, Integer style ) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getRCText(text, font, size, style));
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(2);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	public PdfPCell getCellFix(String text, String font, Integer size, Integer style ) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getText(text, font, size, style));
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(1);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	public void getHeader1(Document document) throws MalformedURLException, IOException, DocumentException {
		PdfPTable header1 = new PdfPTable(3);
		header1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		header1.setWidthPercentage(100);

		header1.setWidths(new float[] { 40, 20, 40 });
		document.add(header1);
		
		Paragraph paragraph1 = new Paragraph();
		paragraph1.setSpacingBefore(10);
		document.add(paragraph1);
		PdfPTable body = new PdfPTable(5);

		body.setWidthPercentage(100);
		body.addCell(getCellC("ORDER DATE", font, 12, 1,1,1,1,1));
		body.addCell(getCellC("SHIP VIA", font, 12, 1,0,1,1,1));
		body.addCell(getCellC("F O B", font, 12, 1,0,1,1,1));
		body.addCell(getCellC("P O NUMBER", font, 12, 1,0,1,1,1));
		body.addCell(getCellC("ORDER NUMBER", font, 12, 1,0,1,1,1));

		Paragraph paragraph1s = new Paragraph();
		paragraph1s.setSpacingBefore(2);
		document.add(paragraph1s);
		if(isLedger == true){
			PdfPTable tblProduct = new PdfPTable(7);
			tblProduct.setWidthPercentage(100);
			tblProduct.addCell(getCellC("INVOICE #", font, 8, 1,1,1,1,1));
			tblProduct.addCell(getCellC("DATE", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("CHK/REF", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("INVOICE", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("PAYMENT", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("DISCOUNT", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("BALANCE FWD", font, 8, 1,0,1,1,1));
			tblProduct.setWidths(new float[] { 13,13,17,13,13,13,18 });
			document.add(tblProduct);
		}else{
			PdfPTable tblProduct = new PdfPTable(9);
			tblProduct.setWidthPercentage(100);
			tblProduct.addCell(getCellC("DATE", font, 8, 1,1,1,1,1));
			tblProduct.addCell(getCellC("INVOICE", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("AMOUNT", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("30", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("60", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("90", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("120", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("Open Balance", font, 8, 1,0,1,1,1));
			tblProduct.addCell(getCellC("Remark", font, 8, 1,0,1,1,1));
			tblProduct.setWidths(new float[] { 10,10,10,10,10,10,10,15,15 });
			document.add(tblProduct);
		}

	}
	public void getHeaderCus(Document document) throws MalformedURLException, IOException, DocumentException {
		PdfPTable header1 = new PdfPTable(3);
		header1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		header1.setWidthPercentage(100);

		header1.setWidths(new float[] { 40, 20, 40 });
		document.add(header1);
		
		Paragraph paragraph1 = new Paragraph();
		paragraph1.setSpacingBefore(10);
		document.add(paragraph1);
		PdfPTable body = new PdfPTable(5);

		body.setWidthPercentage(100);
		body.addCell(getCellC("CUST #", font, 12, 1,1,1,1,1));
		body.addCell(getCellC("SHIP VIA", font, 12, 1,0,1,1,1));
		body.addCell(getCellC("F O B", font, 12, 1,0,1,1,1));
		body.addCell(getCellC("P O NUMBER", font, 12, 1,0,1,1,1));
		body.addCell(getCellC("ORDER NUMBER", font, 12, 1,0,1,1,1));


		Paragraph paragraph1s = new Paragraph();
		paragraph1s.setSpacingBefore(2);
		document.add(paragraph1s);
		PdfPTable tblProduct = new PdfPTable(6);
		tblProduct.setWidthPercentage(100);
		tblProduct.addCell(getCellC("CUST#", font, 8, 1,1,1,1,1));
		tblProduct.addCell(getCellC("CUSTOMER NAME", font, 8, 1,0,1,1,1));
		tblProduct.addCell(getCellC("TERMS", font, 8, 1,0,1,1,1));
		tblProduct.addCell(getCellC("SLSMN", font, 8, 1,0,1,1,1)); 
		tblProduct.addCell(getCellC("PHONE", font, 8, 1,0,1,1,1)); 
		tblProduct.addCell(getCellC("CONTACT ", font, 8, 1,0,1,1,1));
		tblProduct.setWidths(new float[] { 7,30,15,7,15,26 });
		/*if(type.equals("sumary")){
			document.add(tblProduct);
		}*/
		document.add(tblProduct);
	}
	public void getFooter(PdfWriter writer,Document document) throws MalformedURLException, IOException, DocumentException {
		
		PdfContentByte cb = writer.getDirectContent();
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer(),
            (document.right() - document.left()) / 2 + document.leftMargin(),
            document.bottom()-10, 0);
	}
	private Phrase footer() {
	    Phrase p = new Phrase("1924 E Maple Ave, Suite A, El Segundo California 90245, USA Tel: 310-648-7258 Fax: 310-648-7611",
				FontFactory.getFont(font, 10, Font.ITALIC, new CMYKColor(0, 0, 0, 255)));
	    return p;
	}
	public void getHeader(PdfWriter writer,Document document) throws MalformedURLException, IOException, DocumentException {
		Image logo = Image.getInstance("pdf/images/fishlogo_bw.png");
		logo.scaleAbsolute(137.5f, 73.75f);

		PdfPTable header = new PdfPTable(3);
		header.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		header.setWidthPercentage(100);
		PdfPCell pcell1 = new PdfPCell();
		pcell1.addElement(getText("Date: " +todayDate, font, 12, 1));
		PdfPCell pcell2 = new PdfPCell();
		pcell2.addElement(getText("", font, 12, 1));
		PdfPCell pcell3 = new PdfPCell();
		pcell3.addElement(getText(String.format("Page No. %d", writer.getPageNumber()), font, 12, 1));
		pcell3.setBorder(Rectangle.NO_BORDER);
		pcell2.setBorder(Rectangle.NO_BORDER);
		pcell1.setBorder(Rectangle.NO_BORDER);
		PdfPCell cell;
		cell = new PdfPCell();
		cell.setRowspan(3);
		cell.addElement(logo);
		PdfPCell cells = new PdfPCell();
		cells.setRowspan(3);
		cells.addElement(getText("  ", font, 14, 1));
		cell.setBorder(Rectangle.NO_BORDER);
		cells.setBorder(Rectangle.NO_BORDER);
		PdfPCell row4 = new PdfPCell();
		row4.setColspan(3);
		Paragraph paragraph = getText(title, font, 16, 1);
		paragraph.setAlignment(1);
		row4.setBorder(Rectangle.NO_BORDER);
		row4.addElement(paragraph);
		header.addCell(cell);
		header.addCell(cells);

		header.addCell(pcell1);
		header.addCell(pcell3);
		header.addCell(pcell2);
		header.addCell(row4);
		document.add(header);
		// end header
	}

	public void PrintInvoicet() throws MalformedURLException, IOException {

		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			// init
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf/invoice/invoice.pdf"));
			document.open();

			Anchor anchorTarget = new Anchor("First page of the document.");
			anchorTarget.setName("BackToTop");
			Paragraph paragraph1 = new Paragraph();
			paragraph1.setSpacingBefore(50);
			paragraph1.add(anchorTarget);
			document.add(paragraph1);
			document.add(new Paragraph("Some more text on the first page with different color and font type.",
					FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 255, 0, 0))));

			Paragraph title1 = new Paragraph("Chapter 1",
					FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new CMYKColor(0, 255, 255, 17)));
			Chapter chapter1 = new Chapter(title1, 1);
			chapter1.setNumberDepth(0);

			Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
					FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 255, 17)));
			Section section1 = chapter1.addSection(title11);
			Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");
			section1.add(someSectionText);
			someSectionText = new Paragraph("Following is a 3 X 2 table.");
			section1.add(someSectionText);

			PdfPTable t = new PdfPTable(3);
			t.setSpacingBefore(25);
			t.setSpacingAfter(25);
			PdfPCell c1 = new PdfPCell(new Phrase("Header1"));
			t.addCell(c1);
			PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
			t.addCell(c2);
			PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
			t.addCell(c3);
			t.addCell("1.1");
			t.addCell("1.2");
			t.addCell("1.3");
			section1.add(t);

			Image image2 = Image.getInstance("pdf/images/fishlogo_bw.png");

			image2.scaleAbsolute(220f, 118f);

			section1.add(image2);

			document.add(chapter1);

			document.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
