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

import application.Model.SalesModel;

public class PdfSales {
	private String font = FontFactory.COURIER;
	private List<SalesModel> lstSale = new ArrayList<>();
	private String fromDate ="";
	private String toDate ="";
	private String type ="";
	public class HeaderFooterPageEvent extends PdfPageEventHelper {
		PdfTemplate total;
	   public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }
	    public void onStartPage(PdfWriter writer,Document document) {
	    	try {
				getHeader(writer,document);
				getHeader1(document);
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
	public void Print(List<SalesModel> lstSale,String fromDate,String toDate,String type)
			throws MalformedURLException, IOException {
		this.lstSale = lstSale;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.type = type;
		int ver = 1;
		String file ="pdf/sales/Invoice-"+type+"-"+fromDate+"-"+toDate+"-"+ver+".pdf";
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
	public void getBody(Document document) throws MalformedURLException, IOException, DocumentException {
		if(type.equals("sumary")){
			PdfPTable tblProduct = new PdfPTable(7);
			tblProduct.setWidthPercentage(100);
			//tblProduct.setWidths(new float[] { 15,15,23,23,24 });
//			tblProduct.setWidths(new float[] { 15,15,35,35 });
			tblProduct.setWidths(new float[] { 15,15,15,15,15,10,15 });

			Float totalP = 0.f;
			Float totalS = 0.f;
			Float totalC = 0.f;
			Float totalD = 0.f;
			Float totalT = 0.f;
			Float totalN = 0.f;
			for (SalesModel sale : lstSale) {
				PdfPCell date = getCell(sale.getCustomer_date(), font, 10, 0);
				date.setBorderWidthLeft(1);
				date.setBorderColorLeft(BaseColor.DARK_GRAY);
				tblProduct.addCell(date);
				tblProduct.addCell(getCellCN(sale.getDay(), font, 10, 0));
				//tblProduct.addCell(getCellR(sale.getTotal_pending(), font, 10, 0));
				tblProduct.addCell(getCellR(sale.getTotal_sales(), font, 10, 0));
				
				PdfPCell totalc = getCellR(sale.getTotal_complete(), font, 10, 0);
				tblProduct.addCell(totalc);
				
				PdfPCell totald = getCellR(sale.getTotal_discount(), font, 10, 0);
				tblProduct.addCell(totald);
				
				PdfPCell totalt = getCellR(sale.getTotal_tax(), font, 10, 0);
				tblProduct.addCell(totalt);
				
				PdfPCell totaln = getCellR(sale.getTotal_net_sales(), font, 10, 0);
				totaln.setBorderWidthRight(1);
				totaln.setBorderColorRight(BaseColor.DARK_GRAY);
				tblProduct.addCell(totaln);
				
				totalP = totalP + Float.parseFloat(sale.getTotal_pending().replace("$", "").replace(",", ""));
				totalS = totalS + Float.parseFloat(sale.getTotal_sales().replace("$", "").replace(",", ""));
				totalC = totalC + Float.parseFloat(sale.getTotal_complete().replace("$", "").replace(",", ""));
				totalD = totalD + Float.parseFloat(sale.getTotal_discount().replace("$", "").replace(",", ""));
				totalT = totalT + Float.parseFloat(sale.getTotal_tax().replace("$", "").replace(",", ""));
				totalN = totalN + Float.parseFloat(sale.getTotal_net_sales().replace("$", "").replace(",", ""));
	
			}
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
			
		/*	PdfPCell tp = getCellRR("$"+String.format ("%,.2f",totalP), font, 10, 0);
			tp.setBorderWidthBottom(1);
			tp.setBorderColorBottom(BaseColor.DARK_GRAY);
			tblProduct.addCell(tp);*/
			
			PdfPCell ts = getCellRR("$"+String.format ("%,.2f",totalS), font, 10, 0);
			ts.setBorderWidthBottom(1);
			ts.setBorderColorBottom(BaseColor.DARK_GRAY);
			tblProduct.addCell(ts);
			
			PdfPCell totalc = getCellRR("$"+String.format ("%,.2f",totalC), font, 10, 0);
			totalc.setBorderWidthBottom(1);
			totalc.setBorderColorBottom(BaseColor.DARK_GRAY);
			tblProduct.addCell(totalc);
			
			PdfPCell totald = getCellRR("$"+String.format ("%,.2f",totalD), font, 10, 0);
			totald.setBorderWidthBottom(1);
			totald.setBorderColorBottom(BaseColor.DARK_GRAY);
			tblProduct.addCell(totald);
			
			PdfPCell totalt = getCellRR("$"+String.format ("%,.2f",totalT), font, 10, 0);
			totalt.setBorderWidthBottom(1);
			totalt.setBorderColorBottom(BaseColor.DARK_GRAY);
			tblProduct.addCell(totalt);
			
			PdfPCell totaln = getCellRR("$"+String.format ("%,.2f",totalN), font, 10, 0);
			totaln.setBorderWidthBottom(1);
			totaln.setBorderColorBottom(BaseColor.DARK_GRAY);
			totaln.setBorderWidthRight(1);
			totaln.setBorderColorRight(BaseColor.DARK_GRAY);
			tblProduct.addCell(totaln);
			
			document.add(tblProduct);
		}
		if(type.equals("sale")){
			String currentState ="title";
			for (SalesModel sale : lstSale) {
				String typeRow ="";
				String cusName = sale.getInvCusName();
				String date = sale.getIntDate();
				String totalNone = sale.getInvNon();
				String total= sale.getInvTotal();
				if(date.length()>0  || (totalNone.length()>0 && total.length()==0)){
					typeRow ="order";
				}
				if(date.length()==0 && cusName.length()==0 && totalNone.length()>0 && total.length()>0){
					typeRow ="total";
				}
				if(date.length()==0 && cusName.length()>0 && totalNone.length()==0){
					typeRow ="title";
				}
				System.out.println(date);
				// add data
				if(typeRow.equals("title")){
					addTitle(cusName,document);
					System.out.println("1");
					currentState = typeRow;
				}
				if(typeRow.equals("order")){
					if(currentState.equals("title")){
						currentState = typeRow;
						// add title invoice
						addTitleInvoice(document);
					}
						addRow(sale,document);
					
					// add list invoice
					
					
				}
				if(typeRow.equals("total")){
					// show total
					addTotal(sale,document);
					currentState = typeRow;
				}
			}
		}
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
		//PdfPCell mid = new PdfPCell();
		//mid.addElement(getText("", font, 12, 0));
		//mid.setBorder(Rectangle.NO_BORDER);
		//mid.setRowspan(6);
		//header1.addCell(getCell("BILL TO:", font, 14, 1));
		//header1.addCell(mid);
		//header1.addCell(getCell("SHIP TO:", font, 14, 1));

		/*header1.addCell(getCell(bill.getCompanyName(), font, 12, 0));
		header1.addCell(getCell(ship.getCompanyName(), font, 12, 0));

		header1.addCell(getCell(bill.getAddress(), font, 12, 0));
		header1.addCell(getCell(ship.getAddress(), font, 12, 0));

		header1.addCell(getCell(bill.getAddress2(), font, 12, 0));
		header1.addCell(getCell(ship.getAddress2(), font, 12, 0));

		header1.addCell(getCell(bill.getCity() + ", " + bill.getStates() + " " + bill.getZip(), font, 12, 0));
		header1.addCell(getCell(ship.getCity() + ", " + ship.getStates() + " " + ship.getZip(), font, 12, 0));

		header1.addCell(getCell(bill.getPhone1(), font, 12, 0));
		header1.addCell(getCell(ship.getPhone1(), font, 12, 0));*/
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

		/*body.addCell(getCellC(currentOrder.getCustomer_date(), font, 12, 0,1,0,1,1));
		body.addCell(getCellC(orderInfo.getCustomer_ship(), font, 12, 0,0,0,1,1));
		body.addCell(getCellC(orderInfo.getFob(), font, 12, 0,0,0,1,1));
		body.addCell(getCellC(orderInfo.getPonumber() + "", font, 12, 0,0,0,1,1));
		body.addCell(getCellC(currentOrder.getOrder_id() + "", font, 12, 0,0,0,1,1));

		body.addCell(getCellC("DUE DATE", font, 12, 1,1,0,1,1));
		body.addCell(getCellC("TERMS ", font, 12, 1,0,0,1,1));
		body.addCell(getCellC("SALESPERSON ", font, 12, 1,0,0,1,1));
		body.addCell(getCellC("ARRIVE", font, 12, 1,0,0,1,1));
		body.addCell(getCellC("TRACKING NUMBER", font, 12, 1,0,0,1,1));

		body.addCell(getCellC(currentOrder.getCustomer_date(), font, 12, 0,1,0,1,1));
		body.addCell(getCellC(orderInfo.getTerms(), font, 12, 0,0,0,1,1));
		body.addCell(getCellC(orderInfo.getSalesperson(), font, 12, 0,0,0,1,1));
		body.addCell(getCellC("", font, 12, 0,0,0,1,1));
		body.addCell(getCellC(orderInfo.getAwb(), font, 12, 0,0,0,1,1));*/
		//document.add(body);
		Paragraph paragraph1s = new Paragraph();
		paragraph1s.setSpacingBefore(2);
		document.add(paragraph1s);
		PdfPTable tblProduct = new PdfPTable(7);
		tblProduct.setWidthPercentage(100);
		tblProduct.addCell(getCellC("DATE", font, 12, 1,1,1,1,1));
		tblProduct.addCell(getCellC("DAY", font, 12, 1,0,1,1,1));
		//tblProduct.addCell(getCellC("PENDING TOTAL", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("SALES TOTAL", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("COMPLETED", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("DISCOUNT", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("TAX", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("NET SALES", font, 12, 1,0,1,1,1));
		//tblProduct.setWidths(new float[] { 15, 15,23,23,24 });
		tblProduct.setWidths(new float[] { 15,15,15,15,15,10,15 });
		if(type.equals("sumary")){
			document.add(tblProduct);
		}
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
		// header.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell pcell1 = new PdfPCell();
		pcell1.addElement(getText("From Date: " +fromDate, font, 12, 1));
		PdfPCell pcell2 = new PdfPCell();
		pcell2.addElement(getText("To Date: " +toDate, font, 12, 1));
		PdfPCell pcell3 = new PdfPCell();
		pcell3.addElement(getText(String.format("Page No. %d", writer.getPageNumber()), font, 12, 1));
		System.out.println("--------"+writer.getPageNumber());
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
		String title = "EXOTIC REEF IMPORTS, INC. SUMMARY INVOICE REGISTER - DAILY";
		if(type.equals("sale")){
			title = "EXOTIC REEF IMPORTS, INC.";
		}
		Paragraph paragraph = getText(title, font, 16, 1);
		paragraph.setAlignment(1);
		row4.setBorder(Rectangle.NO_BORDER);
		row4.addElement(paragraph);
		header.addCell(cell);
		header.addCell(cells);

		header.addCell(pcell1);
		header.addCell(pcell2);
		header.addCell(pcell3);
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

			// section1.add(image2);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
