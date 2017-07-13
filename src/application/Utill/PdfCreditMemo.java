package application.Utill;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
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

import application.Dao.CustomerDao;
import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.ProductModel;

public class PdfCreditMemo {
	private String font = FontFactory.COURIER;
	private CustomerModel bill = new CustomerModel();
	private CustomerModel ship = new CustomerModel();
	private OrderInfoModel orderInfo = new OrderInfoModel();
	private List<ProductModel> lstProduct;
	OrderDetailModel orderDetail = new OrderDetailModel();
	private String logos ="pdf/images/fishlogo_bw.png";
	private String footers ="1924 E Maple Ave, Suite A, El Segundo California 90245, USA Tel: 310-648-7258 Fax: 310-648-7611";
	private String footers1 ="";
	String cusid = "";
	String orderidc = "";

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
	public void print(String cusid,String orderidc) throws MalformedURLException, IOException, ClassNotFoundException, SQLException{
		this.cusid = cusid;
		this.orderidc = orderidc;
		int orderid =  Integer.parseInt(orderidc.replace("-c",""));
		CustomerDao cusDao = new CustomerDao();
		OrderDao orderDao = new OrderDao();
		CustomerModel customer = cusDao.getCustomerByCusId(cusid);
		bill = customer;
		ship = customer;
		orderDetail = orderDao.getOrderDetail(orderid);
		orderInfo = orderDetail.getOrderInfo();
		lstProduct = orderDetail.getLstProduct();
		Document document = new Document(PageSize.A4, 10, 10, 10, 20);
		try {
			//load data from database for pdf 
			
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("pdf/creditmemo/Credit-Memo #" + cusid + ".pdf"));
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
					File myFile = new File("pdf/creditmemo/Credit-Memo #" + cusid + ".pdf");
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
		Float totalc = 0.f;
		int totalqty = 0;
		for (ProductModel product : lstProduct) {
			if(product.getFishdie() == true){
				PdfPCell sku = getCell(product.getSku(), font, 10, 0);
				sku.setBorderWidthLeft(1);
				sku.setBorderColorLeft(BaseColor.DARK_GRAY);
				tblProduct.addCell(sku);
				tblProduct.addCell(getCellCN(product.getQty(), font, 10, 0));
				tblProduct.addCell(getCellCN(product.getSize(), font, 10, 0));
				tblProduct.addCell(getCell(product.getName(), font, 8, 0));
				tblProduct.addCell(getCell(product.getScientific(), font, 8, 0));
				tblProduct.addCell(getCellR(product.getGrxp(), font, 10, 0));
				tblProduct.addCell(getCellR(product.getPrice() + "", font, 10, 0));
				PdfPCell total = getCellR("-"+product.getTotal(), font, 10, 0);
				total.setBorderWidthRight(1);
				total.setBorderColorRight(BaseColor.DARK_GRAY);
				tblProduct.addCell(total);
				totalc = totalc + Float.parseFloat(product.getTotal());
				totalqty = totalqty + Integer.parseInt(product.getQty());
			}
		}
		PdfPCell fix = getCell("", font, 10, 0);
		fix.setBorderWidthTop(1);
		fix.setBorderColorLeft(BaseColor.DARK_GRAY);
		tblProduct.addCell(fix);
		tblProduct.addCell(fix);
		tblProduct.addCell(fix);
		tblProduct.addCell(fix);
		tblProduct.addCell(fix);
		tblProduct.addCell(fix);
		tblProduct.addCell(fix);
		tblProduct.addCell(fix);
		
		PdfPTable tblProducts = new PdfPTable(3);
		tblProducts.setWidthPercentage(100);
		tblProducts.setWidths(new float[] { 30,30,40 });
		PdfPCell blank = getCell("", font, 10, 0);
		
		tblProducts.addCell(blank);
		tblProducts.addCell(blank);
		tblProducts.addCell(getCellR("Sub Total............... -"+totalc, font, 10, 0));
		
		tblProducts.addCell(blank);
		tblProducts.addCell(blank);
		tblProducts.addCell(getCellR("Discount ( 0.000%)......... 0.00", font, 10, 0));
		
		tblProducts.addCell(blank);
		tblProducts.addCell(blank);
		tblProducts.addCell(getCellR("Tax ( 0.000%).............  0.00", font, 10, 0));
		
		tblProducts.addCell(blank);
		tblProducts.addCell(blank);
		tblProducts.addCell(getCellR("Total .................. -"+totalc, font, 10, 0));
		tblProducts.setSpacingBefore(10);
		
		PdfPTable tblbuttom = new PdfPTable(1);
		tblbuttom.setWidthPercentage(100);
		tblbuttom.setWidths(new float[] { 100 });
		PdfPCell totalqtys = getCell(totalqty+" Units", font, 10, 0);
		tblbuttom.addCell(totalqtys);
		PdfPCell totalqtys1 = getCell("# - indicates non discountable; * - indicates non taxable", font, 10, 0);
		PdfPCell totalqtys2 = getCell("**********************************************************", font, 10, 0);
		tblbuttom.addCell(totalqtys1);
		tblbuttom.addCell(totalqtys2);
		tblbuttom.setSpacingBefore(10);
		document.add(tblProduct);
		document.add(tblProducts);
		document.add(tblbuttom);

		
	/*	PdfPTable tblProducts = new PdfPTable(1);
		tblProducts.setWidthPercentage(100);
		tblProducts.setWidths(new float[] { 100});
		PdfPCell total = getCellR("", font, 10, 0);
		total.setBorderWidthRight(1);
		total.setBorderColorRight(BaseColor.DARK_GRAY);
		tblProducts.addCell(total);*/
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
	public PdfPCell getCellCa(String text, String font, Integer size, Integer style,Integer left ,Integer top ,Integer right ,Integer botom) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(getTextC(text, font, size, style));
		//cell.setVerticalAlignment(Element.ALIGN_LEFT);
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		cell.setPadding(1);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderColor(BaseColor.WHITE);
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
		PdfPTable body = new PdfPTable(4);

		body.setWidthPercentage(100);
		body.addCell(getCellCa("Date.............", font, 12, 1,1,1,1,1));
		body.addCell(getCellCa(orderInfo.getCustomerDate(), font, 12, 1,1,1,1,1));
		body.addCell(getCellCa("Order Date.......", font, 12, 1,0,1,1,1));
		body.addCell(getCellCa(orderInfo.getCustomerDate(), font, 12, 1,1,1,1,1));

		body.addCell(getCellCa("Ship Via.........", font, 12, 1,0,1,1,1));
		body.addCell(getCellCa(orderInfo.getCustomer_ship(), font, 12, 1,1,1,1,1));
		body.addCell(getCellCa("P.O.B............", font, 12, 1,0,1,1,1));
		body.addCell(getCellCa(orderInfo.getFob(), font, 12, 1,1,1,1,1));

		body.addCell(getCellCa("F.O Number.......", font, 12, 1,0,1,1,1));
		body.addCell(getCellCa(orderInfo.getPonumber() + "", font, 12, 1,1,1,1,1));
		body.addCell(getCellCa("Sales Person.....", font, 12, 1,1,0,1,1));
		body.addCell(getCellCa(orderInfo.getSalesperson(), font, 12, 1,1,1,1,1));

		
		body.addCell(getCellCa("Term.............", font, 12, 1,0,0,1,1));
		body.addCell(getCellCa(orderInfo.getTerms(), font, 12, 1,1,1,1,1));
		body.addCell(getCellCa("Due Date.........", font, 12, 1,0,0,1,1));
		body.addCell(getCellCa(orderInfo.getCustomerDate(), font, 12, 1,1,1,1,1));


		
		document.add(body);
		Paragraph paragraph1s = new Paragraph();
		paragraph1s.setSpacingBefore(2);
		document.add(paragraph1s);
		PdfPTable tblProduct = new PdfPTable(8);
		tblProduct.setWidthPercentage(100);
		tblProduct.addCell(getCellC("Item No", font, 12, 1,1,1,1,1));
		tblProduct.addCell(getCellC("Qty", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("Size", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("Common Name", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("Scientific Name", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("Group", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("Price", font, 12, 1,0,1,1,1));
		tblProduct.addCell(getCellC("Amount", font, 12, 1,0,1,1,1));
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
	//	pcell1.addElement(getText("Invoice No. " + currentOrder.getOrder_id(), font, 12, 1));
		pcell1.addElement(getText("Invoice No. " + orderidc, font, 12, 1));

		PdfPCell pcell2 = new PdfPCell();
	//	pcell2.addElement(getText("Invoice Date: " + currentOrder.getCustomer_date(), font, 12, 1));
		pcell2.addElement(getText("Cust No. " + cusid, font, 12, 1));

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
		Paragraph paragraph = getText("CREDIT MEMO", font, 18, 1);
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
