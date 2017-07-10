package edu.learn.beans.views;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Ticket;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

@Component
public class PdfTicketsView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		List<Ticket> tickets = (List<Ticket>) model.get("tickets");
		Event event = (Event) model.get("event");
		Auditorium auditorium = (Auditorium) model.get("auditorium");

		Table table = new Table(5);
		table.addCell("ID");
		table.addCell("Time");
		table.addCell("Seats");
		table.addCell("User email");
		table.addCell("Price");

		for (Ticket ticket : tickets) {
			table.addCell(Long.valueOf(ticket.getId()).toString());
			table.addCell(ticket.getDateTime().toString());
			table.addCell(ticket.getSeats());
			table.addCell(ticket.getUser().getEmail());
			table.addCell(ticket.getPrice().toString());
		}

		document.add(table);
	}

}
