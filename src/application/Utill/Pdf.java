package application.Utill;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
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

import application.Model.CustomerModel;
import application.Model.ExpressModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.OrderModel;
import application.Model.ProductModel;

public class Pdf {
	private String font = FontFactory.COURIER;
	private OrderDetailModel orderDetail;
	private OrderModel currentOrder;
	private CustomerModel bill;
	private CustomerModel ship;
	private OrderInfoModel orderInfo;
	private List<ProductModel> lstProduct;
	private String logos ="pdf/images/fishlogo_bw.png";
	private String footers ="1924 E Maple Ave, Suite A, El Segundo California 90245, USA Tel: 310-648-7258 Fax: 310-648-7611";
	private String footers1 ="";

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
	public void PrintInvoice(OrderDetailModel orderDetails, OrderModel currentOrders)
			throws MalformedURLException, IOException {
		orderDetail = orderDetails;
		currentOrder = currentOrders;
		bill = orderDetail.getCustomer();
		ship = orderDetails.getShipTo();
		orderInfo = orderDetails.getOrderInfo();
		lstProduct = orderDetails.getLstProduct();
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("pdf/invoice/" + currentOrder.getOrder_id() + ".pdf"));
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
					File myFile = new File("pdf/invoice/" + currentOrder.getOrder_id() + ".pdf");
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
	public void PrintInvoiceExpress(OrderDetailModel orderDetails, OrderModel currentOrders,ExpressModel expressModel,String chlSL)
			throws MalformedURLException, IOException {
		if(expressModel.getLogo() != null){
			if(!expressModel.getLogo().equals("")){
				logos = expressModel.getLogo().replace(" ", "%20");
				logos = logos.replace(" ", "%20");
				logos = logos.replace(" ", "%20");
				logos = logos.replace(" ", "%20");
			}
		}
		if(expressModel.getStoreId() != null){
			footers = expressModel.getStoreName()+", "+expressModel.getAddress1()+", "+expressModel.getCity()+", "+expressModel.getState()+", "+expressModel.getZipCode()+", "+expressModel.getPhoneNumber();
		}
		if(chlSL.equals("1")){
			footers1 ="I agree for a substitute of a size smaller or larger";	
		}
		orderDetail = orderDetails;
		currentOrder = currentOrders;
		bill = orderDetail.getCustomer();
		ship = orderDetails.getShipTo();
		orderInfo = orderDetails.getOrderInfo();
		lstProduct = orderDetails.getLstProduct();
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("pdf/invoice/" + currentOrder.getOrder_id() + ".pdf"));
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
					File myFile = new File("pdf/invoice/" + currentOrder.getOrder_id() + ".pdf");
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
	public String PrintInvoices(OrderDetailModel orderDetails, OrderModel currentOrders)
			throws MalformedURLException, IOException {
		String filexx = "";
		orderDetail = orderDetails;
		currentOrder = currentOrders;
		bill = orderDetail.getCustomer();
		ship = orderDetails.getShipTo();
		orderInfo = orderDetails.getOrderInfo();
		lstProduct = orderDetails.getLstProduct();
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("pdf/invoice/" + currentOrder.getOrder_id() + ".pdf"));
			Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			//getHeader(document);
			//getHeader1(document);
			getBody(document);
			document.close();
			filexx = "pdf/invoice/" + currentOrder.getOrder_id() + ".pdf";
			return filexx;

		/*	if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File("pdf/invoice/" + currentOrder.getOrder_id() + ".pdf");
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
				}
			}*/

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return filexx;
		return filexx;
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

	public void getBody(Document document) throws MalformedURLException, IOException, DocumentException {
		PdfPTable tblProduct = new PdfPTable(8);
		tblProduct.setWidthPercentage(100);
		tblProduct.setWidths(new float[] { 12, 5, 12, 26,20, 7, 7, 9 });
		for (ProductModel product : lstProduct) {
			PdfPCell sku = getCell(product.getSku(), font, 10, 0);
			sku.setBorderWidthLeft(1);
			sku.setBorderColorLeft(BaseColor.DARK_GRAY);
			tblProduct.addCell(sku);
			tblProduct.addCell(getCellCN(product.getQty(), font, 10, 0));
			tblProduct.addCell(getCellCN(product.getSize(), font, 10, 0));
			tblProduct.addCell(getCell(product.getName(), font, 8, 0));
			tblProduct.addCell(getCell(product.getScientific(), font, 10, 0));
			tblProduct.addCell(getCellR(product.getPrice(), font, 10, 0));
			tblProduct.addCell(getCellR(product.getDisc() + "%", font, 10, 0));
			PdfPCell total = getCellR(product.getTotal(), font, 10, 0);
			total.setBorderWidthRight(1);
			total.setBorderColorRight(BaseColor.DARK_GRAY);
			tblProduct.addCell(total);
		}
		document.add(tblProduct);
		PdfPTable tblProducts = new PdfPTable(1);
		tblProducts.setWidthPercentage(100);
		tblProducts.setWidths(new float[] { 100});
		PdfPCell total = getCellR("", font, 10, 0);
		total.setBorderWidthRight(1);
		total.setBorderColorRight(BaseColor.DARK_GRAY);
		tblProducts.addCell(total);
		//document.add(tblProducts);
		PdfPTable tblFootProduct = new PdfPTable(3);
		tblFootProduct.setWidthPercentage(100);
		PdfPCell left = new PdfPCell();
		PdfPTable tblLeft= new PdfPTable(1);
		tblLeft.setWidthPercentage(100);
		left.setUseAscender(true);
		left.setUseDescender(false);
		left.setPaddingTop(3);
		left.setPaddingRight(0);
		left.setPaddingLeft(1);
		left.setPaddingBottom(0);
		tblLeft.addCell(getCellFix("#=indicates non-discountable item. *=Indicates not guaranteed.", font, 10, 0));
		tblLeft.addCell(getCellFix("- DOA'S IN EXCESS OG 10% GUARANTEED", font, 10, 0));
		tblLeft.addCell(getCellFix("- BAD CHECK/RETURNED CHECK FEE $25.00", font, 10, 0));
		tblLeft.addCell(getCellFix("- ALL DOA'S MUST BE REPORTED WITHIN 24 HOURS OF SHIPMENT RECEIPT", font, 10, 0));
		tblLeft.addCell(getCellFix("- 2.5% SURCHARGE ON ALL PRE-PAID FREIGHT", font, 10, 0));
		tblLeft.addCell(getCellFix("- NO CLAIMS ON FREIGHT INVOICES", font, 10, 0));
		tblLeft.addCell(getCellFix("(0) Past due for 0000.00", font, 10, 0));
		tblLeft.addCell(getCellFix("(0) Current for 0.00", font, 10, 0));
		tblLeft.addCell(getCellFix("(0) Credit for 0.00", font, 10, 0));
		//tblLeft.addCell(getCellFix());
		left.addElement(tblLeft);
		left.setBorderColor(BaseColor.DARK_GRAY);
		left.setBorderWidthRight(0);
		left.setBorderWidthTop(1);
		left.setBorderWidthBottom(1);
		left.setBorderWidthLeft(1);
		left.setRowspan(6);
		tblFootProduct.addCell(left);
		Float totalAll =  Float.parseFloat(currentOrder.getAll_Total());
		Float surcharge = currentOrder.getSurcharge();
		Float salesDisc = Float.valueOf(bill.getSalesDisc())/100;
		Float subTotal1 = totalAll - surcharge;
		Float salesDiscSub =  salesDisc*subTotal1;
		Float subTotal = subTotal1 - salesDiscSub;
		Float shipping = bill.getShippingCost();
		Float extendedTotal = subTotal +  surcharge + shipping;
		Float paidAmount = (float) 0;
		Float blanceDue = extendedTotal - paidAmount;
		tblFootProduct.addCell(getCellRB("Sub Total:", font, 12, 1,1,1,1,1));
		tblFootProduct.addCell(getCellRB(String.format("%.2f", subTotal), font, 12, 1,0,1,1,1));
		
		tblFootProduct.addCell(getCellRB("0.00% Discount:", font, 12, 1,1,0,1,1));
		tblFootProduct.addCell(getCellRB(String.format("%.2f", salesDisc), font, 12, 1,0,0,1,1));
		
		tblFootProduct.addCell(getCellRB("Surcharge:", font, 12, 1,1,0,1,1));
		tblFootProduct.addCell(getCellRB(String.format("%.2f", surcharge), font, 12, 1,0,0,1,1));
		
		tblFootProduct.addCell(getCellRB("Shipping:", font, 12, 1,1,0,1,1));
		tblFootProduct.addCell(getCellRB(String.format("%.2f", shipping), font, 12, 1,0,0,1,1));
		
		tblFootProduct.addCell(getCellRB("Extended Total:", font, 12, 1,1,0,1,1));
		tblFootProduct.addCell(getCellRB(String.format("%.2f", extendedTotal), font, 12, 1,0,0,1,1));
		
		tblFootProduct.addCell(getCellRB("Paid Amount:", font, 12, 1,1,0,1,1));
		tblFootProduct.addCell(getCellRB(String.format("%.2f", paidAmount), font, 12, 1,0,0,1,1));
		
		tblFootProduct.addCell(getCellCN("", font, 10, 0));
		tblFootProduct.addCell(getCellRB("BALANCE DUE:", font, 12, 1,1,0,1,1));
		tblFootProduct.addCell(getCellRB(String.format("%.2f", blanceDue), font, 12, 1,0,0,1,1));
		
		tblFootProduct.setWidths(new float[] { 68, 20, 12 });
		document.add(tblFootProduct);
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
		PdfPCell mid = new PdfPCell();
		mid.addElement(getText("", font, 12, 0));
		mid.setBorder(Rectangle.NO_BORDER);
		mid.setRowspan(6);
		header1.addCell(getCell("BILL TO:", font, 14, 1));
		header1.addCell(mid);
		header1.addCell(getCell("SHIP TO:", font, 14, 1));

		header1.addCell(getCell(bill.getCompanyName(), font, 12, 0));
		header1.addCell(getCell(ship.getCompanyName(), font, 12, 0));

		header1.addCell(getCell(bill.getAddress(), font, 12, 0));
		header1.addCell(getCell(ship.getAddress(), font, 12, 0));

		header1.addCell(getCell(bill.getAddress2(), font, 12, 0));
		header1.addCell(getCell(ship.getAddress2(), font, 12, 0));

		header1.addCell(getCell(bill.getCity() + ", " + bill.getStates() + " " + bill.getZip(), font, 12, 0));
		header1.addCell(getCell(ship.getCity() + ", " + ship.getStates() + " " + ship.getZip(), font, 12, 0));

		header1.addCell(getCell(bill.getPhone1(), font, 12, 0));
		header1.addCell(getCell(ship.getPhone1(), font, 12, 0));
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

		body.addCell(getCellC(currentOrder.getCustomer_date(), font, 12, 0,1,0,1,1));
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
		body.addCell(getCellC(orderInfo.getAwb(), font, 12, 0,0,0,1,1));
		document.add(body);
		Paragraph paragraph1s = new Paragraph();
		paragraph1s.setSpacingBefore(2);
		document.add(paragraph1s);
		PdfPTable tblProduct = new PdfPTable(8);
		tblProduct.setWidthPercentage(100);
		tblProduct.addCell(getCellC("SKU", font, 12, 1,1,1,1,1));
		tblProduct.addCell(getCellC("QTY", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("SIZE", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("NAME", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("SCIENTIFIC ", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("PRICE", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("DISC", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("TOTAL", font, 12, 1,0,1,1,1));
		tblProduct.setWidths(new float[] { 12, 5, 12, 26,20, 7, 7, 9 });
		document.add(tblProduct);
	}
	public void getFooter(PdfWriter writer,Document document) throws MalformedURLException, IOException, DocumentException {
        if(!footers1.equals("")){
        	PdfContentByte cb1 = writer.getDirectContent();
            ColumnText.showTextAligned(cb1, Element.ALIGN_CENTER, footer1(),
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom(), 0);
        }
		PdfContentByte cb = writer.getDirectContent();
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer(),
            (document.right() - document.left()) / 2 + document.leftMargin(),
            document.bottom()-10, 0);
	}
	private Phrase footer() {
	    Phrase p = new Phrase(footers,
				FontFactory.getFont(font, 10, Font.ITALIC, new CMYKColor(0, 0, 0, 255)));
	    return p;
	}
	private Phrase footer1() {
	    Phrase p = new Phrase(footers1,
				FontFactory.getFont(font, 12, Font.BOLD, new CMYKColor(0, 0, 0, 255)));
	    return p;
	}
	public void getHeader(PdfWriter writer,Document document) throws MalformedURLException, IOException, DocumentException {
		//Image logo = Image.getInstance(logos);
		Image logo = Image.getInstance(logos);
		logo.scaleAbsolute(137.5f, 73.75f);

		PdfPTable header = new PdfPTable(3);
		header.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		header.setWidthPercentage(100);
		// header.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell pcell1 = new PdfPCell();
		pcell1.addElement(getText("Invoice No. " + currentOrder.getOrder_id(), font, 12, 1));
		PdfPCell pcell2 = new PdfPCell();
		pcell2.addElement(getText("Invoice Date: " + currentOrder.getCustomer_date(), font, 12, 1));
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
		Paragraph paragraph = getText("INVOICE", font, 18, 1);
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
