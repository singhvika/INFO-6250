package com.vikas.eventplanner.views;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.vikas.eventplanner.dao.EventDAO;
import com.vikas.eventplanner.dao.ItemDAO;
import com.vikas.eventplanner.pojo.Event;
import com.vikas.eventplanner.pojo.User;

@Component
public class PDFView extends AbstractCustomPDFView {

	

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		// List<Book> listBooks = (List<Book>) model.get("listBooks");
		
		EventDAO eventDAO = (EventDAO)model.get("eventDao");
		ItemDAO itemDao = (ItemDAO)model.get("itemDao");
		
		
		Event event = (Event) model.get("event");
		Chunk eventBaseDetailsChunk = new Chunk();
		eventBaseDetailsChunk.append("\t \t EVENT SUMMARY \t \t");
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-2);
		dottedline.setGap(2f);

		Paragraph para = new Paragraph();
		para.add(eventBaseDetailsChunk);
		para.add(dottedline);

		Paragraph p2 = new Paragraph();
		Chunk ch2 = new Chunk();
		ch2.append("\n EVENT NAME:		 	" + event.getEventName());
		ch2.append("\n EVENT FROM DATE:		" + event.getFromDate());
		ch2.append("\n EVENT TO DATE:		" + event.getToDate());
		ch2.append("\n EVENT By:			" + event.getCreatedByUser().getLastName() + ", "
				+ event.getCreatedByUser().getFirstName() + " | " + event.getCreatedByUser().getEmail());

		double totalEventExpense = 0.0;
		for (com.vikas.eventplanner.pojo.Item item : event.getItemList()) {
			if (item.getFullfilledByUser() != null) {
				totalEventExpense = totalEventExpense + item.getTotalPrice();
			}
		}

		ch2.append("\n EVENT TOTAL EXPENSE: " + totalEventExpense);

		double equalExpensePerUser = totalEventExpense / (int) event.getParticipatingUsers().size();

		ch2.append("\n EXPENSE PER USER: " + equalExpensePerUser);

		p2.add(ch2);
		p2.add(dottedline);

		Chunk ch3 = new Chunk();
		ch3.append("\n \n PARTICIPATING USERS:");
		p2.add(ch3);
		doc.add(para);

		doc.add(p2);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);

		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
		font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("First Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Last Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("User Expense", font ));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Balance", font));
		table.addCell(cell);
		
		System.out.println("8888888888888888" + event.getItemList());

		for (User user : event.getParticipatingUsers()) {
			double userExpense = 0;
			double userBalance = 0;
			System.out.println("itemDAO: "+itemDao);
			for (com.vikas.eventplanner.pojo.Item item : event.getItemList())
			{	System.out.println("item: "+item.getName());
				if (item.getFullfilledByUser()!=null)
				{
					if (item.getFullfilledByUser().getEmail().equals(user.getEmail()))
					{
						userExpense = userExpense + item.getTotalPrice();
						System.out.println("user Expense: "+userExpense);
					}
				}
			}
			userBalance = userExpense - equalExpensePerUser;
			table.addCell(user.getFirstName());
			table.addCell(user.getLastName());
			table.addCell(user.getEmail());
			table.addCell(Double.toString(userExpense));
			table.addCell(Double.toString(userBalance));
			System.out.println(user.getEmail());
		}

		doc.add(table);

		Paragraph p3 = new Paragraph();
		Chunk ch4 = new Chunk();
		ch4.append("\n CLAIMED ITEM LIST:");

		p3.add(ch4);
		doc.add(p3);

		PdfPTable claimedItemListTable = new PdfPTable(5);

		claimedItemListTable.setWidthPercentage(100.0f);

		claimedItemListTable.setSpacingBefore(10);

		cell.setPhrase(new Phrase("Item Name", font));
		claimedItemListTable.addCell(cell);

		cell.setPhrase(new Phrase("Requested Quantity", font));
		claimedItemListTable.addCell(cell);

		cell.setPhrase(new Phrase("Fullfilled By", font));
		claimedItemListTable.addCell(cell);

		cell.setPhrase(new Phrase("Fullfulled Quantity", font));
		claimedItemListTable.addCell(cell);

		cell.setPhrase(new Phrase("Total Price", font));
		claimedItemListTable.addCell(cell);

		for (com.vikas.eventplanner.pojo.Item item : event.getItemList()) {
			if (item.getFullfilledByUser() != null) {
				claimedItemListTable.addCell(item.getName());
				claimedItemListTable.addCell(Integer.toString(item.getRequestedQuantity()));
				claimedItemListTable.addCell(
						item.getFullfilledByUser().getLastName() + ", " + item.getFullfilledByUser().getFirstName());
				claimedItemListTable.addCell(Integer.toString(item.getFullFulledQuantity()));
				claimedItemListTable.addCell(Double.toString(item.getTotalPrice()));

			}
		}

		doc.add(claimedItemListTable);

		Paragraph p5 = new Paragraph();
		p5.add(dottedline);
		Chunk ch5 = new Chunk();
		ch5.append("\n \n Unclaimed Item List");
		p5.add(ch5);
		doc.add(p5);

		PdfPTable unclaimedItemListTable = new PdfPTable(2);
		unclaimedItemListTable.setWidthPercentage(100.0f);
		unclaimedItemListTable.setSpacingBefore(10);

		cell.setPhrase(new Phrase("Item Name", font));
		unclaimedItemListTable.addCell(cell);

		cell.setPhrase(new Phrase("Requested Quantity", font));
		unclaimedItemListTable.addCell(cell);

		for (com.vikas.eventplanner.pojo.Item item : event.getItemList()) {
			if (item.getFullfilledByUser() == null) {
				unclaimedItemListTable.addCell(item.getName());
				unclaimedItemListTable.addCell(Integer.toString(item.getRequestedQuantity()));

			}
		}

		doc.add(unclaimedItemListTable);

	}

}
